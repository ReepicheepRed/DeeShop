package com.deeshop.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.deeshop.R;
import com.base.BaseFragment;
import com.deeshop.SearchActivity;
import com.deeshop.adapter.MarketPagerAdapter;
import com.deeshop.bean.Shop;
import com.deeshop.databinding.FragmentMarketBinding;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by zhiPeng.S on 2017/3/21.
 */

public class MarketFragment extends BaseFragment {
    private FragmentMarketBinding binding;

    public static MarketFragment newInstance(int position){
        MarketFragment fragment = new MarketFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position",position);
        fragment.setArguments(bundle);
        return fragment;
    }
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_market, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initData() {
        super.initData();
        MarketPagerAdapter pagerAdapter = new MarketPagerAdapter(getActivity(),getChildFragmentManager(),binding);
        binding.marketVp.setAdapter(pagerAdapter);
        binding.marketTl.setupWithViewPager(binding.marketVp);

    }

    @Override
    protected void initActionbar() {
        super.initActionbar();
        Drawable drawable = getResources().getDrawable(R.mipmap.nav_icon_sousuo);
        drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
        binding.includeMarket.backIv.setCompoundDrawables(drawable,null,null,null);
        binding.includeMarket.titleTv.setText(R.string.market);

        binding.includeMarket.backIv.setOnClickListener(this);
    }

    public FragmentMarketBinding getBinding() {
        return binding;
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.back_iv:
                intent.setClass(getActivity(), SearchActivity.class);
                startActivity(intent);
                break;
        }
    }
}
