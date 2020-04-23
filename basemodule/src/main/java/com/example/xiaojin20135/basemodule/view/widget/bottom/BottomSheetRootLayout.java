package com.example.xiaojin20135.basemodule.view.widget.bottom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.example.xiaojin20135.basemodule.R;
import com.example.xiaojin20135.basemodule.layout.UILinearLayout;
import com.example.xiaojin20135.basemodule.skin.SkinHelper;
import com.example.xiaojin20135.basemodule.skin.SkinValueBuilder;
import com.example.xiaojin20135.basemodule.util.ui.ResHelper;

/*
* @author lixiaojin
* create on 2020-03-19 19:31
* description: 根布局 ，取线性布局
*/
public class BottomSheetRootLayout extends UILinearLayout {
    private final int mUsePercentMinHeight;
    private final float mHeightPercent;
    private final int mMaxWidth;

    public BottomSheetRootLayout(Context context) {
        this(context, null);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-20 17:24
    * description: 执行一些初始化信息
    */
    public BottomSheetRootLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        //设置布局方向
        setOrientation(VERTICAL);
        //设置布局背景，此处为背景
        setBackground(ResHelper.getAttrDrawable(context, R.attr.skin_support_bottom_sheet_bg));
        SkinValueBuilder builder = SkinValueBuilder.acquire();
        builder.background(R.attr.skin_support_bottom_sheet_bg);
        SkinHelper.setSkinValue(this, builder);
        builder.release();
        //设置圆角半径
        int radius = ResHelper.getAttrDimen(context, R.attr.bottom_sheet_radius);
        if (radius > 0) {
            //隐藏底部圆角
            setRadius(radius, HIDE_RADIUS_SIDE_BOTTOM);
        }
        //限定的最小高度 640
        mUsePercentMinHeight = ResHelper.getAttrDimen(context, R.attr.bottom_sheet_use_percent_min_height);
        //占屏幕高度百分比 0.75
        mHeightPercent = ResHelper.getAttrFloatValue(context, R.attr.bottom_sheet_height_percent);
        //最大宽度 500
        mMaxWidth = ResHelper.getAttrDimen(context, R.attr.bottom_sheet_max_width);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-19 19:32
    * description: 测量View大小
    */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
        //如果父视图测量的宽度大于当前指定的最大宽度
        if(widthSize > mMaxWidth){
            widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(mMaxWidth, widthMode);
        }
        //如果测量高度大于限制的高度，按比例减少
        int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);
        if (heightSize >= mUsePercentMinHeight) {
            heightMeasureSpec = View.MeasureSpec.makeMeasureSpec((int) (heightSize * mHeightPercent), View.MeasureSpec.AT_MOST);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
