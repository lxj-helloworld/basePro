package com.example.xiaojin20135.basemodule.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * 张龙飞
 */

public class SlideCloseLayout extends FrameLayout {

    private int previousY;
    private int previousX;
    private boolean isScrollingUp;
    private boolean isLocked = false;
    private LayoutScrollListener mScrollListener;
    private Drawable mBackground;

    public SlideCloseLayout(Context context) {
        this(context, null);
    }

    public SlideCloseLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideCloseLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setLayoutScrollListener(LayoutScrollListener listener) {
        mScrollListener = listener;
    }

    /**
     * 设置渐变的背景
     *
     * @param drawable
     */
    public void setGradualBackground(Drawable drawable) {
        this.mBackground = drawable;
    }

    public void lock() {
        isLocked = true;
    }

    public void unLock() {
        isLocked = false;
    }


    /**
     * 当垂直滑动时，拦截子view的滑动事件
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isLocked && ev.getPointerCount() > 1) {
            return false;
        } else {
            final int y = (int) ev.getRawY();
            final int x = (int) ev.getRawX();
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    previousX = x;
                    previousY = y;
                    break;
                case MotionEvent.ACTION_MOVE:
                    int diffY = y - previousY;
                    int diffX = x - previousX;

                    if (Math.abs(diffX)  < Math.abs(diffY)) {
                        return true;
                    }
                    break;
            }
            return false;
        }

    }

    public interface LayoutScrollListener {
        //关闭布局
        void onLayoutClosed();

        //正在滑动
        void onLayoutScrolling(float alpha);

        //滑动结束并且没有触发关闭
        void onLayoutScrollRevocer();
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public boolean onTouchEvent(@NonNull MotionEvent ev) {
        if (!isLocked) {
            final int y = (int) ev.getRawY();
            final int x = (int) ev.getRawX();
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    previousX = x;
                    previousY = y;
                    break;
                case MotionEvent.ACTION_MOVE:
                    int diffY = y - previousY;

                    //当方向为垂直方向时，移动布局并改变透明度

                    isScrollingUp = diffY <= 0;
                    this.setTranslationY(diffY);

                        int alpha = (int) (255 * Math.abs(diffY * 1f)) / getHeight();
                    if (mBackground != null) {
                        mBackground.setAlpha(255 - alpha);
                    }
                        mScrollListener.onLayoutScrolling(alpha / 255f);

                    break;
                case MotionEvent.ACTION_UP:
                    int height = this.getHeight();
                    //判断滑动距离是否大于height/7
                    if (Math.abs(getTranslationY()) > (height / 6)) {
                        //执行退出动画
                        layoutExitAnim();
                    } else {
                        //执行恢复动画
                        layoutRecoverAnim();
                    }

            }
        }
        return false;
    }

    /**
     * 退出布局的动画
     *
     * @param
     * @param
     */
    public void layoutExitAnim() {
        ObjectAnimator exitAnim;

            exitAnim = ObjectAnimator.ofFloat(this, "translationY", getTranslationY(), isScrollingUp ? -getHeight() : getHeight());

        exitAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (mBackground != null) {
                    mBackground.setAlpha(0);
                }
                if (mScrollListener != null) {
                    mScrollListener.onLayoutClosed();
                }

            }
        });
        exitAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (mBackground != null) {
                    int alpha = (int) (255 * Math.abs(getTranslationY() * 1f)) / getHeight();
                    mBackground.setAlpha(255 - alpha);
                }
            }
        });
        exitAnim.setDuration(200);
        exitAnim.start();
    }

    /**
     * 恢复动画
     */
    private void layoutRecoverAnim() {
        ObjectAnimator recoverAnim = ObjectAnimator.ofFloat(this, "translationY", this.getTranslationY(), 0);
        recoverAnim.setDuration(250);
        recoverAnim.start();
        if (mBackground != null) {
            mBackground.setAlpha(255);
            mScrollListener.onLayoutScrollRevocer();
        }
    }


}