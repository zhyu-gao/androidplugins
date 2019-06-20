
/*
 * Project Name:ThirdPlugins
 * File Name:ShareWrapper.java
 * Package Name:
 * Date:17-1-9 下午2:33
 * Copyright (c) 2016, newvision All Rights Reserved.
 */

package com.newvision.share.wrapper;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.newvision.share.Constants;
import com.unity3d.player.UnityPlayer;

/**
 * Created by gaozhenyu on 2016/12/20.
 * Unity调用部分，连接Unity中的C#
 */

public class ShareWrapper {
    private static ShareBase shareBase;

    /**
     * 初始化各个平台分享所需要的申请的AppID
     *
     * @param appids
     */
    public static void InitSharePlatform(String[] appids) {
        Constants.WECHATAPP_ID = appids[0];
        Constants.QQAPP_ID = appids[1];
        Constants.WEIBOAPP_ID = appids[2];
    }

    /**
     * 回调方法的自定义部分
     *
     * @param gameObjectName 接收回调脚本挂载的物体
     * @param methodName     接受回调的方法名
     */
    public static void CallBackMethod(String gameObjectName, String methodName) {
        Constants.GAMEOBJECT_NAME = gameObjectName;
        Constants.METHOD_NAME = methodName;
    }

    /**
     * 检测微信是否安装
     *
     * @return true->isInstalled false->not
     */
    public static boolean isWXAppInstalled() {
        return ShareManager.isWXInstalled();
    }

    /**
     * 微信登入，此方法会拉起微信=并放回code，或者取消授权
     */
    public static void LoginWithWechat() {
        LoginWithWechat.Login();
    }

    /**
     * 分享对外的调用方法
     *
     * @param contents 所要分享的内容
     * @param type     分享的类型 0，news 1、image
     * @param platform 分享平台 0、wechat friend 1、wechatmoment 2、QQ 3、weibo
     */
    public static void ShareContent(String contents, int type, int platform) {
        LoginWithWechat.isReciveCode = false;
        ShareContent content = JSON.parseObject(contents, ShareContent.class);
        switch (platform) {
            case 0:
                shareBase = new ShareToWechatFried();
                break;
            case 1:
                shareBase = new ShareToWechatMoment();
                break;
            case 2:
                shareBase = new ShareToQQ();
                break;
            case 3:
                shareBase = new ShareToWeibo();
                break;
        }
        shareBase.contents = content;
        switch (type) {
            case 0:
                shareBase.ShareNews();
                break;
            case 1:
                shareBase.ShareImage();
                break;
        }
    }

    public static void SendMessage(ErrorMsg msg) {
        String message = JSON.toJSONString(msg);
        Log.i("12312312321321", Constants.GAMEOBJECT_NAME + "-----" + Constants.METHOD_NAME + msg.errorCode);
        UnityPlayer.UnitySendMessage(Constants.GAMEOBJECT_NAME, Constants.METHOD_NAME, message);
    }

}
