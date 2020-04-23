package com.example.xiaojin20135.basemodule.view.widget.bottom;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

import com.example.xiaojin20135.basemodule.R;


/*
* @author lixiaojin
* create on 2020-03-28 10:12
* description: 列表类型Item 数据模型
*/
public class BottomSheetListItemModel {
    Drawable image = null;
    int imageRes = 0;
    int imageSkinTintColorAttr = 0;
    int imageSkinSrcAttr = 0;
    int textSkinColorAttr = R.color.colorPrimary;
    CharSequence text;
    String tag = "";
    boolean hasRedPoint = false;
    boolean isDisabled = false;
    Typeface typeface;

    public BottomSheetListItemModel(CharSequence text, String tag) {
        this.text = text;
        this.tag = tag;
    }

    public BottomSheetListItemModel image(Drawable image) {
        this.image = image;
        return this;
    }

    public BottomSheetListItemModel image(int imageRes) {
        this.imageRes = imageRes;
        return this;
    }

    public BottomSheetListItemModel skinTextColorAttr(int attr) {
        this.textSkinColorAttr = attr;
        return this;
    }

    public BottomSheetListItemModel skinImageTintColorAttr(int attr) {
        this.imageSkinTintColorAttr = attr;
        return this;
    }

    public BottomSheetListItemModel skinImageSrcAttr(int attr) {
        this.imageSkinSrcAttr = attr;
        return this;
    }

    public BottomSheetListItemModel redPoint(boolean hasRedPoint) {
        this.hasRedPoint = hasRedPoint;
        return this;
    }

    public BottomSheetListItemModel disabled(boolean isDisabled) {
        this.isDisabled = isDisabled;
        return this;
    }

    public BottomSheetListItemModel typeface(Typeface typeface){
        this.typeface = typeface;
        return this;
    }
}

