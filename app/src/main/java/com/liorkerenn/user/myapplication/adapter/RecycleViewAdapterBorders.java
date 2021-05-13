package com.liorkerenn.user.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.liorkerenn.user.myapplication.R;
import com.liorkerenn.user.myapplication.databinding.ItemViewBorderBinding;
import com.liorkerenn.user.myapplication.model.Country;
import com.liorkerenn.user.myapplication.view.BorderViewHolder;
import com.liorkerenn.user.myapplication.viewmodel.BorderItemViewmodel;
import java.util.List;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class RecycleViewAdapterBorders extends RecyclerView.Adapter<BorderViewHolder>{
    private List<Country> mCountriesList;
    private List<Country> mFilterCountriesList;
    private LayoutInflater mLayoutInflater;
    private float mPrevAlpha;

    @Override
    public BorderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemViewBorderBinding binding =
                DataBindingUtil.inflate(mLayoutInflater, R.layout.item_view_border, parent, false);

        BorderViewHolder borderViewHolder = new BorderViewHolder(binding, parent.getContext());
        mPrevAlpha = borderViewHolder.itemView.getAlpha();

        return borderViewHolder;
    }

    @Override
    public void onBindViewHolder(BorderViewHolder holder, final int position) {
        holder.itemView.setAlpha(mPrevAlpha);

        BorderItemViewmodel listItemViewmodel = new BorderItemViewmodel();

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

    public RecycleViewAdapterBorders(List<Country> rateList) {
        this.mCountriesList = rateList;
        mFilterCountriesList = rateList;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //Listener to update data between recycle view and the viewmodel
    public interface HistoryRecycleAdapterListener {
        void notifyDataChange();
    }
}

