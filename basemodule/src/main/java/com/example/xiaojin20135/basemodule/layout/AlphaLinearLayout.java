package com.example.xiaojin20135.basemodule.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.example.xiaojin20135.basemodule.view.widget.alpha.AlphaViewHelper;
import com.example.xiaojin20135.basemodule.view.widget.alpha.AlphaViewInf;

/*
* @author lixiaojin
* create on 2020-03-14 10:21
* description: 透明度线性布局
* 设置透明度改变条件以及在条件满足时进行透明度变化的调用
*/
public class AlphaLinearLayout extends LinearLayout implements AlphaViewInf {
    private static final String TAG = "AlphaLinearLayout";
    //视图透明度帮助类
    private AlphaViewHelper mAlphaViewHelper;

    public AlphaLinearLayout(Context context) {
        super(context);
    }

    public AlphaLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AlphaLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-14 10:22
    * description: 获取透明度视图帮助类，有择返回，无则创建
    */
    private AlphaViewHelper getAlphaViewHelper() {
        if (mAlphaViewHelper == null) {
            mAlphaViewHelper = new AlphaViewHelper(this);
        }
        return mAlphaViewHelper;
    }

    /*
    * @author lixiaojin
    * create on 2020-03-14 10:28
     * description: 点压状态变化，处理对应View的透明度
    */
    @Override
    public void setPressed(boolean pressed) {
        super.setPressed(pressed);
        getAlphaViewHelper().onPressedChanged(this, pressed);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-14 10:28
    * description: 启用禁用状态变化，处理View的透明度
    */
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        getAlphaViewHelper().onEnableChanged(this, enabled);
    }

   /*
   * @author lixiaojin
   * create on 2020-03-14 10:46
   * description: 是否要在发生点压事件时改变透明度
   */
    @Override
    public void setChangeAlphaWhenPress(boolean changeAlphaWhenPress) {
        getAlphaViewHelper().setChangeAlphaWhenPress(changeAlphaWhenPress);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-14 10:47
    * description: 设置是否要在启用禁用时改变透明度
    */
    @Override
    public void setChangeAlphaWhenDisable(boolean changeAlphaWhenDisable) {
        getAlphaViewHelper().setChangeAlphaDisable(changeAlphaWhenDisable);
    }

}

