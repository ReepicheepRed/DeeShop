package com.deeshop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.deeshop.bean.Message;
import com.deeshop.databinding.ItemMessageBinding;
import com.deeshop.databinding.ItemMessageBinding;

import java.util.List;

/**
 * Created by zhiPeng.S on 2017/3/31.
 */

public class MessageAdapter extends BaseAdapter<MessageAdapter.ViewHolder,Message> {

    public MessageAdapter(Context context, List<Message> datas) {
        super(context, datas);
    }

    @Override
    protected ViewHolder getViewHolder(LayoutInflater inflater, ViewGroup parent) {
        return ViewHolder.create(inflater,parent);
    }

    @Override
    protected void setValues(ViewHolder holder, Message bean) {
        holder.bindTo(bean);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private ItemMessageBinding binding;

        static MessageAdapter.ViewHolder create(LayoutInflater inflater, ViewGroup parent){
            ItemMessageBinding binding = ItemMessageBinding.inflate(inflater,parent,false);
            return new MessageAdapter.ViewHolder(binding);
        }
        private ViewHolder(ItemMessageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindTo(Message bean){
            binding.setMsg(bean);
            binding.executePendingBindings();
        }
    }
}
