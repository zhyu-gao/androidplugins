/*
 * Project Name:ThirdPlugins
 * File Name:ShareQQActivity.java
 * Package Name:
 * Date:17-1-12 下午5:42
 * Copyright (c) 2016, newvision All Rights Reserved.
 */

package com.newvision.share.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.newvision.share.NativeUtils;
import com.newvision.share.wrapper.ErrorMsg;
import com.newvision.share.wrapper.ShareManager;
import com.newvision.share.wrapper.ShareWrapper;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

/**
 * Created by gaozhenyu on 2017/1/12.
 */

public class QQResponseActivity extends Activity
{
    private static Activity context;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Bundle parms = intent.getBundleExtra("parms");
        context = this;
        if(parms != null)
        {
            ShareManager.getQqapi().shareToQQ(this, parms,qqShareListener);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Tencent.onActivityResultData(requestCode,resultCode,data,qqShareListener);
        super.onActivityResult(requestCode,resultCode,data);
    }

    //回调
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
            context.finish();
        }

        @Override
        public void onComplete(Object arg0)
        {
            ErrorMsg msg = new ErrorMsg();
            msg.errorCode = 0;
            ShareWrapper.SendMessage(msg);
            NativeUtils.ShowMessage("分享成功");
            context.finish();
        }

        @Override
        public void onCancel()
        {
            ErrorMsg msg = new ErrorMsg();
            msg.errorCode = -2;
            msg.msg = "canel";
            ShareWrapper.SendMessage(msg);
            NativeUtils.ShowMessage("取消分享");
            context.finish();
        }
    };
}
