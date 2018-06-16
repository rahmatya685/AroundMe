package cloudgis.mihanblog.com.aroundme.task

import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver

class LocResultReceiver ( val callback:Callback): ResultReceiver(Handler()) {



    override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
        super.onReceiveResult(resultCode, resultData)
        val location:Location= resultData!!.get(LocationTrackingService.LAST_LOC) as Location
        callback.onLocRecieved(location)
    }

    interface Callback{
        fun onLocRecieved(loc:Location)
    }
}