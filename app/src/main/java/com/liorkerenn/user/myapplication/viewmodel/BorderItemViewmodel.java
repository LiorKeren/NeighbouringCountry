package com.liorkerenn.user.myapplication.viewmodel;

import android.graphics.drawable.Drawable;
import com.liorkerenn.user.myapplication.App;
import com.liorkerenn.user.myapplication.injection.DataApi;
import com.liorkerenn.user.myapplication.injection.component.DaggerViewmodelComponent;
import com.liorkerenn.user.myapplication.injection.component.ViewmodelComponent;
import com.liorkerenn.user.myapplication.model.Country;
import com.liorkerenn.user.myapplication.rx.RxDataPass;
import javax.inject.Inject;
import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

public class BorderItemViewmodel extends ViewModel {
    private ViewmodelComponent mViewmodelComponent;
    private Country mModel;
    public ObservableField<Drawable> posterImage = new ObservableField<>();
    public ObservableField<Country> mModelObservable = new ObservableField<>();
    @Inject
    RxDataPass rxDataPass;
    @Inject
    DataApi dataApi;



    public BorderItemViewmodel() {
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
     * Set viewmodel data by Rate mModel
     *
     * @param model The Country mModel for setting viewmodel data
     * */
    public void setModel(Country model) {
        mModel = model;
        mModelObservable.set(mModel);
    }


    public Country getModel() {
        return mModel;
    }

}
