package com.deeshop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.deeshop.databinding.ItemEgoodsBinding;
import com.deeshop.bean.Product.GoodlistBean.Images;

import java.util.List;

/**
 * Created by zhiPeng.S on 2017/3/31.
 */

public class EGoodsAdapter extends BaseAdapter<EGoodsAdapter.ViewHolder,Images> {

    public EGoodsAdapter(Context context, List<Images> datas) {
        super(context, datas);
    }

    @Override
    protected ViewHolder getViewHolder(LayoutInflater inflater, ViewGroup parent) {
        return ViewHolder.create(inflater,parent);
    }

    @Override
    protected void setValues(ViewHolder holder, Images bean) {
        holder.bindTo(bean);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private ItemEgoodsBinding binding;

        static EGoodsAdapter.ViewHolder create(LayoutInflater inflater, ViewGroup parent){
            ItemEgoodsBinding binding = ItemEgoodsBinding.inflate(inflater,parent,false);
            return new EGoodsAdapter.ViewHolder(binding);
        }
        private ViewHolder(ItemEgoodsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindTo(Images bean){
            binding.setGoods(bean);
            binding.executePendingBindings();
        }
    }
}
