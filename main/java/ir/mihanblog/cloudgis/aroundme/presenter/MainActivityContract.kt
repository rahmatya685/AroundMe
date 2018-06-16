package ir.mihanblog.cloudgis.aroundme.presenter

import android.content.Context
import android.location.Location
import ir.mihanblog.cloudgis.aroundme.model.Venue
import com.edsab.pm.presenter.BaseContract

interface MainActivityContract {
    interface Presenter : BaseContract.Presenter<View> {
        fun locationChanged(loc: Location, context: Context)
        fun syncData()
        fun fetchRemoteData(context: Context,doSync: Boolean)
    }

    interface View : BaseContract.View {
        fun showModel(venues: MutableList<Venue>)

    }
}