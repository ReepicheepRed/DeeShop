package com.deeshop.gadget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.deeshop.R;
import com.deeshop.adapter.PromotionAdapter;
import com.deeshop.bean.Pop;
import com.deeshop.databinding.PopPromotionBinding;

import java.util.ArrayList;
import java.util.List;

public class PopPromotion extends PopupWindow {
    private View mMenuView;

    @SuppressLint("InflateParams")
    public PopPromotion(Context context, PromotionAdapter.OnItemClickListener onItemClickListener) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        PopPromotionBinding binding = DataBindingUtil.inflate(inflater,R.layout.pop_promotion,null,false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        int divider = context.getResources().getDimensionPixelSize(R.dimen.height_1px);
        int divider_20 = context.getResources().getDimensionPixelSize(R.dimen.margin_20px);
        ItemDecoration itemDecoration1 = new ItemDecoration(divider);
        ItemDecoration itemDecoration2 = new ItemDecoration(divider_20);
        itemDecoration1.setBottom(true);
        itemDecoration2.setBottom(true);
        List<Pop> datas = new ArrayList<>();
        String[] strings = context.getResources().getStringArray(R.array.promotion);
        for (String str: strings) {
            Pop pop = new Pop();
            pop.string1.set(str);
            datas.add(pop);
        }
        PromotionAdapter adapter = new PromotionAdapter(context,datas);
        adapter.setOnItemClickListener(onItemClickListener);
        binding.popPromotionRv.setAdapter(adapter);
        binding.popPromotionRv.setLayoutManager(layoutManager);
        binding.popPromotionRv.addItemDecoration(itemDecoration1);
//        binding.popPromotionRv.addItemDecoration(itemDecoration2,3);

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

            int height = mMenuView.findViewById(R.id.pop_promotion_rv).getTop();
            int y = (int) event.getY();
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (y < height) {
                    dismiss();
                }
            }
            return true;
        });

    }

}
