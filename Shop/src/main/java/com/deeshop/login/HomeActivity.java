package com.deeshop.login;

import android.databinding.DataBindingUtil;
import android.view.View;

import com.deeshop.R;
import com.base.BaseActivity;
import com.deeshop.databinding.ActivityHomeBinding;
import com.deeshop.manager.LoginManager;

/**
 * Created by zhiPeng.S on 2017/3/16.
 */

public class HomeActivity extends BaseActivity {
    private ActivityHomeBinding homeBinding;

    @Override
    protected void initData() {
        super.initData();
        homeBinding = DataBindingUtil.setContentView(this,R.layout.activity_home);
        homeBinding.setHomeActivity(this);
        if (LoginManager.getInstance().checkLogin()) LoginManager.getInstance().goMain();
        LoginManager.logFirst(this);
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.home_register_btn:
                LoginManager.getInstance().goRegister(this);
                break;
            case R.id.home_login_btn:
                LoginManager.getInstance().goLogin(this);
                break;
        }
    }


}
