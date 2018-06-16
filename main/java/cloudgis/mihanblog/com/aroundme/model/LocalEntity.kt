package cloudgis.mihanblog.com.aroundme.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity

@Entity(tableName = "LocalEntity")
class LocalEntity(
        @ColumnInfo(name = "Venue", typeAffinity = ColumnInfo.TEXT)
        var venue: Venue?

) : BaseClass()