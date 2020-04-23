package com.example.xiaojin20135.basemodule.view.widget.bottom;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.xiaojin20135.basemodule.R;
import com.example.xiaojin20135.basemodule.layout.UIConstraintLayout;
import com.example.xiaojin20135.basemodule.skin.SkinHelper;
import com.example.xiaojin20135.basemodule.skin.SkinValueBuilder;
import com.example.xiaojin20135.basemodule.util.ui.ResHelper;
import com.example.xiaojin20135.basemodule.view.widget.textview.SpanTouchFixTextView;

/*
* @author lixiaojin
* create on 2020-04-08 11:48
* description: 列表Item视图
*/
public class BottomSheetListItemView extends UIConstraintLayout {
    private static final String TAG = "BottomSheetListItemView";

    private AppCompatImageView mIconView;    //左侧图标
    private SpanTouchFixTextView mTextView;  //文本
    private FrameLayout mRedPointView; //红点
    private AppCompatImageView mMarkView = null; ///右侧标记视图
    private int mItemHeight; //item高度

    public BottomSheetListItemView(Context context, boolean markStyle, boolean gravityCenter) {
        super(context);
        //设置背景
        setBackground(ResHelper.getAttrDrawable(context, R.attr.skin_support_bottom_sheet_list_item_bg));
        int paddingHor = ResHelper.getAttrDimen(context, R.attr.bottom_sheet_padding_hor);
        Log.d(TAG,"paddingHor = " + paddingHor);
        setPadding(paddingHor, 0, paddingHor, 0);
        SkinValueBuilder builder = SkinValueBuilder.acquire();
        builder.background(R.attr.skin_support_bottom_sheet_list_item_bg);
        SkinHelper.setSkinValue(this, builder);
        builder.clear();
        //左侧icon
        mIconView = new AppCompatImageView(context);
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR1){
            mIconView.setId(View.generateViewId());
        }
        mIconView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        //中间文本
        mTextView = new SpanTouchFixTextView(context);
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR1){
            mTextView.setId(View.generateViewId());
        }
        //红点
        mRedPointView = new FrameLayout(context);
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR1){
            mRedPointView.setId(View.generateViewId());
        }
        //
        mRedPointView.setBackgroundColor(ResHelper.getAttrColor(context, R.attr.skin_support_bottom_sheet_list_red_point_color));
        builder.background(R.attr.skin_support_bottom_sheet_list_red_point_color);
        SkinHelper.setSkinValue(mRedPointView, builder);
        builder.clear();
        //标记
        if (markStyle) {
            mMarkView = new AppCompatImageView(context);
            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR1){
                mMarkView.setId(View.generateViewId());
            }
            mMarkView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            mMarkView.setImageDrawable(ResHelper.getAttrDrawable(context, R.attr.skin_support_bottom_sheet_list_mark));
            builder.src(R.attr.skin_support_bottom_sheet_list_mark);
            SkinHelper.setSkinValue(mMarkView, builder);
        }
        builder.release();
        //图标大小
        int iconSize = ResHelper.getAttrDimen(context, R.attr.bottom_sheet_list_item_icon_size);
        Log.d(TAG,"iconSize = " + iconSize);
        //添加左侧icon
        LayoutParams lp = new UIConstraintLayout.LayoutParams(iconSize, iconSize);
        lp.leftToLeft = LayoutParams.PARENT_ID; //左侧对齐父视图
        lp.topToTop = LayoutParams.PARENT_ID; //顶部对齐父视图
        lp.rightToLeft = mTextView.getId(); //图标有边界对齐文本框左侧
        lp.bottomToBottom = LayoutParams.PARENT_ID;  //底部对齐父视图
        lp.horizontalChainStyle = LayoutParams.CHAIN_PACKED; // 把控件整体居中
        lp.horizontalBias = gravityCenter ? 0.5f : 0f;
        addView(mIconView, lp);

        //添加图标右侧文本框
        lp = new UIConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftToRight = mIconView.getId();
        lp.rightToLeft = mRedPointView.getId();
        lp.topToTop = LayoutParams.PARENT_ID;
        lp.bottomToBottom = LayoutParams.PARENT_ID;
        lp.horizontalChainStyle = LayoutParams.CHAIN_PACKED;
        lp.horizontalBias = gravityCenter ? 0.5f : 0f;
        lp.leftMargin = ResHelper.getAttrDimen(context, R.attr.bottom_sheet_list_item_icon_margin_right);
        lp.goneLeftMargin = 0;
        addView(mTextView, lp);

        //小红点
        int redPointSize = ResHelper.getAttrDimen(context, R.attr.bottom_sheet_list_item_red_point_size);
        lp = new UIConstraintLayout.LayoutParams(redPointSize, redPointSize);
        lp.leftToRight = mTextView.getId();
        if (markStyle) {
            lp.rightToLeft = mMarkView.getId();
            lp.rightMargin = ResHelper.getAttrDimen(context, R.attr.bottom_sheet_list_item_mark_margin_left);
        } else {
            lp.rightToRight = LayoutParams.PARENT_ID;
        }
        lp.topToTop = LayoutParams.PARENT_ID;
        lp.bottomToBottom = LayoutParams.PARENT_ID;
        lp.horizontalChainStyle = LayoutParams.CHAIN_PACKED;
        lp.horizontalBias = gravityCenter ? 0.5f : 0f;
        lp.leftMargin = ResHelper.getAttrDimen(context, R.attr.bottom_sheet_list_item_tip_point_margin_left);
        addView(mRedPointView, lp);

        //添加标记
        if (markStyle) {
            lp = new UIConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.rightToRight = LayoutParams.PARENT_ID;
            lp.topToTop = LayoutParams.PARENT_ID;
            lp.bottomToBottom = LayoutParams.PARENT_ID;
            addView(mMarkView, lp);
        }

        mItemHeight = ResHelper.getAttrDimen(context, R.attr.bottom_sheet_list_item_height);
        Log.d(TAG,"mItemHeight = " + mItemHeight);
    }

    /*
    * @author lixiaojin
    * create on 2020-04-10 09:08
    * description: 渲染View内容以及样式，设置左侧图标、文本以及右侧选中标记
    */
    public void render(@NonNull BottomSheetListItemModel itemModel, boolean isChecked) {
        SkinValueBuilder builder = SkinValueBuilder.acquire();
        if (itemModel.imageSkinSrcAttr != 0) {
            builder.src(itemModel.imageSkinSrcAttr);
            SkinHelper.setSkinValue(mIconView, builder);
            mIconView.setImageDrawable(SkinHelper.getSkinDrawable(this, itemModel.imageSkinSrcAttr));
            mIconView.setVisibility(View.VISIBLE);
        } else {
            Drawable drawable = itemModel.image;
            if (drawable == null && itemModel.imageRes != 0) {
                drawable = ContextCompat.getDrawable(getContext(), itemModel.imageRes);
            }
            if (drawable != null) {
                drawable.mutate();
                mIconView.setImageDrawable(drawable);
                if (itemModel.imageSkinTintColorAttr != 0) {
                    builder.tintColor(itemModel.imageSkinTintColorAttr);
                    SkinHelper.setSkinValue(mIconView, builder);
                } else {
                    SkinHelper.setSkinValue(mIconView, "");
                }
            } else {
                mIconView.setVisibility(View.GONE);
            }
        }
        builder.clear();

        mTextView.setText(itemModel.text);
        if (itemModel.typeface != null) {
            mTextView.setTypeface(itemModel.typeface);
        }
        if (itemModel.textSkinColorAttr != 0) {
            builder.textColor(itemModel.textSkinColorAttr);
            SkinHelper.setSkinValue(mTextView, builder);
            mTextView.setTextColor(getResources().getColor(R.color.black));
        } else {
            SkinHelper.setSkinValue(mTextView, "");
        }

        mRedPointView.setVisibility(itemModel.hasRedPoint ? View.VISIBLE : View.GONE);

        if (mMarkView != null) {
            mMarkView.setVisibility(isChecked ? View.VISIBLE : View.INVISIBLE);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(mItemHeight, View.MeasureSpec.EXACTLY));
    }
}
