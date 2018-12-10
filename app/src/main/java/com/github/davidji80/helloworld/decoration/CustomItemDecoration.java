package com.github.davidji80.helloworld.decoration;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 继承RecyclerView.ItemDecoration抽象类
 * 重新实现
 */
public class CustomItemDecoration extends RecyclerView.ItemDecoration {
    /**
     * 通过读取系统主题中的 Android.R.attr.listDivider作为Item间的分割线
     * 支持横向和纵向
     */
    private static final int[] ATTRS = new int[]{
            android.R.attr.listDivider
    };
    /**
     * 定义方向常量及变量
     */
    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;
    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;
    private int mOrientation;
    /**
     * Drawable
     * 在getItemOffsets中，outRect去设置了绘制的范围
     */
    private Drawable mDivider;


    /**
     * 构造函数
     *
     * @param context     上下文
     * @param orientation 方向
     */
    public CustomItemDecoration(Context context, int orientation) {
        //获取到listDivider
        final TypedArray typedArray = context.obtainStyledAttributes(ATTRS);
        //获取到listDivider以后，设置Drawable
        mDivider = typedArray.getDrawable(0);
        typedArray.recycle();
        setOrientation(orientation);
    }

    /**
     * 设置方向
     *
     * @param orientation
     */
    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
    }

    /**
     * 在Item绘制之前被调用，该方法主要用于绘制间隔样式
     *
     * @param c
     * @param parent
     * @param state
     */
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == VERTICAL_LIST) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }

    }

    /**
     * 在Item绘制之后被调用，该方法主要用于绘制间隔样式
     *
     * @param c
     * @param parent
     * @param state
     */
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }

    /**
     * 垂直绘制
     *
     * @param c
     * @param parent
     */
    public void drawVertical(Canvas c, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            android.support.v7.widget.RecyclerView v = new android.support.v7.widget.RecyclerView(parent.getContext());
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    /**
     * 水平绘制
     *
     * @param c
     * @param parent
     */
    public void drawHorizontal(Canvas c, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    /**
     * 设置item的偏移量，偏移的部分用于填充间隔样式，即设置分割线的宽、高
     * RecyclerView继承了ViewGroup，并重写了measureChild()，getItemOffsets方法在onMeasure()中被调用，
     * 用来计算每个child的大小，计算每个child大小的时候就需要加上getItemOffsets()设置的外间距
     *
     * @param outRect 是当前item四周的间距
     * @param view
     * @param parent
     * @param state
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == VERTICAL_LIST) {
            //类似margin属性，现在设置了该item下间距为mDivider.getIntrinsicHeight()
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        } else {
            outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
        }
    }
}
