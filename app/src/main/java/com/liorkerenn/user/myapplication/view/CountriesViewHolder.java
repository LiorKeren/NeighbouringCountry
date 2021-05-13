package com.liorkerenn.user.myapplication.view;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.liorkerenn.user.myapplication.databinding.ItemViewCountryBinding;
/**
 * Class holder for recycle item view
 * */
public class CountriesViewHolder extends RecyclerView.ViewHolder {
    public ItemViewCountryBinding bindingView;
    private Context context;

    public CountriesViewHolder(@NonNull ItemViewCountryBinding itemView, Context context) {
        super(itemView.getRoot());
        this.context = context;
        bindingView = itemView;

        }

    public Context getContext() {
        return context;
    }
}
