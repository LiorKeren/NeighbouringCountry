package com.liorkerenn.user.myapplication.view;

import android.content.Context;

import com.liorkerenn.user.myapplication.databinding.ItemViewBorderBinding;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BorderViewHolder extends RecyclerView.ViewHolder {
    public ItemViewBorderBinding bindingView;
    private Context context;

    public BorderViewHolder(@NonNull ItemViewBorderBinding itemView, Context context) {
        super(itemView.getRoot());
        this.context = context;
        bindingView = itemView;

    }

    public Context getContext() {
        return context;
    }
}
