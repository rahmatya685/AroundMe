package ir.mihanblog.cloudgis.aroundme.presenter

import ir.mihanblog.cloudgis.aroundme.model.Venue
import com.edsab.pm.presenter.BaseContract

interface VenueContract {

    interface Presenter : BaseContract.Presenter<BaseContract.View> {
        fun getData()
        fun increaseRadius()
        fun setDefRadius()
    }


    interface ListView : BaseContract.View {
        fun showModel(venues: MutableList<Venue>)

    }
    interface MapView : BaseContract.View {
        fun showModel(venues: MutableList<Venue>)

    }
}