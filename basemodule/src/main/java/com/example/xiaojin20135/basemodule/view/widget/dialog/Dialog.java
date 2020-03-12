package com.example.xiaojin20135.basemodule.view.widget.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.TransformationMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.xiaojin20135.basemodule.R;
import com.example.xiaojin20135.basemodule.skin.SkinHelper;
import com.example.xiaojin20135.basemodule.skin.SkinValueBuilder;
import com.example.xiaojin20135.basemodule.util.LangHelper;
import com.example.xiaojin20135.basemodule.util.LogUtils;
import com.example.xiaojin20135.basemodule.util.ui.DisplayHelper;
import com.example.xiaojin20135.basemodule.util.ui.ResHelper;
import com.example.xiaojin20135.basemodule.view.widget.textview.SpanTouchFixTextView;

import java.util.ArrayList;
import java.util.BitSet;

public class Dialog extends BaseDialog {
    private static final String TAG = "Dialog";
    private Context mBaseContext;

    public Dialog(Context context) {
        this(context, R.style.Dialog);
    }

    public Dialog(Context context, int styleRes) {
        super(context, styleRes);
        mBaseContext = context;
        init();
    }

    private void init() {
        setCancelable(true);
        setCanceledOnTouchOutside(true);
    }

    public void showWithImmersiveCheck(Activity activity) {
        // http://stackoverflow.com/questions/22794049/how-to-maintain-the-immersive-mode-in-dialogs
        Window window = getWindow();
        if (window == null) {
            return;
        }
        Window activityWindow = activity.getWindow();
        int activitySystemUi = activityWindow.getDecorView().getSystemUiVisibility();
        if ((activitySystemUi & View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN) == View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN ||
                (activitySystemUi & View.SYSTEM_UI_FLAG_FULLSCREEN) == View.SYSTEM_UI_FLAG_FULLSCREEN) {
            window.setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
            window.getDecorView().setSystemUiVisibility(activity.getWindow().getDecorView().getSystemUiVisibility());
            super.show();
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        } else {
            super.show();
        }
    }

    public void showWithImmersiveCheck() {
        if (!(mBaseContext instanceof Activity)) {
            super.show();
            return;
        }
        Activity activity = (Activity) mBaseContext;
        showWithImmersiveCheck(activity);
    }


    /**
     * 消息类型的对话框 Builder。通过它可以生成一个带标题、文本消息、按钮的对话框。
     */
    public static class MessageDialogBuilder extends DialogBuilder<MessageDialogBuilder> {
        protected CharSequence mMessage;

        public MessageDialogBuilder(Context context) {
            super(context);
        }

        /**
         * 设置对话框的消息文本
         */
        public MessageDialogBuilder setMessage(CharSequence message) {
            this.mMessage = message;
            return this;
        }

        /**
         * 设置对话框的消息文本
         */
        public MessageDialogBuilder setMessage(int resId) {
            return setMessage(getBaseContext().getResources().getString(resId));
        }

        @Nullable
        @Override
        protected View onCreateContent(@NonNull Dialog dialog, @NonNull DialogView parent, @NonNull Context context) {
            if (mMessage != null && mMessage.length() != 0) {
                SpanTouchFixTextView tv = new SpanTouchFixTextView(context);
                assignMessageTvWithAttr(tv, hasTitle(), R.attr.dialog_message_content_style);
                tv.setText(mMessage);
                tv.setMovementMethodDefault();

                SkinValueBuilder valueBuilder = SkinValueBuilder.acquire();
                valueBuilder.textColor(R.attr.skin_support_dialog_message_text_color);
                valueBuilder.textColor(Color.BLACK);
                SkinHelper.setSkinValue(tv, valueBuilder);
                SkinValueBuilder.release(valueBuilder);

                return wrapWithScroll(tv);
            }
            return null;
        }

        @Nullable
        @Override
        protected View onCreateTitle(@NonNull Dialog dialog, @NonNull DialogView parent, @NonNull Context context) {
            View tv = super.onCreateTitle(dialog, parent, context);
            if (tv != null && (mMessage == null || mMessage.length() == 0)) {
                TypedArray a = context.obtainStyledAttributes(null, R.styleable.DialogTitleTvCustomDef, R.attr.dialog_title_style, 0);
                int count = a.getIndexCount();
                for (int i = 0; i < count; i++) {
                    int attr = a.getIndex(i);
                    if (attr == R.styleable.DialogTitleTvCustomDef_paddingBottomWhenNotContent) {
                        tv.setPadding(
                                tv.getPaddingLeft(),
                                tv.getPaddingTop(),
                                tv.getPaddingRight(),
                                a.getDimensionPixelSize(attr, tv.getPaddingBottom())
                        );
                    }
                }
                a.recycle();
            }
            return tv;
        }

        public static void assignMessageTvWithAttr(TextView messageTv, boolean hasTitle, int defAttr) {
            ResHelper.assignTextViewWithAttr(messageTv, defAttr);
            if (!hasTitle) {
                TypedArray a = messageTv.getContext().obtainStyledAttributes(null, R.styleable.DialogMessageTvCustomDef, defAttr, 0);
                int count = a.getIndexCount();
                for (int i = 0; i < count; i++) {
                    int attr = a.getIndex(i);
                    if (attr == R.styleable.DialogMessageTvCustomDef_paddingTopWhenNotTitle) {
                        messageTv.setPadding(
                                messageTv.getPaddingLeft(),
                                a.getDimensionPixelSize(attr, messageTv.getPaddingTop()),
                                messageTv.getPaddingRight(),
                                messageTv.getPaddingBottom()
                        );
                    }
                }
                a.recycle();
            }
        }
    }

    /**
     * 带 CheckBox 的消息确认框 Builder
     */
    public static class CheckBoxMessageDialogBuilder extends DialogBuilder<CheckBoxMessageDialogBuilder> {
        protected String mMessage;
        private boolean mIsChecked = false;
        private SpanTouchFixTextView mTextView;

        public CheckBoxMessageDialogBuilder(Context context) {
            super(context);

        }

        /**
         * 设置对话框的消息文本
         */
        public CheckBoxMessageDialogBuilder setMessage(String message) {
            this.mMessage = message;
            return this;
        }

        /**
         * 设置对话框的消息文本
         */
        public CheckBoxMessageDialogBuilder setMessage(int resid) {
            return setMessage(getBaseContext().getResources().getString(resid));
        }

        /**
         * CheckBox 是否处于勾选状态
         */
        public boolean isChecked() {
            return mIsChecked;
        }

        /**
         * 设置 CheckBox 的勾选状态
         */
        public CheckBoxMessageDialogBuilder setChecked(boolean checked) {
            if (mIsChecked != checked) {
                mIsChecked = checked;
                if (mTextView != null) {
                    mTextView.setSelected(checked);
                }
            }
            return this;
        }

        @Nullable
        @Override
        protected View onCreateContent(Dialog dialog, DialogView parent, Context context) {
            if (mMessage != null && mMessage.length() != 0) {
                mTextView = new SpanTouchFixTextView(context);
                mTextView.setMovementMethodDefault();
                MessageDialogBuilder.assignMessageTvWithAttr(mTextView, hasTitle(), R.attr.dialog_message_content_style);
                mTextView.setText(mMessage);
                Drawable drawable = SkinHelper.getSkinDrawable(mTextView, R.attr.skin_support_s_dialog_check_drawable);
                if (drawable != null) {
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),  drawable.getIntrinsicHeight());
                    mTextView.setCompoundDrawables(drawable, null, null, null);
                }
                SkinValueBuilder valueBuilder = SkinValueBuilder.acquire();
                valueBuilder.textColor(R.attr.skin_support_dialog_message_text_color);
                valueBuilder.textCompoundLeftSrc(R.attr.skin_support_s_dialog_check_drawable);
                SkinHelper.setSkinValue(mTextView, valueBuilder);
                SkinValueBuilder.release(valueBuilder);
                mTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setChecked(!mIsChecked);
                    }
                });
                mTextView.setSelected(mIsChecked);
                return wrapWithScroll(mTextView);
            }
            return null;
        }

        @Deprecated
        public SpanTouchFixTextView getTextView() {
            return mTextView;
        }

    }

    /**
     * 带输入框的对话框 Builder
     */
    public static class EditTextDialogBuilder extends DialogBuilder<EditTextDialogBuilder> {
        protected String mPlaceholder;
        protected TransformationMethod mTransformationMethod;
        protected EditText mEditText;
        protected AppCompatImageView mRightImageView;
        private int mInputType = InputType.TYPE_CLASS_TEXT;
        private CharSequence mDefaultText = null;
        private TextWatcher mTextWatcher;

        public EditTextDialogBuilder(Context context) {
            super(context);
        }

        /**
         * 设置输入框的 placeholder
         */
        public EditTextDialogBuilder setPlaceholder(String placeholder) {
            this.mPlaceholder = placeholder;
            return this;
        }

        /**
         * 设置输入框的 placeholder
         */
        public EditTextDialogBuilder setPlaceholder(int resId) {
            return setPlaceholder(getBaseContext().getResources().getString(resId));
        }

        public EditTextDialogBuilder setDefaultText(CharSequence defaultText) {
            mDefaultText = defaultText;
            return this;
        }

        /**
         * 设置 EditText 的 transformationMethod
         */
        public EditTextDialogBuilder setTransformationMethod(TransformationMethod transformationMethod) {
            mTransformationMethod = transformationMethod;
            return this;
        }

        /**
         * 设置 EditText 的 inputType
         */
        public EditTextDialogBuilder setInputType(int inputType) {
            mInputType = inputType;
            return this;
        }

        public EditTextDialogBuilder setTextWatcher(TextWatcher textWatcher) {
            mTextWatcher = textWatcher;
            return this;
        }

        @Override
        protected ConstraintLayout.LayoutParams onCreateContentLayoutParams(Context context) {
            ConstraintLayout.LayoutParams lp = super.onCreateContentLayoutParams(context);
            int marginHor = ResHelper.getAttrDimen(context, R.attr.dialog_padding_horizontal);
            lp.leftMargin = marginHor;
            lp.rightMargin = marginHor;
            lp.topMargin = ResHelper.getAttrDimen(context, R.attr.dialog_edit_margin_top);
            lp.bottomMargin = ResHelper.getAttrDimen(context, R.attr.dialog_edit_margin_bottom);
            return lp;
        }

        @Nullable
        @Override
        protected View onCreateContent(Dialog dialog, DialogView parent, Context context) {
            ConstraintLayout boxLayout = new ConstraintLayout(context);
//            boxLayout.onlyShowBottomDivider(0, 0,
//                    ResHelper.getAttrDimen(context,
//                            R.attr.dialog_edit_bottom_line_height),
//                    ResHelper.getAttrColor(context,
//                            R.attr.skin_support_dialog_edit_bottom_line_color));
            SkinValueBuilder builder = SkinValueBuilder.acquire();
            builder.bottomSeparator(R.attr.skin_support_dialog_edit_bottom_line_color);
            SkinHelper.setSkinValue(boxLayout, builder);

            mEditText = new AppCompatEditText(context);
            mEditText.setBackgroundResource(0);
            MessageDialogBuilder.assignMessageTvWithAttr(mEditText, hasTitle(), R.attr.dialog_edit_content_style);
            mEditText.setFocusable(true);
            mEditText.setFocusableInTouchMode(true);
            mEditText.setImeOptions(EditorInfo.IME_ACTION_GO);
            mEditText.setId(R.id.dialog_edit_input);

            if (!LangHelper.isNullOrEmpty(mDefaultText)) {
                mEditText.setText(mDefaultText);
            }
            if (mTextWatcher != null) {
                mEditText.addTextChangedListener(mTextWatcher);
            }
            builder.clear();
            builder.textColor(R.attr.skin_support_dialog_edit_text_color);
            builder.hintColor(R.attr.skin_support_dialog_edit_text_hint_color);
            SkinHelper.setSkinValue(mEditText, builder);
            SkinValueBuilder.release(builder);


            mRightImageView = new AppCompatImageView(context);
            mRightImageView.setId(R.id.dialog_edit_right_icon);
            mRightImageView.setVisibility(View.GONE);
            configRightImageView(mRightImageView, mEditText);

            if (mTransformationMethod != null) {
                mEditText.setTransformationMethod(mTransformationMethod);
            } else {
                mEditText.setInputType(mInputType);
            }

            if (mPlaceholder != null) {
                mEditText.setHint(mPlaceholder);
            }
            boxLayout.addView(mEditText, createEditTextLayoutParams(context));
            boxLayout.addView(mRightImageView, createRightIconLayoutParams(context));

            return boxLayout;
        }

        protected void configRightImageView(AppCompatImageView imageView, EditText editText) {

        }

        protected ConstraintLayout.LayoutParams createEditTextLayoutParams(Context context) {
            ConstraintLayout.LayoutParams editLp = new ConstraintLayout.LayoutParams(
                    0, ViewGroup.LayoutParams.WRAP_CONTENT);
            editLp.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID;
            editLp.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
            editLp.rightToLeft = R.id.dialog_edit_right_icon;
            editLp.rightToRight = DisplayHelper.dp2px(context, 5);
            editLp.goneRightMargin = 0;
            return editLp;
        }

        protected ConstraintLayout.LayoutParams createRightIconLayoutParams(Context context) {
            ConstraintLayout.LayoutParams rightIconLp = new ConstraintLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            rightIconLp.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID;
            rightIconLp.bottomToBottom = R.id.dialog_edit_input;
            return rightIconLp;
        }

        @Override
        protected void onAfterCreate(Dialog dialog, DialogRootLayout rootLayout, Context context) {
            super.onAfterCreate(dialog, rootLayout, context);
            final InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    inputMethodManager.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
                }
            });
            mEditText.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mEditText.requestFocus();
                    inputMethodManager.showSoftInput(mEditText, 0);
                }
            }, 300);
        }

        /**
         * 注意该方法只在调用 {@link #create()} 或 {@link #create(int)} 或 {@link #show()} 生成 Dialog 之后
         * 才能返回对应的 EditText，在此之前将返回 null
         */
        @Deprecated
        public EditText getEditText() {
            return mEditText;
        }

        public ImageView getRightImageView() {
            return mRightImageView;
        }
    }


    public static class MenuBaseDialogBuilder<T extends DialogBuilder> extends DialogBuilder<T> {
        protected ArrayList<ItemViewFactory> mMenuItemViewsFactoryList;
        protected ArrayList<DialogMenuItemView> mMenuItemViews = new ArrayList<>();

        public MenuBaseDialogBuilder(Context context) {
            super(context);
            mMenuItemViewsFactoryList = new ArrayList<>();
        }

        public void clear() {
            mMenuItemViewsFactoryList.clear();
        }

        @SuppressWarnings("unchecked")
        @Deprecated
        public T addItem(final DialogMenuItemView itemView, final DialogInterface.OnClickListener listener) {
            itemView.setMenuIndex(mMenuItemViewsFactoryList.size());
            itemView.setListener(new DialogMenuItemView.MenuItemViewListener() {
                @Override
                public void onClick(int index) {
                    onItemClick(index);
                    if (listener != null) {
                        listener.onClick(mDialog, index);
                    }
                }
            });
            mMenuItemViewsFactoryList.add(new ItemViewFactory() {
                @Override
                public DialogMenuItemView createItemView(Context context) {
                    return itemView;
                }
            });
            return (T) this;
        }

        public T addItem(final ItemViewFactory itemViewFactory, final DialogInterface.OnClickListener listener) {
            mMenuItemViewsFactoryList.add(new ItemViewFactory() {
                @Override
                public DialogMenuItemView createItemView(Context context) {
                    DialogMenuItemView itemView = itemViewFactory.createItemView(context);
                    itemView.setMenuIndex(mMenuItemViewsFactoryList.indexOf(this));
                    itemView.setListener(new DialogMenuItemView.MenuItemViewListener() {
                        @Override
                        public void onClick(int index) {
                            onItemClick(index);
                            if (listener != null) {
                                listener.onClick(mDialog, index);
                            }
                        }
                    });
                    return itemView;
                }
            });
            return (T) this;
        }

        protected void onItemClick(int index) {

        }

        @Nullable
        @Override
        protected View onCreateContent(Dialog dialog, DialogView parent, Context context) {
            LinearLayout layout = new LinearLayout(context);
            layout.setOrientation(LinearLayout.VERTICAL);


            TypedArray a = context.obtainStyledAttributes(
                    null, R.styleable.DialogMenuContainerStyleDef, R.attr.dialog_menu_container_style, 0);
            int count = a.getIndexCount();
            int paddingTop = 0, paddingBottom = 0, paddingVerWhenSingle = 0,
                    paddingTopWhenTitle = 0, paddingBottomWhenAction = 0, itemHeight = -1;
            for (int i = 0; i < count; i++) {
                int attr = a.getIndex(i);
                if (attr == R.styleable.DialogMenuContainerStyleDef_android_paddingTop) {
                    paddingTop = a.getDimensionPixelSize(attr, paddingTop);
                } else if (attr == R.styleable.DialogMenuContainerStyleDef_android_paddingBottom) {
                    paddingBottom = a.getDimensionPixelSize(attr, paddingBottom);
                } else if (attr == R.styleable.DialogMenuContainerStyleDef_dialog_menu_container_single_padding_vertical) {
                    paddingVerWhenSingle = a.getDimensionPixelSize(attr, paddingVerWhenSingle);
                } else if (attr == R.styleable.DialogMenuContainerStyleDef_dialog_menu_container_padding_top_when_title_exist) {
                    paddingTopWhenTitle = a.getDimensionPixelSize(attr, paddingTopWhenTitle);
                } else if (attr == R.styleable.DialogMenuContainerStyleDef_dialog_menu_container_padding_bottom_when_action_exist) {
                    paddingBottomWhenAction = a.getDimensionPixelSize(attr, paddingBottomWhenAction);
                } else if (attr == R.styleable.DialogMenuContainerStyleDef_dialog_menu_item_height) {
                    itemHeight = a.getDimensionPixelSize(attr, itemHeight);
                }
            }
            a.recycle();

            if (mMenuItemViewsFactoryList.size() == 1) {
                paddingBottom = paddingTop = paddingVerWhenSingle;
            }

            if (hasTitle()) {
                paddingTop = paddingTopWhenTitle;
            }

            if (mActions.size() > 0) {
                paddingBottom = paddingBottomWhenAction;
            }

            layout.setPadding(0, paddingTop, 0, paddingBottom);

            LinearLayout.LayoutParams itemLp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, itemHeight);
            itemLp.gravity = Gravity.CENTER_VERTICAL;


            mMenuItemViews.clear();
            for (ItemViewFactory factory : mMenuItemViewsFactoryList) {
                DialogMenuItemView itemView = factory.createItemView(context);
                layout.addView(itemView, itemLp);
                mMenuItemViews.add(itemView);
            }
            return wrapWithScroll(layout);
        }

        public interface ItemViewFactory {
            DialogMenuItemView createItemView(Context context);
        }
    }

    /**
     * 菜单类型的对话框 Builder
     */
    public static class MenuDialogBuilder extends MenuBaseDialogBuilder<MenuDialogBuilder> {

        public MenuDialogBuilder(Context context) {
            super(context);
        }

        /**
         * 添加多个菜单项
         *
         * @param items    所有菜单项的文字
         * @param listener 菜单项的点击事件
         */
        public MenuDialogBuilder addItems(CharSequence[] items, DialogInterface.OnClickListener listener) {
            for (final CharSequence item : items) {
                addItem(item, listener);
            }
            return this;
        }

        /**
         * 添加单个菜单项
         *
         * @param item     菜单项的文字
         * @param listener 菜单项的点击事件
         */
        public MenuDialogBuilder addItem(final CharSequence item, DialogInterface.OnClickListener listener) {
            addItem(new ItemViewFactory() {
                @Override
                public DialogMenuItemView createItemView(Context context) {
                    return new DialogMenuItemView.TextItemView(context, item);
                }
            }, listener);
            return this;
        }

    }

    /**
     * 单选类型的对话框 Builder
     */
    public static class CheckableDialogBuilder extends MenuBaseDialogBuilder<CheckableDialogBuilder> {

        /**
         * 当前被选中的菜单项的下标, 负数表示没选中任何项
         */
        private int mCheckedIndex = -1;

        public CheckableDialogBuilder(Context context) {
            super(context);
        }

        /**
         * 获取当前选中的菜单项的下标
         *
         * @return 负数表示没选中任何项
         */
        public int getCheckedIndex() {
            return mCheckedIndex;
        }

        /**
         * 设置选中的菜单项的下班
         */
        public CheckableDialogBuilder setCheckedIndex(int checkedIndex) {
            mCheckedIndex = checkedIndex;
            return this;
        }

        @Nullable
        @Override
        protected View onCreateContent(Dialog dialog, DialogView parent, Context context) {
            View result = super.onCreateContent(dialog, parent, context);
            if (mCheckedIndex > -1 && mCheckedIndex < mMenuItemViews.size()) {
                mMenuItemViews.get(mCheckedIndex).setChecked(true);
            }
            return result;
        }

        @Override
        protected void onItemClick(int index) {
            for (int i = 0; i < mMenuItemViews.size(); i++) {
                DialogMenuItemView itemView = mMenuItemViews.get(i);
                if (i == index) {
                    itemView.setChecked(true);
                    mCheckedIndex = index;
                } else {
                    itemView.setChecked(false);
                }
            }
        }

        /**
         * 添加菜单项
         *
         * @param items    所有菜单项的文字
         * @param listener 菜单项的点击事件,可以在点击事件里调用 {@link #setCheckedIndex(int)} 来设置选中某些菜单项
         */
        public CheckableDialogBuilder addItems(CharSequence[] items, DialogInterface.OnClickListener listener) {
            for (final CharSequence item : items) {
                addItem(new ItemViewFactory() {
                    @Override
                    public DialogMenuItemView createItemView(Context context) {
                        return new DialogMenuItemView.MarkItemView(context, item);
                    }
                }, listener);
            }
            return this;
        }
    }

    /**
     * 多选类型的对话框 Builder
     */
    public static class MultiCheckableDialogBuilder extends MenuBaseDialogBuilder<MultiCheckableDialogBuilder> {

        /**
         * 该 int 的每一位标识菜单的每一项是否被选中 (1为选中,0位不选中)
         */
        private BitSet mCheckedItems = new BitSet();

        public MultiCheckableDialogBuilder(Context context) {
            super(context);
        }

        /**
         * 设置被选中的菜单项的下标
         *
         * @param checkedItems <b>注意: 该 int 参数的每一位标识菜单项的每一项是否被选中</b>
         *                     <p>如 20 表示选中下标为 1、3 的菜单项, 因为 (2<<1) + (2<<3) = 20</p>
         */
        public MultiCheckableDialogBuilder setCheckedItems(BitSet checkedItems) {
            mCheckedItems.clear();
            mCheckedItems.or(checkedItems);
            return this;
        }

        /**
         * 设置被选中的菜单项的下标
         *
         * @param checkedIndexes 被选中的菜单项的下标组成的数组,如 [1,3] 表示选中下标为 1、3 的菜单项
         */
        public MultiCheckableDialogBuilder setCheckedItems(int[] checkedIndexes) {
            mCheckedItems.clear();
            if (checkedIndexes != null && checkedIndexes.length > 0) {
                for (int checkedIndex : checkedIndexes) {
                    mCheckedItems.set(checkedIndex);
                }
            }
            return this;
        }

        /**
         * 添加菜单项
         *
         * @param items    所有菜单项的文字
         * @param listener 菜单项的点击事件,可以在点击事件里调用 {@link #setCheckedItems(int[])}} 来设置选中某些菜单项
         */
        public MultiCheckableDialogBuilder addItems(CharSequence[] items, DialogInterface.OnClickListener listener) {
            for (final CharSequence item : items) {
                addItem(new ItemViewFactory() {
                    @Override
                    public DialogMenuItemView createItemView(Context context) {
                        return new DialogMenuItemView.CheckItemView(context, true, item);
                    }
                }, listener);
            }
            return this;
        }

        @Nullable
        @Override
        protected View onCreateContent(Dialog dialog, DialogView parent, Context context) {
            View result = super.onCreateContent(dialog, parent, context);
            for (int i = 0; i < mMenuItemViews.size(); i++) {
                DialogMenuItemView itemView = mMenuItemViews.get(i);
                itemView.setChecked(mCheckedItems.get(i));
            }
            return result;
        }

        @Override
        protected void onItemClick(int index) {
            DialogMenuItemView itemView = mMenuItemViews.get(index);
            itemView.setChecked(!itemView.isChecked());
            mCheckedItems.set(index, itemView.isChecked());
        }

        /**
         * @return 被选中的菜单项的下标 <b>注意: 如果选中的是1，3项(以0开始)，因为 (2<<1) + (2<<3) = 20</b>
         */
        public BitSet getCheckedItemRecord() {
            return (BitSet) mCheckedItems.clone();
        }

        /**
         * @return 被选中的菜单项的下标数组。如果选中的是1，3项(以0开始)，则返回[1,3]
         */
        public int[] getCheckedItemIndexes() {
            ArrayList<Integer> array = new ArrayList<>();
            int length = mMenuItemViews.size();

            for (int i = 0; i < length; i++) {
                DialogMenuItemView itemView = mMenuItemViews.get(i);
                if (itemView.isChecked()) {
                    array.add(itemView.getMenuIndex());
                }
            }
            int[] output = new int[array.size()];
            for (int i = 0; i < array.size(); i++) {
                output[i] = array.get(i);
            }
            return output;
        }

        protected boolean existCheckedItem() {
            return !mCheckedItems.isEmpty();
        }
    }

    /**
     * 自定义对话框内容区域的 Builder
     */
    public static class CustomDialogBuilder extends DialogBuilder {

        private int mLayoutId;

        public CustomDialogBuilder(Context context) {
            super(context);
        }

        /**
         * 设置内容区域的 layoutResId
         */
        public CustomDialogBuilder setLayout(@LayoutRes int layoutResId) {
            mLayoutId = layoutResId;
            return this;
        }

        @Nullable
        @Override
        protected View onCreateContent(Dialog dialog, DialogView parent, Context context) {
            return LayoutInflater.from(context).inflate(mLayoutId, parent, false);
        }
    }

    /**
     * 随键盘升降自动调整 Dialog 高度的 Builder
     */
    public static abstract class AutoResizeDialogBuilder extends DialogBuilder {

        protected ScrollView mScrollView;

        public AutoResizeDialogBuilder(Context context) {
            super(context);
            setCheckKeyboardOverlay(true);
        }

        @Nullable
        @Override
        protected View onCreateContent(Dialog dialog, DialogView parent, Context context) {
            mScrollView = wrapWithScroll(onBuildContent(dialog));
            return mScrollView;
        }

        public abstract View onBuildContent(Dialog dialog);
    }
}
