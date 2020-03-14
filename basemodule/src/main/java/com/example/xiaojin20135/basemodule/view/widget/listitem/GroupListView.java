package com.example.xiaojin20135.basemodule.view.widget.listitem;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.example.xiaojin20135.basemodule.R;
import com.example.xiaojin20135.basemodule.layout.UIConstraintLayout;
import com.example.xiaojin20135.basemodule.skin.SkinHelper;
import com.example.xiaojin20135.basemodule.skin.SkinValueBuilder;
import com.example.xiaojin20135.basemodule.util.ui.ResHelper;
import com.example.xiaojin20135.basemodule.util.ui.ViewHelper;

public class GroupListView extends LinearLayout {

    private SparseArray<Section> mSections;

    public GroupListView(Context context) {
        this(context, null);
    }

    public GroupListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GroupListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mSections = new SparseArray<>();
        setOrientation(LinearLayout.VERTICAL);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-13 15:27
    * description: 创建一个section并返回
    */
    public static Section newSection(Context context) {
        return new Section(context);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-13 15:29
    * description: 返回section的个数
    */
    public int getSectionCount() {
        return mSections.size();
    }

    /*
    * @author lixiaojin
    * create on 2020-03-13 15:30
    * description: 创建一个cell视图
    *  设置 布局方向、布局宽高、左侧图标、主信息、辅助信息以及右侧附加视图
    */
    public CommonListItemView createItemView(@Nullable Drawable imageDrawable, CharSequence titleText, String detailText, int orientation, int accessoryType, int height) {
        CommonListItemView itemView = new CommonListItemView(getContext());
        itemView.setOrientation(orientation);
        itemView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height));
        itemView.setImageDrawable(imageDrawable);
        itemView.setText(titleText);
        itemView.setDetailText(detailText);
        itemView.setAccessoryType(accessoryType);
        return itemView;
    }

    /*
    * @author lixiaojin
    * create on 2020-03-13 15:34
    * description: 根究布局方向设置不同的宽高
    */
    public CommonListItemView createItemView(@Nullable Drawable imageDrawable, CharSequence titleText, String detailText, int orientation, int accessoryType) {
        int height;
        if (orientation == CommonListItemView.VERTICAL) {
            height = ResHelper.getAttrDimen(getContext(), R.attr.list_item_height_higher);
            return createItemView(imageDrawable, titleText, detailText, orientation, accessoryType, height);
        } else {
            height = ResHelper.getAttrDimen(getContext(), R.attr.list_item_height);
            return createItemView(imageDrawable, titleText, detailText, orientation, accessoryType, height);
        }
    }

    /*
    * @author lixiaojin
    * create on 2020-03-13 15:34
    * description: 创建不带左侧图标以及辅助描述信息的cell
    */
    public CommonListItemView createItemView(CharSequence titleText) {
        return createItemView(null, titleText, null, CommonListItemView.HORIZONTAL, CommonListItemView.ACCESSORY_TYPE_NONE);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-13 15:36
    * description: 添加一个section，不涉及View更新
    */
    private void addSection(Section section) {
        mSections.append(mSections.size(), section);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-13 15:37
    * description: 移除一section
    */
    private void removeSection(Section section) {
        for (int i = 0; i < mSections.size(); i++) {
            Section each = mSections.valueAt(i);
            if (each == section) {
                mSections.remove(i);
            }
        }
    }

    /*
    * @author lixiaojin
    * create on 2020-03-13 15:38
    * description: 返回特定的section
    */
    public Section getSection(int index) {
        return mSections.get(index);
    }



    /*
    * @author lixiaojin
    * create on 2020-03-13 17:07
    * description: 每一组展示View，包括分组头、cell 视图和分组尾
    */
    public static class Section {
        private Context mContext; //当前上下文
        private GroupListSectionHeaderFooterView mTitleView; //分组头视图
        private GroupListSectionHeaderFooterView mDescriptionView; //分组为视图
        private SparseArray<CommonListItemView> mItemViews; //cellView数组
        private boolean mUseDefaultTitleIfNone; //使用默认的title
        private boolean mUseTitleViewForSectionSpace = true; //使用空格作为title
        private int mSeparatorColorAttr = R.attr.skin_support_common_list_separator_color; //分割线颜色
        private boolean mHandleSeparatorCustom = false; //使用自定义的分割线
        private boolean mShowSeparator = true; //显示分割线
        private boolean mOnlyShowStartEndSeparator = false; //
        private boolean mOnlyShowMiddleSeparator = false; //仅显示中间分割线
        private int mMiddleSeparatorInsetLeft = 0; //中间分割线左侧内边距
        private int mMiddleSeparatorInsetRight = 0; //中间分割线右侧内边距
        private int mBgAttr = R.attr.skin_support_s_common_list_bg; //背景色

        private int mLeftIconWidth = ViewGroup.LayoutParams.WRAP_CONTENT; //左侧图标宽度
        private int mLeftIconHeight = ViewGroup.LayoutParams.WRAP_CONTENT; //右侧图标宽度

        /*
        * @author lixiaojin
        * create on 2020-03-13 17:19
        * description: 构造函数，必要初始化
        */
        public Section(Context context) {
            mContext = context;
            mItemViews = new SparseArray<>();
        }

        /*
        * @author lixiaojin
        * create on 2020-03-13 17:19
        * description: 像分组中添加一个cellView以及对应的点击事件
        */
        public Section addItemView(CommonListItemView itemView, View.OnClickListener onClickListener) {
            return addItemView(itemView, onClickListener, null);
        }

       /*
       * @author lixiaojin
       * create on 2020-03-13 17:20
       * description: 像分组中添加一个cellView以及对应 的点击事件和长按事件
       */
        public Section addItemView(final CommonListItemView itemView, View.OnClickListener onClickListener, View.OnLongClickListener onLongClickListener) {
            //处理item cell点击事件
            if (onClickListener != null) {
                itemView.setOnClickListener(onClickListener);
            }
            //处理item cell 的长按事件
            if (onLongClickListener != null) {
                itemView.setOnLongClickListener(onLongClickListener);
            }
            //添加该cell，不涉及View更新
            mItemViews.append(mItemViews.size(), itemView);
            return this;
        }

        /*
        * @author lixiaojin
        * create on 2020-03-13 17:21
        * description: 设置section 的title
        */
        public Section setTitle(CharSequence title) {
            mTitleView = createSectionHeader(title);
            return this;
        }

        /*
        * @author lixiaojin
        * create on 2020-03-13 17:22
        * description: 设置section底部的文字
        */
        public Section setDescription(CharSequence description) {
            mDescriptionView = createSectionFooter(description);
            return this;
        }


        public Section setUseDefaultTitleIfNone(boolean useDefaultTitleIfNone) {
            mUseDefaultTitleIfNone = useDefaultTitleIfNone;
            return this;
        }

        public Section setUseTitleViewForSectionSpace(boolean useTitleViewForSectionSpace) {
            mUseTitleViewForSectionSpace = useTitleViewForSectionSpace;
            return this;
        }

        /*
        * @author lixiaojin
        * create on 2020-03-13 17:22
        * description: 设置左侧图标的大小
        */
        public Section setLeftIconSize(int width, int height) {
            mLeftIconHeight = height;
            mLeftIconWidth = width;
            return this;
        }

        public Section setSeparatorColorAttr(int attr) {
            mSeparatorColorAttr = attr;
            return this;
        }

        public Section setHandleSeparatorCustom(boolean handleSeparatorCustom) {
            mHandleSeparatorCustom = handleSeparatorCustom;
            return this;
        }

        public Section setShowSeparator(boolean showSeparator) {
            mShowSeparator = showSeparator;
            return this;
        }

        public Section setOnlyShowStartEndSeparator(boolean onlyShowStartEndSeparator) {
            mOnlyShowStartEndSeparator = onlyShowStartEndSeparator;
            return this;
        }

        public Section setOnlyShowMiddleSeparator(boolean onlyShowMiddleSeparator) {
            mOnlyShowMiddleSeparator = onlyShowMiddleSeparator;
            return this;
        }

        public Section setMiddleSeparatorInset(int insetLeft, int insetRight) {
            mMiddleSeparatorInsetLeft = insetLeft;
            mMiddleSeparatorInsetRight = insetRight;
            return this;
        }

        public Section setBgAttr(int bgAttr) {
            mBgAttr = bgAttr;
            return this;
        }


        /*
        * @author lixiaojin
        * create on 2020-03-13 17:23
        * description: 将section添加到listView上
        */
        public void addTo(GroupListView groupListView) {
            //判断titleView是否为空，如果为空，则判断是否使用默认的title或者使用空格代替
            if (mTitleView == null) {
                if (mUseDefaultTitleIfNone) {
                    setTitle("Section " + groupListView.getSectionCount());
                } else if (mUseTitleViewForSectionSpace) {
                    setTitle("");
                }
            }
            //如果headerView不为空，则将其添加到listView中
            if (mTitleView != null) {
                groupListView.addView(mTitleView);
            }

            //获取item cell的个数
            final int itemViewCount = mItemViews.size();
            //图标大小
            CommonListItemView.LayoutParamConfig leftIconLpConfig = new CommonListItemView.LayoutParamConfig() {
                @Override
                public UIConstraintLayout.LayoutParams onConfig(UIConstraintLayout.LayoutParams lp) {
                    lp.width = mLeftIconWidth;
                    lp.height = mLeftIconHeight;
                    return lp;
                }
            };
            //背景、顶部分割线以及底部分割线
            SkinValueBuilder builder = SkinValueBuilder.acquire();
            String skin = builder.background(mBgAttr)
                    .topSeparator(mSeparatorColorAttr)
                    .bottomSeparator(mSeparatorColorAttr)
                    .build();
            SkinValueBuilder.release(builder);
            int separatorColor = ResHelper.getAttrColor(groupListView.getContext(), mSeparatorColorAttr);
            //添加每一个item cell
            for (int i = 0; i < itemViewCount; i++) {
                CommonListItemView itemView = mItemViews.get(i);
                Drawable bg = SkinHelper.getSkinDrawable(groupListView, mBgAttr);
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
                    ViewHelper.setBackgroundKeepingPadding(itemView, bg == null ? null : bg.mutate());
                }
                SkinHelper.setSkinValue(itemView, skin);
                if (!mHandleSeparatorCustom && mShowSeparator) { //如果未自定义分割线并且显示分割线
                    if (itemViewCount == 1) { //如果只有一个item，只处理顶部和底部分割线
                        itemView.updateTopDivider(0, 0, 1, separatorColor);
                        itemView.updateBottomDivider(0, 0, 1, separatorColor);
                    } else if (i == 0) {
                        //如果没有限定只显示中间分割线，那么显示第一个item cell 的顶部分割线
                        if(!mOnlyShowMiddleSeparator){
                            itemView.updateTopDivider(0, 0, 1, separatorColor);
                        }

                        if (!mOnlyShowStartEndSeparator) {
                            itemView.updateBottomDivider(mMiddleSeparatorInsetLeft, mMiddleSeparatorInsetRight, 1, separatorColor);
                        }
                    } else if (i == itemViewCount - 1) { //如果是最后一个item cell
                        //如果未限定只显示中间分割线，显示最后一个item cell 的底部分割线
                        if(!mOnlyShowMiddleSeparator){
                            itemView.updateBottomDivider(0, 0, 1, separatorColor);
                        }
                    } else if (!mOnlyShowStartEndSeparator) {
                        itemView.updateBottomDivider(mMiddleSeparatorInsetLeft, mMiddleSeparatorInsetRight, 1, separatorColor);
                    }
                }
                itemView.updateImageViewLp(leftIconLpConfig);
                groupListView.addView(itemView);
            }

            if (mDescriptionView != null) {
                groupListView.addView(mDescriptionView);
            }
            //在视图更新完之后
            groupListView.addSection(this);
        }

        public void removeFrom(GroupListView parent) {
            if (mTitleView != null && mTitleView.getParent() == parent) {
                parent.removeView(mTitleView);
            }
            for (int i = 0; i < mItemViews.size(); i++) {
                CommonListItemView itemView = mItemViews.get(i);
                parent.removeView(itemView);
            }
            if (mDescriptionView != null && mDescriptionView.getParent() == parent) {
                parent.removeView(mDescriptionView);
            }
            parent.removeSection(this);
        }

        /**
         * 创建 Section Header，每个 Section 都会被创建一个 Header，有 title 时会显示 title，没有 title 时会利用 header 的上下 padding 充当 Section 分隔条
         */
        public GroupListSectionHeaderFooterView createSectionHeader(CharSequence titleText) {
            return new GroupListSectionHeaderFooterView(mContext, titleText);
        }

        /**
         * Section 的 Footer，形式与 Header 相似，都是显示一段文字
         */
        public GroupListSectionHeaderFooterView createSectionFooter(CharSequence text) {
            return new GroupListSectionHeaderFooterView(mContext, text, true);
        }
    }

}
