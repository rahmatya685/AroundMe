package cloudgis.mihanblog.com.aroundme.dataSource.local

import android.location.Location
import cloudgis.mihanblog.com.aroundme.dataSource.local.dao.LocalEntityDao
import cloudgis.mihanblog.com.aroundme.model.LocalEntity
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EntityRepositoryImpl @Inject constructor(localEntityDao: LocalEntityDao) : EntityRepository {



    var localEntityDao: LocalEntityDao = localEntityDao
    override fun insert(localEntities: List<LocalEntity>) {
        localEntityDao.insert(obj = *localEntities.toTypedArray())
    }

    override fun getLocalEntities( ): Flowable<List<LocalEntity>> {
        return localEntityDao.getLocalEntities()
    }

    override fun deleteAllLocalEntity(ids: List<Int>) {
        localEntityDao.deleteLocalEntities(ids)
    }

    override fun getLocalEntitiesSimple(): List<LocalEntity> {
        return localEntityDao.getLocalEntitiesSimple()
    }

}