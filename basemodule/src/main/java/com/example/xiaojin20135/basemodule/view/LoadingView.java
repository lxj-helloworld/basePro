package com.example.xiaojin20135.basemodule.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.example.xiaojin20135.basemodule.R;
import com.example.xiaojin20135.basemodule.util.ui.DisplayHelper;

import java.util.HashMap;

/*
* @author lixiaojin
* create on 2020-03-11 10:34
* description: 显示加载中的View，支持颜色和大小的设置
*/
public class LoadingView extends View {
    private int mSize;
    private int mPaintColor;
    private int mAnimateValue = 0;
    private ValueAnimator mAnimator;
    private Paint mPaint;
    private static final int LINE_COUNT = 12; //等待框中短线的个数
    private static final int DEGREE_PER_LINE = 360 / LINE_COUNT; //计算每次动画旋转的度数
    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.LoadingStyle);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-11 16:48
    * description: 基于样式文件初始化
    */
    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //设置自定义属性，包括等待视图大小以及颜色
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.LoadingView, defStyleAttr, 0);
        mSize = array.getDimensionPixelSize(R.styleable.LoadingView_loading_view_size, DisplayHelper.dp2px(context, 50));
        mPaintColor = array.getInt(R.styleable.LoadingView_android_color, Color.WHITE);
        array.recycle();
        initPaint();
    }

    /*
    * @author lixiaojin
    * create on 2020-03-11 16:48
    * description: 基于具体参数值初始化
    */
    public LoadingView(Context context, int size, int color) {
        super(context);
        mSize = size;
        mPaintColor = color;
        initPaint();
    }

    /*
    * @author lixiaojin
    * create on 2020-03-11 16:49
    * description: 初始化画笔，设置画笔颜色、消除锯齿以及画笔线帽样式
    */
    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(mPaintColor);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-11 16:51
    * description: 设置等待框(画笔)颜色
    */
    public void setColor(int color) {
        mPaintColor = color;
        mPaint.setColor(color);
        invalidate();
    }

    /*
    * @author lixiaojin
    * create on 2020-03-11 16:51
    * description: 设置等待框大小
    */
    public void setSize(int size) {
        mSize = size;
        requestLayout();
    }

    /*
    * @author lixiaojin
    * create on 2020-03-11 16:51
    * description: 动画监听事件，获取当前动画中运动点的值
    */
    private ValueAnimator.AnimatorUpdateListener mUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            mAnimateValue = (int) animation.getAnimatedValue();
            invalidate();
        }
    };

    /*
    * @author lixiaojin
    * create on 2020-03-11 16:52
    * description: 开启动画
    */
    public void start() {
        if (mAnimator == null) {
            //设置一个值从0到11的动画
            mAnimator = ValueAnimator.ofInt(0, LINE_COUNT - 1);
            mAnimator.addUpdateListener(mUpdateListener);
            mAnimator.setDuration(600); //设置动画间隔
            mAnimator.setRepeatMode(ValueAnimator.RESTART); //动画循环模式
            mAnimator.setRepeatCount(ValueAnimator.INFINITE); //无限循环
            mAnimator.setInterpolator(new LinearInterpolator());
            mAnimator.start();
        } else if (!mAnimator.isStarted()) {
            //如果已经存在过并且未开启，直接启动动画
            mAnimator.start();
        }
    }

    /*
    * @author lixiaojin
    * create on 2020-03-11 17:01
    * description: 停止动画，先移除监听函数，再取消动画， 设置为null是为了方便空间回收
    */
    public void stop() {
        if (mAnimator != null) {
            mAnimator.removeUpdateListener(mUpdateListener);
            mAnimator.removeAllUpdateListeners();
            mAnimator.cancel();
            mAnimator = null;
        }
    }

    /*
    * @author lixiaojin
    * create on 2020-03-11 17:02
    * description: 绘制加载动画
    * rotateDegrees为当前动画中已经旋转的度数
    */
    private void drawLoading(Canvas canvas, int rotateDegrees) {
        //画笔宽度设置为总大小的十二分之一，短线的长度设置为总大小的六分之一
        int width = mSize / 12, height = mSize / 6;
        mPaint.setStrokeWidth(width);

        //
        canvas.rotate(rotateDegrees, mSize / 2, mSize / 2);
        canvas.translate(mSize / 2, mSize / 2);

        //每次旋转十二分之一，绘制短线
        for (int i = 0; i < LINE_COUNT; i++) {
            canvas.rotate(DEGREE_PER_LINE);
            mPaint.setAlpha((int) (255f * (i + 1) / LINE_COUNT));
            canvas.translate(0, -mSize / 2 + width / 2); //短线的长度为总宽度的六分之一，将画布平移到短线的起点
            canvas.drawLine(0, 0, 0, height, mPaint);
            canvas.translate(0, mSize / 2 - width / 2); //绘制完短线后，将画布平移回原先的位置
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(mSize, mSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int saveCount = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
        drawLoading(canvas, mAnimateValue * DEGREE_PER_LINE);
        canvas.restoreToCount(saveCount);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == VISIBLE) {
            start();
        } else {
            stop();
        }
    }
    
}

