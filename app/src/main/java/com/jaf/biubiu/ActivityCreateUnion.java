package com.jaf.biubiu;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.baidu.location.BDLocation;
import com.jaf.jcore.BaseActionBarActivity;
import com.jaf.jcore.BindView;
import com.jaf.jcore.Http;
import com.jaf.jcore.HttpCallBack;
import com.jarrah.photo.FileUtil;
import com.jarrah.photo.ImageUtil;
import com.jarrah.photo.PhotoPicker;
import com.jarrah.photo.PopupUtil;
import com.jarrah.photo.ReqeustCode;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ActivityCreateUnion extends BaseActionBarActivity
		implements
			ReqeustCode {

	@BindView(id = R.id.capture, onClick = "onCaptureClick")
	private ImageView mCapture;

	@BindView(id = R.id.unionEdit)
	private EditText mEditText;

	@BindView(id = R.id.locDesc)
	private TextView mLocDesc;

	@BindView(id = R.id.btnCreate, onClick = "onSubmitClick")
	private Button mSubmit;

	private File mImageFile;
    private ProgressDialog mProgressDialog;

    @Override
	protected int onLoadViewResource() {
		return R.layout.activity_create_union;
	}

	@Override
	protected void onViewDidLoad(Bundle savedInstanceState) {
		LocationManager.getInstance().requestLocation(
                new LocationManager.JLsn() {
                    @Override
                    public void onResult(double latitude, double longitude,
                                         BDLocation location) {
                        super.onResult(latitude, longitude, location);
                        mLocDesc.setText(location.getAddrStr());
                    }
                });
	}

    private void showProgressDialog() {
        mProgressDialog = ProgressDialog.show(this, getString(R.string.titleWait),
                getString(R.string.submittingData), true);
        mProgressDialog.setCancelable(true);
    }

    public static void start(Activity activity) {
		activity.startActivity(new Intent(activity, ActivityCreateUnion.class));
	}

	public void onCaptureClick(View v) {
		popup(this);
	}

	private Dialog dialog;

	protected File captureFile;

	@SuppressLint("InflateParams")
	protected void popup(Context context) {

		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View holder = inflater.inflate(
				com.jarrah.photo.R.layout.view_popup_button, null, false);
		View gallery = holder.findViewById(com.jarrah.photo.R.id.btnPhoto);
		View capture = holder.findViewById(com.jarrah.photo.R.id.btnCapture);
		View cancel = holder.findViewById(com.jarrah.photo.R.id.btnCanel);

		ButtonClick click = new ButtonClick(this);
		gallery.setOnClickListener(click);
		capture.setOnClickListener(click);
		cancel.setOnClickListener(click);

		dialog = PopupUtil.makePopup(context, holder);
		dialog.show();
	}

	public class ButtonClick implements View.OnClickListener {

		private Activity activity;

		public ButtonClick(Activity activity) {
			this.activity = activity;
		}

		@Override
		public void onClick(View v) {

			if (dialog != null) {
				dialog.dismiss();
			}

			if (v.getId() == com.jarrah.photo.R.id.btnPhoto) {
				PhotoPicker.launchGallery(activity, FROM_GALLERY);
			}

			if (v.getId() == com.jarrah.photo.R.id.btnCapture) {
				captureFile = FileUtil.getCaptureFile(activity);
				PhotoPicker.launchCamera(activity, FROM_CAPTURE, captureFile);
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {

			if (requestCode == FROM_GALLERY) {
				if (data != null) {
					String path = PhotoPicker
							.getPhotoPathByLocalUri(this, data);
					onGalleryComplete(path);
				}
			} else if (requestCode == FROM_CAPTURE) {

				onCaptureComplete(captureFile);

			} else if (requestCode == FROM_CROP) {
				if (data != null) {
					Bitmap bitmap = data.getParcelableExtra("data");
					onCropComplete(bitmap);
				}
			}

		}
	}

	protected void onGalleryComplete(String path) {
		PhotoPicker.startCrop(this, path, FROM_CROP, false);
	}

	protected void onCropComplete(Bitmap bitmap) {
		bitmap = ImageUtil.circleImage(bitmap);
		mCapture.setImageBitmap(bitmap);

		try {
			mImageFile = saveBitmap(bitmap, this);
		} catch (IOException e) {
			e.printStackTrace();
			L.dbg("save bitmap fail");
		}

	}

	protected void onCaptureComplete(File captureFile) {
		PhotoPicker.startCrop(this, captureFile.getAbsolutePath(), FROM_CROP,
				false);
	}

	public void onSubmitClick(View v) {
		L.dbg("onSubmit");
		String unionName = mEditText.getText().toString();
		if (TextUtils.isEmpty(unionName)) {
			Toast.makeText(this, R.string.saySomething, Toast.LENGTH_SHORT)
					.show();
		} else {
			asynMultipart(unionName);
		}
	}

	private void asynMultipart(final String unionName) {
        showProgressDialog();
		if (mImageFile == null) {
			L.dbg("image file not found");
            mProgressDialog.dismiss();
            Toast.makeText(this, R.string.uploadImagePlease, Toast.LENGTH_SHORT).show();
			return;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("file", mImageFile);
		params.put("name", mImageFile.getName());

		AQuery aq = new AQuery(getApplicationContext());
		aq.ajax(Constant.MULTIPART, params, String.class,
				new AjaxCallback<String>() {
					@Override
					public void callback(String url, String object,
							AjaxStatus status) {
						super.callback(url, object, status);
						if (status.getCode() == 200) {
							postUnionInfo(object, unionName);
						} else {
                            mProgressDialog.dismiss();
							Toast.makeText(getApplicationContext(),
									R.string.network_err, Toast.LENGTH_SHORT)
									.show();
						}
					}
				});
	}

	private void postUnionInfo(String path, String unionName) {
		Http http = new Http();
		String loc = mLocDesc.getText().toString();
		http.url(Constant.API).JSON(U.postCreateUnion(unionName, loc, path))
				.post(new HttpCallBack() {
                    @Override
                    public void onResponse(JSONObject response) {
                        super.onResponse(response);
                        L.dbg("create union success");
                        mProgressDialog.dismiss();
                        new DialogSubmit(ActivityCreateUnion.this).show();
                    }
                });
	}

	private File saveBitmap(Bitmap b, Context context) throws IOException {

		File f = new File(context.getCacheDir(), "union_image");
		f.createNewFile();

		// Convert bitmap to byte array
		Bitmap bitmap = b;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 0 /* ignored for PNG */, bos);
		byte[] bitmapdata = bos.toByteArray();

		// write the bytes in file
		FileOutputStream fos = new FileOutputStream(f);
		fos.write(bitmapdata);
		fos.flush();
		fos.close();
		return f;
	}
}
