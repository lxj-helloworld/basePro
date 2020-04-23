package com.example.xiaojin20135.basemodule.view.widget.bottom;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.example.xiaojin20135.basemodule.R;
import com.example.xiaojin20135.basemodule.layout.UIButton;
import com.example.xiaojin20135.basemodule.skin.SkinHelper;
import com.example.xiaojin20135.basemodule.skin.SkinValueBuilder;
import com.example.xiaojin20135.basemodule.util.ui.ResHelper;
import com.example.xiaojin20135.basemodule.view.widget.textview.SpanTouchFixTextView;

/*
* @author lixiaojin
* create on 2020-03-20 16:13
* description: BottomSheet构建者
*/
public abstract class BottomSheetBaseBuilder<T extends BottomSheetBaseBuilder> {
    private static final String TAG = "BottomSheetBaseBuilder";
    private Context mContext; //当前上下文
    protected MyBottomSheet mDialog; //将要创建的BottomSheet对象
    private CharSequence mTitle; //BottomSheet顶部对象
    private boolean mAddCancelBtn; //BottomSheet底部取消按钮
    private String mCancelText; //BottomSheet底部取消按钮文本
    private DialogInterface.OnDismissListener mOnBottomDialogDismissListener; //BottomSheet销毁监听事件
    private int mRadius = -1; //BottomSheet外边框圆角半径,默认不显示
    private boolean mAllowDrag = false; //是否允许拖拽
    private MyBottomSheetBehavior.DownDragDecisionMaker mDownDragDecisionMaker = null; //拖拽决策者

    public BottomSheetBaseBuilder(Context context) {
        mContext = context;
    }

    /*
    * @author lixiaojin
    * create on 2020-03-20 16:51
    * description: 设置title
    */
    @SuppressWarnings("unchecked")
    public T setTitle(CharSequence title) {
        mTitle = title;
        return (T) this;
    }

    /*
    * @author lixiaojin
    * create on 2020-03-20 16:51
    * description: 是否有title
    */
    protected boolean hasTitle() {
        return mTitle != null && mTitle.length() != 0;
    }

    /*
    * @author lixiaojin
    * create on 2020-03-20 16:51
    * description: 设置是否允许拖拽
    */
    @SuppressWarnings("unchecked")
    public T setAllowDrag(boolean allowDrag) {
        mAllowDrag = allowDrag;
        return (T) this;
    }

    /*
    * @author lixiaojin
    * create on 2020-03-20 17:05
    * description: 设置拖拽决策者
    */
    @SuppressWarnings("unchecked")
    public T setDownDragDecisionMaker(MyBottomSheetBehavior.DownDragDecisionMaker downDragDecisionMaker) {
        mDownDragDecisionMaker = downDragDecisionMaker;
        return (T) this;
    }

    /*
    * @author lixiaojin
    * create on 2020-03-20 17:06
    * description: 设置取消按钮
    */
    @SuppressWarnings("unchecked")
    public T setAddCancelBtn(boolean addCancelBtn) {
        mAddCancelBtn = addCancelBtn;
        return (T) this;
    }

    /*
    * @author lixiaojin
    * create on 2020-03-20 17:06
    * description: 设置取消按钮文本
    */
    @SuppressWarnings("unchecked")
    public T setCancelText(String cancelText) {
        mCancelText = cancelText;
        return (T) this;
    }

    /*
    * @author lixiaojin
    * create on 2020-03-20 17:07
    * description: 设置圆角半径
    */
    @SuppressWarnings("unchecked")
    public T setRadius(int radius) {
        mRadius = radius;
        return (T) this;
    }

    /*
    * @author lixiaojin
    * create on 2020-03-20 17:07
    * description: 设置BottomSheet消失事件
    */
    @SuppressWarnings("unchecked")
    public T setOnBottomDialogDismissListener(DialogInterface.OnDismissListener listener) {
        mOnBottomDialogDismissListener = listener;
        return (T) this;
    }

    /*
    * @author lixiaojin
    * create on 2020-03-20 17:08
    * description:创建一个BottomSheet并返回
    */
    public MyBottomSheet build() {
        return build(R.style.BottomSheet);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-20 17:08
    * description: 构建一个BottomSheet
    */
    public MyBottomSheet build(int style) {
        mDialog = new MyBottomSheet(mContext, style);
        //获取dialog上下文
        Context dialogContext = mDialog.getContext();
        //获取当前Dialog的根视图
        BottomSheetRootLayout rootLayout = mDialog.getRootView();
        //移除现有根视图上的所有子视图
        rootLayout.removeAllViews();
        //创建Title视图
        View titleView = onCreateTitleView(mDialog, rootLayout, dialogContext);
        if (titleView != null) {
            mDialog.addContentView(titleView);
        }
        //在title和内容之间添加自定义View
        onAddCustomViewBetweenTitleAndContent(mDialog, rootLayout, dialogContext);
        //创建内容视图
        View contentView = onCreateContentView(mDialog, rootLayout, dialogContext);
        if (contentView != null) {
            mDialog.addContentView(contentView);
        }
        onAddCustomViewAfterContent(mDialog, rootLayout, dialogContext);
        if (mAddCancelBtn) {
            mDialog.addContentView(onCreateCancelBtn(mDialog, rootLayout, dialogContext),
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ResHelper.getAttrDimen(dialogContext, R.attr.bottom_sheet_cancel_btn_height)));
        }
        //设置BottomSheet消失监听事件
        if (mOnBottomDialogDismissListener != null) {
            mDialog.setOnDismissListener(mOnBottomDialogDismissListener);
        }
        //设置圆角半径
        if (mRadius != -1) {
            mDialog.setRadius(mRadius);
        }
        //设置BottomSheet行为
        MyBottomSheetBehavior behavior = mDialog.getBehavior();
        behavior.setAllowDrag(mAllowDrag);
        behavior.setDownDragDecisionMaker(mDownDragDecisionMaker);
        return mDialog;
    }


    /*
    * @author lixiaojin
    * create on 2020-03-20 17:51
    * description: 创建title视图
    */
    @Nullable
    protected View onCreateTitleView(@NonNull MyBottomSheet bottomSheet,
                                     @NonNull BottomSheetRootLayout rootLayout,
                                     @NonNull Context context) {
        //如果有title，创建title视图
        if (hasTitle()) {
            //创建文本视图
            SpanTouchFixTextView tv = new SpanTouchFixTextView(context);
            tv.setId(R.id.bottom_sheet_title);
            tv.setText(mTitle);
            tv.onlyShowBottomDivider(0, 0, 1, ResHelper.getAttrColor(context, R.attr.skin_support_bottom_sheet_separator_color));
            ResHelper.assignTextViewWithAttr(tv, R.attr.bottom_sheet_title_style);
            SkinValueBuilder valueBuilder = SkinValueBuilder.acquire();

            valueBuilder.textColor(R.attr.skin_support_bottom_sheet_title_text_color);
            valueBuilder.bottomSeparator(R.attr.skin_support_bottom_sheet_separator_color);
            SkinHelper.setSkinValue(tv, valueBuilder);
            valueBuilder.release();
            return tv;
        }
        return null;
    }

    protected void onAddCustomViewBetweenTitleAndContent(@NonNull MyBottomSheet bottomSheet,
                                                         @NonNull BottomSheetRootLayout rootLayout,
                                                         @NonNull Context context) {
    }

    @Nullable
    protected abstract View onCreateContentView(@NonNull MyBottomSheet bottomSheet,
                                                @NonNull BottomSheetRootLayout rootLayout,
                                                @NonNull Context context);

    protected void onAddCustomViewAfterContent(@NonNull MyBottomSheet bottomSheet,
                                               @NonNull BottomSheetRootLayout rootLayout,
                                               @NonNull Context context) {
    }

    @NonNull
    protected View onCreateCancelBtn(@NonNull final MyBottomSheet bottomSheet,
                                     @NonNull BottomSheetRootLayout rootLayout,
                                     @NonNull Context context) {
        UIButton button = new UIButton(context);
        button.setId(R.id.bottom_sheet_cancel);
        if (mCancelText == null || mCancelText.isEmpty()) {
            mCancelText = "取消";
        }
        button.setPadding(0, 0,0, 0);
        button.setBackground(ResHelper.getAttrDrawable(context, R.attr.skin_support_bottom_sheet_cancel_bg));
        button.setText(mCancelText);
        ResHelper.assignTextViewWithAttr(button, R.attr.bottom_sheet_cancel_style);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheet.cancel();
            }
        });
        button.onlyShowTopDivider(0, 0, 1,
                ResHelper.getAttrColor(context, R.attr.skin_support_bottom_sheet_separator_color));

        SkinValueBuilder valueBuilder = SkinValueBuilder.acquire();
        valueBuilder.textColor(R.attr.skin_support_bottom_sheet_cancel_text_color);
        valueBuilder.topSeparator(R.attr.skin_support_bottom_sheet_separator_color);
        valueBuilder.background(R.attr.skin_support_bottom_sheet_cancel_bg);
        SkinHelper.setSkinValue(button, valueBuilder);
        valueBuilder.release();
        return button;
    }
}
