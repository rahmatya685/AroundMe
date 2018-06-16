package cloudgis.mihanblog.com.aroundme.inject.module;


import cloudgis.mihanblog.com.aroundme.view.MainActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by rahmati on 2/15/2018.
 */
@Module
public abstract  class ActivityBuilder {

//    @Binds
//    @IntoMap
//    @ActivityKey(MainActivity.class)
//    abstract AndroidInjector.Factory<? extends Activity> bindMainActivity(MainActivityComponent.Builder builder);



    @ContributesAndroidInjector(modules ={ MainActivityModule.class,FragmentBuilder.class })
    abstract MainActivity bindMainActivity();
}
