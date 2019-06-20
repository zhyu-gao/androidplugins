package com.newvision.baiduspeech;

import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.unity3d.player.UnityPlayer;

/**
 * Created by gaozhenyu on 2017/11/27.
 */

public class BaiduSynthesizerCallBack implements SpeechSynthesizerListener {
    private static final String TAG = "BaiduSynthesizer";

    public void SendUnityMessage(String methodName, String message) {
        UnityPlayer.UnitySendMessage(BaiduSynthesizer.callBackObjName, methodName, message);
    }

    @Override
    public void onSynthesizeStart(String s) {
        Logger.log("语音合成开始");
    }

    @Override
    public void onSynthesizeDataArrived(String s, byte[] bytes, int i) {
        Logger.log("合成的语音" + s);
    }

    @Override
    public void onSynthesizeFinish(String s) {
        Logger.log("语音合成结束" + s);
    }

    @Override
    public void onSpeechStart(String s) {
        Logger.log("语音合成开始发声");
        SendUnityMessage("GetSynthesizerStateResult", "begin");
    }

    @Override
    public void onSpeechProgressChanged(String s, int i) {
        Logger.log("语音合成开始发声进度" + i);
    }

    @Override
    public void onSpeechFinish(String s) {
        Logger.log("语音合成结束读取");
        SendUnityMessage("GetSynthesizerStateResult", "complete");
    }

    @Override
    public void onError(String s, SpeechError speechError) {
        Logger.log("语音合成错误" + speechError.code);
        SendUnityMessage("GetSynthesizerStateResult", "error:{" + s + "}");
    }
}
