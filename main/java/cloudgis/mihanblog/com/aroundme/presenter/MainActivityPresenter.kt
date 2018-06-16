package cloudgis.mihanblog.com.aroundme.presenter

import android.content.Context
import android.location.Location
import cloudgis.mihanblog.com.aroundme.R
import cloudgis.mihanblog.com.aroundme.dataSource.local.EntityRepository
import cloudgis.mihanblog.com.aroundme.dataSource.local.SharedPrefUtil
import cloudgis.mihanblog.com.aroundme.dataSource.remote.HttpUtil
import cloudgis.mihanblog.com.aroundme.model.LocalEntity
import cloudgis.mihanblog.com.aroundme.model.SearchEntity
import cloudgis.mihanblog.com.aroundme.util.Constants
import com.google.gson.Gson
import io.reactivex.Single
import io.reactivex.SingleEmitter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import javax.inject.Inject


class MainActivityPresenter @Inject constructor() : MainActivityContract.Presenter {


    private val subscriptions = CompositeDisposable()

    private lateinit var view: MainActivityContract.View


    @Inject
    lateinit var sharedPrefUtil: SharedPrefUtil

    @Inject
    lateinit var entityRepository: EntityRepository

    override fun subscribe() {

    }

    override fun unSubscribe() {
        subscriptions.clear()
    }

    override fun attachView(view: MainActivityContract.View) {
        this.view = view
    }


    override fun syncData() {
        Single.create({ e: SingleEmitter<Boolean> ->
            val ll = sharedPrefUtil.getString(Constants.LAST_LOC)
            if (ll.isNotEmpty()) {


                val radius = sharedPrefUtil.getInt(Constants.PAR_RADIUS)

                val entities = entityRepository.getLocalEntitiesSimple()

                val s = ll.split(",")

                val userCurLoc = Location("")
                userCurLoc.latitude = s[0].toDouble()
                userCurLoc.longitude = s[1].toDouble()

                var deleteQueue = mutableListOf<Int>()

                entities.forEach {
                    val venueLoc = Location("")
                    venueLoc.latitude = it.venue!!.location!!.lat
                    venueLoc.longitude = it.venue!!.location.lng
                    val dist2Loc = userCurLoc.distanceTo(venueLoc)
                    if (dist2Loc > radius) {
                        deleteQueue.add(it.id)
                    }
                }
                if (deleteQueue.isNotEmpty()) {
                    entityRepository.deleteAllLocalEntity(deleteQueue)
                }

                e.onSuccess(true)
            } else {
                e.onError(Throwable( Constants.ERROR_LOC_IS_EMPTY))
            }

        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    view.showProgress("در حال دریافت اطلاعات...")
                }
                .subscribe(
                        {

                        },
                        {
                            view.showError(it.message!!)
                        })
    }

    override fun fetchRemoteData(context: Context, doSync: Boolean) {

        Single.create({ e: SingleEmitter<MutableList<LocalEntity>> ->

            if (doSync)
                syncData()

            val ll = sharedPrefUtil.getString(Constants.LAST_LOC)
            var radius = sharedPrefUtil.getInt(Constants.PAR_RADIUS)

            if (radius == -1) {
                radius = 250
                sharedPrefUtil.putInt(Constants.PAR_RADIUS, radius)
            }

            val url = context.getString(R.string.baseUrl)

            var params = JSONObject()
            params.put(Constants.PAR_CLIENT_ID, context.getString(R.string.client_id))
            params.put(Constants.PAR_CLIENT_SECRET, context.getString(R.string.client_secret))
            params.put(Constants.PAR_LL, ll)
            params.put(Constants.PAR_LIMIT, 50)
            params.put(Constants.PAR_RADIUS, radius)
            params.put(Constants.PAR_V, "20180323")
            params.put(Constants.PAR_LIMIT, 50)
            params.put(Constants.PAR_INTENT, "browse")

            val responce = HttpUtil.doGet(url, HttpUtil.getPostDataString(params))
            val gson = Gson()
            val searchEntity = gson.fromJson<SearchEntity>(responce, SearchEntity::class.java)
            var list = mutableListOf<LocalEntity>()

            val localEntities = entityRepository.getLocalEntitiesSimple()

            searchEntity.response.venues.forEach {
                val newV = it
                if (localEntities.count { it.venue!!.id == newV.id } == 0) {
                    list.add(LocalEntity(it))
                }
            }
            entityRepository.insert(list)
            e.onSuccess(list)

        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    view.showProgress("در حال دریافت اطلاعات...")

                }
                .subscribe(
                        {
                            view.dismissProgress()
                        },
                        {
                            view.dismissProgress()
                            view.showError(it.message!!)
                        })
    }


    override fun locationChanged(loc: Location, context: Context) {
        sharedPrefUtil.putString(Constants.LAST_LOC, "${loc.latitude},${loc.longitude}")
        fetchRemoteData(context, true)
    }


}