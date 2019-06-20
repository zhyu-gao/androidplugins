package com.newvision.thirdplugins;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.newvision.mediautils.InvokeOSMedia;


public class MainActivity extends AppCompatActivity
{
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) this.findViewById(R.id.button);
        //GetPrivateKey.GetKey(9);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //QRCodeUtils.GoToQRCodeTest(getApplicationContext(),"test","test");
                ImageCropTest();
            }
        });
//        Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
//        startActivity(intent);
//        setContentView(R.layout.activity_main);
//        Log.i("1111","2222");
//        // Example of a call to a native method
//        TextView tv = (TextView) findViewById(R.id.sample_text);
//        tv.setText(stringFromJNI());
//        Logger.d("11111111111"+AndroidFileSystem.FileExists("xml/xml.xml"));
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.qrcode);
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
//        byte[] bytes = baos.toByteArray();
//        ZbarManager manager = new ZbarManager();
//        Log.i("1111111",manager.decode(bytes,bitmap.getWidth(),bitmap.getHeight(),true,0,0,0,0) + "1111");
    }

    public void ImageCropTest()
    {
        InvokeOSMedia.TakePhoto("123","123","m","withcut");
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
}
