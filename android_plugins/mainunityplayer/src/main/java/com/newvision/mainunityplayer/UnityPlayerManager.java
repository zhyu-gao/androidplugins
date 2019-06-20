package com.newvision.mainunityplayer;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaozhenyu on 2018/3/8.
 */

public class UnityPlayerManager {
    public static List<IUnityPlayer> unityPlayerList = new ArrayList<>();
    public static void setUnityPlayerHandler(IUnityPlayer unityPlayer)
    {
        unityPlayerList.add(unityPlayer);
    }
    public static void removeUnityPlayerHandler(IUnityPlayer unityPlayer)
    {
        if(unityPlayerList.contains(unityPlayer))
            unityPlayerList.remove(unityPlayer);
    }
}
