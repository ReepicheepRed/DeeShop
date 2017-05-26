package com.deeshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.deeshop.bean.Detail.GooddetialBean.GoodimgBean;
import com.deeshop.databinding.ItemBannerBinding;
import org.xutils.image.ImageOptions;

import java.util.List;

public class BannerDetailAdapter extends ListBaseAdapter<GoodimgBean> {

	private LayoutInflater mInflater;
	private ImageOptions imageOptions;
	private ViewHolder holder;
	private int datasSize;

	public BannerDetailAdapter(Context context, List<GoodimgBean> list) {
		super(context,list);
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageOptions = new ImageOptions.Builder()
				.build();
	}

	@Override
	public int getCount() {
		return Integer.MAX_VALUE;   //设置成最大值来无限循环
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		holder = ViewHolder.createViewHolder(LayoutInflater.from(parent.getContext()),parent);
		convertView = holder.getBinding().getRoot();

        datasSize = getData().size();
		if(getData()==null || datasSize == 0){
			return  convertView;
		}

		final GoodimgBean bean = getData().get(position % datasSize);
		holder.bindTo(bean);
		return convertView;
	}

	private static class ViewHolder {
		private ItemBannerBinding binding;
		private GoodimgBean bean;
		static ViewHolder createViewHolder(LayoutInflater inflater, ViewGroup parent){
			ItemBannerBinding binding = ItemBannerBinding.inflate(inflater,parent,false);
			return new ViewHolder(binding);
		}
		private ViewHolder(ItemBannerBinding binding){
			super();
			this.binding = binding;
		}

		public ItemBannerBinding getBinding() {
			return binding;
		}

		public void bindTo(GoodimgBean bean){
			this.bean = bean;
			binding.setDetailBanner(bean);
			binding.executePendingBindings();
		}
	}


}
