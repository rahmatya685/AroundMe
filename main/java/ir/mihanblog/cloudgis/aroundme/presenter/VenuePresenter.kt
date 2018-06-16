package ir.mihanblog.cloudgis.aroundme.presenter

import android.util.Log
import ir.mihanblog.cloudgis.aroundme.dataSource.local.EntityRepository
import ir.mihanblog.cloudgis.aroundme.dataSource.local.SharedPrefUtil
import ir.mihanblog.cloudgis.aroundme.model.Venue
import ir.mihanblog.cloudgis.aroundme.util.Constants
import com.edsab.pm.presenter.BaseContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class VenuePresenter @Inject constructor() : VenueContract.Presenter {


    private val subscriptions = CompositeDisposable()

    lateinit var view: BaseContract.View

    @Inject
    lateinit var sharedPrefUtil: SharedPrefUtil

    @Inject
    lateinit var entityRepository: EntityRepository

    override fun subscribe() {

    }

    override fun unSubscribe() {
        subscriptions.clear()
    }


    override fun attachView(view: BaseContract.View) {
        this.view = view
    }


    override fun getData() {


        entityRepository.getLocalEntities()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    if (view is VenueContract.MapView) {
                        (view as VenueContract.MapView).showProgress("")
                    }
                    if (view is VenueContract.ListView) {
                        (view as VenueContract.ListView).showProgress("")
                    }
                }
                .subscribe(
                        {
                            val venues = mutableListOf<Venue>()

                            it.forEach {
                                venues.add(it.venue!!)
                            }

                            val all = entityRepository.getLocalEntitiesSimple()
                            Log.e("", all.size.toString())
                            if (view is VenueContract.MapView) {
                                (view as VenueContract.MapView).showModel(venues)
                                (view as VenueContract.MapView).dismissProgress()
                            }
                            if (view is VenueContract.ListView) {
                                (view as VenueContract.ListView).showModel(venues)
                                (view as VenueContract.ListView).dismissProgress()
                            }

                        },
                        {
                            if (view is VenueContract.MapView) {
                                (view as VenueContract.MapView).dismissProgress()
                                (view as VenueContract.MapView).showError(it.message!!)
                            }
                            if (view is VenueContract.ListView) {
                                (view as VenueContract.ListView).dismissProgress()
                                (view as VenueContract.ListView).showError(it.message!!)
                            }
                        }
                )
    }
    override fun increaseRadius() {
        val oldRad= sharedPrefUtil.getInt(Constants.PAR_RADIUS)
        sharedPrefUtil.putInt(Constants.PAR_RADIUS, oldRad+100)
    }
    override fun setDefRadius() {
        sharedPrefUtil.putInt(Constants.PAR_RADIUS, 250)
    }
}