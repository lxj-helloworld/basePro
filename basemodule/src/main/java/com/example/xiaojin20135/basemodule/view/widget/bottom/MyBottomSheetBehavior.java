package com.example.xiaojin20135.basemodule.view.widget.bottom;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.xiaojin20135.basemodule.util.LogUtils;

/*
* @author lixiaojin
* create on 2020-03-20 16:29
* description: BottomSheet 行为
* 控制BottomSheet的展开以及折叠
*/
public class MyBottomSheetBehavior <V extends ViewGroup> extends BottomSheetBehavior<V> {
    private static final String TAG = "MyBottomSheetBehavior";

    private boolean mAllowDrag = true;//是否允许拖拽
    private boolean mMotionEventCanDrag = true;//按下事件是否允许拖拽
    private DownDragDecisionMaker mDownDragDecisionMaker;

    /*
    * @author lixiaojin
    * create on 2020-03-20 16:39
    * description: 设置是否允许拖拽
    */
    public void setAllowDrag(boolean allowDrag) {
        mAllowDrag = allowDrag;
    }

    public void setDownDragDecisionMaker(DownDragDecisionMaker downDragDecisionMaker) {
        mDownDragDecisionMaker = downDragDecisionMaker;
    }

   /*
   * @author lixiaojin
   * create on 2020-03-20 16:43
   * description: 触摸事件监听
   */
    @Override
    public boolean onTouchEvent(@NonNull CoordinatorLayout parent, @NonNull V child, @NonNull MotionEvent event) {
        //判断是否允许拖动
        if(!mAllowDrag){
            return false;
        }
        //如果触摸事件是按下事件
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            mMotionEventCanDrag = mDownDragDecisionMaker == null || mDownDragDecisionMaker.canDrag(parent, child, event);
        }
        //判断按下事件是否允许拖拽
        if(!mMotionEventCanDrag){
            return false;
        }
        LogUtils.d(TAG,"触摸事件向下传递...");
        return super.onTouchEvent(parent, child, event);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-20 16:39
    * description: 处理触摸事件
    */
    @Override
    public boolean onInterceptTouchEvent(@NonNull CoordinatorLayout parent, @NonNull V child, @NonNull MotionEvent event) {
        if(!mAllowDrag){
            return false;
        }
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            //如果未设置拖拽决策者的话，允许拖拽，否则有拖拽决策者决定
            mMotionEventCanDrag = mDownDragDecisionMaker == null || mDownDragDecisionMaker.canDrag(parent, child, event);
        }
        if(!mMotionEventCanDrag){
            return false;
        }
        return super.onInterceptTouchEvent(parent, child, event);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout,
                                       @NonNull V child,
                                       @NonNull View directTargetChild,
                                       @NonNull View target, int axes, int type) {
        if(!mAllowDrag){
            return false;
        }
        return super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type);
    }

    public void addBottomSheetCallback(BottomSheetCallback bottomSheetCallback) {

    }

    /*
    * @author lixiaojin
    * create on 2020-03-20 16:57
    * description: 拖拽决策者
    */
    public interface DownDragDecisionMaker {
        boolean canDrag(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull MotionEvent event);
    }
}
