package com.jaf.biubiu;

/**
 * Created by jarrah on 2015/4/14. log
 */
public class L {
    private static final String TAG = "bbut";
    private static final boolean DBG = true;

    public static void dbg(String s) {
        if(DBG) android.util.Log.e(TAG, s);
    }

    public static void dbg(String f, Object... args) {
        dbg(String.format(f, args));
    }
}
