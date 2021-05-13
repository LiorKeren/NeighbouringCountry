package com.liorkerenn.user.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.liorkerenn.user.myapplication.R;
import com.liorkerenn.user.myapplication.databinding.ItemViewCountryBinding;
import com.liorkerenn.user.myapplication.model.Country;
import com.liorkerenn.user.myapplication.view.CountriesViewHolder;
import com.liorkerenn.user.myapplication.viewmodel.CountryItemViewmodel;

import java.util.List;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

/**
 * An adapter class for a List(Recycle) view
 * */
public class RecycleViewAdapter extends RecyclerView.Adapter<CountriesViewHolder> {
    private List<Country> mCountriesList;
    private LayoutInflater mLayoutInflater;
    private float mPrevAlpha;

    @Override
    public CountriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemViewCountryBinding binding =
                DataBindingUtil.inflate(mLayoutInflater, R.layout.item_view_country, parent, false);

        CountriesViewHolder countriesViewHolder = new CountriesViewHolder(binding, parent.getContext());
        mPrevAlpha = countriesViewHolder.itemView.getAlpha();

        return countriesViewHolder;
    }

    @Override
    public void onBindViewHolder(CountriesViewHolder holder, final int position) {
        holder.itemView.setAlpha(mPrevAlpha);

        CountryItemViewmodel listItemViewmodel = new CountryItemViewmodel();

        //Set the viewmodel by sending the updated model
        listItemViewmodel.setModel(mCountriesList.get(position));

        holder.bindingView.setViewmodel(listItemViewmodel);

    }

    @Override
    public int getItemCount() {
        return mCountriesList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public RecycleViewAdapter(List<Country> countries) {
        this.mCountriesList = countries;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //Listener to update data between recycle view and the viewmodel
    public interface RecycleAdapterListener {
        void notifyDataChange();
    }
}
