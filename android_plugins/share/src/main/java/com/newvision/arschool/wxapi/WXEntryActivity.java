
/*
 * Project Name:ThirdPlugins
 * File Name:WXEntryActivity.java
 * Package Name:
 * Date:16-12-28 下午3:37
 * Copyright (c) 2016, newvision All Rights Reserved.
 */

package com.newvision.arschool.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.newvision.share.NativeUtils;
import com.newvision.share.wrapper.ErrorMsg;
import com.newvision.share.wrapper.LoginWithWechat;
import com.newvision.share.wrapper.ShareManager;
import com.newvision.share.wrapper.ShareWrapper;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.constant.WBConstants;
import com.tencent.mm.sdk.openapi.BaseReq;
import com.tencent.mm.sdk.openapi.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.SendAuth;

/**
 * 此文件必须在项目包名.wxapi下
 */
public class WXEntryActivity extends Activity implements
        IWXAPIEventHandler
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ShareManager.getWxapi().handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);
        //.ShareManager.getWxapi().handleIntent(getIntent(),this);
        ShareManager.getWbapi().handleWeiboResponse(getIntent(), response);
    }

    IWeiboHandler.Response response = new IWeiboHandler.Response()
    {
        @Override
        public void onResponse(BaseResponse baseResponse)
        {
            ErrorMsg errorMsg = new ErrorMsg();
            switch (baseResponse.errCode)
            {
                case WBConstants.ErrorCode.ERR_OK:
                    NativeUtils.ShowMessage("分享成功");
                    errorMsg.errorCode = 10000;
                    errorMsg.msg = "success";
                    NativeUtils.ShowMessage("发送成功");
                    break;
                case WBConstants.ErrorCode.ERR_CANCEL:
                    errorMsg.errorCode = 10001;
                    errorMsg.msg = "cancel";
                    NativeUtils.ShowMessage("取消分享");
                    break;
                case WBConstants.ErrorCode.ERR_FAIL:
                    errorMsg.errorCode = 10002;
                    errorMsg.msg = "fail";
                    errorMsg.msg = baseResponse.errMsg;
                    //NativeUtils.ShowMessage("error" + baseResponse);
                    break;
            }
            ShareWrapper.SendMessage(errorMsg);
        }
    };

    @Override
    public void onResp(BaseResp resp)
    {
        if (!LoginWithWechat.isReciveCode)
        {
            ErrorMsg errorMsg = new ErrorMsg();
            errorMsg.errorCode = resp.errCode;
            errorMsg.msg = resp.errStr;
            Log.i("22222222222222222", "weixincallback");
            switch (resp.errCode)
            {
                case BaseResp.ErrCode.ERR_OK:
                    if (resp instanceof SendAuth.Resp)
                    {
                        SendAuth.Resp auth = (SendAuth.Resp) resp;
                        String code = auth.token;
                        errorMsg.loginCode = code;
                        Log.i("123123123", code);
                    }
                    else
                    {
                        errorMsg.errorCode = 10000;
                        errorMsg.msg = "success";
                        NativeUtils.ShowMessage("发送成功");
                    }
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    NativeUtils.ShowMessage("发送取消");
                    errorMsg.errorCode = 10001;
                    errorMsg.msg = "cancel";
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                    errorMsg.errorCode = 10002;
                    errorMsg.msg = "fail";
                    NativeUtils.ShowMessage("发送被拒绝");
                    break;
                default:
                    break;
            }
            ShareWrapper.SendMessage(errorMsg);
            LoginWithWechat.isReciveCode = true;
        }
        finish();
    }

    @Override
    public void onReq(BaseReq baseReq)
    {
        finish();
    }
}
