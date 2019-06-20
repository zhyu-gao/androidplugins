/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.newvision.checksignandrealcut;

import android.app.Application;
import android.util.Log;

/**
 * Created by gaozhenyu on 2016/12/9.
 */

public class CheckSign extends Application
{
    static
    {
        System.loadLibrary("getkeyandrealcut");
    }

    private native void jniCheckAPP();

    public static native int[] GetPrivateKey(int num);


    @Override
    public void onCreate()
    {
        super.onCreate();
        jniCheckAPP();
    }

    public void popAlarm()
    {
        Log.i("error", "error");
        System.exit(0);
    }
}
