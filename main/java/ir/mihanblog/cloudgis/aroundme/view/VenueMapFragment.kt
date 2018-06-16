package ir.mihanblog.cloudgis.aroundme.view

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import ir.mihanblog.cloudgis.aroundme.R
import ir.mihanblog.cloudgis.aroundme.model.Venue
import ir.mihanblog.cloudgis.aroundme.presenter.VenueContract
import ir.mihanblog.cloudgis.aroundme.util.DialogUtil
import ir.mihanblog.cloudgis.aroundme.util.GeneralUtil
import ir.mihanblog.cloudgis.aroundme.util.ToastUtil
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.*
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_venue_map.*
import java.util.*
import javax.inject.Inject

class VenueMapFragment : Fragment(), VenueContract.MapView {


    private var mMapView: MapView? = null
    private var map: GoogleMap? = null
    private var mBundle: Bundle? = null

    private val REQUEST_CODE_LOCATION = 100

    private var getLocAction: String? = null

    private var markers: MutableList<Marker> = mutableListOf()


    private var mFusedLocationClient: FusedLocationProviderClient? = null
    @Inject
    lateinit var presenter: VenueContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity!!)

        presenter.attachView(this)


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_venue_map, container, false)


        try {
            MapsInitializer.initialize(activity!!.applicationContext)
            mMapView = rootView.findViewById(R.id.mapView)
            mMapView!!.onCreate(mBundle)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        if (map == null) {
            mMapView?.getMapAsync(this::onMapReady)
        }


        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fabMyLocation.setOnClickListener {
            requestLocation4Action("ZoomToCurLoc")
        }
        presenter.getData()
    }


    companion object {
        @JvmStatic
        fun newInstance(): VenueMapFragment {
            var frg = VenueMapFragment()
            return frg
        }
    }

    override fun showModel(venues: MutableList<Venue>) {

        if (venues.isEmpty())
            return

        val deleteQueue = mutableListOf<Marker>()
        val iterator = markers.iterator()
        while (iterator.hasNext()) {
            val m = iterator.next()
            val v = m.tag as Venue
            if (venues.count { v.id == it.id } == 0) {
                m.remove()
                deleteQueue.add(m)

            }
        }
        markers.removeAll(deleteQueue)

        venues.forEach {

            val v =it
            if (markers.count { (it.tag as Venue).id == v.id } == 0) {

                val markerOptions = MarkerOptions()
                markerOptions.position(LatLng(it.location.lat, it.location.lng))
                markerOptions.icon(GeneralUtil.vectorToBitmap(activity!!, R.drawable.location))
                val marker = map?.addMarker(markerOptions)
                marker!!.tag = it
                marker.title = it.name
                markers.add(marker!!)
            }
        }
    }


    override fun showProgress(msg: String) {
        progressBar.visibility = View.VISIBLE
    }

    override fun dismissProgress() {
        progressBar.visibility = View.GONE
    }

    override fun showMessage(msg: String) {
        ToastUtil.ShowToast(activity!!, msg, ToastUtil.ToastActionState.Info, Toast.LENGTH_SHORT)
    }

    override fun showError(error: String) {
        ToastUtil.ShowToast(activity!!, error, ToastUtil.ToastActionState.Failure, Toast.LENGTH_LONG)
    }

    fun onMapReady(p0: GoogleMap?) {
        map = p0
        map?.isTrafficEnabled = true;
        if (ActivityCompat.checkSelfPermission(activity!!, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity!!, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            map!!.isMyLocationEnabled = true

            val c = Calendar.getInstance()
            val timeOfDay = c.get(Calendar.HOUR_OF_DAY)

            map!!.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            activity!!, R.raw.night_style_json))
            requestLocation4Action("ZoomToCurLoc")

            if (timeOfDay in 6..11) {
                ToastUtil.ShowToast(activity!!, "صبح بخیر", ToastUtil.ToastActionState.Info)

            } else if (timeOfDay in 12..15) {
                ToastUtil.ShowToast(activity!!, "ظهر بخیر", ToastUtil.ToastActionState.Info)

            } else if (timeOfDay in 16..20) {
                ToastUtil.ShowToast(activity!!, "عصر بخیر", ToastUtil.ToastActionState.Info)

            } else if (timeOfDay in 21..5) {

                ToastUtil.ShowToast(activity!!, "شب بخیر", ToastUtil.ToastActionState.Info)
            }
        }

        try {
            val parent = mMapView?.findViewWithTag<View>("GoogleMapMyLocationButton")!!.parent as ViewGroup
            parent.post {
                try {
                    val locationButton = (mMapView?.findViewById<View>(Integer.parseInt("1"))!!.parent as View).findViewById<ImageView>(Integer.parseInt("2"))
                    locationButton.visibility = View.GONE

                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

    }

    override fun onPause() {
        super.onPause()
        mMapView?.onPause()

    }

    override fun onLowMemory() {
        super.onLowMemory()
        mMapView?.onLowMemory()
    }

    override fun onResume() {
        super.onResume()
        mMapView?.onResume()
    }

    override fun onStart() {
        super.onStart()
        mMapView?.onStart()

    }


    override fun onStop() {
        super.onStop()
        mMapView?.onStop()
    }

    fun requestLocation4Action(getLocAction: String) {
        this.getLocAction = getLocAction
        if (ActivityCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_CODE_LOCATION)

            return
        }
        mFusedLocationClient?.lastLocation!!.addOnSuccessListener(this::onSuccess)

    }

    private fun onSuccess(location: Location?) {
        if (location == null) {
            val manager = activity!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val statusOfGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            if (!statusOfGPS) {

                activity!!.runOnUiThread {
                    DialogUtil.showInfo(activity, getString(R.string.msg_info_enable_gps), "", "", View.OnClickListener { v ->
                        when (v.id) {
                            R.id.btnOk -> enableGps()
                            R.id.btnCancel -> {
                            }
                        }
                        val dialog = v.tag as Dialog
                        dialog.dismiss()
                    })
                }

            }
            return
        }


        //check for
        when (getLocAction) {
            "ZoomToCurLoc" -> zoomToCuLoc(location)
        }
        this.getLocAction = ""
    }

    private fun zoomToCuLoc(location: Location?) {
        if (location != null) {
            val sydney = LatLng(location.latitude, location.longitude)
            val cameraPosition = CameraPosition.Builder().target(sydney).zoom(17f).build()
            map?.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        }
    }

    private fun enableGps() {
        val gpsOptionsIntent = Intent(
                Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        startActivityForResult(gpsOptionsIntent, REQUEST_CODE_LOCATION)
    }

}
