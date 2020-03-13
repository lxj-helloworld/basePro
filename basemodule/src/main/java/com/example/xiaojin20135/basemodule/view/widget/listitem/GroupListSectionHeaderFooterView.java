package com.example.xiaojin20135.basemodule.view.widget.listitem;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.xiaojin20135.basemodule.R;
import com.example.xiaojin20135.basemodule.util.LangHelper;

/*
* @author lixiaojin
* create on 2020-03-12 15:44
* description: 列表分组头和分组尾
*/
public class GroupListSectionHeaderFooterView extends LinearLayout {

    private TextView mTextView;

    /*
    * @author lixiaojin
    * create on 2020-03-12 15:48
    * description: 初始化样式采用自定义样式，只定义View内边距
    */
    public GroupListSectionHeaderFooterView(Context context) {
        this(context, null, R.attr.GroupListSectionViewStyle);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-12 15:51
    * description: 同上
    */
    public GroupListSectionHeaderFooterView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.GroupListSectionViewStyle);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-12 15:51
    * description: 同上
    */
    public GroupListSectionHeaderFooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-12 15:51
    * description: 初始化，附带标题
    */
    public GroupListSectionHeaderFooterView(Context context, CharSequence titleText) {
        this(context);
        setText(titleText);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-12 15:51
    * description: 初始化，附带标题以及是否需要显示底部分组
    */
    public GroupListSectionHeaderFooterView(Context context, CharSequence titleText, boolean isFooter) {
        this(context);
        if (isFooter) {
            // Footer View 不需要 padding bottom
            setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), 0);
        }
        setText(titleText);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-12 16:00
    * description: 初始化布局
    */
    private void init(Context context) {
        //加载布局文件
        LayoutInflater.from(context).inflate(R.layout.group_list_section_layout, this, true);
        setGravity(Gravity.BOTTOM);
        mTextView = findViewById(R.id.group_list_section_header_textView);
    }

    public void setText(CharSequence text) {
        if (LangHelper.isNullOrEmpty(text)) {
            mTextView.setVisibility(GONE);
        } else {
            mTextView.setVisibility(VISIBLE);
        }
        mTextView.setText(text);
    }

    public TextView getTextView() {
        return mTextView;
    }

    public void setTextGravity(int gravity) {
        mTextView.setGravity(gravity);
    }
}
