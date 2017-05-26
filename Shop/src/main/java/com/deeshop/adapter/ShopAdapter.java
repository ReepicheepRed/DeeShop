package com.deeshop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.deeshop.R;
import com.deeshop.bean.Market;
import com.deeshop.bean.Shop;
import com.deeshop.databinding.ItemShopBinding;
import com.deeshop.databinding.ItemShopBinding;

import java.util.List;

/**
 * Created by zhiPeng.S on 2017/3/21.
 */

public class ShopAdapter extends BaseAdapter<ShopAdapter.ViewHolder,Shop.Info> {

    public ShopAdapter(Context context, List<Shop.Info> datas) {
        super(context, datas);
    }

    @Override
    protected ViewHolder getViewHolder(LayoutInflater inflater, ViewGroup parent) {
        return ViewHolder.create(inflater,parent);
    }

    @Override
    protected void setValues(ViewHolder holder, Shop.Info bean) {
        int position = holder.getAdapterPosition();
        if(position == 0)
            holder.setHeight(context.getResources().getDimensionPixelSize(R.dimen.height_180px));
        holder.bindTo(bean);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private ItemShopBinding binding;
        
        static ViewHolder create(LayoutInflater inflater, ViewGroup parent){
            ItemShopBinding binding = ItemShopBinding.inflate(inflater,parent,false);
            return new ViewHolder(binding);
        }
        private ViewHolder(ItemShopBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        
        public void bindTo(Shop.Info bean){

            binding.setShop(bean);
            binding.executePendingBindings();
        }

        private void setHeight(int height){
            binding.getRoot().getLayoutParams().height = height;
        }
    }
}
