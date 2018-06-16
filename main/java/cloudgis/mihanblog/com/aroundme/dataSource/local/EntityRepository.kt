package cloudgis.mihanblog.com.aroundme.dataSource.local

import cloudgis.mihanblog.com.aroundme.model.LocalEntity
import io.reactivex.Flowable

interface EntityRepository {

    fun insert(localEntities: List<LocalEntity>)
    fun deleteAllLocalEntity(ids: List<Int>)
    fun getLocalEntities(): Flowable<List<LocalEntity>>
    fun getLocalEntitiesSimple(): List<LocalEntity>

}