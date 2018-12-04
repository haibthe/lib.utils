package com.hb.lib.utils.ui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;


public class ThemeUtils {

	private ThemeUtils() {}

	public static int getAccentColor(Context context) {
		int colorAttr;
	    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
	        colorAttr = android.R.attr.colorAccent;
	    } else { 
	        //Get colorAccent defined for AppCompat 
	        colorAttr = context.getResources().getIdentifier("colorAccent", "attr", context.getPackageName());
	    } 
	    TypedValue outValue = new TypedValue();
	    context.getTheme().resolveAttribute(colorAttr, outValue, true);
	    return outValue.data;
	}
	
	public static int getPrimaryColor(Context context) {
		int colorAttr;
	    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
	        colorAttr = android.R.attr.colorPrimary;
	    } else { 
	        //Get colorAccent defined for AppCompat 
	        colorAttr = context.getResources().getIdentifier("colorPrimary", "attr", context.getPackageName());
	    } 
	    TypedValue outValue = new TypedValue();
	    context.getTheme().resolveAttribute(colorAttr, outValue, true);
	    return outValue.data;
	}
	
	public static int dpToPx(Context context, int dp) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics()) + 0.5f);
    }

    public static int spToPx(Context context, int sp) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics()) + 0.5f);
    }

	public static float pxToDp(float px) {
		float densityDpi = Resources.getSystem().getDisplayMetrics().densityDpi;
		return px / (densityDpi / 160f);
	}

	public static void changeIconDrawableToGray(Context context, Drawable drawable) {
		changeIconDrawable(drawable, ContextCompat.getColor(context, android.R.color.darker_gray));
	}

	public static void changeIconDrawable(Drawable drawable, int color) {
		if (drawable != null) {
			drawable.mutate();
			drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
		}
	}

}
