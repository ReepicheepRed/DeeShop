package com.deeshop.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.deeshop.R;
import com.deeshop.bean.Market;
import com.deeshop.bean.Pop;
import com.deeshop.bean.Product;
import com.deeshop.databinding.ItemProductsBinding;
import com.deeshop.databinding.ItemProductsBinding;
import com.deeshop.gadget.PopPhoto;
import com.deeshop.gadget.PopPromotion;
import com.deeshop.gadget.PopShare;
import com.deeshop.manager.LoginManager;
import com.deeshop.manager.NetManager;
import com.deeshop.manager.ShareManager;
import com.deeshop.manager.WebManager;
import com.deeshop.util.Constant;
import com.deeshop.util.FileUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhiPeng.S on 2017/3/21.
 */

public class ProductAdapter extends BaseAdapter<ProductAdapter.ViewHolder,Product.GoodlistBean> {

    public ProductAdapter(Context context, List<Product.GoodlistBean> datas) {
        super(context, datas);
    }

    @Override
    protected ViewHolder getViewHolder(LayoutInflater inflater, ViewGroup parent) {
        ViewHolder viewHolder = ViewHolder.create(inflater,parent);
        viewHolder.setCallback(callback);
        return viewHolder;
    }

    @Override
    protected void setValues(ViewHolder holder, Product.GoodlistBean bean) {
        holder.bindTo(bean);
    }

    private NetManager.Callback callback;

    public void setCallback(NetManager.Callback callback) {
        this.callback = callback;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,PromotionAdapter.OnItemClickListener{
        private ItemProductsBinding binding;
        private PopPromotion menuWindow;
        private PopShare popShare;
        private Pop pop = new Pop();
        private Context context;
        static ViewHolder create(LayoutInflater inflater, ViewGroup parent){
            ItemProductsBinding binding = ItemProductsBinding.inflate(inflater,parent,false);
            return new ViewHolder(binding);
        }
        private ViewHolder(ItemProductsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.context = binding.getRoot().getContext();
            menuWindow = new PopPromotion(context, this);
            popShare = new PopShare(context,pop);
            binding.productsPreviewTv.setOnClickListener(this);
            binding.productsPromotionTv.setOnClickListener(this);
            binding.productsShareTv.setOnClickListener(this);
            binding.productsPreviewTv2.setOnClickListener(this);
            binding.productsOnSaleTv.setOnClickListener(this);
        }
        
        public void bindTo(Product.GoodlistBean bean){
            binding.setGoods(bean);
            binding.executePendingBindings();
        }

        private NetManager.Callback callback;

        public void setCallback(NetManager.Callback callback) {
            this.callback = callback;
        }

        @Override
        public void onClick(View v) {
            String goodId = binding.getGoods().goodid.get();
            String img = binding.getGoods().goodimgurl.get();
            String userId = LoginManager.user(context).userid.get();
            switch (v.getId()){
                case R.id.products_preview_tv:
                case R.id.products_preview_tv2:
                    String url = WebManager.GOODS + goodId;
                    WebManager.getInstance().goWeb(context,url);
                    break;
                case R.id.products_promotion_tv:
                    menuWindow.showAtLocation(binding.productsPromotionTv, Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL,0,0);
                    break;
                case R.id.products_share_tv:
                    pop.string1.set(WebManager.SHOP + goodId);
                    pop.string2.set(img);
                    popShare.showAtLocation(binding.productsShareTv,Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                    break;
                case R.id.products_on_sale_tv:
                    NetManager.commitSaleOrSoldOut(userId,goodId,true,callback);
                    break;
            }
        }

        @Override
        public void onItemClick(View view, Object object) {
            if(object instanceof Pop) {
                String[] strings = context.getResources().getStringArray(R.array.promotion);
                Product.GoodlistBean bean = binding.getGoods();
                Pop pop = (Pop)object;
                int position = 0;
                for (int i = 0; i < strings.length; i++) {
                    if(pop.string1.get().equals(strings[i]))
                        position = i;
                }
                switch (position){
                    case 0:
                        ArrayList<String> urls = new ArrayList<>();
                        for (Product.GoodlistBean.Images image: bean.goodimgs) {
                            urls.add(image.path.get());
                        }
                        FileUtils.savePicture(urls);
                        break;
                    case 1:
                        ShareManager.copy(bean.goodname.get() + bean.link.get());
                        break;
                    case 2:
                        ShareManager.copy(bean.goodname.get());
                        break;
                    case 3:
                        ShareManager.copy(bean.link.get());
                        break;
                    case 4:
                        break;
                }
                menuWindow.dismiss();
            }
        }

        @Override
        public void onItemLongClick(View view, Object bean) {

        }
    }
}
