/*
 * Project Name:ThirdPlugins
 * File Name:ShareToWeibo.java
 * Package Name:
 * Date:17-1-10 下午1:58
 * Copyright (c) 2016, newvision All Rights Reserved.
 */

package com.newvision.share.wrapper;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.newvision.share.NativeUtils;
import com.newvision.share.activity.WeiboResponseActivity;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.constant.WBConstants;
import com.sina.weibo.sdk.utils.Utility;
import com.unity3d.player.UnityPlayer;

/**
 * Created by gaozhenyu on 2017/1/10.
 */

public class ShareToWeibo extends ShareBase implements IWeiboHandler.Response
{
    @Override
    public void ShareImage()
    {
        if(!ShareManager.isWeiboSuported())
            return;
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        TextObject tb = new TextObject();
        tb.text = "test";
        weiboMessage.mediaObject = tb;
        ImageObject iob = new ImageObject();
        Bitmap bitmap = BitmapFactory.decodeFile(contents.path);
        iob.setImageObject(bitmap);
        weiboMessage.imageObject = iob;
        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.multiMessage = weiboMessage;
        Intent intent = new Intent(UnityPlayer.currentActivity, WeiboResponseActivity.class);
        ShareManager.weiboRequest = request;
        UnityPlayer.currentActivity.startActivity(intent);
        //ShareManager.getWbapi().sendRequest(UnityPlayer.currentActivity, request);
    }


    @Override
    public void ShareNews()
    {
        if(!ShareManager.isWeiboSuported())
            return;
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        TextObject tb = new TextObject();
        tb.text = contents.title;
        weiboMessage.textObject = tb;
        WebpageObject wo = new WebpageObject();
        wo.identify = Utility.generateGUID();
        wo.title = contents.title;
        wo.description = contents.description;
        wo.thumbData = NativeUtils.GetThumbBytes(contents.path);
        wo.actionUrl = contents.url;
        weiboMessage.mediaObject = wo;
        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
        // 用transaction唯一标识�?个请�?
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.multiMessage = weiboMessage;
        // 3. 发�?�请求消息到微博，唤起微博分享界�?
        Intent intent = new Intent(UnityPlayer.currentActivity, WeiboResponseActivity.class);
        ShareManager.weiboRequest = request;
        UnityPlayer.currentActivity.startActivity(intent);
        //ShareManager.getWbapi().sendRequest(UnityPlayer.currentActivity, request);
    }

    @Override
    public void onResponse(BaseResponse baseResponse)
    {
        ErrorMsg errorMsg = new ErrorMsg();
        switch (baseResponse.errCode) {
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
