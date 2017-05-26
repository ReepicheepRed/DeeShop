package com.deeshop;

import android.databinding.DataBindingUtil;
import android.text.Html;
import android.view.Gravity;
import android.view.View;

import com.base.BaseActivity;
import com.deeshop.adapter.BannerDetailAdapter;
import com.deeshop.bean.Detail;
import com.deeshop.bean.Pop;
import com.deeshop.bean.Title;
import com.deeshop.databinding.ActivityDetailBinding;
import com.deeshop.gadget.PopContact;
import com.deeshop.bean.Detail.GooddetialBean.GoodimgBean;
import com.deeshop.manager.LoginManager;
import com.deeshop.manager.NetManager;
import com.deeshop.manager.WebManager;
import com.deeshop.util.GsonUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zhiPeng.S on 2017/3/28.
 */

public class DetailActivity extends BaseActivity implements View.OnClickListener,NetManager.Callback {
    private ActivityDetailBinding binding;
    private Title title = new Title();
    private Detail.GooddetialBean bean = new Detail.GooddetialBean();
    private Pop pop = new Pop();
    private PopContact menuWindow;
    private List<GoodimgBean> banners;
    private BannerDetailAdapter bannerAdapter;
    private int offset_x,offset_y;
    private String userId,goodId;

    @Override
    protected void initData() {
        super.initData();
        binding = DataBindingUtil.setContentView(this,R.layout.activity_detail);
        binding.setTitle(title);
        binding.setDetail(bean);
        binding.setDetailActivity(this);
        menuWindow = new PopContact(this,pop,this);
        offset_x = getResources().getDimensionPixelOffset(R.dimen.height_90px);
        offset_y = getResources().getDimensionPixelOffset(R.dimen.height_90px);

        //      init banner
        banners = new ArrayList<>();
        bannerAdapter = new BannerDetailAdapter(this, banners);
        binding.detailBannerVf.setFlowIndicator(binding.detailBannerFi);
        binding.detailBannerVf.setAdapter(bannerAdapter);
        binding.detailBannerVf.setTimeSpan(4500);
        binding.detailBannerVf.setSelection(3 * 1000);
        binding.detailBannerVf.startAutoFlowTimer();

//      init goods info
        goodId = getIntent().hasExtra("goodId") ? getIntent().getStringExtra("goodId") : "";
        userId = LoginManager.user(this).userid.get();
        NetManager.obtainDetailInfo(userId,goodId,this);
    }

    @Override
    protected void initActionbar() {
        super.initActionbar();
        binding.includeDetail.titleTv.setText(getString(R.string.sourcing_details));
        binding.includeDetail.backIv.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if(menuWindow.isShowing()) menuWindow.dismiss();

        switch (view.getId()){
            case R.id.back_iv:
                finish();
                break;
            case R.id.detail_into_tv:
                WebManager.getInstance().goWeb(this,WebManager.SHOP + bean.shopid.get());
                break;
            case R.id.detail_contact_btn:
                menuWindow.showAtLocation(binding.detailContactBtn,Gravity.BOTTOM | Gravity.START, offset_x, offset_y);
                break;
            case R.id.detail_sale_btn:
                NetManager.commitSaleOrSoldOut(userId,goodId,bean.exist.get() == 0,this);
                break;
//          contact method
            case R.id.pop_phone_tv:
                break;
            case R.id.pop_facebook_tv:
                break;
            case R.id.pop_instagram_tv:
                break;
        }
    }

    public void showBanner(List<GoodimgBean> urls){
        if (urls != null) {
            binding.detailBannerVf.setmSideBuffer(urls.size());
            bannerAdapter.setData(urls);
            binding.detailBannerVf.setAdapter(bannerAdapter);
            binding.detailBannerVf.startAutoFlowTimer();
        }
    }

    @Override
    public void update(int what, String result) {
        switch (what){
            case NetManager.DETAIL:
                Gson gson = GsonUtils.gson();
                Detail detail = gson.fromJson(result,Detail.class);
                bean = detail.gooddetial;
                bean.introHtml.set(Html.fromHtml(bean.introduction.get()));
                binding.setDetail(bean);

                showBanner(bean.goodimg);
                pop.string1.set(bean.shopphone.get());
                pop.string2.set(bean.facebook.get());
                pop.string3.set(bean.line.get());
                break;
            case NetManager.ON_SALE:
                bean.exist.set(1);
                break;
            case NetManager.OFF_SHELVES:
                bean.exist.set(0);
                break;
        }
    }
}
