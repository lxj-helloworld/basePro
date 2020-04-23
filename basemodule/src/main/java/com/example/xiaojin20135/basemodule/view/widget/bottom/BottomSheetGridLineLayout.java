package com.example.xiaojin20135.basemodule.view.widget.bottom;

import android.content.Context;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.example.xiaojin20135.basemodule.R;
import com.example.xiaojin20135.basemodule.util.LogUtils;
import com.example.xiaojin20135.basemodule.util.ui.ResHelper;

import java.util.List;

public class BottomSheetGridLineLayout extends LinearLayout {
    private static final String TAG = "BottomSheetGridLineLayout";

    private int maxItemCountInLines;
    private int miniItemWidth = -1;
    private List<Pair<View, LayoutParams>> mFirstLineViews;
    private List<Pair<View, LinearLayout.LayoutParams>> mSecondLineViews;
    private int linePaddingHor;
    private int itemWidth;

    public BottomSheetGridLineLayout(MyBottomSheet bottomSheet,
                                         List<Pair<View, LinearLayout.LayoutParams>> firstLineViews,
                                         List<Pair<View, LinearLayout.LayoutParams>> secondLineViews) {
        super(bottomSheet.getContext());
        setOrientation(VERTICAL); //方向
        setGravity(Gravity.TOP); //权重
        int paddingVer = ResHelper.getAttrDimen(bottomSheet.getContext(), R.attr.bottom_sheet_grid_padding_vertical);
        setPadding(0, paddingVer, 0, paddingVer); //垂直间距
        mFirstLineViews = firstLineViews;
        mSecondLineViews = secondLineViews;
        maxItemCountInLines = Math.max(firstLineViews != null ? firstLineViews.size() : 0, secondLineViews != null ? secondLineViews.size() : 0);
        //水平间距
        linePaddingHor = ResHelper.getAttrDimen(bottomSheet.getContext(), R.attr.bottom_sheet_padding_hor);

        boolean hasFirstLine = false;
        if (firstLineViews != null && !firstLineViews.isEmpty()) {
            LogUtils.d(TAG,"第一行。。。");
            hasFirstLine = true;
            HorizontalScrollView firstLine = createHorScroller(bottomSheet, firstLineViews);
            addView(firstLine, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        if (secondLineViews != null && !secondLineViews.isEmpty()) {
            LogUtils.d(TAG,"第二行。。。");
            HorizontalScrollView secondLine = createHorScroller(bottomSheet, secondLineViews);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (hasFirstLine) {
                lp.topMargin = ResHelper.getAttrDimen(bottomSheet.getContext(), R.attr.bottom_sheet_grid_line_vertical_space);
            }
            addView(secondLine, lp);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        itemWidth = calculateItemWidth(
                measureWidth, maxItemCountInLines, linePaddingHor, linePaddingHor);
        if (mFirstLineViews != null) {
            for (Pair<View, LinearLayout.LayoutParams> pair : mFirstLineViews) {
                if (pair.second.width != itemWidth) {
                    pair.second.width = itemWidth;
                }
            }
        }

        if (mSecondLineViews != null) {
            for (Pair<View, LinearLayout.LayoutParams> pair : mSecondLineViews) {
                if (pair.second.width != itemWidth) {
                    pair.second.width = itemWidth;
                }
            }
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /*
    * @author lixiaojin
    * create on 2020-04-09 15:45
    * description: 创建水平滚动器
    */
    protected HorizontalScrollView createHorScroller(MyBottomSheet bottomSheet, List<Pair<View, LinearLayout.LayoutParams>> itemViews) {
        LogUtils.d(TAG,"创建水平视图");
        Context context = bottomSheet.getContext();
        //水平滚动视图
        HorizontalScrollView scroller = new HorizontalScrollView(context);
        scroller.setHorizontalScrollBarEnabled(false);
        scroller.setClipToPadding(true);

        LinearLayout linear = new LinearLayout(context);
        linear.setOrientation(LinearLayout.HORIZONTAL);
        linear.setGravity(Gravity.CENTER_VERTICAL);
        linear.setPadding(linePaddingHor, 0, linePaddingHor, 0);
        scroller.addView(linear, new HorizontalScrollView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        for (int i = 0; i < itemViews.size(); i++) {
            Pair<View, LinearLayout.LayoutParams> pair = itemViews.get(i);
            linear.addView(pair.first, pair.second);
        }
        return scroller;
    }


    @Override
    protected void measureChild(View child, int parentWidthMeasureSpec, int parentHeightMeasureSpec) {
        super.measureChild(child, parentWidthMeasureSpec, parentHeightMeasureSpec);
    }


    private int calculateItemWidth(int width, int calculateCount, int paddingLeft, int paddingRight) {
        if (miniItemWidth == -1) {
            miniItemWidth = ResHelper.getAttrDimen(getContext(), R.attr.bottom_sheet_grid_item_mini_width);
        }

        final int parentSpacing = width - paddingLeft - paddingRight;
        int itemWidth = miniItemWidth;
        // there is no more space for the last one item. then stretch the item width
        if (calculateCount >= 3
                && parentSpacing - calculateCount * itemWidth > 0
                && parentSpacing - calculateCount * itemWidth < itemWidth) {
            int count = parentSpacing / itemWidth;
            itemWidth = parentSpacing / count;
        }
        // if there are more items. then show half of the first that is exceeded
        // to tell user that there are more.
        if (itemWidth * calculateCount > parentSpacing) {
            int count = (width - paddingLeft) / itemWidth;
            itemWidth = (int) ((width - paddingLeft) / (count + .5f));
        }
        return itemWidth;
    }
}
