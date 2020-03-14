package com.example.xiaojin20135.basemodule.view.widget.listitem;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.IntDef;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.xiaojin20135.basemodule.R;
import com.example.xiaojin20135.basemodule.layout.UIConstraintLayout;
import com.example.xiaojin20135.basemodule.skin.SkinHelper;
import com.example.xiaojin20135.basemodule.skin.SkinValueBuilder;
import com.example.xiaojin20135.basemodule.util.LangHelper;
import com.example.xiaojin20135.basemodule.util.ui.ResHelper;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/*
* @author lixiaojin
* create on 2020-03-13 14:18
* description: 通用列表cell视图
*/
public class CommonListItemView extends UIConstraintLayout {
    //纯文本，右侧不显示任何内容
    public final static int ACCESSORY_TYPE_NONE = 0;
    //文本右侧显示右箭头
    public final static int ACCESSORY_TYPE_CHEVRON = 1;
    //文本右侧显示开关
    public final static int ACCESSORY_TYPE_SWITCH = 2;
    //文本右侧显示一个自定义的View
    public final static int ACCESSORY_TYPE_CUSTOM = 3;
    //主文本和解释信息垂直排列
    public final static int VERTICAL = 0;
    //主文本和解释信息水平排列
    public final static int HORIZONTAL = 1;

    //右侧附件展示的view类型
    @IntDef({ACCESSORY_TYPE_NONE, ACCESSORY_TYPE_CHEVRON, ACCESSORY_TYPE_SWITCH, ACCESSORY_TYPE_CUSTOM})
    @Retention(RetentionPolicy.SOURCE)
    public @interface CommonListItemAccessoryType {
    }

    //附加展示的view类型
    @CommonListItemAccessoryType
    private int mAccessoryType;

    //主文本和解释信息的布局方向
    @IntDef({VERTICAL, HORIZONTAL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface CommonListItemOrientation {
    }

    //布局方向
    private int mOrientation = HORIZONTAL;

    protected ImageView mImageView; //文本左侧图标
    private ViewGroup mAccessoryView;   //文本右侧view
    protected TextView mTextView; //主文本
    protected TextView mDetailTextView; //辅助信息
    protected CheckBox mSwitch; //右侧开关
    private boolean mDisableSwitchSelf = false; //是否启用开关

    public CommonListItemView(Context context) {
        this(context, null);
    }

    public CommonListItemView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.CommonListItemViewStyle);
    }

    public CommonListItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-12 17:03
    * description: 视图初始化
    */
    protected void init(Context context, AttributeSet attrs, int defStyleAttr) {
        //加载布局文件
        LayoutInflater.from(context).inflate(R.layout.common_list_item, this, true);
        //处理样式值
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CommonListItemView, defStyleAttr, 0);
        //布局方向
        @CommonListItemOrientation int orientation = array.getInt(R.styleable.CommonListItemView_orientation, HORIZONTAL);
        //配件样式
        @CommonListItemAccessoryType int accessoryType = array.getInt(R.styleable.CommonListItemView_accessory_type, ACCESSORY_TYPE_NONE);
        //主字体颜色
        final int initTitleColor = array.getColor(R.styleable.CommonListItemView_common_list_title_color, 0);
        //辅字体颜色
        final int initDetailColor = array.getColor(R.styleable.CommonListItemView_common_list_detail_color, 0);
        //回收
        array.recycle();

        //基础view初始化
        mImageView = findViewById(R.id.group_list_item_imageView); //左图标
        mTextView = findViewById(R.id.group_list_item_textView); //主文本
        mDetailTextView = findViewById(R.id.group_list_item_detailTextView); //辅助文本
        mTextView.setTextColor(initTitleColor); //主字体颜色
        mDetailTextView.setTextColor(initDetailColor); //辅助字体颜色
        mAccessoryView = findViewById(R.id.group_list_item_accessoryView); //右侧附带View
        setOrientation(orientation); //设置布局方向
        setAccessoryType(accessoryType); //设置附带View
    }

    /*
    * @author lixiaojin
    * create on 2020-03-12 17:15
    * description: 更新图标布局参数
    */
    public void updateImageViewLp(LayoutParamConfig lpConfig) {
        if (lpConfig != null) {
            LayoutParams lp = (LayoutParams) mImageView.getLayoutParams();
            mImageView.setLayoutParams(lpConfig.onConfig(lp));
        }
    }

    /*
    * @author lixiaojin
    * create on 2020-03-12 17:15
    * description:设置图标
    */
    public void setImageDrawable(Drawable drawable) {
        if (drawable == null) {
            mImageView.setVisibility(View.GONE);
        } else {
            mImageView.setImageDrawable(drawable);
            mImageView.setVisibility(View.VISIBLE);
        }
    }

    /*
    * @author lixiaojin
    * create on 2020-03-12 17:16
    * description: 返回当前title
    */
    public CharSequence getText() {
        return mTextView.getText();
    }

    /*
    * @author lixiaojin
    * create on 2020-03-12 17:17
    * description: 设置当前title
    */
    public void setText(CharSequence text) {
        mTextView.setText(text);
        if (LangHelper.isNullOrEmpty(text)) {
            mTextView.setVisibility(View.GONE);
        } else {
            mTextView.setVisibility(View.VISIBLE);
        }
    }


    /*
    * @author lixiaojin
    * create on 2020-03-13 14:42
    * description: 设置辅助文本左侧外边距，如果是垂直布局，设置为零，如果是水平布局，设置相应值
    */
    private void checkDetailLeftMargin() {
        LayoutParams detailLp = (LayoutParams) mDetailTextView.getLayoutParams();
        if (mOrientation == VERTICAL) {
            detailLp.leftMargin = 0;
        } else {
            detailLp.leftMargin = ResHelper.getAttrDimen(getContext(), R.attr.common_list_item_detail_h_margin_with_title);
        }
    }

    /*
    * @author lixiaojin
    * create on 2020-03-12 17:19
    * description: 返回辅助信息文本
    */
    public CharSequence getDetailText() {
        return mDetailTextView.getText();
    }


    /*
    * @author lixiaojin
    * create on 2020-03-12 17:20
    * description: 设置辅助展示信息
    */
    public void setDetailText(CharSequence text) {
        mDetailTextView.setText(text);
        if (LangHelper.isNullOrEmpty(text)) {
            mDetailTextView.setVisibility(View.GONE);
        } else {
            mDetailTextView.setVisibility(View.VISIBLE);
        }
    }

    /*
    * @author lixiaojin
    * create on 2020-03-12 17:21
    * description: 返回主文本以及辅助文本
    */
    public int getOrientation() {
        return mOrientation;
    }

    /*
    * @author lixiaojin
    * create on 2020-03-12 17:21
    * description: 设置布局方向
    */
    public void setOrientation(@CommonListItemOrientation int orientation) {
        if (mOrientation == orientation) {
            return;
        }
        mOrientation = orientation;
        //获取主文字和辅助文字的View的布局参数
        LayoutParams titleLp = (LayoutParams) mTextView.getLayoutParams();
        LayoutParams detailLp = (LayoutParams) mDetailTextView.getLayoutParams();
        if (orientation == VERTICAL) {
            //设置主文本字体大小
            mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, ResHelper.getAttrDimen(getContext(), R.attr.common_list_item_title_v_text_size));
            //设置辅助文本字体大小
            mDetailTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, ResHelper.getAttrDimen(getContext(), R.attr.common_list_item_detail_v_text_size));
            titleLp.horizontalChainStyle = LayoutParams.UNSET;
            titleLp.verticalChainStyle = LayoutParams.CHAIN_PACKED;
            titleLp.bottomToBottom = LayoutParams.UNSET;
            titleLp.bottomToTop = mDetailTextView.getId();

            detailLp.horizontalChainStyle = LayoutParams.UNSET;
            detailLp.verticalChainStyle = LayoutParams.CHAIN_PACKED;
            detailLp.leftToRight = LayoutParams.UNSET;
            detailLp.leftToLeft = mTextView.getId();
            detailLp.horizontalBias = 0f;
            detailLp.topToTop = LayoutParams.UNSET;
            detailLp.topToBottom = mTextView.getId();
            detailLp.leftMargin = 0;
            detailLp.topMargin = ResHelper.getAttrDimen(getContext(), R.attr.common_list_item_detail_v_margin_with_title);
        } else {
            mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, ResHelper.getAttrDimen(getContext(), R.attr.common_list_item_title_h_text_size));
            mDetailTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, ResHelper.getAttrDimen(getContext(), R.attr.common_list_item_detail_h_text_size));
            titleLp.horizontalChainStyle = LayoutParams.CHAIN_SPREAD_INSIDE;
            titleLp.verticalChainStyle = LayoutParams.UNSET;
            titleLp.bottomToBottom = LayoutParams.PARENT_ID;
            titleLp.bottomToTop = LayoutParams.UNSET;

            detailLp.horizontalChainStyle = LayoutParams.CHAIN_SPREAD_INSIDE;
            detailLp.verticalChainStyle = LayoutParams.UNSET;
            detailLp.leftToRight = mTextView.getId();
            detailLp.leftToLeft = LayoutParams.UNSET;
            detailLp.horizontalBias = 0f;
            detailLp.topToTop = LayoutParams.PARENT_ID;
            detailLp.topToBottom = LayoutParams.UNSET;
            detailLp.topMargin = 0;
            checkDetailLeftMargin();
        }
    }

    public int getAccessoryType() {
        return mAccessoryType;
    }

    /*
    * @author lixiaojin
    * create on 2020-03-13 15:16
    * description:
    */
    public void setAccessoryType(@CommonListItemAccessoryType int type) {
        //移除现有view
        mAccessoryView.removeAllViews();
        //设定右侧附加view类型
        mAccessoryType = type;
        switch (type) {
            case ACCESSORY_TYPE_CHEVRON: { // 右箭头
                ImageView tempImageView = getAccessoryImageView();
                tempImageView.setImageDrawable(ResHelper.getAttrDrawable(getContext(), R.attr.common_list_item_chevron));
                mAccessoryView.addView(tempImageView);
                mAccessoryView.setVisibility(VISIBLE);
            }
                break;
            case ACCESSORY_TYPE_SWITCH: { //switch开关
                if (mSwitch == null) {
                    mSwitch = new AppCompatCheckBox(getContext());
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
                        mSwitch.setBackground(null);mSwitch.setBackground(null);
                    }
                    mSwitch.setButtonDrawable(ResHelper.getAttrDrawable(getContext(), R.attr.common_list_item_switch));
                    mSwitch.setLayoutParams(getAccessoryLayoutParams());
                    if(mDisableSwitchSelf){
                        mSwitch.setClickable(false);
                        mSwitch.setEnabled(false);
                    }
                }
                mAccessoryView.addView(mSwitch);
                mAccessoryView.setVisibility(VISIBLE);
            }
                break;
            case ACCESSORY_TYPE_CUSTOM:   // 自定义View
                mAccessoryView.setVisibility(VISIBLE);
                break;
            case ACCESSORY_TYPE_NONE:  // 清空所有accessoryView
                mAccessoryView.setVisibility(GONE);
                break;
        }
        LayoutParams titleLp = (LayoutParams) mTextView.getLayoutParams();
        LayoutParams detailLp = (LayoutParams) mDetailTextView.getLayoutParams();
        if (mAccessoryView.getVisibility() != View.GONE) {
            detailLp.goneRightMargin = detailLp.rightMargin;
            titleLp.goneRightMargin = titleLp.rightMargin;
        } else {
            detailLp.goneRightMargin = 0;
            titleLp.goneRightMargin = 0;
        }
    }

    private ViewGroup.LayoutParams getAccessoryLayoutParams() {
        return new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }


    private ImageView getAccessoryImageView() {
        AppCompatImageView resultImageView = new AppCompatImageView(getContext());
        resultImageView.setLayoutParams(getAccessoryLayoutParams());
        resultImageView.setScaleType(ImageView.ScaleType.CENTER);
        SkinValueBuilder builder = SkinValueBuilder.acquire();
        builder.tintColor(R.attr.skin_support_common_list_chevron_color);
        SkinHelper.setSkinValue(resultImageView, builder);
        SkinValueBuilder.release(builder);
        return resultImageView;
    }

    public TextView getTextView() {
        return mTextView;
    }

    public TextView getDetailTextView() {
        return mDetailTextView;
    }

    public CheckBox getSwitch() {
        return mSwitch;
    }

    public ViewGroup getAccessoryContainerView() {
        return mAccessoryView;
    }

    /**
     * 添加自定义的 Accessory View
     *
     * @param view 自定义的 Accessory View
     */
    public void addAccessoryCustomView(View view) {
        if (mAccessoryType == ACCESSORY_TYPE_CUSTOM) {
            mAccessoryView.addView(view);
        }
    }

    public void setDisableSwitchSelf(boolean disableSwitchSelf) {
        mDisableSwitchSelf = disableSwitchSelf;
        if(mSwitch != null){
            mSwitch.setClickable(!disableSwitchSelf);
            mSwitch.setEnabled(!disableSwitchSelf);
        }
    }

    public void setSkinConfig(SkinConfig skinConfig) {
        SkinValueBuilder builder = SkinValueBuilder.acquire();
        if (skinConfig.iconTintColorRes != 0) {
            builder.tintColor(skinConfig.iconTintColorRes);
        }
        if (skinConfig.iconSrcRes != 0) {
            builder.src(skinConfig.iconSrcRes);
        }
        SkinHelper.setSkinValue(mImageView, builder);

        builder.clear();
        if (skinConfig.titleTextColorRes != 0) {
            builder.textColor(skinConfig.titleTextColorRes);
        }
        SkinHelper.setSkinValue(mTextView, builder);

        builder.clear();
        if (skinConfig.detailTextColorRes != 0) {
            builder.textColor(skinConfig.detailTextColorRes);
        }
        SkinHelper.setSkinValue(mDetailTextView, builder);

        builder.clear();
        if (skinConfig.newTipSrcRes != 0) {
            builder.src(skinConfig.newTipSrcRes);
        }

        builder.clear();
        if (skinConfig.tipDotColorRes != 0) {
            builder.bgTintColor(skinConfig.tipDotColorRes);
        }

        builder.release();
    }


    public interface LayoutParamConfig {
        UIConstraintLayout.LayoutParams onConfig(UIConstraintLayout.LayoutParams lp);
    }

    public static class SkinConfig {
        public int iconTintColorRes = R.attr.skin_support_common_list_icon_tint_color;
        public int iconSrcRes = 0;
        public int titleTextColorRes = R.attr.skin_support_common_list_title_color;
        public int detailTextColorRes = R.attr.skin_support_common_list_detail_color;
        public int newTipSrcRes = R.attr.skin_support_common_list_new_drawable;
        public int tipDotColorRes = R.attr.skin_support_common_list_red_point_tint_color;
    }
}
