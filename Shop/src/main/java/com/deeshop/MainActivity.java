package com.deeshop;

import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.base.BaseActivity;
import com.deeshop.bean.Product;
import com.deeshop.bean.Setting;
import com.deeshop.databinding.ActivityMainBinding;
import com.deeshop.fragment.MarketFragment;
import com.deeshop.fragment.ProductFragment;
import com.deeshop.fragment.SettingFragment;
import com.deeshop.fragment.ShopFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private ActivityMainBinding mainBinding;
    private FragmentManager manager;
    private List<Fragment> fragments = new ArrayList<>();
    private Fragment curFragment;
    private TextView curTextView;
    @Override
    protected void initData() {
        super.initData();
        mainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        mainBinding.setMainActivity(this);
        curTextView = mainBinding.mainMarketTv;
        curTextView.setSelected(true);
        MarketFragment fragment = MarketFragment.newInstance(0);
        ProductFragment fragment_1 = ProductFragment.newInstance(1);
        ShopFragment fragment_2 = ShopFragment.newInstance(2);
        SettingFragment fragment_3 = SettingFragment.newInstance(3);
        fragments.add(fragment);
        fragments.add(fragment_1);
        fragments.add(fragment_2);
        fragments.add(fragment_3);
        manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        for (int i = 0; i < fragments.size(); i++) {
            transaction.add(mainBinding.mainFl.getId(),fragments.get(i));
            transaction.hide(fragments.get(i));
        }
        transaction.commit();
        showFragment(0);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        curTextView.setSelected(false);
        curTextView.setTextColor(getResources().getColor(R.color.gray_7b_c));
        switch (view.getId()){
            case R.id.main_market_tv:
                curTextView = mainBinding.mainMarketTv;
                showFragment(0);
                break;
            case R.id.main_products_tv:
                curTextView = mainBinding.mainProductsTv;
                showFragment(1);
                break;
            case R.id.main_shop_tv:
                curTextView = mainBinding.mainShopTv;
                showFragment(2);
                break;
            case R.id.main_setting_tv:
                curTextView = mainBinding.mainSettingTv;
                showFragment(3);
                break;
        }
        curTextView.setSelected(true);
        curTextView.setTextColor(getResources().getColor(R.color.red_primary_c));
    }


    private void showFragment(int id){
        if(id >= fragments.size()) return;
        FragmentTransaction transaction = manager.beginTransaction();
        if(curFragment != null)
            transaction.hide(curFragment);
        transaction.show(fragments.get(id));
        curFragment = fragments.get(id);
        transaction.commit();
    }
}
