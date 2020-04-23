package com.example.xiaojin20135.basemodule.view.widget.bottom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.example.xiaojin20135.basemodule.R;
import com.example.xiaojin20135.basemodule.util.ui.ResHelper;

public class MyBottomSheetListItemDecoration  extends RecyclerView.ItemDecoration {

    private final Paint mSeparatorPaint;
    private final int mSeparatorAttr;

    public MyBottomSheetListItemDecoration(Context context) {
        mSeparatorPaint = new Paint();
        mSeparatorPaint.setStrokeWidth(1);
        mSeparatorPaint.setStyle(Paint.Style.STROKE);
        mSeparatorAttr = R.attr.skin_support_bottom_sheet_separator_color;
        if (mSeparatorAttr != 0) {
            mSeparatorPaint.setColor(ResHelper.getAttrColor(context, mSeparatorAttr));
        }
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        RecyclerView.Adapter adapter = parent.getAdapter();
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (adapter == null || layoutManager == null || mSeparatorAttr == 0) {
            return;
        }
        for (int i = 0; i < parent.getChildCount(); i++) {
            View view = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(view);
            if (view instanceof BottomSheetListItemView) {
                if (position > 0 &&
                        adapter.getItemViewType(position - 1) != BottomSheetListAdapter.ITEM_TYPE_NORMAL) {
                    int top = layoutManager.getDecoratedTop(view);
                    c.drawLine(0, top, parent.getWidth(), top, mSeparatorPaint);
                }
                if (position + 1 < adapter.getItemCount() &&
                        adapter.getItemViewType(position + 1) == BottomSheetListAdapter.ITEM_TYPE_NORMAL) {
                    int bottom = layoutManager.getDecoratedBottom(view);
                    c.drawLine(0, bottom, parent.getWidth(), bottom, mSeparatorPaint);
                }
            }
        }
    }

}
