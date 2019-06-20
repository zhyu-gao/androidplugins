package com.zbar.lib;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import com.unity3d.player.UnityPlayer;

public class QRCodeUtils {
    public static void GoToQRCodeTest(final Context context, final String gameobejectName,
                                      final String methodeName) {
        Intent intent = new Intent(context,
                CaptureActivity.class);
        intent.putExtra("gameobejectName", gameobejectName);
        intent.putExtra("methodeName", methodeName);
        context.startActivity(intent);
    }

    public static void GoToQRCode(final String gameobejectName,
                                  final String methodeName) {
        UnityPlayer.currentActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(UnityPlayer.currentActivity,
                        CaptureActivity.class);
                intent.putExtra("gameobejectName", gameobejectName);
                intent.putExtra("methodeName", methodeName);
                UnityPlayer.currentActivity.startActivity(intent);
            }
        });
    }

    static Boolean isHasCamera(Context context) {
        if (context.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            return true;
        } else {
            return false;
        }
    }
}
