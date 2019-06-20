package com.newvision.share;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.constant.WBConstants;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.utils.Utility;
import com.unity3d.player.UnityPlayer;

public class WeiboShareUtils implements WeiboAuthListener,IWeiboHandler.Response
{
	private IWeiboShareAPI mWeiboShareAPI;

	public WeiboShareUtils()
	{
		if (mWeiboShareAPI == null)
		{
			mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(
					UnityPlayer.currentActivity, Constants.WEIBOAPP_ID);
		}
		mWeiboShareAPI.registerApp();
		boolean isInstalledWeibo = mWeiboShareAPI.isWeiboAppInstalled();
		if (!isInstalledWeibo)
		{
			NativeUtils.ShowMessage("请安装新浪微博客户端后再进行分享");
			return;
		}
	}

	public void WeiboShareImage(String[] contents)
	{
		WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
		TextObject tb = new TextObject();
		tb.text = "test";
		weiboMessage.mediaObject = tb;
		ImageObject iob = new ImageObject();
		Bitmap bitmap = BitmapFactory.decodeFile(contents[0]);
		iob.setImageObject(bitmap);
		weiboMessage.imageObject = iob;
		SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
		// 用transaction唯一标识�?个请�?
		request.transaction = String.valueOf(System.currentTimeMillis());
		request.multiMessage = weiboMessage;
		// 3. 发�?�请求消息到微博，唤起微博分享界�?
		mWeiboShareAPI.sendRequest(UnityPlayer.currentActivity, request);
	}

	public void WeiboShareTuwen(String[] contents)
	{
		WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
		TextObject tb = new TextObject();
		tb.text = contents[1];
		weiboMessage.textObject = tb;
		WebpageObject wo = new WebpageObject();
		wo.identify = Utility.generateGUID();
		wo.title = contents[1];
		wo.description = contents[2];
		//byte[] bytes = Util.getHtmlByteArray(contents[0]);
		Bitmap bitmap = Util.drawable2Bitmap(Util.GetIcon());
		wo.setThumbImage(bitmap);
		wo.actionUrl = contents[3];
		weiboMessage.mediaObject = wo;
		SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
		// 用transaction唯一标识�?个请�?
		request.transaction = String.valueOf(System.currentTimeMillis());
		request.multiMessage = weiboMessage;
		// 3. 发�?�请求消息到微博，唤起微博分享界�?
		mWeiboShareAPI.sendRequest(UnityPlayer.currentActivity, request);
	}

	@Override
	public void onCancel()
	{
		NativeUtils.ShowMessage("取消分享");
	}
	@Override
	public void onComplete(Bundle arg0)
	{
		NativeUtils.ShowMessage("分享成功");
	}
	@Override
	public void onWeiboException(WeiboException arg0)
	{
		NativeUtils.ShowMessage("error" + arg0);
	}

	@Override
	public void onResponse(BaseResponse baseResp)
	{
		// TODO Auto-generated method stub
		switch (baseResp.errCode) {
	        case WBConstants.ErrorCode.ERR_OK:
	        	NativeUtils.ShowMessage("分享成功");
	            break;
	        case WBConstants.ErrorCode.ERR_CANCEL:
	        	NativeUtils.ShowMessage("取消分享");
	            break;
	        case WBConstants.ErrorCode.ERR_FAIL:
	        	NativeUtils.ShowMessage("error" + baseResp);
	            break;
	        }
	}
}
