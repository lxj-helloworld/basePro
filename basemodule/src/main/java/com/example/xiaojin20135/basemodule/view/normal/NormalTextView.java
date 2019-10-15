package com.example.xiaojin20135.basemodule.view.normal;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.xiaojin20135.basemodule.R;

/*
* @author lixiaojin
* create on 2019/9/20 09:27
* description: 常规显示，左侧title，右侧展示内容
*/
public class NormalTextView extends LinearLayout {

    private LinearLayout mLl_normal_textvew;
    private TextView mTv_left_title,mTv_value;
    private ImageView linebackground_iv;
    private TextView mItem_notnull;


    private String mTitleText = "";//左侧title值
    private Float mWidthTitle;
    private Boolean mMust = true; //是否必填


    public NormalTextView(Context context) {
        super(context);
    }

    public NormalTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.NormalEditText);
        mTitleText = mTypedArray.getString(R.styleable.NormalEditText_text_title);
        mWidthTitle = mTypedArray.getDimension(R.styleable.NormalEditText_width_title,getResources().getDimension(R.dimen.title_width));
        mMust = mTypedArray.getBoolean(R.styleable.NormalEditText_must,false);
        mTypedArray.recycle();
        initView(context);
    }

    public NormalTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView(Context context){
        LayoutInflater.from(context).inflate(R.layout.item_normal_textview,this,true);
        mItem_notnull = findViewById(R.id.item_notnull);
        mLl_normal_textvew = findViewById(R.id.ll_normal_textview);
        mTv_left_title = findViewById(R.id.tv_left_title);
        mTv_value = findViewById(R.id.tv_value);
        linebackground_iv = findViewById(R.id.linebackground_iv);

        if(getResources().getDimension(R.dimen.title_width) != mWidthTitle){
            ViewGroup.LayoutParams params = mTv_left_title.getLayoutParams();
            params.width = mWidthTitle.intValue();
            mTv_left_title.setLayoutParams(params);
        }
        //设置是否是必填
        if(mMust){
            mItem_notnull.setVisibility(VISIBLE);
        }else{
            mItem_notnull.setVisibility(GONE);
        }
        setmTitleText(mTitleText);
    }


    public void setmTitleText(String mTitleText) {
        this.mTitleText = mTitleText;
        if(mTv_left_title != null){
            mTv_left_title.setText(mTitleText);
        }
    }

    public void setRightValue(String rightValue){
        if(mTv_value != null){
            this.mTv_value.setText(rightValue);
        }


    }
}
