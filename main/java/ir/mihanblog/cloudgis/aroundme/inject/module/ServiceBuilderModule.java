package ir.mihanblog.cloudgis.aroundme.inject.module;

import ir.mihanblog.cloudgis.aroundme.task.LocationTrackingService;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ServiceBuilderModule {

    @ContributesAndroidInjector
    abstract LocationTrackingService contributeLocationTrackingService();
}
