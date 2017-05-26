package com.deeshop;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.text.Html;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.base.BaseActivity;
import com.deeshop.adapter.BaseAdapter;
import com.deeshop.adapter.EGoodsAdapter;
import com.deeshop.bean.Category;
import com.deeshop.bean.Goods;
import com.deeshop.bean.Product;
import com.deeshop.databinding.ActivityEditGoodsBinding;
import com.deeshop.gadget.PopPhoto;
import com.deeshop.manager.LoginManager;
import com.deeshop.manager.NetManager;
import com.deeshop.manager.PhotoManager;
import com.deeshop.util.Constant;
import com.deeshop.bean.Product.GoodlistBean.Images;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.deeshop.manager.NetManager.DELETE;
import static com.deeshop.manager.NetManager.OFF_SHELVES;
import static com.deeshop.util.ImageUtils.getDrawablePath;


/**
 * Created by zhiPeng.S on 2017/3/30.
 */

public class EditGoodsActivity extends BaseActivity implements NetManager.Callback,PhotoManager.Callback,BaseAdapter.OnItemClickListener<Images> {
    private ActivityEditGoodsBinding binding;
    private Goods goods = new Goods();
    private String userId,goodId;
    private List<Images> images;
    Images addImage = new Images();
    private EGoodsAdapter adapter;
    private PopPhoto menuWindow;

    @Override
    protected void initData() {
        super.initData();
        binding = DataBindingUtil.setContentView(this,R.layout.activity_edit_goods);
        binding.setEGoodsActivity(this);
        binding.setGoods(goods);
        goods.type.set(getString(R.string.select_category));
        menuWindow = new PopPhoto(this, this);

        goodId = getIntent().hasExtra("goodId") ? getIntent().getStringExtra("goodId") : "";
        userId = LoginManager.user(this).userid.get();

        goods.isShow.set(!goodId.equals(""));
//      init goods picture
        Product.GoodlistBean product = getIntent().hasExtra("product") ?
                (Product.GoodlistBean) getIntent().getSerializableExtra("product") : null;
        images = new ArrayList<>();
        if(product != null){
            images = product.goodimgs;
            goods.goodname.set(product.goodname.get());
            goods.content.set(Html.fromHtml(product.introduction.get()));
            goods.type.set(product.typename.get());
            goods.price.set(product.price.get());
            goods.profit.set(product.profit.get());
            goods.isDis.set(product.isdistribution.get()!=0);
        }
        addImage.path.set(getDrawablePath(R.mipmap.tianjiatupian_btn));
        images.add(addImage);
        LayoutManager layoutManager = new GridLayoutManager(this,4);
        adapter = new EGoodsAdapter(this,images);
        adapter.setOnItemClickListener(this);
        binding.eGoodsRv.setLayoutManager(layoutManager);
        binding.eGoodsRv.setAdapter(adapter);
    }

    @Override
    protected void initActionbar() {
        super.initActionbar();
        Drawable drawable = getResources().getDrawable(R.mipmap.nav_quxiao_icon);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        binding.include.titleTv.setText(R.string.edit_item_detail);
        if(!goods.isShow.get())
            binding.include.titleTv.setText(R.string.add_goods);
        binding.include.rightTv.setText(R.string.done);
        binding.include.backIv.setCompoundDrawables(drawable,null,null,null);

        binding.include.rightTv.setOnClickListener(this);
        binding.include.backIv.setOnClickListener(this);
        goods.enabled.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                if (observable == goods.enabled) {
                    binding.include.rightTv.setEnabled(goods.enabled.get());
                    if(goods.enabled.get())
                        binding.include.rightTv.setTextColor(getResources().getColor(R.color.black_1_c));
                    else
                        binding.include.rightTv.setTextColor(getResources().getColor(R.color.gray_a0_c));
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if(menuWindow.isShowing()) menuWindow.dismiss();
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.back_iv:
                finish();
                break;
            case R.id.right_tv:
                commitGoodsInfo();
                break;
            case R.id.eGoods_off_shelve_tv:
                break;
            case R.id.eGoods_delete_tv:
                NetManager.commitDelete(goodId,this);
                break;
            case R.id.eGoods_category_ll:
                intent.setClass(this,CategoryActivity.class);
                intent.putExtra("category",goods.type.get());
                startActivityForResult(intent,CATEGORY);
                break;
            case R.id.eGoods_swi:
                break;
            case R.id.eGoods_rule_iv:
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



    private void commitGoodsInfo(){
        String url = "Good/UpdateGoodByID.ashx";
        if(goodId.equals(""))
            url = "Good/AddGood.ashx";
        RequestParams params = new RequestParams(Constant.getBaseUrl() + url);
        if(!goodId.equals(""))
            params.addBodyParameter("goodid",goodId);
        params.addBodyParameter("goodname",goods.goodname.get());
        params.addBodyParameter("type",goods.typeId.get() + "");
        params.addBodyParameter("userid",userId);
        params.addBodyParameter("price",goods.price.get());
        String isDis = goods.isDis.get() ? "1" : "0";
        params.addBodyParameter("isDis",isDis);
        params.addBodyParameter("profit",goods.profit.get());

        params.setMultipart(true);
        for (int i = 0; i < images.size()-1; i++) {
            String path = images.get(i).path.get();
            if(!path.equals("")){
                params.addBodyParameter("file"+i,new File(path),null); // 如果文件没有扩展名, 最好设置contentType参数.
            }
        }

        LoginManager.addToken(params);
        x.http().post(params, new org.xutils.common.Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LoginManager.saveCookie();
                toast("success", Toast.LENGTH_SHORT);
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
    public void update(int what, String result) {
        switch (what){
            case DELETE:
                break;
            case OFF_SHELVES:
                break;
        }
    }

    @Override
    public void photo(String path) {
        Images image = new Images();
        image.path.set(path);
        images.remove(images.size()-1);
        images.add(image);
        images.add(addImage);
        adapter.setDatas(images);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(View view, Images bean) {
        if(images.indexOf(bean) == images.size()-1)
            menuWindow.showAtLocation(binding.eGoodsRv,Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    @Override
    public void onItemLongClick(View view, Images bean) {

    }

    public final static int CATEGORY = 1 << 3;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Category.GoodtypesBean category = data != null ? data.hasExtra("category") ?
                (Category.GoodtypesBean) data.getSerializableExtra("category") : null : null;
        switch (requestCode){
            case CATEGORY:
                if(category != null){
                    goods.type.set(category.name.get());
                    goods.typeId.set(category.tyid.get());
                }
                break;
            case PhotoManager.SELECT_PIC_BY_TACK_PHOTO:
            case PhotoManager.SELECT_PIC_BY_PICK_PHOTO:
                PhotoManager.getInstance().doPhoto(requestCode,data,this,this);
                break;
        }
    }
}
