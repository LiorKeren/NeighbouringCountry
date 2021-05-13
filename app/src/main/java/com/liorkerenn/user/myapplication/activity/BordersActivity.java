package com.liorkerenn.user.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;

import com.liorkerenn.user.myapplication.App;
import com.liorkerenn.user.myapplication.R;
import com.liorkerenn.user.myapplication.adapter.RecycleViewAdapterBorders;
import com.liorkerenn.user.myapplication.databinding.ActivityBordersBinding;
import com.liorkerenn.user.myapplication.injection.component.ActivityComponent;
import com.liorkerenn.user.myapplication.injection.component.DaggerActivityComponent;
import com.liorkerenn.user.myapplication.injection.module.ActivityModule;
import com.liorkerenn.user.myapplication.viewmodel.BordersActivityViewmodel;

import java.util.Objects;

public class BordersActivity extends AppCompatActivity implements RecycleViewAdapterBorders.HistoryRecycleAdapterListener{

        private ActivityComponent mActivityComponent;
        private RecycleViewAdapterBorders mRecycleViewAdapter;
        private RecyclerView mRecyclerView;
        private ActivityBordersBinding mBinding;
        private BordersActivityViewmodel mViewmodel;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //DI
            getActivityComponent().inject(this);

            // Obtain the ViewModel component.
            mViewmodel = ViewModelProviders.of(this)
                    .get(BordersActivityViewmodel.class);

            // Inflate view and obtain an instance of the Binding class.
            mBinding = DataBindingUtil.setContentView(this, R.layout.activity_borders);

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

            mRecycleViewAdapter = new RecycleViewAdapterBorders(mViewmodel.getRateList());
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
}

