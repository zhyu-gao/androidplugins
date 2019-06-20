package com.zbar.lib.decode;


import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.newvision.qrcode.R;
import com.zbar.lib.CaptureActivity;
import com.zbar.lib.ZbarManager;
import com.zbar.lib.camera.CameraManager;


/**
 * 描述: 接受消息后解�?
 */
final class DecodeHandler extends Handler {

    CaptureActivity activity = null;

    DecodeHandler(CaptureActivity activity) {
        this.activity = activity;
    }

    @Override
    public void handleMessage(Message message) {
        if (message.what == R.id.decode) {
            decode((byte[]) message.obj, message.arg1, message.arg2);

        } else if (message.what == R.id.quit) {
            Looper.myLooper().quit();

        }
    }

    private void decode(byte[] data, int width, int height) {
        byte[] rotatedData = new byte[data.length];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++)
                rotatedData[x * height + height - y - 1] = data[x + y * width];
        }
        int tmp = width;// Here we are swapping, that's the difference to #11
        width = height;
        height = tmp;

        Log.i("2222",width+"------"+height+"-------"+data.length);
        ZbarManager manager = new ZbarManager();
		String result = manager.decode(rotatedData, width, height, false,
				activity.getX(), activity.getY(), activity.getCropWidth(),
				activity.getCropHeight());

        if (result != null) {
            Log.i("333333",result);
            if (null != activity.getHandler()) {
                Message msg = new Message();
                msg.obj = result;
                msg.what = R.id.decode_succeeded;
                activity.getHandler().sendMessage(msg);
            }
            CameraManager.get().stopPreview();
        } else {
            if (null != activity.getHandler()) {
                activity.getHandler().sendEmptyMessage(R.id.decode_failed);
            }
        }
    }

}
