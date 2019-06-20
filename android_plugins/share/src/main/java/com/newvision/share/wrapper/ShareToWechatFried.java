package com.newvision.share.wrapper;

import com.newvision.share.NativeUtils;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXImageObject;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXWebpageObject;
import com.tencent.mm.sdk.platformtools.Log;

import java.io.File;

/**
 * Created by gaozhenyu on 2016/12/27.
 */

public class ShareToWechatFried extends ShareBase
{
    @Override
    public void ShareImage()
    {
        if(!ShareManager.isWXInstalled())
            return;
        File file = new File(contents.path);
        if (!file.exists())
        {
            NativeUtils.ShowMessage("文件不存在" + " path = " + contents.path);
            return;
        }
        WXImageObject imgObj = new WXImageObject();
        imgObj.setImagePath(contents.path);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;
        ShareManager.getWxapi().sendReq(req);
    }

    @Override
    public void ShareNews()
    {
        if(!ShareManager.isWXInstalled())
            return;
        Log.i("123123123123123",contents.url);
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = contents.url;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = contents.title;
        msg.description = contents.description;
        msg.thumbData = NativeUtils.GetThumbBytes(contents.path);
        //msg.setThumbImage(NativeUtils.GetThumbImage(contents.path));
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("tuwen");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;
        ShareManager.getWxapi().sendReq(req);
    }


    private String buildTransaction(final String type)
    {
        return (type == null) ? String.valueOf(System.currentTimeMillis())
                : type + System.currentTimeMillis();
    }
}
