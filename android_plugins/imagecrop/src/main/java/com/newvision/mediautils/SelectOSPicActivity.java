package com.newvision.mediautils;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.newvision.enjoycrop.EnjoyCropLayout;
import com.newvision.enjoycrop.core.BaseLayerView;
import com.newvision.enjoycrop.core.clippath.ClipPathLayerView;
import com.newvision.enjoycrop.core.clippath.ClipPathSquare;
import com.newvision.enjoycrop.core.mask.ColorMask;
import com.newvison.imagecrop.R;
import com.unity3d.player.UnityPlayer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SelectOSPicActivity extends Activity
{
    public static final int PHOTOSELECT = 1;
    public static final int PHOTOCUT = 2;
    public static final int PHOTORESOULT = 3;
    public static final int PHOTOHRAPH = 4;
    public static final int TAKEPHOTO = 5;
    private static final String TAG = "SelectOSPicActivity";

    private String withCut;
    private String tempPicPath;
    private String methodName;
    private String gameObjectName;
    private String type;
    File picture;

    EnjoyCropLayout enjoyCropLayout;

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbardone, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle presses on the action bar items
        int i = item.getItemId();
        if (i == R.id.action_done)
        {
            try
            {
                Crop();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        this.finish();
        return super.onOptionsItemSelected(item);
    }

    private void Crop() throws IOException
    {
        //裁剪图片，注意这里得到的图片并未进行任何压缩，是裁剪出来的原图大小
        Bitmap bitmap = enjoyCropLayout.crop();
        //保存图片
        SaveBitmap(bitmap);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enjoycropmainactivity);
        enjoyCropLayout = (EnjoyCropLayout) findViewById(R.id.enjoycroplayout);
        ActionBar actionBar = getActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);
        Intent intentParams = getIntent();
        withCut = intentParams.getStringExtra("withcut");
        methodName = intentParams.getStringExtra("methodName");
        gameObjectName = intentParams.getStringExtra("gameObjectName");
        tempPicPath = intentParams.getStringExtra("tempPicPath");
        type = intentParams.getStringExtra("type");

        if (type != null && type.equals("takephoto"))
        {
            if (!withCut.equals("True"))
            {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,
                        GetUriFile(new File(tempPicPath)));
                startActivityForResult(intent, TAKEPHOTO);
            }
            else
            {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, GetUriFile(new File(tempPicPath)));
                startActivityForResult(intent, PHOTOHRAPH);
            }
        }
        else
        {
            if (withCut != null && withCut.equals("True"))
            {
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, PHOTOCUT);
            }
            else
            {
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, PHOTOSELECT);
            }
        }

    }

    private void defineCropParams()
    {
        //设置裁剪集成视图，这里通过一定的方式集成了遮罩层与预览框
        BaseLayerView layerView = new ClipPathLayerView(this);
        layerView.setMask(ColorMask.getTranslucentMask()); //设置遮罩层,这里使用半透明的遮罩层
        layerView.setShape(new ClipPathSquare(1200)); //设置预览框形状
        enjoyCropLayout.setLayerView(layerView); //设置裁剪集成视图
        enjoyCropLayout.setRestrict(true); //设置边界限制，如果设置了该参数，预览框则不会超出图片
    }

    public void TakePhoto(String gameobjectName, String methodName, String picpath)
    {
        this.methodName = methodName;
        this.gameObjectName = gameobjectName;
        tempPicPath = picpath;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, GetUriFile(new File(tempPicPath)));
        startActivityForResult(intent, PHOTOHRAPH);
    }

    public Uri GetUriFile(File file)
    {
        int version = Build.VERSION.SDK_INT;
        Log.i("version",version+"");
        if(version >= 24)
        {
            return FileProvider.getUriForFile(this, "com.newvision.babysaid.provider", file);
        }
        else
        {
            return Uri.fromFile(file);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Log.i("11111111111111", resultCode + "-----" + data);
        if (resultCode == 0)
        {
            SelectOSPicActivity.this.finish();
            return;
        }

        if (requestCode == TAKEPHOTO)
        {
            Log.i("takephoto",
                    "is done ----------------------------------------");
            UnityPlayer.UnitySendMessage(gameObjectName, methodName, "done");
            SelectOSPicActivity.this.finish();
        }

        if (requestCode == PHOTOSELECT)
        {
            Uri selectedImage = data.getData(); // 获取系统返回的照片的Uri
            Log.i("123123123123", selectedImage.toString());
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);// 从系统表中查询指定Uri对应的照片
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex); // 获取照片路径
            UnityPlayer.UnitySendMessage(gameObjectName, methodName,
                    picturePath);
            cursor.close();
            SelectOSPicActivity.this.finish();
        }
        if (requestCode == PHOTOHRAPH)
        {
            // 设置文件保存路径这里放在跟目录下
            //picture = new File(tempPicPath);
            Bitmap bitmap = BitmapFactory.decodeFile(tempPicPath);
            enjoyCropLayout.setImage(bitmap);
            defineCropParams();
            //startPhotoZoom(GetUriFile(picture));
        }
        if (requestCode == PHOTOCUT)
        {
            Uri selectedImage = data.getData();
            try
            {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                enjoyCropLayout.setImage(bitmap);
                defineCropParams();
            }
            catch (RuntimeException e)
            {
                Log.e("error", "is too large");
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

        }
        if (requestCode == PHOTORESOULT)
        {
            Bundle extras = data.getExtras();
            if (extras != null)
            {
                Bitmap photo = extras.getParcelable("data");
                try
                {
                    SaveBitmap(photo);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            UnityPlayer.UnitySendMessage(gameObjectName, methodName, "done");
            SelectOSPicActivity.this.finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

//    private Bitmap compressImage(Bitmap image)
//    {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
//        int options = 100;
//        while (baos.toByteArray().length / 1024 > 1024)
//        {    //循环判断如果压缩后图片是否大于100kb,大于继续压缩
//            baos.reset();//重置baos即清空baos
//            options -= 50;//每次都减少10
//            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
//
//        }
//        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
//        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
//        return bitmap;
//    }

//    public String getDiskCacheDir(Context context)
//    {
//        String cachePath = null;
//        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
//                || !Environment.isExternalStorageRemovable())
//        {
//            cachePath = context.getExternalCacheDir().getPath();
//        }
//        else
//        {
//            cachePath = context.getCacheDir().getPath();
//        }
//        return cachePath;
//    }

//    public void startPhotoZoom(Uri uri)
//    {
//        Intent intent = new Intent("com.android.camera.action.CROP");
//        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        intent.setDataAndType(uri, "image/*");
//        intent.putExtra("crop", "true");
//        // aspectX aspectY 是宽高的比例
//        intent.putExtra("aspectX", 1);
//        intent.putExtra("aspectY", 1);
//        // outputX outputY 是裁剪图片宽高
//        intent.putExtra("outputX", 300);
//        intent.putExtra("outputY", 300);
//        intent.putExtra("return-data", true);
//        startActivityForResult(intent, PHOTORESOULT);
//    }

    public void SaveBitmap(Bitmap bitmap) throws IOException
    {
        FileOutputStream fOut = null;
        File file1 = null;
        File destDir = null;
        try
        {
            // 查看这个路径是否存在，
            // 如果并没有这个路径，
            // 创建这个路径
            String[] tempPath = tempPicPath.split("/");
            String path = "";
            for (int i = 0; i < tempPath.length - 1; i++)
            {
                path += tempPath[i] + "/";
                destDir = new File(path);
            }
            file1 = new File(tempPicPath);

            if (!destDir.exists())
            {
                destDir.mkdirs();
            }
            if (!file1.exists())
            {
                file1.createNewFile();
            }
            fOut = new FileOutputStream(tempPicPath);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        // 将Bitmap对象写入本地路径中，Unity在去相同的路径来读取这个文件
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
        UnityPlayer.UnitySendMessage(gameObjectName, methodName, "done");
        try
        {
            if (fOut != null)
            {
                fOut.flush();
            }
            fOut.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        // MediaScannerConnection.scanFile(this, new
        // String()[]{Environment.getExternalStoragePublicDirectory((Environment.DIRECTORY_DCIM.get)});

//		Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//		Uri uri = Uri.fromFile(file1);
//		intent.setData(uri);
//		SelectOSPicActivity.this.sendBroadcast(intent);
    }
}
