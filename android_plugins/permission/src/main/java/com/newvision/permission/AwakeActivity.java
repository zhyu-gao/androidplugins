package com.newvision.permission;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.unity3d.player.UnityPlayerActivity;

/**
 * Created by Administrator on 2018/5/29.
 */

public class AwakeActivity extends UnityPlayerActivity {
    String WRITE_EXTERNAL_STORAGE="android.permission.WRITE_EXTERNAL_STORAGE";
    String READ_PHONE_STATE="android.permission.READ_PHONE_STATE";
    String READ_EXTERNAL_STORAGE="android.permission.READ_EXTERNAL_STORAGE";
    String CAMERA="android.permission.CAMERA";
    String ACCESS_COARSE_LOCATION="android.permission.ACCESS_COARSE_LOCATION";
    String RECORD_AUDIO="android.permission.RECORD_AUDIO";
    String READ_CONTACTS="android.permission.READ_CONTACTS";
    String CALL_PHONE="android.permission.CALL_PHONE";

    String sr = WRITE_EXTERNAL_STORAGE +","+READ_PHONE_STATE+","+READ_EXTERNAL_STORAGE+","+CAMERA+","+RECORD_AUDIO+","+READ_CONTACTS;

    static Context context;
    static AwakeActivity awakeActivity;

    @SuppressLint("ObsoleteSdkInt")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("AwakeActivity", "onCreate");
        context = this;
        awakeActivity = this;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] permissions = sr.split(",");
            UnityAndroidPermissions unityAndroidPermissions = new UnityAndroidPermissions();
            unityAndroidPermissions.RequestPermissionAsync(this, permissions, new UnityAndroidPermissions.IPermissionRequestResult() {
                @Override
                public void OnPermissionGranted(String permissionName) {

                }

                @Override
                public void OnPermissionDenied(String permissionName) {

                }
            });
            //ActivityCompat.requestPermissions(this, permissions, 10001);
        }
        super.onCreate(savedInstanceState);
    }

//    //回调
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == 10001) {
//            for (int i = 0; i < permissions.length; i++) {
//                Log.e("AwakeActivity:p", String.valueOf(permissions[i]));
//                Log.e("AwakeActivity:g", String.valueOf(grantResults[i]));
//            }
//            for (int i = 0; i < permissions.length; i++) {
//                if (grantResults[i] == -1) {
//                    showDialogTipUserRequestPermission();
//                    return;
//                }
//            }
//            Log.e("123","123");
//            Intent intent = new Intent(this, QRChangActivity.class);
//            startActivity(intent);
//            overridePendingTransition(0, 0);
//            finish();
//        }
//
//    }

    //弹窗
//    private void showDialogTipUserRequestPermission() {
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle("权限申请");
//        builder.setMessage("您已拒绝某些权限,请设置应用权限再次重新授权");
//        builder.setCancelable(false);
//        builder.setPositiveButton("重新授权", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Intent intent = new Intent(context, AwakeActivity.class);
//                startActivity(intent);
//                awakeActivity.finish();
//                overridePendingTransition(0, 0);
//            }
//        });
//        builder.setNegativeButton("退出", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                ((Activity) context).finish();
//            }
//        });
//        AlertDialog alertDialog = builder.create();
//        alertDialog.show();
//    }
}
