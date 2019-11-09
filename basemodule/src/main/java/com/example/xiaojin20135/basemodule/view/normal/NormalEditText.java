package com.example.xiaojin20135.basemodule.view.normal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.Dimension;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.xiaojin20135.basemodule.R;

/*
 * @author lixiaojin
 * create on 2019/9/6 16:36
 * description: 常规输入，左侧title，右侧输入框
 */
public class NormalEditText extends LinearLayout {
    private LinearLayout mLl_normal_input;
    private TextView mItem_notnull;
    public TextView mTv_left_title;
    public EditText mEt_value;


    private String mTitleText = ""; //左侧title名称
    private Boolean mMust = true; //是否必填
    private String mHint = ""; //默认提示信息
    private Float mWidthTitle;
    private boolean mOnlyClick;//只可点击，不可编辑
    private String mTextAlignment = "";

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public NormalEditText(Context context) {
        super(context);
        initView(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public NormalEditText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.NormalEditText);

        mTitleText = mTypedArray.getString(R.styleable.NormalEditText_text_title);
        mMust = mTypedArray.getBoolean(R.styleable.NormalEditText_must, true);
        mWidthTitle = mTypedArray.getDimension(R.styleable.NormalEditText_width_title, getResources().getDimension(R.dimen.title_width));
        mHint = mTypedArray.getString(R.styleable.NormalEditText_text_hint);
        mOnlyClick = mTypedArray.getBoolean(R.styleable.NormalEditText_only_click, false);
        mTextAlignment = mTypedArray.getString(R.styleable.NormalEditText_text_alignment);

        mTypedArray.recycle();

        initView(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public NormalEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @SuppressLint("WrongConstant")
    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.item_normal_edittext, this, true);
        mItem_notnull = findViewById(R.id.item_notnull);
        mLl_normal_input = findViewById(R.id.ll_normal_input);
        mTv_left_title = findViewById(R.id.tv_left_title);
        mEt_value = findViewById(R.id.et_value);

        if (getResources().getDimension(R.dimen.title_width) != mWidthTitle) {
            ViewGroup.LayoutParams params = mTv_left_title.getLayoutParams();
            params.width = mWidthTitle.intValue();
            mTv_left_title.setLayoutParams(params);
            Log.d("NormalEditText", "重新设置宽度mWidthTitle = " + mWidthTitle);
        }

        //设置是否是必填
        if (mMust) {
            mItem_notnull.setVisibility(VISIBLE);
        } else {
            mItem_notnull.setVisibility(GONE);
        }
        setTitleLeft(mTitleText);

        //设置hint属性
        if (!"".equals(mHint)) {
            mEt_value.setHint(mHint);
        }

        //设置点击事件
        if (mOnlyClick) {
            mEt_value.setCursorVisible(false);
            mEt_value.setFocusable(false);
            mEt_value.setFocusableInTouchMode(false);
        }

        //设置alignment
        if (mTextAlignment != null && !"".equals(mTextAlignment)) {
            switch (mTextAlignment) {
                case "center":
                    mEt_value.setTextAlignment(TEXT_ALIGNMENT_CENTER);
                    break;
                case "textEnd":
                    mEt_value.setTextAlignment(TEXT_ALIGNMENT_TEXT_END);
                    break;
                case "gravity":
                    mEt_value.setTextAlignment(TEXT_ALIGNMENT_GRAVITY);
                    break;
                case "inherit":
                    mEt_value.setTextAlignment(TEXT_ALIGNMENT_INHERIT);
                    break;
                case "textStart":
                    mEt_value.setTextAlignment(TEXT_ALIGNMENT_TEXT_START);
                    break;
                case "viewEnd":
                    mEt_value.setTextAlignment(TEXT_ALIGNMENT_VIEW_END);
                    break;
                case "viewStart":
                    mEt_value.setTextAlignment(TEXT_ALIGNMENT_VIEW_START);
                    break;
            }

        }

    }

    /*
     * @author lixiaojin
     * create on 2019/9/6 16:49
     * description: 设置左侧title名称
     */
    public void setTitleLeft(String titleLeft) {
        mTv_left_title.setText(titleLeft);
    }

    /*
     * @author lixiaojin
     * create on 2019/9/6 16:50
     * description: 设置右侧值
     */
    public void setRightValue(String value) {
        mEt_value.setText(value);
    }


}
