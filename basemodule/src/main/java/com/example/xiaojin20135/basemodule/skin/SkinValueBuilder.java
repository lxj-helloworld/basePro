package com.example.xiaojin20135.basemodule.skin;

import android.content.Context;
import android.view.View;

import com.example.xiaojin20135.basemodule.R;

import java.util.HashMap;
import java.util.LinkedList;

/*
* @author lixiaojin
* create on 2020-03-04 11:16
* description: 样式基础构建类
*/
public class SkinValueBuilder {
    public static final String BACKGROUND = "background";
    public static final String TEXT_COLOR = "textColor";
    public static final String HINT_COLOR = "hintColor";
    public static final String SECOND_TEXT_COLOR = "secondTextColor";
    public static final String SRC = "src";
    public static final String BORDER = "border";
    public static final String TOP_SEPARATOR = "topSeparator";
    public static final String BOTTOM_SEPARATOR = "bottomSeparator";
    public static final String RIGHT_SEPARATOR = "rightSeparator";
    public static final String LEFT_SEPARATOR = "LeftSeparator";
    public static final String ALPHA = "alpha";
    public static final String TINT_COLOR = "tintColor";
    public static final String BG_TINT_COLOR = "bgTintColor";
    public static final String PROGRESS_COLOR = "progressColor";
    public static final String TEXT_COMPOUND_TINT_COLOR = "tclTintColor";
    public static final String TEXT_COMPOUND_LEFT_SRC = "tclTintColor";
    public static final String TEXT_COMPOUND_RIGHT_SRC = "tcrTintColor";
    public static final String TEXT_COMPOUND_TOP_SRC = "tctTintColor";
    public static final String TEXT_COMPOUND_BOTTOM_SRC = "tcbTintColor";
    public static final String UNDERLINE = "underline";
    public static final String MORE_TEXT_COLOR = "moreTextColor";
    public static final String MORE_BG_COLOR = "moreBgColor";
    private static LinkedList<SkinValueBuilder> sValueBuilders;

    public static SkinValueBuilder acquire(){
        if(sValueBuilders == null){
            return new SkinValueBuilder();
        }
        SkinValueBuilder valueBuilder = sValueBuilders.poll();
        if(valueBuilder != null){
            return valueBuilder;
        }
        return new SkinValueBuilder();
    }

    private HashMap<String,String> mValues = new HashMap<>();

    /*
    * @author lixiaojin
    * create on 2020-03-04 14:10
    * description: 设置边框颜色
    */
    public SkinValueBuilder border(int attr){
        mValues.put(BORDER,String.valueOf(attr));
        return this;
    }

    public SkinValueBuilder border(String attrName){
        mValues.put(BORDER,attrName);
        return this;
    }

    /*
    * @author lixiaojin
    * create on 2020-03-04 14:10
    * description: 设置背景颜色
    */
    public SkinValueBuilder background(int attr){
        mValues.put(BACKGROUND,String.valueOf(attr));
        return this;
    }

    public SkinValueBuilder background(String attrName){
        mValues.put(BACKGROUND,attrName);
        return this;
    }





    public String build(){
        StringBuilder builder = new StringBuilder();
        boolean isFirstItem = true;
        for(String name : mValues.keySet()){
            String itemValue = mValues.get(name);
            if(itemValue == null || itemValue.isEmpty()){
                continue;
            }
            if(!isFirstItem){
                builder.append("|");
            }
            builder.append(name);
            builder.append(":");
            builder.append(itemValue);
            isFirstItem = false;
        }
        return builder.toString();
    }


    public SkinValueBuilder clear(){
        mValues.clear();
        return this;
    }


    /*
    * @author lixiaojin
    * create on 2020-03-04 14:44
    * description: 释放资源
    */
    public static void release(SkinValueBuilder valueBuilder){
        valueBuilder.clear();
        if(sValueBuilders == null){
            sValueBuilders = new LinkedList<>();
        }
        if(sValueBuilders.size() < 2){
            sValueBuilders.push(valueBuilder);
        }

    }


    public void release(){
        SkinValueBuilder.release(this);
    }

}
