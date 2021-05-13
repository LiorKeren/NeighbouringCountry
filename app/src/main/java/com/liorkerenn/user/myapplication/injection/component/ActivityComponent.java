package com.liorkerenn.user.myapplication.injection.component;

import com.liorkerenn.user.myapplication.activity.BordersActivity;
import com.liorkerenn.user.myapplication.activity.CountriesActivity;
import com.liorkerenn.user.myapplication.injection.annotations.PerActivity;
import com.liorkerenn.user.myapplication.injection.module.ActivityModule;

import dagger.Component;


@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(CountriesActivity countriesActivity);
    void inject(BordersActivity bordersActivity);
}
