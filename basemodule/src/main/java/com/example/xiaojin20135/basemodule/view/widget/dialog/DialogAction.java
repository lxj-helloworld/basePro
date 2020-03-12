package com.example.xiaojin20135.basemodule.view.widget.dialog;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.View;
import com.example.xiaojin20135.basemodule.R;
import com.example.xiaojin20135.basemodule.layout.UIButton;
import com.example.xiaojin20135.basemodule.skin.SkinHelper;
import com.example.xiaojin20135.basemodule.skin.SkinValueBuilder;
import com.example.xiaojin20135.basemodule.util.LogUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/*
* @author lixiaojin
* create on 2020-03-09 16:43
* description: 窗口点击动作封装
*/
public class DialogAction {
    private static final String TAG = "DialogAction";

    @IntDef({ACTION_PROP_NEGATIVE, ACTION_PROP_NEUTRAL, ACTION_PROP_POSITIVE})
    @Retention(RetentionPolicy.SOURCE) //源码注解
    public @interface Prop {
    }

    //用于标记positive/negative/neutral
    public static final int ACTION_PROP_POSITIVE = 0;
    public static final int ACTION_PROP_NEUTRAL = 1;
    public static final int ACTION_PROP_NEGATIVE = 2;

    private CharSequence mStr;
    private int mIconRes = 0;
    private int mActionProp = ACTION_PROP_NEUTRAL;
    private int mSkinTextColorAttr = 0;
    private int mSkinBackgroundAttr = 0;
    private int mSkinIconTintColorAttr = 0;
    private int mSkinSeparatorColorAttr = R.attr.skin_support_dialog_action_divider_color;
    private ActionListener mOnClickListener;
    private UIButton mButton;
    private boolean mIsEnabled = true;

    public DialogAction(Context context, int strRes) {
        this(context.getResources().getString(strRes));
    }

    public DialogAction(CharSequence str) {
        this(str, null);
    }

    public DialogAction(Context context, int strRes, @Nullable ActionListener onClickListener) {
        this(context.getResources().getString(strRes), onClickListener);
    }

    public DialogAction(CharSequence str, @Nullable ActionListener onClickListener) {
        mStr = str;
        mOnClickListener = onClickListener;
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 16:47
    * description: 设置按钮类型
    */
    public DialogAction prop(@Prop int actionProp) {
        mActionProp = actionProp;
        return this;
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 16:48
    * description: 设置按钮图标
    */
    public DialogAction iconRes(@Prop int iconRes) {
        mIconRes = iconRes;
        return this;
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 16:48
    * description: 设置按钮点击事件
    */
    public DialogAction onClick(ActionListener onClickListener) {
        mOnClickListener = onClickListener;
        return this;
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 16:48
    * description: 设置按钮文本颜色
    */
    public DialogAction skinTextColorAttr(int skinTextColorAttr) {
        mSkinTextColorAttr = skinTextColorAttr;
        return this;
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 16:48
    * description: 设置按钮背景色
    */
    public DialogAction skinBackgroundAttr(int skinBackgroundAttr) {
        mSkinBackgroundAttr = skinBackgroundAttr;
        return this;
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 16:49
    * description: 设置按钮图标
    */
    public DialogAction skinIconTintColorAttr(int skinIconTintColorAttr) {
        mSkinIconTintColorAttr = skinIconTintColorAttr;
        return this;
    }

    /**
     * inner usage
     * @param skinSeparatorColorAttr
     * @return
     */
    DialogAction skinSeparatorColorAttr(int skinSeparatorColorAttr){
        mSkinSeparatorColorAttr = skinSeparatorColorAttr;
        return this;
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 16:50
    * description: 按钮启用禁用
    */
    public DialogAction setEnabled(boolean enabled) {
        mIsEnabled = enabled;
        if (mButton != null) {
            mButton.setEnabled(enabled);
        }
        return this;
    }


    /*
    * @author lixiaojin
    * create on 2020-03-09 16:50
    * description: 生成一个button
    */
    public UIButton buildActionView(final Dialog dialog, final int index) {
        mButton = generateActionButton(dialog.getContext(), mStr, mIconRes, mSkinBackgroundAttr, mSkinTextColorAttr, mSkinIconTintColorAttr);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickListener != null && mButton.isEnabled()) {
                    mOnClickListener.onClick(dialog, index);
                }
            }
        });
        return mButton;
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 13:45
    * description: 生成对话框按钮
    * 参数为当前上下文、文本、图标、背景色、文本颜色、图标色调
    */
    private UIButton generateActionButton(Context context, CharSequence text, int iconRes, int skinBackgroundAttr, int skinTextColorAttr, int iconTintColor) {
        UIButton button = new UIButton(context);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            LogUtils.d(TAG,"button背景设置为空");
            button.setBackground(null);
        }
//        button.setBorderWidth(1);
//        button.setBorderColor(Color.RED);
        button.setMinHeight(0);
        button.setMinimumHeight(0);
        button.setChangeAlphaWhenDisable(false); //启用透明度
        button.setChangeAlphaWhenPress(false); //启用透明度
        TypedArray a = context.obtainStyledAttributes(null, R.styleable.DialogActionStyleDef, R.attr.dialog_action_style, 0);
        int count = a.getIndexCount();
        int paddingHor = 0, iconSpace = 0;
        //颜色状态列表
        ColorStateList negativeTextColor = null, positiveTextColor = null;
        for (int i = 0; i < count; i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.DialogActionStyleDef_android_gravity) {
                //权重
                LogUtils.d(TAG,"权重");
                button.setGravity(a.getInt(attr, -1));
            } else if (attr == R.styleable.DialogActionStyleDef_android_textColor) {
                //字体颜色
                LogUtils.d(TAG,"字体颜色");
                button.setTextColor(a.getColorStateList(attr));
            } else if (attr == R.styleable.DialogActionStyleDef_android_textSize) {
                //字体大小
                LogUtils.d(TAG,"字体大小");
                button.setTextSize(TypedValue.COMPLEX_UNIT_PX, a.getDimensionPixelSize(attr, 0));
            } else if (attr == R.styleable.DialogActionStyleDef_dialog_action_button_padding_horizontal) {
                //水平内边距
                LogUtils.d(TAG,"水平内间距");
                paddingHor = a.getDimensionPixelSize(attr, 0);
            } else if (attr == R.styleable.DialogActionStyleDef_android_background) {
                //背景色
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
                    LogUtils.d(TAG,"背景色");
                    button.setBackground(a.getDrawable(attr));
                }
            } else if (attr == R.styleable.DialogActionStyleDef_android_minWidth) {
                //设置视图最小宽度 必须同时设置才会生效
                LogUtils.d(TAG,"视图最小宽度");
                int miniWidth = a.getDimensionPixelSize(attr, 0);
                button.setMinWidth(miniWidth);
                button.setMinimumWidth(miniWidth);
            } else if (attr == R.styleable.DialogActionStyleDef_dialog_positive_action_text_color) {
                //positive类按钮字体颜色
                LogUtils.d(TAG,"positive按钮字体颜色");
                positiveTextColor = a.getColorStateList(attr);
            } else if (attr == R.styleable.DialogActionStyleDef_dialog_negative_action_text_color) {
                //negative类按钮字体颜色
                LogUtils.d(TAG,"negative字体颜色");
                negativeTextColor = a.getColorStateList(attr);
            } else if (attr == R.styleable.DialogActionStyleDef_dialog_action_icon_space) {
                //图标空间
                LogUtils.d(TAG,"图标空间");
                iconSpace = a.getDimensionPixelSize(attr, 0);
            } else if (attr == R.styleable.TextCommonStyleDef_android_textStyle) {
                //字体样式
                LogUtils.d(TAG,"字体样式");
                int styleIndex = a.getInt(attr, -1);
                button.setTypeface(null, styleIndex);
            }
        }
        a.recycle();
        //设置水平内边距
        LogUtils.d(TAG,"paddingHor = " + paddingHor);
        button.setPadding(paddingHor, 0, paddingHor, 0);
        if (iconRes <= 0) {
            button.setText(text);
        } else {
            button.setText(text);
//            button.setText(SpanHelper.generateSideIconText(true, iconSpace, text, ContextCompat.getDrawable(context, iconRes), iconTintColor, button));
        }
        button.setClickable(true); //可点击
        button.setEnabled(mIsEnabled); //启用、禁用  ，默认启用
        if (mActionProp == ACTION_PROP_NEGATIVE) {   //negative 类按钮， 设置按钮颜色
            button.setTextColor(negativeTextColor);
            if (skinTextColorAttr == 0) {
                skinTextColorAttr = R.attr.skin_support_dialog_negative_action_text_color;
            }
        } else if (mActionProp == ACTION_PROP_POSITIVE && positiveTextColor != null) { //positive 类按钮，设置按钮颜色
            button.setTextColor(positiveTextColor);
            if (skinTextColorAttr == 0) {
                skinTextColorAttr = R.attr.skin_support_dialog_positive_action_text_color;
            }
        } else {
            if (skinTextColorAttr == 0) {
                skinTextColorAttr = R.attr.skin_support_dialog_action_text_color;
            }
        }
        SkinValueBuilder skinValueBuilder = SkinValueBuilder.acquire();
        LogUtils.d(TAG,"skinBackgroundAttr = " + skinBackgroundAttr);
        skinBackgroundAttr = (skinBackgroundAttr == 0) ? R.attr.skin_support_dialog_action_bg : skinBackgroundAttr;
        skinValueBuilder.background(skinBackgroundAttr);
        skinValueBuilder.textColor(skinTextColorAttr);
        if(mSkinSeparatorColorAttr != 0){
            skinValueBuilder.topSeparator(mSkinSeparatorColorAttr);
            skinValueBuilder.leftSeparator(mSkinSeparatorColorAttr);
        }
        LogUtils.d(TAG,"DialogAction 的 SkinValueBuilder = " + skinValueBuilder.toString());
        SkinHelper.setSkinValue(button, skinValueBuilder);
        skinValueBuilder.release();

        LogUtils.d(TAG,"button是否有边框" + button.hasBorder());
        LogUtils.d(TAG,"button是否有顶部分割线" + button.hasTopSeparator());
        LogUtils.d(TAG,"button是否有左侧分割线" + button.hasLeftSeparator());
        LogUtils.d(TAG,"button是否有右侧分割线" + button.hasRightSeparator());
        LogUtils.d(TAG,"button是否有底部分割线" + button.hasBottomSeparator());
//        button.setBackgroundColor(Color.WHITE);
        return button;
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 17:05
    * description: 返回当前按钮类型
    */
    public int getActionProp() {
        return mActionProp;
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 17:05
    * description:  当前按钮点击动作事件
    */
    public interface ActionListener {
        void onClick(Dialog dialog, int index);
    }
}

