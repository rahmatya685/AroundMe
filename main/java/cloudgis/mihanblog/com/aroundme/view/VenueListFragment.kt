package cloudgis.mihanblog.com.aroundme.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import cloudgis.mihanblog.com.aroundme.R
import cloudgis.mihanblog.com.aroundme.adapter.VenueAdaptor
import cloudgis.mihanblog.com.aroundme.model.Venue
import cloudgis.mihanblog.com.aroundme.presenter.VenueContract
import cloudgis.mihanblog.com.aroundme.util.ToastUtil
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_venue_list.*
import javax.inject.Inject


class VenueListFragment : Fragment(), VenueContract.ListView, VenueAdaptor.Listsner {


    @Inject
    lateinit var presenter: VenueContract.Presenter

    lateinit var adaptor: VenueAdaptor

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
        presenter.attachView(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_venue_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val linearLayoutManager = LinearLayoutManager(context)
        rv_venue.layoutManager = linearLayoutManager
        adaptor = VenueAdaptor(this)
        rv_venue.adapter = adaptor

        swipeLayout.setOnRefreshListener(this::onRefresh)
        swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light)

        val scrollListener = object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                progressBar.visibility = View.VISIBLE
                presenter.increaseRadius()
                (activity as MainActivity).refechData(false)
            }
        }
        rv_venue.addOnScrollListener(scrollListener)

        presenter.getData()
    }

    private fun onRefresh() {
        presenter.setDefRadius()
        (activity as MainActivity).refechData(true)
    }


    companion object {

        @JvmStatic
        fun newInstance(): VenueListFragment {
            var frg = VenueListFragment()
            return frg
        }

    }

    override fun showModel(venues: MutableList<Venue>) {
        adaptor.replaceAll(venues)
        (activity as MainActivity).setTitle("  تعداد مکانهای اطراف شما (${adaptor.itemCount})")

    }

    override fun showMessage(msg: String) {
        ToastUtil.ShowToast(activity!!, msg, ToastUtil.ToastActionState.Info, Toast.LENGTH_SHORT)
    }

    override fun showError(error: String) {
        ToastUtil.ShowToast(activity!!, error, ToastUtil.ToastActionState.Failure, Toast.LENGTH_LONG)
    }

    override fun showProgress(msg: String) {
        swipeLayout.isRefreshing = true
    }


    override fun dismissProgress() {
        progressBar.visibility = View.GONE
        swipeLayout.isRefreshing = false
    }

    override fun onItemClicked(venue: Venue) {
       val frg=   VenueDetailFragment.newInstance()
        frg.show(childFragmentManager,VenueDetailFragment::class.java.simpleName)
    }
}
