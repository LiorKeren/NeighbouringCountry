package com.liorkerenn.user.myapplication.injection.component;

import android.content.Context;

import com.liorkerenn.user.myapplication.App;
import com.liorkerenn.user.myapplication.injection.DataApi;
import com.liorkerenn.user.myapplication.injection.annotations.ApplicationContext;
import com.liorkerenn.user.myapplication.injection.module.ApplicationModule;
import com.liorkerenn.user.myapplication.rx.RxDataPass;

import javax.inject.Singleton;

import dagger.Component;



@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(App app);

    @ApplicationContext
    Context getContext();

    DataApi getDataApi();

    RxDataPass getRxDataPass();
}
