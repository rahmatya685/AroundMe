package ir.mihanblog.cloudgis.aroundme.dataSource.local.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import ir.mihanblog.cloudgis.aroundme.model.LocalEntity
import com.edsab.pm.dao.BaseDao
import io.reactivex.Flowable

 @Dao
interface LocalEntityDao : BaseDao<LocalEntity> {
    @Query("select * from LocalEntity")
    fun getLocalEntities(): Flowable<List<LocalEntity>>

     @Query("select * from LocalEntity")
     fun getLocalEntitiesSimple():   List<LocalEntity>

    @Query("delete   from LocalEntity WHERE LocalEntity.ID IN (:ids)")
    fun deleteLocalEntities(ids:List<Int>)
}