package com.deeshop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.deeshop.bean.Market;
import com.deeshop.databinding.ItemMarketBinding;

import java.util.List;

/**
 * Created by zhiPeng.S on 2017/3/21.
 */

public class MarketAdapter extends BaseAdapter<MarketAdapter.ViewHolder,Market.GoodlistBean> {

    public MarketAdapter(Context context, List<Market.GoodlistBean> datas) {
        super(context, datas);
    }

    @Override
    protected ViewHolder getViewHolder(LayoutInflater inflater, ViewGroup parent) {
        return ViewHolder.create(inflater,parent);
    }

    @Override
    protected void setValues(ViewHolder holder, Market.GoodlistBean bean) {
        holder.bindTo(bean);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private ItemMarketBinding binding;
        
        static ViewHolder create(LayoutInflater inflater, ViewGroup parent){
            ItemMarketBinding binding = ItemMarketBinding.inflate(inflater,parent,false);
            return new ViewHolder(binding);
        }
        private ViewHolder(ItemMarketBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        
        public void bindTo(Market.GoodlistBean bean){
            binding.setGoods(bean);
            binding.executePendingBindings();
        }
    }
}
