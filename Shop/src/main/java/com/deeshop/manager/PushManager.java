package com.deeshop.manager;

import android.content.Context;

/**
 * Created by zhiPeng.S on 2017/5/2.
 */

public class PushManager {
    public static final String CID = "cid";

    private PushManager() {

    }
    private static volatile PushManager instance;

    public static PushManager getInstance() {
        if (instance == null) {
            synchronized (PushManager.class) {
                if (instance == null) {
                    instance = new PushManager();
                }
            }
        }
        return instance;
    }

    public void saveCid(Context context,String cid){
        LoginManager.getAppSharedPreferences(context,CID).edit().putString(CID,cid).apply();
    }

    public String cid(Context context){
        return LoginManager.getAppSharedPreferences(context,CID).getString(CID,"");
    }
}
