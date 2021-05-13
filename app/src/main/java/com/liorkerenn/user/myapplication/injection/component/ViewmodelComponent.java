package com.liorkerenn.user.myapplication.injection.component;

import com.liorkerenn.user.myapplication.injection.annotations.PerActivity;
import com.liorkerenn.user.myapplication.injection.module.ViewmodelModule;
import com.liorkerenn.user.myapplication.viewmodel.CountriesActivityViewmodel;
import com.liorkerenn.user.myapplication.viewmodel.CountryItemViewmodel;
import com.liorkerenn.user.myapplication.viewmodel.BordersActivityViewmodel;
import com.liorkerenn.user.myapplication.viewmodel.BorderItemViewmodel;

import dagger.Component;


@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ViewmodelModule.class)
public interface ViewmodelComponent {
   void inject(CountriesActivityViewmodel countriesActivityViewmodel);
   void inject(CountryItemViewmodel countryItemViewmodel);
   void inject(BordersActivityViewmodel bordersActivityViewmodel);
   void inject(BorderItemViewmodel borderItemViewmodel);
}
