package com.example.xiaojin20135.basemodule.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;


public class WrapContentScrollView extends ObservableScrollView {
    private int mMaxHeight = Integer.MAX_VALUE >> 2;

    public WrapContentScrollView(Context context) {
        super(context);
    }

    public WrapContentScrollView(Context context, int maxHeight) {
        super(context);
        mMaxHeight = maxHeight;
    }

    public WrapContentScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WrapContentScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 09:46
    * description: 设定最大高度
    */
    public void setMaxHeight(int maxHeight) {
        if (mMaxHeight != maxHeight) {
            mMaxHeight = maxHeight;
            requestLayout();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        ViewGroup.LayoutParams lp = getLayoutParams();
        int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);
        //高度取测量高度与限定的最大高度之间的较小值
        int maxHeight = Math.min(heightSize, mMaxHeight);
        int expandSpec;
        if (lp != null && lp.height > 0 && lp.height <= mMaxHeight) {
            //如果能确定高度确切值，并且高度符合条件，测量模式位确切模式
            expandSpec = View.MeasureSpec.makeMeasureSpec(lp.height, View.MeasureSpec.EXACTLY);
        } else {
            //如果高度去了最大值，测量模式使用最大化模式
            expandSpec = View.MeasureSpec.makeMeasureSpec(maxHeight, View.MeasureSpec.AT_MOST);
        }
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
