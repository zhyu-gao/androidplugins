package com.newvision.share;

import android.graphics.Bitmap;
import android.widget.Toast;

import com.tencent.mm.sdk.openapi.BaseReq;
import com.tencent.mm.sdk.openapi.BaseResp;
import com.tencent.mm.sdk.openapi.ConstantsAPI;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXImageObject;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXWebpageObject;
import com.unity3d.player.UnityPlayer;

import java.io.File;

public class WechatShareUtils implements IWXAPIEventHandler
{
	private IWXAPI api;

	public WechatShareUtils()
	{
		if (api == null)
		{
			api = WXAPIFactory.createWXAPI(UnityPlayer.currentActivity,
					Constants.WECHATAPP_ID);
		}
		if (!api.isWXAppInstalled())
		{
			NativeUtils.ShowMessage("请安装微信后进行分享！");
			return;
		}
		api.registerApp(Constants.WECHATAPP_ID);
	}

	/*
	 * 微信分享图片形式
	 */
	public void ShareImageToFriend(String[] contents)
	{
		File file = new File(contents[0]);
		if (!file.exists())
		{
			Toast.makeText(UnityPlayer.currentActivity,
					"文件不存在" + " path = " + contents[0], Toast.LENGTH_LONG)
					.show();
			return;
		}
		WXImageObject imgObj = new WXImageObject();
		imgObj.setImagePath(contents[0]);
		WXMediaMessage msg = new WXMediaMessage();
		msg.mediaObject = imgObj;
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("img");
		req.message = msg;
		req.scene = SendMessageToWX.Req.WXSceneSession;
		api.sendReq(req);
	}

	public void ShareImageToMoment(String[] contents)
	{
		File file = new File(contents[0]);
		if (!file.exists())
		{
			Toast.makeText(UnityPlayer.currentActivity,
					"文件不存在" + " path = " + contents[0], Toast.LENGTH_LONG)
					.show();
			return;
		}
		WXImageObject imgObj = new WXImageObject();
		imgObj.setImagePath(contents[0]);
		WXMediaMessage msg = new WXMediaMessage();
		msg.mediaObject = imgObj;
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("img");
		req.message = msg;
		req.scene = SendMessageToWX.Req.WXSceneTimeline;
		api.sendReq(req);
	}

	public void ShareTuwenToFriend(String[] contents)
	{
		WXWebpageObject webpage = new WXWebpageObject();
		webpage.webpageUrl = contents[3];
		WXMediaMessage msg = new WXMediaMessage(webpage);
		msg.title = contents[1];
		msg.description = contents[2];
		Bitmap thumb;
		//byte[] bytes = Util.getHtmlByteArray(contents[0]);
		thumb = Util.drawable2Bitmap(Util.GetIcon());//BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
		msg.setThumbImage(thumb);
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("tuwen");
		req.message = msg;
		req.scene = SendMessageToWX.Req.WXSceneSession;
		api.sendReq(req);
	}

	public void ShareTuwenToMoment(String[] contents)
	{
		WXWebpageObject webpage = new WXWebpageObject();
		webpage.webpageUrl = contents[3];
		WXMediaMessage msg = new WXMediaMessage(webpage);
		msg.title = contents[1];
		msg.description = contents[2];
		Bitmap thumb;
		//byte[] bytes = Util.getHtmlByteArray(contents[0]);
		//thumb = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
		thumb = Util.drawable2Bitmap(Util.GetIcon());
		msg.setThumbImage(thumb);
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("tuwen");
		req.message = msg;
		req.scene = SendMessageToWX.Req.WXSceneTimeline;
		api.sendReq(req);
	}

	private String buildTransaction(final String type)
	{
		return (type == null) ? String.valueOf(System.currentTimeMillis())
				: type + System.currentTimeMillis();
	}

	@Override
	public void onReq(BaseReq req)
	{
		switch (req.getType())
		{
			case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
				break;
			case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
				break;
			default:
				break;
		}
	}

	@Override
	public void onResp(BaseResp resp)
	{
		switch (resp.errCode)
		{
			case BaseResp.ErrCode.ERR_OK:
				NativeUtils.ShowMessage("发送成功");
				break;
			case BaseResp.ErrCode.ERR_USER_CANCEL:
				NativeUtils.ShowMessage("发送取消");
				break;
			case BaseResp.ErrCode.ERR_AUTH_DENIED:
				NativeUtils.ShowMessage("发送被拒绝");
				break;
			default:
				NativeUtils.ShowMessage("发送返回");
				break;
		}
	}
}
