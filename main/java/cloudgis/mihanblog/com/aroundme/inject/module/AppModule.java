package cloudgis.mihanblog.com.aroundme.inject.module;


import android.app.Application;
import android.arch.persistence.room.Room;

import javax.inject.Singleton;

import cloudgis.mihanblog.com.aroundme.dataSource.local.EntityRepository;
import cloudgis.mihanblog.com.aroundme.dataSource.local.EntityRepositoryImpl;
import cloudgis.mihanblog.com.aroundme.dataSource.local.dao.LocalEntityDao;
import cloudgis.mihanblog.com.aroundme.dataSource.local.db.Database;
import dagger.Module;
import dagger.Provides;


@Module
public class AppModule {


    @Provides
    @Singleton
    public Database providesAppDatabase(Application application) {
        return Room.databaseBuilder(application, Database.class, Database.DB_NAME).allowMainThreadQueries().build();
    }


    @Provides
    @Singleton
    public LocalEntityDao providesTbClassDao(Database db) {
        return db.localEntityDao();
    }

    @Provides
    @Singleton
    public EntityRepository providesTbClassSource(LocalEntityDao localEntityDao) {
        return new EntityRepositoryImpl(localEntityDao);
    }


}
