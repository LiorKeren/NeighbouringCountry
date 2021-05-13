package com.liorkerenn.user.myapplication.activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.liorkerenn.user.myapplication.App;
import com.liorkerenn.user.myapplication.R;
import com.liorkerenn.user.myapplication.adapter.RecycleViewAdapter;
import com.liorkerenn.user.myapplication.databinding.ActivityCountriesBinding;
import com.liorkerenn.user.myapplication.injection.component.ActivityComponent;
import com.liorkerenn.user.myapplication.injection.component.DaggerActivityComponent;
import com.liorkerenn.user.myapplication.injection.module.ActivityModule;
import com.liorkerenn.user.myapplication.rx.RxDataPass;
import com.liorkerenn.user.myapplication.viewmodel.CountriesActivityViewmodel;

import java.util.Objects;

import javax.inject.Inject;


public class CountriesActivity extends AppCompatActivity implements RecycleViewAdapter.RecycleAdapterListener{

    private ActivityComponent mActivityComponent;
    private RecycleViewAdapter mRecycleViewAdapter;
    private RecyclerView mRecyclerView;
    private ActivityCountriesBinding mBinding;
    private CountriesActivityViewmodel mViewmodel;
    private final CompositeDisposable mDisposables = new CompositeDisposable();

    @Inject
    RxDataPass rxDataPass;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //DI
        getActivityComponent().inject(this);

        // Obtain the ViewModel component.
        mViewmodel = ViewModelProviders.of(this)
                .get(CountriesActivityViewmodel.class);


        mDisposables.add(rxDataPass.getStartActivitySubject()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(r -> {
            Intent intent = new Intent(this, r);
            startActivity(intent);
                }, throwable -> {
                    if (throwable != null && throwable.getMessage() != null) {
                        Log.w("error OnItemClick", throwable.getMessage());
                    }
                }
        ));

        // Inflate view and obtain an instance of the Binding class.
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_countries);

        // Assign the component to a property in the Binding class.
        mBinding.setViewmodel(mViewmodel);

        mViewmodel.setListener(this);

        getLifecycle().addObserver(mViewmodel);
        mBinding.setLifecycleOwner(this);

        initRecyclerView();
    }

    /**
     * Activity component for Injectors
     * */
    public ActivityComponent getActivityComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .applicationComponent(App.get(this).getComponent())
                    .build();
        }
        return mActivityComponent;
    }

    /**
    *  Init Recycle Grid View
    * */
    private void initRecyclerView() {
        mRecyclerView = mBinding.recyclerView;
        mRecyclerView.setHasFixedSize(true);


        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        //add divider to recycle view
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                layoutManager.getOrientation());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dividerItemDecoration.setDrawable(Objects.requireNonNull(getDrawable(R.drawable.divider_background)));
        }
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        mRecycleViewAdapter = new RecycleViewAdapter(mViewmodel.getRateList());
        mRecycleViewAdapter.setHasStableIds(true);
        mRecyclerView.setAdapter(mRecycleViewAdapter);
    }

    /**
     * Notify recycle adapter from viewmodel listener
     * */
    @Override
    public void notifyDataChange() {
        mRecycleViewAdapter.notifyDataSetChanged();
    }


        @Override
    protected void onDestroy() {
        super.onDestroy();
            // Using clear will clear all, but can accept new disposable
            mDisposables.clear();
            // Using dispose will clear all and set isDisposed = true, so it will not accept any new disposable
            mDisposables.dispose();
    }
}
