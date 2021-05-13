package com.liorkerenn.user.myapplication.viewmodel;


import android.content.Intent;
import android.view.View;

import com.liorkerenn.user.myapplication.App;
import com.liorkerenn.user.myapplication.activity.BordersActivity;
import com.liorkerenn.user.myapplication.injection.DataApi;
import com.liorkerenn.user.myapplication.injection.component.DaggerViewmodelComponent;
import com.liorkerenn.user.myapplication.injection.component.ViewmodelComponent;
import com.liorkerenn.user.myapplication.model.Country;
import com.liorkerenn.user.myapplication.rx.RxDataPass;

import javax.inject.Inject;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

public class CountryItemViewmodel extends ViewModel {
    private ViewmodelComponent mViewmodelComponent;
    private Country mModel;
    //Global variables for further use

    public ObservableField<Country> mModelObservable = new ObservableField<>();
    @Inject RxDataPass rxDataPass;
    @Inject DataApi dataApi;



    public CountryItemViewmodel() {
        getmViewmodelComponent().inject(this);
    }

    //DI
    private ViewmodelComponent getmViewmodelComponent() {
        if (mViewmodelComponent == null) {
            mViewmodelComponent = DaggerViewmodelComponent.builder()
                    .applicationComponent(App.get(App.getInstance()).getComponent())
                    .build();
        }
        return mViewmodelComponent;
    }

    /**
     * Set viewmodel data by Country mModel
     *
     * @param model The Country mModel for setting viewmodel data
     * */
    public void setModel(Country model) {
        mModel = model;
        mModelObservable.set(mModel);
    }

    /**
    * Send on click to Country viewmodel that holds the list
    * @param view to start activity with context
    * */
    public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), BordersActivity.class);
        view.getContext().startActivity(intent);

        rxDataPass.getListItemClickSubject().onNext(mModel);
    }

    public Country getModel() {
        return mModel;
    }
}
