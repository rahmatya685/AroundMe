package ir.mihanblog.cloudgis.aroundme.view

import android.app.Dialog
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ir.mihanblog.cloudgis.aroundme.R


class VenueDetailFragment : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setStyle(STYLE_NO_TITLE, 0)
        super.onCreate(savedInstanceState)

    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var dialog = super.onCreateDialog(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.window.statusBarColor =ContextCompat.getColor(activity!!,R.color.app_blue_dark)
        }
        dialog.window!!
                .attributes.windowAnimations = R.style.DialogAnimation
        return dialog
    }
    override fun getTheme(): Int {
        return R.style.FullScreenDialog
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_venue_detail, container, false)
    }


    companion object {
        @JvmStatic
        fun newInstance() =
                VenueDetailFragment().apply {

                }
    }
}
