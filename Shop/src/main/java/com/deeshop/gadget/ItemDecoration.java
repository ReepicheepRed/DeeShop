package com.deeshop.gadget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.deeshop.R;
import com.deeshop.util.BitmapUtils;

import org.xutils.x;

/**
 * Created by zhiPeng.S on 2017/3/22.
 */

public class ItemDecoration extends RecyclerView.ItemDecoration {

    private int space,divider;
    private boolean top,bottom,left,right;
    private int leftPadding,rightPadding;
    private int[] leftPosition,rightPosition;
    private Paint paint = new Paint();
    private Resources res;

    public ItemDecoration(int space) {
        this(x.app().getResources());
        this.space = BitmapUtils.dip2px(x.app(),space);
    }

    public ItemDecoration(Context context,int space){
        this(context.getResources());
        this.space = space;
        paint.setColor(Color.WHITE);
    }

    public ItemDecoration(Resources resource){
        res = resource;
        divider = resource.getDimensionPixelSize(R.dimen.height_1px);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if(top)
            outRect.top = space;
        if(bottom)
            outRect.bottom = space;
        if(left)
            outRect.top = space;
        if(right)
            outRect.bottom = space;

//       general divider for 1px
        if(leftPadding <= 0 && rightPadding <= 0) return;
        int position = parent.getChildAdapterPosition(view);
        if(leftPadding > 0){
            for (int j = 0; j < leftPosition.length; j++) {
                if(position == leftPosition[j]){
                    int flag = top ? 0 : bottom ? 1 : -1;
                    switch (flag){
                        case 0:
                            outRect.top = divider;
                            break;
                        case 1:
                            outRect.bottom = divider;
                            break;
                    }
                }
            }
        }

        if(rightPadding > 0){
            for (int j = 0; j < rightPosition.length; j++) {
                if(position == rightPosition[j]){
                    int flag = top ? 0 : bottom ? 1 : -1;
                    switch (flag){
                        case 0:
                            outRect.top = divider;
                            break;
                        case 1:
                            outRect.bottom = divider;
                            break;
                    }
                }
            }
        }
    }

    public void setTop(boolean top) {
        this.top = top;
    }

    public void setBottom(boolean bottom) {
        this.bottom = bottom;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

//       general divider for 1px
        if(leftPadding <= 0 && rightPadding <= 0) return;

        int width = parent.getWidth();
        int left = parent.getPaddingLeft();
        int right = width - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(view);
            float bottom = view.getTop();
            float divideTop = bottom - divider;
            if(leftPadding > 0){
                for (int j = 0; j < leftPosition.length; j++) {
                    if(position == leftPosition[j])
                        c.drawRect(left, divideTop, left+leftPadding, bottom, paint);//绘制矩形
                }
            }

            if(rightPadding > 0){
                for (int j = 0; j < rightPosition.length; j++) {
                    if(position == rightPosition[j])
                        c.drawRect(right-rightPadding, divideTop, right, bottom, paint);//绘制矩形
                }
            }

        }
    }

    public void setPaintColor(int color){
        paint.setColor(color);
    }

    public void setLeftPadding(int leftPadding,int... position) {
        this.leftPadding = leftPadding;
        leftPosition = position;
    }

    public void setRightPadding(int rightPadding,int... position) {
        this.rightPadding = rightPadding;
        rightPosition = position;
    }
}
