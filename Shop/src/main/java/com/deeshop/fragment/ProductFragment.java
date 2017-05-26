package com.deeshop.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.base.BaseActivity;
import com.base.BaseFragment;
import com.deeshop.DetailActivity;
import com.deeshop.EditGoodsActivity;
import com.deeshop.R;
import com.deeshop.adapter.BaseAdapter;
import com.deeshop.adapter.ProductAdapter;
import com.deeshop.bean.Product;
import com.deeshop.databinding.FragmentProductBinding;
import com.deeshop.gadget.ItemDecoration;
import com.deeshop.manager.LoginManager;
import com.deeshop.manager.NetManager;
import com.deeshop.util.Constant;
import com.deeshop.util.GsonUtils;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.PtrHandler2;

/**
 * Created by zhiPeng.S on 2017/3/21.
 */

public class ProductFragment extends BaseFragment implements BaseAdapter.OnItemClickListener,NetManager.Callback {
    private FragmentProductBinding binding;
    private TextView checkedTextView;
    private List<Product.GoodlistBean> datas;
    private ProductAdapter adapter;
    private String userId,goodId;
    private boolean isSale;
    private int count = 10;
    public static ProductFragment newInstance(int position){
        ProductFragment fragment = new ProductFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position",position);
        fragment.setArguments(bundle);
        return fragment;
    }
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initData() {
        super.initData();
        binding.setFragment(this);
        userId = LoginManager.user(getActivity()).userid.get();
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        ItemDecoration itemDecoration = new ItemDecoration(16/2);
        itemDecoration.setTop(true);
        datas = new ArrayList<>();
        adapter = new ProductAdapter(getActivity(),datas);
        adapter.setOnItemClickListener(this);
        adapter.setCallback(this);
        binding.productsRv.setAdapter(adapter);
        binding.productsRv.setLayoutManager(linearLayoutManager);
        binding.productsRv.addItemDecoration(itemDecoration);

        checkSale(true);

        binding.productsPtr.setPtrHandler(this);
        binding.productsPtr.setLoadingMinTime(3000);
        binding.productsPtr.setResistanceFooter(1.0f);
        binding.productsPtr.setDurationToCloseFooter(0); // footer will hide immediately when completed
        binding.productsPtr.setForceBackWhenComplete(true);
        binding.productsPtr.setLastUpdateTimeRelateObject(this);

    }

    @Override
    protected void initActionbar() {
        super.initActionbar();
        binding.includeProduct.backIv.setVisibility(View.GONE);
        binding.includeProduct.titleTv.setText(R.string.products);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.products_sale_tv:
                checkSale(true);
                break;
            case R.id.products_off_shelve_tv:
                checkSale(false);
                break;
            case R.id.products_add_btn:
                Intent intent = new Intent(getActivity(), EditGoodsActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        count = 10;
        obtainProducts();
    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
    }

    @Override
    public boolean checkCanDoLoadMore(PtrFrameLayout frame, View content, View footer) {
        return PtrDefaultHandler2.checkContentCanBePulledUp(frame, binding.productsRv, footer);
    }

    @Override
    public void onLoadMoreBegin(PtrFrameLayout frame) {
        count = count + 10;
        obtainProducts();
    }

    private void checkSale(boolean checked){
        isSale = checked;
        if(checkedTextView != null){
            checkedTextView.setCompoundDrawables(null,null,null,null);
            checkedTextView.setTextColor(getResources().getColor(R.color.black_1_c));
        }

        count = 10;
        obtainProducts();

        Drawable drawable = getResources().getDrawable(R.mipmap.tab_biaoti);
        drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
        if(checked){
            checkedTextView = binding.productsSaleTv;
            checkedTextView.setCompoundDrawables(null,null,null,drawable);
            checkedTextView.setTextColor(getResources().getColor(R.color.red_primary_c));
            return;
        }
        checkedTextView = binding.productsOffShelveTv;
        checkedTextView.setCompoundDrawables(null,null,null,drawable);
        checkedTextView.setTextColor(getResources().getColor(R.color.red_primary_c));
    }

    private void obtainProducts(){
        RequestParams params = new RequestParams(Constant.getBaseUrl() + "Good/MyGoodListByState.ashx");
        params.addQueryStringParameter("userid", userId);
        params.addQueryStringParameter("pagesize",count + "");
        params.addQueryStringParameter("state",isSale ? "1" : "0");
        LoginManager.addToken(params);
        x.http().get(params, new org.xutils.common.Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LoginManager.saveCookie();
                Product product = GsonUtils.gson().fromJson(result,Product.class);
                if(product.returncode.get().equals(Constant.ERROR)){
                    ((BaseActivity)getActivity()).toast(product.msg.get(), Toast.LENGTH_SHORT);
                    LoginManager.getInstance().logout(getActivity());
                    return;
                }
                datas = product.goodlist;
                adapter.setDatas(datas);
                adapter.notifyDataSetChanged();
                binding.productsPtr.refreshComplete();
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
    public void onItemClick(View view, Object object) {
        if(object instanceof Product.GoodlistBean) {
            Product.GoodlistBean bean = (Product.GoodlistBean)object;
            int state = bean.offsale.get().equals("2") ? 2 : bean.offsale.get().equals("3") ? 3 : 1;
            goodId = bean.goodid.get();
            Intent intent = new Intent();
            switch (state) {
                case 1:
                    intent.setClass(getActivity(), EditGoodsActivity.class);
                    intent.putExtra("goodId", goodId);
                    intent.putExtra("product", bean);
                    startActivity(intent);
                    break;
                case 2:
                    intent.setClass(getActivity(), DetailActivity.class);
                    intent.putExtra("goodId", goodId);
                    startActivity(intent);
                    break;
                case 3:
                    ((BaseActivity) getActivity()).alert(getActivity().getString(R.string.alter),
                            getActivity().getString(R.string.alter_tip),
                            getActivity().getString(android.R.string.ok), this,
                            getActivity().getString(R.string.delete), this);
                    break;
            }
        }


    }

    @Override
    public void onItemLongClick(View view, Object object) {

    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        ((BaseActivity)getActivity()).dismissProgressDialog();
        switch (which){
            case DialogInterface.BUTTON_POSITIVE:
                NetManager.commitDelete(goodId,this);
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                break;
        }
    }

    @Override
    public void update(int what, String result) {
        switch (what){
            case NetManager.DELETE:
            case NetManager.ON_SALE:
                obtainProducts();
                break;
        }
    }
}
