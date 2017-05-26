package com.deeshop.gadget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;

import com.deeshop.R;
import com.deeshop.util.BitmapUtils;

/**
 * Created by zhiPeng.S on 2017/3/28.
 */

public class SectionDecoration extends RecyclerView.ItemDecoration {
    private static final String TAG = "SectionDecoration";

    private TextPaint textPaint;
    private Paint paint;
    private int topGap;
    private Paint.FontMetrics fontMetrics;
    private Context context;
    private Resources res;
    public SectionDecoration(Context context) {
        res = context.getResources();
        this.context = context;

        paint = new Paint();

        textPaint = new TextPaint();
        textPaint.setTypeface(Typeface.DEFAULT);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(BitmapUtils.dip2px(26/2));
        textPaint.setColor(res.getColor(R.color.gray_b7_c));
        textPaint.getFontMetrics(fontMetrics);
        textPaint.setTextAlign(Paint.Align.LEFT);
        fontMetrics = new Paint.FontMetrics();
        topGap = res.getDimensionPixelSize(R.dimen.height_80px);


    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int pos = parent.getChildAdapterPosition(view);
        switch (pos){
            case 4:
                outRect.top = topGap;
                break;
            case 0:
                outRect.top = BitmapUtils.dip2px(20/2);
                break;
            default:
                outRect.top = res.getDimensionPixelSize(R.dimen.height_1px);
        }

    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(view);
            String textLine = context.getString(R.string.contact_information);
            float bottom = view.getTop();
            if (position == 4) {
                paint.setColor(Color.TRANSPARENT);
                float top = view.getTop() - topGap;
                int leftMargin = res.getDimensionPixelSize(R.dimen.margin_28px);
                int bottomMargin = res.getDimensionPixelSize(R.dimen.margin_14px);
                c.drawRect(left, top, right, bottom, paint);//绘制矩形
                c.drawText(textLine, left+leftMargin, bottom-bottomMargin, textPaint);//绘制文本
            }else if(position != 0){
                paint.setColor(Color.WHITE);
                int divideTop = view.getTop() - res.getDimensionPixelSize(R.dimen.height_1px);
                int divideRight = res.getDimensionPixelSize(R.dimen.margin_28px);
                c.drawRect(left, divideTop, divideRight, bottom, paint);//绘制矩形

            }
        }
    }

}
