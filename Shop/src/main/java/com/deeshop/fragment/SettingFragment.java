package com.deeshop.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.BaseFragment;
import com.deeshop.AccountActivity;
import com.deeshop.MessageActivity;
import com.deeshop.R;
import com.deeshop.WebActivity;
import com.deeshop.adapter.BaseAdapter;
import com.deeshop.adapter.SettingAdapter;
import com.deeshop.bean.Pop;
import com.deeshop.bean.Setting;
import com.deeshop.bean.User;
import com.deeshop.databinding.FragmentSettingBinding;
import com.deeshop.gadget.ItemDecoration;
import com.deeshop.gadget.PopShare;
import com.deeshop.manager.LoginManager;
import com.deeshop.manager.WebManager;
import com.deeshop.util.ImageUtils;

import java.util.ArrayList;
import java.util.List;

import static com.deeshop.util.ImageUtils.getDrawablePath;

/**
 * Created by zhiPeng.S on 2017/3/21.
 */

public class SettingFragment extends BaseFragment implements BaseAdapter.OnItemClickListener<Setting>,LoginManager.Callback{
    private FragmentSettingBinding binding;
    private List<Setting> datas = new ArrayList<>();
    private SettingAdapter adapter;
    private PopShare popShare;
    private Pop pop = new Pop();
    private final int REQUEST_ACCOUNT = 1;
    public static SettingFragment newInstance(int position){
        SettingFragment fragment = new SettingFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position",position);
        fragment.setArguments(bundle);
        return fragment;
    }
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initData() {
        super.initData();
        popShare = new PopShare(getActivity(),pop);

        String[] drawables = {
                getDrawablePath(R.mipmap.ic_launcher),
                getDrawablePath(R.mipmap.help_icon),
                getDrawablePath(R.mipmap.tuiguang_icon),
                getDrawablePath(R.mipmap.heart_icon),
                getDrawablePath(R.mipmap.yaoqing_icon),
        };
        String[] intro = getResources().getStringArray(R.array.setting_intro);
        for (int i = 0; i < intro.length; i++) {
            Setting setting = new Setting();
            setting.icon.set(drawables[i]);
            setting.title.set(intro[i]);
            if(i ==0){
                User.Info info = LoginManager.user(getActivity());
                setting.icon.set(info.headimgurl.get());
                setting.title.set(info.nickname.get());
                setting.isCircle.set(true);
            }
            datas.add(setting);
        }
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        adapter = new SettingAdapter(getActivity(),datas);
        adapter.setOnItemClickListener(this);
        int top = getResources().getDimensionPixelSize(R.dimen.margin_20px);
        int left = getResources().getDimensionPixelSize(R.dimen.margin_28px);
        ItemDecoration itemDecoration = new ItemDecoration(getActivity(),top);
        itemDecoration.setTop(true);
        itemDecoration.setLeftPadding(left,2,3);
        binding.settingRv.setLayoutManager(layoutManager);
        binding.settingRv.setAdapter(adapter);
        binding.settingRv.addItemDecoration(itemDecoration);


    }

    @Override
    protected void initActionbar() {
        super.initActionbar();
        Drawable drawable = getResources().getDrawable(R.mipmap.nav_icon_xiaoxi_2x);
        drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
        binding.includeSetting.backIv.setCompoundDrawables(null,null,null,null);
        binding.includeSetting.rightTv.setCompoundDrawables(drawable,null,null,null);
        binding.includeSetting.titleTv.setText(R.string.setting);

        binding.includeSetting.rightTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.right_tv:
                Intent intent = new Intent(getActivity(), MessageActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onItemClick(View view, Setting bean) {
        final int account = 0,about = 2,invite = 4;

        int position = datas.indexOf(bean);
        Intent intent = new Intent();
        switch (position){
            case account:
                intent.setClass(getActivity(), AccountActivity.class);
                startActivityForResult(intent,REQUEST_ACCOUNT);
                break;
            case about:
                WebManager.getInstance().goWeb(getActivity(),WebManager.ABOUT);
                break;
            case invite:
                pop.string1.set(WebManager.DOWNLOAD);
                pop.string2.set(ImageUtils.getDrawablePath(R.drawable.logo));
                popShare.showAtLocation(binding.settingRv, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
        }
    }

    @Override
    public void onItemLongClick(View view, Setting bean) {

    }

    @Override
    public void info(User.Info info) {
        datas.get(0).icon.set(info.headimgurl.get());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_ACCOUNT:
                LoginManager.getInstance().updateUser(this);
                break;
        }
    }
}
