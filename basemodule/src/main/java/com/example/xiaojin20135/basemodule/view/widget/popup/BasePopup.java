package com.example.xiaojin20135.basemodule.view.widget.popup;

import android.content.Context;
import android.content.pm.ShortcutInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import java.lang.ref.WeakReference;

/*
* @author lixiaojin
* create on 2020-03-03 14:39
* description:
* 定义PopupWindow
* 定义两个事件：触摸视图边界外监听事件、视图attach到window或者从window dettached事件
*/
public class BasePopup<T extends BasePopup>{
    public static final float DIM_AMOUNT_NOT_EXIST = -1f;
    public static final int NOT_SET = -1; //未设置初始值
    protected final PopupWindow mWindow;
    protected final WindowManager mWindowManager;
    protected Context mContext;
    //悬浮窗销毁时调用
    private PopupWindow.OnDismissListener mDismissListener;
    //触摸视图边界外时是否允许视图销毁标识
    private boolean mDissmissIfOutsideTouch = true;

    protected WeakReference<View> mAttachdViewRf;

    private float mDimAmount = DIM_AMOUNT_NOT_EXIST;
    private int mDimAmountAttr = 0;


    //当触摸发生在视图边界之外的监听事件
    private View.OnTouchListener mOutsideTouchDismissListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            //触摸发生在视图边界外时销毁当前window
            if(event.getAction() == MotionEvent.ACTION_OUTSIDE){
                mWindow.dismiss();
                return true;
            }
            return false;
        }
    };

    /*
    * @author lixiaojin
    * create on 2020-03-03 14:00
    * description: 当一个View attach到Window或者从Window detached的时候会调用
    */
    private View.OnAttachStateChangeListener mOnAttachStateChangeListener = new View.OnAttachStateChangeListener() {
        @Override
        public void onViewAttachedToWindow(View v) {

        }

        @Override
        public void onViewDetachedFromWindow(View v) {
            //当view从window移除时，调用dismiss方法进行清理工作
            dismiss();
        }
    };

    /*
    * @author lixiaojin
    * create on 2020-03-03 14:03
    * description: 构造函数，进行参数初始化
    */
    public BasePopup(Context context){
        mContext = context;
        mWindow = new PopupWindow();
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        initWindow();
    }

    /*
    * @author lixiaojin
    * create on 2020-03-03 10:59
    * description: 初始化相关
    */
    private void initWindow(){
        mWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mWindow.setFocusable(true); //获取焦点
        mWindow.setTouchable(true); //可触摸，可接收触摸事件
        //设置窗口销毁监听事件，当PopupWindow被销毁时调用
        mWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                BasePopup.this.onDismiss();
                if(mDismissListener != null){
                    mDismissListener.onDismiss();
                }
            }
        });
        dismissIfOutsideToch();
    }

    /*
    * @author lixiaojin
    * create on 2020-03-03 14:17
    * description: 试图边界范围外点击准备工作
    */
    public T dismissIfOutsideToch(){
        //设置当前窗口是否允许视图边界范围外点击
        mWindow.setOutsideTouchable(mDissmissIfOutsideTouch);
        if(mDissmissIfOutsideTouch){
            //如果允许边界范围外单击，设置对应的监听事件，发生任何触摸事件都会回调该监听
            mWindow.setTouchInterceptor(mOutsideTouchDismissListener);
        }else{
            mWindow.setTouchInterceptor(null);
        }
        return (T)this;
    }

    public T onDismiss(PopupWindow.OnDismissListener listener){
        mDismissListener = listener;
        return (T) this;
    }

    /*
    * @author lixiaojin
    * create on 2020-03-03 14:22
    * description: 移除attach和detached监听事件
    */
    private void removeOldArrachStateChangeListener(){
        if(mAttachdViewRf != null){
            View oldAttachedView = mAttachdViewRf.get();
            if(oldAttachedView != null){
                oldAttachedView.removeOnAttachStateChangeListener(mOnAttachStateChangeListener);
            }
        }
    }


    /*
    * @author lixiaojin
    * create on 2020-03-03 14:23
    * description: 取得当前的DecorView
    */
    public View getDecorView(){
        View decorView = null;
        try {
            if(mWindow.getBackground() == null){
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    decorView = (View)mWindow.getContentView().getParent();
                }else{
                    decorView = mWindow.getContentView();
                }
            }else{
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    decorView = (View)mWindow.getContentView().getParent().getParent();
                }else{
                    decorView = (View)mWindow.getContentView().getParent();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return decorView;
    }

    /*
    * @author lixiaojin
    * create on 2020-03-03 14:26
    * description:
    */
    protected void showAtLocation(View parent,int x,int y){
        //如果该View已经attached到了window上，返回，不做处理
        if(!ViewCompat.isAttachedToWindow(parent)){
            return;
        }
        //移除旧的attach和dettached事件
        removeOldArrachStateChangeListener();
        //添加新的attach和dettached事件
        parent.addOnAttachStateChangeListener(mOnAttachStateChangeListener);

        mAttachdViewRf = new WeakReference<>(parent);
        //在要求的位置显示窗口
        mWindow.showAtLocation(parent, Gravity.NO_GRAVITY,x,y);

        if (mDimAmount != DIM_AMOUNT_NOT_EXIST) {
            updateDimAmount(mDimAmount);
        }
    }

    private void updateDimAmount(float dimAmount) {
        View decorView = getDecorView();
        if (decorView != null) {
            WindowManager.LayoutParams p = (WindowManager.LayoutParams) decorView.getLayoutParams();
            // 在这个window之后的所有东西都会变暗
            p.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            //与FLAG_DIM_BEHIND搭配使用，该数值指示变暗度
            p.dimAmount = dimAmount;
            modifyWindowLayoutParams(p);
            //将view添加到Window上
            mWindowManager.updateViewLayout(decorView, p);
        }
    }

    protected void modifyWindowLayoutParams(WindowManager.LayoutParams lp) {

    }

    /*
    * @author lixiaojin
    * create on 2020-03-03 14:12
    * description: 悬浮窗从window移除时进行必要的清理工作
    */
    public final void dismiss(){
        //移除attach和dettach监听事件
        removeOldArrachStateChangeListener();
        mAttachdViewRf = null;
        //将窗口销毁
        mWindow.dismiss();
    }


    protected void onDismiss() {

    }
}
