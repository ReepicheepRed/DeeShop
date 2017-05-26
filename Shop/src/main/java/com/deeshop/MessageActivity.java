package com.deeshop;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.base.BaseActivity;
import com.deeshop.adapter.MessageAdapter;
import com.deeshop.bean.Common;
import com.deeshop.bean.Message;
import com.deeshop.databinding.ActivityMessageBinding;
import com.deeshop.util.Constant;
import com.deeshop.util.GsonUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhiPeng.S on 2017/3/17.
 */

public class MessageActivity extends BaseActivity {
    private ActivityMessageBinding binding;
    private List<Message> datas = new ArrayList<>();
    private MessageAdapter adapter;

    @Override
    protected void initData() {
        super.initData();
        binding = DataBindingUtil.setContentView(this,R.layout.activity_message);

        adapter = new MessageAdapter(this,datas);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        binding.msgRv.setAdapter(adapter);
        binding.msgRv.setLayoutManager(layoutManager);
        obtainMsg();
    }

    @Override
    protected void initActionbar() {
        super.initActionbar();
        binding.include.titleTv.setText(R.string.msg);
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

    private void obtainMsg(){
        RequestParams params = new RequestParams(Constant.getBaseUrl() + "Handler/Tips.ashx");
        params.addQueryStringParameter("system","2");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                datas = GsonUtils.gson().fromJson(result, Common.class).tipslist;
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
}
