package ir.mihanblog.cloudgis.aroundme.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.PrimaryKey

open class BaseClass {
    @ColumnInfo(name = "ID")
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}