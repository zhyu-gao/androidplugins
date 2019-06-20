/*
 * Project Name:ThirdPlugins
 * File Name:WeiboResponseActivity.java
 * Package Name:
 * Date:17-1-13 下午2:13
 * Copyright (c) 2016, newvision All Rights Reserved.
 */

package com.newvision.share.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.newvision.share.NativeUtils;
import com.newvision.share.wrapper.ErrorMsg;
import com.newvision.share.wrapper.ShareManager;
import com.newvision.share.wrapper.ShareWrapper;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.constant.WBConstants;

/**
 * Created by gaozhenyu on 2017/1/13.
 */

public class WeiboResponseActivity extends Activity implements IWeiboHandler.Response
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (ShareManager.weiboRequest == null)
            return;
        ShareManager.getWbapi().handleWeiboResponse(getIntent(), this);
        ShareManager.getWbapi().sendRequest(this, ShareManager.weiboRequest);
    }

    @Override
    protected void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);
        ShareManager.getWbapi().handleWeiboResponse(getIntent(), this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        ShareManager.getWbapi().handleWeiboResponse(getIntent(), this);
        finish();
    }

    @Override
    public void onResponse(BaseResponse baseResponse)
    {
        ErrorMsg errorMsg = new ErrorMsg();
        Log.i("2222222222222222222222","weibocallbackkkkkkkkkkkkkkkkkkkkk");
        switch (baseResponse.errCode)
        {
            case WBConstants.ErrorCode.ERR_OK:
                errorMsg.errorCode = baseResponse.errCode;
                NativeUtils.ShowMessage("分享成功");
                break;
            case WBConstants.ErrorCode.ERR_CANCEL:
                errorMsg.errorCode = -2;
                NativeUtils.ShowMessage("取消分享");
                break;
            case WBConstants.ErrorCode.ERR_FAIL:
                errorMsg.errorCode = -1;
                errorMsg.msg = baseResponse.errMsg;
                //NativeUtils.ShowMessage("error" + baseResponse);
                break;
        }
        ShareWrapper.SendMessage(errorMsg);
    }
}
