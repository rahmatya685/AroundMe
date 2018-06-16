package cloudgis.mihanblog.com.aroundme.inject.module;

import cloudgis.mihanblog.com.aroundme.presenter.MainActivityContract;
import cloudgis.mihanblog.com.aroundme.presenter.MainActivityPresenter;
import dagger.Binds;
import dagger.Module;

@Module
public abstract class MainActivityModule {

    @Binds
    abstract MainActivityContract.Presenter provideMainActivity(MainActivityPresenter mainActivityPresenter );


}
