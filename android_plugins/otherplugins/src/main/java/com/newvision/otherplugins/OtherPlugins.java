package com.newvision.otherplugins;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.unity3d.player.UnityPlayer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gaozhenyu on 2017/4/10.
 */

public class OtherPlugins {
    public static void CallPhone(final String phone) {
        UnityPlayer.currentActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                if (ActivityCompat.checkSelfPermission(UnityPlayer.currentActivity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    Toast.makeText(UnityPlayer.currentActivity.getApplicationContext(),"没有拨打电话权限",Toast.LENGTH_LONG).show();
                    return;
                }
                UnityPlayer.currentActivity.startActivity(intent);
            }
        });
    }

    public static String GetWIFIMacAddress()
    {
        WifiManager wifiMan = (WifiManager) UnityPlayer.currentActivity.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifiMan.getConnectionInfo();
        return info.getMacAddress();
    }
    /*
	 * Android Toast��ʾ
	 */
    public static void ShowAndroidToast(final String msg)
    {
        UnityPlayer.currentActivity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                Toast.makeText(UnityPlayer.currentActivity, msg,
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void ShowAndroidDialog(final String msg)
    {
        UnityPlayer.currentActivity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                Dialog alertDialog = new AlertDialog.Builder(
                        UnityPlayer.currentActivity)
                        .setTitle("提示ʾ")
                        .setMessage(msg)
                        .setPositiveButton("确定",
                                new DialogInterface.OnClickListener()
                                {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which)
                                    {
                                        dialog.dismiss();
                                    }
                                }).create();
                alertDialog.show();
            }
        });
    }

    /**
     * 保存图片，并发送消息更新相册
     *
     * @param path
     *            Unity传过来的图片
     */
    public static void SavePic(final String path, final String folderName)
    {
        UnityPlayer.currentActivity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                Bitmap bmp = null;
                File file = new File(getSDPath() + "/DCIM/" + folderName);
                SimpleDateFormat df = new SimpleDateFormat("yyyyMMddss");
                String filename = df.format(new Date());
                File file1 = new File(getSDPath() + "/DCIM/" + folderName + "/"
                        + getApplicationName() + filename + ".jpg");
                if (!file.exists())
                {
                    file.mkdir();
                }
                if (!file1.exists())
                {
                    try
                    {
                        file1.createNewFile();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                try
                {
                    FileInputStream fis = new FileInputStream(new File(path));
                    bmp = BitmapFactory.decodeStream(fis);

                    FileOutputStream output = new FileOutputStream(file1);
                    bmp.compress(Bitmap.CompressFormat.JPEG, 100, output);
                    output.flush();
                    output.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                Intent intent = new Intent(
                        Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri uri = Uri.fromFile(file1);
                intent.setData(uri);
                UnityPlayer.currentActivity.sendBroadcast(intent);
            }
        });
    }

    public static String getSDPath()
    {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
        if (sdCardExist)
        {
            sdDir = Environment.getExternalStorageDirectory();
        }
        return sdDir.toString();
    }

    public static String getApplicationName()
    {
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo = null;
        try
        {
            packageManager = UnityPlayer.currentActivity
                    .getApplicationContext().getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(
                    UnityPlayer.currentActivity.getPackageName(), 0);
        }
        catch (PackageManager.NameNotFoundException e)
        {
            applicationInfo = null;
        }
        String applicationName = (String) packageManager
                .getApplicationLabel(applicationInfo);
        return applicationName;
    }
}
