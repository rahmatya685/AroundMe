package cloudgis.mihanblog.com.aroundme.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter

class FrgAdaptor(
        var frgs: MutableList<Fragment>, fragmentManager: android.support.v4.app.FragmentManager
) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): android.support.v4.app.Fragment {
         return frgs[position]
    }

    override fun getCount(): Int {
        return frgs.size
    }
}