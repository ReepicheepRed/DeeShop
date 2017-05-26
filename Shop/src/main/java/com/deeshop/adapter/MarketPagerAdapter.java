package com.deeshop.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.deeshop.R;
import com.deeshop.databinding.FragmentMarketBinding;
import com.deeshop.fragment.MarketChildFragment;
import com.deeshop.fragment.MarketFragment;

/**
 * Created by zhiPeng.S on 2017/3/21.
 */

public class MarketPagerAdapter extends FragmentPagerAdapter {

    private String[] category;
    private MarketChildFragment curFragment;
    private FragmentMarketBinding binding;
    public MarketPagerAdapter(Context context, FragmentManager fm,FragmentMarketBinding binding) {
        super(fm);
        category = context.getResources().getStringArray(R.array.market_category);
        this.binding = binding;
    }

    @Override
    public Fragment getItem(int position) {
        curFragment = MarketChildFragment.newInstance(position);
        return curFragment;
    }

    @Override
    public int getCount() {
        return category.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return category[position];
    }

    public void updateData(){
        curFragment.update();
//        new Handler().postDelayed(() -> binding.marketPtrFrame.refreshComplete(),2000);
    }

    public boolean checkCanDoRefresh() {
        if (curFragment == null) {
            return true;
        }
        return curFragment.checkCanDoRefresh();
    }
}
