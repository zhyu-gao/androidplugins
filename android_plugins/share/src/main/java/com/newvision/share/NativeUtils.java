package com.newvision.share;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import com.unity3d.player.UnityPlayer;

public class NativeUtils {
    public static void ShowMessage(final String msg) {
        UnityPlayer.currentActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (Constants.isDebugMode) {
                    Toast.makeText(UnityPlayer.currentActivity, msg,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static Bitmap GetThumbImage(String content) {
        if (content == null || content.equals("")) {
            return Util.drawable2Bitmap(Util.GetIcon());
        } else if (Patterns.WEB_URL.matcher(content).matches()) {
            return Util.Bytes2Bitmap(Util.getHtmlByteArray(content));
        } else {
            return Util.Bytes2Bitmap(Util.readFromFile(content, 0, -1));
        }
    }

    public static byte[] GetThumbBytes(String content) {
        if (content == null || content.equals("")) {
            return Util.drawable2Bytes(Util.GetIcon());
        } else if (Patterns.WEB_URL.matcher(content).matches()) {
            byte[] bytes = Util.getHtmlByteArray(content);
            if (bytes.length > 32 * 1024) {
                Log.i("share debug", "this url size--" + bytes.length + " > 32K,get icon");
                return Util.drawable2Bytes(Util.GetIcon());
            } else {
                return Util.getHtmlByteArray(content);
            }
        } else {
            return Util.readFromFile(content, 0, -1);
        }
    }

}
