package com.example.xiaojin20135.basemodule.view.widget.alpha;

import android.view.View;

import com.example.xiaojin20135.basemodule.R;
import com.example.xiaojin20135.basemodule.util.ui.ResHelper;

import java.lang.ref.WeakReference;

/*
 * @author lixiaojin
 * create on 2020-03-04 16:07
 * description： 改变View透明度帮助类 包括按下时以及禁用时
 */
public class AlphaViewHelper {
    //弱引用，方便GC是内存回收
    private WeakReference<View> mTarget;
    //设置是否要在press时改变View的透明度
    private boolean mChangeAlphaWhenPress = true;
    //设置是否要在disable时改变透明度
    private boolean mChangeAlphaDisable = true;
    private float mNormalAlpha = 1f;     //默认不透明
    private float mPressedAlpha = 0.5f;   //按下时默认透明度
    private float mDisabledAlpha = 0.5f; // 按下时默认透明度

    /*
    * @author lixiaojin
    * create on 2020-03-06 16:22
    * description: 使用默认的按下和禁用时的透明度
    */
    public AlphaViewHelper(View target){
        mTarget = new WeakReference<>(target);
        mPressedAlpha = ResHelper.getAttrColor(target.getContext(), R.attr.alpha_pressed);
        mDisabledAlpha = ResHelper.getAttrFloatValue(target.getContext(),R.attr.alpha_disabled);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-06 16:22
    * description: 指定透明度
    */
    public AlphaViewHelper(View target,float pressedAlpha, float disabledAlpha){
        mTarget = new WeakReference<>(target);
        mPressedAlpha = pressedAlpha;
        mDisabledAlpha = disabledAlpha;
    }

    /*
     * @author lixiaojin
     * create on 2020-03-04 16:48
     * description: 发生点压事件
     */
    public void onPressedChanged(View current, boolean pressed){
        View target = mTarget.get();
        if(target == null){
            return;
        }
        //如果当期view处于启用状态
        if(current.isEnabled()){
            target.setAlpha(mChangeAlphaDisable && pressed && current.isClickable() ? mPressedAlpha : mNormalAlpha);
        }else{
            //如果当前View处于未启用状态，尝试为view设置未启用状态的透明度
            if(mChangeAlphaDisable){
                target.setAlpha(mDisabledAlpha);
            }
        }
    }

    /*
    * @author lixiaojin
    * create on 2020-03-06 16:32
    * description: View发生启用禁用时调用
    */
    public void onEnableChanged(View current, boolean enabled){
        View target = mTarget.get();
        if(target == null){
            return;
        }
        float alphaForIsEnable;
        if(mChangeAlphaDisable){
            alphaForIsEnable = enabled ? mNormalAlpha : mDisabledAlpha;
        }else{
            alphaForIsEnable = mNormalAlpha;
        }
        if(current != target && target.isEnabled() != enabled){
            target.setEnabled(enabled);
        }
        target.setAlpha(alphaForIsEnable);
    }


    /*
    * @author lixiaojin
    * create on 2020-03-06 16:32
    * description：设置 允许点击时是否允许改变透明度
    */
    public void setChangeAlphaWhenPress(boolean changeAlphaWhenPress){
        mChangeAlphaWhenPress = changeAlphaWhenPress;
    }

    /*
    * @author lixiaojin
    * create on 2020-03-06 16:43
    * description: 设置view是否根据启用和禁用状态改变透明度
    */
    public void setChangeAlphaDisable(boolean changeAlphaDisable){
        mChangeAlphaDisable = changeAlphaDisable;
        //为了保证view初次渲染时透明度处于正常值
        View target = mTarget.get();
        if(target != null){
            onEnableChanged(target,target.isEnabled());
        }
    }



}

