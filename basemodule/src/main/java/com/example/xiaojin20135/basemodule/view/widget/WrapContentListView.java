package com.example.xiaojin20135.basemodule.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import java.util.List;

public class WrapContentListView extends ListView {
    private int mMaxHeight = Integer.MAX_VALUE >> 2;

    public WrapContentListView(Context context){
        super(context);
    }

    public WrapContentListView(Context context, int maxHeight) {
        super(context);
        mMaxHeight = maxHeight;
    }

    public WrapContentListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WrapContentListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setMaxHeight(int maxHeight) {
        if(mMaxHeight != maxHeight){
            mMaxHeight = maxHeight;
            requestLayout();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(mMaxHeight, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
