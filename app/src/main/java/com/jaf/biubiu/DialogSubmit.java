package com.jaf.biubiu;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

/**
 * Created by jarrahwu on 15/4/26.
 */
public class DialogSubmit extends Dialog
		implements
			android.view.View.OnClickListener {

	public Button mBtnOk;
    private Activity activity;

	public DialogSubmit(Activity activity) {
		super(activity);
        this.activity = activity;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		setContentView(R.layout.dialog_submit);
        setCancelable(false);
		mBtnOk = (Button) findViewById(R.id.btnOK);
		mBtnOk.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btnOK :
				dismiss();
                activity.finish();
				break;
			default :
				break;
		}
		dismiss();
	}
}