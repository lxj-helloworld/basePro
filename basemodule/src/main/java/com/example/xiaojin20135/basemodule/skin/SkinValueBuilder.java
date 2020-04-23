package com.example.xiaojin20135.basemodule.skin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.example.xiaojin20135.basemodule.R;

import java.util.HashMap;
import java.util.LinkedList;

/*
* @author lixiaojin
* create on 2020-03-04 11:16
* description: 建造者模式
* 基础样式构建器
*/
public class SkinValueBuilder {
    public static final String BACKGROUND = "background"; //背景
    public static final String TEXT_COLOR = "textColor"; //文本颜色
    public static final String HINT_COLOR = "hintColor"; //提示文本颜色
    public static final String SECOND_TEXT_COLOR = "secondTextColor"; //次要文本颜色
    public static final String SRC = "src"; //源
    public static final String BORDER = "border"; //边框
    public static final String TOP_SEPARATOR = "topSeparator"; //上边框
    public static final String BOTTOM_SEPARATOR = "bottomSeparator"; //下边框
    public static final String RIGHT_SEPARATOR = "rightSeparator"; //右边框
    public static final String LEFT_SEPARATOR = "LeftSeparator"; //左边框
    public static final String ALPHA = "alpha"; //透明度
    public static final String TINT_COLOR = "tintColor"; // 浅色
    public static final String BG_TINT_COLOR = "bgTintColor"; //背景浅色
    public static final String PROGRESS_COLOR = "progressColor"; //进度条颜色
    public static final String TEXT_COMPOUND_TINT_COLOR = "tclTintColor";
    public static final String TEXT_COMPOUND_LEFT_SRC = "tclTintColor";
    public static final String TEXT_COMPOUND_RIGHT_SRC = "tcrTintColor";
    public static final String TEXT_COMPOUND_TOP_SRC = "tctTintColor";
    public static final String TEXT_COMPOUND_BOTTOM_SRC = "tcbTintColor";
    public static final String UNDERLINE = "underline"; //下划线
    public static final String MORE_TEXT_COLOR = "moreTextColor"; //更多文本颜色
    public static final String MORE_BG_COLOR = "moreBgColor"; //更多背景颜色
    private static LinkedList<SkinValueBuilder> sValueBuilders;

    public static SkinValueBuilder acquire() {
        if (sValueBuilders == null) {
            return new SkinValueBuilder();
        }
        SkinValueBuilder valueBuilder = sValueBuilders.poll();
        if (valueBuilder != null) {
            return valueBuilder;
        }
        return new SkinValueBuilder();
    }

    public static void release(@NonNull SkinValueBuilder valueBuilder) {
        valueBuilder.clear();
        if (sValueBuilders == null) {
            sValueBuilders = new LinkedList<>();
        }
        if (sValueBuilders.size() < 2) {
            sValueBuilders.push(valueBuilder);
        }
    }

    private SkinValueBuilder() {

    }

    private HashMap<String, String> mValues = new HashMap<>();

    /*
    * @author lixiaojin
    * create on 2020-04-09 14:29
    * description:
    */
    public SkinValueBuilder background(int attr) {
        mValues.put(BACKGROUND, String.valueOf(attr));
        return this;
    }

    public SkinValueBuilder background(String attrName) {
        mValues.put(BACKGROUND, attrName);
        return this;
    }

    public SkinValueBuilder underline(int attr) {
        mValues.put(UNDERLINE, String.valueOf(attr));
        return this;
    }

    public SkinValueBuilder underline(String attrName) {
        mValues.put(UNDERLINE, attrName);
        return this;
    }

    public SkinValueBuilder moreTextColor(int attr) {
        mValues.put(MORE_TEXT_COLOR, String.valueOf(attr));
        return this;
    }

    public SkinValueBuilder moreTextColor(String attrName) {
        mValues.put(MORE_TEXT_COLOR, attrName);
        return this;
    }

    public SkinValueBuilder moreBgColor(int attr) {
        mValues.put(MORE_BG_COLOR, String.valueOf(attr));
        return this;
    }

    public SkinValueBuilder moreBgColor(String attrName) {
        mValues.put(MORE_BG_COLOR, attrName);
        return this;
    }

    public SkinValueBuilder textCompoundTintColor(int attr) {
        mValues.put(TEXT_COMPOUND_TINT_COLOR, String.valueOf(attr));
        return this;
    }

    public SkinValueBuilder textCompoundTintColor(String attrName) {
        mValues.put(TEXT_COMPOUND_TINT_COLOR, attrName);
        return this;
    }

    public SkinValueBuilder textCompoundTopSrc(int attr) {
        mValues.put(TEXT_COMPOUND_TOP_SRC, String.valueOf(attr));
        return this;
    }

    public SkinValueBuilder textCompoundTopSrc(String attrName) {
        mValues.put(TEXT_COMPOUND_TOP_SRC, attrName);
        return this;
    }

    public SkinValueBuilder textCompoundRightSrc(int attr) {
        mValues.put(TEXT_COMPOUND_RIGHT_SRC, String.valueOf(attr));
        return this;
    }

    public SkinValueBuilder textCompoundRightSrc(String attrName) {
        mValues.put(TEXT_COMPOUND_RIGHT_SRC, attrName);
        return this;
    }

    public SkinValueBuilder textCompoundBottomSrc(int attr) {
        mValues.put(TEXT_COMPOUND_BOTTOM_SRC, String.valueOf(attr));
        return this;
    }

    public SkinValueBuilder textCompoundBottomSrc(String attrName) {
        mValues.put(TEXT_COMPOUND_BOTTOM_SRC, attrName);
        return this;
    }

    public SkinValueBuilder textCompoundLeftSrc(int attr) {
        mValues.put(TEXT_COMPOUND_LEFT_SRC, String.valueOf(attr));
        return this;
    }

    public SkinValueBuilder textCompoundLeftSrc(String attrName) {
        mValues.put(TEXT_COMPOUND_LEFT_SRC, attrName);
        return this;
    }

    public SkinValueBuilder textColor(int attr) {
        mValues.put(TEXT_COLOR, String.valueOf(attr));
        return this;
    }

    public SkinValueBuilder textColor(String attrName) {
        mValues.put(TEXT_COLOR, attrName);
        return this;
    }

    public SkinValueBuilder hintColor(int attr) {
        mValues.put(HINT_COLOR, String.valueOf(attr));
        return this;
    }

    public SkinValueBuilder hintColor(String attrName) {
        mValues.put(HINT_COLOR, attrName);
        return this;
    }

    public SkinValueBuilder progressColor(int attr) {
        mValues.put(PROGRESS_COLOR, String.valueOf(attr));
        return this;
    }

    public SkinValueBuilder progressColor(String attrName) {
        mValues.put(PROGRESS_COLOR, attrName);
        return this;
    }

    public SkinValueBuilder src(int attr) {
        mValues.put(SRC, String.valueOf(attr));
        return this;
    }

    public SkinValueBuilder src(String attrName) {
        mValues.put(SRC, attrName);
        return this;
    }

    public SkinValueBuilder border(int attr) {
        mValues.put(BORDER, String.valueOf(attr));
        return this;
    }

    public SkinValueBuilder border(String attrName) {
        mValues.put(BORDER, attrName);
        return this;
    }

    public SkinValueBuilder topSeparator(int attr) {
        mValues.put(TOP_SEPARATOR, String.valueOf(attr));
        return this;
    }

    public SkinValueBuilder topSeparator(String attrName) {
        mValues.put(TOP_SEPARATOR, attrName);
        return this;
    }

    public SkinValueBuilder rightSeparator(int attr) {
        mValues.put(RIGHT_SEPARATOR, String.valueOf(attr));
        return this;
    }

    public SkinValueBuilder rightSeparator(String attrName) {
        mValues.put(RIGHT_SEPARATOR, attrName);
        return this;
    }

    public SkinValueBuilder bottomSeparator(int attr) {
        mValues.put(BOTTOM_SEPARATOR, String.valueOf(attr));
        return this;
    }

    public SkinValueBuilder bottomSeparator(String attrName) {
        mValues.put(BOTTOM_SEPARATOR, attrName);
        return this;
    }

    public SkinValueBuilder leftSeparator(int attr) {
        mValues.put(LEFT_SEPARATOR, String.valueOf(attr));
        return this;
    }

    public SkinValueBuilder leftSeparator(String attrName) {
        mValues.put(LEFT_SEPARATOR, attrName);
        return this;
    }

    public SkinValueBuilder alpha(int attr) {
        mValues.put(ALPHA, String.valueOf(attr));
        return this;
    }

    public SkinValueBuilder alpha(String attrName) {
        mValues.put(ALPHA, attrName);
        return this;
    }

    public SkinValueBuilder tintColor(int attr) {
        mValues.put(TINT_COLOR, String.valueOf(attr));
        return this;
    }

    public SkinValueBuilder tintColor(String attrName) {
        mValues.put(TINT_COLOR, attrName);
        return this;
    }

    public SkinValueBuilder bgTintColor(int attr) {
        mValues.put(BG_TINT_COLOR, String.valueOf(attr));
        return this;
    }

    public SkinValueBuilder bgTintColor(String attrName) {
        mValues.put(BG_TINT_COLOR, attrName);
        return this;
    }

    public SkinValueBuilder secondTextColor(int attr) {
        mValues.put(SECOND_TEXT_COLOR, String.valueOf(attr));
        return this;
    }

    public SkinValueBuilder secondTextColor(String attrName) {
        mValues.put(SECOND_TEXT_COLOR, attrName);
        return this;
    }

    public SkinValueBuilder custom(String name, int attr) {
        mValues.put(name, String.valueOf(attr));
        return this;
    }

    public SkinValueBuilder custom(String name, String attrName) {
        mValues.put(name, attrName);
        return this;
    }

    public SkinValueBuilder clear() {
        mValues.clear();
        return this;
    }

    public SkinValueBuilder convertFrom(String value) {
        String[] items = value.split("[|]");
        for (String item : items) {
            String[] kv = item.split(":");
            if (kv.length != 2) {
                continue;
            }
            mValues.put(kv[0].trim(), kv[1].trim());
        }
        return this;
    }

    public boolean isEmpty() {
        return mValues.isEmpty();
    }

    public String build() {
        StringBuilder builder = new StringBuilder();
        boolean isFirstItem = true;
        for (String name : mValues.keySet()) {
            String itemValue = mValues.get(name);
            if (itemValue == null || itemValue.isEmpty()) {
                continue;
            }
            if (!isFirstItem) {
                builder.append("|");
            }
            builder.append(name);
            builder.append(":");
            builder.append(itemValue);
            isFirstItem = false;
        }
        return builder.toString();
    }

    public void release() {
        SkinValueBuilder.release(this);
    }


    @NonNull
    @Override
    public String toString() {
        if(mValues.isEmpty()){
            return "当前SkinValue为空...";
        }else{
            return build();
        }
    }
}
