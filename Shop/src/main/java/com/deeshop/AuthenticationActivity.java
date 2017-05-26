package com.deeshop;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.base.BaseActivity;
import com.deeshop.bean.Auth;
import com.deeshop.databinding.ActivityAuthenticationBinding;
import com.deeshop.gadget.PopPhoto;
import com.deeshop.manager.LoginManager;
import com.deeshop.manager.PhotoManager;
import com.deeshop.util.Constant;
import com.deeshop.util.ImageUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

/**
 * Created by zhiPeng.S on 2017/3/17.
 */

public class AuthenticationActivity extends BaseActivity implements View.OnClickListener,PhotoManager.Callback{
    private ActivityAuthenticationBinding binding;
    private Auth auth = new Auth();
    private PopPhoto menuWindow;
    private String shopId,userId;
    @Override
    protected void initData() {
        super.initData();
        binding = DataBindingUtil.setContentView(this,R.layout.activity_authentication);
        binding.setActivity(this);
        binding.setAuth(auth);

        menuWindow = new PopPhoto(this, this);

        String defaultPath = ImageUtils.getDrawablePath(R.mipmap.shenfenzhengshili_img);
        String authImg = getIntent().hasExtra("img") ? getIntent().getStringExtra("img") : "";
        auth.enabled.set(!authImg.equals(""));
        String path =  auth.enabled.get() ? authImg : defaultPath;
        shopId = getIntent().hasExtra("id")? getIntent().getStringExtra("id") : "";
        userId = LoginManager.user(this).userid.get();
        auth.url.set(path);

    }

    @Override
    protected void initActionbar() {
        super.initActionbar();
        binding.include.titleTv.setText(R.string.authentication);
        binding.include.backIv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if(menuWindow.isShowing()) menuWindow.dismiss();
        switch (view.getId()){
            case R.id.back_iv:
                finish();
                break;
            case R.id.auth_id_card_iv:
            case R.id.auth_add_picture_iv:
                menuWindow.showAtLocation(binding.authIdCardIv, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.auth_upload_btn:
                commitIdCard();
                break;
            //为弹出窗口实现点击事件
            case R.id.pop_take_photo_tv:// 拍照
                PhotoManager.getInstance().takePhoto(this);
                break;
            case R.id.pop_album_tv:// 相册选择图片
                PhotoManager.getInstance().pickPhoto(this);
                break;
            case R.id.pop_cancel_tv:// 取消
                break;
        }
    }

    @Override
    public void photo(String path) {
        auth.url.set(path);
        auth.enabled.set(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case PhotoManager.SELECT_PIC_BY_TACK_PHOTO:
            case PhotoManager.SELECT_PIC_BY_PICK_PHOTO:
                PhotoManager.getInstance().doPhoto(requestCode,data,this,this);
                break;
        }
    }

    private void commitIdCard(){
        RequestParams params = new RequestParams(Constant.getBaseUrl() + "Shop/ShopAuthImg.ashx");
        params.addBodyParameter("shopid",shopId);
        params.addBodyParameter("userid",userId);
        params.addBodyParameter("file",new File(auth.url.get()),null);
        LoginManager.addToken(params);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LoginManager.saveCookie();
                auth.enabled.set(false);
                toast("upload success", Toast.LENGTH_SHORT);
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
