package com.example.xiaojin20135.basemodule.view.widget.popup;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.xiaojin20135.basemodule.R;
import com.example.xiaojin20135.basemodule.skin.SkinHelper;
import com.example.xiaojin20135.basemodule.skin.SkinValueBuilder;
import com.example.xiaojin20135.basemodule.util.LogUtils;
import com.example.xiaojin20135.basemodule.util.ui.ResHelper;


public class NormalPopup <T extends BasePopup> extends BasePopup<T>{
    private static final String TAG = "NormalPopup";

    public static final int DIRECTION_TOP = 0;
    public static final int DIRECTION_BOTTOM = 1;
    public static final int DIRECTION_CENTER_IN_SCREEN = 2;

    private int mInitWidth; //初始宽度
    private int mInitHeight; //初始高度

    private View mContentView;

    private int mPreferredDirection = DIRECTION_TOP;

    private int mEdgeProtectionTop;
    private int mEdgeProtectionLeft;
    private int mEdgeProtectionRight;
    private int mEdgeProtectionBottom;

    private int mOffsetX = 10;
    private int mOffsetYIfTop = 10;
    private int mOffsetYIfBottom = 10;



    private int mArrowWidth = NOT_SET;
    private int mArrowHeight = NOT_SET;

    //浮窗是否显示指示箭头
    private boolean mShowArrow = true;
    private int mBorderWidth = NOT_SET;
    private int mBorderColor = NOT_SET;

    //是否显示遮罩
    private boolean mAddShadow = true;
    //圆角大小
    private int mRadius;

    //遮罩权重相关
    private int mShadowElevation;
    private float mShadowAlpha; //透明度

    //遮罩内边距
    private int mShadowInset;

    //边框相关
    private int mBorderUsedColor = Color.TRANSPARENT;
    private int mBorderColorAttr = R.attr.skin_support_popup_border_color;

    //背景相关
    private int mBgColor = NOT_SET;
    private int mBgUsedColor = Color.TRANSPARENT;
    private int mBgColorAttr = R.attr.skin_support_popup_bg;


    public NormalPopup(Context context,int width,int height) {
        super(context);
        mInitWidth = width;
        mInitHeight = height;
    }

    //浮窗箭头
    public T arrow(boolean showArrow){
        mShowArrow = showArrow;
        return (T) this;
    }

    //箭头大小
    public T arrowSize(int width, int height){
        mArrowWidth = width;
        mArrowHeight = height;
        return (T) this;
    }

    //遮罩
    public T shadow(boolean addShadow){
        mAddShadow = addShadow;
        return (T) this;
    }

    //圆角
    public T radius(int radius){
        mRadius = radius;
        return (T) this;
    }

    //遮罩权重
    public T shadowElevation(int shadowElevation, float shadowAlapha){
        mShadowElevation = shadowElevation;
        mShadowAlpha = shadowAlapha;
        return (T) this;
    }

    //遮罩内边距
    public T shadowInset(int shadowInset){
        mShadowInset = shadowInset;
        return (T) this;
    }

    //边缘
    public T edgeProtection(int distance){
        mEdgeProtectionLeft = distance;
        mEdgeProtectionRight = distance;
        mEdgeProtectionTop = distance;
        mEdgeProtectionBottom = distance;
        return (T) this;
    }

    //左边距
    public T offsetX(int offsetX){
        mOffsetX = offsetX;
        return (T) this;
    }

    //顶部边距
    public T offsetYIfTop(int y){
        mOffsetYIfTop = y;
        return (T) this;
    }

    //底部边距
    public T offsetYIfBottom(int y){
        mOffsetYIfBottom = y;
        return (T) this;
    }

    /*
    * @author lixiaojin
    * create on 2020-03-04 10:59
    * description: 设置方向
    */
    public T preferDirection(int preferredDirection){
        mPreferredDirection = preferredDirection;
        return (T) this;
    }

    /*
     * @author lixiaojin
     * create on 2020-03-04 08:31
     * description: 在popup 窗口中展示的view 参数为视图对象
     */
    public T view(View contentView){
        mContentView = contentView;
        return (T)this;
    }

    /*
     * @author lixiaojin
     * create on 2020-03-04 08:37
     * description: 在popup窗口中展示的view， 参数为视图资源id
     */
    public T view(int contentViewResId){
        return view(LayoutInflater.from(mContext).inflate(contentViewResId,null));
    }

    /*
    * @author lixiaojin
    * create on 2020-03-04 11:01
    * description: 边框宽度
    */
    public T borderWidth(int borderWidth){
        mBorderWidth = borderWidth;
        return (T) this;
    }

    /*
    * @author lixiaojin
    * create on 2020-03-04 11:02
    * description: 边框颜色
    */
    public T borderColor(int borderColor){
        mBorderColor = borderColor;
        return (T) this;
    }

    /*
    * @author lixiaojin
    * create on 2020-03-04 08:55
    * description:
    */
    class ShowInfo{
        View anchor; //悬浮窗停靠展示的视图
        private int[] anchorRootLocation = new int[2];
        private int[] anchorLocation = new int[2];
        int anchorCenter;
        Rect visibleWindowFrame = new Rect();
        int x;
        int width;

        int decorationLeft;
        int decorationRight;
        int decorationTop;
        int decorationBottom;

        int height;
        int y;

        int direction = mPreferredDirection;
        int contentWidthMeasureSpec;
        int contentHeightMeasureSpec;

        ShowInfo(View anchor){
            this.anchor = anchor;
            //返回View左上角的坐标
            anchor.getRootView().getLocationOnScreen(anchorRootLocation);
            anchor.getLocationOnScreen(anchorLocation);
            //计算被停靠View水平中心位置
            anchorCenter = anchorLocation[0] + anchor.getWidth() / 2;
            anchor.getWindowVisibleDisplayFrame(visibleWindowFrame);
        }

        float anchorProportion(){
            return (anchorCenter - x) / (float)width;
        }

        int windowWidth(){
            return decorationLeft + width + decorationRight;
        }

        int windowHeight(){
            return decorationTop + height + decorationBottom;
        }

        int getVisibleWidth(){
            return visibleWindowFrame.width();
        }

        int getVisibleHeight(){
            return visibleWindowFrame.height();
        }

        int getWindowX(){
            return x - anchorRootLocation[0];
        }

        int getWindowY(){
            return y - anchorRootLocation[1];
        }
    }


    /*
    * @author lixiaojin
    * create on 2020-03-04 08:30
    * description: 显示悬浮框
    *   anchor为悬浮窗停靠的视图
    */
    public T show(View anchor){
        if(mContentView == null){
            throw new RuntimeException("you should call view() to set your content view");
        }
        ShowInfo showInfo = new ShowInfo(anchor);
        calculateWindowSize(showInfo);
        calculateXY(showInfo);
        adjustShowInfo(showInfo);
        decorateContentView(showInfo);
//        setAnimationStyle(showInfo.anchorProportion(),showInfo.direction);
        mWindow.setWidth(showInfo.windowWidth());
        mWindow.setHeight(showInfo.windowHeight());
        showAtLocation(anchor,showInfo.getWindowX(),showInfo.getWindowY());
        LogUtils.d(TAG,"showInfo.width = " + showInfo.width + "  showInfo.height = " + showInfo.height + "  showInfo.getWindowX() = " + showInfo.getWindowX() + " showInfo.getWindowY() = " + showInfo.getWindowY());
        return (T) this;
    }

    /*
    * @author lixiaojin
    * create on 2020-03-03 16:46
    * description: 计算视图的宽度和高度
    */
    private void calculateWindowSize(ShowInfo showInfo){
        boolean needMeasureForWidth = false,needMeasureForHeight = false;
        //绘制宽度
        if(mInitWidth > 0){
            //如果传入的宽度是具体值，
            showInfo.width = mInitWidth;
            showInfo.contentWidthMeasureSpec = View.MeasureSpec.makeMeasureSpec(showInfo.width,View.MeasureSpec.EXACTLY);
        }else{//如果收到的宽度是要参照父元素
            int maxWidth = showInfo.getVisibleWidth() - mEdgeProtectionLeft - mEdgeProtectionRight;
            //宽度与父元素宽度相同
            if(mInitWidth == ViewGroup.LayoutParams.MATCH_PARENT){
                showInfo.width = maxWidth;
                //测量模式为精准模式
                showInfo.contentWidthMeasureSpec = View.MeasureSpec.makeMeasureSpec(showInfo.width,View.MeasureSpec.EXACTLY);
            }else{
                //尚未有确切宽度，需要后续计算
                needMeasureForWidth = true;
                showInfo.contentWidthMeasureSpec = View.MeasureSpec.makeMeasureSpec(showInfo.width,View.MeasureSpec.AT_MOST);
            }
        }
        //绘制高度
        if(mInitHeight > 0){
            //高度为指定宽度，为具体值，
            showInfo.height = mInitHeight;
            showInfo.contentHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(showInfo.height,View.MeasureSpec.EXACTLY);
        }else{
            //高度为父元素的宽度
            int maxHeight = showInfo.getVisibleHeight() - mEdgeProtectionTop - mEdgeProtectionBottom;
            if(mInitHeight == ViewGroup.LayoutParams.MATCH_PARENT){
                showInfo.height = maxHeight;
                showInfo.contentHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(showInfo.height,View.MeasureSpec.EXACTLY);
            }else{
                //尚未有确切高度，需要后续计算
                needMeasureForHeight = true;
                showInfo.contentHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(maxHeight,View.MeasureSpec.AT_MOST);
            }
        }
        //计算宽度、高度
        if(needMeasureForWidth || needMeasureForHeight){
            mContentView.measure(showInfo.contentWidthMeasureSpec,showInfo.contentHeightMeasureSpec);
            if(needMeasureForWidth){
                showInfo.width = mContentView.getMeasuredWidth();
            }
            if(needMeasureForHeight){
                showInfo.height = mContentView.getMeasuredHeight();
            }
        }
        LogUtils.d(TAG,"mContentView : " + mContentView.getWidth() + "   " + mContentView.getHeight());
    }

    /*
    * @author lixiaojin
    * create on 2020-03-04 08:54
    * description:
    */
    private void calculateXY(ShowInfo showInfo){
        LogUtils.d(TAG,"");
        //如果被停靠视图的中心位置 在 可见视图中心点 的左侧
        if(showInfo.anchorCenter < showInfo.visibleWindowFrame.left + showInfo.getVisibleWidth() / 2){
            showInfo.x = Math.max(mEdgeProtectionLeft + showInfo.visibleWindowFrame.left, showInfo.anchorCenter - showInfo.width / 2 + mOffsetX);
        }else{
            showInfo.x = Math.min(showInfo.visibleWindowFrame.right - mEdgeProtectionRight - showInfo.width,showInfo.anchorCenter - showInfo.width / 2 + mOffsetX);
        }
        int nextDirection = DIRECTION_CENTER_IN_SCREEN;
        if(mPreferredDirection == DIRECTION_BOTTOM){
            nextDirection = DIRECTION_TOP;
        }else{
            nextDirection = DIRECTION_BOTTOM;
        }
        handleDirection(showInfo,mPreferredDirection,nextDirection);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-04 10:02
    * description:
    */
    private void handleDirection(ShowInfo showInfo,int currentDirection,int nextDirection){
        if(currentDirection == DIRECTION_CENTER_IN_SCREEN){
            showInfo.x = showInfo.visibleWindowFrame.left + (showInfo.getVisibleWidth() - showInfo.width) / 2;
            showInfo.y = showInfo.visibleWindowFrame.top + (showInfo.getVisibleHeight() - showInfo.height) / 2;
        }else if(currentDirection == DIRECTION_TOP){
            showInfo.y = showInfo.anchorLocation[0] - showInfo.height - mOffsetYIfTop;
            if(showInfo.y < mEdgeProtectionTop + showInfo.visibleWindowFrame.top){
                handleDirection(showInfo,nextDirection,DIRECTION_CENTER_IN_SCREEN);
            }else{
                showInfo.direction = DIRECTION_TOP;
            }
        }else if(currentDirection == DIRECTION_BOTTOM){
            showInfo.y = showInfo.anchorLocation[1] + showInfo.anchor.getHeight() + mOffsetYIfBottom;
            if(showInfo.y > showInfo.visibleWindowFrame.bottom - mEdgeProtectionBottom - showInfo.height){
                handleDirection(showInfo,nextDirection,DIRECTION_CENTER_IN_SCREEN);
            }else{
                showInfo.direction = DIRECTION_BOTTOM;
            }
        }
    }

    private void adjustShowInfo(ShowInfo showInfo){
        if(showInfo.direction == DIRECTION_BOTTOM){
            showInfo.decorationTop = Math.max(showInfo.decorationTop,mArrowHeight);
        }else if(showInfo.direction == DIRECTION_TOP){
            showInfo.decorationBottom = Math.max(showInfo.decorationBottom,mArrowHeight);
            showInfo.y = showInfo.y - mArrowHeight;
        }
        LogUtils.d(TAG,"showInfo.decorationTop  = " + showInfo.decorationTop );
    }

    private void decorateContentView(ShowInfo showInfo){
        ContentView contentView = ContentView.wrap(mContentView,mInitWidth,mInitHeight);

        SkinValueBuilder builder = SkinValueBuilder.acquire();

        if(mBorderColor != NOT_SET){
            mBorderUsedColor = mBorderColor;
        }else if(mBorderColorAttr != 0){
            mBorderUsedColor = ResHelper.getAttrColor(mContext,mBorderColorAttr);
            builder.border(mBorderColorAttr);
        }

        if(mBgColor != NOT_SET){
            mBgUsedColor = mBgColor;
        }else if(mBgColorAttr != 0){
            mBgUsedColor = ResHelper.getAttrColor(mContext,mBgColorAttr);
            builder.background(mBgColorAttr);
        }

        if(mBorderWidth == NOT_SET){
            mBorderWidth = ResHelper.getAttrDimen(mContext,R.attr.popup_border_width);
        }

        SkinHelper.setSkinValue(contentView,builder);
        builder.release();

        contentView.setBackgroundColor(Color.LTGRAY);

        //设置圆角
        if(mRadius == NOT_SET){
            mRadius = ResHelper.getAttrDimen(mContext,R.attr.popup_radius);
        }
        //设置遮罩
//        if(mAddShadow){
//            contentView.setRa
//        }




        DecorRootView decorRootView = new DecorRootView(mContext, showInfo);
        decorRootView.setContentView(contentView);
        mWindow.setContentView(decorRootView);
    }






    static class ContentView extends FrameLayout {
        private ContentView(Context context){
            super(context);
        }
        static ContentView wrap(View businessView,int width, int height){
            ContentView contentView = new ContentView(businessView.getContext());
            if(businessView.getParent() != null){
                ((ViewGroup)businessView.getParent()).removeView(businessView);
            }
            contentView.addView(businessView,new LayoutParams(width,height));
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
                mWindow.update(mShowInfo.getWindowX(),mShowInfo.getWindowY(),mShowInfo.windowWidth(),mShowInfo.windowHeight());
            }
        };


        private DecorRootView(Context context,ShowInfo showInfo){
            super(context);
            mShowInfo = showInfo;
            mArrowPaint = new Paint();
            mArrowPaint.setAntiAlias(true);
            mArrowPath = new Path();
        }


        public void setContentView(View contentView){
            if(mContentView != null){
                removeView(mContentView);
            }
            if(contentView.getParent() != null){
                ((ViewGroup)contentView.getParent()).removeView(contentView);
            }
            mContentView = contentView;
            addView(contentView);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
           removeCallbacks(mUpdateWindowAction);
           if(mContentView != null){
               mContentView.measure(mShowInfo.contentWidthMeasureSpec,mShowInfo.contentHeightMeasureSpec);
               int measuredWidth = mContentView.getMeasuredWidth();
               int measureHeight = mContentView.getMeasuredHeight();
               if(mShowInfo.width != measuredWidth || mShowInfo.height != measureHeight){
                   mPendingWidth = measuredWidth;
                   mPendingHeight = measureHeight;
                   post(mUpdateWindowAction);
               }
           }
            setMeasuredDimension(mShowInfo.windowWidth(),mShowInfo.windowHeight());
        }

        @Override
        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            if(mContentView != null){
                mContentView.layout(mShowInfo.decorationLeft,mShowInfo.decorationTop,mShowInfo.width + mShowInfo.decorationLeft,mShowInfo.height + mShowInfo.decorationTop);
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
            if(mShowArrow){
                if(mShowInfo.direction == DIRECTION_TOP){
                    canvas.save();
                    mArrowPaint.setStyle(Paint.Style.FILL);
                    mArrowPaint.setColor(mBgUsedColor);
                    int l = mShowInfo.anchorCenter - mShowInfo.x - mArrowWidth / 2;
                    l = Math.min(Math.max(l,mShowInfo.decorationLeft),getWidth() - mShowInfo.decorationRight - mArrowWidth);
                    int t = mShowInfo.decorationTop + mShowInfo.height - mBorderWidth - 1;
                    canvas.translate(l,t);
                    mArrowPath.reset();
                    mArrowPath.lineTo(mArrowWidth / 2, mArrowHeight);
                    mArrowPath.lineTo(mArrowWidth,0);
                    mArrowPath.close();
                    canvas.drawPath(mArrowPath,mArrowPaint);
                    canvas.restore();
                }else if(mShowInfo.direction == DIRECTION_BOTTOM){
                    canvas.save();
                    mArrowPaint.setStyle(Paint.Style.FILL);
                    mArrowPaint.setColor(mBgUsedColor);
                    int l = mShowInfo.anchorCenter - mShowInfo.x - mArrowWidth / 2;
                    l = Math.min(Math.max(l,mShowInfo.decorationLeft),getWidth() - mShowInfo.decorationRight - mArrowWidth);
                    int t = mShowInfo.decorationTop + mBorderWidth + 1;
                    canvas.translate(l,t);

                    mArrowPath.reset();
                    mArrowPath.setLastPoint(0,0);
                    mArrowPath.lineTo(mArrowWidth / 2, -mArrowHeight);
                    mArrowPath.lineTo(mArrowWidth,0);
                    mArrowPath.close();
                    canvas.drawPath(mArrowPath,mArrowPaint);

                    canvas.restore();

                }
            }
        }
    }



}
