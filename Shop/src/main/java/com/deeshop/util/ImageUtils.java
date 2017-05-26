package com.deeshop.util;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.deeshop.R;

import org.xutils.x;

/**
 * Created by zhiPeng.S on 2017/3/22.
 */

public class ImageUtils {

    @BindingAdapter(value = {"image" ,"default"}, requireAll = false)
    public static void imageLoader(ImageView imageView, String url,Drawable defaultImage){
        Context context = imageView.getContext();
        Glide.with(context).load(url)
                .placeholder(defaultImage)
                .error(defaultImage)
                .into(imageView);
    }

    @BindingAdapter({"circleIcon"})
    public static void circleAvatar(ImageView imageView, String url){
        Context context = imageView.getContext();
        Glide.with(context)
                .load(url)
                .placeholder(R.mipmap.touxiang_img_default)
                .error(R.mipmap.touxiang_img_default)
                .transform(new GlideCircleTransform(context))
                .into(imageView);

    }

    @BindingAdapter({"circleIcon","isCircle"})
    public static void circleIcon(ImageView imageView, String url,boolean isCircle){
        if(isCircle)
            circleAvatar(imageView, url);
        else
            imageLoader(imageView, url,null);
    }

    public static class GlideCircleTransform extends BitmapTransformation {
        public GlideCircleTransform(Context context) {
            super(context);
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return circleCrop(pool, toTransform);
        }

        private Bitmap circleCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;
            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;
            // TODO this could be acquired from the pool too
            Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);
            Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            }
            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);
            return result;
        }

        @Override
        public String getId() {
            return getClass().getName();
        }
    }

    public static String getDrawablePath(int drawable){
        Resources resources = x.app().getResources();
        return ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                + resources.getResourcePackageName(drawable) + "/"
                + resources.getResourceTypeName(drawable) + "/"
                + resources.getResourceEntryName(drawable);
    }
}
