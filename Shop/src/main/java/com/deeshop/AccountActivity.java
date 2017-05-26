package com.deeshop;

import android.content.Intent;
import android.content.pm.ProviderInfo;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;

import com.base.BaseActivity;
import com.deeshop.bean.Modify;
import com.deeshop.bean.User;
import com.deeshop.databinding.ActivityAccountBinding;
import com.deeshop.gadget.PopPhoto;
import com.deeshop.manager.LoginManager;
import com.deeshop.manager.PhotoManager;
import com.deeshop.util.Constant;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

import static com.deeshop.manager.PhotoManager.REQUESTCODE_CUTTING;
import static com.deeshop.manager.PhotoManager.REQUESTCODE_PICK;
import static com.deeshop.manager.PhotoManager.REQUESTCODE_TAKE;

/**
 * Created by zhiPeng.S on 2017/3/17.
 */

public class AccountActivity extends BaseActivity implements LoginManager.Callback,PhotoManager.Callback{
    private ActivityAccountBinding binding;
    private User.Info user;
    private final int REQUEST_MODIFY = 1 << 2;
    private PopPhoto menuWindow;

    @Override
    protected void initData() {
        super.initData();
        binding = DataBindingUtil.setContentView(this,R.layout.activity_account);
        binding.setActivity(this);
        user = LoginManager.user(this);
        binding.setUser(user);
        menuWindow = new PopPhoto(this);
    }

    @Override
    protected void initActionbar() {
        super.initActionbar();
        binding.include.titleTv.setText(R.string.account);
        binding.include.backIv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.back_iv:
                finish();
                break;
            case R.id.account_avatar:
                menuWindow.showAtLocation(binding.accountAvatar, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.account_nickname_ll:
                intent.setClass(this, ModifyActivity.class);
                intent.putExtra("userInfo","");
                Modify modify = new Modify();
                modify.title.set(getString(R.string.nickname));
                modify.content.set(user.nickname.get());
                modify.type.set("Name");
                modify.id.set(user.userid.get());
                intent.putExtra("info",modify);
                startActivityForResult(intent,REQUEST_MODIFY);
                break;
            case R.id.account_pwd_ll:
                LoginManager.getInstance().goPwd(this,user.mobile.get(),false);
                break;
            case R.id.account_logout_btn:
                LoginManager.getInstance().logout(this);
                break;
        }
    }

    @Override
    public void info(User.Info info) {
        if(info != null) user = info;
        binding.setUser(user);
    }

    @Override
    public void photo(String path) {
        user.headimgurl.set(path);
        PhotoManager.getInstance().uploadAvatar(path,true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUESTCODE_PICK:// 直接从相册获取
                try {
                    PhotoManager.getInstance().startPhotoZoom(data.getData(),this);
                } catch (NullPointerException e) {
                    e.printStackTrace();// 用户点击取消操作
                }
                break;
            case REQUESTCODE_TAKE:// 调用相机拍照
                PhotoManager.getInstance().startPhotoZoom(Uri.fromFile(new File(PhotoManager.IMAGE_FILE_PATH)),this);
                break;
            case REQUESTCODE_CUTTING:// 取得裁剪后的图片
                PhotoManager.getInstance().setPicToView(data,this,this);
                break;
            case REQUEST_MODIFY:
                LoginManager.getInstance().updateUser(this);
                break;
        }

    }

}
