package com.deeshop.login;

import android.databinding.DataBindingUtil;
import android.view.View;
import android.widget.Toast;

import com.deeshop.R;
import com.base.BaseActivity;
import com.deeshop.bean.Password;
import com.deeshop.bean.User;
import com.deeshop.databinding.ActivityPwdBinding;
import com.deeshop.manager.LoginManager;
import com.deeshop.util.Constant;
import com.deeshop.util.GsonUtils;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import static com.deeshop.manager.LoginManager.getUserSharedPreferences;

/**
 * Created by zhiPeng.S on 2017/3/15.
 */

public class PwdActivity extends BaseActivity {
    private ActivityPwdBinding binding;
    private Password password = new Password();
    private LoginManager.VerifyTimer timer;
    private boolean isRegister;

    @Override
    protected void initData() {
        super.initData();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pwd);
        binding.setPwdActivity(this);
        binding.setPwd(password);

        timer = new LoginManager.VerifyTimer(60*1000,1000);
        timer.setButton(binding.pwdGetVerifyTv);

        String phone = getIntent().getStringExtra("phone");
        isRegister = getIntent().getBooleanExtra("register",false);
        password.phoneNum.set(phone);
    }

    @Override
    protected void initActionbar() {
        super.initActionbar();
        String title = isRegister ? getString(R.string.set_password) : getString(R.string.reset_password);
        binding.include.titleTv.setText(title);
        binding.include.backIv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.back_iv:
                finish();
                break;
            case R.id.pwd_btn:
                commitInfo();
                break;
            case R.id.pwd_get_verify_tv:
                obtainVerifyCode();
                break;
            case R.id.pwd_clear_phone_iv:
                binding.pwdPhoneEt.setText("");
                break;
            case R.id.pwd_pwd_et:
                binding.pwdPwdEt.setText("");
                break;
        }
    }

    private void commitInfo(){
        String url = "Account/UpdatePassword.ashx";
        if(isRegister)
            url = "Account/Register.ashx";
        RequestParams params = new RequestParams(Constant.getBaseUrl() + url);
        params.addBodyParameter("mobile",password.phoneNum.get());
        params.addBodyParameter("Password",password.password.get());
        params.addBodyParameter("verifycode",password.verifyCode.get());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LoginManager.getInstance().loginFinish(result);
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

    private void obtainVerifyCode(){
        timer.start();
        RequestParams params = new RequestParams(Constant.getBaseUrl() + "Account/PSWVerify.ashx");
        if(isRegister)
            params.addBodyParameter("verifytype","register");
        else
            params.addBodyParameter("verifytype","password");
        params.addBodyParameter("mobile",password.phoneNum.get());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

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
