package com.example.xiaojin20135.basemodule.view.widget.bottom;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

/*
* @author lixiaojin
* create on 2020-04-10 08:59
* description: List item 适配器
*/
public class BottomSheetListAdapter extends RecyclerView.Adapter<BottomSheetListAdapter.VH> {
    private static final String TAG = "BottomSheetListAdapter";

    public static final int ITEM_TYPE_HEADER = 1;
    public static final int ITEM_TYPE_FOOTER = 2;
    public static final int ITEM_TYPE_NORMAL = 3;
    @Nullable
    private View mHeaderView;
    @Nullable
    private View mFooterView;
    private List<BottomSheetListItemModel> mData = new ArrayList<>();
    private final boolean mNeedMark;
    private final boolean mGravityCenter;
    private int mCheckedIndex = -1;
    private OnItemClickListener mOnItemClickListener;

    public BottomSheetListAdapter(boolean needMark, boolean gravityCenter){
        mNeedMark = needMark;
        mGravityCenter = gravityCenter;
    }

    public void setCheckedIndex(int checkedIndex) {
        mCheckedIndex = checkedIndex;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setData(@Nullable View headerView, @Nullable View footerView, List<BottomSheetListItemModel> data) {
        mHeaderView = headerView;
        mFooterView = footerView;
        mData.clear();
        if (data != null) {
            mData.addAll(data);
        }
        notifyDataSetChanged();
    }

    /*
    * @author lixiaojin
    * create on 2020-04-10 09:00
    * description: 视图类型
    */
    @Override
    public int getItemViewType(int position) {
        if(mHeaderView != null){ //头部试图
            if(position == 0){
                return ITEM_TYPE_HEADER;
            }
        }
        if(position == getItemCount() - 1){ //底部视图
            if(mFooterView != null){
                return ITEM_TYPE_FOOTER;
            }
        }
        return ITEM_TYPE_NORMAL; //中间普通视图
    }

    /*
    * @author lixiaojin
    * create on 2020-04-10 09:00
    * description: 创建具体视图，并添加点击事件
    */
    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == ITEM_TYPE_HEADER){
            return new VH(mHeaderView);
        }else if(viewType == ITEM_TYPE_FOOTER){
            return new VH(mFooterView);
        }
        final VH vh = new VH(new BottomSheetListItemView(parent.getContext(), mNeedMark, mGravityCenter));
        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemClickListener != null){
                    int adapterPosition = vh.getAdapterPosition();
                    int dataPos = mHeaderView != null ? adapterPosition - 1 : adapterPosition;
                    mOnItemClickListener.onClick(vh, dataPos, mData.get(dataPos));
                }
            }
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        if(holder.getItemViewType() != ITEM_TYPE_NORMAL){
            return;
        }
        if(mHeaderView != null){
            position--;
        }
        BottomSheetListItemModel itemModel = mData.get(position);
        BottomSheetListItemView itemView = (BottomSheetListItemView) holder.itemView;
        itemView.render(itemModel, position == mCheckedIndex);
    }

    @Override
    public int getItemCount() {
        Log.d(TAG,"in getItemCount. nData.size = " + mData.size());
        return mData.size() + (mHeaderView != null ? 1 : 0) + (mFooterView != null ? 1 : 0);
    }

    static class VH extends RecyclerView.ViewHolder {
        public VH(@NonNull View itemView) {
            super(itemView);
        }
    }

    interface OnItemClickListener {
        void onClick(VH vh, int dataPos, BottomSheetListItemModel model);
    }
}
