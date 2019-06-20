package com.newvision.filesystem;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.unity3d.player.UnityPlayer;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaozhenyu on 2016/12/5.
 */

public class AndroidFileSystem {
    private static boolean isDebug;

    public static void DebugMode(boolean isDebug) {
        AndroidFileSystem.isDebug = isDebug;
    }

    public static void DebugToast(String msg) {
        if (isDebug)
            Toast.makeText(UnityPlayer.currentActivity, msg, Toast.LENGTH_LONG).show();
    }

    public static boolean CreateDirectory(String path) {
        return false;
    }

    public static boolean CreateFile(String path) {
        return false;
    }

    public static boolean DeleteDirectory(String path) {
        return false;
    }

    public static boolean DeleteFile(String path) {
        return false;
    }

    public static boolean DirectoryExists(String path) {
        return false;
    }

    public static boolean FileExists(String path) {
        try {
            InputStreamReader inputReader = new InputStreamReader(
                    UnityPlayer.currentActivity.getResources().getAssets()
                            .open(path));
            if (inputReader != null)
                return true;
            else
                return false;
        } catch (IOException e) {
            return false;
        }
    }

    public static long FileSize(String path) {
        try {
            InputStream inputStream =
                    UnityPlayer.currentActivity.getResources().getAssets()
                            .open(path);
            DebugToast(inputStream.available() + "K_debug");
            return inputStream.available();
        } catch (IOException e) {
            return 0;
        }
    }

    public static String[] GetDirectoryFiles(String path, String postfix) {
        String[] filesName = null;
        List<String> resultsName = new ArrayList<String>();
        try {
            filesName = UnityPlayer.currentActivity.getResources().getAssets()
                    .list(path);
            for (int i = 0; i < filesName.length; i++) {
                try {
                    UnityPlayer.currentActivity.getResources().getAssets()
                            .open(path + "/" + filesName[i]);
                    if (postfix == null) {
                        resultsName.add(filesName[i]);
                    } else {
                        if (filesName[i].endsWith(postfix)) {
                            resultsName.add(filesName[i]);
                        }
                    }

                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }

            }
            String[] result = new String[resultsName.size()];
            resultsName.toArray(result);
            DebugToast(result.length + "_debug" + path);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String[]{};
    }

    public static String[] GetDirectorys(String path) {
        String[] filesName = null;
        List<String> resultsName = new ArrayList<String>();
        try {
            filesName = UnityPlayer.currentActivity.getResources().getAssets()
                    .list(path);
            for (int i = 0; i < filesName.length; i++) {
                try {
                    UnityPlayer.currentActivity.getResources().getAssets()
                            .open(path + "/" + filesName[i]);

                } catch (FileNotFoundException e1) {
                    resultsName.add(filesName[i]);
                    e1.printStackTrace();
                }
            }
            String[] result = new String[resultsName.size()];
            resultsName.toArray(result);
            DebugToast(result.length + "_debug" + path);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String[]{};
    }

    public static byte[] ReadFile(String path) {
        try {
            InputStream inputStream =
                    UnityPlayer.currentActivity.getResources().getAssets()
                            .open(path);
            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
            byte[] buff = new byte[512];
            int rc = 0;
            while ((rc = inputStream.read(buff, 0, 100)) > 0) {
                swapStream.write(buff, 0, rc);
            }

            byte[] in2b = swapStream.toByteArray();
            DebugToast(in2b.length + "1111111");
            return in2b;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new byte[]{};
    }

    public static String ReadTextFile(String path) {
        try {
            InputStreamReader inputReader = new InputStreamReader(
                    UnityPlayer.currentActivity.getResources().getAssets()
                            .open(path));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String Result = "";
            while ((line = bufReader.readLine()) != null)
                Result += line;
            DebugToast(Result + "_debug");
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    public static String[] ReadAllLinesTest(Context activity, String path) {
        List<String> results = new ArrayList<>();
        try {
            InputStreamReader inputReader = new InputStreamReader(
                    activity.getResources().getAssets()
                            .open(path));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            while ((line = bufReader.readLine()) != null)
            {
                results.add(line);
            }
            String[] strings = new String[results.size()];
            for(int i =0;i<results.size();i++)
            {
                Log.i("123123123123",line);
                strings[i] = results.get(i);
            }
            return strings;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String[]{};
    }
    public static String[] ReadAllLines(String path) {
        List<String> results = new ArrayList<>();
        try {
            Log.i("123123213213213213",UnityPlayer.currentActivity.toString());
            InputStreamReader inputReader = new InputStreamReader(
                    UnityPlayer.currentActivity.getResources().getAssets()
                            .open(path));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            while ((line = bufReader.readLine()) != null)
            {
                results.add(line);
            }
            String[] strings = new String[results.size()];
            for(int i =0;i<results.size();i++)
            {
                strings[i] = results.get(i);
            }
            return strings;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String[]{};
    }
    public static boolean WriteToFile(String path, byte[] info, int length) {
        return false;
    }

    public static boolean WriteToTextFile(String path, String content) {
        return false;
    }
}
