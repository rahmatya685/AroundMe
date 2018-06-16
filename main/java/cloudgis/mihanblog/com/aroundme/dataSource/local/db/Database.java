package cloudgis.mihanblog.com.aroundme.dataSource.local.db;

import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import cloudgis.mihanblog.com.aroundme.dataSource.local.dao.LocalEntityDao;
import cloudgis.mihanblog.com.aroundme.model.LocalEntity;

import static cloudgis.mihanblog.com.aroundme.dataSource.local.db.Database.EXISTING_VERSION;
 @android.arch.persistence.room.Database(entities = LocalEntity.class,version = EXISTING_VERSION, exportSchema = false)
  @TypeConverters(Converter.class)
 public abstract class Database extends RoomDatabase{
    public static String DB_NAME = "Db";
    public static final int EXISTING_VERSION = 1;

    public abstract LocalEntityDao localEntityDao();


}
