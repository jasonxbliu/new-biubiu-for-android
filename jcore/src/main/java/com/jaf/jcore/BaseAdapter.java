package com.jaf.jcore;

import java.util.ArrayList;
import java.util.Arrays;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author jarrah
 * 
 * @param <T>
 *            数据类型
 * @param <V>
 *            itemView 类型
 */
public abstract class BaseAdapter<T, V extends View> extends
		android.widget.BaseAdapter {

	private Context mContext;

	protected ArrayList<T> mDataSource;

	protected LayoutInflater mInflater;

	public BaseAdapter(Context context) {
		init(context);
	}

	private void init(Context context) {
		mDataSource = new ArrayList<T>();
		mContext = context;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public Context getContext() {
		return mContext;
	}

	@Override
	public int getCount() {
		return mDataSource == null ? 0 : mDataSource.size();
	}

	@Override
	public T getItem(int position) {
		return mDataSource.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public int getPosition(T item) {
		return mDataSource.indexOf(item);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return makeView(position, convertView, parent);
	}

	@SuppressWarnings("unchecked")
	protected V makeView(int position, View convertView, ViewGroup parent) {
		V itemView;
		if (convertView == null) {
			itemView = newView(mInflater, position, convertView, parent);
		} else {
			itemView = (V) convertView;
		}
		onDispatchData(position, getItem(position), itemView);
		return itemView;
	}

	/**
	 * 分发数据
	 * 
	 * @param position
	 * @param data
	 * @param itemView
	 */
	public abstract void onDispatchData(int position, T data, V itemView);

	/**
	 * instantiate an item view
	 * 
	 * @param inflater
	 * @param position
	 * @param convertView
	 * @param parent
	 * @return
	 */
	public abstract V newView(LayoutInflater inflater, int position,
			View convertView, ViewGroup parent);

	public void setData(T... array) {
		mDataSource.addAll(Arrays.asList(array));
		notifyDataSetChanged();
	}

	public void setData(ArrayList<T> list) {
		mDataSource = list;
		notifyDataSetChanged();
	}

	public void removeItemAt(int... positions) {
		for (int index : positions) {
			mDataSource.remove(index);
		}
		notifyDataSetChanged();
	}
	
	public void add(T item) {
		mDataSource.add(item);
		notifyDataSetChanged();
	}
	
	public void insertItemInto(int position, T item) {
		mDataSource.add(position, item);
		notifyDataSetChanged();
	}

	public void append(ArrayList<T> append) {
		mDataSource.addAll(append);
		notifyDataSetChanged();
	}
}
