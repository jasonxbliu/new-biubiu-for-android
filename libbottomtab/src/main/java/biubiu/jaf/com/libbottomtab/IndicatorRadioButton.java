package biubiu.jaf.com.libbottomtab;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.RadioButton;

/**
 * Created by jarrah on 2015/4/27.
 */
public class IndicatorRadioButton extends RadioButton{

    public static final int SIZE = 30;
    public static final int PADDING = 50;
    private static final int PADDING_TOP = 0;
    private boolean isDrawRedPoint = false;
    private Paint mPaint;
    private RectF mRectF;

    public IndicatorRadioButton(Context context) {
        super(context);
        init();
    }

    public IndicatorRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public IndicatorRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.parseColor("#fc9185"));
        mRectF = new RectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (isDrawRedPoint) {
            int left = getMeasuredWidth()/2 + SIZE;
            int top = PADDING_TOP;
            mRectF.set(left, top, left + SIZE, top + SIZE);
            canvas.drawOval(mRectF, mPaint);
        }
        super.onDraw(canvas);
    }

    public void showIndicator(boolean show) {
        isDrawRedPoint = show;
        postInvalidate();
    }
}
