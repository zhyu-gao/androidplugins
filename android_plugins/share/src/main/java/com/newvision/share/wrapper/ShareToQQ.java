/*
 * Project Name:ThirdPlugins
 * File Name:ShareToQQ.java
 * Package Name:
 * Date:17-1-9 下午2:15
 * Copyright (c) 2016, newvision All Rights Reserved.
 */

package com.newvision.share.wrapper;

import android.content.Intent;
import android.os.Bundle;

import com.newvision.mainunityplayer.IUnityPlayer;
import com.newvision.mainunityplayer.UnityPlayerManager;
import com.newvision.share.NativeUtils;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.unity3d.player.UnityPlayer;

/**
 * Created by gaozhenyu on 2017/1/9.
 */

public class ShareToQQ extends ShareBase
{
    public ShareToQQ()
    {
        UnityPlayerManager.setUnityPlayerHandler(new IUnityPlayer() {
            @Override
            public void setActivityResult(int requestCode, int resultCode, Intent data) {
                Tencent.onActivityResultData(requestCode,resultCode,data,qqShareListener);
                UnityPlayerManager.removeUnityPlayerHandler(this);
            }

            @Override
            public void setRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResultsa) {

            }

            @Override
            public void onDestroy() {

            }
        });
    }
    @Override
    public void ShareImage()
    {
        Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE,
                QQShare.SHARE_TO_QQ_TYPE_IMAGE);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, contents.path);
        ShareManager.getQqapi().shareToQQ(UnityPlayer.currentActivity, params, qqShareListener);
//        Intent intent = new Intent(UnityPlayer.currentActivity, QQResponseActivity.class);
//        intent.putExtra("parms",params);
//        UnityPlayer.currentActivity.startActivity(intent);
    }

    @Override
    public void ShareNews()
    {
        Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE,
                QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, contents.title);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, contents.description);
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, contents.url);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, contents.path);
        //params.putByteArray(QQShare.SHARE_TO_QQ_IMAGE_URL,Util.Bitmap2Bytes(Util.drawable2Bitmap(Util.GetIcon())));
        ShareManager.getQqapi().shareToQQ(UnityPlayer.currentActivity, params, qqShareListener);
        //Intent intent = new Intent(UnityPlayer.currentActivity, QQResponseActivity.class);
        //intent.putExtra("parms",params);
        //UnityPlayer.currentActivity.startActivity(intent);
    }
    private void CallBack()
    {
        ErrorMsg errorMsg = new ErrorMsg();
        errorMsg.errorCode = 10000;
        errorMsg.msg = "success";
        ShareWrapper.SendMessage(errorMsg);
    }
    public static IUiListener qqShareListener = new IUiListener()
    {
        @Override
        public void onError(UiError arg0)
        {
            ErrorMsg msg = new ErrorMsg();
            msg.errorCode = arg0.errorCode;
            msg.msg = arg0.errorMessage;
            ShareWrapper.SendMessage(msg);
            NativeUtils.ShowMessage("error" + arg0);
        }

        @Override
        public void onComplete(Object arg0)
        {
            ErrorMsg msg = new ErrorMsg();
            msg.errorCode = 10000;
            ShareWrapper.SendMessage(msg);
            NativeUtils.ShowMessage("分享成功");
        }

        @Override
        public void onCancel()
        {
            ErrorMsg msg = new ErrorMsg();
            msg.errorCode = -2;
            msg.msg = "canel";
            ShareWrapper.SendMessage(msg);
            NativeUtils.ShowMessage("取消分享");
        }
    };
}
