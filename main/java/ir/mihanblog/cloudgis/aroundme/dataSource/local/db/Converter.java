package ir.mihanblog.cloudgis.aroundme.dataSource.local.db;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;

import ir.mihanblog.cloudgis.aroundme.model.Venue;

public class Converter {

    @TypeConverter
    public String toString(Venue venue){
        Gson gson=new Gson();
        return gson.toJson(venue);
    }
    @TypeConverter
    public Venue toVenue(String s){
        Gson gson=new Gson();
        return gson.fromJson(s,Venue.class);
    }
}
