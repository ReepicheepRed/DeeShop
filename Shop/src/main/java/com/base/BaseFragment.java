package com.base;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.xutils.x;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler2;

/**
 * Created by zhiPeng.S on 2017/3/15.
 */

public class BaseFragment extends Fragment implements View.OnClickListener,DialogInterface.OnClickListener,PtrHandler2 {

    private boolean injected = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        injected = true;
        return x.view().inject(this,inflater, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(!injected){
            x.view().inject(this,this.getView());
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initActionbar();
    }

    protected void initData(){

    }

    protected void initActionbar(){

    }

    public void onClick(View view){

    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

    }

    @Override
    public boolean checkCanDoLoadMore(PtrFrameLayout frame, View content, View footer) {
        return false;
    }

    @Override
    public void onLoadMoreBegin(PtrFrameLayout frame) {

    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return false;
    }

    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {

    }
}
