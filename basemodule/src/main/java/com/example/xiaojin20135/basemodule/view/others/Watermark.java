package com.example.xiaojin20135.basemodule.view.others;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
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

    /**
     * 图片缩放尺寸，默认为1，不缩放
     */
    private float mImageScale = 1f;


    /**
     * 偏移地址计算，默认是1.2
     */
    private double mOffsetParas = 1.2;

    private static Watermark sInstance;

    private Watermark() {
        mText = "";
        mTextColor = 0xDEDEDEDE;
        mTextSize = 18;
        mRotation = -25;
        mAlpha = 80;

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
     * create on 2019/9/7 14:31
     * description: 偏移地址
     */
    public Watermark setOffsetParas(double offsetParas){
        mOffsetParas = offsetParas;
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


/*
* @author lixiaojin
* create on 2019/9/9 16:52
* description: LoGo缩放处理
*/
    public Watermark setImageScale(float imageScale){
        mImageScale = imageScale;
        return sInstance;
    }


    /**
     * 显示水印，铺满整个页面
     * @param context 活动
     */
    public void show(Context context,View view) {
        show(context,view, mText);
    }

    /**
     * 显示水印，铺满整个页面
     * @param context 活动
     * @param text     水印
     */
    public void show(Context context, View view,String text) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(drawTextToBitmap(context, text,mBitmap));
        }
    }

    public BitmapDrawable drawTextToBitmap(Context gContext, String gText, Bitmap logoIcon) {
        //计算新绘制的图片的图片的宽和高
        int newWidth = 0;
        int newHight = 0;
        //计算文本的宽度
        Paint paint = new Paint();
        paint.setColor(Color.GRAY);
        if(mAlpha != 0){
            paint.setAlpha(mAlpha); //透明度
        }

        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(spToPx(mTextSize)); //文本大小

        //文本大小
        float textWidth = paint.measureText(gText);

        //图片尺寸
        if(logoIcon != null){
            //进行缩放处理
            Matrix matrix = new Matrix();
            matrix.postScale(mImageScale,mImageScale);
            logoIcon = Bitmap.createBitmap(mBitmap,0,0,mBitmap.getWidth(),mBitmap.getHeight(),matrix,true);

            int logoWidth = logoIcon.getWidth();
            int logoHeight = logoIcon.getHeight();

            newWidth = (int) Math.sqrt((logoWidth + textWidth) * (logoWidth + textWidth) + (logoHeight + textWidth) * (logoHeight + textWidth));
            newHight = newWidth;
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
                canvas.drawText(gText,logoIcon.getWidth(),newHight / 2,paint);
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
        return null;

    }

    public static int spToPx(float spValue) {
        float fontScale = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}