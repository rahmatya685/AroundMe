package cloudgis.mihanblog.com.aroundme.task

import android.app.IntentService
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.os.ResultReceiver
import android.util.Log
import cloudgis.mihanblog.com.aroundme.dataSource.local.SharedPrefUtil
import cloudgis.mihanblog.com.aroundme.util.Constants
import com.google.android.gms.location.LocationResult
import dagger.android.AndroidInjection
import javax.inject.Inject


class LocationTrackingService : IntentService(LocationTrackingService::class.java.simpleName) {

    @Inject
    lateinit var sharedPrefUtil: SharedPrefUtil


    companion object {
        const val ACTION_GET_LOC_UPDATES = "ACTION_GET_LOC_UPDATES"
        const val LAST_LOC = "LAST_LOC"
        const val ACTION_GET_RESULT_REC = "ACTION_GET_RESULT_REC"
        var resultReceiver: ResultReceiver? = null

    }

    private var lastLoc: Location? = null
    override fun onCreate() {
        super.onCreate()
        AndroidInjection.inject(this)
    }

    override fun onHandleIntent(intent: Intent?) {

        if (intent?.action == null)
            return
        if (intent.action == ACTION_GET_RESULT_REC) {
            resultReceiver = intent.extras["resultReceiver"] as ResultReceiver

        }
        if (intent.action == ACTION_GET_LOC_UPDATES) {
            val ll = sharedPrefUtil.getString(Constants.LAST_LOC)
            if (ll.isNotEmpty() && lastLoc == null) {
                lastLoc = Location("")
                val sp = ll.split(",")
                lastLoc!!.latitude = sp[0].toDouble()
                lastLoc!!.longitude = sp[1].toDouble()
            }

            val result = LocationResult.extractResult(intent) ?: return
            val newLocation = result.lastLocation
            val data = Bundle()
            if (lastLoc == null) {
                lastLoc = newLocation
                data.putParcelable(LAST_LOC, newLocation)
                resultReceiver?.send(1, data)
            } else {
                val rad = sharedPrefUtil.getInt(Constants.PAR_RADIUS)
                if (lastLoc?.distanceTo(newLocation)!! > rad) {
                    data.putParcelable(LAST_LOC, newLocation)
                    lastLoc = newLocation
                    resultReceiver?.send(1, data)
                }
            }
            Log.e(ACTION_GET_LOC_UPDATES, newLocation.toString())
        }
    }


}