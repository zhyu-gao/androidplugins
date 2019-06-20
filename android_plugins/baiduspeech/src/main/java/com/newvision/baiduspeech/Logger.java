package com.newvision.baiduspeech;

import android.util.Log;

/**
 * Created by gaozhenyu on 2017/11/28.
 */

public class Logger {
    private static boolean isDebug = true;

    public static void log(String msg) {
        if (isDebug)
            Log.e("baiduyuyin--------", msg);
    }
}
