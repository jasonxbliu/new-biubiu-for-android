package com.jaf.biubiu;

import android.content.Context;
import android.media.Image;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by jarrah on 2015/4/27.
 */
public class EmptyHelper {

    public static View getEmptyView(Context c, int d, int t) {
        LayoutInflater lf = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = lf.inflate(R.layout.empty_layout, null);
        ImageView iv = (ImageView) view.findViewById(R.id.emptyBg);
        TextView tv = (TextView) view.findViewById(R.id.emptyText);
        iv.setImageResource(d);
        tv.setText(t);
        return view;
    }
}
