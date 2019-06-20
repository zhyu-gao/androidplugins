/*
 * Project Name:ThirdPlugins
 * File Name:ShareManager.java
 * Package Name:
 * Date:17-1-12 下午4:00
 * Copyright (c) 2016, newvision All Rights Reserved.
 */

package com.newvision.share.wrapper;

import com.newvision.share.Constants;
import com.newvision.share.NativeUtils;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.Tencent;
import com.unity3d.player.UnityPlayer;

/**
 * Created by gaozhenyu on 2017/1/12.
 */

public class ShareManager {
    private static IWXAPI wxapi;
    private static Tencent qqapi;
    private static IWeiboShareAPI wbapi;
    public static SendMultiMessageToWeiboRequest weiboRequest;

    public static IWXAPI getWxapi() {
        if (wxapi == null) {
            wxapi = WXAPIFactory.createWXAPI(UnityPlayer.currentActivity,
                    Constants.WECHATAPP_ID);
        }
        wxapi.registerApp(Constants.WECHATAPP_ID);
        return wxapi;
    }

    public static Tencent getQqapi() {
        if (qqapi == null) {
            qqapi = Tencent.createInstance(Constants.QQAPP_ID,
                    UnityPlayer.currentActivity.getApplicationContext());
        }
        return qqapi;
    }

    public static IWeiboShareAPI getWbapi() {
        if (wbapi == null) {
            wbapi = WeiboShareSDK.createWeiboAPI(
                    UnityPlayer.currentActivity, Constants.WEIBOAPP_ID);
            wbapi.registerApp();
        }
        return wbapi;
    }

    public static boolean isQQSuportShare() {
//        if (Util.isSupportShareToQQ(UnityPlayer.currentActivity))
//        {
//            return true;
//        }
//        else
//        {
//            return false;
//        }
        return true;
    }

    public static boolean isWXInstalled() {
        if (getWxapi().isWXAppInstalled()) {
            return true;
        } else {
            NativeUtils.ShowMessage("please install wechat first");
            return false;
        }
    }

    public static boolean isWeiboSuported() {
        if (getWbapi().isWeiboAppInstalled()) {
            return true;
        } else {
            NativeUtils.ShowMessage("请安装新浪微博客户端后再进行分享");
            return false;
        }
    }

}
