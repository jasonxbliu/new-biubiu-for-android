package com.jaf.jcore;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

public abstract class BindableFragment extends BaseFragment implements
		Bindable<BindableFragment> {

	private ViewBinder<BindableFragment> mInjector;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        Util.e("Current fragment : " + getClass().getName());
		mInjector = new ViewBinder<BindableFragment>(this);
	}

	@Override
	protected void onViewDidLoad(Bundle savedInstanceState) {
		mInjector.initViews();
		mInjector.bindClicks();
	}

	@Override
	public BindableFragment getClassOwner() {
		return self();
	}

	public BindableFragment self() {
		return this;
	}

	@Override
	public View id(int id) {
		return _rootView.findViewById(id);
	}

	/**
	 * retrieve and cast type
	 * 
	 * @param id
	 * @param clz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends View> T id(int id, Class<T> clz) {
		return (T) id(id);
	}

	public void log(Object o) {
		if (Constant.Debug.DEBUG) {
			Log.e(this.getClass().getName(), o.toString());
		}
	}
}
