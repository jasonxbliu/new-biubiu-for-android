package com.jaf.jcore;

import android.view.View;

public interface Bindable<I extends Object> {
	/**
	 * get the parent for retrieve views
	 * 
	 * @return
	 */
	I getClassOwner();

	/**
	 * the find view method
	 * 
	 * @param id
	 * @return
	 */
	View id(int id);
}
