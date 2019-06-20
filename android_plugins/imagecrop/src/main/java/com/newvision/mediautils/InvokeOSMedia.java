package com.newvision.mediautils;

import android.app.Activity;
import android.content.Intent;

import com.unity3d.player.UnityPlayer;

/**
 * Created by gaozhenyu on 2017/2/22.
 */

public class InvokeOSMedia
{
    /**
     * 直接选取本地系统图片，不进行任何处理，直接放回绝对路径
     *
     * @param gameObjectName
     *            回调时脚本挂载物体名称
     * @param methodName
     *            回调Unity时的方法名
     */
    public static void GoToDCIM(final String gameObjectName,
                                final String methodName, final String type)
    {
        UnityPlayer.currentActivity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                Intent intent = new Intent(UnityPlayer.currentActivity,
                        SelectOSPicActivity.class);
                intent.putExtra("methodName", methodName);
                intent.putExtra("gameObjectName", gameObjectName);
                intent.putExtra("type", type);
                UnityPlayer.currentActivity.startActivity(intent);
            }
        });
    }

    /**
     * 选取系统图片，并进行裁剪保存到本地路径
     *
     * @param gameObjectName
     *            回调时脚本挂载物体名称
     * @param methodName
     *            回调Unity时的方法名
     * @param tempPicPath
     *            截取完的图片临时的存储位置
     */
    public static void GoToSelectOSPicWithCut(final String gameObjectName,
                                              final String methodName, final String tempPicPath, final String type)
    {
        UnityPlayer.currentActivity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                Intent intent = new Intent(UnityPlayer.currentActivity,
                        SelectOSPicActivity.class);
                intent.putExtra("withcut", type);
                intent.putExtra("methodName", methodName);
                intent.putExtra("gameObjectName", gameObjectName);
                intent.putExtra("tempPicPath", tempPicPath);
                intent.putExtra("type", "select");
                UnityPlayer.currentActivity.startActivity(intent);
            }
        });
    }
    public static void Test(final Activity context, final int mode, final int cuttype, final String picPath, final String gameObjectName, final String callbackMethodName)
    {
        final String mediamode;
        final String wichcuttype;
        if (mode == 0)
        {
            mediamode = "takephoto";
        }
        else
        {
            mediamode = "select";
        }
        if (cuttype == 0)
        {
            wichcuttype = "False";
        }
        else
        {
            wichcuttype = "True";
        }
        context.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                Intent intent = new Intent(context,
                        SelectOSPicActivity.class);
                intent.putExtra("withcut", wichcuttype);
                intent.putExtra("methodName", callbackMethodName);
                intent.putExtra("gameObjectName", gameObjectName);
                intent.putExtra("tempPicPath", picPath);
                intent.putExtra("type", mediamode);
                context.startActivity(intent);
            }
        });
    }
    public static void InvokeOsMediaMethod(final int mode,final int cuttype,final String picPath,final String gameObjectName,final String callbackMethodName)
    {
        final String mediamode;
        final String wichcuttype;
        if(mode == 0)
        {
            mediamode = "takephoto";
        }
        else
        {
            mediamode = "select";
        }
        if(cuttype == 0)
        {
            wichcuttype = "False";
        }
        else
        {
            wichcuttype = "True";
        }
        UnityPlayer.currentActivity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                Intent intent = new Intent(UnityPlayer.currentActivity,
                        SelectOSPicActivity.class);
                intent.putExtra("withcut", wichcuttype);
                intent.putExtra("methodName", callbackMethodName);
                intent.putExtra("gameObjectName", gameObjectName);
                intent.putExtra("tempPicPath", picPath);
                intent.putExtra("type", mediamode);
                UnityPlayer.currentActivity.startActivity(intent);
            }
        });
    }

    public static void TakePhoto(final String gameObjectName,
                                 final String methodName, final String picturePath, final String type)
    {
        UnityPlayer.currentActivity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                Intent intent = new Intent(UnityPlayer.currentActivity,
                        SelectOSPicActivity.class);
                intent.putExtra("withcut", type);
                intent.putExtra("methodName", methodName);
                intent.putExtra("gameObjectName", gameObjectName);
                intent.putExtra("tempPicPath", picturePath);
                intent.putExtra("type", "takephoto");
                UnityPlayer.currentActivity.startActivity(intent);
            }
        });
    }
}
