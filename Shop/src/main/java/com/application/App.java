package com.application;

import android.app.Application;

import com.deeshop.push.PushService;
import com.deeshop.push.ReceiverService;
import com.deeshop.util.Constant;
import com.igexin.sdk.PushManager;

import org.xutils.BuildConfig;
import org.xutils.x;

import javax.net.ssl.HttpsURLConnection;

import cn.sharesdk.framework.ShareSDK;

/**
 * Created by zhiPeng.S on 2017/3/15.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);

        ShareSDK.initSDK(this);
        // com.getui.demo.DemoPushService 为第三方自定义推送服务
        PushManager.getInstance().initialize(this, PushService.class);
        // com.getui.demo.DemoIntentService 为第三方自定义的推送服务事件接收类
        PushManager.getInstance().registerPushIntentService(this, ReceiverService.class);


        // HostnameVerifier安全问题处理方式
        HttpsURLConnection.setDefaultHostnameVerifier((hostName, session) -> {
        // 只要这里不是直接 return true; google play就能校验通过
            return Constant.getBaseUrl().equals(hostName);
        });
    }
}
