package ir.mihanblog.cloudgis.aroundme.inject.module;


import android.app.Application;
import android.arch.persistence.room.Room;

import javax.inject.Singleton;

import ir.mihanblog.cloudgis.aroundme.dataSource.local.EntityRepository;
import ir.mihanblog.cloudgis.aroundme.dataSource.local.EntityRepositoryImpl;
import ir.mihanblog.cloudgis.aroundme.dataSource.local.dao.LocalEntityDao;
import ir.mihanblog.cloudgis.aroundme.dataSource.local.db.Database;
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
