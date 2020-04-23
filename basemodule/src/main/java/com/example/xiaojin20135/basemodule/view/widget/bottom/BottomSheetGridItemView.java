package com.example.xiaojin20135.basemodule.view.widget.bottom;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.xiaojin20135.basemodule.R;
import com.example.xiaojin20135.basemodule.layout.UIConstraintLayout;
import com.example.xiaojin20135.basemodule.skin.SkinHelper;
import com.example.xiaojin20135.basemodule.skin.SkinSimpleDefaultAttrProvider;
import com.example.xiaojin20135.basemodule.skin.SkinValueBuilder;
import com.example.xiaojin20135.basemodule.util.ui.ResHelper;
import com.example.xiaojin20135.basemodule.view.widget.textview.SpanTouchFixTextView;

/*
* @author lixiaojin
* create on 2020-04-09 10:15
* description: Grid Item 视图
*/
public class BottomSheetGridItemView extends UIConstraintLayout {
    private static final String TAG = "BottomSheetGridItemView";
    protected AppCompatImageView mIconIv;
    protected AppCompatImageView mSubscriptIv;
    protected TextView mTitleTv;
    protected Object mModelTag;

    public BottomSheetGridItemView(Context context) {
        this(context, null);
    }

    public BottomSheetGridItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomSheetGridItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setChangeAlphaWhenPress(true);
        //设置View内边距
        int paddingVer = ResHelper.getAttrDimen(context, R.attr.bottom_sheet_grid_item_padding_ver);
        setPadding(0, paddingVer, 0, paddingVer);
        //图标视图部分，  创建图片视图、设置缩放模式、大小以及边界
        mIconIv = onCreateIconView(context);
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR1){
            mIconIv.setId(View.generateViewId());
        }
        mIconIv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        int iconSize = ResHelper.getAttrDimen(context, R.attr.bottom_sheet_grid_item_icon_size); //图标大小
        LayoutParams iconLp = new LayoutParams(iconSize, iconSize);
        iconLp.leftToLeft = LayoutParams.PARENT_ID; //左侧贴父类
        iconLp.rightToRight = LayoutParams.PARENT_ID; //右侧贴父类
        iconLp.topToTop = LayoutParams.PARENT_ID; //底部贴父类
        addView(mIconIv, iconLp);
        //文本框部分 创建文本视图，设置文本颜色、字体样式和视图边界
        mTitleTv = onCreateTitleView(context);
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR1){
            mTitleTv.setId(View.generateViewId());
        }
        SkinSimpleDefaultAttrProvider provider = new SkinSimpleDefaultAttrProvider();
        //设置文本颜色
        provider.setDefaultSkinAttr(SkinValueBuilder.TEXT_COLOR, R.attr.skin_support_bottom_sheet_grid_item_text_color);
        //设置字体样式
        ResHelper.assignTextViewWithAttr(mTitleTv, R.attr.bottom_sheet_grid_item_text_style);
        SkinHelper.setSkinDefaultProvider(mTitleTv, provider);
        LayoutParams titleLp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        titleLp.leftToLeft = LayoutParams.PARENT_ID; //贴父视图
        titleLp.rightToRight = LayoutParams.PARENT_ID; //贴父视图
        titleLp.topToBottom = mIconIv.getId(); //文本置于图片之下
        titleLp.topMargin = ResHelper.getAttrDimen(context, R.attr.bottom_sheet_grid_item_text_margin_top);//文本与图片间距
        addView(mTitleTv, titleLp);
    }

    /*
    * @author lixiaojin
    * create on 2020-04-09 11:24
    * description: 创建ImageView
    */
    protected AppCompatImageView onCreateIconView(Context context) {
        return new AppCompatImageView(context);
    }

    /*
    * @author lixiaojin
    * create on 2020-04-09 11:24
    * description: 创建文本框
    */
    protected TextView onCreateTitleView(Context context) {
        return new SpanTouchFixTextView(context);
    }

    /*
    * @author lixiaojin
    * create on 2020-04-09 11:25
    * description: 
    */
    public void render(@NonNull BottomSheetGridItemModel model) {
        mModelTag = model.tag;
        setTag(model.tag);
        SkinValueBuilder builder = SkinValueBuilder.acquire();
        renderIcon(model, builder);
        builder.clear();
        renderTitle(model, builder);
        builder.clear();
        renderSubScript(model, builder);
        builder.release();
    }
    
    public Object getModelTag() {
        return mModelTag;
    }

    /*
    * @author lixiaojin
    * create on 2020-04-09 11:27
    * description: 渲染图标
    */
    protected void renderIcon(@NonNull BottomSheetGridItemModel model, @NonNull SkinValueBuilder builder) {
        if (model.imageSkinSrcAttr != 0) {
            builder.src(model.imageSkinSrcAttr);
            SkinHelper.setSkinValue(mIconIv, builder);
            Drawable drawable = SkinHelper.getSkinDrawable(mIconIv, model.imageSkinSrcAttr);
            mIconIv.setImageDrawable(drawable);
        } else {
            Drawable drawable = model.image;
            if (drawable == null && model.imageRes != 0) {
                drawable = ContextCompat.getDrawable(getContext(), model.imageRes);
            }
            if (drawable != null) {
                drawable.mutate();
            }
            mIconIv.setImageDrawable(drawable);
            if (model.imageSkinTintColorAttr != 0) {
                builder.tintColor(model.imageSkinTintColorAttr);
                SkinHelper.setSkinValue(mIconIv, builder);
            } else {
                SkinHelper.setSkinValue(mIconIv, "");
            }
        }
    }

    /*
    * @author lixiaojin
    * create on 2020-04-09 11:29
    * description: 渲染文本
    */
    protected void renderTitle(@NonNull BottomSheetGridItemModel model, @NonNull SkinValueBuilder builder) {
        mTitleTv.setText(model.text);
        if (model.textSkinColorAttr != 0) {
            builder.textColor(model.textSkinColorAttr);
        }
        SkinHelper.setSkinValue(mTitleTv, builder);
        if (model.typeface != null) {
            mTitleTv.setTypeface(model.typeface);
        }
    }

    protected void renderSubScript(@NonNull BottomSheetGridItemModel model, @NonNull SkinValueBuilder builder) {
        if (model.subscriptRes != 0 || model.subscript != null || model.subscriptSkinSrcAttr != 0) {
            if (mSubscriptIv == null) {
                mSubscriptIv = new AppCompatImageView(getContext());
                mSubscriptIv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                LayoutParams lp = new LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.rightToRight = mIconIv.getId();
                lp.topToTop = mIconIv.getId();
                addView(mSubscriptIv, lp);
            }
            mSubscriptIv.setVisibility(View.VISIBLE);
            if (model.subscriptSkinSrcAttr != 0) {
                builder.src(model.subscriptSkinSrcAttr);
                SkinHelper.setSkinValue(mSubscriptIv, builder);
                Drawable drawable = SkinHelper.getSkinDrawable(mSubscriptIv, model.subscriptSkinSrcAttr);
                mIconIv.setImageDrawable(drawable);
            } else {
                Drawable drawable = model.subscript;
                if (drawable == null && model.subscriptRes != 0) {
                    drawable = ContextCompat.getDrawable(getContext(), model.subscriptRes);
                }
                if (drawable != null) {
                    drawable.mutate();
                }
                mSubscriptIv.setImageDrawable(drawable);
                if (model.subscriptSkinTintColorAttr != 0) {
                    builder.tintColor(model.subscriptSkinTintColorAttr);
                    SkinHelper.setSkinValue(mSubscriptIv, builder);
                } else {
                    SkinHelper.setSkinValue(mSubscriptIv, "");
                }
            }
        } else if (mSubscriptIv != null) {
            mSubscriptIv.setVisibility(View.GONE);
        }
    }
}
