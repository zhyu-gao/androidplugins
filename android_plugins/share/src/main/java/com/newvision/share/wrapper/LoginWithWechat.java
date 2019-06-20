/*
 * Project Name:ThirdPlugins
 * File Name:LoginWithWechat.java
 * Package Name:
 * Date:12/30/16 4:21 PM
 * Copyright (c) 2016, newvision All Rights Reserved.
 */

package com.newvision.share.wrapper;

import com.tencent.mm.sdk.openapi.SendAuth;

/**
 * Created by gaozhenyu on 12/30/2016.
 */

public class LoginWithWechat
{
    public static  boolean isReciveCode;
    public static void Login()
    {
        if(!ShareManager.isWXInstalled())
            return;
        isReciveCode = false;
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "arschool";
        ShareManager.getWxapi().sendReq(req);
    }
}
