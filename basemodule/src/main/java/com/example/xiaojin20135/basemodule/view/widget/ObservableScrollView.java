package com.example.xiaojin20135.basemodule.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;


/*
* @author lixiaojin
* create on 2020-03-09 09:43
* description: 监听滚动事件，并能在滚动回调中获取每次滚东前后的偏移量
* 由于 {@link ScrollView} 没有类似于 addOnScrollChangedListener 的方法可以监听滚动事件，
* 所以需要通过重写 {@link android.view.View#onScrollChanged}，来触发滚动监听
 */
public class ObservableScrollView extends ScrollView {

    private int mScrollOffset = 0;

    private List<OnScrollChangedListener> mOnScrollChangedListeners;

    public ObservableScrollView(Context context) {
        super(context);
    }

    public ObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ObservableScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void addOnScrollChangedListener(OnScrollChangedListener onScrollChangedListener) {
        if (mOnScrollChangedListeners == null) {
            mOnScrollChangedListeners = new ArrayList<>();
        }
        if (mOnScrollChangedListeners.contains(onScrollChangedListener)) {
            return;
        }
        mOnScrollChangedListeners.add(onScrollChangedListener);
    }

    public void removeOnScrollChangedListener(OnScrollChangedListener onScrollChangedListener) {
        if (mOnScrollChangedListeners == null) {
            return;
        }
        mOnScrollChangedListeners.remove(onScrollChangedListener);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        mScrollOffset = t;
        if (mOnScrollChangedListeners != null && !mOnScrollChangedListeners.isEmpty()) {
            for (OnScrollChangedListener listener : mOnScrollChangedListeners) {
                listener.onScrollChanged(this, l, t, oldl, oldt);
            }
        }
    }

    public int getScrollOffset() {
        return mScrollOffset;
    }

    public interface OnScrollChangedListener {
        void onScrollChanged(ObservableScrollView scrollView, int l, int t, int oldl, int oldt);
    }

}
