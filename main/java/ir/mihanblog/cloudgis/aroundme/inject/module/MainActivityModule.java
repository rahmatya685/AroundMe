package ir.mihanblog.cloudgis.aroundme.inject.module;

import ir.mihanblog.cloudgis.aroundme.presenter.MainActivityContract;
import ir.mihanblog.cloudgis.aroundme.presenter.MainActivityPresenter;
import dagger.Binds;
import dagger.Module;

@Module
public abstract class MainActivityModule {

    @Binds
    abstract MainActivityContract.Presenter provideMainActivity(MainActivityPresenter mainActivityPresenter );


}
