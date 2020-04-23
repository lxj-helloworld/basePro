package com.example.xiaojin20135.basemodule.view.widget.bottom;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import com.example.xiaojin20135.basemodule.R;
import com.example.xiaojin20135.basemodule.util.LogUtils;
import com.example.xiaojin20135.basemodule.view.widget.dialog.BaseDialog;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import static com.example.xiaojin20135.basemodule.layout.LayoutInf.HIDE_RADIUS_SIDE_BOTTOM;

/*
* @author lixiaojin
* create on 2020-03-20 17:09
* description: BottomSheet对象
*/
public class MyBottomSheet extends BaseDialog {
    private static final String TAG = "MyBottomSheet";
    private BottomSheetRootLayout mRootView; //BottomSheet底部线性布局
    private OnBottomSheetShowListener mOnBottomSheetShowListener; //BottomSheet显示监听事件
    private MyBottomSheetBehavior<BottomSheetRootLayout> mBehavior; //BottomSheet行为
    private boolean mAnimateToCancel = false; //是否启用取消动画
    private boolean mAnimateToDismiss = false; //是否启用消失动画

    /*
    * @author lixiaojin
    * create on 2020-03-20 17:11
    * description: 基于默认样式初始化一个BottomSheet
    */
    public MyBottomSheet(Context context) {
        this(context, R.style.BottomSheet);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-20 17:12
    * description: 基于指定的样式初始化一个BottomSheet
    */
    public MyBottomSheet(Context context, int style) {
        super(context, style);
        //创建一个BottomSheet布局的容器
        ViewGroup container = (ViewGroup) View.inflate(context, R.layout.bottom_sheet_dialog, null);
        //创建一个BottomSheet布局
        mRootView = container.findViewById(R.id.bottom_sheet);
        //BottomSheet行为类
        mBehavior = new MyBottomSheetBehavior<>();
        mBehavior.setHideable(cancelable);
        mBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                LogUtils.d(TAG,"BottomSheet状态发生变化 newState = " + newState);
                LogUtils.d(TAG,"mAnimateToCancel = " + mAnimateToCancel);
                LogUtils.d(TAG,"mAnimateToDismiss = " + mAnimateToDismiss);
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    if (mAnimateToCancel) { //取消动画
                        // cancel() invoked
                        cancel();
                    } else if (mAnimateToDismiss) { //消失动画
                        // dismiss() invoked but it it not triggered by cancel()
                        dismiss();
                    } else { //取消
                        cancel();
                    }
                }
            }
            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                LogUtils.d(TAG,"BottomSheet滑动 slideOffset = " + slideOffset);
            }
        });
        mBehavior.setPeekHeight(0); //设置高度
        mBehavior.setAllowDrag(false); //是否允许拖拽
        mBehavior.setSkipCollapsed(true); //当被展开过之后，被隐藏时，跳过折叠状态
        CoordinatorLayout.LayoutParams rootViewLp = (CoordinatorLayout.LayoutParams) mRootView.getLayoutParams();
        rootViewLp.setBehavior(mBehavior);
        container.findViewById(R.id.touch_outside)
                .setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                LogUtils.d(TAG,"container 点击事件");
                                //STATE_SETTLING  视图从脱离手指到自由滑动到最终停下的这一小段时间
                                if(mBehavior.getState() == BottomSheetBehavior.STATE_SETTLING){
                                    return;
                                }
                                if (cancelable && isShowing() && shouldWindowCloseOnTouchOutside()) {
                                    cancel();
                                }
                            }
                        });
        mRootView.setOnTouchListener(
                new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent event) {
                        // 消耗事件，并阻止他向下传递
                        return true;
                    }
                });
        super.setContentView(container, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }


    /*
    * @author lixiaojin
    * create on 2020-03-28 09:52
    * description: 当视图拖拽下拉的时候，BottomSheet是否可以全部隐藏
    */
    @Override
    protected void onSetCancelable(boolean cancelable) {
        super.onSetCancelable(cancelable);
        mBehavior.setHideable(cancelable);
    }


    /*
    * @author lixiaojin
    * create on 2020-04-08 16:06
    * description:  创建
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        if (window != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            }
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        ViewCompat.requestApplyInsets(mRootView);
    }

    /*
    * @author lixiaojin
    * create on 2020-04-08 16:06
    * description: 启动
    */
    @Override
    protected void onStart() {
        super.onStart();
        LogUtils.d(TAG,"onStart mBehavior.getState() = " + mBehavior.getState());
        //STATE_HIDDEN 默认无此状态，启用后用户将能通过向下滑动完全隐藏BottomSheet
        if (mBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
            //STATTE_COLLAPSED 默认的折叠状态，BottomSheet只在底部显示一部分布局，显示高度可以通过app:behavior_peekHeight 设置，默认是0
            mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }
    
    /*
    * @author lixiaojin
    * create on 2020-04-08 16:07
    * description: 取消
    */
    @Override
    public void cancel() {
        LogUtils.d(TAG,"cancel mBehavior.getState() = " + mBehavior.getState());
        if (mBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
            mAnimateToCancel = false;
            super.cancel();
        } else {
            mAnimateToCancel = true;
            mBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        }
    }

    /*
    * @author lixiaojin
    * create on 2020-04-08 15:29
    * description: 销毁弹框
    */
    @Override
    public void dismiss() {
        Log.d(TAG,"on dismiss mBehavior.getState()  = " + mBehavior.getState() );
        if (mBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
            //如果BottomSheet处于隐藏状态
            mAnimateToDismiss = false;
            super.dismiss();
        } else {
            //如果BottomSheet未处于隐藏状态，将其设置为隐藏状态
            mAnimateToDismiss = true;
            mBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        }
    }

    /*
    * @author lixiaojin
    * create on 2020-03-28 10:05
    * description: 设置显示监听事件
    */
    public void setOnBottomSheetShowListener(OnBottomSheetShowListener onBottomSheetShowListener) {
        mOnBottomSheetShowListener = onBottomSheetShowListener;
    }

    /*
    * @author lixiaojin
    * create on 2020-03-28 10:05
    * description: 设置圆角半径
    */
    public void setRadius(int radius) {
        mRootView.setRadius(radius, HIDE_RADIUS_SIDE_BOTTOM);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-28 10:05
    * description: 返回根视图
    */
    public BottomSheetRootLayout getRootView() {
        return mRootView;
    }

    /*
    * @author lixiaojin
    * create on 2020-03-28 10:05
    * description: 返回BottomSheet行为对象
    */
    public MyBottomSheetBehavior<BottomSheetRootLayout> getBehavior() {
        return mBehavior;
    }

    /*
    * @author lixiaojin
    * create on 2020-03-28 10:06
    * description: 显示BottomSheet
    */
    @Override
    public void show() {
        super.show();
        //如果显示监听事件不为空，通知对应方法
        if (mOnBottomSheetShowListener != null) {
            LogUtils.d(TAG,"显示监听事件不为空！");
            mOnBottomSheetShowListener.onShow();
        }
        //如果BottomSheet未处于完全展开状态，将其设置为完全展开状态
        if (mBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
            mRootView.postOnAnimation(new Runnable() {
                @Override
                public void run() {
                    LogUtils.d(TAG,"显示后将 behavior设置为展开状态");
                    mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            });
        }
        //取消动画设置为false
        mAnimateToCancel = false;
        //销毁动画设置为false
        mAnimateToDismiss = false;
    }

    /*
    * @author lixiaojin
    * create on 2020-03-28 10:10
    * description: BottomSheet显示监听事件
    */
    public interface OnBottomSheetShowListener {
        void onShow();
    }

    @Override
    public void setContentView(View view) {
        throw new IllegalStateException("Use addContentView(View, ConstraintLayout.LayoutParams) for replacement");
    }

    @Override
    public void setContentView(int layoutResId) {
        throw new IllegalStateException("Use addContentView(int) for replacement");
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        throw new IllegalStateException("Use addContentView(View, LinearLayout.LayoutParams) for replacement");
    }

    @Override
    public void addContentView(View view, ViewGroup.LayoutParams params) {
        throw new IllegalStateException("Use addContentView(View, LinearLayout.LayoutParams) for replacement");
    }

    /*
    * @author lixiaojin
    * create on 2020-03-28 10:10
    * description: 增加子视图，附带自定义布局参数
    */
    public void addContentView(View view, LinearLayout.LayoutParams layoutParams) {
        mRootView.addView(view, layoutParams);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-28 10:11
    * description: 添加子视图，基于默认布局参数
    */
    public void addContentView(View view) {
        mRootView.addView(view, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }


    public void addContentView(int layoutResId) {
        LayoutInflater.from(mRootView.getContext()).inflate(layoutResId, mRootView, true);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-28 10:12
    * description: 列表类型BottomSheet
    */
    public static class BottomListSheetBuilder extends BottomSheetBaseBuilder<BottomListSheetBuilder> {
        private static final String TAG = "BottomListSheetBuilder";
        private List<BottomSheetListItemModel> mItems; //items
        private List<View> mContentHeaderViews; //顶部视图
        private List<View> mContentFooterViews; //底部视图
        private boolean mNeedRightMark; //是否需要在选中的Item右侧显示一个勾
        private int mCheckedIndex;
        private boolean mGravityCenter = false;
        private OnSheetItemClickListener mOnSheetItemClickListener; //item点击事件

        /*
        * @author lixiaojin
        * create on 2020-03-28 10:14
        * description: 构造函数
        */
        public BottomListSheetBuilder(Context context) {
            this(context, false);
        }

        /*
        * @author lixiaojin
        * create on 2020-03-28 10:15
        * description: 初始化，附带是否需要显示右侧勾符号
        */
        public BottomListSheetBuilder(Context context, boolean needRightMark) {
            super(context);
            mItems = new ArrayList<>();
            mNeedRightMark = needRightMark;
        }

        /**
         * 设置要被选中的 Item 的下标。
         * 注意:仅当 {@link #mNeedRightMark} 为 true 时才有效。
         */
        public BottomListSheetBuilder setCheckedIndex(int checkedIndex) {
            mCheckedIndex = checkedIndex;
            return this;
        }

        public BottomListSheetBuilder setNeedRightMark(boolean needRightMark) {
            mNeedRightMark = needRightMark;
            return this;
        }

        public BottomListSheetBuilder setGravityCenter(boolean gravityCenter) {
            mGravityCenter = gravityCenter;
            return this;
        }

        public BottomListSheetBuilder setOnSheetItemClickListener(OnSheetItemClickListener onSheetItemClickListener) {
            mOnSheetItemClickListener = onSheetItemClickListener;
            return this;
        }

        public BottomListSheetBuilder addItem(BottomSheetListItemModel itemModel) {
            mItems.add(itemModel);
            return this;
        }

        /**
         * @param textAndTag Item 的文字内容，同时会把内容设置为 tag。
         */
        public BottomListSheetBuilder addItem(String textAndTag) {
            mItems.add(new BottomSheetListItemModel(textAndTag, textAndTag));
            return this;
        }

        /**
         * @param image      icon Item 的 icon。
         * @param textAndTag Item 的文字内容，同时会把内容设置为 tag。
         */
        public BottomListSheetBuilder addItem(Drawable image, String textAndTag) {
            mItems.add(new BottomSheetListItemModel(textAndTag, textAndTag).image(image));
            return this;
        }

        /**
         * @param text Item 的文字内容。
         * @param tag  item 的 tag。
         */
        public BottomListSheetBuilder addItem(String text, String tag) {
            mItems.add(new BottomSheetListItemModel(text, tag));
            return this;
        }

        /**
         * @param imageRes Item 的图标 Resource。
         * @param text     Item 的文字内容。
         * @param tag      Item 的 tag。
         */
        public BottomListSheetBuilder addItem(int imageRes, String text, String tag) {
            mItems.add(new BottomSheetListItemModel(text, tag).image(imageRes));
            return this;
        }

        /**
         * @param imageRes    Item 的图标 Resource。
         * @param text        Item 的文字内容。
         * @param tag         Item 的 tag。
         * @param hasRedPoint 是否显示红点。
         */
        public BottomListSheetBuilder addItem(int imageRes, String text, String tag, boolean hasRedPoint) {
            mItems.add(new BottomSheetListItemModel(text, tag).image(imageRes).redPoint(hasRedPoint));
            return this;
        }

        /**
         * @param imageRes    Item 的图标 Resource。
         * @param text        Item 的文字内容。
         * @param tag         Item 的 tag。
         * @param hasRedPoint 是否显示红点。
         * @param disabled    是否显示禁用态。
         */
        public BottomListSheetBuilder addItem(int imageRes, CharSequence text, String tag, boolean hasRedPoint, boolean disabled) {
            mItems.add(new BottomSheetListItemModel(text, tag).image(imageRes).redPoint(hasRedPoint).disabled(disabled));
            return this;
        }

        @Deprecated
        public BottomListSheetBuilder addHeaderView(@NonNull View view) {
            return addContentHeaderView(view);
        }

        public BottomListSheetBuilder addContentHeaderView(@NonNull View view) {
            if (mContentHeaderViews == null) {
                mContentHeaderViews = new ArrayList<>();
            }
            mContentHeaderViews.add(view);
            return this;
        }

        public BottomListSheetBuilder addContentFooterView(@NonNull View view) {
            if (mContentFooterViews == null) {
                mContentFooterViews = new ArrayList<>();
            }
            mContentFooterViews.add(view);
            return this;
        }

        /*
        * @author lixiaojin
        * create on 2020-04-08 10:38
        * description: 列表类创建列表视图
        */
        @Nullable
        @Override
        protected View onCreateContentView(@NonNull final MyBottomSheet bottomSheet, @NonNull BottomSheetRootLayout rootLayout, @NonNull Context context) {
            RecyclerView recyclerView = new RecyclerView(context);
            BottomSheetListAdapter adapter = new BottomSheetListAdapter(mNeedRightMark, mGravityCenter);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(context) {
                @Override
                public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                    return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                }
            });
            recyclerView.addItemDecoration(new MyBottomSheetListItemDecoration(context));
            LinearLayout headerView = null;
            if (mContentHeaderViews != null && mContentHeaderViews.size() > 0) {
                headerView = new LinearLayout(context);
                headerView.setOrientation(LinearLayout.VERTICAL);
                for (View view : mContentHeaderViews) {
                    if (view.getParent() != null) {
                        ((ViewGroup) view.getParent()).removeView(view);
                    }
                    headerView.addView(view, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                }
            }
            LinearLayout footerView = null;
            if (mContentFooterViews != null && mContentHeaderViews.size() > 0) {
                footerView = new LinearLayout(context);
                footerView.setOrientation(LinearLayout.VERTICAL);
                for (View view : mContentFooterViews) {
                    if (view.getParent() != null) {
                        ((ViewGroup) view.getParent()).removeView(view);
                    }
                    footerView.addView(view, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                }
            }
            adapter.setData(headerView, footerView, mItems);
            adapter.setOnItemClickListener(new BottomSheetListAdapter.OnItemClickListener() {
                @Override
                public void onClick(BottomSheetListAdapter.VH vh, int dataPos, BottomSheetListItemModel model) {
                    LogUtils.d(TAG,"触发点击事件 mOnSheetItemClickListener = " + mOnSheetItemClickListener);
                    if (mOnSheetItemClickListener != null) {
                        mOnSheetItemClickListener.onClick(bottomSheet, vh.itemView, dataPos, model.tag);
                    }
                }
            });
            adapter.setCheckedIndex(mCheckedIndex);
            recyclerView.scrollToPosition(mCheckedIndex + (headerView == null ? 0 : 1));
            return recyclerView;
        }

        public interface OnSheetItemClickListener {
            void onClick(MyBottomSheet dialog, View itemView, int position, String tag);
        }
    }

    /**
     * 生成宫格类型的 {@link MyBottomSheet} 对话框。
     */
    public static class BottomGridSheetBuilder extends BottomSheetBaseBuilder<BottomGridSheetBuilder> implements View.OnClickListener {
        private static final String TAG = "BottomGridSheetBuilder";
        public static final int FIRST_LINE = 0;
        public static final int SECOND_LINE = 1;
        public static final ItemViewFactory DEFAULT_ITEM_VIEW_FACTORY = new DefaultItemViewFactory();

        public interface ItemViewFactory {
            BottomSheetGridItemView create(MyBottomSheet bottomSheet, BottomSheetGridItemModel model);
        }

        public static class DefaultItemViewFactory implements ItemViewFactory {
            @Override
            public BottomSheetGridItemView create(@NonNull MyBottomSheet bottomSheet, @NonNull BottomSheetGridItemModel model) {
                BottomSheetGridItemView itemView = new BottomSheetGridItemView(bottomSheet.getContext());
                itemView.render(model);
                return itemView;
            }
        }

        private ArrayList<BottomSheetGridItemModel> mFirstLineItems;
        private ArrayList<BottomSheetGridItemModel> mSecondLineItems;
        private ItemViewFactory mItemViewFactory = DEFAULT_ITEM_VIEW_FACTORY;
        private OnSheetItemClickListener mOnSheetItemClickListener;

        public BottomGridSheetBuilder(Context context) {
            super(context);
            mFirstLineItems = new ArrayList<>();
            mSecondLineItems = new ArrayList<>();
        }

        public BottomGridSheetBuilder addItem(@NonNull BottomSheetGridItemModel model, @Style int style) {
            LogUtils.d(TAG,"on addItem style = " + style);
            switch (style) {
                case FIRST_LINE:
                    mFirstLineItems.add(model);
                    break;
                case SECOND_LINE:
                    mSecondLineItems.add(model);
                    break;
            }
            return this;
        }

        public BottomGridSheetBuilder addItem(int imageRes, CharSequence textAndTag, @Style int style) {
            return addItem(imageRes, textAndTag, textAndTag, style, 0);
        }

        public BottomGridSheetBuilder addItem(int imageRes, CharSequence text, Object tag, @Style int style) {
            return addItem(imageRes, text, tag, style, 0);
        }

        public BottomGridSheetBuilder addItem(int imageRes, CharSequence text, Object tag, @Style int style, int subscriptRes) {
            return addItem(imageRes, text, tag, style, subscriptRes, null);
        }

        public BottomGridSheetBuilder addItem(int imageRes, CharSequence text, Object tag, @Style int style, int subscriptRes, Typeface typeface) {
            return addItem(new BottomSheetGridItemModel(text, tag)
                    .image(imageRes)
                    .subscript(subscriptRes)
                    .typeface(typeface), style);
        }

        public void setItemViewFactory(ItemViewFactory itemViewFactory) {
            mItemViewFactory = itemViewFactory;
        }

        public BottomGridSheetBuilder setOnSheetItemClickListener(OnSheetItemClickListener onSheetItemClickListener) {
            mOnSheetItemClickListener = onSheetItemClickListener;
            return this;
        }

        @Override
        public void onClick(View v) {
            if (mOnSheetItemClickListener != null) {
                mOnSheetItemClickListener.onClick(mDialog, v);
            }
        }

        @Nullable
        @Override
        protected View onCreateContentView(@NonNull MyBottomSheet bottomSheet,
                                           @NonNull BottomSheetRootLayout rootLayout,
                                           @NonNull Context context) {
            if (mFirstLineItems.isEmpty() && mSecondLineItems.isEmpty()) {
                return null;
            }
            List<Pair<View, LinearLayout.LayoutParams>> firstLines = null;
            List<Pair<View, LinearLayout.LayoutParams>> secondLines = null;
            int wrapContent = ViewGroup.LayoutParams.WRAP_CONTENT;

            if (!mFirstLineItems.isEmpty()) {
                firstLines = new ArrayList<>();
                for (BottomSheetGridItemModel model : mFirstLineItems) {
                    BottomSheetGridItemView itemView = mItemViewFactory.create(bottomSheet, model);
                    itemView.setOnClickListener(this);
                    firstLines.add(new Pair<View, LinearLayout.LayoutParams>(itemView, new LinearLayout.LayoutParams(wrapContent, wrapContent)));
                }
            }
            if (!mSecondLineItems.isEmpty()) {
                secondLines = new ArrayList<>();
                for (BottomSheetGridItemModel model : mSecondLineItems) {
                    BottomSheetGridItemView itemView = mItemViewFactory.create(bottomSheet, model);
                    itemView.setOnClickListener(this);
                    secondLines.add(new Pair<View, LinearLayout.LayoutParams>(itemView, new LinearLayout.LayoutParams(wrapContent, wrapContent)));
                }
            }
            return new BottomSheetGridLineLayout(mDialog, firstLines, secondLines);
        }

        public interface OnSheetItemClickListener {
            void onClick(MyBottomSheet dialog, View itemView);
        }

        @Retention(RetentionPolicy.SOURCE)
        @IntDef({FIRST_LINE, SECOND_LINE})
        public @interface Style {
        }
    }
}
