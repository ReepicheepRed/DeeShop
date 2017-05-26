package com.deeshop.manager;

import android.app.ProgressDialog;

import com.deeshop.R;
import com.deeshop.helper.DialogHelper;
import com.deeshop.util.Constant;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by zhiPeng.S on 2017/4/12.
 */

public class NetManager {
    public final static int DELETE = 1;
    public final static int ON_SALE = 1 << 1;
    public final static int OFF_SHELVES = 1 << 2;
    public final static int DETAIL = 1 << 3;

    public interface Callback {
        void update(int what,String result);
    }
    public static void commitDelete(String goodId,Callback callback){
        RequestParams params = new RequestParams(Constant.getBaseUrl() + "Shop/UpdateGood.ashx");
        params.addQueryStringParameter("goodid", goodId);
        params.addQueryStringParameter("modify","delete");
        params.addQueryStringParameter("content","1");
        LoginManager.addToken(params);
        x.http().get(params, new org.xutils.common.Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LoginManager.saveCookie();
                JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();
                String code = jsonObject.get("returncode").getAsString();
                if(code.equals(Constant.SUCCESS)){
                    callback.update(DELETE,result);
                    DialogHelper.toast(x.app().getString(R.string.delete_success));
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    public static void obtainDetailInfo(String userId,String goodId,Callback callback){
        RequestParams params = new RequestParams(Constant.getBaseUrl() + "Good/GoodDetial.ashx");
        params.addQueryStringParameter("userid",userId);
        params.addQueryStringParameter("goodid",goodId);
        x.http().get(params, new org.xutils.common.Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                callback.update(DETAIL,result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    public static void commitSaleOrSoldOut(String userId,String goodId,boolean isSale,Callback callback){
        // 0 shelves ,other sold out
        String url = isSale ? "Good/GetGood.ashx" : "Good/ShelfGood.ashx";
        RequestParams params = new RequestParams(Constant.getBaseUrl() + url);
        if(isSale)
            params.addBodyParameter("userid",userId);
        params.addBodyParameter("goodid",goodId);
        LoginManager.addToken(params);
        x.http().post(params, new org.xutils.common.Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if(isSale){
                    callback.update(ON_SALE,result);
                    DialogHelper.toast(x.app().getString(R.string.on_sale_success));
                }
                else{
                    callback.update(OFF_SHELVES,result);
                    DialogHelper.toast(x.app().getString(R.string.off_shelves_success));
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}
