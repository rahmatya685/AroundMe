package ir.mihanblog.cloudgis.aroundme.inject.component;

import android.app.Application;

import javax.inject.Singleton;

import ir.mihanblog.cloudgis.aroundme.inject.module.ActivityBuilder;
import ir.mihanblog.cloudgis.aroundme.inject.module.AppModule;
import ir.mihanblog.cloudgis.aroundme.inject.module.ServiceBuilderModule;
import ir.mihanblog.cloudgis.aroundme.inject.module.SharedPreferencesModule;
import ir.mihanblog.cloudgis.aroundme.view.App;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

//import cloudgis.mihanblog.com.aroundme.inject.module.AppModule;

/**
 * Created by rahmati on 2/9/2018.
 */
@Singleton
@Component(modules = {AndroidInjectionModule.class, AppModule.class, ServiceBuilderModule.class, SharedPreferencesModule.class,
        ActivityBuilder.class})
public interface ApplicationComponent {


    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        Builder sharedPreferencesModule(SharedPreferencesModule pmSharedPreferencesModule);


        Builder appModule(AppModule applicationModule);

        ApplicationComponent build();
    }


    void inject(App application);

}
