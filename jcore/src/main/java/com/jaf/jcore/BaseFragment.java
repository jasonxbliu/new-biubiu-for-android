package com.jaf.jcore;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {

	public BaseFragment() {
	} // default constructor

	protected View _rootView;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		if (_rootView == null)
			_rootView = inflater
					.inflate(onLoadViewResource(), container, false);
		else
			((ViewGroup) _rootView.getParent()).removeView(_rootView);
		return _rootView;
	}

	/**
	 * return layout id
	 * 
	 * @return
	 */
	protected abstract int onLoadViewResource();

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		onViewDidLoad(savedInstanceState);
	}

	/**
	 * do view retrieve
	 * 
	 * @param savedInstanceState
	 */
	protected abstract void onViewDidLoad(Bundle savedInstanceState);

}
