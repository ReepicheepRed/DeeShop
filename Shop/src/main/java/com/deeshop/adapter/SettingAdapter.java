package com.deeshop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.deeshop.R;
import com.deeshop.bean.Setting;
import com.deeshop.databinding.ItemSettingBinding;

import java.util.List;

/**
 * Created by zhiPeng.S on 2017/3/21.
 */

public class SettingAdapter extends BaseAdapter<SettingAdapter.ViewHolder,Setting> {

    public SettingAdapter(Context context, List<Setting> datas) {
        super(context, datas);
    }

    @Override
    protected ViewHolder getViewHolder(LayoutInflater inflater, ViewGroup parent) {
        return ViewHolder.create(inflater,parent);
    }

    @Override
    protected void setValues(ViewHolder holder, Setting bean) {
        if (holder.getAdapterPosition() == 0){
            holder.setHeight(context.getResources().getDimensionPixelSize(R.dimen.height_218px));
            holder.setIconSize(context.getResources().getDimensionPixelSize(R.dimen.height_130px));
        }
        holder.bindTo(bean);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private ItemSettingBinding binding;
        
        static ViewHolder create(LayoutInflater inflater, ViewGroup parent){
            ItemSettingBinding binding = ItemSettingBinding.inflate(inflater,parent,false);
            return new ViewHolder(binding);
        }
        private ViewHolder(ItemSettingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        
        private void bindTo(Setting bean){
            binding.setSetting(bean);
            binding.executePendingBindings();
        }

        private void setHeight(int height){
            binding.getRoot().getLayoutParams().height = height;
        }

        private void setIconSize(int size){
            binding.settingIcon.getLayoutParams().height = size;
            binding.settingIcon.getLayoutParams().width = size;

        }
    }
}
