package com.example.xiaojin20135.basemodule.layout;

import android.support.annotation.ColorInt;
import android.support.annotation.IntDef;
import android.view.View;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface LayoutInf {
    int HIDE_RADIUS_SIDE_NONE = 0;
    int HIDE_RADIUS_SIDE_TOP = 1;
    int HIDE_RADIUS_SIDE_RIGHT = 2;
    int HIDE_RADIUS_SIDE_BOTTOM = 3;
    int HIDE_RADIUS_SIDE_LEFT = 4;

    /*
    * @author lixiaojin
    * create on 2020-03-05 11:04
    * description: 第一个IntDef注解
    */
    @IntDef({
            HIDE_RADIUS_SIDE_NONE,
            HIDE_RADIUS_SIDE_TOP,
            HIDE_RADIUS_SIDE_RIGHT,
            HIDE_RADIUS_SIDE_BOTTOM,
            HIDE_RADIUS_SIDE_LEFT})
    /*
    * @author lixiaojin
    * create on 2020-03-05 11:04
    * description: 表明上面注解的保留策略，只保留源码中，编译时删除
    */
    @Retention(RetentionPolicy.SOURCE)
    /*
    * @author lixiaojin
    * create on 2020-03-05 11:08
    * description: 表明上面注解的类型，在方法中使用
    */
    @interface HideRadiusSide {
    }

    /*
    * @author lixiaojin
    * create on 2020-03-05 11:09
    * description: Layout的限定宽度
    */
    boolean setWidthLimit(int widthLimit);

    /*
    * @author lixiaojin
    * create on 2020-03-05 11:09
    * description: Layout 的限定高度
    */
    boolean setHeightLimit(int heightLimit);

    /*
    * @author lixiaojin
    * create on 2020-03-05 11:11
    * description: 从主题中应用阴影权重
    */
    void setUseThemeGeneralShadowElevation();


    /*
    * @author lixiaojin
    * create on 2020-03-05 11:11
    * description: 边框是否包含内边距区域，默认为false
    */
    void setOutlineExcludePadding(boolean outlineExcludePadding);

    /*
    * @author lixiaojin
    * create on 2020-03-05 11:12
    * description: 设置阴影权重
    */
    void setShadowElevation(int elevation);

    /*
    * @author lixiaojin
    * create on 2020-03-05 11:12
    * description: 获取阴影权重
    */
    int getShadowElevation();

    /*
    * @author lixiaojin
    * create on 2020-03-05 11:13
    * description: 设置边框透明度
    */
    void setShadowAlpha(float shadowAlpha);

    /*
    * @author lixiaojin
    * create on 2020-03-05 11:13
    * description: 获取设置的透明度
    */
    float getShadowAlpha();

    /*
    * @author lixiaojin
    * create on 2020-03-05 11:14
    * description: 设置阴影颜色
    */
    void setShadowColor(int shadowColor);

    /*
    * @author lixiaojin
    * create on 2020-03-05 11:16
    * description: 获取阴影颜色
    */
    int getShadowColor();


    /*
    * @author lixiaojin
    * create on 2020-03-05 11:16
    * description: 设置layout的圆角半径
    */
    void setRadius(int radius);


    /*
    * @author lixiaojin
    * create on 2020-03-05 11:17
    * description: 设置圆角半径，附带隐藏某一侧
    */
    void setRadius(int radius, @LayoutHelper.HideRadiusSide int hideRadiusSide);

    /*
    * @author lixiaojin
    * create on 2020-03-05 11:30
    * description: 返回圆角半径
    */
    int getRadius();

    /*
    * @author lixiaojin
    * create on 2020-03-05 11:30
    * description: 设置内边距
    */
    void setOutlineInset(int left, int top, int right, int bottom);

    /**
     * the shadow elevation only work after L, so we provide a downgrading compatible solutions for android 4.x
     * usually we use border, but the border may be redundant for android L+. so will not show border default,
     * if your designer like the border exists with shadow, you can call setShowBorderOnlyBeforeL(false)
     *
     * @param showBorderOnlyBeforeL
     */
    void setShowBorderOnlyBeforeL(boolean showBorderOnlyBeforeL);

    /*
    * @author lixiaojin
    * create on 2020-03-05 11:29
    * description: 设置不显示圆角的边
    */
    void setHideRadiusSide(@HideRadiusSide int hideRadiusSide);

   /*
   * @author lixiaojin
   * create on 2020-03-05 11:29
   * description: 返回隐藏的侧
   */
    int getHideRadiusSide();

    /*
    * @author lixiaojin
    * create on 2020-03-05 11:28
    * description: 确定圆角半径和遮罩
    */
    void setRadiusAndShadow(int radius, int shadowElevation, float shadowAlpha);

    /*
     * @author lixiaojin
     * create on 2020-03-05 11:27
     * description: 设置圆角半径和遮罩，并且隐藏某边
     */
    void setRadiusAndShadow(int radius, @HideRadiusSide int hideRadiusSide, int shadowElevation, float shadowAlpha);


   /*
   * @author lixiaojin
   * create on 2020-03-05 11:27
   * description: 设置圆角半径和遮罩，并且隐藏某边，   有Android版本显示
   */
    void setRadiusAndShadow(int radius, @HideRadiusSide int hideRadiusSide, int shadowElevation, int shadowColor, float shadowAlpha);

    /*
    * @author lixiaojin
    * create on 2020-03-05 11:26
    * description: 设置边框颜色
    */
    void setBorderColor(@ColorInt int borderColor);

    /*
    * @author lixiaojin
    * create on 2020-03-05 11:26
    * description: 设置边框宽度
    */
    void setBorderWidth(int borderWidth);

    /*
    * @author lixiaojin
    * create on 2020-03-05 11:25
    * description: 更新顶部分割线
    */
    void updateTopDivider(int topInsetLeft, int topInsetRight, int topDividerHeight, int topDividerColor);

    /*
    * @author lixiaojin
    * create on 2020-03-05 11:25
    * description: 更新底部分割线
    */
    void updateBottomDivider(int bottomInsetLeft, int bottomInsetRight, int bottomDividerHeight, int bottomDividerColor);

   /*
   * @author lixiaojin
   * create on 2020-03-05 11:25
   * description: 更新左侧分割线
   */
    void updateLeftDivider(int leftInsetTop, int leftInsetBottom, int leftDividerWidth, int leftDividerColor);

   /*
   * @author lixiaojin
   * create on 2020-03-05 11:25
   * description: 更新右侧分割线
   */
    void updateRightDivider(int rightInsetTop, int rightInsetBottom, int rightDividerWidth, int rightDividerColor);

   /*
   * @author lixiaojin
   * create on 2020-03-05 11:25
   * description: 仅显示顶部分割线
   */
    void onlyShowTopDivider(int topInsetLeft, int topInsetRight, int topDividerHeight, int topDividerColor);

    /*
    * @author lixiaojin
    * create on 2020-03-05 11:24
    * description: 仅显示底部分割线
    */
    void onlyShowBottomDivider(int bottomInsetLeft, int bottomInsetRight, int bottomDividerHeight, int bottomDividerColor);

   /*
   * @author lixiaojin
   * create on 2020-03-05 11:24
   * description: 仅显示左侧分割线
   */
    void onlyShowLeftDivider(int leftInsetTop, int leftInsetBottom, int leftDividerWidth, int leftDividerColor);

    /*
    * @author lixiaojin
    * create on 2020-03-05 11:24
    * description: 仅显示右侧分割线
    */
    void onlyShowRightDivider(int rightInsetTop, int rightInsetBottom, int rightDividerWidth, int rightDividerColor);

    /*
    * @author lixiaojin
    * create on 2020-03-05 11:23
    * description: 设置顶部分割线透明度
    */
    void setTopDividerAlpha(int dividerAlpha);

    /*
    * @author lixiaojin
    * create on 2020-03-05 11:23
    * description: 设置底部分割线透明度
    */
    void setBottomDividerAlpha(int dividerAlpha);

    /*
    * @author lixiaojin
    * create on 2020-03-05 11:22
    * description: 设置左侧分割线透明度
    */
    void setLeftDividerAlpha(int dividerAlpha);

    /*
    * @author lixiaojin
    * create on 2020-03-05 11:21
    * description: 设置右侧分割线透明度
    */
    void setRightDividerAlpha(int dividerAlpha);

    /**
     * only available before android L
     *
     * @param color
     */
//    void setOuterNormalColor(int color);

    /*
    * @author lixiaojin
    * create on 2020-03-05 11:20
    * description:  更新左侧分割线颜色
    */
    void updateLeftSeparatorColor(int color);

    /*
    * @author lixiaojin
    * create on 2020-03-05 11:19
    * description: 更新右侧分割线颜色
    */
    void updateRightSeparatorColor(int color);

    /*
    * @author lixiaojin
    * create on 2020-03-05 11:19
    * description: 更新顶部分割线颜色
    */
    void updateTopSeparatorColor(int color);

    /*
    * @author lixiaojin
    * create on 2020-03-05 11:19
    * description: 更新底部分割线颜色
    */
    void updateBottomSeparatorColor(int color);

    /*
    * @author lixiaojin
    * create on 2020-03-05 11:19
    * description: 是否有顶部分割线
    */
    boolean hasTopSeparator();

    /*
    * @author lixiaojin
    * create on 2020-03-05 11:19
    * description: 是否有右分割线
    */
    boolean hasRightSeparator();

    /*
    * @author lixiaojin
    * create on 2020-03-05 11:18
    * description: 是否有左分割线
    */
    boolean hasLeftSeparator();

    /*
    * @author lixiaojin
    * create on 2020-03-05 11:18
    * description: 底部是否有分割线
    */
    boolean hasBottomSeparator();

    /*
    * @author lixiaojin
    * create on 2020-03-05 11:18
    * description: 是否有边框
    */
    boolean hasBorder();

}
