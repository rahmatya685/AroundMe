package cloudgis.mihanblog.com.aroundme.adapter

import android.support.v4.app.Fragment
import android.support.v7.util.SortedList
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import cloudgis.mihanblog.com.aroundme.R
import cloudgis.mihanblog.com.aroundme.model.Venue
import cloudgis.mihanblog.com.aroundme.util.inflate
import kotlinx.android.synthetic.main.item_venue.view.*
import java.util.*

class VenueAdaptor(frg:Fragment)  : RecyclerView.Adapter<VenueAdaptor.ViewHolder>() {

    private var mComparator: Comparator<Venue>? = null
    private var listsner:Listsner? = null

    private val mSortedList =
            SortedList(Venue::class.java, object : SortedList.Callback<Venue>() {
                override fun compare(a: Venue, b: Venue): Int {
                    return mComparator!!.compare(a, b)
                }

                override fun onInserted(position: Int, count: Int) {
                    notifyItemRangeInserted(position, count)
                }

                override fun onRemoved(position: Int, count: Int) {
                    notifyItemRangeRemoved(position, count)
                }

                override fun onMoved(fromPosition: Int, toPosition: Int) {
                    notifyItemMoved(fromPosition, toPosition)
                }

                override fun onChanged(position: Int, count: Int) {
                    notifyItemRangeChanged(position, count)
                }

                override fun areContentsTheSame(oldItem: Venue, newItem: Venue): Boolean {
                    return oldItem == newItem
                }

                override fun areItemsTheSame(item1: Venue, item2: Venue): Boolean {
                    return item1.id == item2.id
                }
            })
    init {
        mComparator = Comparator<Venue> { item1, item2 -> item1!!.location.distance .compareTo(item2!!.location.distance) }

        listsner = frg as Listsner

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent.inflate(R.layout.item_venue),listsner!!)


    override fun getItemCount(): Int {
        return  mSortedList.size()
    }

    override fun onBindViewHolder(holder: ViewHolder, p: Int) = holder.bind(mSortedList[p],p)

    class ViewHolder(itemView: View ,val listsner:Listsner) : RecyclerView.ViewHolder(itemView) {
        fun bind(venue: Venue,p: Int) = with(itemView) {
            setOnClickListener {
                listsner.onItemClicked(venue)
            }


            locName.text = venue.name
            if (venue.categories.isNotEmpty()){
                locType.text = "${venue.categories[0].name}"
            }else{
                locType.visibility=View.GONE
            }
            var address=""
            if (venue.location.city != null){
                address += venue.location.city
            }
            if (venue.location.address != null)  {
                address +=   venue.location.address
            }
            if (address.isNotEmpty()) {
                locAddress.text = address
            }else{
                locAddress.text = "آدرسی ثبت نشده است"
            }
            distance.text = "  در فاصله ی  ${venue.location.distance} متری "

        }
    }

    fun replaceAll(venues: MutableList<Venue>) {
        val removeQueue= mutableListOf<Venue>()
        for (i in mSortedList.size() - 1 downTo 0) {
            val oldVenue = mSortedList.get(i)
            if (venues.count{it.id == oldVenue.id}==0){
                removeQueue.add(oldVenue)
            }else{
                venues.remove(oldVenue)
            }
        }

        mSortedList.beginBatchedUpdates()
        mSortedList.addAll(venues)
        mSortedList.endBatchedUpdates()
    }


    interface Listsner{
        fun onItemClicked(venue: Venue)
    }

}