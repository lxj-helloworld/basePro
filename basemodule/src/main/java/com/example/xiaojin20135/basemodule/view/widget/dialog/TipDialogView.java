package com.example.xiaojin20135.basemodule.view.widget.dialog;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;

import com.example.xiaojin20135.basemodule.R;
import com.example.xiaojin20135.basemodule.layout.UILinearLayout;
import com.example.xiaojin20135.basemodule.skin.SkinHelper;
import com.example.xiaojin20135.basemodule.skin.SkinValueBuilder;
import com.example.xiaojin20135.basemodule.util.ui.ResHelper;

/*
* @author lixiaojin
* create on 2020-03-14 14:30
* description: 简单弹框
*/
public class TipDialogView extends UILinearLayout {
    private static final String TAG = "TipDialogView";

    private final int mMaxWidth; //View最大宽度
    private final int mMiniWidth; //View最小宽度
    private final int mMiniHeight; //View最小高度

    public TipDialogView(Context context) {
        super(context);
        setOrientation(VERTICAL); //子View垂直布局
        setGravity(Gravity.CENTER_HORIZONTAL); //子View布局
        int radius = ResHelper.getAttrDimen(context, R.attr.tip_dialog_radius); //圆角半径
        Drawable background = ResHelper.getAttrDrawable(context, R.attr.skin_support_tip_dialog_bg); //View背景
        int paddingHor = ResHelper.getAttrDimen(context, R.attr.tip_dialog_padding_horizontal); //水平内边距
        int paddingVer = ResHelper.getAttrDimen(context, R.attr.tip_dialog_padding_vertical); //垂直内边距
        setBackground(background); //设置view背景
        setPadding(paddingHor, paddingVer, paddingHor, paddingVer); //设置内边距
        setRadius(radius); //设置圆角半径
        SkinValueBuilder builder = SkinValueBuilder.acquire();
        builder.background(R.attr.skin_support_tip_dialog_bg);
        SkinHelper.setSkinValue(this, builder); //设置自定义主题属性
        builder.release();
        mMaxWidth = ResHelper.getAttrDimen(context, R.attr.tip_dialog_max_width); //获取最大宽度
        mMiniWidth = ResHelper.getAttrDimen(context, R.attr.tip_dialog_min_width); //获取最小宽度
        mMiniHeight = ResHelper.getAttrDimen(context, R.attr.tip_dialog_min_height); //获取最小高度
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
        //如果父View给定的宽度超过最大宽度，取最大宽度
        if(widthSize > mMaxWidth){
            widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(mMaxWidth, widthMode);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //如果测量宽度小于最小宽度或者测量高度小于最小高度，调整测量参数后，重新测量
        boolean needRemeasure = false;
        //最小宽度校验
        if(getMeasuredWidth() < mMiniWidth){
            widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(mMiniWidth, View.MeasureSpec.EXACTLY);
            needRemeasure = true;
        }
        //最小高度校验
        if(getMeasuredHeight() < mMiniHeight){
            heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(mMiniHeight, View.MeasureSpec.EXACTLY);
            needRemeasure = true;
        }
        if(needRemeasure){
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
