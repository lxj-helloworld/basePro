package com.example.xiaojin20135.basemodule.view.widget.popup;

import android.content.Context;
import android.content.pm.ShortcutInfo;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
    public static final int NOT_SET = -1; //变量未设置初始值

    protected final PopupWindow mWindow;
    protected WindowManager mWindowManager;
    protected Context mContext;
    protected WeakReference<View> mAttachedViewRf; //弱引用，便于GC时及时回收空间
    private float mDimAmount = DIM_AMOUNT_NOT_EXIST;
    private int mDimAmountAttr = 0;
    private PopupWindow.OnDismissListener mDismissListener; //popup window被dismiss时调用
    private boolean mDismissIfOutsideTouch = true; //是否开启view 边界外点击事件监听

    /*
    * @author lixiaojin
    * create on 2020-03-05 16:34
    * description:当view attach到window或者从window detached时调用
    */
    private View.OnAttachStateChangeListener mOnAttachStateChangeListener = new View.OnAttachStateChangeListener() {
        @Override
        public void onViewAttachedToWindow(View v) {

        }
        @Override
        public void onViewDetachedFromWindow(View v) {
            //detached时进行必要的清理工作
            dismiss();
        }
    };

    /*
    * @author lixiaojin
    * create on 2020-03-05 16:53
    * description: 视图边界外监听事件监听
    */
    private View.OnTouchListener mOutsideTouchDismissListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                mWindow.dismiss();
                return true;
            }
            return false;
        }
    };


    public BasePopup(Context context) {
        mContext = context;
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mWindow = new PopupWindow(context);
        initWindow();
    }

    /*
    * @author lixiaojin
    * create on 2020-03-05 16:57
    * description:
    */
    private void initWindow() {
        mWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mWindow.setFocusable(true); //获取焦点
        mWindow.setTouchable(true); //可点击
        mWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                BasePopup.this.onDismiss();
                if (mDismissListener != null) {
                    mDismissListener.onDismiss();
                }
            }
        });
        //视图外点击事件监听检查
        dismissIfOutsideTouch();
    }

    public T dimAmount(float dimAmount) {
        mDimAmount = dimAmount;
        return (T) this;
    }

    public T dimAmountAttr(int dimAmountAttr) {
        mDimAmountAttr = dimAmountAttr;
        return (T) this;
    }

    /*
    * @author lixiaojin
    * create on 2020-03-05 16:56
    * description: 是否开启视图外点击事件监听
    */
    public T dismissIfOutsideTouch() {
        mWindow.setOutsideTouchable(mDismissIfOutsideTouch);
        if (mDismissIfOutsideTouch) {
            mWindow.setTouchInterceptor(mOutsideTouchDismissListener);
        } else {
            mWindow.setTouchInterceptor(null);
        }
        return (T) this;
    }


    public T onDismiss(PopupWindow.OnDismissListener listener) {
        mDismissListener = listener;
        return (T) this;
    }

    /*
    * @author lixiaojin
    * create on 2020-03-05 16:59
    * description: 移除旧的attach和detached 监听事件
    */
    private void removeOldAttachStateChangeListener() {
        if (mAttachedViewRf != null) {
            View oldAttachedView = mAttachedViewRf.get();
            if (oldAttachedView != null) {
                oldAttachedView.removeOnAttachStateChangeListener(mOnAttachStateChangeListener);
            }
        }
    }


    public View getDecorView() {
        View decorView = null;
        try {
            if (mWindow.getBackground() == null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    decorView = (View) mWindow.getContentView().getParent();
                } else {
                    decorView = mWindow.getContentView();
                }
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    decorView = (View) mWindow.getContentView().getParent().getParent();
                } else {
                    decorView = (View) mWindow.getContentView().getParent();
                }
            }
        } catch (Exception ignore) {

        }
        return decorView;
    }

    /*
    * @author lixiaojin
    * create on 2020-03-05 17:00
    * description: 在指定的位置显示
    */
    protected void showAtLocation(@NonNull View parent, int x, int y) {
        if (!ViewCompat.isAttachedToWindow(parent)) {
            return;
        }
        //移除旧的监听事件 添加新的监听事件
        removeOldAttachStateChangeListener();
        parent.addOnAttachStateChangeListener(mOnAttachStateChangeListener);
        mAttachedViewRf = new WeakReference<>(parent);
        mWindow.showAtLocation(parent, Gravity.NO_GRAVITY, x, y);
        if (mDimAmount != DIM_AMOUNT_NOT_EXIST) {
            updateDimAmount(mDimAmount);
        }
    }

    /*
    * @author lixiaojin
    * create on 2020-03-05 17:16
    * description: 遮罩处理
    */
    private void updateDimAmount(float dimAmount) {
        View decorView = getDecorView();
        if (decorView != null) {
            WindowManager.LayoutParams p = (WindowManager.LayoutParams) decorView.getLayoutParams();
            //window标识，在其之后的所有内容都将变淡
            p.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            //变淡度
            p.dimAmount = dimAmount;
            mWindowManager.updateViewLayout(decorView, p);
        }
    }

    protected void onDismiss() {

    }

    /*
    * @author lixiaojin
    * create on 2020-03-05 16:53
    * description: 清理工作
    */
    public final void dismiss() {
        //移除attach和detached监听事件
        removeOldAttachStateChangeListener();
        //方便内存回收
        mAttachedViewRf = null;
        mWindow.dismiss();
    }
}
