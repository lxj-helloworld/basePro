package com.example.xiaojin20135.basemodule.view.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntDef;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.xiaojin20135.basemodule.R;
import com.example.xiaojin20135.basemodule.skin.SkinHelper;
import com.example.xiaojin20135.basemodule.skin.SkinValueBuilder;
import com.example.xiaojin20135.basemodule.util.ui.ResHelper;
import com.example.xiaojin20135.basemodule.view.LoadingView;
import com.example.xiaojin20135.basemodule.view.widget.textview.SpanTouchFixTextView;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.HashSet;

/*
* @author lixiaojin
* create on 2020-03-14 14:53
* description: 提示框
*/
public class TipDialog extends BaseDialog {

    public TipDialog(Context context) {
        this(context, R.style.TipDialog);
    }

    public TipDialog(Context context, int themeResId) {
        super(context, themeResId);
        //禁用点击边框外事件监听
        setCanceledOnTouchOutside(false);
    }

    /**
     * 生成默认的 {@link TipDialog}
     * <p>
     * 提供了一个图标和一行文字的样式, 其中图标有几种类型可选。见 {@link IconType}
     * </p>
     *
     * @see CustomBuilder
     */
    public static class Builder {
        //不显示任何icon
        public static final int ICON_TYPE_NOTHING = 0;
        //显示加载图标
        public static final int ICON_TYPE_LOADING = 1;
        //显示成功图标
        public static final int ICON_TYPE_SUCCESS = 2;
        //显示失败图标
        public static final int ICON_TYPE_FAIL = 3;
        //显示一般消息图标
        public static final int ICON_TYPE_INFO = 4;

        @IntDef({ICON_TYPE_NOTHING, ICON_TYPE_LOADING, ICON_TYPE_SUCCESS, ICON_TYPE_FAIL, ICON_TYPE_INFO})
        @Retention(RetentionPolicy.SOURCE)
        public @interface IconType {
        }

        //图标类型
        private @IconType int mCurrentIconType = ICON_TYPE_NOTHING;
        //当前上下文
        private Context mContext;
        //提示文字
        private CharSequence mTipWord;

        public Builder(Context context) {
            mContext = context;
        }

        /*
        * @author lixiaojin
        * create on 2020-03-16 09:59
        * description:设置显示的图标
        */
        public Builder setIconType(@IconType int iconType) {
            mCurrentIconType = iconType;
            return this;
        }

        /*
        * @author lixiaojin
        * create on 2020-03-16 09:59
        * description: 设置显示的提示信息
        */
        public Builder setTipWord(CharSequence tipWord) {
            mTipWord = tipWord;
            return this;
        }

        /*
        * @author lixiaojin
        * create on 2020-03-16 09:59
        * description: 创建一个弹框
        */
        public TipDialog create() {
            return create(true);
        }

        /*
        * @author lixiaojin
        * create on 2020-03-16 09:59
        * description:  创建一个弹框，是否可以取消
        */
        public TipDialog create(boolean cancelable) {
            return create(cancelable, R.style.TipDialog);
        }

        /*
        * @author lixiaojin
        * create on 2020-03-16 09:58
        * description: 创建弹框
        */
        public TipDialog create(boolean cancelable, int style) {
            //初始化一个弹框对象
            TipDialog dialog = new TipDialog(mContext, style);
            dialog.setCancelable(cancelable); //是否可以取消
            Context dialogContext = dialog.getContext(); //获取弹框的上下文
            //创建一个弹框View
            TipDialogView dialogView = new TipDialogView(dialogContext);
            SkinValueBuilder builder = SkinValueBuilder.acquire();
            if (mCurrentIconType == ICON_TYPE_LOADING) { //如果是等待框
                //创建等待框视图
                LoadingView loadingView = new LoadingView(dialogContext);
                //设置等待框颜色
                loadingView.setColor(ResHelper.getAttrColor(dialogContext, R.attr.skin_support_tip_dialog_loading_color));
                //设置等待框大小
                loadingView.setSize(ResHelper.getAttrDimen(dialogContext, R.attr.tip_dialog_loading_size));
                builder.tintColor(R.attr.skin_support_tip_dialog_loading_color);
                SkinHelper.setSkinValue(loadingView, builder);
                //将等待框添加到弹框中
                dialogView.addView(loadingView, onCreateIconOrLoadingLayoutParams(dialogContext));
            } else if (mCurrentIconType == ICON_TYPE_SUCCESS || mCurrentIconType == ICON_TYPE_FAIL || mCurrentIconType == ICON_TYPE_INFO) {
                //如果是成功、失败或者提示信息，创建图片View
                ImageView imageView = new AppCompatImageView(mContext);
                //现有样式清理
                builder.clear();
                Drawable drawable;
                //根据使用场景初始书不同的图片
                if (mCurrentIconType == ICON_TYPE_SUCCESS) {
                    drawable = ResHelper.getAttrDrawable(dialogContext, R.attr.skin_support_tip_dialog_icon_success_src);
                    builder.src( R.attr.skin_support_tip_dialog_icon_success_src);
                } else if (mCurrentIconType == ICON_TYPE_FAIL) {
                    drawable = ResHelper.getAttrDrawable(dialogContext, R.attr.skin_support_tip_dialog_icon_error_src);
                    builder.src(R.attr.skin_support_tip_dialog_icon_error_src);
                } else {
                    drawable = ResHelper.getAttrDrawable(dialogContext, R.attr.skin_support_tip_dialog_icon_info_src);
                    builder.src(R.attr.skin_support_tip_dialog_icon_info_src);
                }
                //展示图片
                imageView.setImageDrawable(drawable);
                //设置样式
                SkinHelper.setSkinValue(imageView, builder);
                //在弹框 中展示 图片
                dialogView.addView(imageView, onCreateIconOrLoadingLayoutParams(dialogContext));
            }

            //展示文字提示信息
            if (mTipWord != null && mTipWord.length() > 0) {
                TextView tipView = new SpanTouchFixTextView(mContext);
                tipView.setEllipsize(TextUtils.TruncateAt.END);//文字过多时的展示模式
                tipView.setGravity(Gravity.CENTER); //对齐方式
                tipView.setTextSize(TypedValue.COMPLEX_UNIT_PX, ResHelper.getAttrDimen(dialogContext, R.attr.tip_dialog_text_size)); //文字大小
                tipView.setTextColor(ResHelper.getAttrColor(dialogContext, R.attr.skin_support_tip_dialog_text_color)); //文字颜色
                tipView.setText(mTipWord);
                //现有样式清理
                builder.clear();
                builder.textColor(R.attr.skin_support_tip_dialog_text_color);
                SkinHelper.setSkinValue(tipView, builder);
                dialogView.addView(tipView, onCreateTextLayoutParams(dialogContext, mCurrentIconType));
            }
            builder.release();
            dialog.setContentView(dialogView);
            return dialog;
        }

        protected LinearLayout.LayoutParams onCreateIconOrLoadingLayoutParams(Context context) {
            return new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        protected LinearLayout.LayoutParams onCreateTextLayoutParams(Context context, @IconType int iconType) {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (iconType != ICON_TYPE_NOTHING) {
                lp.topMargin = ResHelper.getAttrDimen(context, R.attr.tip_dialog_text_margin_top);
            }
            return lp;
        }
    }

    /**
     * CustomBuilder with xml layout
     */
    public static class CustomBuilder {
        private Context mContext;
        private int mContentLayoutId;

        public CustomBuilder(Context context) {
            mContext = context;
        }

        public CustomBuilder setContent(@LayoutRes int layoutId) {
            mContentLayoutId = layoutId;
            return this;
        }

        public TipDialog create() {
            TipDialog dialog = new TipDialog(mContext);
            Context dialogContext = dialog.getContext();
            TipDialogView tipDialogView = new TipDialogView(dialogContext);
            LayoutInflater.from(dialogContext).inflate(mContentLayoutId, tipDialogView, true);
            dialog.setContentView(tipDialogView);
            return dialog;
        }
    }
}
