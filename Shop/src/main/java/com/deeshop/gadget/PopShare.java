package com.deeshop.gadget;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

import com.deeshop.R;
import com.deeshop.adapter.BaseAdapter;
import com.deeshop.adapter.PromotionAdapter;
import com.deeshop.adapter.ShareAdapter;
import com.deeshop.bean.Pop;
import com.deeshop.databinding.PopShareBinding;
import com.deeshop.manager.ShareManager;
import com.deeshop.util.ImageUtils;

import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.facebook.Facebook;
import cn.sharesdk.facebookmessenger.FacebookMessenger;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.instagram.Instagram;
import cn.sharesdk.line.Line;
import cn.sharesdk.twitter.Twitter;

import static com.deeshop.manager.ShareManager.showShare;

public class PopShare extends PopupWindow implements BaseAdapter.OnItemClickListener<Pop>,View.OnClickListener{
    private View mMenuView;
    private List<Pop> list;
    private Context mContext;
    private Pop pop;

    @SuppressLint("InflateParams")
    public PopShare(Context context, Pop pop) {
        super(context);
        mContext = context;
        this.pop = pop;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        PopShareBinding binding = DataBindingUtil.inflate(inflater,R.layout.pop_share,null,false);
        String[] drawables = {
                ImageUtils.getDrawablePath(R.mipmap.facebook_icon),
                ImageUtils.getDrawablePath(R.mipmap.messenger_icon),
                ImageUtils.getDrawablePath(R.mipmap.line_icon),
                ImageUtils.getDrawablePath(R.mipmap.instagram_icon),
                ImageUtils.getDrawablePath(R.mipmap.twitter_icon),
                ImageUtils.getDrawablePath(R.mipmap.copylink_icon)};
        String[] strings = context.getResources().getStringArray(R.array.share);
        list = new ArrayList<>();
        for (int i = 0; i < drawables.length; i++) {
            Pop iPop = new Pop();
            iPop.string1.set(drawables[i]);
            iPop.string2.set(strings[i]);
            list.add(iPop);
        }
        
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context,3);
        ShareAdapter adapter = new ShareAdapter(context,list);
        adapter.setOnItemClickListener(this);
        binding.popShareRv.setAdapter(adapter);
        binding.popShareRv.setLayoutManager(layoutManager);
        binding.popShareCancelTv.setOnClickListener(this);


        mMenuView = binding.getRoot();
        this.setContentView(mMenuView);
        this.setClippingEnabled(false);
        this.setWidth(LayoutParams.MATCH_PARENT);
        this.setHeight(LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        this.setAnimationStyle(R.style.PopupAnimation);
        ColorDrawable dw = new ColorDrawable(0x99000000);
        this.setBackgroundDrawable(dw);
        mMenuView.setOnTouchListener((v, event) -> {

            int height = mMenuView.findViewById(R.id.pop_share_rv).getTop();
            int y = (int) event.getY();
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (y < height) {
                    dismiss();
                }
            }
            return true;
        });

    }

    @Override
    public void onClick(View v) {
        dismiss();
    }

    @Override
    public void onItemClick(View view, Pop bean) {
        onClick(view);
        Platform plat = null;
        switch (list.indexOf(bean)){
            case 0: //facebook
                plat = ShareSDK.getPlatform(Facebook.NAME);
                break;
            case 1: //messenger
                plat = ShareSDK.getPlatform(FacebookMessenger.NAME);
                break;
            case 2: //line
                plat = ShareSDK.getPlatform(Line.NAME);
                break;
            case 3: //instagram
                plat = ShareSDK.getPlatform(Instagram.NAME);
                break;
            case 4: //twitter
                plat = ShareSDK.getPlatform(Twitter.NAME);
                break;
            case 5: //copyLink
                ShareManager.copy(pop.string1.get());
                break;
        }
        if(plat != null)
            showShare(plat.getName(),pop);
    }

    @Override
    public void onItemLongClick(View view, Pop bean) {

    }
}
