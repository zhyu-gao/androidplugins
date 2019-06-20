package com.newvision.share;

public class Constants
{
	public static String WECHATAPP_ID = "wx031f3469ef4d952b";
	public static String QQAPP_ID;
	public static String WEIBOAPP_ID;
    public static String GAMEOBJECT_NAME = "Main Camera";
    public static String METHOD_NAME = "ThirdPluginsCallBack";
	/** 当前 DEMO 应用�? APP_KEY，第三方应用应该使用自己�? APP_KEY 替换�? APP_KEY */
   // public static final String APP_KEY      = "2045436852";

    /** 
     * 当前 DEMO 应用的回调页，第三方应用可以使用自己的回调页�?
     * 
     * <p>
     * 注：关于授权回调页对移动客户端应用来说对用户是不可见的，�?以定义为何种形式都将不影响，
     * 但是没有定义将无法使�? SDK 认证登录�?
     * 建议使用默认回调页：https://api.weibo.com/oauth2/default.html
     * </p>
     */
    public static final String REDIRECT_URL = "http://www.sina.com";

    /**
     * Scope �? OAuth2.0 授权机制�? authorize 接口的一个参数�?��?�过 Scope，平台将�?放更多的微博
     * 核心功能给开发�?�，同时也加强用户隐私保护，提升了用户体验，用户在新 OAuth2.0 授权页中有权�?
     * 选择赋予应用的功能�??
     * 
     * 我们通过新浪微博�?放平�?-->管理中心-->我的应用-->接口管理处，能看到我们目前已有哪些接口的
     * 使用权限，高级权限需要进行申请�??
     * 
     * 目前 Scope 支持传入多个 Scope 权限，用逗号分隔�?
     * 
     * 有关哪些 OpenAPI �?要权限申请，请查看：http://open.weibo.com/wiki/%E5%BE%AE%E5%8D%9AAPI
     * 关于 Scope 概念及注意事项，请查看：http://open.weibo.com/wiki/Scope
     */
    public static final String SCOPE = 
            "email,direct_messages_read,direct_messages_write,"
            + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
            + "follow_app_official_microblog," + "invitation_write";
    public static boolean isDebugMode = false;
}
