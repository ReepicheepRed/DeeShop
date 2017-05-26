package com.deeshop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.deeshop.bean.Pop;
import com.deeshop.databinding.ItemPromotionBinding;

import java.util.List;

/**
 * Created by zhiPeng.S on 2017/3/31.
 */

public class PromotionAdapter extends BaseAdapter<PromotionAdapter.ViewHolder,Pop> {

    public PromotionAdapter(Context context, List<Pop> datas) {
        super(context, datas);
    }

    @Override
    protected ViewHolder getViewHolder(LayoutInflater inflater, ViewGroup parent) {
        return ViewHolder.create(inflater,parent);
    }

    @Override
    protected void setValues(ViewHolder holder, Pop bean) {
        holder.bindTo(bean);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private ItemPromotionBinding binding;

        static PromotionAdapter.ViewHolder create(LayoutInflater inflater, ViewGroup parent){
            ItemPromotionBinding binding = ItemPromotionBinding.inflate(inflater,parent,false);
            return new PromotionAdapter.ViewHolder(binding);
        }
        private ViewHolder(ItemPromotionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindTo(Pop bean){
            binding.setPop(bean);
            binding.executePendingBindings();
        }
    }
}
