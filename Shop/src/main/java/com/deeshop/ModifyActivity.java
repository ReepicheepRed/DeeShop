package com.deeshop;

import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.base.BaseActivity;
import com.deeshop.bean.Modify;
import com.deeshop.databinding.ActivityModifyBinding;
import com.deeshop.manager.LoginManager;
import com.deeshop.util.Constant;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by zhiPeng.S on 2017/4/1.
 */

public class ModifyActivity extends BaseActivity implements View.OnClickListener{
    private ActivityModifyBinding binding;
    private Modify modify;
    private boolean isUser;
    private String userid;

    @Override
    protected void initData() {
        super.initData();
        binding = DataBindingUtil.setContentView(this,R.layout.activity_modify);
        binding.setActivity(this);
        userid = LoginManager.user(this).userid.get();

        modify = getIntent().hasExtra("info") ? (Modify) getIntent().getSerializableExtra("info") : new Modify();
        binding.setModify(modify);
        isUser = getIntent().hasExtra("userInfo");

    }

    @Override
    protected void initActionbar() {
        super.initActionbar();
        binding.include.titleTv.setText(getString(R.string.edit_nickname,modify.title.get()));
        binding.include.rightTv.setText(R.string.done);
        binding.include.backIv.setOnClickListener(this);
        binding.include.rightTv.setOnClickListener(this);
        modify.content.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                boolean enabled = modify.content.get().trim().length() > 0;
                binding.include.rightTv.setEnabled(enabled);
                if(enabled)
                    binding.include.rightTv.setTextColor(getResources().getColor(R.color.black_1_c));
                else
                    binding.include.rightTv.setTextColor(getResources().getColor(R.color.gray_a0_c));
            }
        });
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.back_iv:
                finish();
                break;
            case R.id.right_tv:
                commitInfo();
                break;
            case R.id.modify_clear_iv:
                modify.content.set("");
                break;
        }
    }

    public void afterTextChanged(Editable s) {
        binding.modifyEt.setSelection(s.length());
        if(s.length() > 0){
            binding.include.rightTv.setEnabled(true);
            binding.include.rightTv.setTextColor(getResources().getColor(R.color.black_1_c));
            return;
        }
        binding.include.rightTv.setEnabled(false);
        binding.include.rightTv.setTextColor(getResources().getColor(R.color.gray_a0_c));
    }

    private void commitInfo(){
        String url = isUser ? "Account/UserModify.ashx" : "Shop/UpdateShop.ashx";
        RequestParams params = new RequestParams(Constant.getBaseUrl() + url);
        if(!isUser)
         params.addBodyParameter("shopid",modify.id.get());
        params.addBodyParameter("userid",userid);
        params.addBodyParameter("modify",modify.type.get());
        params.addBodyParameter("content",modify.content.get());
        LoginManager.addToken(params);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LoginManager.saveCookie();
                toast("success", Toast.LENGTH_SHORT);
                finish();
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
