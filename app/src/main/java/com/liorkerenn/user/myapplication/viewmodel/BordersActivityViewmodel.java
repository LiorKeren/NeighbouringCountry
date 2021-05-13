package com.liorkerenn.user.myapplication.viewmodel;

import android.annotation.SuppressLint;
import android.os.Build;
import android.util.Log;

import com.liorkerenn.user.myapplication.App;
import com.liorkerenn.user.myapplication.adapter.RecycleViewAdapterBorders;
import com.liorkerenn.user.myapplication.injection.DataApi;
import com.liorkerenn.user.myapplication.injection.component.DaggerViewmodelComponent;
import com.liorkerenn.user.myapplication.injection.component.ViewmodelComponent;
import com.liorkerenn.user.myapplication.model.Country;
import com.liorkerenn.user.myapplication.rx.RxDataPass;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.ViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class BordersActivityViewmodel extends ViewModel implements LifecycleObserver {
    private ViewmodelComponent mViewmodelComponent;
    private RecycleViewAdapterBorders.HistoryRecycleAdapterListener mListener;
    //base value start with 1
    private final CompositeDisposable mDisposables = new CompositeDisposable();
    @Inject
    DataApi dataApi;
    @Inject
    RxDataPass rxDataPass;

    private List<Country> mResponseList = new ArrayList<>();

    /**
     * Set recycle view mListener to notify the activity when data set changed.
     *
     * @param recycleAdapterListener The activity recycle view mListener.
     */
    public void setListener(RecycleViewAdapterBorders.HistoryRecycleAdapterListener recycleAdapterListener) {
        mListener = recycleAdapterListener;
    }

    /**
     * viewmodel constructor.
     * <p>
     * Inside the constructor we subscribe the OnItemClick to method :onListItemClicked
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("CheckResult")
    public BordersActivityViewmodel() {
        getmViewmodelComponent().inject(this);
        loadViewmodelDisposables();
    }


    /**
     * Called when item clicked.
     *
     * @param country The item in List
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void onListItemClicked(Country country) {
        //Prepare the string for correct api call on the next screen
        String s = String.join(";", country.borders);
        StringBuilder borders = new StringBuilder(s);
        borders.deleteCharAt(s.length()-1);
        borders.deleteCharAt(0);

        mDisposables.add(dataApi.getAPIService()
                        .getCountriesByISO(s)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::loadCurrenciesSuccess, this::loadCurrenciesFailed)
        );
    }

    /**
     * Method called after failed api call that prints a Log
     */
    private void loadCurrenciesFailed(Throwable throwable) {
        if (throwable != null && throwable.getMessage() != null) {
            Log.w("API error ", throwable.getMessage());
        }
    }

    /**
     * Method after success api loading.
     * We take the response And extract A Country List from it by iterating Response Class field.
     *
     * @param countriesResponse A class API Response
     */
    private synchronized void loadCurrenciesSuccess(List<Country> countriesResponse) {

        mResponseList.clear();
        mResponseList.addAll(countriesResponse);
        //Notify Listener
        mListener.notifyDataChange();
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
     * Clear all disposables(unsubscribe All threads)
     */
    @Override
    protected void onCleared() {
        // Using clear will clear all, but can accept new disposable
        mDisposables.clear();
        // Using dispose will clear all and set isDisposed = true, so it will not accept any new disposable
        mDisposables.dispose();
        super.onCleared();
    }


    public List<Country> getRateList() {
        return mResponseList;
    }


    /**
     * Inside the method we subscribe the OnItemClick to method :onListItemClicked
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("CheckResult")
    public void loadViewmodelDisposables() {

        mDisposables.add(rxDataPass.getListItemClickSubject().subscribe(this::onListItemClicked, throwable -> {
            if (throwable != null && throwable.getMessage() != null) {
                Log.w("error OnItemClick", throwable.getMessage());
            }
        }));
    }
}
