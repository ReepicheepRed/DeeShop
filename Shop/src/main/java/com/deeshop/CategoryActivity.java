package com.deeshop;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.base.BaseActivity;
import com.deeshop.adapter.CategoryAdapter;
import com.deeshop.bean.Category;
import com.deeshop.bean.Category.GoodtypesBean;
import com.deeshop.databinding.ActivityCategoryBinding;
import com.deeshop.gadget.ItemDecoration;
import com.deeshop.util.Constant;
import com.deeshop.util.GsonUtils;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhiPeng.S on 2017/3/17.
 */

public class CategoryActivity extends BaseActivity implements View.OnClickListener,CategoryAdapter.OnItemClickListener<GoodtypesBean>{
    private ActivityCategoryBinding binding;
    private CategoryAdapter adapter;
    private List<GoodtypesBean> datas = new ArrayList<>();
    private GoodtypesBean curType;
    private String initCategory;

    @Override
    protected void initData() {
        super.initData();
        binding  = DataBindingUtil.setContentView(this,R.layout.activity_category);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        adapter = new CategoryAdapter(this,datas);
        adapter.setOnItemClickListener(this);
        int divider = getResources().getDimensionPixelSize(R.dimen.height_1px);
        int padding = getResources().getDimensionPixelSize(R.dimen.margin_28px);
        ItemDecoration itemDecoration = new ItemDecoration(this,divider);
        itemDecoration.setBottom(true);
        itemDecoration.setLeftPadding(padding);
        binding.categoryRv.setLayoutManager(layoutManager);
        binding.categoryRv.setAdapter(adapter);
        binding.categoryRv.addItemDecoration(itemDecoration);

        Intent data = getIntent();
        initCategory = data != null ? data.hasExtra("category") ? data.getStringExtra("category") : "" : "";
        obtainCategory();
    }

    @Override
    protected void initActionbar() {
        super.initActionbar();
        binding.include.titleTv.setText(R.string.select_category);
        binding.include.backIv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.back_iv:
                finish();
                break;
        }
    }

    private void obtainCategory(){
        RequestParams params = new RequestParams(Constant.getBaseUrl() + "Good/GoodTypes.ashx");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson =  GsonUtils.gson();
                Category category = gson.fromJson(result,Category.class);
                datas = category.goodtypes;
                for (int i = 0; i < datas.size(); i++) {
                    GoodtypesBean type = datas.get(i);
                    if(type.name.get().equals(initCategory))
                        type.isSelect.set(true);
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

    @Override
    public void onItemClick(View view, GoodtypesBean bean) {
        if(curType != null)
            curType.isSelect.set(false);
        curType = bean;
        curType.isSelect.set(true);
        Intent intent = new Intent();
        intent.putExtra("category",curType);
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    public void onItemLongClick(View view, GoodtypesBean bean) {

    }
}
