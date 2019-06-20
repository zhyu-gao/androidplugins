package com.newvision.mainunityplayer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by gaozhenyu on 2018/3/8.
 */

public interface IUnityPlayer {
    void setActivityResult(int requestCode, int resultCode, Intent data);

    void setRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResultsa);

    void onDestroy();
}
