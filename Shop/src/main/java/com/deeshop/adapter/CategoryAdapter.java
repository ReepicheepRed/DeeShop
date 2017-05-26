package com.deeshop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.deeshop.bean.Category.GoodtypesBean;
import com.deeshop.databinding.ItemCategoryBinding;

import java.util.List;

/**
 * Created by zhiPeng.S on 2017/3/21.
 */

public class CategoryAdapter extends BaseAdapter<CategoryAdapter.ViewHolder,GoodtypesBean> {

    public CategoryAdapter(Context context, List<GoodtypesBean> datas) {
        super(context, datas);
    }

    @Override
    protected ViewHolder getViewHolder(LayoutInflater inflater, ViewGroup parent) {
        return ViewHolder.create(inflater,parent);
    }

    @Override
    protected void setValues(ViewHolder holder, GoodtypesBean bean) {
        holder.bindTo(bean);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private ItemCategoryBinding binding;
        
        static ViewHolder create(LayoutInflater inflater, ViewGroup parent){
            ItemCategoryBinding binding = ItemCategoryBinding.inflate(inflater,parent,false);
            return new ViewHolder(binding);
        }
        private ViewHolder(ItemCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        
        public void bindTo(GoodtypesBean bean){
            binding.setCategory(bean);
            binding.executePendingBindings();
        }
    }
}
