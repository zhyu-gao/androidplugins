package com.newvision.baiduspeech;

import android.media.AudioManager;

import com.baidu.tts.auth.AuthInfo;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;
import com.unity3d.player.UnityPlayer;

import java.io.File;

/**
 * Created by gaozhenyu on 2017/11/27.
 */

public class BaiduSynthesizer {
    private static final String TAG = "BaiduSynthesizer";

    private static String appId ;
    private static String appKey;
    private static String keySecret;

    public static String callBackObjName;

    private static TtsMode ttsMode = TtsMode.ONLINE;

    // ================选择TtsMode.ONLINE  不需要设置以下参数; 选择TtsMode.MIX 需要设置下面2个离线资源文件的路径
    private static String TEMP_DIR = "/sdcard/baiduTTS"; //重要！请手动将assets目录下的3个dat 文件复制到该目录

    private static String TEXT_FILENAME = TEMP_DIR + "/" + "bd_etts_text.dat"; // 请确保该PATH下有这个文件

    private static String MODEL_FILENAME = TEMP_DIR + "/" + "bd_etts_speech_male.dat"; // 请确保该PATH下有这个文件 male是男声 female女声

    // ===============初始化参数设置完毕，更多合成参数请至getParams()方法中设置 =================

    protected static SpeechSynthesizer mSpeechSynthesizer;

    public static void InitSynthesizer(final String gameObjName,final String[] appKeys) {
        UnityPlayer.currentActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                callBackObjName = gameObjName;
                appId = appKeys[0];
                appKey = appKeys[1];
                keySecret = appKeys[2];
                initTTs();
            }
        });
    }

    public static void BeginSynthesizer(final String text, final String voicer, final String speed) {
        UnityPlayer.currentActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, voicer);
                mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEED, speed);
                int result = mSpeechSynthesizer.speak(text);
                checkResult(result, "speak");
            }
        });
    }

    public static void StopSynthesizer() {
        UnityPlayer.currentActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mSpeechSynthesizer.stop();
                mSpeechSynthesizer.release();
            }
        });
    }

    private static void initTTs() {
        boolean isMix = ttsMode.equals(TtsMode.MIX);
        SpeechSynthesizerListener listener = new BaiduSynthesizerCallBack(); // 日志更新在UI中，可以换成MessageListener，在logcat中查看日志
        mSpeechSynthesizer = SpeechSynthesizer.getInstance();
        mSpeechSynthesizer.setContext(UnityPlayer.currentActivity);
        mSpeechSynthesizer.setSpeechSynthesizerListener(listener);

        Logger.log(appId+"---"+appKey+"----"+keySecret);

        int result = mSpeechSynthesizer.setAppId(appId);
        checkResult(result, "setAppId");
        result = mSpeechSynthesizer.setApiKey(appKey, keySecret);
        checkResult(result, "setApiKey");

        // 以下setParam 参数选填。不填写则默认值生效
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "4"); // 设置在线发声音人： 0 普通女声（默认） 1 普通男声 2 特别男声 3 情感男声<度逍遥> 4 情感儿童声<度丫丫>
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_VOLUME, "9"); // 设置合成的音量，0-9 ，默认 5
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEED, "5");// 设置合成的语速，0-9 ，默认 5
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_PITCH, "5");// 设置合成的语调，0-9 ，默认 5

        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_MIX_MODE, SpeechSynthesizer.MIX_MODE_DEFAULT);
        // 该参数设置为TtsMode.MIX生效。即纯在线模式不生效。
        // MIX_MODE_DEFAULT 默认 ，wifi状态下使用在线，非wifi离线。在线状态下，请求超时6s自动转离线
        // MIX_MODE_HIGH_SPEED_SYNTHESIZE_WIFI wifi状态下使用在线，非wifi离线。在线状态下， 请求超时1.2s自动转离线
        // MIX_MODE_HIGH_SPEED_NETWORK ， 3G 4G wifi状态下使用在线，其它状态离线。在线状态下，请求超时1.2s自动转离线
        // MIX_MODE_HIGH_SPEED_SYNTHESIZE, 2G 3G 4G wifi状态下使用在线，其它状态离线。在线状态下，请求超时1.2s自动转离线

        mSpeechSynthesizer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        result = mSpeechSynthesizer.initTts(ttsMode);
        checkResult(result, "initTts");
        UnityPlayer.UnitySendMessage(callBackObjName, "GetSynthesizerInitResult", "ok");
    }

    /**
     * 检查 TEXT_FILENAME, MODEL_FILENAME 这2个文件是否存在，不存在请自行从assets目录里手动复制
     *
     * @return
     */
    private static boolean checkOfflineResources() {
        String[] filenames = {TEXT_FILENAME, MODEL_FILENAME};
        for (String path : filenames) {
            File f = new File(path);
            if (!f.canRead()) {
                Logger.log("[ERROR] 文件不存在或者不可读取，请从assets目录复制改文件到：" + path);
                return false;
            }
        }
        return true;
    }

    private static void checkResult(int result, String method) {
        if (result != 0) {
            UnityPlayer.UnitySendMessage(BaiduSynthesizer.callBackObjName, "GetSynthesizerInitResult", "error:{" + result + method + "}");
            Logger.log( "error code :" + result + " method:" + method + ", 错误码文档:http://yuyin.baidu.com/docs/tts/122 ");
        }
    }

    /**
     * 检查appId ak sk 是否填写正确，另外检查官网应用内设置的包名是否与运行时的包名一致。本demo的包名定义在build.gradle文件中
     *
     * @return
     */
    private static boolean checkAuth() {
        AuthInfo authInfo = mSpeechSynthesizer.auth(ttsMode);
        if (!authInfo.isSuccess()) {
            // 离线授权需要网站上的应用填写包名。本demo的包名是com.baidu.tts.sample，定义在build.gradle中
            String errorMsg = authInfo.getTtsError().getDetailMessage();
            UnityPlayer.UnitySendMessage(BaiduSynthesizer.callBackObjName, "GetSynthesizerInitResult", "error:{" + errorMsg + "}");
            Logger.log("【error】鉴权失败 errorMsg=" + errorMsg);
            return false;
        } else {
            Logger.log("验证通过，离线正式授权文件存在。");
            return true;
        }
    }
}
