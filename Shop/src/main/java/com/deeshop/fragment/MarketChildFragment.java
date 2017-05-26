package com.deeshop.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.BaseFragment;
import com.deeshop.DetailActivity;
import com.deeshop.R;
import com.deeshop.adapter.BannerAdapter;
import com.deeshop.adapter.BaseAdapter;
import com.deeshop.adapter.MarketAdapter;
import com.deeshop.bean.Banner;
import com.deeshop.bean.Market;
import com.deeshop.databinding.FragmentMarketChildBinding;
import com.deeshop.gadget.ItemDecoration;
import com.deeshop.util.Constant;
import com.deeshop.util.GsonUtils;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.PtrHandler2;

/**
 * Created by zhiPeng.S on 2017/3/22.
 */

public class MarketChildFragment extends BaseFragment implements BaseAdapter.OnItemClickListener<Market.GoodlistBean>{
    private FragmentMarketChildBinding binding;
    private List<Banner.BannerlistBean> banners;
    private BannerAdapter bannerAdapter;
    private List<Market.GoodlistBean> data;
    private MarketAdapter adapter;
    private boolean isScrollTop;
    private String type;
    private int count = 10;
    public static MarketChildFragment newInstance(int position){
        MarketChildFragment fragment = new MarketChildFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position",position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_market_child,container,false);
        return binding.getRoot();
    }

    @Override
    protected void initData() {
        super.initData();
        type = String.valueOf(this.getArguments().getInt("position",0) + 1);
//      init banner
        banners = new ArrayList<>();
        bannerAdapter = new BannerAdapter(getActivity(), banners);
        binding.bannerVf.setFlowIndicator(binding.bannerFi);
        binding.bannerVf.setAdapter(bannerAdapter);
        binding.bannerVf.setTimeSpan(4500);
        binding.bannerVf.setSelection(3 * 1000);
        binding.bannerVf.startAutoFlowTimer();
        binding.bannerVf.setViewPager(((MarketFragment)getParentFragment()).getBinding().marketVp);

//      init market goods list
        data = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        adapter = new MarketAdapter(getActivity(),data);
        adapter.setOnItemClickListener(this);
        ItemDecoration itemDecoration = new ItemDecoration(20/2);
        itemDecoration.setBottom(true);
        binding.marketRv.setLayoutManager(linearLayoutManager);
        binding.marketRv.setAdapter(adapter);
        binding.marketRv.addItemDecoration(itemDecoration);

//      refresh setting
        binding.marketPtr.setPtrHandler(this);
        binding.marketPtr.disableWhenHorizontalMove(true);
        binding.marketPtr.setLastUpdateTimeRelateObject(this);
        binding.marketPtr.setForceBackWhenComplete(true);


        update();
    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
    }

    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        count = 10;
        update();
    }

    @Override
    public boolean checkCanDoLoadMore(PtrFrameLayout frame, View content, View footer) {
        return PtrDefaultHandler2.checkContentCanBePulledUp(frame, content, footer);
    }

    @Override
    public void onLoadMoreBegin(PtrFrameLayout frame) {
        count = count + 10;
        update();
    }

    public void update(){
        banners.clear();
        data.clear();
        obtainBanner();
        obtainList();
    }

    private void obtainBanner(){
        RequestParams params = new RequestParams(Constant.getBaseUrl() + "Handler/Banner.ashx");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = GsonUtils.gson();
                Banner banner = gson.fromJson(result,Banner.class);
                banners = banner.bannerlist;
                showBanner(banners);
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

    public void showBanner(List<Banner.BannerlistBean> urls){
        if (urls != null) {
            binding.bannerVf.setmSideBuffer(urls.size());
            bannerAdapter.setData(urls);
            binding.bannerVf.setAdapter(bannerAdapter);
            binding.bannerVf.startAutoFlowTimer();
        }
    }

    private void obtainList(){
        RequestParams params = new RequestParams(Constant.getBaseUrl() + "Good/GoodsList.ashx");
        params.addBodyParameter("pagesize",count + "");
        params.addBodyParameter("type",type);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = GsonUtils.gson();
                Market bean =  gson.fromJson(result,Market.class);
                adapter.setDatas(bean.goodlist);
                adapter.notifyDataSetChanged();
                binding.marketPtr.refreshComplete();
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
    public void onItemClick(View view, Market.GoodlistBean bean) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("goodId",bean.goodid.get());
        startActivity(intent);

    }

    @Override
    public void onItemLongClick(View view, Market.GoodlistBean bean) {

    }

    public boolean checkCanDoRefresh() {
        return  binding.marketRv.getChildCount() == 0 || binding.marketRv == null || isScrollTop;
//        return !binding.marketRv.canScrollVertically(-1);
    }
}
