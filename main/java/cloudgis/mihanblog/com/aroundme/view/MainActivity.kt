package cloudgis.mihanblog.com.aroundme.view

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import cloudgis.mihanblog.com.aroundme.R
import cloudgis.mihanblog.com.aroundme.adapter.FrgAdaptor
import cloudgis.mihanblog.com.aroundme.model.Venue
import cloudgis.mihanblog.com.aroundme.presenter.MainActivityContract
import cloudgis.mihanblog.com.aroundme.task.LocResultReceiver
import cloudgis.mihanblog.com.aroundme.task.LocationTrackingService
import cloudgis.mihanblog.com.aroundme.util.Constants
import cloudgis.mihanblog.com.aroundme.util.ToastUtil
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasFragmentInjector
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : AppCompatActivity(), MainActivityContract.View, HasFragmentInjector, HasSupportFragmentInjector {

    @Inject
    lateinit var supFragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<android.app.Fragment>

    private val PERMISSION_LOCATION_REQUEST_CODE = 105

    @Inject
    lateinit var presenter: MainActivityContract.Presenter

    private var fusedLocationClient: FusedLocationProviderClient? = null

    var subscriptions: CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        AndroidInjection.inject(this)
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.attachView(this)
        subscriptions = CompositeDisposable()


        var venueListFragment = VenueListFragment.newInstance()
        var venueMapFragment = VenueMapFragment.newInstance()

        var listFrgs = mutableListOf<Fragment>()
        listFrgs.add(venueListFragment)
        listFrgs.add(venueMapFragment)


        var frgAdaptor = FrgAdaptor(listFrgs, supportFragmentManager)
        viewPager.adapter = frgAdaptor
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        showMap.visibility = View.VISIBLE
                        showList.visibility = View.GONE
                    }
                    1 -> {
                        showMap.visibility = View.GONE
                        showList.visibility = View.VISIBLE
                    }
                }
            }
        })
        showList.setOnClickListener {
            viewPager.currentItem = 0
        }
        showMap.setOnClickListener {
            viewPager.currentItem = 1
        }
        showList.visibility=View.GONE
        viewPager.currentItem = 0

    }

    override fun onStart() {
        super.onStart()
        requestLocationUpdates()
    }

    override fun onStop() {
        super.onStop()
        removeLocationUpdates()
    }

    private fun requestLocationUpdates() {

        var locationManager =    getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            showError(" سرویس Location دستگاه را فعال نمایید")
            return
        }

        val locReq = createLocationRequest(5)

        val pIntent = getPendingIntent(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val resultReceiver = LocResultReceiver(object : LocResultReceiver.Callback {
            override fun onLocRecieved(loc: Location) {
                presenter.locationChanged(loc, this@MainActivity)
            }

        })
        val resReciverIntent = Intent(this, LocationTrackingService::class.java)
        resReciverIntent.action = LocationTrackingService.ACTION_GET_RESULT_REC
        resReciverIntent.putExtra("resultReceiver", resultReceiver)
        startService(resReciverIntent)

        if (ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient!!.requestLocationUpdates(locReq, pIntent)
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), PERMISSION_LOCATION_REQUEST_CODE)
            }else{
                fusedLocationClient!!.requestLocationUpdates(locReq, pIntent)
            }

        }

    }

    private fun removeLocationUpdates() {
        val pIntent = getPendingIntent(this)
        fusedLocationClient?.removeLocationUpdates(pIntent)
    }

    private fun getPendingIntent(context: Context): PendingIntent {

        val intent = Intent(context, LocationTrackingService::class.java)
        intent.action = LocationTrackingService.ACTION_GET_LOC_UPDATES
        return PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private fun createLocationRequest(minTempPeriod: Long): LocationRequest {
        var locationRequest = LocationRequest()
        locationRequest.interval = minTempPeriod * 1000
        locationRequest.fastestInterval = minTempPeriod * 1000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        return locationRequest
    }

    override fun onDestroy() {
        super.onDestroy()
        subscriptions!!.clear()
    }

    override fun showMessage(msg: String) {
        ToastUtil.ShowToast(this, msg, ToastUtil.ToastActionState.Info, Toast.LENGTH_SHORT)
    }

    override fun showError(error: String) {
        if (error == Constants.ERROR_LOC_IS_EMPTY){
             requestLocationUpdates()
        }else{
            ToastUtil.ShowToast(this, error, ToastUtil.ToastActionState.Failure, Toast.LENGTH_LONG)
        }
     }

    override fun showProgress(msg: String) {
        // Toast.makeText(this,msg,Toast.LENGTH_LONG).show()

    }

    override fun dismissProgress() {
    }


    override fun showModel(venues: MutableList<Venue>) {
        ToastUtil.ShowToast(this, venues.size.toString(), ToastUtil.ToastActionState.Info, Toast.LENGTH_SHORT)

    }

    override fun fragmentInjector(): AndroidInjector<android.app.Fragment> {
        return fragmentDispatchingAndroidInjector
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return supFragmentDispatchingAndroidInjector
    }

    fun refechData(doSync: Boolean) {
        presenter.fetchRemoteData(this,doSync)
    }
    fun setTitle(title:String){
        titleText.text= title
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_LOCATION_REQUEST_CODE ->{
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                     requestLocationUpdates()
                }
            }
        }
     }

}
