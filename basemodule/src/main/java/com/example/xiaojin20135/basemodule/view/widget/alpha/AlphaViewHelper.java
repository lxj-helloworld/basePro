package com.example.xiaojin20135.basemodule.view.widget.alpha;

import android.view.View;

import com.example.xiaojin20135.basemodule.R;
import com.example.xiaojin20135.basemodule.util.ui.ResHelper;

import java.lang.ref.WeakReference;

/*
 * @author lixiaojin
 * create on 2020-03-04 16:07
 * description： 改变View透明度帮助类
 */
public class AlphaViewHelper {
    private WeakReference<View> mTarget;

    //设置是否要在press时改变View的透明度
    private boolean mChangeAlphaWhenPress = true;

    //设置是否要在disable时改变透明度
    private boolean mChangeAlphaDisable = true;

    private float mNormalAlpha = 1f;
    private float mPressedAlpha = 0.5f;
    private float mDisabledAlpha = 0.5f;

    public AlphaViewHelper(View target){
        mTarget = new WeakReference<>(target);
        mPressedAlpha = ResHelper.getAttrColor(target.getContext(), R.attr.alpha_pressed);
        mDisabledAlpha = ResHelper.getAttrFloatValue(target.getContext(),R.attr.alpha_disabled);
    }

    public AlphaViewHelper(View target,float pressedAlpha, float disabledAlpha){
        mTarget = new WeakReference<>(target);
        mPressedAlpha = pressedAlpha;
        mDisabledAlpha = disabledAlpha;
    }

    /*
     * @author lixiaojin
     * create on 2020-03-04 16:48
     * description:
     */
    public void onPressedChanged(View current, boolean pressed){
        View target = mTarget.get();
        if(target == null){
            return;
        }
        if(current.isEnabled()){
            target.setAlpha(mChangeAlphaDisable && pressed && current.isClickable() ? mPressedAlpha : mNormalAlpha);
        }else{
            if(mChangeAlphaDisable){
                target.setAlpha(mDisabledAlpha);
            }
        }
    }


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


    public void setChangeAlphaWhenPress(boolean changeAlphaWhenPress){
        mChangeAlphaWhenPress = changeAlphaWhenPress;
    }

    public void setChangeAlphaDisable(boolean changeAlphaDisable){
        mChangeAlphaDisable = changeAlphaDisable;
        View target = mTarget.get();
        if(target != null){
            onEnableChanged(target,target.isEnabled());
        }
    }

}

