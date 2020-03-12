package com.example.xiaojin20135.basemodule.view.widget.dialog;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import com.example.xiaojin20135.basemodule.layout.UIConstraintLayout;


/*
* @author lixiaojin
* create on 2020-03-09 17:07
* description: 菜单类View
* 重在监听绘制开始以及绘制完成事件
*/
public class DialogView  extends UIConstraintLayout {
    //绘制过程监听
    private OnDecorationListener mOnDecorationListener;

    public DialogView(Context context) {
        this(context, null);
    }

    public DialogView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DialogView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //设置绘制过程监听事假
    public void setOnDecorationListener(OnDecorationListener onDecorationListener) {
        mOnDecorationListener = onDecorationListener;
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 17:10
    * description: layout 后，绘制View
    */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //如果监听事件不为空，分发绘制事件
        if (mOnDecorationListener != null) {
            mOnDecorationListener.onDraw(canvas, this);
        }
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 17:10
    * description: 绘制子View
    */
    @Override
    public void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        //如果监听事件不为空，分发绘制完成事件
        if (mOnDecorationListener != null) {
            mOnDecorationListener.onDrawOver(canvas, this);
        }
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 17:08
    * description: 绘制过程监听
    */
    public interface OnDecorationListener {
        //开始绘制
        void onDraw(Canvas canvas, DialogView view);
        //绘制结束
        void onDrawOver(Canvas canvas, DialogView view);
    }
}
