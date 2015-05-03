package com.jaf.jcore;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

public abstract class BindableView extends FrameLayout implements
		Bindable<BindableView> {

	protected LayoutInflater mLayoutInflater;
	
	protected View mContentView;
	
	private ViewBinder<BindableView> mViewBinder;

	public BindableView(Context context) {
		super(context);
		init();
	}

	public BindableView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public BindableView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		mLayoutInflater = (LayoutInflater) getContext().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		setContentView(onLoadViewResource());
		mViewBinder = new ViewBinder<BindableView>(this);
		mViewBinder.initViews();
		mViewBinder.bindClicks();
		onViewDidLoad();
	}

	public abstract void onViewDidLoad();
	public abstract int onLoadViewResource();

	public void setContentView(int layoutId) {
		if (getChildCount() > 0)
			removeAllViews();
		mContentView = mLayoutInflater.inflate(layoutId, this, true);
	}

	@Override
	public BindableView getClassOwner() {
		return this;
	}

	@Override
	public View id(int id) {
		return mContentView.findViewById(id);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends View> T id(int id, Class<T> clz) {
		return (T) id(id);
	}
	
	public void bindClickEvent(View v, String methodName) {
		mViewBinder.bindClickEvent(v, methodName);
	}
}
