package com.jaf.biubiu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;
import com.jaf.bean.BeanUnionItem;
import com.jaf.jcore.Application;
import com.jaf.jcore.BindView;
import com.jaf.jcore.BindableView;

/**
 * Created by jarrah on 2015/4/17.
 */
public class ViewUnionItemView extends BindableView{

    @BindView(id = R.id.topicIcon)
    private ImageView mTopicIcon;

    @BindView(id = R.id.topicTitle)
    private TextView mTopicTitle;

    @BindView(id =R.id.distanceTag)
    private TextView mDistanceTag;

    @BindView(id = R.id.distance)
    private TextView mDistance;

    @BindView(id = R.id.topicCount)
    private TextView mTopicCount;

    public ViewUnionItemView(Context context) {
        super(context);
    }

    @Override
    public void onViewDidLoad() {
    }

    @Override
    public int onLoadViewResource() {
        return R.layout.view_topic_item;
    }

    public void setData(BeanUnionItem data) {
        if (data != null) {
            loadImage(mTopicIcon, data.getPicPath());
            boolean empty = TextUtils.isEmpty(data.getLocDesc());
            int bgId = empty ? R.drawable.shape_orange_corner_fill : R.drawable.shape_blue_corner_fill;
            int tagTextId = empty ? R.string.tagDistance : R.string.tagSchool;
            mDistanceTag.setBackgroundResource(bgId);
            mDistanceTag.setText(tagTextId);
            mTopicTitle.setText(data.getUnionName());
            String distance = data.getDistance() < 3 ? "<3" : String.valueOf(data.getDistance());

            String distanceText = empty ? getContext().getString(R.string.distance, distance) : data.getLocDesc();
            mDistance.setText(distanceText);

            mTopicCount.setText(getContext().getString(R.string.topicCount, data.getQuestionNum()));
        }
    }

    private void loadImage(ImageView iv, String path) {
        Application.getInstance().getAQuery().id(iv).image(path, false, true, 500, 0, new BitmapAjaxCallback() {
            @Override
            protected void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
                bm = processBitmap(bm, 0);
                super.callback(url, iv, bm, status);
            }
        });
    }

    public Bitmap processBitmap(Bitmap bitmap, int round) {
        int pixels = 0;
        if (round == 0)
            pixels = 400;
        else
            pixels = round;
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }
}
