package com.deeshop.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.BaseActivity;
import com.base.BaseFragment;
import com.deeshop.AuthenticationActivity;
import com.deeshop.ModifyActivity;
import com.deeshop.R;
import com.deeshop.adapter.BaseAdapter;
import com.deeshop.adapter.ShopAdapter;
import com.deeshop.bean.Modify;
import com.deeshop.bean.Pop;
import com.deeshop.bean.Shop;
import com.deeshop.databinding.FragmentShopBinding;
import com.deeshop.gadget.PopPhoto;
import com.deeshop.gadget.PopShare;
import com.deeshop.gadget.SectionDecoration;
import com.deeshop.manager.LoginManager;
import com.deeshop.manager.PhotoManager;
import com.deeshop.manager.WebManager;
import com.deeshop.manager.ShareManager;
import com.deeshop.util.Constant;
import com.deeshop.util.GsonUtils;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.deeshop.manager.PhotoManager.REQUESTCODE_CUTTING;
import static com.deeshop.manager.PhotoManager.REQUESTCODE_PICK;
import static com.deeshop.manager.PhotoManager.REQUESTCODE_TAKE;

/**
 * Created by zhiPeng.S on 2017/3/21.
 */

public class ShopFragment extends BaseFragment implements View.OnClickListener,PhotoManager.Callback,BaseAdapter.OnItemClickListener<Shop.Info>{
    private FragmentShopBinding binding;
    private PopPhoto menuWindow;
    private PopShare popShare;
    private Pop pop = new Pop();
    private List<Shop.Info> datas = new ArrayList<>();
    private ShopAdapter adapter;
    private Shop.ShopdetialBean shopDetail = new Shop.ShopdetialBean();
    private String userId,shopId,authImg;
    private int authState;
    public static ShopFragment newInstance(int position){
        ShopFragment fragment = new ShopFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position",position);
        fragment.setArguments(bundle);
        return fragment;
    }
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shop, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initData() {
        super.initData();
        userId = LoginManager.user(getActivity()).userid.get();
        menuWindow = new PopPhoto(this);
        popShare = new PopShare(getActivity(),pop);

        String[] intro = getResources().getStringArray(R.array.shop_intro);
        for (int i = 0; i < intro.length; i++) {
            Shop.Info shop = new Shop.Info();
            shop.title.set(intro[i]);
            datas.add(shop);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        SectionDecoration sectionDecoration = new SectionDecoration(getActivity());
        adapter = new ShopAdapter(getActivity(),datas);
        adapter.setOnItemClickListener(this);
        binding.shopRv.setLayoutManager(layoutManager);
        binding.shopRv.setAdapter(adapter);
        binding.shopRv.addItemDecoration(sectionDecoration);
        obtainShop();
    }

    @Override
    protected void initActionbar() {
        super.initActionbar();
        Drawable drawable = getResources().getDrawable(R.mipmap.nav_icon_tuiguang);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        binding.includeShop.backIv.setCompoundDrawables(null,null,null,null);
        binding.includeShop.backIv.setText(R.string.preview);
        binding.includeShop.rightTv.setText("");
        binding.includeShop.rightTv.setCompoundDrawables(drawable,null,null,null);
        binding.includeShop.titleTv.setText(R.string.shop);

        binding.includeShop.backIv.setOnClickListener(this);
        binding.includeShop.rightTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if(menuWindow.isShowing()) menuWindow.dismiss();
        switch (view.getId()){
            case R.id.back_iv:
                String url = WebManager.SHOP + shopId;
                WebManager.getInstance().goWeb(getActivity(),url);
                break;
            case R.id.right_tv:
                pop.string1.set(WebManager.SHOP + shopDetail.shopid.get());
                pop.string2.set(shopDetail.shoplogo.get());
                popShare.showAtLocation(binding.shopRv,Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
        }
    }

    private void obtainShop(){
        RequestParams params = new RequestParams(Constant.getBaseUrl() + "Shop/ShopDetial.ashx");
        params.addQueryStringParameter("userid",userId);
        LoginManager.addToken(params);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LoginManager.saveCookie();
                Gson gson = GsonUtils.gson();
                Shop bean = gson.fromJson(result,Shop.class);
                shopDetail = bean.shopdetial;
                shopId = bean.shopdetial.id.get().toString();
                authImg = bean.shopdetial.authimg.get();
                for (int i = 0; i < datas.size(); i++) {
                    Shop.Info shop = datas.get(i);
                    switch (i){
                        case 0:
                            shop.avatar.set(bean.shopdetial.shoplogo.get());
                            break;
                        case 1:
                            shop.content.set(bean.shopdetial.storename.get());
                            break;
                        case 2:
                            shop.content.set(bean.shopdetial.introduction.get());
                            break;
                        case 3:
                            authState = bean.shopdetial.authtype.get();
                            switch (authState){
                                case 0:
                                case 3:
                                    shop.content.set(getString(R.string.unauthorized));
                                    break;
                                case 1:
                                    shop.content.set(getString(R.string.authenticating));
                                    break;
                                case 2:
                                    shop.content.set(getString(R.string.authorized));
                                    break;
                            }
                            break;
                        case 4:
                            shop.content.set(bean.shopdetial.phone.get());
                            break;
                        case 5:
                            shop.content.set(bean.shopdetial.facebook.get());
                            break;
                        case 6:
                            shop.content.set(bean.shopdetial.line.get());
                            break;
                    }
                }

                adapter.setDatas(datas);
                adapter.notifyDataSetChanged();
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

    private enum Type{
        logo(0),name(1),slogan(2),identity(3),phone(4),facebook(5),line(6);

        private int id;

        Type(int id){
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    @Override
    public void onItemClick(View view, Shop.Info bean) {
        int index = datas.indexOf(bean);
        Type type  = Type.logo;
        for (Type t: Type.values()) {
            if(t.getId() == index)
                type = t;
        }
        boolean isLogo = false;
        int requestCode = Type.phone.getId();
//      Type name capitalize
        char[] cs= type.name().toCharArray();
        cs[0]-=32;
        Intent intent = new Intent();
        switch(type){
            case logo:
                isLogo = true;
                menuWindow.showAtLocation(binding.shopRv,Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case identity:
                //0 or 3 is unauthorized
                if(authState !=0 && authState != 3) return;
                intent.setClass(getActivity(), AuthenticationActivity.class);
                intent.putExtra("id",shopId);
                intent.putExtra("img",authImg);
                requestCode = Type.identity.getId();
                break;
            case name:
            case slogan:
            case phone:
            case facebook:
            case line:
                intent.setClass(getActivity(), ModifyActivity.class);
                Modify modify = new Modify();
                modify.title.set(bean.title.get());
                modify.content.set(bean.content.get());
                modify.type.set(String.valueOf(cs));
                modify.id.set(shopId);
                intent.putExtra("info",modify);
                break;
        }
        if(!isLogo)
            startActivityForResult(intent,requestCode);
    }

    @Override
    public void onItemLongClick(View view, Shop.Info bean) {

    }

    @Override
    public void photo(String path) {
        datas.get(0).avatar.set(path);
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
                PhotoManager.getInstance().setPicToView(data,getActivity(),this);
                break;
            case 3: //Type.identity.getId()
            case 4: //Type.phone.getId()
                obtainShop();
                break;
        }

    }
}
