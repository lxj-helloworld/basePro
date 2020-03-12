package com.example.xiaojin20135.basemodule.view.widget.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;
import com.example.xiaojin20135.basemodule.R;
import com.example.xiaojin20135.basemodule.layout.UIButton;
import com.example.xiaojin20135.basemodule.layout.UIConstraintLayout;
import com.example.xiaojin20135.basemodule.skin.SkinHelper;
import com.example.xiaojin20135.basemodule.skin.SkinValueBuilder;
import com.example.xiaojin20135.basemodule.util.LogUtils;
import com.example.xiaojin20135.basemodule.util.ui.DisplayHelper;
import com.example.xiaojin20135.basemodule.util.ui.ResHelper;
import com.example.xiaojin20135.basemodule.view.widget.WrapContentScrollView;
import com.example.xiaojin20135.basemodule.view.widget.textview.SpanTouchFixTextView;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

public abstract class DialogBuilder <T extends DialogBuilder> {
    private static final String TAG = "DialogBuilder";

    @IntDef({HORIZONTAL, VERTICAL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Orientation {
    }

    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    /**
     * A global theme provider, use to distinguish theme from different builder type
     */
    private static OnProvideDefaultTheme sOnProvideDefaultTheme = null;

    public static void setOnProvideDefaultTheme(OnProvideDefaultTheme onProvideDefaultTheme) {
        DialogBuilder.sOnProvideDefaultTheme = onProvideDefaultTheme;
    }

    private Context mContext;
    protected Dialog mDialog;
    protected String mTitle;
    private boolean mCancelable = true;
    private boolean mCanceledOnTouchOutside = true;

    protected DialogRootLayout mRootView;
    protected DialogView mDialogView;
    protected List<DialogAction> mActions = new ArrayList<>();
    private DialogView.OnDecorationListener mOnDecorationListener;

    @Orientation private int mActionContainerOrientation = HORIZONTAL;
    private boolean mChangeAlphaForPressOrDisable = true;
    private int mActionDividerThickness = 0;
    private int mActionDividerColorAttr = R.attr.skin_support_dialog_action_divider_color;
    private int mActionDividerInsetStart = 0;
    private int mActionDividerInsetEnd = 0;
    private int mActionDividerColor = 0;
    private boolean mCheckKeyboardOverlay = false;
    private float mMaxPercent = 0.75f;

    public DialogBuilder(Context context) {
        this.mContext = context;
    }

    public Context getBaseContext() {
        return mContext;
    }

    /**
     * 设置对话框顶部的标题文字
     */
    @SuppressWarnings("unchecked")
    public T setTitle(String title) {
        if (title != null && title.length() > 0) {
            this.mTitle = title + mContext.getString(R.string.tool_fixellipsize);
        }
        return (T) this;
    }

    /**
     * 设置对话框顶部的标题文字
     */
    public T setTitle(int resId) {
        return setTitle(mContext.getResources().getString(resId));
    }

    @SuppressWarnings("unchecked")
    public T setCancelable(boolean cancelable) {
        mCancelable = cancelable;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
        mCanceledOnTouchOutside = canceledOnTouchOutside;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setOnDecorationListener(DialogView.OnDecorationListener onDecorationListener) {
        mOnDecorationListener = onDecorationListener;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setActionContainerOrientation(int actionContainerOrientation) {
        mActionContainerOrientation = actionContainerOrientation;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setChangeAlphaForPressOrDisable(boolean changeAlphaForPressOrDisable) {
        mChangeAlphaForPressOrDisable = changeAlphaForPressOrDisable;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setActionDivider(int thickness, int colorAttr, int startInset, int endInset) {
        mActionDividerThickness = thickness;
        mActionDividerColorAttr = colorAttr;
        mActionDividerInsetStart = startInset;
        mActionDividerInsetEnd = endInset;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setActionDividerInsetAndThickness(int thickness, int startInset, int endInset){
        mActionDividerThickness = thickness;
        mActionDividerInsetStart = startInset;
        mActionDividerInsetEnd = endInset;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setActionDividerColorAttr(int colorAttr){
        mActionDividerColorAttr = colorAttr;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setActionDividerColor(int color){
        mActionDividerColor = color;
        mActionDividerColorAttr = 0;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setCheckKeyboardOverlay(boolean checkKeyboardOverlay) {
        mCheckKeyboardOverlay = checkKeyboardOverlay;
        return (T) this;
    }


    @SuppressWarnings("unchecked")
    public T setMaxPercent(float maxPercent) {
        mMaxPercent = maxPercent;
        return (T) this;
    }

    //region 添加action

    /**
     * 添加对话框底部的操作按钮
     */
    @SuppressWarnings("unchecked")
    public T addAction(@Nullable DialogAction action) {
        if (action != null) {
            mActions.add(action);
        }

        return (T) this;
    }

    /**
     * 添加无图标正常类型的操作按钮
     *
     * @param strResId 文案
     * @param listener 点击回调事件
     */
    public T addAction(int strResId, DialogAction.ActionListener listener) {
        return addAction(0, strResId, listener);
    }

    /**
     * 添加无图标正常类型的操作按钮
     *
     * @param str      文案
     * @param listener 点击回调事件
     */
    public T addAction(CharSequence str, DialogAction.ActionListener listener) {
        return addAction(0, str, DialogAction.ACTION_PROP_NEUTRAL, listener);
    }


    /**
     * 添加普通类型的操作按钮
     *
     * @param iconResId 图标
     * @param strResId  文案
     * @param listener  点击回调事件
     */
    public T addAction(int iconResId, int strResId, DialogAction.ActionListener listener) {
        return addAction(iconResId, strResId, DialogAction.ACTION_PROP_NEUTRAL, listener);
    }

    /**
     * 添加普通类型的操作按钮
     *
     * @param iconResId 图标
     * @param str       文案
     * @param listener  点击回调事件
     */
    public T addAction(int iconResId, CharSequence str, DialogAction.ActionListener listener) {
        return addAction(iconResId, str, DialogAction.ACTION_PROP_NEUTRAL, listener);
    }


    /**
     * 添加操作按钮
     *
     * @param iconRes  图标
     * @param strRes   文案
     * @param prop     属性
     * @param listener 点击回调事件
     */
    public T addAction(int iconRes, int strRes, @DialogAction.Prop int prop, DialogAction.ActionListener listener) {
        return addAction(iconRes, mContext.getResources().getString(strRes), prop, listener);
    }

    /**
     * 添加操作按钮
     *
     * @param iconRes  图标
     * @param str      文案
     * @param prop     属性
     * @param listener 点击回调事件
     */
    @SuppressWarnings("unchecked")
    public T addAction(int iconRes, CharSequence str, @DialogAction.Prop int prop, DialogAction.ActionListener listener) {
        DialogAction action = new DialogAction(str)
                .iconRes(iconRes)
                .prop(prop)
                .onClick(listener);
        mActions.add(action);
        return (T) this;
    }


    //endregion

    /**
     * 判断对话框是否需要显示title
     *
     * @return 是否有title
     */
    protected boolean hasTitle() {
        return mTitle != null && mTitle.length() != 0;
    }

    /**
     * 产生一个 Dialog 并显示出来
     */
    public Dialog show() {
        final Dialog dialog = create();
        dialog.show();
        return dialog;
    }

    /**
     * 只产生一个 Dialog, 不显示出来
     *
     * @see #create(int)
     */
    public Dialog create() {
        if (sOnProvideDefaultTheme != null) {
            int theme = sOnProvideDefaultTheme.getThemeForBuilder(this);
            if (theme > 0) {
                return create(theme);
            }
        }
        return create(R.style.Dialog);
    }

    /**
     * 产生一个Dialog，但不显示出来。
     *
     * @param style Dialog 的样式
     * @see #create()
     */
    @SuppressLint("InflateParams")
    public Dialog create(@StyleRes int style) {
        mDialog = new Dialog(mContext, style);
        Context dialogContext = mDialog.getContext();

        mDialogView = onCreateDialogView(dialogContext);
        mRootView = new DialogRootLayout(dialogContext, mDialogView, onCreateDialogLayoutParams());
        mRootView.setCheckKeyboardOverlay(mCheckKeyboardOverlay);
        mRootView.setOverlayOccurInMeasureCallback(new DialogRootLayout.OverlayOccurInMeasureCallback() {
            @Override
            public void call() {
                onOverlayOccurredInMeasure();
            }
        });
        mRootView.setMaxPercent(mMaxPercent);
        configRootLayout(mRootView);
        mDialogView = mRootView.getDialogView();
        mDialogView.setOnDecorationListener(mOnDecorationListener);
        // title
        View titleView = onCreateTitle(mDialog, mDialogView, dialogContext);
        View operatorLayout = onCreateOperatorLayout(mDialog, mDialogView, dialogContext);
        View contentLayout = onCreateContent(mDialog, mDialogView, dialogContext);
        checkAndSetId(titleView, R.id.dialog_title_id);
        checkAndSetId(operatorLayout, R.id.dialog_operator_layout_id);
        checkAndSetId(contentLayout, R.id.dialog_content_id);

        // chain
        if (titleView != null) {
            UIConstraintLayout.LayoutParams lp = onCreateTitleLayoutParams(dialogContext);
            if (contentLayout != null) {
                lp.bottomToTop = contentLayout.getId();
            } else if (operatorLayout != null) {
                lp.bottomToTop = operatorLayout.getId();
            } else {
                lp.bottomToBottom = UIConstraintLayout.LayoutParams.PARENT_ID;
            }
            mDialogView.addView(titleView, lp);
        }

        if (contentLayout != null) {
            UIConstraintLayout.LayoutParams lp = onCreateContentLayoutParams(dialogContext);
            if (titleView != null) {
                lp.topToBottom = titleView.getId();
            } else {
                lp.topToTop = UIConstraintLayout.LayoutParams.PARENT_ID;
            }

            if (operatorLayout != null) {
                lp.bottomToTop = operatorLayout.getId();
            } else {
                lp.bottomToBottom = UIConstraintLayout.LayoutParams.PARENT_ID;
            }
            mDialogView.addView(contentLayout, lp);
        }

        if (operatorLayout != null) {
            UIConstraintLayout.LayoutParams lp = onCreateOperatorLayoutLayoutParams(dialogContext);
            if (contentLayout != null) {
                lp.topToBottom = contentLayout.getId();
            } else if (titleView != null) {
                lp.topToBottom = titleView.getId();
            } else {
                lp.topToTop = UIConstraintLayout.LayoutParams.PARENT_ID;
            }
            mDialogView.addView(operatorLayout, lp);
        }

        mDialog.addContentView(mRootView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mDialog.setCancelable(mCancelable);
        mDialog.setCanceledOnTouchOutside(mCanceledOnTouchOutside);
//        mDialog.setSkinManager(mSkinManager);
        onAfterCreate(mDialog, mRootView, dialogContext);
//        LogUtils.d(TAG,"mDialog 的大小 ： " + mDialo);
        return mDialog;
    }

    protected void onAfterCreate(@NonNull Dialog dialog, @NonNull DialogRootLayout rootLayout, @NonNull Context context){

    }

    protected void onOverlayOccurredInMeasure(){

    }

    private void checkAndSetId(@Nullable View view, int id) {
        if (view != null && view.getId() == View.NO_ID) {
            view.setId(id);
        }
    }

    protected void configRootLayout(@NonNull DialogRootLayout rootLayout){

    }

    /*
    * @author lixiaojin
    * create on 2020-03-10 17:16
    * description: 对话框背景设置
    */
    protected void skinConfigDialogView(DialogView dialogView){
        SkinValueBuilder valueBuilder = SkinValueBuilder.acquire();
        valueBuilder.background(R.attr.skin_support_dialog_bg);
        SkinHelper.setSkinValue(dialogView, valueBuilder);
        SkinValueBuilder.release(valueBuilder);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-10 17:17
    * description: 对话框title设置
    */
    protected void skinConfigTitleView(TextView titleView){
        SkinValueBuilder valueBuilder = SkinValueBuilder.acquire();
        valueBuilder.textColor(R.attr.skin_support_dialog_title_text_color);
        SkinHelper.setSkinValue(titleView, valueBuilder);
        SkinValueBuilder.release(valueBuilder);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-10 17:17
    * description: 对话框  按钮容器设置
    */
    protected void skinConfigActionContainer(ViewGroup actionContainer){
        SkinValueBuilder valueBuilder = SkinValueBuilder.acquire();
        valueBuilder.topSeparator(R.attr.skin_support_dialog_action_container_separator_color);
        SkinHelper.setSkinValue(actionContainer, valueBuilder);
        SkinValueBuilder.release(valueBuilder);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-10 17:20
    * description:
    */
    @NonNull
    protected DialogView onCreateDialogView(@NonNull Context context){
        DialogView dialogView = new DialogView(context);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            dialogView.setBackground(ResHelper.getAttrDrawable(context, R.attr.skin_support_dialog_bg));
        }
        dialogView.setRadius(ResHelper.getAttrDimen(context, R.attr.dialog_radius));
        skinConfigDialogView(dialogView);
        return dialogView;
    }

    @NonNull
    protected FrameLayout.LayoutParams onCreateDialogLayoutParams() {
        return new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-10 17:20
    * description:
    */
    @Nullable
    protected View onCreateTitle(@NonNull Dialog dialog, @NonNull DialogView parent, @NonNull Context context) {
        //如果有标题，创建标题View
        if (hasTitle()) {
            TextView tv = new SpanTouchFixTextView(context);
            tv.setId(R.id.dialog_title_id);
            tv.setText(mTitle);
            ResHelper.assignTextViewWithAttr(tv, R.attr.dialog_title_style);
            skinConfigTitleView(tv);
            return tv;
        }
        return null;
    }

    @NonNull
    protected UIConstraintLayout.LayoutParams onCreateTitleLayoutParams(@NonNull Context context) {
        UIConstraintLayout.LayoutParams lp = new UIConstraintLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftToLeft = UIConstraintLayout.LayoutParams.PARENT_ID;
        lp.rightToRight = UIConstraintLayout.LayoutParams.PARENT_ID;
        lp.topToTop = UIConstraintLayout.LayoutParams.PARENT_ID;
        lp.verticalChainStyle = UIConstraintLayout.LayoutParams.CHAIN_PACKED;
        return lp;
    }


    @Nullable
    protected abstract View onCreateContent(@NonNull Dialog dialog, @NonNull DialogView parent, @NonNull Context context);


    protected WrapContentScrollView wrapWithScroll(@NonNull View view){
        WrapContentScrollView scrollView = new WrapContentScrollView(view.getContext());
        scrollView.addView(view);
        scrollView.setVerticalScrollBarEnabled(false);
        LogUtils.d(TAG,"scrollView 的大小 ： " + scrollView.getMeasuredWidth() + " " + scrollView.getMeasuredHeight());
        return scrollView;
    }

    protected ConstraintLayout.LayoutParams onCreateContentLayoutParams(@NonNull Context context) {
        ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID;
        lp.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID;
//        lp.constrainedHeight = true;
        return lp;
    }


    @Nullable
    protected View onCreateOperatorLayout(@NonNull final Dialog dialog, @NonNull DialogView parent, @NonNull Context context) {
        int size = mActions.size();
        if (size > 0) {
            TypedArray a = context.obtainStyledAttributes(null, R.styleable.DialogActionContainerCustomDef, R.attr.dialog_action_container_style, 0);
            int count = a.getIndexCount();
            int justifyContent = 1, spaceCustomIndex = 0;
            int actionHeight = -1, actionSpace = 0;
            for (int i = 0; i < count; i++) {
                int attr = a.getIndex(i);
                if (attr == R.styleable.DialogActionContainerCustomDef_dialog_action_container_justify_content) {
                    justifyContent = a.getInteger(attr, justifyContent);
                } else if (attr == R.styleable.DialogActionContainerCustomDef_dialog_action_container_custom_space_index) {
                    spaceCustomIndex = a.getInteger(attr, 0);
                } else if (attr == R.styleable.DialogActionContainerCustomDef_dialog_action_space) {
                    actionSpace = a.getDimensionPixelSize(attr, 0);
                } else if (attr == R.styleable.DialogActionContainerCustomDef_dialog_action_height) {
                    actionHeight = a.getDimensionPixelSize(attr, 0);
                }
            }
            a.recycle();
            int spaceInsertPos = -1;
            if (mActionContainerOrientation != VERTICAL) {
                if (justifyContent == 0) {
                    spaceInsertPos = size;
                } else if (justifyContent == 1) {
                    spaceInsertPos = 0;
                } else if (justifyContent == 3) {
                    spaceInsertPos = spaceCustomIndex;
                }
            }
            final LinearLayout layout = new LinearLayout(context, null, R.attr.dialog_action_container_style);
            layout.setId(R.id.dialog_operator_layout_id);
            layout.setOrientation(mActionContainerOrientation == VERTICAL ? LinearLayout.VERTICAL : LinearLayout.HORIZONTAL);
            skinConfigActionContainer(layout);
            for (int i = 0; i < size; i++) {
                if (spaceInsertPos == i) {
                    layout.addView(createActionContainerSpace(context));
                }
                DialogAction action = mActions.get(i);
                action.skinSeparatorColorAttr(mActionDividerColorAttr);
                LinearLayout.LayoutParams actionLp;
                if (mActionContainerOrientation == VERTICAL) {
                    actionLp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, actionHeight);
                } else {
                    actionLp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, actionHeight);
                    if (spaceInsertPos >= 0) {
                        if (i >= spaceInsertPos) {
                            actionLp.leftMargin = actionSpace;
                        } else {
                            actionLp.rightMargin = actionSpace;
                        }
                    }
                    if (justifyContent == 2) {
                        actionLp.weight = 1;
                    }
                }
                //生成一个button
                UIButton actionView = action.buildActionView(mDialog, i);

                // add divider
                LogUtils.d(TAG,"mActionDividerThickness = " + mActionDividerThickness);
                if (mActionDividerThickness > 0 && i > 0 && spaceInsertPos != i) {
                    int color = mActionDividerColorAttr == 0 ? mActionDividerColor : mActionDividerColor;
                    if (mActionContainerOrientation == VERTICAL) {
                        actionView.onlyShowTopDivider(mActionDividerInsetStart, mActionDividerInsetEnd, mActionDividerThickness, color);
                    } else {
                        actionView.onlyShowLeftDivider(mActionDividerInsetStart, mActionDividerInsetEnd, mActionDividerThickness, color);
                    }
                }
                actionView.setChangeAlphaWhenDisable(mChangeAlphaForPressOrDisable);
                actionView.setChangeAlphaWhenPress(mChangeAlphaForPressOrDisable);

//                Button button1 = new Button(mContext);
//                button1.setText("确定");
                layout.addView(actionView, actionLp);
            }
            if (spaceInsertPos == size) {
                layout.addView(createActionContainerSpace(context));
            }
            if (mActionContainerOrientation == HORIZONTAL) {
                layout.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                    @Override
                    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                        int width = right - left;
                        int childCount = layout.getChildCount();
                        if (childCount > 0) {
                            View lastChild = layout.getChildAt(childCount - 1);
                            // 如果ActionButton的宽度过宽，则减小padding
                            if (lastChild.getRight() > width) {
                                int childPaddingHor = Math.max(0, lastChild.getPaddingLeft() - DisplayHelper.dp2px(mContext, 3));
                                for (int i = 0; i < childCount; i++) {
                                    layout.getChildAt(i).setPadding(childPaddingHor, 0, childPaddingHor, 0);
                                }
                            }
                        }
                    }
                });
            }
            return layout;
        }
        return null;
    }

    @NonNull
    protected UIConstraintLayout.LayoutParams onCreateOperatorLayoutLayoutParams(@NonNull Context context) {
        UIConstraintLayout.LayoutParams lp = new UIConstraintLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftToLeft = UIConstraintLayout.LayoutParams.PARENT_ID;
        lp.rightToRight = UIConstraintLayout.LayoutParams.PARENT_ID;
        lp.bottomToBottom = UIConstraintLayout.LayoutParams.PARENT_ID;
        lp.verticalChainStyle = UIConstraintLayout.LayoutParams.CHAIN_PACKED;
        return lp;
    }

    private View createActionContainerSpace(Context context) {
        Space space = new Space(context);
        LinearLayout.LayoutParams spaceLp = new LinearLayout.LayoutParams(0, 0);
        spaceLp.weight = 1;
        space.setLayoutParams(spaceLp);
        return space;
    }

    public List<DialogAction> getPositiveAction() {
        List<DialogAction> output = new ArrayList<>();
        for (DialogAction action : mActions) {
            if (action.getActionProp() == DialogAction.ACTION_PROP_POSITIVE) {
                output.add(action);
            }
        }
        return output;
    }

    public interface OnProvideDefaultTheme {
        int getThemeForBuilder(DialogBuilder builder);
    }
}
