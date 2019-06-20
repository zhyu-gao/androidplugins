using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ShareWrapper : MonoBehaviour
{
    private static AndroidJavaClass shareWrapper;
    /// <summary>
    /// 初始化分享平台的key
    /// </summary>
    /// <param name="keys">0、微信 1、QQ 2、微博</param>
    public static void InitPlatformKey(string[] keys)
    {
        shareWrapper = new AndroidJavaClass("com.newvison.share.wrapper.ShareWrapper");
        shareWrapper.CallStatic("InitSharePlatform", new object[] { keys });
    }

    /// <summary>
    /// 分享内容，分享前需要调用 InitPlatformKey
    /// </summary>
    /// <param name="content">分享内容</param>
    /// <param name="type">分享的类型 0，news 1、image</param>
    /// <param name="platform">分享平台 0、wechat friend 1、wechatmoment 2、QQ 3、weibo</param>
    ///        ShareContent content = new ShareContent();
    ///        content.path = "path";
    ///        content.title = "123";
    ///        content.description = "123";
    ///        content.url = "http://www.baidu.com";
    ///        ShareWrapper.ShareTo(content, 0, 0);
    public static void ShareTo(ShareContent content, int type, int platform)
    {
        if (shareWrapper == null)
            Debug.LogError("please init share first");
        string contents = JsonUtility.ToJson(content);
        shareWrapper.CallStatic("ShareContent", new object[] { contents, type, platform });
    }
    public static void ShareTo(ShareContent content)
    {
        if (shareWrapper == null)
            Debug.LogError("please init share first");
        string contents = JsonUtility.ToJson(content);
        shareWrapper.CallStatic("ShareContent", new object[] { contents, (int)content.shareType, (int)content.plat });
    }
    /// <summary>
    /// 用微信登入
    /// </summary>
    public static void LoginWithWechat()
    {
        if (shareWrapper == null)
            Debug.LogError("please init share first");
        shareWrapper.CallStatic("LoginWithWechat");
    }
    /// <summary>
    /// 检测微信客户端是否安装
    /// </summary>
    /// <returns></returns>
    public static bool isWXInstalled()
    {
        if (shareWrapper == null)
            Debug.LogError("please init share first");
        return shareWrapper.CallStatic<bool>("isWXAppInstalled");
    }

    /// <summary>
    /// 回调方法参数
    /// </summary>
    /// <param name="gameObjectName">脚本挂载的物体，默认为“Main Camera”</param>
    /// <param name="methodName">回调方法，默认“ThirdPluginsCallBack”</param>
    public static void CallBackParms(string gameObjectName, string methodName)
    {
        shareWrapper.CallStatic("CallBackMethod", new object[] { gameObjectName, methodName });
    }

    /// <summary>
    /// 回调方法，可以根据 CallBackParms 放在其他对应的位置
    /// </summary>
    /// <param name="callbackJson"></param>
    public void ThirdPluginsCallBack(string callbackJson)
    {
        ErrorCode callbackCode = JsonUtility.FromJson<ErrorCode>(callbackJson);
        switch (callbackCode.errorCode)
        {
            case 0:
                if (callbackCode.loginCode != null)
                {

                }
                else
                {

                }
                break;
            case -1:
                break;
            case -2:
                break;
            case -3:
                break;
        }
    }

}
public class ShareContent
{
    public string path;
    public string title;
    public string description;
    public string url;
    public ShareType shareType;
    public SharePlatform plat;
}
public class ErrorCode
{
    public int errorCode;
    public string msg;
    public string loginCode;
}
public enum ShareType
{
    News = 0,
    Image = 1
}
public enum SharePlatform
{
    WechatFried = 0,
    WechatMoment = 1,
    QQ = 2,
    Weibo = 3
}