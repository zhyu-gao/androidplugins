package com.newvision.share;

import android.os.Bundle;
import android.util.Log;

import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.unity3d.player.UnityPlayer;

import java.util.ArrayList;

public class QQShareUtils
{
    private Tencent tencent;

    public QQShareUtils()
    {
        if (tencent == null)
        {
            tencent = Tencent.createInstance(Constants.QQAPP_ID,
                    UnityPlayer.currentActivity.getApplicationContext());
        }
    }

    //QQ
    public void QQShareImage(String[] contents)
    {
        Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE,
                QQShare.SHARE_TO_QQ_TYPE_IMAGE);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, contents[0]);
        tencent.shareToQQ(UnityPlayer.currentActivity, params, qqShareListener);
    }

    public void QQShareTuwen(String[] contents)
    {
        Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE,
                QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, contents[1]);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, contents[2]);
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, contents[3]);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, contents[0]);
        //params.putByteArray(QQShare.SHARE_TO_QQ_IMAGE_URL,Util.Bitmap2Bytes(Util.drawable2Bitmap(Util.GetIcon())));
        tencent.shareToQQ(UnityPlayer.currentActivity, params, qqShareListener);
    }

    //QZone
    //qq不支持分享图片统�?都用图文分享
    public void QZoneShareImage(String[] contents)
    {
        Bundle params = new Bundle();
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE,
                QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE);
        params.putString(QzoneShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, contents[0]);// 必填
        tencent.shareToQzone(UnityPlayer.currentActivity, params,
                qqShareListener);
    }

    public void QZoneShareTuwen(String[] contents)
    {
        Log.i("111111111111111111", contents[3]);
        Bundle params = new Bundle();
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE,
                QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, contents[1]);// 必填
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, contents[2]);// 选填
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, contents[3].toString());// 必填
        ArrayList<String> imageurl = new ArrayList<String>();
        imageurl.add(contents[0]);
        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageurl);
        tencent.shareToQzone(UnityPlayer.currentActivity, params,
                qqShareListener);
    }

    //回调
    IUiListener qqShareListener = new IUiListener()
    {
        @Override
        public void onError(UiError arg0)
        {
            NativeUtils.ShowMessage("error" + arg0);
        }

        @Override
        public void onComplete(Object arg0)
        {
            NativeUtils.ShowMessage("分享成功");
        }

        @Override
        public void onCancel()
        {
            NativeUtils.ShowMessage("取消分享");
        }
    };
}
