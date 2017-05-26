package com.deeshop.gadget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.deeshop.R;
import com.deeshop.bean.Pop;
import com.deeshop.databinding.PopContactBinding;

public class PopContact extends PopupWindow {

    private TextView takePhotoBtn, pickPhotoBtn, cancelBtn;
    private View mMenuView;

    @SuppressLint("InflateParams")
    public PopContact(Context context, Pop pop,OnClickListener itemsOnClick) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

//        mMenuView = inflater.inflate(R.layout.pop_photo, null);
//        takePhotoBtn = (TextView) mMenuView.findViewById(R.id.pop_take_photo_tv);
//        pickPhotoBtn = (TextView) mMenuView.findViewById(R.id.pop_album_tv);
//        cancelBtn = (TextView) mMenuView.findViewById(R.id.pop_cancel_tv);

        PopContactBinding binding = DataBindingUtil.inflate(inflater,R.layout.pop_contact,null,false);
        binding.setPop(pop);
        mMenuView = binding.getRoot();

        binding.popPhoneTv.setOnClickListener(itemsOnClick);
        binding.popFacebookTv.setOnClickListener(itemsOnClick);
        binding.popInstagramTv.setOnClickListener(itemsOnClick);

        this.setContentView(mMenuView);
        this.setWidth(LayoutParams.WRAP_CONTENT);
        this.setHeight(LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setAnimationStyle(R.style.PopupAnimation);
        ColorDrawable dw = new ColorDrawable(0x00000000);
        this.setBackgroundDrawable(dw);
        mMenuView.setOnTouchListener((v, event) -> {

            int height = binding.popContact.getTop();
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
