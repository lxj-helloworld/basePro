package com.example.xiaojin20135.basemodule.layout;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import com.example.xiaojin20135.basemodule.view.widget.alpha.AlphaViewHelper;
import com.example.xiaojin20135.basemodule.view.widget.alpha.AlphaViewInf;

/*
* @author lixiaojin
* create on 2020-03-09 14:21
* description: 按钮透明度处理
* 处理按下以及启用禁用时
*/
public class AlphaButton extends AppCompatButton implements AlphaViewInf {
    //透明度视图帮助类
    private AlphaViewHelper mAlphaViewHelper;

    public AlphaButton(Context context) {
        super(context);
    }

    public AlphaButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AlphaButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 14:23
    * description: 生成帮助类
    */
    private AlphaViewHelper getAlphaViewHelper() {
        if (mAlphaViewHelper == null) {
            mAlphaViewHelper = new AlphaViewHelper(this);
        }
        return mAlphaViewHelper;
    }


    @Override
    public void setPressed(boolean pressed) {
        super.setPressed(pressed);
        getAlphaViewHelper().onPressedChanged(this, pressed);
    }

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
     * @param changeAlphaWhenDisable 是否要在 disabled 时改变透明度
     */
    @Override
    public void setChangeAlphaWhenDisable(boolean changeAlphaWhenDisable) {
        getAlphaViewHelper().setChangeAlphaDisable(changeAlphaWhenDisable);
    }

}
