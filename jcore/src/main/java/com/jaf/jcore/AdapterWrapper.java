package com.jaf.jcore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jarrah on 2015/4/15.
 */
public abstract class AdapterWrapper<T, V extends View> extends ArrayAdapterCompat<T>{


    private static final int NO_RESOURCE = -2;

    public AdapterWrapper(Context context) {
        super(context, NO_RESOURCE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(getViewResource() == NO_RESOURCE) {
            V item = null;
            if (convertView == null) {
                item = newView(position, getLayoutInflater(), convertView, parent);
            } else {
                item = (V) convertView;
            }
            onBindView(position, getItem(position), item);
            return item;
        }else {
           return super.getView(position, convertView, parent);
        }
    }

    protected abstract void onBindView(int position, T item, V view);

    public abstract V newView(int position, LayoutInflater lf, View convertView, ViewGroup parent);
}
