package com.deeshop;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.databinding.Observable.OnPropertyChangedCallback;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;

import com.base.BaseActivity;
import com.deeshop.bean.CShop;
import com.deeshop.databinding.ActivityCreateShopBinding;
import com.deeshop.gadget.PopPhoto;
import com.deeshop.manager.LoginManager;
import com.deeshop.manager.PhotoManager;
import com.deeshop.util.Constant;
import com.deeshop.util.GsonUtils;
import com.deeshop.util.ImageUtils;
import com.google.gson.Gson;

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

public class CreateShopActivity extends BaseActivity implements PhotoManager.Callback{
    private ActivityCreateShopBinding binding;
    private PopPhoto menuWindow;
    private CShop cShop = new CShop();
    private String userId,avatarPath = "";

    @Override
    protected void initData() {
        super.initData();
        binding = DataBindingUtil.setContentView(this,R.layout.activity_create_shop);
        binding.setActivity(this);
        binding.setCShop(cShop);
        cShop.avatar.set(ImageUtils.getDrawablePath(R.mipmap.btn_tianjiatouxiang));
        menuWindow = new PopPhoto(this, this);
        userId = LoginManager.user(this).userid.get();
    }

    @Override
    protected void initActionbar() {
        super.initActionbar();
        binding.includeCShop.rightTv.setText("Done");
        binding.includeCShop.rightTv.setOnClickListener(this);
        cShop.enabled.addOnPropertyChangedCallback(new OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                if (observable == cShop.enabled) {
                    binding.includeCShop.rightTv.setEnabled(cShop.enabled.get());
                    if(cShop.enabled.get())
                        binding.includeCShop.rightTv.setTextColor(getResources().getColor(R.color.black_1_c));
                    else
                        binding.includeCShop.rightTv.setTextColor(getResources().getColor(R.color.gray_a0_c));
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if(menuWindow.isShowing()) menuWindow.dismiss();
        switch (view.getId()){
            case R.id.c_shop_avatar_iv:
                menuWindow.showAtLocation(findViewById(R.id.activity_create_shop_cl),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.right_tv:
                commitShopInfo();
                break;
            //为弹出窗口实现点击事件
            case R.id.pop_take_photo_tv:// 拍照
                PhotoManager.getInstance().takeAvatarPhoto(CreateShopActivity.this);
                break;
            case R.id.pop_album_tv:// 相册选择图片
                PhotoManager.getInstance().pickAvatarPhoto(CreateShopActivity.this);
                break;
            case R.id.pop_cancel_tv:// 取消
                break;
        }
    }

    @Override
    public void photo(String path) {
        avatarPath = path;
    }

    private void commitShopInfo(){
        RequestParams params = new RequestParams(Constant.getBaseUrl() + "Shop/CreateShop.ashx");
        params.setMultipart(true);
        params.addBodyParameter("userid",userId);
        params.addBodyParameter("shopname",cShop.name.get());
        params.addBodyParameter("facebook",cShop.facebook.get());
        if(!avatarPath.equals(""))
            params.addBodyParameter("file",new File(avatarPath),null);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = GsonUtils.gson();
                CShop bean = gson.fromJson(result,CShop.class);
                if(bean.returncode.get().equals(Constant.SUCCESS))
                    LoginManager.getInstance().goMain();
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
