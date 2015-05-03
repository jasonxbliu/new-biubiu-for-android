package com.jaf.jcore;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;

public class TextSpanUtil {
	
	public static SpannableStringBuilder color(String text, int r, int g, int b) {
		SpannableStringBuilder builder = new SpannableStringBuilder(text);
		int color = Color.rgb(r, g, b);
		ForegroundColorSpan colorSpan = new ForegroundColorSpan(color);
		builder.setSpan(colorSpan, 0, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		return builder;
	}
	
	public static SpannableStringBuilder color(String text, Context context, int colorRes) {
		SpannableStringBuilder builder = new SpannableStringBuilder(text);
		int color = context.getResources().getColor(colorRes);
		ForegroundColorSpan colorSpan = new ForegroundColorSpan(color);
		builder.setSpan(colorSpan, 0, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		return builder;
	}
	
}
