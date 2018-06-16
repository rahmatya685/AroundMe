package cloudgis.mihanblog.com.aroundme.inject.module;

import cloudgis.mihanblog.com.aroundme.task.LocationTrackingService;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ServiceBuilderModule {

    @ContributesAndroidInjector
    abstract LocationTrackingService contributeLocationTrackingService();
}
