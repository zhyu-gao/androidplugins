/*
 * Project Name:ThirdPlugins
 * File Name:ShareToWechatMoment.java
 * Package Name:
 * Date:12/30/16 4:19 PM
 * Copyright (c) 2016, newvision All Rights Reserved.
 */

package com.newvision.share.wrapper;

import android.widget.Toast;

import com.newvision.share.Constants;
import com.newvision.share.NativeUtils;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXImageObject;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXWebpageObject;
import com.unity3d.player.UnityPlayer;

import java.io.File;

/**
 * Created by gaozhenyu on 12/30/2016.
 */

public class ShareToWechatMoment extends ShareBase
{
    private IWXAPI api;
    public ShareToWechatMoment()
    {
        if (api == null)
        {
            api = WXAPIFactory.createWXAPI(UnityPlayer.currentActivity,
                    Constants.WECHATAPP_ID);
        }
        if (!api.isWXAppInstalled())
        {
            NativeUtils.ShowMessage("请安装微信后进行分享！");
            return;
        }
        api.registerApp(Constants.WECHATAPP_ID);
    }

    @Override
    public void ShareImage()
    {
        File file = new File(contents.path);
        if (!file.exists())
        {
            Toast.makeText(UnityPlayer.currentActivity,
                    "文件不存在" + " path = " + contents.path, Toast.LENGTH_LONG)
                    .show();
            return;
        }
        WXImageObject imgObj = new WXImageObject();
        imgObj.setImagePath(contents.path);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneTimeline;
        api.sendReq(req);
    }

    @Override
    public void ShareNews()
    {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = contents.url;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = contents.title;
        msg.description = contents.description;
        msg.thumbData = NativeUtils.GetThumbBytes(contents.path);
        //Bitmap thumb;
        //byte[] bytes = Util.getHtmlByteArray(contents[0]);
        //thumb = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        //thumb = Util.drawable2Bitmap(Util.GetIcon());
        //msg.setThumbImage(thumb);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("tuwen");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneTimeline;
        api.sendReq(req);
    }


    private String buildTransaction(final String type)
    {
        return (type == null) ? String.valueOf(System.currentTimeMillis())
                : type + System.currentTimeMillis();
    }
}
