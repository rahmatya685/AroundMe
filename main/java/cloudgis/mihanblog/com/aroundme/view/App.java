package cloudgis.mihanblog.com.aroundme.view;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.support.multidex.MultiDexApplication;

import javax.inject.Inject;

import cloudgis.mihanblog.com.aroundme.inject.component.ApplicationComponent;
import cloudgis.mihanblog.com.aroundme.inject.component.DaggerApplicationComponent;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.HasServiceInjector;

public class App extends MultiDexApplication implements HasActivityInjector,HasServiceInjector {
    public ApplicationComponent applicationComponent;
    @Inject
    public DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;
    @Inject
    DispatchingAndroidInjector<Service> dispatchingServiceInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent.builder()
                .application(this).sharedPreferencesModule(new cloudgis.mihanblog.com.aroundme.inject.module.SharedPreferencesModule(this)).build();
        applicationComponent.inject(this);

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }

    @Override
    public AndroidInjector<Service> serviceInjector() {
        return dispatchingServiceInjector;
    }
}
