package ir.mihanblog.cloudgis.aroundme.inject.module;

import ir.mihanblog.cloudgis.aroundme.presenter.VenueContract;
import ir.mihanblog.cloudgis.aroundme.presenter.VenuePresenter;
import dagger.Binds;
import dagger.Module;

@Module
public abstract class VenueModule {

    @Binds
    abstract VenueContract.Presenter provideVenuePresenter(VenuePresenter venuePresenter );


}
