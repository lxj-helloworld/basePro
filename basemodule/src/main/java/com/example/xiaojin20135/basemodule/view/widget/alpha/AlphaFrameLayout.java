package com.example.xiaojin20135.basemodule.view.widget.alpha;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/*
* @author lixiaojin
* create on 2020-03-04 16:05
* description: 改变View 的透明度
*/
public class AlphaFrameLayout extends FrameLayout implements AlphaViewInf{
    private AlphaViewHelper mAlphaViewHelper;

    public AlphaFrameLayout(@NonNull Context context) {
        super(context);
    }

    public AlphaFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AlphaFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private AlphaViewHelper getAlphaViewHelper(){
        if(mAlphaViewHelper == null){
            mAlphaViewHelper = new AlphaViewHelper(this);
        }
        return mAlphaViewHelper;
    }

    @Override
    public void setPressed(boolean pressed) {
        super.setPressed(pressed);
        getAlphaViewHelper().onPressedChanged(this,pressed);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        getAlphaViewHelper().onEnableChanged(this,enabled);
    }


    @Override
    public void setChangeAlphaWhenPress(boolean changeAlphaWhenPress) {
        getAlphaViewHelper().setChangeAlphaWhenPress(changeAlphaWhenPress);
    }

    @Override
    public void setChangeAlphaWhenDisable(boolean changeAlphaWhenDisable) {
        getAlphaViewHelper().setChangeAlphaDisable(changeAlphaWhenDisable);
    }
}
