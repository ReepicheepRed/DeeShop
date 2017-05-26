package com.deeshop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.view.View;

import com.base.BaseActivity;
import com.deeshop.adapter.BaseAdapter;
import com.deeshop.adapter.HistoryAdapter;
import com.deeshop.adapter.MarketAdapter;
import com.deeshop.bean.Market;
import com.deeshop.bean.Pop;
import com.deeshop.databinding.ActivitySearchBinding;
import com.deeshop.gadget.ItemDecoration;
import com.deeshop.manager.LoginManager;
import com.deeshop.util.Constant;
import com.deeshop.util.GsonUtils;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhiPeng.S on 2017/4/19.
 */

public class SearchActivity extends BaseActivity implements BaseAdapter.OnItemClickListener{
    private ActivitySearchBinding binding;
    private List<Market.GoodlistBean> datas = new ArrayList<>();
    private MarketAdapter adapter;
    private Pop pop = new Pop();
    private List<Pop> history = new ArrayList<>();
    private HistoryAdapter adapter_his;

    @Override
    protected void initData() {
        super.initData();
        binding = DataBindingUtil.setContentView(this,R.layout.activity_search);
        binding.setPop(pop);
        binding.setActivity(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        binding.searchRv.setLayoutManager(linearLayoutManager);
        ItemDecoration itemDecoration = new ItemDecoration(20/2);
        itemDecoration.setBottom(true);
        binding.searchRv.addItemDecoration(itemDecoration);

        adapter = new MarketAdapter(this,datas);
        adapter.setOnItemClickListener(this);

        adapter_his = new HistoryAdapter(this,history);
        adapter_his.setOnItemClickListener(this);
        loadHistory();

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.search_cancel_tv:
                finish();
                break;
            case R.id.search_clear_iv:
                pop.string1.set("");
                break;
            case R.id.search_clear_history_tv:
                clearHistory();
                loadHistory();
                break;
        }
    }

    public void afterTextChanged(Editable s) {
        if(s.length() > 0){
            obtainSearch();
            binding.searchHistoryLl.setVisibility(View.GONE);
        } else if(history.size() > 0) {
            loadHistory();
        }

    }

    @Override
    public void onItemClick(View view, Object bean) {
        if(bean instanceof Market.GoodlistBean){
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("goodId",((Market.GoodlistBean)bean).goodid.get());
            startActivity(intent);
            return;
        }

        pop.string1.set(((Pop)bean).string1.get());
    }

    @Override
    public void onItemLongClick(View view, Object bean) {

    }

    private void loadHistory(){
        history = readHistory();
        binding.searchHistoryLl.setVisibility(history.size() > 0 ? View.VISIBLE : View.GONE);
        adapter_his.setDatas(history);
        binding.searchRv.setAdapter(adapter_his);
    }

    private List<Pop> readHistory(){
        SharedPreferences preferences = LoginManager.getAppSharedPreferences(this,"history");
        String history = preferences.getString("history","");
        List<Pop> list = GsonUtils.gson().fromJson(history,new TypeToken<List<Pop>>(){}.getType());
        return list == null ? new ArrayList<>() : list;
    }

    private void addHistory(List<Pop> history){
        SharedPreferences.Editor editor = LoginManager.getAppSharedPreferences(this,"history").edit();
        String str = GsonUtils.gson().toJson(history);
        editor.putString("history",str);
        editor.apply();

    }

    private void clearHistory(){
        SharedPreferences.Editor editor = LoginManager.getAppSharedPreferences(this,"history").edit();
        editor.clear();
        editor.apply();
    }

    private void obtainSearch(){
        RequestParams params = new RequestParams(Constant.getBaseUrl() + "Good/GoodsSearch.ashx");
        params.addBodyParameter("str",pop.string1.get());
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Market market = GsonUtils.gson().fromJson(result,Market.class);
                if(market.returncode.get().equals(Constant.SUCCESS)){
                    for (int i = 0; i < history.size(); i++) {
                        Pop po = history.get(i);
                        if(pop.string1.get().equals(po.string1.get())){
                            history.remove(po);
                            i--;
                        }

                    }

                    history.add(0,pop);
                    addHistory(history);
                }
                datas = market.goodlist;
                adapter.setDatas(datas);
                binding.searchRv.setAdapter(adapter);
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
