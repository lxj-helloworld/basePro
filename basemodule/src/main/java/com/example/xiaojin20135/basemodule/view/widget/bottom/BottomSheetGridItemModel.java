package com.example.xiaojin20135.basemodule.view.widget.bottom;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import com.example.xiaojin20135.basemodule.R;
import com.example.xiaojin20135.basemodule.layout.UILinearLayout;
import com.example.xiaojin20135.basemodule.skin.SkinHelper;
import com.example.xiaojin20135.basemodule.skin.SkinValueBuilder;
import com.example.xiaojin20135.basemodule.util.ui.ResHelper;

public class BottomSheetGridItemModel {
    private static final String TAG = "BottomSheetGridItemModel";
    Drawable image = null;
    int imageRes = 0;
    int imageSkinTintColorAttr = 0;
    int imageSkinSrcAttr = 0;
    int textSkinColorAttr = 0;
    CharSequence text;
    Object tag = "";
    Drawable subscript = null;
    int subscriptRes = 0;
    int subscriptSkinTintColorAttr = 0;
    int subscriptSkinSrcAttr = 0;
    Typeface typeface; //字体

    public BottomSheetGridItemModel(CharSequence text, Object tag) {
        this.text = text;
        this.tag = tag;
    }

    public BottomSheetGridItemModel image(Drawable image) {
        this.image = image;
        return this;
    }

    public BottomSheetGridItemModel image(int imageRes) {
        this.imageRes = imageRes;
        return this;
    }

    public BottomSheetGridItemModel subscript(Drawable image) {
        this.subscript = image;
        return this;
    }

    public BottomSheetGridItemModel subscript(int imageRes) {
        this.subscriptRes = imageRes;
        return this;
    }

    public BottomSheetGridItemModel skinTextColorAttr(int attr) {
        this.textSkinColorAttr = attr;
        return this;
    }

    public BottomSheetGridItemModel skinImageTintColorAttr(int attr) {
        this.imageSkinTintColorAttr = attr;
        return this;
    }

    public BottomSheetGridItemModel skinImageSrcAttr(int attr) {
        this.imageSkinSrcAttr = attr;
        return this;
    }

    public BottomSheetGridItemModel skinSubscriptTintColorAttr(int attr) {
        this.subscriptSkinTintColorAttr = attr;
        return this;
    }

    public BottomSheetGridItemModel skinSubscriptSrcAttr(int attr) {
        this.subscriptSkinSrcAttr = attr;
        return this;
    }

    public BottomSheetGridItemModel typeface(Typeface typeface) {
        this.typeface = typeface;
        return this;
    }

    public CharSequence getText() {
        return text;
    }

    public Drawable getImage() {
        return image;
    }

    public Drawable getSubscript() {
        return subscript;
    }

    public int getImageRes() {
        return imageRes;
    }

    public int getImageSkinSrcAttr() {
        return imageSkinSrcAttr;
    }

    public int getImageSkinTintColorAttr() {
        return imageSkinTintColorAttr;
    }

    public int getSubscriptRes() {
        return subscriptRes;
    }

    public int getSubscriptSkinSrcAttr() {
        return subscriptSkinSrcAttr;
    }

    public int getSubscriptSkinTintColorAttr() {
        return subscriptSkinTintColorAttr;
    }

    public int getTextSkinColorAttr() {
        return textSkinColorAttr;
    }

    public Object getTag() {
        return tag;
    }

    public Typeface getTypeface() {
        return typeface;
    }

    /*
    * @author lixiaojin
    * create on 2020-04-09 11:37
    * description: 根布局
    */
    public static class BottomSheetRootLayout extends UILinearLayout {
        private static final String TAG = "BottomSheetRootLayout";
        private final int mUsePercentMinHeight;
        private final float mHeightPercent;
        private final int mMaxWidth;

        public BottomSheetRootLayout(Context context) {
            this(context, null);
        }

        public BottomSheetRootLayout(Context context, AttributeSet attrs) {
            super(context, attrs);
            setOrientation(VERTICAL);
            setBackground(ResHelper.getAttrDrawable(context, R.attr.skin_support_bottom_sheet_bg));
            SkinValueBuilder builder = SkinValueBuilder.acquire();
            builder.background(R.attr.skin_support_bottom_sheet_bg); //设置背景颜色
            SkinHelper.setSkinValue(this, builder);
            builder.release();
            int radius = ResHelper.getAttrDimen(context, R.attr.bottom_sheet_radius);
            if (radius > 0) {
                setRadius(radius, HIDE_RADIUS_SIDE_BOTTOM);
            }
            mUsePercentMinHeight = ResHelper.getAttrDimen(context, R.attr.bottom_sheet_use_percent_min_height);
            mHeightPercent = ResHelper.getAttrFloatValue(context, R.attr.bottom_sheet_height_percent);
            mMaxWidth = ResHelper.getAttrDimen(context, R.attr.bottom_sheet_max_width);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int widthSize = MeasureSpec.getSize(widthMeasureSpec);
            int widthMode = MeasureSpec.getMode(widthMeasureSpec);
            if(widthSize > mMaxWidth){
                widthMeasureSpec = MeasureSpec.makeMeasureSpec(mMaxWidth, widthMode);
            }
            int heightSize = MeasureSpec.getSize(heightMeasureSpec);
            if (heightSize >= mUsePercentMinHeight) {
                heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) (heightSize * mHeightPercent), MeasureSpec.AT_MOST);
            }
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}

