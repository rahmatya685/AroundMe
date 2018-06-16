package cloudgis.mihanblog.com.aroundme.dataSource.local

import android.content.SharedPreferences
import javax.inject.Inject
import android.R.id.edit
import javax.inject.Singleton


/**
 * Created by alireza on 3/15/2018.
 */
@Singleton
public class SharedPrefUtil @Inject constructor(sharedPreferences: SharedPreferences) {


    private var sharedPref: SharedPreferences = sharedPreferences


    fun putInt(key: String, data: Int) {
        sharedPref.edit().putInt(key, data).apply()
    }

    fun getInt(key: String): Int {
        return sharedPref.getInt(key, -1)
    }
    fun putBoolean(key: String, data: Boolean) {
        sharedPref.edit().putBoolean(key, data).apply()
    }

    fun getBoolean(key: String): Boolean {
        return sharedPref.getBoolean(key, false)
    }

    fun putString(key: String,data:String){
        sharedPref.edit().putString(key,data).apply()

    }

    fun getString(key: String):String{
        return sharedPref.getString(key,"")
    }
}