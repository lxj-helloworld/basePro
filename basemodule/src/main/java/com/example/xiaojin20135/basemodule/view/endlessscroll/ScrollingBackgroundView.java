package com.example.xiaojin20135.basemodule.view.endlessscroll;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.xiaojin20135.basemodule.R;

public class ScrollingBackgroundView extends View {

    private static final String TAG = "Watermark";
    /**
     * Logo
     */
//    private Bitmap mBitmap;



    /**
     * 水印文本
     */
    private String mText = "";



    /**
     * 字体颜色，十六进制形式，例如：0xAEAEAEAE
     */
    private int mTextColor = 0xAEAEAEAE;
    /**
     * 字体大小，单位为sp
     */
    private float mTextSize = 18;
    /**
     * 旋转角度
     */
    private float mRotation = -25;

    /**
     * 图片缩放尺寸，默认为1，不缩放
     */
    private float mImageScale = 1f;


    /**
     * 偏移地址计算，默认是1.2
     */
    private double mOffsetParas = 1.2;



    public ScrollingBackgroundView(Context context) {
        super(context);
        mText = "";
        mTextColor = 0xDEDEDEDE;
        mTextSize = 18;
        mRotation = -25;
    }

    public ScrollingBackgroundView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollingBackgroundView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Simple listener to be notified when this view size has changed
     */
    public interface OnSizeChangedListener {
        /**
         * Will be triggered after {@link android.view.View#onSizeChanged(int, int, int, int)} is called
         * @param width new width of the view
         * @param height new height of the view
         */
        void onSizeChanged(int width, int height);
    }


    private Drawable mDrawable;

    private int mScrollX;
    private int mScrollY;

    private OnSizeChangedListener mOnSizeChangedListener;

    private void init(Context context, AttributeSet attributeSet) {

        if (attributeSet != null) {
            final TypedArray array = context.obtainStyledAttributes(attributeSet, R.styleable.ScrollingBackgroundView);
            try {

                mScrollX = array.getDimensionPixelSize(R.styleable.ScrollingBackgroundView_sbv_scrollX, 0);
                mScrollY = array.getDimensionPixelSize(R.styleable.ScrollingBackgroundView_sbv_scrollY, 0);

                final Drawable drawable = array.getDrawable(R.styleable.ScrollingBackgroundView_sbv_drawable);
                setDrawable(drawable);

            } finally {
                array.recycle();
            }
        }
    }

    /**
     * Set the drawable object manually. There is also an XML attribute `sbv_drawable`
     * If provided via XML the intrinsic bounds will be used.
     * The drawable will be <i>tiled</i> to fill this view
     * @param drawable to be tiled and drawn as background
     */
    public void setDrawable(Drawable drawable) {
        mDrawable = drawable;
        if (mDrawable != null) {
            final Rect rect = mDrawable.getBounds();
            if (rect == null
                    || rect.isEmpty()) {
                mDrawable.setBounds(0, 0, mDrawable.getIntrinsicWidth(), mDrawable.getIntrinsicHeight());
            }
            setWillNotDraw(false);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            postInvalidateOnAnimation();
        }
    }

    /**
     * @return current {@link #mDrawable}
     */
    public Drawable getDrawable() {
        return mDrawable;
    }

    /**
     *
     * @param listener to be notified when the size changes, or NULL to stop listening
     * @see OnSizeChangedListener
     */
    public void setOnSizeChangedListener(OnSizeChangedListener listener) {
        this.mOnSizeChangedListener = listener;
    }

    @Override
    public void scrollBy(int x, int y) {
        if (y != 0 || x != 0) {
            mScrollX += x;
            mScrollY += y;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                postInvalidateOnAnimation();
            }
        }
    }

    @Override
    public void scrollTo(int x, int y) {
        if (mScrollY != y
                || mScrollX != x) {
            mScrollX = x;
            mScrollY = y;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                postInvalidateOnAnimation();
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if (mOnSizeChangedListener != null) {
            mOnSizeChangedListener.onSizeChanged(w, h);
        }
    }

    /**
     * Getter for current {@link #mScrollX}
     * @return current {@link #mScrollX}
     */
    public int scrollX() {
        return mScrollX;
    }

    /**
     * Getter for current {@link #mScrollY}
     * @return current {@link #mScrollY}
     */
    public int scrollY() {
        return mScrollY;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // to draw possible background, etc
        super.onDraw(canvas);

        final Drawable drawable = mDrawable;
        if (drawable == null) {
            return;
        }

        final int scrollX = mScrollX;
        final int scrollY = mScrollY;

        final int width = canvas.getWidth();
        final int height = canvas.getHeight();

        final Rect rect = drawable.getBounds();

        final int drawableWidth = rect.width();
        final int drawableHeight = rect.height();

        final int startX = start(scrollX, drawableWidth);
        final int iterationsX = iterations(width, startX, drawableWidth);

        final int startY = start(scrollY, drawableHeight);
        final int iterationsY = iterations(height, startY, drawableHeight);

        final int save = canvas.save();
        try {

            canvas.translate(startX, startY);

            for (int x = 0; x < iterationsX; x++) {
                for (int y = 0; y < iterationsY; y++) {
                    drawable.draw(canvas);
                    canvas.translate(.0F, drawableHeight);
                }
                canvas.translate(drawableWidth, -(drawableHeight * iterationsY));
            }

        } finally {
            canvas.restoreToCount(save);
        }
    }

    private static int start(int scroll, int side) {

        final int start;

        final int modulo = Math.abs(scroll) % side;
        if (modulo == 0) {
            start = 0;
        } else if (scroll < 0) {
            start = -(side - modulo);
        } else {
            start = -modulo;
        }

        return start;
    }

    private static int iterations(int total, int start, int side) {
        final int diff = total - start;
        final int base = diff / side;
        return base + (diff % side > 0 ? 1 : 0);
    }

    @Override
    public void setAlpha(float alpha) {
        super.setAlpha(alpha);
    }

    @Override
    public float getAlpha() {
        return super.getAlpha();
    }

    public BitmapDrawable drawTextToBitmap(Context gContext, String gText, Bitmap logoIcon) {
        //计算新绘制的图片的图片的宽和高
        int newWidth = 0;
        int newHight = 0;
        //计算文本的宽度
        Paint paint = new Paint();
        paint.setColor(Color.GRAY);

        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(spToPx(mTextSize)); //文本大小

        //文本大小
        float textWidth = paint.measureText(gText);
        //计算文本高度
        float textHeight = 0;
        Rect rect = new Rect();
        paint.getTextBounds(mText,0,mText.length(),rect);
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        textHeight = fontMetrics.bottom - fontMetrics.top + fontMetrics.leading;
        Log.d(TAG,"textHeight = " + textHeight);

        int logoWidth = 0;
        int logoHeight = 0;

        //图片尺寸
        if(logoIcon != null){
            //进行缩放处理
            Matrix matrix = new Matrix();
            matrix.postScale(mImageScale,mImageScale);
            logoIcon = Bitmap.createBitmap(logoIcon,0,0,logoIcon.getWidth(),logoIcon.getHeight(),matrix,true);

            logoWidth = logoIcon.getWidth();
            logoHeight = logoIcon.getHeight();

            Log.d(TAG,"logoWidth = " + logoWidth);
            Log.d(TAG,"logoHeight = " + logoHeight);

            newWidth = (int) Math.sqrt((logoWidth + textWidth) * (logoWidth + textWidth) + (logoHeight + textWidth) * (logoHeight + textWidth));
            newHight = newWidth;

            Log.d(TAG,"newHight = " + newHight);
        }else{
            newWidth = (int) Math.sqrt(( textWidth * 2) * (textWidth * 2) + (textWidth) * (textWidth));
            newHight = newWidth;
        }

        newWidth = (int)(newWidth * mOffsetParas);
        newHight = (int)(newHight * mOffsetParas);
        try {
            Bitmap bitmap = Bitmap.createBitmap(newWidth, newHight, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            canvas.drawColor(Color.WHITE);
            canvas.rotate(mRotation);
            if(logoIcon != null){
                Log.d(TAG,"开始绘制图像");
                canvas.drawBitmap(logoIcon,0,newHight / 4,paint);
//                paint.setTextSize(mTextSize); //文本大小
                canvas.drawText(gText,logoIcon.getWidth(),newHight / 4 + logoHeight / 2 + 20,paint);
            }else{
//                paint.setTextSize(mTextSize); //文本大小
                canvas.drawText(gText,0,0,paint);
            }
            canvas.save();
            canvas.restore();
            BitmapDrawable drawable = new BitmapDrawable(gContext.getResources(),bitmap);
            drawable.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
            drawable.setDither(true);
            return drawable;
        } catch (Exception e) {

        }
        gContext = null;
        return null;
    }

    public static int spToPx(float spValue) {
        float fontScale = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

//    public ScrollingBackgroundView setBitmap(Bitmap bitmap){
//        mBitmap = bitmap;
//        return this;
//    }

    public ScrollingBackgroundView setText(String text){
        mText = text;
        return this;
    }

    public ScrollingBackgroundView setTextColor(int textColor){
        mTextColor = textColor;
        return this;
    }

    public ScrollingBackgroundView setTextSize(int textSize){
        mTextSize = textSize;
        return this;
    }

    public ScrollingBackgroundView setRotation(int rotation){
        mRotation = rotation;
        return this;
    }

    public ScrollingBackgroundView setImageScale(int imageScale){
        mImageScale = imageScale;
        return this;
    }

    public ScrollingBackgroundView setOffsetParas(int offsetParas){
        mOffsetParas = offsetParas;
        return this;
    }
}
