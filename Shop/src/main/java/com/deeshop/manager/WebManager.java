package com.deeshop.manager;

import android.content.Context;
import android.content.Intent;

import com.deeshop.WebActivity;
import com.deeshop.util.Constant;

/**
 * Created by zhiPeng.S on 2017/4/14.
 */

public class WebManager {
    public static final String WEB_H5= "web_h5";
    public static final String SHOP = Constant.getBaseUrl() + "GoodHander/shop_preview.aspx?shopid=";
    public static final String GOODS = Constant.getBaseUrl() + "GoodHander/product.aspx?goodid=";
    public static final String SERVICE = Constant.getBaseUrl() + "One2Sell%20Terms%20of%20service.html";
    public static final String ABOUT = Constant.getBaseUrl() + "Handler/aboutus/about.html";
    public static final String DOWNLOAD = Constant.getBaseUrl() + "GoodHander/download/%20download.html";

    private WebManager(){

    }

    private static volatile WebManager instance;

    public static WebManager getInstance() {
        if (instance == null) {
            synchronized (WebManager.class) {
                if (instance == null) {
                    instance = new WebManager();
                }
            }
        }
        return instance;
    }

    public void goWeb(Context context,String url){
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(WEB_H5,url);
        context.startActivity(intent);
    }
}
