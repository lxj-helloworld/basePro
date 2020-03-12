package com.example.xiaojin20135.basemodule.view.widget.alpha;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;

/*
* @author lixiaojin
* create on 2020-03-06 16:50
* description: 主要处理pressed和enabled改变时view的透明度设置
*/
public class AlphaConstraintLayout extends ConstraintLayout implements AlphaViewInf {
    private static final String TAG = "AlphaConstraintLayout";

    //View 透明度设置帮助类
    private AlphaViewHelper mAlphaViewHelper;

    public AlphaConstraintLayout(Context context) {
        super(context);
    }

    public AlphaConstraintLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AlphaConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-06 16:46
    * description:
    */
    private AlphaViewHelper getAlphaViewHelper() {
        if (mAlphaViewHelper == null) {
            mAlphaViewHelper = new AlphaViewHelper(this);
        }
        return mAlphaViewHelper;
    }


    /*
    * @author lixiaojin
    * create on 2020-03-06 16:47
    * description: 当按下事件发生时，去设置view的透明度
    */
    @Override
    public void setPressed(boolean pressed) {
        super.setPressed(pressed);
        getAlphaViewHelper().onPressedChanged(this, pressed);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-06 当View的启用和禁用状态发生变化时，去设置view的透明度
    * description:
    */
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        getAlphaViewHelper().onEnableChanged(this, enabled);
    }

    /**
     * 设置是否要在 press 时改变透明度
     * @param changeAlphaWhenPress 是否要在 press 时改变透明度
     */
    @Override
    public void setChangeAlphaWhenPress(boolean changeAlphaWhenPress) {
        getAlphaViewHelper().setChangeAlphaWhenPress(changeAlphaWhenPress);
    }

    /**
     * 设置是否要在 disabled 时改变透明度
     *
     * @param changeAlphaWhenDisable 是否要在 disabled 时改变透明度
     */
    @Override
    public void setChangeAlphaWhenDisable(boolean changeAlphaWhenDisable) {
        getAlphaViewHelper().setChangeAlphaDisable(changeAlphaWhenDisable);
    }

}
