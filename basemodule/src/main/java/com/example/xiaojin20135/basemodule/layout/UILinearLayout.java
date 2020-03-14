package com.example.xiaojin20135.basemodule.layout;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;

/*
* @author lixiaojin
* create on 2020-03-14 14:29
* description: 自定义线性布局，主要设置边框、阴影、透明度
*/
public class UILinearLayout extends AlphaLinearLayout implements LayoutInf {
    private static final String TAG = "UILinearLayout";
    //布局帮助类
    private LayoutHelper mLayoutHelper;

    public UILinearLayout(Context context) {
        super(context);
        init(context, null, 0);
    }

    public UILinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public UILinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        mLayoutHelper = new LayoutHelper(context, attrs, defStyleAttr, this);
        //禁用点击和启用状态变化时的禁用状态
        setChangeAlphaWhenPress(false);
        setChangeAlphaWhenDisable(false);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-14 10:59
    * description: 设置顶部分割线，两侧的内边距、高度和颜色
    */
    @Override
    public void updateTopDivider(int topInsetLeft, int topInsetRight, int topDividerHeight, int topDividerColor) {
        mLayoutHelper.updateTopDivider(topInsetLeft, topInsetRight, topDividerHeight, topDividerColor);
        invalidate();
    }

    /*
    * @author lixiaojin
    * create on 2020-03-14 10:59
    * description: 设置底部分割线，参数同上
    */
    @Override
    public void updateBottomDivider(int bottomInsetLeft, int bottomInsetRight, int bottomDividerHeight, int bottomDividerColor) {
        mLayoutHelper.updateBottomDivider(bottomInsetLeft, bottomInsetRight, bottomDividerHeight, bottomDividerColor);
        invalidate();
    }

    /*
    * @author lixiaojin
    * create on 2020-03-14 11:00
    * description: 设置左侧分割线，参数同上
    */
    @Override
    public void updateLeftDivider(int leftInsetTop, int leftInsetBottom, int leftDividerWidth, int leftDividerColor) {
        mLayoutHelper.updateLeftDivider(leftInsetTop, leftInsetBottom, leftDividerWidth, leftDividerColor);
        invalidate();
    }

    /*
    * @author lixiaojin
    * create on 2020-03-14 11:00
    * description: 设置右侧分割线，参数同上
    */
    @Override
    public void updateRightDivider(int rightInsetTop, int rightInsetBottom, int rightDividerWidth, int rightDividerColor) {
        mLayoutHelper.updateRightDivider(rightInsetTop, rightInsetBottom, rightDividerWidth, rightDividerColor);
        invalidate();
    }

    /*
    * @author lixiaojin
    * create on 2020-03-14 11:00
    * description: 仅显示顶部分割线
    */
    @Override
    public void onlyShowTopDivider(int topInsetLeft, int topInsetRight, int topDividerHeight, int topDividerColor) {
        mLayoutHelper.onlyShowTopDivider(topInsetLeft, topInsetRight, topDividerHeight, topDividerColor);
        invalidate();
    }

    /*
    * @author lixiaojin
    * create on 2020-03-14 11:00
    * description: 仅显示底部分割线
    */
    @Override
    public void onlyShowBottomDivider(int bottomInsetLeft, int bottomInsetRight, int bottomDividerHeight, int bottomDividerColor) {
        mLayoutHelper.onlyShowBottomDivider(bottomInsetLeft, bottomInsetRight, bottomDividerHeight, bottomDividerColor);
        invalidate();
    }

    /*
    * @author lixiaojin
    * create on 2020-03-14 11:01
    * description: 仅显示左侧分割线
    */
    @Override
    public void onlyShowLeftDivider(int leftInsetTop, int leftInsetBottom, int leftDividerWidth, int leftDividerColor) {
        mLayoutHelper.onlyShowLeftDivider(leftInsetTop, leftInsetBottom, leftDividerWidth, leftDividerColor);
        invalidate();
    }

    /*
    * @author lixiaojin
    * create on 2020-03-14 11:01
    * description: 仅显示右侧分割线
    */
    @Override
    public void onlyShowRightDivider(int rightInsetTop, int rightInsetBottom, int rightDividerWidth, int rightDividerColor) {
        mLayoutHelper.onlyShowRightDivider(rightInsetTop, rightInsetBottom, rightDividerWidth, rightDividerColor);
        invalidate();
    }

    /*
    * @author lixiaojin
    * create on 2020-03-14 11:01
    * description: 设置顶部分割线透明度
    */
    @Override
    public void setTopDividerAlpha(int dividerAlpha) {
        mLayoutHelper.setTopDividerAlpha(dividerAlpha);
        invalidate();
    }

    /*
    * @author lixiaojin
    * create on 2020-03-14 11:01
    * description: 设置底部分割线透明度
    */
    @Override
    public void setBottomDividerAlpha(int dividerAlpha) {
        mLayoutHelper.setBottomDividerAlpha(dividerAlpha);
        invalidate();
    }

    /*
    * @author lixiaojin
    * create on 2020-03-14 11:01
    * description: 设置左侧分割线透明度
    */
    @Override
    public void setLeftDividerAlpha(int dividerAlpha) {
        mLayoutHelper.setLeftDividerAlpha(dividerAlpha);
        invalidate();
    }

    /*
    * @author lixiaojin
    * create on 2020-03-14 11:02
    * description: 设置右侧分割线透明度
    */
    @Override
    public void setRightDividerAlpha(int dividerAlpha) {
        mLayoutHelper.setRightDividerAlpha(dividerAlpha);
        invalidate();
    }

    /*
    * @author lixiaojin
    * create on 2020-03-14 11:02
    * description: View测量工作
    */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        widthMeasureSpec = mLayoutHelper.getMeasuredWidthSpec(widthMeasureSpec);
        heightMeasureSpec = mLayoutHelper.getMeasuredHeightSpec(heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int minW = mLayoutHelper.handleMiniWidth(widthMeasureSpec, getMeasuredWidth());
        int minH = mLayoutHelper.handleMiniHeight(heightMeasureSpec, getMeasuredHeight());
        if (widthMeasureSpec != minW || heightMeasureSpec != minH) {
            super.onMeasure(minW, minH);
        }
    }

    /*
    * @author lixiaojin
    * create on 2020-03-14 13:42
    * description: 设置圆角和遮罩
    */
    @Override
    public void setRadiusAndShadow(int radius, int shadowElevation, final float shadowAlpha) {
        mLayoutHelper.setRadiusAndShadow(radius, shadowElevation, shadowAlpha);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-14 13:42
    * description: 设置圆角和遮罩，并附带隐藏某一边的圆角
    */
    @Override
    public void setRadiusAndShadow(int radius, @LayoutHelper.HideRadiusSide int hideRadiusSide, int shadowElevation, final float shadowAlpha) {
        mLayoutHelper.setRadiusAndShadow(radius, hideRadiusSide, shadowElevation, shadowAlpha);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-14 13:45
    * description: 设置圆角的和遮罩，并附带隐藏某一边的圆角，设置阴影颜色
    */
    @Override
    public void setRadiusAndShadow(int radius, int hideRadiusSide, int shadowElevation, int shadowColor, float shadowAlpha) {
        mLayoutHelper.setRadiusAndShadow(radius, hideRadiusSide, shadowElevation,  shadowColor, shadowAlpha);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-14 13:46
    * description: 设置圆角
    */
    @Override
    public void setRadius(int radius) {
        mLayoutHelper.setRadius(radius);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-14 13:46
    * description: 仅设置圆角，并附带隐藏某一边的圆角
    */
    @Override
    public void setRadius(int radius, @LayoutHelper.HideRadiusSide int hideRadiusSide) {
        mLayoutHelper.setRadius(radius, hideRadiusSide);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-14 13:47
    * description: 取得圆角半径
    */
    @Override
    public int getRadius() {
        return mLayoutHelper.getRadius();
    }

    /*
    * @author lixiaojin
    * create on 2020-03-14 13:47
    * description: 设置外轮廓内边距
    */
    @Override
    public void setOutlineInset(int left, int top, int right, int bottom) {
        mLayoutHelper.setOutlineInset(left, top, right, bottom);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-14 13:47
    * description: 设置边框颜色
    */
    @Override
    public void setBorderColor(@ColorInt int borderColor) {
        mLayoutHelper.setBorderColor(borderColor);
        invalidate();
    }

    /*
    * @author lixiaojin
    * create on 2020-03-14 13:47
    * description: 设置边框宽度
    */
    @Override
    public void setBorderWidth(int borderWidth) {
        mLayoutHelper.setBorderWidth(borderWidth);
        invalidate();
    }

    /*
    * @author lixiaojin
    * create on 2020-03-14 13:48
    * description: 设置是否仅在居前时显示边框
    */
    @Override
    public void setShowBorderOnlyBeforeL(boolean showBorderOnlyBeforeL) {
        mLayoutHelper.setShowBorderOnlyBeforeL(showBorderOnlyBeforeL);
        invalidate();
    }

    /*
    * @author lixiaojin
    * create on 2020-03-14 13:48
    * description: 设置隐藏圆角的边
    */
    @Override
    public void setHideRadiusSide(int hideRadiusSide) {
        mLayoutHelper.setHideRadiusSide(hideRadiusSide);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-14 13:48
    * description: 取得隐藏圆角的边
    */
    @Override
    public int getHideRadiusSide() {
        return mLayoutHelper.getHideRadiusSide();
    }

    /*
    * @author lixiaojin
    * create on 2020-03-14 13:48
    * description: 设置限定宽度值
    */
    @Override
    public boolean setWidthLimit(int widthLimit) {
        if (mLayoutHelper.setWidthLimit(widthLimit)) {
            requestLayout();
            invalidate();
        }
        return true;
    }

    /*
    * @author lixiaojin
    * create on 2020-03-14 13:48
    * description: 设置限定高度值
    */
    @Override
    public boolean setHeightLimit(int heightLimit) {
        if (mLayoutHelper.setHeightLimit(heightLimit)) {
            requestLayout();
            invalidate();
        }
        return true;
    }


    @Override
    public void setUseThemeGeneralShadowElevation() {
        mLayoutHelper.setUseThemeGeneralShadowElevation();
    }

    /*
    * @author lixiaojin
    * create on 2020-03-14 13:49
    * description: 设置外轮廓内边距
    */
    @Override
    public void setOutlineExcludePadding(boolean outlineExcludePadding) {
        mLayoutHelper.setOutlineExcludePadding(outlineExcludePadding);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-14 13:50
    * description: 更新底部分割线颜色
    */
    @Override
    public void updateBottomSeparatorColor(int color) {
        mLayoutHelper.updateBottomSeparatorColor(color);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-14 13:51
    * description: 更新左侧分割线颜色
    */
    @Override
    public void updateLeftSeparatorColor(int color) {
        mLayoutHelper.updateLeftSeparatorColor(color);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-14 13:51
    * description: 更新右侧分割线颜色
    */
    @Override
    public void updateRightSeparatorColor(int color) {
        mLayoutHelper.updateRightSeparatorColor(color);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-14 13:51
    * description: 更新顶部分割线颜色
    */
    @Override
    public void updateTopSeparatorColor(int color) {
        mLayoutHelper.updateTopSeparatorColor(color);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-14 13:51
    * description: 设置View的高度，该项决定阴影的大小
    */
    @Override
    public void setShadowElevation(int elevation) {
        mLayoutHelper.setShadowElevation(elevation);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-14 13:51
    * description: 返回View的高度，即阴影的大小
    */
    @Override
    public int getShadowElevation() {
        return mLayoutHelper.getShadowElevation();
    }

    /*
    * @author lixiaojin
    * create on 2020-03-14 14:04
    * description: 设置View的透明度，值越小越透明
    */
    @Override
    public void setShadowAlpha(float shadowAlpha) {
        mLayoutHelper.setShadowAlpha(shadowAlpha);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-14 14:08
    * description:设置阴影颜色
    */
    @Override
    public void setShadowColor(int shadowColor) {
        mLayoutHelper.setShadowColor(shadowColor);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-14 14:09
    * description: 取得阴影颜色
    */
    @Override
    public int getShadowColor() {
        return mLayoutHelper.getShadowColor();
    }

    /*
    * @author lixiaojin
    * create on 2020-03-14 14:09
    * description:取得View 的透明度
    */
    @Override
    public float getShadowAlpha() {
        return mLayoutHelper.getShadowAlpha();
    }

    /*
    * @author lixiaojin
    * create on 2020-03-14 14:10
    * description:绘制子视图
    */
    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        //绘制边界线
        mLayoutHelper.drawDividers(canvas, getWidth(), getHeight());
        //绘制圆角边框
        mLayoutHelper.dispatchRoundBorderDraw(canvas);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-14 14:12
    * description: 是否有边框
    */
    @Override
    public boolean hasBorder() {
        return mLayoutHelper.hasBorder();
    }

    /*
    * @author lixiaojin
    * create on 2020-03-14 14:13
    * description: 是否有左侧分割线
    */
    @Override
    public boolean hasLeftSeparator() {
        return mLayoutHelper.hasLeftSeparator();
    }

    /*
    * @author lixiaojin
    * create on 2020-03-14 14:13
    * description: 是否有右侧分割线
    */
    @Override
    public boolean hasTopSeparator() {
        return mLayoutHelper.hasTopSeparator();
    }

    /*
    * @author lixiaojin
    * create on 2020-03-14 14:13
    * description: 是否有右侧分割线
    */
    @Override
    public boolean hasRightSeparator() {
        return mLayoutHelper.hasRightSeparator();
    }

    /*
    * @author lixiaojin
    * create on 2020-03-14 14:13
    * description: 是否有底部分割线
    */
    @Override
    public boolean hasBottomSeparator() {
        return mLayoutHelper.hasBottomSeparator();
    }
}
