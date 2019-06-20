package com.newvision.mainunityplayer;

import android.content.Intent;
import android.os.Bundle;

import com.unity3d.player.UnityPlayerActivity;

/**
 * Created by gaozhenyu on 2018/3/8.
 */

public class MainUnityPlayer extends UnityPlayerActivity {
    public  static MainUnityPlayer mainUnityPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mainUnityPlayer=this;
        super.onCreate(savedInstanceState);

    }
    @Override
    protected void  onResume()
    {
        super.onResume();
        if(  AwakeActivity.awakeActivity!=null) {
            AwakeActivity.awakeActivity.finish();
            AwakeActivity.awakeActivity.overridePendingTransition(0, 0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        for (IUnityPlayer unityPlayer : UnityPlayerManager.unityPlayerList)
        {
            unityPlayer.setActivityResult(requestCode,resultCode,data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public  void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        for (IUnityPlayer unityPlayer : UnityPlayerManager.unityPlayerList)
        {
            unityPlayer.setRequestPermissionsResult(requestCode,permissions,grantResults);
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public  void  onDestroy()
    {
        for (IUnityPlayer unityPlayer : UnityPlayerManager.unityPlayerList)
        {
            unityPlayer.onDestroy();
        }
        super.onDestroy();
    }
}
