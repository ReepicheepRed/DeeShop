package com.deeshop.manager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.deeshop.CreateShopActivity;
import com.base.BaseActivity;
import com.deeshop.MainActivity;
import com.deeshop.R;
import com.deeshop.bean.User;
import com.deeshop.guide.GuideActivity;
import com.deeshop.helper.DialogHelper;
import com.deeshop.login.HomeActivity;
import com.deeshop.login.LoginActivity;
import com.deeshop.login.PwdActivity;
import com.deeshop.login.RegisterActivity;
import com.deeshop.util.Constant;
import com.deeshop.util.GsonUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.http.cookie.DbCookieStore;
import org.xutils.x;

import java.net.HttpCookie;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhiPeng.S on 2017/3/15.
 */

public class LoginManager {
    private final String TAG = getClass().getSimpleName();

    private LoginManager() {

    }
    private static volatile LoginManager instance;

    public static LoginManager getInstance() {
        if (instance == null) {
            synchronized (LoginManager.class) {
                if (instance == null) {
                    instance = new LoginManager();
                }
            }
        }
        return instance;
    }

    public interface Callback {
        void info(User.Info info);
    }

    public void goRegister(Context activity){
        Intent intent = new Intent(activity, RegisterActivity.class);
        activity.startActivity(intent);
    }

    public void goLogin(Context activity){
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
    }

    public void goPwd(Context activity,String phone,boolean isRegister){
        Intent intent = new Intent(activity, PwdActivity.class);
        intent.putExtra("phone",phone);
        intent.putExtra("register",isRegister);
        activity.startActivity(intent);
    }

    public void initShop(){
        Intent intent = new Intent(x.app(), CreateShopActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        x.app().startActivity(intent);
    }

    public void goMain(){
        Intent intent = new Intent(x.app(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        x.app().startActivity(intent);
    }

    public void goHome(){
        Intent intent = new Intent(x.app(), HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        x.app().startActivity(intent);
    }

    public void goGuide(){
        Intent intent = new Intent(x.app(), GuideActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        x.app().startActivity(intent);
    }

    public void login(CharSequence account,CharSequence pwd){
        RequestParams params = new RequestParams(Constant.getBaseUrl() + "Account/Login.ashx");
        params.addBodyParameter("mobile",account.toString());
        params.addBodyParameter("password",pwd.toString());
        params.addBodyParameter("cid",PushManager.getInstance().cid(x.app()));
        x.http().post(params, new org.xutils.common.Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                loginFinish(result);
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

    public void loginFinish(String result){
        saveCookie();
        User user = saveUser(result);
        if(user.memberinfo.shop.get().equals("true"))
            goMain();
        else
            initShop();
    }

    public void updateUser(Callback callback){
        RequestParams params = new RequestParams(Constant.getBaseUrl() + "Account/UserDetial.ashx");
        params.addQueryStringParameter("system","2");
        params.addQueryStringParameter("userid",user(x.app()).userid.get());
        LoginManager.addToken(params);
        x.http().get(params, new org.xutils.common.Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                saveCookie();
                User user = saveUser(result);
                callback.info(user.memberinfo);
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

    public User saveUser(String result){
        Gson gson = GsonUtils.gson();
        User user = gson.fromJson(result,User.class);
        if(user.returncode.get().equals(Constant.ERROR)){
            Toast.makeText(x.app(),user.msg.get(),Toast.LENGTH_SHORT).show();
            return user;
        }
        String userInfo = gson.toJson(user.memberinfo);
        getUserSharedPreferences(x.app()).edit()
                .putString(Constant.USER,userInfo)
                .apply();
        return user;
    }

    public static class VerifyTimer extends CountDownTimer {
        private String verifyCode;
        private String verifyTimer;
        private TextView verify_code_btn;

        public VerifyTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            verifyCode = x.app().getString(R.string.get_verify);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            verifyTimer = "(" + millisUntilFinished/1000 + ")";
            verify_code_btn.setText(verifyTimer);
        }

        @Override
        public void onFinish() {
            this.cancel();
            verify_code_btn.setText(verifyCode);
            verify_code_btn.setEnabled(true);
        }

        public void setButton(TextView verify_code_btn) {
            this.verify_code_btn = verify_code_btn;
        }
    }


    public static SharedPreferences getAppSharedPreferences(Context context, String name) {
        if (context == null || TextUtils.isEmpty(name)) {
            return null;
        }
        return context.getSharedPreferences(name, Context.MODE_APPEND);
    }

    public static SharedPreferences getUserSharedPreferences(Context context) {
        return getAppSharedPreferences(context, Constant.USER_PREFERENCES_NAME);
    }

    public static User.Info user(Context context) {
        String userInfo = LoginManager.getUserSharedPreferences(context).getString(Constant.USER,"");
        return GsonUtils.gson().fromJson(userInfo, User.Info.class);
    }

    public static boolean isFirst(Context context) {
        return getAppSharedPreferences(context, Constant.PREFERENCES_LOGIN_FIRST)
                .getBoolean("isFirst", true);
    }

    public static void logFirst(Context context) {
         getAppSharedPreferences(context, Constant.PREFERENCES_LOGIN_FIRST).edit()
                 .putBoolean("isFirst", false).apply();
    }

    public static boolean addToken(RequestParams params){
        Context context = x.app();
        try{
            SharedPreferences preferences = getUserSharedPreferences(context);
            params.addHeader("userid", user(context).userid.get());
            params.addHeader(Constant.ACCESS_TOKEN,preferences.getString(Constant.ACCESS_TOKEN,""));
            params.addHeader(Constant.REFRESH_TOKEN,preferences.getString(Constant.REFRESH_TOKEN,""));
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }


    public static boolean saveCookie(){
        Context context = x.app();
        try{
            SharedPreferences preferences = getUserSharedPreferences(context);
            SharedPreferences.Editor editor = preferences.edit();
            // 保存cookie的值
            DbCookieStore instance = DbCookieStore.INSTANCE;
            List<HttpCookie> cookies = instance.getCookies();
            String cookieValue = "";
            for (int i = 0; i < cookies.size(); i++) {
                HttpCookie cookie = cookies.get(i);
                if (cookie.getName() != null&&cookie.getName().equals(Constant.COOKIE_NAME)) {
                    cookieValue = cookie.getValue();
                }
                Log.i("TokenVerify" , ": cookie name --> " + cookie.getName());
                Log.i("TokenVerify" , ": cookie value --> " + cookie.getValue());
            }

            Map<String,String> map = new HashMap<>();
            String[] value = cookieValue.split("&");
            for (int i = 0; i < value.length; i++) {
                String[] valueChild = value[i].split("=");
                map.put(valueChild[0],valueChild[1]);
            }
            if (map.containsKey(Constant.ACCESS_TOKEN)) {
                editor.putString(Constant.ACCESS_TOKEN, map.get(Constant.ACCESS_TOKEN));
            }

            if (map.containsKey(Constant.REFRESH_TOKEN)) {
                editor.putString(Constant.REFRESH_TOKEN, map.get(Constant.REFRESH_TOKEN));
            }
            editor.apply();
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean checkLogin(){
        try{
            String userId = user(x.app()).userid.get();
            if(userId != null && !userId.equals("")){
                goMain();
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public void logout(Context activity){
        getUserSharedPreferences(x.app()).edit().clear().apply();
        Intent intent = new Intent(activity,LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    public void checkRegister(String phone, Callback callback){
        RequestParams params = new RequestParams(Constant.getBaseUrl() + "Account/MobileValid.ashx");
        params.addQueryStringParameter("mobile",phone);
        x.http().get(params, new org.xutils.common.Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                JsonObject json = new JsonParser().parse(result).getAsJsonObject();
                if(json.get("returncode").getAsString().equals(Constant.ERROR)){
                    DialogHelper.toast(json.get("msg").getAsString());
                    return;
                }
                callback.info(null);// null stands for phone number isn't register
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
