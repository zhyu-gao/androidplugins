package com.newvision.share;

/*
 * unity调用Android�?要的�?
 * unity调用此类的中的方�?
 */
public class ShareNativeUtils
{
	/*
	 * 初始化各个平台的id，要在分享之前赋�?
	 */

	public static void InitSharePlatformId(String[] PlatformIds)
	{
		Constants.WECHATAPP_ID = PlatformIds[0];
		Constants.QQAPP_ID = PlatformIds[1];
		Constants.WEIBOAPP_ID = PlatformIds[2];
	}

	/*
	 * 此处预留，好的做法是就用�?个方法然后统�?用unity调用，但现在分享平台比较少， 每个都用�?个方法这样不容易出现错误，哪个有问题也好�?
	 * whichway ,false:image;true:tuwen
	 */

	public static void ShareContents(String[] contents, Boolean whichway,
			int platformid)
	{
		switch (platformid)
		{
			case 0:
				WechatShareUtils shareFriend = new WechatShareUtils();
				if (whichway)
				{
					shareFriend.ShareTuwenToFriend(contents);
				}
				else
				{
					shareFriend.ShareImageToFriend(contents);
				}
				break;
			case 1:
				WechatShareUtils shareMoment = new WechatShareUtils();
				if (whichway)
				{
					shareMoment.ShareTuwenToMoment(contents);
				}
				else
				{
					shareMoment.ShareImageToMoment(contents);
				}
				break;
			default:
				break;
		}
	}

	/*
	 * 微信好友
	 */

	public static void WechatShareImageToFriend(String[] contents)
	{
		WechatShareUtils share = new WechatShareUtils();
		share.ShareImageToFriend(contents);
	}

	public static void WechatShareImageToMoment(String[] contents)
	{
		WechatShareUtils share = new WechatShareUtils();
		share.ShareImageToMoment(contents);
	}

	/*
	 * 朋友�?
	 */

	public static void WechatShareTuwenToFriend(final String[] contents)
	{
		WechatShareUtils share = new WechatShareUtils();
		share.ShareTuwenToFriend(contents);
	}

	public static void WechatShareTuwenToMoment(final String[] contents)
	{
		WechatShareUtils share = new WechatShareUtils();
		share.ShareTuwenToMoment(contents);
	}

	/*
	 * QQ
	 */

	public static void QQShareImage(String[] contents)
	{
		QQShareUtils qqshare = new QQShareUtils();
		qqshare.QQShareImage(contents);
	}

	public static void QQShareTuwen(String[] contents)
	{
		QQShareUtils qqshare = new QQShareUtils();
		qqshare.QQShareTuwen(contents);
	}

	/*
	 * QQ空间
	 */

	public static void QZoneShareTuwen(String[] contents)
	{
		QQShareUtils qqshare = new QQShareUtils();
		qqshare.QZoneShareTuwen(contents);
	}

	public static void QZoneShareImage(String[] contents)
	{
		QQShareUtils qqshare = new QQShareUtils();
		qqshare.QZoneShareImage(contents);
	}

	/*
	 * 新浪微博
	 */
	public static void WeiboShareImage(String[] contents)
	{
		WeiboShareUtils wbshare = new WeiboShareUtils();
		wbshare.WeiboShareImage(contents);
	}

	public static void WeiboShareTuwen(String[] contents)
	{
		WeiboShareUtils wbshare = new WeiboShareUtils();
		wbshare.WeiboShareTuwen(contents);
	}
}
