package com.deeshop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by zhiPeng.S on 2017/3/21.
 */

public abstract class BaseAdapter<T extends RecyclerView.ViewHolder,E> extends RecyclerView.Adapter<T> {
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener<E> {
        void onItemClick(View view, E bean);
        void onItemLongClick(View view, E bean);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    protected Context context;
    protected List<E> datas;

    public BaseAdapter(Context context, List<E> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public T onCreateViewHolder(ViewGroup parent, int viewType){
        return getViewHolder(LayoutInflater.from(parent.getContext()),parent);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public void onBindViewHolder(final T holder, final int position){
        final E bean = datas.get(position);
        setValues(holder,bean);
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null)
                onItemClickListener.onItemClick(holder.itemView, bean);
        });

        holder.itemView.setOnLongClickListener(v -> {
            if (onItemClickListener != null)
                onItemClickListener.onItemLongClick(holder.itemView, bean);
            return true;
        });
    }

    /**
     * return ViewHolder
     * @param inflater
     * @param parent
     * @author Reepicheep
     * Created at 2017/3/22 9:54
     */
    protected abstract T getViewHolder(LayoutInflater inflater,ViewGroup parent);
    /**
     * 设置控件数据
     * @param holder
     * @param bean
     */
    protected abstract void setValues(T holder, E bean);

    public List<E> getDatas() {
        return datas;
    }

    public void setDatas(List<E> datas) {
        this.datas = datas;
    }
}
