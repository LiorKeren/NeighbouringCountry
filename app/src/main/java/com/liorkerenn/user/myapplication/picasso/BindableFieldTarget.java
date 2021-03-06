package com.liorkerenn.user.myapplication.picasso;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import androidx.databinding.ObservableField;

/**
 * Class that helps load drawables to Picasso library
 * */
public class BindableFieldTarget implements Target {

    private ObservableField<Drawable> observableField;
    private Resources resources;

    public BindableFieldTarget(ObservableField<Drawable> observableField, Resources resources) {
        this.observableField = observableField;
        this.resources = resources;
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        observableField.set(new BitmapDrawable(resources, bitmap));
    }

    @Override
    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
        observableField.set(errorDrawable);

    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {
        observableField.set(placeHolderDrawable);
    }
}
