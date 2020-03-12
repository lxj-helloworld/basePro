package com.example.xiaojin20135.basemodule.view.widget.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.xiaojin20135.basemodule.R;
import com.example.xiaojin20135.basemodule.layout.UIConstraintLayout;
import com.example.xiaojin20135.basemodule.skin.SkinHelper;
import com.example.xiaojin20135.basemodule.skin.SkinValueBuilder;
import com.example.xiaojin20135.basemodule.util.ui.ResHelper;
import com.example.xiaojin20135.basemodule.util.ui.ViewHelper;
import com.example.xiaojin20135.basemodule.view.widget.textview.SpanTouchFixTextView;

public class DialogMenuItemView  extends UIConstraintLayout {
    private int index = -1;
    private MenuItemViewListener mListener;
    private boolean mIsChecked = false;

    public DialogMenuItemView(Context context) {
        super(context, null, R.attr.dialog_menu_item_style);
        SkinValueBuilder builder = SkinValueBuilder.acquire();
        builder.background(R.attr.skin_support_s_dialog_menu_item_bg);
        SkinHelper.setSkinValue(this, builder);
        SkinValueBuilder.release(builder);
    }

    @SuppressLint("CustomViewStyleable")
    public static TextView createItemTextView(Context context) {
        TextView tv = new SpanTouchFixTextView(context);
        TypedArray a = context.obtainStyledAttributes(null, R.styleable.DialogMenuTextStyleDef, R.attr.dialog_menu_item_style, 0);
        int count = a.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.DialogMenuTextStyleDef_android_gravity) {
                tv.setGravity(a.getInt(attr, -1));
            } else if (attr == R.styleable.DialogMenuTextStyleDef_android_textColor) {
                tv.setTextColor(a.getColorStateList(attr));
            } else if (attr == R.styleable.DialogMenuTextStyleDef_android_textSize) {
                tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, a.getDimensionPixelSize(attr, 0));
            }
        }
        a.recycle();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            tv.setId(View.generateViewId());
        }
        tv.setSingleLine(true);
        tv.setEllipsize(TextUtils.TruncateAt.MIDDLE);
        tv.setDuplicateParentStateEnabled(false);
        SkinValueBuilder builder = SkinValueBuilder.acquire();
        builder.textColor(R.attr.skin_support_dialog_menu_item_text_color);
         builder.textColor(Color.BLACK);
        SkinHelper.setSkinValue(tv, builder);
        SkinValueBuilder.release(builder);
        return tv;
    }

    public int getMenuIndex() {
        return this.index;
    }

    public void setMenuIndex(int index) {
        this.index = index;
    }

    protected void notifyCheckChange(boolean isChecked) {

    }

    public boolean isChecked() {
        return mIsChecked;
    }

    public void setChecked(boolean checked) {
        mIsChecked = checked;
        notifyCheckChange(mIsChecked);
    }

    public void setListener(MenuItemViewListener listener) {
        if (!isClickable()) {
            setClickable(true);
        }
        mListener = listener;
    }

    @Override
    public boolean performClick() {
        if (mListener != null) {
            mListener.onClick(index);
        }
        return super.performClick();
    }

    public interface MenuItemViewListener {
        void onClick(int index);
    }

    public static class TextItemView extends DialogMenuItemView {
        protected TextView mTextView;

        public TextItemView(Context context) {
            super(context);
            init();
        }

        public TextItemView(Context context, CharSequence text) {
            super(context);
            init();
            setText(text);
        }

        private void init() {
            mTextView = createItemTextView(getContext());
            LayoutParams lp = new LayoutParams(0, 0);
            lp.leftToLeft = LayoutParams.PARENT_ID;
            lp.rightToRight = LayoutParams.PARENT_ID;
            lp.bottomToBottom = LayoutParams.PARENT_ID;
            lp.topToTop = LayoutParams.PARENT_ID;
            addView(mTextView, lp);
        }

        public void setText(CharSequence text) {
            mTextView.setText(text);
        }

        @Deprecated
        public void setTextColor(int color) {
            mTextView.setTextColor(color);
        }

        public void setTextColorAttr(int colorAttr) {
//            int color = SkinHelper.getSkinColor(this, colorAttr);
//            mTextView.setTextColor(color);
            SkinValueBuilder builder = SkinValueBuilder.acquire();
            builder.textColor(colorAttr);
            SkinHelper.setSkinValue(mTextView, builder);
            SkinValueBuilder.release(builder);
        }
    }

    public static class MarkItemView extends DialogMenuItemView {
        private Context mContext;
        private TextView mTextView;
        private AppCompatImageView mCheckedView;

        @SuppressLint("CustomViewStyleable")
        public MarkItemView(Context context) {
            super(context);
            mContext = context;
            mCheckedView = new AppCompatImageView(mContext);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
                mCheckedView.setId(View.generateViewId());
            }
            TypedArray a = context.obtainStyledAttributes(null, R.styleable.DialogMenuMarkDef, R.attr.dialog_menu_item_style, 0);
            int markMarginHor = 0;
            int count = a.getIndexCount();
            for (int i = 0; i < count; i++) {
                int attr = a.getIndex(i);
                if (attr == R.styleable.DialogMenuMarkDef_dialog_menu_item_check_mark_margin_hor) {
                    markMarginHor = a.getDimensionPixelSize(attr, 0);
                } else if (attr == R.styleable.DialogMenuMarkDef_dialog_menu_item_mark_drawable) {
                    mCheckedView.setImageDrawable(ResHelper.getAttrDrawable(context, a, attr));
                }
            }
            a.recycle();
            SkinValueBuilder builder = SkinValueBuilder.acquire();
            builder.src(R.attr.skin_support_dialog_mark_drawable);
            SkinHelper.setSkinValue(mCheckedView, builder);
            SkinValueBuilder.release(builder);

            LayoutParams checkLp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            checkLp.rightToRight = LayoutParams.PARENT_ID;
            checkLp.topToTop = LayoutParams.PARENT_ID;
            checkLp.bottomToBottom = LayoutParams.PARENT_ID;
            addView(mCheckedView, checkLp);


            mTextView = createItemTextView(mContext);
            LayoutParams tvLp = new LayoutParams(0, 0);
            tvLp.leftToLeft = LayoutParams.PARENT_ID;
            tvLp.topToTop = LayoutParams.PARENT_ID;
            tvLp.bottomToBottom = LayoutParams.PARENT_ID;
            tvLp.rightToLeft = mCheckedView.getId();
            tvLp.rightMargin = markMarginHor;
            addView(mTextView, tvLp);
            mCheckedView.setVisibility(INVISIBLE);
        }

        public MarkItemView(Context context, CharSequence text) {
            this(context);
            setText(text);
        }

        public void setText(CharSequence text) {
            mTextView.setText(text);
        }

        @Override
        protected void notifyCheckChange(boolean isChecked) {
            mCheckedView.setVisibility(isChecked ? VISIBLE : INVISIBLE);
        }
    }

    @SuppressLint({"ViewConstructor", "CustomViewStyleable"})
    public static class CheckItemView extends DialogMenuItemView {
        private Context mContext;
        private TextView mTextView;
        private AppCompatImageView mCheckedView;

        public CheckItemView(Context context, boolean right) {
            super(context);
            mContext = context;
            mCheckedView = new AppCompatImageView(mContext);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
                mCheckedView.setId(View.generateViewId());
            }
            TypedArray a = context.obtainStyledAttributes(null, R.styleable.DialogMenuCheckDef, R.attr.dialog_menu_item_style, 0);
            int markMarginHor = 0;
            int count = a.getIndexCount();
            for (int i = 0; i < count; i++) {
                int attr = a.getIndex(i);
                if (attr == R.styleable.DialogMenuCheckDef_dialog_menu_item_check_mark_margin_hor) {
                    markMarginHor = a.getDimensionPixelSize(attr, 0);
                } else if (attr == R.styleable.DialogMenuCheckDef_dialog_menu_item_check_drawable) {
                    mCheckedView.setImageDrawable(ResHelper.getAttrDrawable(context, a, attr));
                }
            }
            a.recycle();
            LayoutParams checkLp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            checkLp.topToTop = LayoutParams.PARENT_ID;
            checkLp.bottomToBottom = LayoutParams.PARENT_ID;
            if(right){
                checkLp.rightToRight = LayoutParams.PARENT_ID;
            }else{
                checkLp.leftToLeft = LayoutParams.PARENT_ID;
            }
            SkinValueBuilder builder = SkinValueBuilder.acquire();
            builder.src(R.attr.skin_support_s_dialog_check_drawable);
            SkinHelper.setSkinValue(mCheckedView, builder);
            SkinValueBuilder.release(builder);
            addView(mCheckedView, checkLp);

            mTextView = createItemTextView(mContext);
            LayoutParams tvLp = new LayoutParams(0, 0);
            if(right){
                tvLp.leftToLeft = LayoutParams.PARENT_ID;
                tvLp.rightToLeft = mCheckedView.getId();
                tvLp.rightMargin = markMarginHor;
            }else{
                tvLp.rightToRight = LayoutParams.PARENT_ID;
                tvLp.leftToRight = mCheckedView.getId();
                tvLp.leftMargin = markMarginHor;
            }

            tvLp.topToTop = LayoutParams.PARENT_ID;
            tvLp.bottomToBottom = LayoutParams.PARENT_ID;
            addView(mTextView, tvLp);
        }

        public CheckItemView(Context context, boolean right, CharSequence text) {
            this(context, right);
            setText(text);
        }

        public void setText(CharSequence text) {
            mTextView.setText(text);
        }

        public CharSequence getText() {
            return mTextView.getText();
        }

        @Override
        protected void notifyCheckChange(boolean isChecked) {
            ViewHelper.safeSetImageViewSelected(mCheckedView, isChecked);
        }
    }
}
