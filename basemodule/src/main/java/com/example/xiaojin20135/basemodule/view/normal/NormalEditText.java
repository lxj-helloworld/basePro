package com.example.xiaojin20135.basemodule.view.normal;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Dimension;
import android.support.annotation.Nullable;
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
    private Float mWidthTitle;

    public NormalEditText(Context context) {
        super(context);
        initView(context);
    }

    public NormalEditText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs,R.styleable.NormalEditText);

        mTitleText = mTypedArray.getString(R.styleable.NormalEditText_text_title);
        mMust = mTypedArray.getBoolean(R.styleable.NormalEditText_must,true);
        mWidthTitle = mTypedArray.getDimension(R.styleable.NormalEditText_width_title,getResources().getDimension(R.dimen.title_width));

        mTypedArray.recycle();

        initView(context);
    }

    public NormalEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }


    private void initView(Context context){
        LayoutInflater.from(context).inflate(R.layout.item_normal_edittext,this,true);
        mItem_notnull = findViewById(R.id.item_notnull);
        mLl_normal_input = findViewById(R.id.ll_normal_input);
        mTv_left_title = findViewById(R.id.tv_left_title);
        mEt_value = findViewById(R.id.et_value);

        if(getResources().getDimension(R.dimen.title_width) != mWidthTitle){
            ViewGroup.LayoutParams params = mTv_left_title.getLayoutParams();
            params.width = mWidthTitle.intValue();
            mTv_left_title.setLayoutParams(params);
            Log.d("NormalEditText","重新设置宽度mWidthTitle = " + mWidthTitle);
        }

        //设置是否是必填
        if(mMust){
            mItem_notnull.setVisibility(VISIBLE);
        }else{
            mItem_notnull.setVisibility(GONE);
        }
        setTitleLeft(mTitleText);

    }

    /*
    * @author lixiaojin
    * create on 2019/9/6 16:49
    * description: 设置左侧title名称
    */
    public void setTitleLeft(String titleLeft){
        mTv_left_title.setText(titleLeft);
    }

    /*
    * @author lixiaojin
    * create on 2019/9/6 16:50
    * description: 设置右侧值
    */
    public void setRightValue(String value){
        mEt_value.setText(value);
    }





}
