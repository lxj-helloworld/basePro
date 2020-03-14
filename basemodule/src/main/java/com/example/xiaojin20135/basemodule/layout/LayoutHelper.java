package com.example.xiaojin20135.basemodule.layout;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;

import com.example.xiaojin20135.basemodule.R;
import com.example.xiaojin20135.basemodule.util.LogUtils;
import com.example.xiaojin20135.basemodule.util.ui.ResHelper;

import java.lang.ref.WeakReference;

/*
* @author lixiaojin
* create on 2020-03-09 14:19
* description: 布局帮助类
*/
public class LayoutHelper implements LayoutInf{
    private static final String TAG = "LayoutHelper";
    private Context mContext;
    // layout 宽高
    private int mWidthLimit = 0; //指定的宽度
    private int mHeightLimit = 0; //指定的高度
    private int mWidthMini = 0; //指定的最小宽度
    private int mHeightMini = 0; //指定的最小高度

    private static int defaultValue = 0;
    // 分割线
    private int mTopDividerHeight = defaultValue;
    private int mTopDividerInsetLeft = defaultValue;
    private int mTopDividerInsetRight = defaultValue;
    private int mTopDividerColor;
    private int mTopDividerAlpha = 255;

    private int mBottomDividerHeight = defaultValue;
    private int mBottomDividerInsetLeft = defaultValue;
    private int mBottomDividerInsetRight = defaultValue;
    private int mBottomDividerColor;
    private int mBottomDividerAlpha = 255;

    private int mLeftDividerWidth = defaultValue;
    private int mLeftDividerInsetTop = defaultValue;
    private int mLeftDividerInsetBottom = defaultValue;
    private int mLeftDividerColor;
    private int mLeftDividerAlpha = 255;

    private int mRightDividerWidth = defaultValue;
    private int mRightDividerInsetTop = defaultValue;
    private int mRightDividerInsetBottom = defaultValue;
    private int mRightDividerColor;
    private int mRightDividerAlpha = 255;
    private Paint mDividerPaint;

    // 圆角
    private Paint mClipPaint;
    private PorterDuffXfermode mMode;
    private int mRadius;
    private @LayoutInf.HideRadiusSide int mHideRadiusSide = HIDE_RADIUS_SIDE_NONE;
    private float[] mRadiusArray; ///长度为8 ，两个一组，分别表示左边框、上边框、右边框和下边框
    private RectF mBorderRect;
    private int mBorderColor = 0;
    private int mBorderWidth = 0;
    private int mOuterNormalColor = 0;
    private WeakReference<View> mOwner; //弱引用，是对象能够在GC运行时被及时回收
    private boolean mIsOutlineExcludePadding = false;
    private Path mPath = new Path();

    // 遮罩
    private boolean mIsShowBorderOnlyBeforeL = false;
    private int mShadowElevation = 0; //View的高度即隐藏的大小
    private float mShadowAlpha; ///透明度，值越小，越透明
    private int mShadowColor = Color.BLACK; //阴影颜色

    // 轮廓内边距
    private int mOutlineInsetLeft = defaultValue;
    private int mOutlineInsetRight = defaultValue;
    private int mOutlineInsetTop = defaultValue;
    private int mOutlineInsetBottom = defaultValue;

    public LayoutHelper(Context context, AttributeSet attrs, int defAttr, View owner) {
        this(context, attrs, defAttr, 0, owner);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-06 15:37
    * description: defAttr 为当前主题 的默认属性
    */
    public LayoutHelper(Context context, AttributeSet attrs, int defAttr, int defStyleRes, View owner) {
        mContext = context;
        mOwner = new WeakReference<>(owner);
        mBottomDividerColor = mTopDividerColor = ContextCompat.getColor(context, R.color.config_color_separator);
        mMode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
        mClipPaint = new Paint();
        mClipPaint.setAntiAlias(true);
        mShadowAlpha = ResHelper.getAttrFloatValue(context, R.attr.general_shadow_alpha);

        mBorderRect = new RectF();

        LogUtils.d(TAG,"mBottomDividerColor = " + mBottomDividerColor);
        LogUtils.d(TAG,"mShadowAlpha = " + mShadowAlpha);

        int radius = 0, shadow = 0;
        boolean useThemeGeneralShadowElevation = false;
        if (null != attrs || defAttr != 0 || defStyleRes != 0) {
            //获取样式属性信息
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Layout, defAttr, defStyleRes);
            int count = ta.getIndexCount();
            for (int i = 0; i < count; ++i) {
                int index = ta.getIndex(i);
                if (index == R.styleable.Layout_android_maxWidth) {
                    mWidthLimit = ta.getDimensionPixelSize(index, mWidthLimit);
                } else if (index == R.styleable.Layout_android_maxHeight) {
                    mHeightLimit = ta.getDimensionPixelSize(index, mHeightLimit);
                } else if (index == R.styleable.Layout_android_minWidth) {
                    mWidthMini = ta.getDimensionPixelSize(index, mWidthMini);
                } else if (index == R.styleable.Layout_android_minHeight) {
                    mHeightMini = ta.getDimensionPixelSize(index, mHeightMini);
                } else if (index == R.styleable.Layout_topDividerColor) {
                    mTopDividerColor = ta.getColor(index, mTopDividerColor);
                } else if (index == R.styleable.Layout_topDividerHeight) {
                    mTopDividerHeight = ta.getDimensionPixelSize(index, mTopDividerHeight);
                } else if (index == R.styleable.Layout_topDividerInsetLeft) {
                    mTopDividerInsetLeft = ta.getDimensionPixelSize(index, mTopDividerInsetLeft);
                } else if (index == R.styleable.Layout_topDividerInsetRight) {
                    mTopDividerInsetRight = ta.getDimensionPixelSize(index, mTopDividerInsetRight);
                } else if (index == R.styleable.Layout_bottomDividerColor) {
                    mBottomDividerColor = ta.getColor(index, mBottomDividerColor);
                } else if (index == R.styleable.Layout_bottomDividerHeight) {
                    mBottomDividerHeight = ta.getDimensionPixelSize(index, mBottomDividerHeight);
                } else if (index == R.styleable.Layout_bottomDividerInsetLeft) {
                    mBottomDividerInsetLeft = ta.getDimensionPixelSize(index, mBottomDividerInsetLeft);
                } else if (index == R.styleable.Layout_bottomDividerInsetRight) {
                    mBottomDividerInsetRight = ta.getDimensionPixelSize(index, mBottomDividerInsetRight);
                } else if (index == R.styleable.Layout_leftDividerColor) {
                    mLeftDividerColor = ta.getColor(index, mLeftDividerColor);
                } else if (index == R.styleable.Layout_leftDividerWidth) {
                    mLeftDividerWidth = ta.getDimensionPixelSize(index, mLeftDividerWidth);
                } else if (index == R.styleable.Layout_leftDividerInsetTop) {
                    mLeftDividerInsetTop = ta.getDimensionPixelSize(index, mLeftDividerInsetTop);
                } else if (index == R.styleable.Layout_leftDividerInsetBottom) {
                    mLeftDividerInsetBottom = ta.getDimensionPixelSize(index, mLeftDividerInsetBottom);
                } else if (index == R.styleable.Layout_rightDividerColor) {
                    mRightDividerColor = ta.getColor(index, mRightDividerColor);
                } else if (index == R.styleable.Layout_rightDividerWidth) {
                    mRightDividerWidth = ta.getDimensionPixelSize(index, mRightDividerWidth);
                } else if (index == R.styleable.Layout_rightDividerInsetTop) {
                    mRightDividerInsetTop = ta.getDimensionPixelSize(index, mRightDividerInsetTop);
                } else if (index == R.styleable.Layout_rightDividerInsetBottom) {
                    mRightDividerInsetBottom = ta.getDimensionPixelSize(index, mRightDividerInsetBottom);
                } else if (index == R.styleable.Layout_borderColor) {
                    mBorderColor = ta.getColor(index, mBorderColor);
                } else if (index == R.styleable.Layout_borderWidth) {
                    mBorderWidth = ta.getDimensionPixelSize(index, mBorderWidth);
                } else if (index == R.styleable.Layout_radius) {
                    radius = ta.getDimensionPixelSize(index, 0);
                } else if (index == R.styleable.Layout_outerNormalColor) {
                    mOuterNormalColor = ta.getColor(index, mOuterNormalColor);
                } else if (index == R.styleable.Layout_hideRadiusSide) {
                    mHideRadiusSide = ta.getColor(index, mHideRadiusSide);
                } else if (index == R.styleable.Layout_showBorderOnlyBeforeL) {
                    mIsShowBorderOnlyBeforeL = ta.getBoolean(index, mIsShowBorderOnlyBeforeL);
                } else if (index == R.styleable.Layout_shadowElevation) {
                    shadow = ta.getDimensionPixelSize(index, shadow);
                } else if (index == R.styleable.Layout_shadowAlpha) {
                    mShadowAlpha = ta.getFloat(index, mShadowAlpha);
                } else if (index == R.styleable.Layout_useThemeGeneralShadowElevation) {
                    useThemeGeneralShadowElevation = ta.getBoolean(index, false);
                } else if (index == R.styleable.Layout_outlineInsetLeft) {
                    mOutlineInsetLeft = ta.getDimensionPixelSize(index, 0);
                } else if (index == R.styleable.Layout_outlineInsetRight) {
                    mOutlineInsetRight = ta.getDimensionPixelSize(index, 0);
                } else if (index == R.styleable.Layout_outlineInsetTop) {
                    mOutlineInsetTop = ta.getDimensionPixelSize(index, 0);
                } else if (index == R.styleable.Layout_outlineInsetBottom) {
                    mOutlineInsetBottom = ta.getDimensionPixelSize(index, 0);
                } else if (index == R.styleable.Layout_outlineExcludePadding) {
                    mIsOutlineExcludePadding = ta.getBoolean(index, false);
                }
            }
            ta.recycle();
        }
        LogUtils.d(TAG,"shadow = " + shadow);
        LogUtils.d(TAG,"useThemeGeneralShadowElevation = " + useThemeGeneralShadowElevation);
        if (shadow == 0 && useThemeGeneralShadowElevation) {
            shadow = ResHelper.getAttrDimen(context, R.attr.general_shadow_elevation);
        }
        LogUtils.d(TAG," after shadow = " + shadow);
        setRadiusAndShadow(radius, mHideRadiusSide, shadow, mShadowAlpha);
    }

    @Override
    public void setUseThemeGeneralShadowElevation() {
        mShadowElevation = ResHelper.getAttrDimen(mContext, R.attr.general_shadow_elevation);
        setRadiusAndShadow(mRadius, mHideRadiusSide, mShadowElevation, mShadowAlpha);
    }

    @Override
    public void setOutlineExcludePadding(boolean outlineExcludePadding) {
        if (useFeature()) {
            View owner = mOwner.get();
            if (owner == null) {
                return;
            }
            mIsOutlineExcludePadding = outlineExcludePadding;
            owner.invalidateOutline();
        }
    }

    @Override
    public boolean setWidthLimit(int widthLimit) {
        if (mWidthLimit != widthLimit) {
            mWidthLimit = widthLimit;
            return true;
        }
        return false;
    }

    @Override
    public boolean setHeightLimit(int heightLimit) {
        if (mHeightLimit != heightLimit) {
            mHeightLimit = heightLimit;
            return true;
        }
        return false;
    }

    @Override
    public void updateLeftSeparatorColor(int color) {
        if (mLeftDividerColor != color) {
            mLeftDividerColor = color;
            invalidate();
        }
    }

    @Override
    public void updateBottomSeparatorColor(int color) {
        if (mBottomDividerColor != color) {
            mBottomDividerColor = color;
            invalidate();
        }
    }

    @Override
    public void updateTopSeparatorColor(int color) {
        if (mTopDividerColor != color) {
            mTopDividerColor = color;
            invalidate();
        }
    }

    @Override
    public void updateRightSeparatorColor(int color) {
        if (mRightDividerColor != color) {
            mRightDividerColor = color;
            invalidate();
        }
    }

    @Override
    public int getShadowElevation() {
        return mShadowElevation;
    }

    @Override
    public float getShadowAlpha() {
        return mShadowAlpha;
    }

    @Override
    public int getShadowColor() {
        return mShadowColor;
    }

    @Override
    public void setOutlineInset(int left, int top, int right, int bottom) {
        if (useFeature()) {
            View owner = mOwner.get();
            if (owner == null) {
                return;
            }
            mOutlineInsetLeft = left;
            mOutlineInsetRight = right;
            mOutlineInsetTop = top;
            mOutlineInsetBottom = bottom;
            owner.invalidateOutline();
        }
    }


    @Override
    public void setShowBorderOnlyBeforeL(boolean showBorderOnlyBeforeL) {
        mIsShowBorderOnlyBeforeL = showBorderOnlyBeforeL;
        invalidate();
    }

    @Override
    public void setShadowElevation(int elevation) {
        if (mShadowElevation == elevation) {
            return;
        }
        mShadowElevation = elevation;
        invalidateOutline();
    }

    @Override
    public void setShadowAlpha(float shadowAlpha) {
        if (mShadowAlpha == shadowAlpha) {
            return;
        }
        mShadowAlpha = shadowAlpha;
        invalidateOutline();
    }

    @Override
    public void setShadowColor(int shadowColor) {
        if (mShadowColor == shadowColor) {
            return;
        }
        mShadowColor = shadowColor;
        setShadowColorInner(mShadowColor);
    }

    private void setShadowColorInner(int shadowColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            View owner = mOwner.get();
            if (owner == null) {
                return;
            }
            owner.setOutlineAmbientShadowColor(shadowColor);
            owner.setOutlineSpotShadowColor(shadowColor);
        }
    }

    private void invalidateOutline() {
        if (useFeature()) {
            View owner = mOwner.get();
            if (owner == null) {
                return;
            }
            if (mShadowElevation == 0) {
                owner.setElevation(0);
            } else {
                owner.setElevation(mShadowElevation);
            }
            //重绘轮廓
            owner.invalidateOutline();
        }
    }

    private void invalidate() {
        View owner = mOwner.get();
        if (owner == null) {
            return;
        }
        owner.invalidate();
    }

    @Override
    public void setHideRadiusSide(@HideRadiusSide int hideRadiusSide) {
        if (mHideRadiusSide == hideRadiusSide) {
            return;
        }
        setRadiusAndShadow(mRadius, hideRadiusSide, mShadowElevation, mShadowAlpha);
    }

    @Override
    public int getHideRadiusSide() {
        return mHideRadiusSide;
    }

    @Override
    public void setRadius(int radius) {
        if (mRadius != radius) {
            setRadiusAndShadow(radius, mShadowElevation, mShadowAlpha);
        }
    }

    @Override
    public void setRadius(int radius, @LayoutInf.HideRadiusSide int hideRadiusSide) {
        if (mRadius == radius && hideRadiusSide == mHideRadiusSide) {
            return;
        }
        setRadiusAndShadow(radius, hideRadiusSide, mShadowElevation, mShadowAlpha);
    }

    @Override
    public int getRadius() {
        return mRadius;
    }

    /*
    * @author lixiaojin
    * create on 2020-03-07 08:49
    * description: 设置圆角和遮罩
    */
    @Override
    public void setRadiusAndShadow(int radius, int shadowElevation, float shadowAlpha) {
        setRadiusAndShadow(radius, mHideRadiusSide, shadowElevation, shadowAlpha);
    }

    @Override
    public void setRadiusAndShadow(int radius, @LayoutInf.HideRadiusSide int hideRadiusSide, int shadowElevation, float shadowAlpha) {
        setRadiusAndShadow(radius, hideRadiusSide, shadowElevation, mShadowColor, shadowAlpha);
    }

    @Override
    public void setRadiusAndShadow(int radius, int hideRadiusSide, int shadowElevation, int shadowColor, float shadowAlpha) {
        final View owner = mOwner.get();
        if (owner == null) {
            return;
        }
        mRadius = radius;
        mHideRadiusSide = hideRadiusSide;

        if (mRadius > 0) {
            //处理圆角
            if (hideRadiusSide == HIDE_RADIUS_SIDE_TOP) {
                mRadiusArray = new float[]{0, 0, 0, 0, mRadius, mRadius, mRadius, mRadius};
            } else if (hideRadiusSide == HIDE_RADIUS_SIDE_RIGHT) {
                mRadiusArray = new float[]{mRadius, mRadius, 0, 0, 0, 0, mRadius, mRadius};
            } else if (hideRadiusSide == HIDE_RADIUS_SIDE_BOTTOM) {
                mRadiusArray = new float[]{mRadius, mRadius, mRadius, mRadius, 0, 0, 0, 0};
            } else if (hideRadiusSide == HIDE_RADIUS_SIDE_LEFT) {
                mRadiusArray = new float[]{0, 0, mRadius, mRadius, mRadius, mRadius, 0, 0};
            } else {
                mRadiusArray = null;
            }
        }
        //
        mShadowElevation = shadowElevation;
        mShadowAlpha = shadowAlpha;
        mShadowColor = shadowColor;
        if (useFeature()) {
            if (mShadowElevation == 0 || isRadiusWithSideHidden()) {
                owner.setElevation(0);
            } else {
                owner.setElevation(mShadowElevation);
            }
            setShadowColorInner(mShadowColor);
            owner.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                @TargetApi(21)
                public void getOutline(View view, Outline outline) {
                    int w = view.getWidth(), h = view.getHeight();
                    if (w == 0 || h == 0) {
                        return;
                    }
                    if (isRadiusWithSideHidden()) {
                        int left = 0, top = 0, right = w, bottom = h;
                        if (mHideRadiusSide == HIDE_RADIUS_SIDE_LEFT) {
                            left -= mRadius;
                        } else if (mHideRadiusSide == HIDE_RADIUS_SIDE_TOP) {
                            top -= mRadius;
                        } else if (mHideRadiusSide == HIDE_RADIUS_SIDE_RIGHT) {
                            right += mRadius;
                        } else if (mHideRadiusSide == HIDE_RADIUS_SIDE_BOTTOM) {
                            bottom += mRadius;
                        }
                        outline.setRoundRect(left, top, right, bottom, mRadius);
                        return;
                    }
                    int top = mOutlineInsetTop,
                            bottom = Math.max(top + 1, h - mOutlineInsetBottom),
                            left = mOutlineInsetLeft,
                            right = w - mOutlineInsetRight;
                    if (mIsOutlineExcludePadding) {
                        left += view.getPaddingLeft();
                        top += view.getPaddingTop();
                        right = Math.max(left + 1, right - view.getPaddingRight());
                        bottom = Math.max(top + 1, bottom - view.getPaddingBottom());
                    }
                    float shadowAlpha = mShadowAlpha;
                    if (mShadowElevation == 0) {
                        // outline.setAlpha will work even if shadowElevation == 0
                        shadowAlpha = 1f;
                    }
                    outline.setAlpha(shadowAlpha);
                    if (mRadius <= 0) {
                        outline.setRect(left, top, right, bottom);
                    } else {
                        outline.setRoundRect(left, top, right, bottom, mRadius);
                    }
                }
            });
            owner.setClipToOutline(mRadius > 0);
        }
        owner.invalidate();
    }

    /**
     * 有radius, 但是有一边不显示radius。
     *
     * @return
     */
    public boolean isRadiusWithSideHidden() {
        return mRadius > 0 && mHideRadiusSide != HIDE_RADIUS_SIDE_NONE;
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 13:50
    * description: 底部分割线处理，参数为左内边距、右内边距、高度和颜色
    */
    @Override
    public void updateTopDivider(int topInsetLeft, int topInsetRight, int topDividerHeight, int topDividerColor) {
        mTopDividerInsetLeft = topInsetLeft;
        mTopDividerInsetRight = topInsetRight;
        mTopDividerHeight = topDividerHeight;
        mTopDividerColor = topDividerColor;
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 13:51
    * description: 底部分割线处理，参数同上
    */
    @Override
    public void updateBottomDivider(int bottomInsetLeft, int bottomInsetRight, int bottomDividerHeight, int bottomDividerColor) {
        mBottomDividerInsetLeft = bottomInsetLeft;
        mBottomDividerInsetRight = bottomInsetRight;
        mBottomDividerColor = bottomDividerColor;
        mBottomDividerHeight = bottomDividerHeight;
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 13:51
    * description: 左侧分割线处理，参数为左上内边距、左下内边距、线宽以及颜色
    */
    @Override
    public void updateLeftDivider(int leftInsetTop, int leftInsetBottom, int leftDividerWidth, int leftDividerColor) {
        mLeftDividerInsetTop = leftInsetTop;
        mLeftDividerInsetBottom = leftInsetBottom;
        mLeftDividerWidth = leftDividerWidth;
        mLeftDividerColor = leftDividerColor;
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 13:52
    * description: 右侧分割线处理，参数为右上内边距、右下内边距、线宽以及颜色
    */
    @Override
    public void updateRightDivider(int rightInsetTop, int rightInsetBottom, int rightDividerWidth, int rightDividerColor) {
        mRightDividerInsetTop = rightInsetTop;
        mRightDividerInsetBottom = rightInsetBottom;
        mRightDividerWidth = rightDividerWidth;
        mRightDividerColor = rightDividerColor;
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 13:53
    * description: 仅显示顶部分割线，左、右和下分割线宽度设置为0
    */
    @Override
    public void onlyShowTopDivider(int topInsetLeft, int topInsetRight, int topDividerHeight, int topDividerColor) {
        updateTopDivider(topInsetLeft, topInsetRight, topDividerHeight, topDividerColor);
        mLeftDividerWidth = 0;
        mRightDividerWidth = 0;
        mBottomDividerHeight = 0;
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 14:01
    * description: 同上
    */
    @Override
    public void onlyShowBottomDivider(int bottomInsetLeft, int bottomInsetRight, int bottomDividerHeight, int bottomDividerColor) {
        updateBottomDivider(bottomInsetLeft, bottomInsetRight, bottomDividerHeight, bottomDividerColor);
        mLeftDividerWidth = 0;
        mRightDividerWidth = 0;
        mTopDividerHeight = 0;
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 14:01
    * description: 同上
    */
    @Override
    public void onlyShowLeftDivider(int leftInsetTop, int leftInsetBottom, int leftDividerWidth, int leftDividerColor) {
        updateLeftDivider(leftInsetTop, leftInsetBottom, leftDividerWidth, leftDividerColor);
        mRightDividerWidth = 0;
        mTopDividerHeight = 0;
        mBottomDividerHeight = 0;
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 14:01
    * description: 同上
    */
    @Override
    public void onlyShowRightDivider(int rightInsetTop, int rightInsetBottom, int rightDividerWidth, int rightDividerColor) {
        updateRightDivider(rightInsetTop, rightInsetBottom, rightDividerWidth, rightDividerColor);
        mLeftDividerWidth = 0;
        mTopDividerHeight = 0;
        mBottomDividerHeight = 0;
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 14:02
    * description: 设置顶部分割线透明度
    */
    @Override
    public void setTopDividerAlpha(int dividerAlpha) {
        mTopDividerAlpha = dividerAlpha;
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 14:02
    * description: 同上
    */
    @Override
    public void setBottomDividerAlpha(int dividerAlpha) {
        mBottomDividerAlpha = dividerAlpha;
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 14:03
    * description: 同上
    */
    @Override
    public void setLeftDividerAlpha(int dividerAlpha) {
        mLeftDividerAlpha = dividerAlpha;
    }

    /*
    * @author lixiaojin
    * create on 2020-03-09 14:03
    * description: 同上
    */
    @Override
    public void setRightDividerAlpha(int dividerAlpha) {
        mRightDividerAlpha = dividerAlpha;
    }


    /*
    * @author lixiaojin
    * create on 2020-03-07 08:23
    * description: 参照最小宽度，重新设定宽度
    */
    public int handleMiniWidth(int widthMeasureSpec, int measuredWidth) {
        //如果不是精确模式，则要么是未指定，要么是最大模式，将宽度调整为窗口宽度
        if (View.MeasureSpec.getMode(widthMeasureSpec) != View.MeasureSpec.EXACTLY && measuredWidth < mWidthMini) {
            return View.MeasureSpec.makeMeasureSpec(mWidthMini, View.MeasureSpec.EXACTLY);
        }
        return widthMeasureSpec;
    }

    /*
    * @author lixiaojin
    * create on 2020-03-07 08:25
    * description: 同上
    */
    public int handleMiniHeight(int heightMeasureSpec, int measuredHeight) {
        if (View.MeasureSpec.getMode(heightMeasureSpec) != View.MeasureSpec.EXACTLY && measuredHeight < mHeightMini) {
            return View.MeasureSpec.makeMeasureSpec(mHeightMini, View.MeasureSpec.EXACTLY);
        }
        return heightMeasureSpec;
    }

    /*
    * @author lixiaojin
    * create on 2020-03-06 16:54
    * description: 获取View的宽度的测量模式
    */
    public int getMeasuredWidthSpec(int widthMeasureSpec) {
        //如果有限定宽度
        if (mWidthLimit > 0) {
            //获取View的父视图设置给子View的参考尺寸
            int size = View.MeasureSpec.getSize(widthMeasureSpec);
            //如果子View所需要的尺寸大于当前窗口的尺寸，则需要根据指定的模式来确定view的尺寸，否则，不需要
            //取size和mWidthLimit中较小的那个值作为新的宽度
            if (size > mWidthLimit) {
                //获取参数所代表的模式值
                int mode = View.MeasureSpec.getMode(widthMeasureSpec);
                if (mode == View.MeasureSpec.AT_MOST) { //至多，子View不能超过父View的大小
                    widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(mWidthLimit, View.MeasureSpec.AT_MOST);
                } else {
                    //如果是未指定或者精确模式，则将宽度指定为窗口的宽度
                    widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(mWidthLimit, View.MeasureSpec.EXACTLY);
                }
            }
        }
        return widthMeasureSpec;
    }

    /*
    * @author lixiaojin
    * create on 2020-03-06 17:08
    * description: 确定view的高度，同上
    */
    public int getMeasuredHeightSpec(int heightMeasureSpec) {
        if (mHeightLimit > 0) {
            int size = View.MeasureSpec.getSize(heightMeasureSpec);
            if (size > mHeightLimit) {
                int mode = View.MeasureSpec.getMode(heightMeasureSpec);
                if (mode == View.MeasureSpec.AT_MOST) {
                    heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(mWidthLimit, View.MeasureSpec.AT_MOST);
                } else {
                    heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(mWidthLimit, View.MeasureSpec.EXACTLY);
                }
            }
        }
        return heightMeasureSpec;
    }

    /*
    * @author lixiaojin
    * create on 2020-03-06 17:08
    * description:设置边框的颜色
    */
    @Override
    public void setBorderColor(@ColorInt int borderColor) {
        mBorderColor = borderColor;
    }

    /*
    * @author lixiaojin
    * create on 2020-03-06 17:09
    * description: 设置边框的宽度
    */
    @Override
    public void setBorderWidth(int borderWidth) {
        mBorderWidth = borderWidth;
    }

//    @Override
//    public void setOuterNormalColor(int color) {
//        mOuterNormalColor = color;
//        View owner = mOwner.get();
//        if (owner != null) {
//            owner.invalidate();
//        }
//    }

    /*
    * @author lixiaojin
    * create on 2020-03-06 17:09
    * description: 如果顶部分割线高度大于0，则有顶部分割线
    */
    @Override
    public boolean hasTopSeparator() {
        return mTopDividerHeight > 0;
    }

    /*
    * @author lixiaojin
    * create on 2020-03-06 17:10
    * description: 同上
    */
    @Override
    public boolean hasRightSeparator() {
        return mRightDividerWidth > 0;
    }

    /*
    * @author lixiaojin
    * create on 2020-03-06 17:10
    * description: 同上
    */
    @Override
    public boolean hasBottomSeparator() {
        return mBottomDividerHeight > 0;
    }

    /*
    * @author lixiaojin
    * create on 2020-03-06 17:10
    * description: 同上
    */
    @Override
    public boolean hasLeftSeparator() {
        return mLeftDividerWidth > 0;
    }

    /*
    * @author lixiaojin
    * create on 2020-03-06 17:10
    * description: 同上
    */
    @Override
    public boolean hasBorder() {
        return mBorderWidth > 0;
    }

    /*
    * @author lixiaojin
    * create on 2020-03-06 17:10
    * description: 绘制边框 canvas为画布  w 和 h 分别为view 的宽度和高度
    */
    public void drawDividers(Canvas canvas, int w, int h) {
        View owner = mOwner.get();
        if(owner == null){
            return;
        }
        //如果画笔为空，并且至少有一边边界线需要绘制，则初始化画笔
        if (mDividerPaint == null && (mTopDividerHeight > 0 || mBottomDividerHeight > 0 || mLeftDividerWidth > 0 || mRightDividerWidth > 0)) {
            mDividerPaint = new Paint();
        }
        //平移以前保存画布的当前状态
        canvas.save();
        //移动画布，使画布左上角与view的左上角对齐
        canvas.translate(owner.getScrollX(), owner.getScrollY());
        //绘制顶部边界线
        if (mTopDividerHeight > 0) {
            //设置画笔的粗细、颜色、透明度
            mDividerPaint.setStrokeWidth(mTopDividerHeight);
            mDividerPaint.setColor(mTopDividerColor);
            if (mTopDividerAlpha < 255) {
                mDividerPaint.setAlpha(mTopDividerAlpha);
            }
            //画笔高度的一半设置为 线的起始点的纵坐标，左侧移除左右两侧内边距
            float y = mTopDividerHeight / 2f;
            canvas.drawLine(mTopDividerInsetLeft, y, w - mTopDividerInsetRight, y, mDividerPaint);
        }

        //绘制底部分割线，同上，
        if (mBottomDividerHeight > 0) {
            mDividerPaint.setStrokeWidth(mBottomDividerHeight);
            mDividerPaint.setColor(mBottomDividerColor);
            if (mBottomDividerAlpha < 255) {
                mDividerPaint.setAlpha(mBottomDividerAlpha);
            }
            //绘制的线的纵坐标根据view的高度以及线的粗细计算得出
            float y = (float) Math.floor(h - mBottomDividerHeight / 2f);
            canvas.drawLine(mBottomDividerInsetLeft, y, w - mBottomDividerInsetRight, y, mDividerPaint);
        }

        //同上
        if (mLeftDividerWidth > 0) {
            mDividerPaint.setStrokeWidth(mLeftDividerWidth);
            mDividerPaint.setColor(mLeftDividerColor);
            if (mLeftDividerAlpha < 255) {
                mDividerPaint.setAlpha(mLeftDividerAlpha);
            }
            float x = mLeftDividerWidth / 2f;
            canvas.drawLine(x, mLeftDividerInsetTop, x, h - mLeftDividerInsetBottom, mDividerPaint);
        }

        //同上
        if (mRightDividerWidth > 0) {
            mDividerPaint.setStrokeWidth(mRightDividerWidth);
            mDividerPaint.setColor(mRightDividerColor);
            if (mRightDividerAlpha < 255) {
                mDividerPaint.setAlpha(mRightDividerAlpha);
            }
            float x = (float) Math.floor(w - mRightDividerWidth / 2f);
            canvas.drawLine(x, mRightDividerInsetTop, x, h - mRightDividerInsetBottom, mDividerPaint);
        }
        //将画布恢复到平移前的状态
        canvas.restore();
    }

    /*
    * @author lixiaojin
    * create on 2020-03-06 17:32
    * description: 绘制圆角边框
    */
    public void dispatchRoundBorderDraw(Canvas canvas) {
        View owner = mOwner.get();
        if (owner == null) {
            return;
        }

        boolean needCheckFakeOuterNormalDraw = mRadius > 0 && !useFeature() && mOuterNormalColor != 0;
        boolean needDrawBorder = mBorderWidth > 0 && mBorderColor != 0;
        if (!needCheckFakeOuterNormalDraw && !needDrawBorder) {
            return;
        }

        if (mIsShowBorderOnlyBeforeL && useFeature() && mShadowElevation != 0) {
            return;
        }

        int width = canvas.getWidth(), height = canvas.getHeight();
        canvas.save();
        canvas.translate(owner.getScrollX(), owner.getScrollY());

        // react
        float halfBorderWith = mBorderWidth / 2f;
        if (mIsOutlineExcludePadding) {
            mBorderRect.set(
                    owner.getPaddingLeft() + halfBorderWith,
                    owner.getPaddingTop() + halfBorderWith,
                    width - owner.getPaddingRight() - halfBorderWith,
                    height - owner.getPaddingBottom() - halfBorderWith);
        } else {
            mBorderRect.set(halfBorderWith, halfBorderWith,
                    width- halfBorderWith, height - halfBorderWith);
        }

        if (needCheckFakeOuterNormalDraw) {
            int layerId = canvas.saveLayer(0, 0, width, height, null, Canvas.ALL_SAVE_FLAG);
            canvas.drawColor(mOuterNormalColor);
            mClipPaint.setColor(mOuterNormalColor);
            mClipPaint.setStyle(Paint.Style.FILL);
            mClipPaint.setXfermode(mMode);
            if (mRadiusArray == null) {
                canvas.drawRoundRect(mBorderRect, mRadius, mRadius, mClipPaint);
            } else {
                drawRoundRect(canvas, mBorderRect, mRadiusArray, mClipPaint);
            }
            mClipPaint.setXfermode(null);
            canvas.restoreToCount(layerId);
        }

        if (needDrawBorder) {
            mClipPaint.setColor(mBorderColor);
            mClipPaint.setStrokeWidth(mBorderWidth);
            mClipPaint.setStyle(Paint.Style.STROKE);
            if (mRadiusArray != null) {
                drawRoundRect(canvas, mBorderRect, mRadiusArray, mClipPaint);
            } else if (mRadius <= 0) {
                canvas.drawRect(mBorderRect, mClipPaint);
            } else {
                canvas.drawRoundRect(mBorderRect, mRadius, mRadius, mClipPaint);
            }
        }
        canvas.restore();
    }

    private void drawRoundRect(Canvas canvas, RectF rect, float[] radiusArray, Paint paint) {
        mPath.reset();
        mPath.addRoundRect(rect, radiusArray, Path.Direction.CW);
        canvas.drawPath(mPath, paint);

    }

    public static boolean useFeature() {
        return Build.VERSION.SDK_INT >= 21;
    }
}
