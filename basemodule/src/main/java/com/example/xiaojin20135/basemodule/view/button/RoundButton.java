package com.example.xiaojin20135.basemodule.view.button;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.util.SimpleArrayMap;
import android.util.AttributeSet;
import com.example.xiaojin20135.basemodule.R;
import com.example.xiaojin20135.basemodule.layout.AlphaButton;
import com.example.xiaojin20135.basemodule.skin.SkinValueBuilder;
import com.example.xiaojin20135.basemodule.util.ui.ViewHelper;

/*
* @author lixiaojin
* create on 2020-03-11 14:36
* description: 圆角按钮
*/
public class RoundButton extends AlphaButton {

    private RoundButtonDrawable mRoundBg;
    private static SimpleArrayMap<String, Integer> sDefaultSkinAttrs;

    static {
        sDefaultSkinAttrs = new SimpleArrayMap<>(3);
        sDefaultSkinAttrs.put(SkinValueBuilder.BACKGROUND, R.attr.skin_support_round_btn_bg_color);
        sDefaultSkinAttrs.put(SkinValueBuilder.BORDER, R.attr.skin_support_round_btn_border_color);
        sDefaultSkinAttrs.put(SkinValueBuilder.TEXT_COLOR, R.attr.skin_support_round_btn_text_color);
    }

    public RoundButton(Context context) {
        super(context);
        init(context, null, 0);
    }

    public RoundButton(Context context, AttributeSet attrs) {
        super(context, attrs, R.attr.ButtonStyle);
        init(context, attrs, R.attr.ButtonStyle);
    }

    public RoundButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        mRoundBg = RoundButtonDrawable.fromAttributeSet(context, attrs, defStyleAttr);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            ViewHelper.setBackgroundKeepingPadding(this, mRoundBg);
        }
        setChangeAlphaWhenDisable(false);
        setChangeAlphaWhenPress(false);
    }

    @Override
    public void setBackgroundColor(int color) {
        mRoundBg.setBgData(ColorStateList.valueOf(color));
    }

    public void setBgData(@Nullable ColorStateList colors) {
        mRoundBg.setBgData(colors);
    }

    public void setStrokeData(int width, @Nullable ColorStateList colors) {
        mRoundBg.setStrokeData(width, colors);
    }

    public int getStrokeWidth(){
        return mRoundBg.getStrokeWidth();
    }

    public void setStrokeColors(ColorStateList colors) {
        mRoundBg.setStrokeColors(colors);
    }

}
