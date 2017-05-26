package com.deeshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.deeshop.DetailActivity;
import com.deeshop.R;
import com.deeshop.bean.Banner.BannerlistBean;
import com.deeshop.databinding.ItemBannerBinding;
import com.deeshop.manager.WebManager;
import com.deeshop.util.Constant;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

public class BannerAdapter extends ListBaseAdapter<BannerlistBean> {

	private LayoutInflater mInflater;
	private ImageOptions imageOptions;
	private ViewHolder holder;
	private int datasSize;

	public BannerAdapter(Context context, List<BannerlistBean> list) {
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

		final BannerlistBean bean = getData().get(position % datasSize);
		holder.bindTo(bean);
		return convertView;
	}

	private static class ViewHolder implements OnClickListener{
		private ItemBannerBinding binding;
		private BannerlistBean bean;
		private Context context;
		static ViewHolder createViewHolder(LayoutInflater inflater, ViewGroup parent){
			ItemBannerBinding binding = ItemBannerBinding.inflate(inflater,parent,false);
			return new ViewHolder(binding);
		}
		private ViewHolder(ItemBannerBinding binding){
			super();
			this.binding = binding;
			this.context = binding.getRoot().getContext();
			binding.bannerIv.setOnClickListener(this);
		}

		public ItemBannerBinding getBinding() {
			return binding;
		}

		public void bindTo(BannerlistBean bean){
			this.bean = bean;
			binding.setBanner(bean);
			binding.executePendingBindings();
		}

		@Override
		public void onClick(View v) {
			int fdi = bean.type.get();
			switch(fdi){
				case 0:
					obtainDetailWebInfo();
					break;
				case 1:
					obtainDetailInfo();
					break;
			}
		}

		private void obtainDetailInfo(){
			Intent intent = new Intent(context, DetailActivity.class);
			intent.putExtra("goodId",bean.goodid.get());
			context.startActivity(intent);
		}

		private void obtainDetailWebInfo(){
			WebManager.getInstance().goWeb(context,bean.link.get());
		}
	}
}
