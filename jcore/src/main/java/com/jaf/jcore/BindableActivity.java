package com.jaf.jcore;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

public abstract class BindableActivity extends FragmentActivity implements
		Bindable<BindableActivity> {

	private ViewBinder<BindableActivity> mViewBinder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(onLoadViewResource());

		mViewBinder = new ViewBinder<BindableActivity>(this);
		mViewBinder.initViews();
		mViewBinder.bindClicks();

		onViewDidLoad(savedInstanceState);
	}

	protected abstract int onLoadViewResource();

	protected abstract void onViewDidLoad(Bundle savedInstanceState);

	@Override
	public BindableActivity getClassOwner() {
		return this;
	}

	@Override
	public View id(int id) {
		return findViewById(id);
	}

	@SuppressWarnings("unchecked")
	public <T extends View> T id(int id, Class<T> clz) {
		return (T) id(id);
	}
}
