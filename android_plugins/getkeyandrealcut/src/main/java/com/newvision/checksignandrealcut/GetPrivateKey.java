package com.newvision.checksignandrealcut;

import java.io.UnsupportedEncodingException;

/**
 * Created by gaozhenyu on 2017/2/13.
 */

public class GetPrivateKey
{
    public static String GetKey(int num)
    {
        int[] keys = CheckSign.GetPrivateKey(num);
        String str = null;

        byte[] bytes = new byte[36];
        for(int i = 0;i<keys.length;i++)
        {
            bytes[i] = (byte) keys[i];
        }
        try
        {
            str = new String(bytes,"UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return str;
    }
}
