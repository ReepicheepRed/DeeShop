package com.deeshop.login;

import android.databinding.DataBindingUtil;
import android.view.View;
import android.widget.Toast;

import com.deeshop.R;
import com.base.BaseActivity;
import com.deeshop.bean.Login;
import com.deeshop.databinding.ActivityLoginBinding;
import com.deeshop.helper.DialogHelper;
import com.deeshop.manager.LoginManager;


/**
 * Created by zhiPeng.S on 2017/3/15.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener{
    private ActivityLoginBinding loginBinding;
    private Login login = new Login();
    @Override
    protected void initData() {
        super.initData();
        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        loginBinding.setLoginActivity(this);
        loginBinding.setLogin(login);
    }

    @Override
    protected void initActionbar() {
        super.initActionbar();
        loginBinding.includeLogin.backIv.setOnClickListener(this);
        loginBinding.includeLogin.titleTv.setText(R.string.login);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.back_iv:
                finish();
                break;
            case R.id.login_btn:
                LoginManager.getInstance().login(login.phoneNum.get(),login.password.get());
                showProgressDialog("login...",true,null);
                break;
            case R.id.login_forget_pwd_tv:
                String phone = login.phoneNum.get() != null ? login.phoneNum.get() : "";
                if(phone.equals("")){
                    toast(getString(R.string.phone_tip), Toast.LENGTH_SHORT);
                    return;
                }
                LoginManager.getInstance().goPwd(this,phone,false);
                break;
            case R.id.login_clear_phone_iv:
                loginBinding.loginPhoneEt.setText("");
                break;
            case R.id.login_clear_pwd_iv:
                loginBinding.loginPwdEt.setText("");
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        dismissProgressDialog();
    }
}
