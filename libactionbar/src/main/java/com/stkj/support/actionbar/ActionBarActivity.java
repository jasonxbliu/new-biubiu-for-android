package com.stkj.support.actionbar;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.LayoutParams;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * @author jarrah no shadow style -> ActionBarNoShadow
 */
public class ActionBarActivity extends android.support.v7.app.ActionBarActivity {

	private static final boolean ACTIONBAR_NO_ICON = true;
	private static final int ACTIONBAR_ICON_RES = R.drawable.ic_home;
	private static final int ACTIONBAR_VIEW_RES = R.layout.view_action_bar;
	private static final int ACTIONBAR_ICON = ACTIONBAR_NO_ICON ? R.drawable.ic_trans
			: ACTIONBAR_ICON_RES;
	private static final int ACTIONBAR_BG = R.drawable.bg_top_bar;
	
	private static final boolean IS_TITLE_AS_UP = true; //默认开启点击标题 finish();
	protected View mActionBarView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mActionBarView = getActionBarView();
		setupActionBar(mActionBarView);
	}

	@SuppressLint("InflateParams")
	protected View getActionBarView() {
		return LayoutInflater.from(this).inflate(ACTIONBAR_VIEW_RES, null);
	}

	@SuppressLint("InflateParams")
	protected void setupActionBar(View actionBarView) {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setIcon(ACTIONBAR_ICON);

		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		actionBar.setCustomView(actionBarView, lp);

		actionBar.setDisplayShowCustomEnabled(true);

		Drawable d = getResources().getDrawable(ACTIONBAR_BG);
		actionBar.setBackgroundDrawable(d);
		
		setBarTitle(getTitle());
		
		enableTitleDisplayHomeAsUp(IS_TITLE_AS_UP);
	}
	
	public void enableTitleDisplayHomeAsUp(boolean isEnable) {
		if (isEnable && mActionBarView != null) {
			mActionBarView.findViewById(R.id.barTitle).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					onTitleClick(v);
				}
			});
		}else{
            mActionBarView.findViewById(R.id.barTitle).setOnClickListener(null);
        }
	}
	
	
	protected void onTitleClick(View v) {
		finish();
	}

	@Override
	public void setTitle(CharSequence title) {
		setBarTitle(title);
	}
	
	public void setBarTitle(CharSequence title) {
		TextView barTitle = (TextView) mActionBarView.findViewById(R.id.barTitle);
		barTitle.setText(title);
	}
	
//	设置原始actionbar 的title 颜色
//	public void setTitleColor(int color) {
//		int actionBarTitleId = Resources.getSystem().getIdentifier(
//				"action_bar_title", "id", "android");
//		if (actionBarTitleId > 0) {
//			TextView title = (TextView) findViewById(actionBarTitleId);
//			if (title != null) {
//				title.setTextColor(getResources().getColor(color));
//			}
//		}
//	}

}
