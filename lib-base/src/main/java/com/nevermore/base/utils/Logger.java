package com.nevermore.base.utils;

import android.util.Log;

/**
 * @author xct
 * create on: 2023/2/15 11:02
 */
public interface Logger {

    String TAG = "Logger";
    boolean debug = true;

    String getSubTag();

    default boolean isLogOpen() {
        return true;
    }


    default void logI(String msg) {
        if (debug && isLogOpen()) {
            Log.i(getTag(), getOutputString(msg));
        }
    }

    default void logD(String msg) {
        if (debug && isLogOpen()) {
            Log.d(getTag(), getOutputString(msg));
        }
    }

    default void logE(String msg) {
        if (debug && isLogOpen()) {
            Log.e(getTag(), getOutputString(msg));
        }
    }

    default void logW(String msg) {
        if (debug && isLogOpen()) {
            Log.w(getTag(), getOutputString(msg));
        }
    }

    default String getTag() {
        return TAG + "." + getSubTag() + "[" + Thread.currentThread().getName() + "]";
    }


    default String getOutputString(String msg) {
        return msg;
    }


}
