package cloudgis.mihanblog.com.aroundme.inject.module;

import cloudgis.mihanblog.com.aroundme.presenter.VenueContract;
import cloudgis.mihanblog.com.aroundme.presenter.VenuePresenter;
import dagger.Binds;
import dagger.Module;

@Module
public abstract class VenueModule {

    @Binds
    abstract VenueContract.Presenter provideVenuePresenter(VenuePresenter venuePresenter );


}
