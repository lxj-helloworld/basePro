package com.example.xiaojin20135.basemodule.view.widget.popup;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.AnimRes;
import android.support.annotation.IntDef;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.xiaojin20135.basemodule.R;
import com.example.xiaojin20135.basemodule.layout.LayoutHelper;
import com.example.xiaojin20135.basemodule.layout.UIFrameLayout;
import com.example.xiaojin20135.basemodule.skin.SkinHelper;
import com.example.xiaojin20135.basemodule.skin.SkinValueBuilder;
import com.example.xiaojin20135.basemodule.util.LogUtils;
import com.example.xiaojin20135.basemodule.util.ui.ResHelper;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


public class NormalPopup <T extends BasePopup> extends BasePopup<T> {
    private static final String TAG = "NormalPopup";

    public static final int ANIM_AUTO = 0;
    public static final int ANIM_GROW_FROM_LEFT = 1;
    public static final int ANIM_GROW_FROM_RIGHT = 2;
    public static final int ANIM_GROW_FROM_CENTER = 3;
    public static final int ANIM_SPEC = 4;

    //代替枚举注解
    @IntDef(value = {ANIM_AUTO, ANIM_GROW_FROM_LEFT, ANIM_GROW_FROM_RIGHT, ANIM_GROW_FROM_CENTER, ANIM_SPEC})
    //表明注解类型
    @interface AnimStyle {
    }

    public static final int DIRECTION_TOP = 0;
    public static final int DIRECTION_BOTTOM = 1;
    public static final int DIRECTION_CENTER_IN_SCREEN = 2;


    @IntDef({DIRECTION_CENTER_IN_SCREEN, DIRECTION_TOP, DIRECTION_BOTTOM})
    @Retention(RetentionPolicy.SOURCE) //指定当前注解的保留策略， SOURCE为只保留源码中，编译时删除
    public @interface Direction {
    }

    protected @AnimStyle int mAnimStyle;
    protected int mSpecAnimStyle;
    private int mEdgeProtectionTop;
    private int mEdgeProtectionLeft;
    private int mEdgeProtectionRight;
    private int mEdgeProtectionBottom;
    private boolean mShowArrow = true;
    private boolean mAddShadow = false;
    private int mRadius = NOT_SET;
    private int mBorderColor = NOT_SET;
    private int mBorderUsedColor = Color.TRANSPARENT;
    private int mBorderColorAttr = R.attr.skin_support_popup_border_color;
    private int mBorderWidth = NOT_SET;
    private int mShadowElevation = NOT_SET;
    private float mShadowAlpha = 0f;
    private int mShadowInset = NOT_SET;
    private int mBgColor = NOT_SET;
    private int mBgUsedColor = Color.TRANSPARENT;
    private int mBgColorAttr = R.attr.skin_support_popup_bg;
    private int mOffsetX = 0;
    private int mOffsetYIfTop = 0;
    private int mOffsetYIfBottom = 0;
    private @Direction int mPreferredDirection = DIRECTION_BOTTOM;
    protected final int mInitWidth;
    protected final int mInitHeight;
    private int mArrowWidth = NOT_SET;
    private int mArrowHeight = NOT_SET;
    private boolean mRemoveBorderWhenShadow = false;
    private View mContentView;

    public NormalPopup(Context context, int width, int height) {
        super(context);
        mInitWidth = width;
        mInitHeight = height;
    }

    //是否显示箭头
    public T arrow(boolean showArrow) {
        mShowArrow = showArrow;
        return (T) this;
    }

    //设定箭头大小
    public T arrowSize(int width, int height) {
        mArrowWidth = width;
        mArrowHeight = height;
        return (T) this;
    }

    //是否显示遮罩层
    public T shadow(boolean addShadow) {
        mAddShadow = addShadow;
        return (T) this;
    }

    //当显示遮罩时是否移除边框
    public T removeBorderWhenShadow(boolean removeBorderWhenShadow) {
        mRemoveBorderWhenShadow = removeBorderWhenShadow;
        return (T) this;
    }

    //动画样式
    public T animStyle(@AnimStyle int animStyle) {
        mAnimStyle = animStyle;
        return (T) this;
    }

    //自定义动画样式
    public T customAnimStyle(@AnimRes int animStyle) {
        mAnimStyle = ANIM_SPEC;
        mSpecAnimStyle = animStyle;
        return (T) this;
    }

    //弹框圆角半径
    public T radius(int radius) {
        mRadius = radius;
        return (T) this;
    }

    //设置遮罩透明度
    public T shadowElevation(int shadowElevation, float shadowAlpha) {
        mShadowAlpha = shadowAlpha;
        mShadowElevation = shadowElevation;
        return (T) this;
    }

    //设置遮罩内边距
    public T shadowInset(int shadowInset) {
        mShadowInset = shadowInset;
        return (T) this;
    }

    //边缘
    public T edgeProtection(int distance) {
        mEdgeProtectionLeft = distance;
        mEdgeProtectionRight = distance;
        mEdgeProtectionTop = distance;
        mEdgeProtectionBottom = distance;
        return (T) this;
    }


    public T edgeProtection(int left, int top, int right, int bottom) {
        mEdgeProtectionLeft = left;
        mEdgeProtectionRight = top;
        mEdgeProtectionTop = right;
        mEdgeProtectionBottom = bottom;
        return (T) this;
    }


    public T offsetX(int offsetX) {
        mOffsetX = offsetX;
        return (T) this;
    }

    public T offsetYIfTop(int y) {
        mOffsetYIfTop = y;
        return (T) this;
    }

    public T offsetYIfBottom(int y) {
        mOffsetYIfBottom = y;
        return (T) this;
    }

    //方向
    public T preferredDirection(@Direction int preferredDirection) {
        mPreferredDirection = preferredDirection;
        return (T) this;
    }

    public T view(View contentView) {
        mContentView = contentView;
        return (T) this;
    }

    public T view(@LayoutRes int contentViewResId) {
        return view(LayoutInflater.from(mContext).inflate(contentViewResId, null));
    }

    public T borderWidth(int borderWidth) {
        mBorderWidth = borderWidth;
        return (T) this;
    }

    public T borderColor(int borderColor) {
        mBorderColor = borderColor;
        return (T) this;
    }

    public int getBgColor() {
        return mBgColor;
    }

    public int getBgColorAttr() {
        return mBgColorAttr;
    }

    public int getBorderColor() {
        return mBorderColor;
    }

    public int getBorderColorAttr() {
        return mBorderColorAttr;
    }

    public T bgColor(int bgColor) {
        mBgColor = bgColor;
        return (T) this;
    }

    public T borderColorAttr(int borderColorAttr) {
        mBorderColorAttr = borderColorAttr;
        return (T) this;
    }

    public T bgColorAttr(int bgColorAttr) {
        mBgColorAttr = bgColorAttr;
        return (T) this;
    }

    class ShowInfo {
        private int[] anchorRootLocation = new int[2];
        private int[] anchorLocation = new int[2];
        Rect visibleWindowFrame = new Rect();
        int width;
        int height;
        int x;
        int y;
        View anchor;
        int anchorCenter;
        int direction = mPreferredDirection;
        int contentWidthMeasureSpec;
        int contentHeightMeasureSpec;
        int decorationLeft = 0;
        int decorationRight = 0;
        int decorationTop = 0;
        int decorationBottom = 0;

        ShowInfo(View anchor) {
            this.anchor = anchor;
            // for muti window
            anchor.getRootView().getLocationOnScreen(anchorRootLocation);
            anchor.getLocationOnScreen(anchorLocation);
            //计算被附着视图横向中间点
            anchorCenter = anchorLocation[0] + anchor.getWidth() / 2;
            anchor.getWindowVisibleDisplayFrame(visibleWindowFrame);
        }

        float anchorProportion() {
            return (anchorCenter - x) / (float) width;
        }

        int windowWidth() {
            return decorationLeft + width + decorationRight;
        }

        int windowHeight() {
            return decorationTop + height + decorationBottom;
        }

        int getVisibleWidth() {
            return visibleWindowFrame.width();
        }

        int getVisibleHeight() {
            return visibleWindowFrame.height();
        }

        int getWindowX() {
            return x - anchorRootLocation[0];
        }

        int getWindowY() {
            return y - anchorRootLocation[1];
        }
    }

    private boolean shouldShowShadow() {
        return mAddShadow && LayoutHelper.useFeature();
    }

    public T show(@NonNull View anchor) {
        if (mContentView == null) {
            throw new RuntimeException("you should call view() to set your content view");
        }
        ShowInfo showInfo = new ShowInfo(anchor);
        calculateWindowSize(showInfo);
        calculateXY(showInfo);
        adjustShowInfo(showInfo);
        decorateContentView(showInfo);
//        setAnimationStyle(showInfo.anchorProportion(), showInfo.direction);
        mWindow.setWidth(showInfo.windowWidth());
        mWindow.setHeight(showInfo.windowHeight());
        showAtLocation(anchor, showInfo.getWindowX(), showInfo.getWindowY());
        return (T) this;
    }


    private void decorateContentView(ShowInfo showInfo) {
        ContentView contentView = ContentView.wrap(mContentView, mInitWidth, mInitHeight);
        SkinValueBuilder builder = SkinValueBuilder.acquire();
        if (mBorderColor != NOT_SET) {
            mBorderUsedColor = mBorderColor;
        } else if (mBorderColorAttr != 0) {
            mBorderUsedColor = ResHelper.getAttrColor(mContext, R.color.background);
            builder.border(mBorderColorAttr);
        }
        if (mBgColor != NOT_SET) {
            mBgUsedColor = mBgColor;
        } else if (mBgColorAttr != 0) {
            mBgUsedColor = ResHelper.getAttrColor(mContext, mBgColorAttr);
            builder.background(mBgColorAttr);
        }

        if (mBorderWidth == NOT_SET) {
            mBorderWidth = ResHelper.getAttrDimen(mContext, R.attr.popup_border_width);
        }

        SkinHelper.setSkinValue(contentView, builder);
        builder.release();
        contentView.setBackgroundColor(mBgUsedColor);
        contentView.setBorderColor(mBorderUsedColor);
        contentView.setBorderWidth(mBorderWidth);
        contentView.setShowBorderOnlyBeforeL(mRemoveBorderWhenShadow);
        if (mRadius == NOT_SET) {
            mRadius = ResHelper.getAttrDimen(mContext, R.attr.popup_radius);
        }


        if (shouldShowShadow()) {
            contentView.setRadiusAndShadow(mRadius, mShadowElevation, mShadowAlpha);
        } else {
            contentView.setRadius(mRadius);
        }

        DecorRootView decorRootView = new DecorRootView(mContext, showInfo);
        decorRootView.setContentView(contentView);
        mWindow.setContentView(decorRootView);
        Log.e(TAG,"mBgUsedColor = " + mBgUsedColor);
        Log.e(TAG,"mBorderUsedColor = " + mBorderUsedColor);
        Log.e(TAG,"mBorderWidth = " + mBorderWidth);
    }

    private void adjustShowInfo(ShowInfo showInfo) {
        if (shouldShowShadow()) {
            if (mShadowElevation == NOT_SET) {
                mShadowElevation = ResHelper.getAttrDimen(mContext, R.attr.popup_shadow_elevation);
                mShadowAlpha = ResHelper.getAttrFloatValue(mContext, R.attr.popup_shadow_alpha);
            }
            if (mShadowInset == NOT_SET) {
                mShadowInset = ResHelper.getAttrDimen(mContext, R.attr.popup_shadow_inset);
            }

            int originX = showInfo.x, originY = showInfo.y;
            if (originX - mShadowInset > showInfo.visibleWindowFrame.left) {
                showInfo.x -= mShadowInset;
                showInfo.decorationLeft = mShadowInset;
            } else {
                showInfo.decorationLeft = originX - showInfo.visibleWindowFrame.left;
                showInfo.x = showInfo.visibleWindowFrame.left;
            }
            if (originX + showInfo.width + mShadowInset < showInfo.visibleWindowFrame.right) {
                showInfo.decorationRight = mShadowInset;
            } else {
                showInfo.decorationRight = showInfo.visibleWindowFrame.right - originX - showInfo.width;
            }
            if (originY - mShadowInset > showInfo.visibleWindowFrame.top) {
                showInfo.y -= mShadowInset;
                showInfo.decorationTop = mShadowInset;
            } else {
                showInfo.decorationTop = originY - showInfo.visibleWindowFrame.top;
                showInfo.y = showInfo.visibleWindowFrame.top;
            }
            if (originY + showInfo.height + mShadowInset < showInfo.visibleWindowFrame.bottom) {
                showInfo.decorationBottom = mShadowInset;
            } else {
                showInfo.decorationBottom = showInfo.visibleWindowFrame.bottom - originY - showInfo.height;
            }
        }

        if (mShowArrow && showInfo.direction != DIRECTION_CENTER_IN_SCREEN) {
            if (mArrowWidth == NOT_SET) {
                mArrowWidth = ResHelper.getAttrDimen(mContext, R.attr.popup_arrow_width);
            }
            if (mArrowHeight == NOT_SET) {
                mArrowHeight = ResHelper.getAttrDimen(mContext, R.attr.popup_arrow_height);
            }
            if (showInfo.direction == DIRECTION_BOTTOM) {
                if (shouldShowShadow()) {
                    showInfo.y += mArrowHeight;
                }
                showInfo.decorationTop = Math.max(showInfo.decorationTop, mArrowHeight);
            } else if (showInfo.direction == DIRECTION_TOP) {
                showInfo.decorationBottom = Math.max(showInfo.decorationBottom, mArrowHeight);
                showInfo.y -= mArrowHeight;
            }
        }
    }

    /*
    * @author lixiaojin
    * create on 2020-03-05 18:02
    * description: 计算弹窗左上角坐标
    */
    private void calculateXY(ShowInfo showInfo) {
        if (showInfo.anchorCenter < showInfo.visibleWindowFrame.left + showInfo.getVisibleWidth() / 2) { // anchor point on the left
            showInfo.x = Math.max(mEdgeProtectionLeft + showInfo.visibleWindowFrame.left, showInfo.anchorCenter - showInfo.width / 2 + mOffsetX);
        } else { // anchor point on the left
            showInfo.x = Math.min(
                    showInfo.visibleWindowFrame.right - mEdgeProtectionRight - showInfo.width,
                    showInfo.anchorCenter - showInfo.width / 2 + mOffsetX);
        }
        int nextDirection = DIRECTION_CENTER_IN_SCREEN;
        if (mPreferredDirection == DIRECTION_BOTTOM) {
            nextDirection = DIRECTION_TOP;
        } else if (mPreferredDirection == DIRECTION_TOP) {
            nextDirection = DIRECTION_BOTTOM;
        }
        handleDirection(showInfo, mPreferredDirection, nextDirection);
    }

    private void handleDirection(ShowInfo showInfo, int currentDirection, int nextDirection) {
        if (currentDirection == DIRECTION_CENTER_IN_SCREEN) {
            showInfo.x = showInfo.visibleWindowFrame.left + (showInfo.getVisibleWidth() - showInfo.width) / 2;
            showInfo.y = showInfo.visibleWindowFrame.top + (showInfo.getVisibleHeight() - showInfo.height) / 2;
            showInfo.direction = DIRECTION_CENTER_IN_SCREEN;
        } else if (currentDirection == DIRECTION_TOP) {
            showInfo.y = showInfo.anchorLocation[1] - showInfo.height - mOffsetYIfTop;
            if (showInfo.y < mEdgeProtectionTop + showInfo.visibleWindowFrame.top) {
                handleDirection(showInfo, nextDirection, DIRECTION_CENTER_IN_SCREEN);
            } else {
                showInfo.direction = DIRECTION_TOP;
            }
        } else if (currentDirection == DIRECTION_BOTTOM) {
            showInfo.y = showInfo.anchorLocation[1] + showInfo.anchor.getHeight() + mOffsetYIfBottom;
            if (showInfo.y > showInfo.visibleWindowFrame.bottom - mEdgeProtectionBottom - showInfo.height) {
                handleDirection(showInfo, nextDirection, DIRECTION_CENTER_IN_SCREEN);
            } else {
                showInfo.direction = DIRECTION_BOTTOM;
            }
        }
    }

    protected int proxyWidth(int width) {
        return width;
    }

    protected int proxyHeight(int height) {
        return height;
    }

    private void calculateWindowSize(ShowInfo showInfo) {
        boolean needMeasureForWidth = false, needMeasureForHeight = false;
        if (mInitWidth > 0) {
            showInfo.width = proxyWidth(mInitWidth);
            showInfo.contentWidthMeasureSpec = View.MeasureSpec.makeMeasureSpec(
                    showInfo.width, View.MeasureSpec.EXACTLY);
        } else {
            int maxWidth = showInfo.getVisibleWidth() - mEdgeProtectionLeft - mEdgeProtectionRight;
            if (mInitWidth == ViewGroup.LayoutParams.MATCH_PARENT) {
                showInfo.width = proxyWidth(maxWidth);
                showInfo.contentWidthMeasureSpec = View.MeasureSpec.makeMeasureSpec(
                        showInfo.width, View.MeasureSpec.EXACTLY);
            } else {
                needMeasureForWidth = true;
                showInfo.contentWidthMeasureSpec = View.MeasureSpec.makeMeasureSpec(
                        proxyWidth(maxWidth), View.MeasureSpec.AT_MOST);
            }
        }
        if (mInitHeight > 0) {
            showInfo.height = proxyHeight(mInitHeight);
            showInfo.contentHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(
                    showInfo.height, View.MeasureSpec.EXACTLY);
        } else {
            int maxHeight = showInfo.getVisibleHeight() - mEdgeProtectionTop - mEdgeProtectionBottom;
            if (mInitHeight == ViewGroup.LayoutParams.MATCH_PARENT) {
                showInfo.height = proxyHeight(maxHeight);
                showInfo.contentHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(
                        showInfo.height, View.MeasureSpec.EXACTLY);
            } else {
                needMeasureForHeight = true;
                showInfo.contentHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(
                        proxyHeight(maxHeight), View.MeasureSpec.AT_MOST);
            }
        }

        if (needMeasureForWidth || needMeasureForHeight) {
            mContentView.measure(showInfo.contentWidthMeasureSpec, showInfo.contentHeightMeasureSpec);
            if (needMeasureForWidth) {
                showInfo.width = proxyWidth(mContentView.getMeasuredWidth());
            }
            if (needMeasureForHeight) {
                showInfo.height = proxyHeight(mContentView.getMeasuredHeight());
            }
        }
    }

    private void setAnimationStyle(float anchorProportion, @Direction int direction) {
        boolean onTop = direction == DIRECTION_TOP;
        switch (mAnimStyle) {
            case ANIM_GROW_FROM_LEFT:
                mWindow.setAnimationStyle(onTop ? R.style.AnimationPopUpMenu_Left : R.style.AnimationPopDownMenu_Left);
                break;

            case ANIM_GROW_FROM_RIGHT:
                mWindow.setAnimationStyle(onTop ? R.style.AnimationPopUpMenu_Right : R.style.AnimationPopDownMenu_Right);
                break;

            case ANIM_GROW_FROM_CENTER:
                mWindow.setAnimationStyle(onTop ? R.style.AnimationPopUpMenu_Center : R.style.AnimationPopDownMenu_Center);
                break;
            case ANIM_AUTO:
                if (anchorProportion <= 0.25f) {
                    mWindow.setAnimationStyle(onTop ? R.style.AnimationPopUpMenu_Left : R.style.AnimationPopDownMenu_Left);
                } else if (anchorProportion > 0.25f && anchorProportion < 0.75f) {
                    mWindow.setAnimationStyle(onTop ? R.style.AnimationPopUpMenu_Center : R.style.AnimationPopDownMenu_Center);
                } else {
                    mWindow.setAnimationStyle(onTop ? R.style.AnimationPopUpMenu_Right : R.style.AnimationPopDownMenu_Right);
                }
                break;
            case ANIM_SPEC:
                mWindow.setAnimationStyle(mSpecAnimStyle);
                break;
        }
    }

    static class ContentView extends UIFrameLayout {
        private ContentView(Context context) {
            super(context);
        }

        static ContentView wrap(View businessView, int width, int height) {
            ContentView contentView = new ContentView(businessView.getContext());
            if (businessView.getParent() != null) {
                ((ViewGroup) businessView.getParent()).removeView(businessView);
            }
            contentView.addView(businessView, new FrameLayout.LayoutParams(width, height));
            return contentView;
        }
    }

    class DecorRootView extends FrameLayout {
        private ShowInfo mShowInfo;
        private View mContentView;
        private Paint mArrowPaint;
        private Path mArrowPath;

        private int mPendingWidth;
        private int mPendingHeight;
        private Runnable mUpdateWindowAction = new Runnable() {
            @Override
            public void run() {
                mShowInfo.width = mPendingWidth;
                mShowInfo.height = mPendingHeight;
                calculateXY(mShowInfo);
                adjustShowInfo(mShowInfo);
                mWindow.update(mShowInfo.getWindowX(), mShowInfo.getWindowY(), mShowInfo.windowWidth(), mShowInfo.windowHeight());
            }
        };

        private DecorRootView(Context context, ShowInfo showInfo) {
            super(context);
            mShowInfo = showInfo;
            mArrowPaint = new Paint();
            mArrowPaint.setAntiAlias(true);
            mArrowPath = new Path();
        }


        public void setContentView(View contentView) {
            if (mContentView != null) {
                removeView(mContentView);
            }
            if (contentView.getParent() != null) {
                ((ViewGroup) contentView.getParent()).removeView(contentView);
            }
            mContentView = contentView;
            addView(contentView);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            removeCallbacks(mUpdateWindowAction);
            if (mContentView != null) {
                mContentView.measure(mShowInfo.contentWidthMeasureSpec, mShowInfo.contentHeightMeasureSpec);
                int measuredWidth = mContentView.getMeasuredWidth();
                int measuredHeight = mContentView.getMeasuredHeight();
                if (mShowInfo.width != measuredWidth || mShowInfo.height != measuredHeight) {
                    mPendingWidth = measuredWidth;
                    mPendingHeight = measuredHeight;
                    post(mUpdateWindowAction);
                }
            }
            setMeasuredDimension(mShowInfo.windowWidth(), mShowInfo.windowHeight());
        }

        @Override
        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            if (mContentView != null) {
                mContentView.layout(mShowInfo.decorationLeft, mShowInfo.decorationTop,
                        mShowInfo.width + mShowInfo.decorationLeft,
                        mShowInfo.height + mShowInfo.decorationTop);
            }
        }

        @Override
        protected void onAttachedToWindow() {
            super.onAttachedToWindow();
            removeCallbacks(mUpdateWindowAction);
        }


        @Override
        protected void dispatchDraw(Canvas canvas) {
            super.dispatchDraw(canvas);

            if (mShowArrow) {
                if (mShowInfo.direction == DIRECTION_TOP) {
                    canvas.save();
                    mArrowPaint.setStyle(Paint.Style.FILL);
                    mArrowPaint.setColor(mBgUsedColor);
                    int l = mShowInfo.anchorCenter - mShowInfo.x - mArrowWidth / 2;
                    l = Math.min(Math.max(l, mShowInfo.decorationLeft),
                            getWidth() - mShowInfo.decorationRight - mArrowWidth);
                    int t = mShowInfo.decorationTop + mShowInfo.height - mBorderWidth - 1;
                    canvas.translate(l, t);
                    mArrowPath.reset();
                    mArrowPath.setLastPoint(0, 0);
                    mArrowPath.lineTo(mArrowWidth / 2, mArrowHeight);
                    mArrowPath.lineTo(mArrowWidth, 0);
                    mArrowPath.close();
                    canvas.drawPath(mArrowPath, mArrowPaint);
                    if (!mRemoveBorderWhenShadow || !shouldShowShadow()) {
                        mArrowPaint.setStrokeWidth(mBorderWidth);
                        mArrowPaint.setColor(mBorderUsedColor);
                        mArrowPaint.setStyle(Paint.Style.STROKE);
                        canvas.drawLine(0, 0, mArrowWidth / 2, mArrowHeight, mArrowPaint);
                        canvas.drawLine(mArrowWidth / 2, mArrowHeight, mArrowWidth, 0, mArrowPaint);
                    }
                    canvas.restore();
                } else if (mShowInfo.direction == DIRECTION_BOTTOM) {
                    canvas.save();
                    mArrowPaint.setStyle(Paint.Style.FILL);
                    mArrowPaint.setColor(mBgUsedColor);
                    int l = mShowInfo.anchorCenter - mShowInfo.x - mArrowWidth / 2;
                    l = Math.min(Math.max(l, mShowInfo.decorationLeft),
                            getWidth() - mShowInfo.decorationRight - mArrowWidth);
                    int t = mShowInfo.decorationTop + mBorderWidth + 1;
                    canvas.translate(l, t);
                    mArrowPath.reset();
                    mArrowPath.setLastPoint(0, 0);
                    mArrowPath.lineTo(mArrowWidth / 2, -mArrowHeight);
                    mArrowPath.lineTo(mArrowWidth, 0);
                    mArrowPath.close();
                    canvas.drawPath(mArrowPath, mArrowPaint);
                    if (!mRemoveBorderWhenShadow || !shouldShowShadow()) {
                        mArrowPaint.setStrokeWidth(mBorderWidth);
                        mArrowPaint.setStyle(Paint.Style.STROKE);
                        mArrowPaint.setColor(mBorderUsedColor);
                        canvas.drawLine(0, 0, mArrowWidth / 2, -mArrowHeight, mArrowPaint);
                        canvas.drawLine(mArrowWidth / 2, -mArrowHeight, mArrowWidth, 0, mArrowPaint);
                    }
                    canvas.restore();
                }
            }
        }
    }
}
