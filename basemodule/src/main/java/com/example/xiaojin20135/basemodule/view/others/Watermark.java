package com.example.xiaojin20135.basemodule.view.others;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/*
 * @author lixiaojin
 * create on 2019/9/6 20:42
 * description: 页面水印
 */
public class Watermark {
    private static final String TAG = "Watermark";
    /**
     * Logo
     */
    private Bitmap mBitmap;
    /**
     * 水印文本
     */
    private String mText;
    /**
     * 字体颜色，十六进制形式，例如：0xAEAEAEAE
     */
    private int mTextColor;
    /**
     * 字体大小，单位为sp
     */
    private float mTextSize;
    /**
     * 旋转角度
     */
    private float mRotation;
    /**
     * 透明度  范围 0到255
     */
    private int mAlpha;
    private static Watermark sInstance;

    private Watermark() {
        mText = "";
        mTextColor = 0xDEDEDEDE;
        mTextSize = 23;
        mRotation = -25;
    }

    public static Watermark getInstance() {
        if (sInstance == null) {
            synchronized (Watermark.class) {
                sInstance = new Watermark();
            }
        }
        return sInstance;
    }

    /*
     * @author lixiaojin
     * create on 2019/9/6 21:03
     * description: 设置水印LoGo
     */
    public Watermark setLogo(Bitmap bitmap){
        mBitmap = bitmap;
        return sInstance;
    }

    /**
     * 设置水印文本
     *
     * @param text 文本
     * @return Watermark实例
     */
    public Watermark setText(String text) {
        mText = text;
        return sInstance;
    }

    /**
     * 设置字体颜色
     *
     * @param color 颜色，十六进制形式，例如：0xAEAEAEAE
     * @return Watermark实例
     */
    public Watermark setTextColor(int color) {
        mTextColor = color;
        return sInstance;
    }

    /**
     * 设置字体大小
     *
     * @param size 大小，单位为sp
     * @return Watermark实例
     */
    public Watermark setTextSize(float size) {
        mTextSize = size;
        return sInstance;
    }

    /**
     * 设置旋转角度
     * @param degrees 度数
     * @return Watermark实例
     */
    public Watermark setRotation(float degrees) {
        mRotation = degrees;
        return sInstance;
    }

    /*
     * @author lixiaojin
     * create on 2019/9/7 08:36
     * description: 设置透明度
     */
    public Watermark setAlpha(int alpha){
        mAlpha = alpha;
        return sInstance;
    }

    /**
     * 显示水印，铺满整个页面
     * @param activity 活动
     */
    public void show(Activity activity) {
        show(activity, mText);
    }

    /**
     * 显示水印，铺满整个页面
     * @param activity 活动
     * @param text     水印
     */
    public void show(Activity activity, String text) {
        WatermarkDrawable drawable = new WatermarkDrawable();
        drawable.mText = text;
        drawable.mTextColor = mTextColor;
        drawable.mTextSize = mTextSize;
        drawable.mRotation = mRotation;
        if(mAlpha != 0){
            drawable.setAlpha(mAlpha);
        }
        ViewGroup rootView = activity.findViewById(android.R.id.content);
        FrameLayout layout = new FrameLayout(activity);
        layout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            layout.setBackground(drawable);
        }
        rootView.addView(layout);
    }

    private class WatermarkDrawable extends Drawable {
        private Paint mPaint;
        /**
         * 水印文本
         */
        private String mText;
        /**
         * 字体颜色，十六进制形式，例如：0xAEAEAEAE
         */
        private int mTextColor;
        /**
         * 字体大小，单位为sp
         */
        private float mTextSize;
        /**
         * 旋转角度
         */
        private float mRotation;

        private WatermarkDrawable() {
            mPaint = new Paint();
        }

        @Override
        public void draw(@NonNull Canvas canvas) {
            int width = getBounds().right;
            int height = getBounds().bottom;
            int diagonal = (int) Math.sqrt(width * width + height * height); // 对角线的长度
            mPaint.setColor(mTextColor);
            mPaint.setTextSize(spToPx(mTextSize)); // ConvertUtils.spToPx()这个方法是将sp转换成px，ConvertUtils这个工具类在我提供的demo里面有
            mPaint.setAntiAlias(true);
            mPaint.setAlpha(200);
            mPaint.setStyle(Paint.Style.FILL);
            float textWidth = mPaint.measureText(mText);
//            canvas.drawColor(0xBB000000);
            canvas.rotate(mRotation);
            int index = 0;
            float fromX;
            float offsetWidth = 0;//横向坐标偏移量
            // 以对角线的长度来做高度，这样可以保证竖屏和横屏整个屏幕都能布满水印
            for (int positionY = 0; positionY <= diagonal; positionY += diagonal / 3) {
                if(mBitmap != null){
                    fromX = -width + (index++ % 2) * (textWidth + mBitmap.getWidth()); // 上下两行的X轴起始点不一样，错开显示
                    offsetWidth = textWidth + mBitmap.getWidth();
                }else{
                    fromX = -width + (index++ % 2) * textWidth; // 上下两行的X轴起始点不一样，错开显示
                    offsetWidth = textWidth;
                }
                for (float positionX = fromX; positionX < width; positionX += offsetWidth * 3) {
                    if(mBitmap != null){
                        mBitmap.setHasAlpha(true);
                        canvas.drawBitmap(mBitmap,positionX ,positionY,mPaint);
                        if(!TextUtils.isEmpty(mText)){
                            canvas.drawText(mText, positionX + mBitmap.getWidth(), positionY + mBitmap.getHeight()/2 + 20, mPaint);
                        }
                    }else{
                        if(!TextUtils.isEmpty(mText)){
                            canvas.drawText(mText, positionX, positionY, mPaint);
                        }
                    }
                }
            }
            canvas.save();
            canvas.restore();
        }

        @Override
        public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {
            mPaint.setAlpha(alpha);
        }

        @Override
        public void setColorFilter(@Nullable ColorFilter colorFilter) {
            mPaint.setColorFilter(colorFilter);
        }

        @Override
        public int getOpacity() {
            return PixelFormat.TRANSLUCENT;
        }
    }
    public static int spToPx(float spValue) {
        float fontScale = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}