package com.deeshop.login;

import android.databinding.DataBindingUtil;
import android.text.Html;
import android.view.View;

import com.deeshop.R;
import com.base.BaseActivity;
import com.deeshop.bean.Register;
import com.deeshop.bean.User;
import com.deeshop.databinding.ActivityRegisterBinding;
import com.deeshop.manager.LoginManager;
import com.deeshop.manager.WebManager;

/**
 * Created by zhiPeng.S on 2017/3/15.
 */

public class RegisterActivity extends BaseActivity implements LoginManager.Callback{
    private ActivityRegisterBinding binding;
    private Register register = new Register();

    @Override
    protected void initData() {
        super.initData();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        binding.setRegisterActivity(this);
        binding.setRegister(register);
        binding.registerProtocolTv.setText(Html.fromHtml(getString(R.string.protocol_tip)));
    }

    @Override
    protected void initActionbar() {
        super.initActionbar();
        binding.include.titleTv.setText(R.string.register_account);
        binding.include.backIv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.back_iv:
                finish();
                break;
            case R.id.register_btn:
                LoginManager.getInstance().checkRegister(register.phoneNum.get(),this);
                break;
            case R.id.register_protocol_tv:
                WebManager.getInstance().goWeb(this,WebManager.SERVICE);
                break;
            case R.id.register_clear_phone_iv:
                binding.registerPhoneEt.setText("");
                break;
        }
    }

    @Override
    public void info(User.Info info) {
        LoginManager.getInstance().goPwd(this,register.phoneNum.get(),true);
    }
}
