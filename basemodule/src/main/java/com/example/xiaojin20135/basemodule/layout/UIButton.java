package com.example.xiaojin20135.basemodule.layout;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;

import com.example.xiaojin20135.basemodule.util.LogUtils;

/*
* @author lixiaojin
* create on 2020-03-09 13:46
* description: 自定义Button
*
*  主要控制四个边的分割线，包括粗细、是否显示、颜色、透明度等的设置
*/
public class UIButton extends AlphaButton implements LayoutInf {
    private static final String TAG = "UIButton";
    //布局帮助类
    private LayoutHelper mLayoutHelper;

    public UIButton(Context context) {
        super(context);
        init(context, null, 0);
    }

    public UIButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public UIButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        mLayoutHelper = new LayoutHelper(context, attrs, defStyleAttr, this);
        setChangeAlphaWhenDisable(false);
        setChangeAlphaWhenPress(false);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 13:48
    * description: 顶部分割线
    */
    @Override
    public void updateTopDivider(int topInsetLeft, int topInsetRight, int topDividerHeight, int topDividerColor) {
        LogUtils.d(TAG,"处理顶部分割线");
        LogUtils.d(TAG,"topInsetLeft = " + topInsetLeft);
        LogUtils.d(TAG,"topInsetRight = " + topInsetRight);
        LogUtils.d(TAG,"topDividerHeight = " + topDividerHeight);
        LogUtils.d(TAG,"topDividerColor = " + topDividerColor);
        mLayoutHelper.updateTopDivider(topInsetLeft, topInsetRight, topDividerHeight, topDividerColor);
        //对View进行重绘
        invalidate();
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 13:48
    * description: 底部分割线
    */
    @Override
    public void updateBottomDivider(int bottomInsetLeft, int bottomInsetRight, int bottomDividerHeight, int bottomDividerColor) {
        LogUtils.d(TAG,"处理底部分割线");
        LogUtils.d(TAG,"bottomInsetLeft = " + bottomInsetLeft);
        LogUtils.d(TAG,"bottomInsetRight = " + bottomInsetRight);
        LogUtils.d(TAG,"bottomDividerHeight = " + bottomDividerHeight);
        LogUtils.d(TAG,"bottomDividerColor = " + bottomDividerColor);
        mLayoutHelper.updateBottomDivider(bottomInsetLeft, bottomInsetRight, bottomDividerHeight, bottomDividerColor);
        //对View进行重绘
        invalidate();
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 14:28
    * description: 同上
    */
    @Override
    public void updateLeftDivider(int leftInsetTop, int leftInsetBottom, int leftDividerWidth, int leftDividerColor) {
        LogUtils.d(TAG,"处理左侧分割线");
        LogUtils.d(TAG,"leftInsetTop = " + leftInsetTop);
        LogUtils.d(TAG,"leftInsetBottom = " + leftInsetBottom);
        LogUtils.d(TAG,"leftDividerWidth = " + leftDividerWidth);
        LogUtils.d(TAG,"leftDividerColor = " + leftDividerColor);
        mLayoutHelper.updateLeftDivider(leftInsetTop, leftInsetBottom, leftDividerWidth, leftDividerColor);
        //对View进行重绘
        invalidate();
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 14:28
    * description: 同上
    */
    public void updateRightDivider(int rightInsetTop, int rightInsetBottom, int rightDividerWidth, int rightDividerColor) {
        LogUtils.d(TAG,"处理右侧分割线");
        LogUtils.d(TAG,"rightInsetTop = " + rightInsetTop);
        LogUtils.d(TAG,"rightInsetBottom = " + rightInsetBottom);
        LogUtils.d(TAG,"rightDividerWidth = " + rightDividerWidth);
        LogUtils.d(TAG,"rightDividerColor = " + rightDividerColor);
        mLayoutHelper.updateRightDivider(rightInsetTop, rightInsetBottom, rightDividerWidth, rightDividerColor);
        //对View进行 重绘
        invalidate();
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 14:28
    * description: 仅显示顶部分割线
    */
    @Override
    public void onlyShowTopDivider(int topInsetLeft, int topInsetRight, int topDividerHeight, int topDividerColor) {
        LogUtils.d(TAG,"仅显示顶部分割线");
        LogUtils.d(TAG,"topInsetLeft = " + topInsetLeft);
        LogUtils.d(TAG,"topInsetRight = " + topInsetRight);
        LogUtils.d(TAG,"topDividerHeight = " + topDividerHeight);
        LogUtils.d(TAG,"topDividerColor = " + topDividerColor);
        mLayoutHelper.onlyShowTopDivider(topInsetLeft, topInsetRight, topDividerHeight, topDividerColor);
        //对View进行重绘
        invalidate();
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 14:29
    * description: 同上
    */
    @Override
    public void onlyShowBottomDivider(int bottomInsetLeft, int bottomInsetRight, int bottomDividerHeight, int bottomDividerColor) {
        LogUtils.d(TAG,"仅显示底部分割线");
        LogUtils.d(TAG,"bottomInsetLeft = " + bottomInsetLeft);
        LogUtils.d(TAG,"bottomInsetRight = " + bottomInsetRight);
        LogUtils.d(TAG,"bottomDividerHeight = " + bottomDividerHeight);
        LogUtils.d(TAG,"bottomDividerColor = " + bottomDividerColor);
        mLayoutHelper.onlyShowBottomDivider(bottomInsetLeft, bottomInsetRight, bottomDividerHeight, bottomDividerColor);
        //对View进行重绘
        invalidate();
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 14:32
    * description: 同上
    */
    @Override
    public void onlyShowLeftDivider(int leftInsetTop, int leftInsetBottom, int leftDividerWidth, int leftDividerColor) {
        LogUtils.d(TAG,"仅显示左侧分割线");
        LogUtils.d(TAG,"leftInsetTop = " + leftInsetTop);
        LogUtils.d(TAG,"leftInsetBottom = " + leftInsetBottom);
        LogUtils.d(TAG,"leftDividerWidth = " + leftDividerWidth);
        LogUtils.d(TAG,"leftDividerColor = " + leftDividerColor);
        mLayoutHelper.onlyShowLeftDivider(leftInsetTop, leftInsetBottom, leftDividerWidth, leftDividerColor);
        //对View进行重绘
        invalidate();
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 14:32
    * description: 同上
    */
    @Override
    public void onlyShowRightDivider(int rightInsetTop, int rightInsetBottom, int rightDividerWidth, int rightDividerColor) {
        LogUtils.d(TAG,"仅显示右侧分割线");
        LogUtils.d(TAG,"rightInsetTop = " + rightInsetTop);
        LogUtils.d(TAG,"rightInsetBottom = " + rightInsetBottom);
        LogUtils.d(TAG,"rightDividerWidth = " + rightDividerWidth);
        LogUtils.d(TAG,"rightDividerColor = " + rightDividerColor);
        mLayoutHelper.onlyShowRightDivider(rightInsetTop, rightInsetBottom, rightDividerWidth, rightDividerColor);
        //对View进行重绘
        invalidate();
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 14:33
    * description:  设置顶部分割线透明度
    */
    @Override
    public void setTopDividerAlpha(int dividerAlpha) {
        LogUtils.d(TAG,"'设置顶部分割线透明度dividerAlpha = " + dividerAlpha);
        mLayoutHelper.setTopDividerAlpha(dividerAlpha);
        //对View进行重绘
        invalidate();
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 14:34
    * description: 设置底部分割线透明度
    */
    @Override
    public void setBottomDividerAlpha(int dividerAlpha) {
        LogUtils.d(TAG,"'设置底部分割线透明度dividerAlpha = " + dividerAlpha);
        mLayoutHelper.setBottomDividerAlpha(dividerAlpha);
        //对View进行重绘
        invalidate();
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 14:34
    * description: 同上
    */
    @Override
    public void setLeftDividerAlpha(int dividerAlpha) {
        LogUtils.d(TAG,"'设置左侧分割线透明度dividerAlpha = " + dividerAlpha);
        mLayoutHelper.setLeftDividerAlpha(dividerAlpha);
        //对View进行重绘
        invalidate();
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 14:35
    * description: 同上
    */
    @Override
    public void setRightDividerAlpha(int dividerAlpha) {
        LogUtils.d(TAG,"'设置右侧分割线透明度dividerAlpha = " + dividerAlpha);
        mLayoutHelper.setRightDividerAlpha(dividerAlpha);
        //对View进行重绘
        invalidate();
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 14:35
    * description: 设置需要隐藏圆角的边
    */
    @Override
    public void setHideRadiusSide(int hideRadiusSide) {
        LogUtils.d(TAG,"设置需要隐藏圆角的边 hideRadiusSide = " + hideRadiusSide);
        mLayoutHelper.setHideRadiusSide(hideRadiusSide);
        invalidate();
    }

    @Override
    public int getHideRadiusSide() {
        return mLayoutHelper.getHideRadiusSide();
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 14:06
    * description: 测量
    * 参数为父控件提供给子View的参考大小
    */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //获取宽、高新的测量参数
        widthMeasureSpec = mLayoutHelper.getMeasuredWidthSpec(widthMeasureSpec);
        heightMeasureSpec = mLayoutHelper.getMeasuredHeightSpec(heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //判断是否满足最小宽度和最小高度要求
        int minW = mLayoutHelper.handleMiniWidth(widthMeasureSpec, getMeasuredWidth());
        int minH = mLayoutHelper.handleMiniHeight(heightMeasureSpec, getMeasuredHeight());
        if (widthMeasureSpec != minW || heightMeasureSpec != minH) {
            super.onMeasure(minW, minH);
        }
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 15:13
    * description: 设置圆角和阴影
    */
    @Override
    public void setRadiusAndShadow(int radius, int shadowElevation, final float shadowAlpha) {
        LogUtils.d(TAG,"设置圆角和阴影 radius = " + radius  + " shadowAlpha = " + shadowAlpha);
        mLayoutHelper.setRadiusAndShadow(radius, shadowElevation, shadowAlpha);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 15:15
    * description: 设置圆角和阴影  附带隐藏圆角的边设置
    */
    @Override
    public void setRadiusAndShadow(int radius, @LayoutHelper.HideRadiusSide int hideRadiusSide, int shadowElevation, final float shadowAlpha) {
        LogUtils.d(TAG,"设置圆角和阴影 radius = " + radius  + " shadowAlpha = " + shadowAlpha);
        mLayoutHelper.setRadiusAndShadow(radius, hideRadiusSide, shadowElevation, shadowAlpha);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 15:44
    * description: 设置圆角和阴影
    */
    @Override
    public void setRadiusAndShadow(int radius, int hideRadiusSide, int shadowElevation, int shadowColor, float shadowAlpha) {
        LogUtils.d(TAG,"设置圆角和阴影 radius = " + radius  + " shadowAlpha = " + shadowAlpha);
        mLayoutHelper.setRadiusAndShadow(radius, hideRadiusSide, shadowElevation,  shadowColor, shadowAlpha);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 16:04
    * description: 设置圆角半径
    */
    @Override
    public void setRadius(int radius) {
        mLayoutHelper.setRadius(radius);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 16:04
    * description: 设置圆角半径，并附带不显示圆角的边
    */
    @Override
    public void setRadius(int radius, @LayoutHelper.HideRadiusSide int hideRadiusSide) {
        mLayoutHelper.setRadius(radius, hideRadiusSide);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 16:04
    * description: 返回圆角半径
    */
    @Override
    public int getRadius() {
        return mLayoutHelper.getRadius();
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 16:04
    * description:设置外边框内边距
    */
    @Override
    public void setOutlineInset(int left, int top, int right, int bottom) {
        LogUtils.d(TAG,"设置外边框内边距 left = " + left + "   top = " + top + "  right = " + right + "  bottom = " + bottom);
        mLayoutHelper.setOutlineInset(left, top, right, bottom);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 16:05
    * description: 设置边框颜色
    */
    @Override
    public void setBorderColor(@ColorInt int borderColor) {
        LogUtils.d(TAG,"设置边框颜色 borderColor = " + borderColor);
        mLayoutHelper.setBorderColor(borderColor);
        invalidate();
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 16:06
    * description: 设置边框宽度
    */
    @Override
    public void setBorderWidth(int borderWidth) {
        LogUtils.d(TAG,"设置边框宽度 borderWidth = " + borderWidth);
        mLayoutHelper.setBorderWidth(borderWidth);
        invalidate();
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 16:06
    * description:
    */
    @Override
    public void setShowBorderOnlyBeforeL(boolean showBorderOnlyBeforeL) {
        LogUtils.d(TAG,"仅仅居前时显示边框");
        mLayoutHelper.setShowBorderOnlyBeforeL(showBorderOnlyBeforeL);
        invalidate();
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 16:12
    * description: 设置宽度
    */
    @Override
    public boolean setWidthLimit(int widthLimit) {
        LogUtils.d(TAG,"设置宽度");
        ///如果设置新宽度
        if (mLayoutHelper.setWidthLimit(widthLimit)) {
            requestLayout();//重新执行View绘制中的Layout
            invalidate(); //重新执行View绘制中的draw
        }
        return true;
    }

    ///同上
    @Override
    public boolean setHeightLimit(int heightLimit) {
        LogUtils.d(TAG,"设置高度");
        if (mLayoutHelper.setHeightLimit(heightLimit)) {
            requestLayout();
            invalidate();
        }
        return true;
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 16:21
    * description: 设置遮罩
    */
    @Override
    public void setUseThemeGeneralShadowElevation() {
        LogUtils.d(TAG,"设置遮罩");
        mLayoutHelper.setUseThemeGeneralShadowElevation();
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 16:21
    * description: 设置外边框内边距
    */
    @Override
    public void setOutlineExcludePadding(boolean outlineExcludePadding) {
        LogUtils.d(TAG,"设置外轮廓边距");
        mLayoutHelper.setOutlineExcludePadding(outlineExcludePadding);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 16:24
    * description: 设置遮罩标识
    */
    @Override
    public void setShadowElevation(int elevation) {
        LogUtils.d(TAG,"设置遮罩标识");
        mLayoutHelper.setShadowElevation(elevation);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 16:28
    * description:
    */
    @Override
    public int getShadowElevation() {
        LogUtils.d(TAG,"返回遮罩标识");
        return mLayoutHelper.getShadowElevation();
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 16:28
    * description:   设置遮罩透明度
    */
    @Override
    public void setShadowAlpha(float shadowAlpha) {
        LogUtils.d(TAG,"设置透明度");
        mLayoutHelper.setShadowAlpha(shadowAlpha);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 16:29
    * description: 显示遮罩透明度
    */
    @Override
    public float getShadowAlpha() {
        LogUtils.d(TAG,"返回透明度");
        return mLayoutHelper.getShadowAlpha();
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 16:29
    * description: 设置遮罩颜色
    */
    @Override
    public void setShadowColor(int shadowColor) {
        LogUtils.d(TAG,"设置遮罩颜色");
        mLayoutHelper.setShadowColor(shadowColor);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 16:29
    * description: 返回遮罩颜色
    */
    @Override
    public int getShadowColor() {
        LogUtils.d(TAG,"返回遮罩颜色");
        return mLayoutHelper.getShadowColor();
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 16:29
    * description: 更新底部分割线颜色
    */
    @Override
    public void updateBottomSeparatorColor(int color) {
        LogUtils.d(TAG,"更新底部分割线颜色");
        mLayoutHelper.updateBottomSeparatorColor(color);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 16:30
    * description: 同上
    */
    @Override
    public void updateLeftSeparatorColor(int color) {
        LogUtils.d(TAG,"更新左侧分割线颜色");
        mLayoutHelper.updateLeftSeparatorColor(color);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 16:30
    * description: 同上
    */
    @Override
    public void updateRightSeparatorColor(int color) {
        LogUtils.d(TAG,"更新右侧分割线颜色");
        mLayoutHelper.updateRightSeparatorColor(color);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 16:31
    * description: 同上
    */
    @Override
    public void updateTopSeparatorColor(int color) {
        LogUtils.d(TAG,"更新顶部分割线颜色");
        mLayoutHelper.updateTopSeparatorColor(color);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 16:31
    * description: 子控件绘制
    */
    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        mLayoutHelper.drawDividers(canvas, getWidth(), getHeight());
        mLayoutHelper.dispatchRoundBorderDraw(canvas);
        LogUtils.d(TAG,"子控件绘制");
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 16:33
    * description: 是否有边框
    */
    @Override
    public boolean hasBorder() {
        return mLayoutHelper.hasBorder();
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 16:33
    * description: 是否有左分割线
    */
    @Override
    public boolean hasLeftSeparator() {
        return mLayoutHelper.hasLeftSeparator();
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 16:34
    * description: 同上
    */
    @Override
    public boolean hasTopSeparator() {
        return mLayoutHelper.hasTopSeparator();
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 16:34
    * description: 同上
    */
    @Override
    public boolean hasRightSeparator() {
        return mLayoutHelper.hasRightSeparator();
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 16:34
    * description: 同上
    */
    @Override
    public boolean hasBottomSeparator() {
        return mLayoutHelper.hasBottomSeparator();
    }
}
