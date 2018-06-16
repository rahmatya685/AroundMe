package ir.mihanblog.cloudgis.aroundme.inject.module;

import android.support.v4.app.Fragment;


import ir.mihanblog.cloudgis.aroundme.view.VenueListFragment;
import ir.mihanblog.cloudgis.aroundme.view.VenueMapFragment;
import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjector;
import dagger.android.ContributesAndroidInjector;
import dagger.multibindings.IntoMap;

/**
 * Created by rahmati on 2/15/2018.
 */
@Module
public abstract class FragmentBuilder {


    @ContributesAndroidInjector(modules = VenueModule.class)
    abstract VenueListFragment bindVenueListFragment();



    @ContributesAndroidInjector(modules = VenueModule.class)
    abstract VenueMapFragment bindVenueMapFragment();





}
