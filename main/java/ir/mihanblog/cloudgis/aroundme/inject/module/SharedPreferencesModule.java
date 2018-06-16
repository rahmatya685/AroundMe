package ir.mihanblog.cloudgis.aroundme.inject.module;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class SharedPreferencesModule {
     private Context context;

    public SharedPreferencesModule(Application context) {
        this.context = context;
    }

    @Provides
    Context providesContext() {
        return context;
    }

    @Provides
    @Singleton
    android.content.SharedPreferences providesSharedPreferences(Context context) {
        return android.preference.PreferenceManager.getDefaultSharedPreferences(context);
    }
}
