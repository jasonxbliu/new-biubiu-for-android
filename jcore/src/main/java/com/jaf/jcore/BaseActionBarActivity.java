package com.jaf.jcore;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.stkj.support.actionbar.ActionBarActivity;

public abstract class BaseActionBarActivity extends ActionBarActivity implements
		Bindable<BaseActionBarActivity> {

	private ViewBinder<BaseActionBarActivity> mViewBinder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setupActionBar();
		setContentView(onLoadViewResource());
		mViewBinder = new ViewBinder<BaseActionBarActivity>(this);
		mViewBinder.initViews();
		mViewBinder.bindClicks();
		onViewDidLoad(savedInstanceState);
        Util.e("Current Activity : " + getClass().getName());
	}

	protected void setupActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
        Drawable d = new ColorDrawable(Color.parseColor("#54cbff"));
        actionBar.setBackgroundDrawable(d);
		
		int actionBarTitleId = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
		if (actionBarTitleId > 0) {
		    TextView title = (TextView) findViewById(actionBarTitleId);
		    if (title != null) {
		        title.setTextColor(Color.BLACK);
		    }
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onHomeUpButtonClick();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void onHomeUpButtonClick() {
		finish();
	}

	protected abstract int onLoadViewResource();

	protected abstract void onViewDidLoad(Bundle savedInstanceState);

	@Override
	public BaseActionBarActivity getClassOwner() {
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
