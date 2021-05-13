package com.liorkerenn.user.myapplication.viewmodel;

import android.annotation.SuppressLint;
import android.os.Build;
import android.util.Log;
import android.view.View;

import com.liorkerenn.user.myapplication.App;
import com.liorkerenn.user.myapplication.R;
import com.liorkerenn.user.myapplication.adapter.RecycleViewAdapter;
import com.liorkerenn.user.myapplication.injection.DataApi;
import com.liorkerenn.user.myapplication.injection.component.DaggerViewmodelComponent;
import com.liorkerenn.user.myapplication.injection.component.ViewmodelComponent;
import com.liorkerenn.user.myapplication.model.Country;
import com.liorkerenn.user.myapplication.rx.RxDataPass;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.ViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


public class CountriesActivityViewmodel extends ViewModel implements LifecycleObserver {
    private ViewmodelComponent mViewmodelComponent;
    private RecycleViewAdapter.RecycleAdapterListener mListener;
    private final CompositeDisposable mDisposables = new CompositeDisposable();
    @Inject DataApi dataApi;
    @Inject RxDataPass rxDataPass;


    private List<Country> mResponseList = new ArrayList<>();

    /**
     * Set recycle view mListener to notify the activity when data set changed.
     * @param recycleAdapterListener The activity recycle view mListener.
     * */
    public void setListener(RecycleViewAdapter.RecycleAdapterListener recycleAdapterListener){
        mListener = recycleAdapterListener;
    }

    /**
     * viewmodel constructor.
     * */
    @SuppressLint("CheckResult")
    public CountriesActivityViewmodel() {
        getmViewmodelComponent().inject(this);
        loadViewmodelDisposables();
    }

    /**
     * Method called after failed api call that prints a Log
     * */
    private void loadCountriesFailed(Throwable throwable) {
        if (throwable != null  && throwable.getMessage() != null) {
            Log.w("API error ", throwable.getMessage());
        }
    }

    /**
     * Method after success api loading.
     * @param countriesResponse A class API Response
     * */
    @RequiresApi(api = Build.VERSION_CODES.O)
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
     * */
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
     * Lunch an API call.
     * Load data from api with observable and add him to disposables.
     * */
    @SuppressLint("CheckResult")
    public void loadViewmodelDisposables(){

        //  Get all countries from api
          mDisposables.add(dataApi.getAPIService()
                        .getAllCountries()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::loadCurrenciesSuccess, this::loadCountriesFailed)
        );
    }

    /**
     * On click to sort list depends on button ID
     * @param view For get id
     * */

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onSortClick(View view) {

        switch (view.getId()){
            case R.id.sortByNameAToZ:
                //Sort list by alphabetically order
                mResponseList.sort(Comparator.comparing(Country::getName));
                break;

            case R.id.sortByNameZToA:
                //Sort list by reverse alphabetically order(From Z to A)
                mResponseList.sort(Comparator.comparing(Country::getName)
                        .reversed());
                break;

            case R.id.sortByAreaLargeToSmall:
                //Sort list by area size
                mResponseList.sort(Comparator.comparingDouble(Country::getArea));
                break;

            case R.id.sortByAreaSmalTolLarge:
                //Sort list  by reverse area size (From smallest area country to the largest one)
                mResponseList.sort(Comparator.comparingDouble(Country::getArea)
                        .reversed());
                break;
        }

        //Notify Listener
        mListener.notifyDataChange();
    }

}
