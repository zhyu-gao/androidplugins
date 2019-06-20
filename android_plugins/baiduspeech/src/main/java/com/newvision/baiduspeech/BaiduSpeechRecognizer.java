package com.newvision.baiduspeech;

import com.baidu.speech.EventListener;
import com.baidu.speech.EventManager;
import com.baidu.speech.EventManagerFactory;
import com.baidu.speech.asr.SpeechConstant;
import com.newvision.baiduspeech.recognization.BaiduEventListener;
import com.unity3d.player.UnityPlayer;

import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by gaozhenyu on 2017/11/27.
 */

public class BaiduSpeechRecognizer {
    /*
        回调到Unity挂载脚本物体名
     */
    public static String callBackObjName;

    private static String appId ;
    private static String appKey;
    private static String keySecret;

    private static boolean enableOffline = false; // 测试离线命令词，需要改成true
    private static EventManager asr;
    private static boolean logTime = true;
    private static boolean isInited = false;
    private static EventListener mEventListener;
    public static void InitRecognizer(final String gameObjName,final String[] appKeys) {
        UnityPlayer.currentActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isInited) {
                    return;
                }
                appId = appKeys[0];
                appKey = appKeys[1];
                keySecret = appKeys[2];
                callBackObjName = gameObjName;
                isInited = true;
                mEventListener = new BaiduEventListener();
                asr = EventManagerFactory.create(UnityPlayer.currentActivity, "asr");
                asr.registerListener(mEventListener); //  EventListener 中 onEvent方法

                if (enableOffline) {
                    loadOfflineEngine(); //测试离线命令词请开启, 测试 ASR_OFFLINE_ENGINE_GRAMMER_FILE_PATH 参数时开启
                }
            }
        });

    }

    public static void BeginRecognizer(final String language) {
        UnityPlayer.currentActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Map<String, Object> params = new LinkedHashMap<String, Object>();
                String event = null;
                event = SpeechConstant.ASR_START; // 替换成测试的event

                params.put(SpeechConstant.APP_ID,appId);
                params.put(SpeechConstant.APP_KEY,appKey);
                params.put(SpeechConstant.SECRET,keySecret);

                params.put(SpeechConstant.ACCEPT_AUDIO_VOLUME, false);
                params.put(SpeechConstant.VAD, SpeechConstant.VAD_DNN);
                if (enableOffline) {
                    params.put(SpeechConstant.DECODER, 2);
                }
                params.put(SpeechConstant.PID,language);
                if(language.equals("1536")){
                    params.put(SpeechConstant.LANGUAGE, "cmn-Hans-CN");
                }
                else
                {
                    params.put(SpeechConstant.LANGUAGE, "en-GB");
                }
                //  params.put(SpeechConstant.NLU, "enable");
                // params.put(SpeechConstant.VAD_ENDPOINT_TIMEOUT, 800);
                // params.put(SpeechConstant.VAD, SpeechConstant.VAD_DNN);
                //  params.put(SpeechConstant.PROP ,20000);
                String json = null; //可以替换成自己的json
                json = new JSONObject(params).toString(); // 这里可以替换成你需要测试的json
                asr.send(event, json, null, 0, 0);
            }
        });
    }

    /**
     * 取消本次识别，取消后将立即停止不会返回识别结果。
     * cancel 与stop的区别是 cancel在stop的基础上，完全停止整个识别流程，
     */
    public static void CancelRecognizer() {
        UnityPlayer.currentActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                printLog("cancel");
                if (asr != null) {
                    asr.send(SpeechConstant.ASR_CANCEL, "{}", null, 0, 0);
                }
            }
        });

    }

    public static void StopRecognizer() {
        UnityPlayer.currentActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                asr.send(SpeechConstant.ASR_STOP, null, null, 0, 0); //
            }
        });

    }

    public static void ReleaseRecognizer() {
        UnityPlayer.currentActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (asr == null) {
                    return;
                }
                CancelRecognizer();
                if (enableOffline) {
                    asr.send(SpeechConstant.ASR_KWS_UNLOAD_ENGINE, null, null, 0, 0);
                    enableOffline = false;
                }
                asr.unregisterListener(mEventListener);
                asr = null;
                isInited = false;
            }
        });

    }

    private static void loadOfflineEngine() {
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        params.put(SpeechConstant.DECODER, 2);
        params.put(SpeechConstant.ASR_OFFLINE_ENGINE_GRAMMER_FILE_PATH, "assets://baidu_speech_grammar.bsg");
        asr.send(SpeechConstant.ASR_KWS_LOAD_ENGINE, new JSONObject(params).toString(), null, 0, 0);
    }

    private static void printLog(String text) {
        if (logTime) {
            text += "  ;time=" + System.currentTimeMillis();
        }
        text += "\n";
        Logger.log(text);
    }

}