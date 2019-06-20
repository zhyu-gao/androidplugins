package com.newvision.baiduspeech;

import com.newvision.baiduspeech.recognization.IRecogListener;
import com.newvision.baiduspeech.recognization.RecogResult;
import com.unity3d.player.UnityPlayer;

/**
 * Created by gaozhenyu on 2017/11/27.
 */

public class BaiduRecogizationCallBack implements IRecogListener {
    public void SendUnityMessage(String methodName, String message) {
        UnityPlayer.UnitySendMessage(BaiduSpeechRecognizer.callBackObjName, methodName, message);
    }

    @Override
    public void onAsrReady() {
        Logger.log("语音识别开始!");
        SendUnityMessage("GetRecognizerStateResult", "begin");
    }

    @Override
    public void onAsrBegin() {
        Logger.log("开始检测到说话!");
    }

    @Override
    public void onAsrEnd() {
        Logger.log("说话结束!");
    }

    @Override
    public void onAsrPartialResult(String[] results, RecogResult recogResult) {
        Logger.log("当前检测到的说话内容---" + results[0]);
    }

    @Override
    public void onAsrFinalResult(String[] results, RecogResult recogResult) {
        Logger.log("最终说话的内容---" + results[0]);
        SendUnityMessage("GetRecognizerResult", results[0]);
    }

    @Override
    public void onAsrFinish(RecogResult recogResult) {
        SendUnityMessage("GetRecognizerStateResult", "complete");
    }

    @Override
    public void onAsrFinishError(int errorCode, int subErrorCode, String errorMessage, String descMessage, RecogResult recogResult) {
        Logger.log("error:---" + errorCode + errorMessage);
        SendUnityMessage("GetRecognizerStateResult", "error:{" + errorCode + "---" + errorMessage + "}");
    }

    @Override
    public void onAsrLongFinish() {

    }

    @Override
    public void onAsrVolume(int volumePercent, int volume) {

    }

    @Override
    public void onAsrAudio(byte[] data, int offset, int length) {

    }

    @Override
    public void onAsrExit() {
        Logger.log("退出本次识别");
    }

    @Override
    public void onAsrOnlineNluResult(String nluResult) {
        Logger.log("语义解析结果---" + nluResult);
    }

    @Override
    public void onOfflineLoaded() {

    }

    @Override
    public void onOfflineUnLoaded() {

    }
}
