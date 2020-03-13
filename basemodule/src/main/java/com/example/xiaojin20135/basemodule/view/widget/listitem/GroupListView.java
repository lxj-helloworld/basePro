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

    /**
     * 创建一个 Section。
     * @return 返回新创建的 Section。
     */
    public static Section newSection(Context context) {
        return new Section(context);
    }


    public int getSectionCount() {
        return mSections.size();
    }

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

    public CommonListItemView createItemView(CharSequence titleText) {
        return createItemView(null, titleText, null, CommonListItemView.HORIZONTAL, CommonListItemView.ACCESSORY_TYPE_NONE);
    }

    public CommonListItemView createItemView(int orientation) {
        return createItemView(null, null, null, orientation, CommonListItemView.ACCESSORY_TYPE_NONE);
    }

    /**
     * private, use {@link Section#addTo(GroupListView)}
     * <p>这里只是把section记录到数组里面而已</p>
     */
    private void addSection(Section section) {
        mSections.append(mSections.size(), section);
    }

    /**
     * private，use {@link Section#removeFrom(GroupListView)}
     * <p>这里只是把section从记录的数组里移除而已</p>
     */
    private void removeSection(Section section) {
        for (int i = 0; i < mSections.size(); i++) {
            Section each = mSections.valueAt(i);
            if (each == section) {
                mSections.remove(i);
            }
        }
    }

    public Section getSection(int index) {
        return mSections.get(index);
    }


    /**
     * Section 是组成 {@link GroupListView} 的部分。
     * <ul>
     * <li>每个 Section 可以有多个 item, 通过 {@link #addItemView(CommonListItemView, View.OnClickListener)} 添加。</li>
     * <li>Section 还可以有自己的一个顶部 title 和一个底部 description, 通过 {@link #setTitle(CharSequence)} 和 {@link #setDescription(CharSequence)} 设置。</li>
     * </ul>
     */
    public static class Section {
        private Context mContext;
        private GroupListSectionHeaderFooterView mTitleView;
        private GroupListSectionHeaderFooterView mDescriptionView;
        private SparseArray<CommonListItemView> mItemViews;
        private boolean mUseDefaultTitleIfNone;
        private boolean mUseTitleViewForSectionSpace = true;
        private int mSeparatorColorAttr = R.attr.skin_support_common_list_separator_color;
        private boolean mHandleSeparatorCustom = false;
        private boolean mShowSeparator = true;
        private boolean mOnlyShowStartEndSeparator = false;
        private boolean mOnlyShowMiddleSeparator = false;
        private int mMiddleSeparatorInsetLeft = 0;
        private int mMiddleSeparatorInsetRight = 0;
        private int mBgAttr = R.attr.skin_support_s_common_list_bg;

        private int mLeftIconWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
        private int mLeftIconHeight = ViewGroup.LayoutParams.WRAP_CONTENT;

        public Section(Context context) {
            mContext = context;
            mItemViews = new SparseArray<>();
        }

        /**
         * 对 Section 添加一个 {@link CommonListItemView}
         *
         * @param itemView        要添加的 ItemView
         * @param onClickListener ItemView 的点击事件
         * @return Section 本身,支持链式调用
         */
        public Section addItemView(CommonListItemView itemView, View.OnClickListener onClickListener) {
            return addItemView(itemView, onClickListener, null);
        }

        /**
         * 对 Section 添加一个 {@link CommonListItemView}
         *
         * @param itemView            要添加的 ItemView
         * @param onClickListener     ItemView 的点击事件
         * @param onLongClickListener ItemView 的长按事件
         * @return Section 本身, 支持链式调用
         */
        public Section addItemView(final CommonListItemView itemView, View.OnClickListener onClickListener, View.OnLongClickListener onLongClickListener) {
            if (onClickListener != null) {
                itemView.setOnClickListener(onClickListener);
            }

            if (onLongClickListener != null) {
                itemView.setOnLongClickListener(onLongClickListener);
            }

            mItemViews.append(mItemViews.size(), itemView);
            return this;
        }

        /**
         * 设置 Section 的 title
         *
         * @return Section 本身, 支持链式调用
         */
        public Section setTitle(CharSequence title) {
            mTitleView = createSectionHeader(title);
            return this;
        }

        /**
         * 设置 Section 的 description
         *
         * @return Section 本身, 支持链式调用
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


        /**
         * 将 Section 添加到 {@link GroupListView} 上
         */
        public void addTo(GroupListView groupListView) {
            if (mTitleView == null) {
                if (mUseDefaultTitleIfNone) {
                    setTitle("Section " + groupListView.getSectionCount());
                } else if (mUseTitleViewForSectionSpace) {
                    setTitle("");
                }
            }
            if (mTitleView != null) {
                groupListView.addView(mTitleView);
            }


            final int itemViewCount = mItemViews.size();
            CommonListItemView.LayoutParamConfig leftIconLpConfig = new CommonListItemView.LayoutParamConfig() {
                @Override
                public UIConstraintLayout.LayoutParams onConfig(UIConstraintLayout.LayoutParams lp) {
                    lp.width = mLeftIconWidth;
                    lp.height = mLeftIconHeight;
                    return lp;
                }
            };
            SkinValueBuilder builder = SkinValueBuilder.acquire();
            String skin = builder.background(mBgAttr)
                    .topSeparator(mSeparatorColorAttr)
                    .bottomSeparator(mSeparatorColorAttr)
                    .build();
            SkinValueBuilder.release(builder);
            int separatorColor = ResHelper.getAttrColor(groupListView.getContext(), mSeparatorColorAttr);
            for (int i = 0; i < itemViewCount; i++) {
                CommonListItemView itemView = mItemViews.get(i);
                Drawable bg = SkinHelper.getSkinDrawable(groupListView, mBgAttr);
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
                    ViewHelper.setBackgroundKeepingPadding(itemView, bg == null ? null : bg.mutate());
                }
                SkinHelper.setSkinValue(itemView, skin);
                if (!mHandleSeparatorCustom && mShowSeparator) {
                    if (itemViewCount == 1) {
                        itemView.updateTopDivider(0, 0, 1, separatorColor);
                        itemView.updateBottomDivider(0, 0, 1, separatorColor);
                    } else if (i == 0) {
                        if(!mOnlyShowMiddleSeparator){
                            itemView.updateTopDivider(0, 0, 1, separatorColor);
                        }
                        if (!mOnlyShowStartEndSeparator) {
                            itemView.updateBottomDivider(
                                    mMiddleSeparatorInsetLeft, mMiddleSeparatorInsetRight, 1, separatorColor);
                        }
                    } else if (i == itemViewCount - 1) {
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
