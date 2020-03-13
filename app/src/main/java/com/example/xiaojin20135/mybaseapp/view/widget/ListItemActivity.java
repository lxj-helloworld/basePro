package com.example.xiaojin20135.mybaseapp.view.widget;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;

import com.example.xiaojin20135.basemodule.activity.ToolBarActivity;
import com.example.xiaojin20135.basemodule.util.ui.DisplayHelper;
import com.example.xiaojin20135.basemodule.view.widget.listitem.CommonListItemView;
import com.example.xiaojin20135.basemodule.view.widget.listitem.GroupListView;
import com.example.xiaojin20135.mybaseapp.R;

public class ListItemActivity extends ToolBarActivity {
    private GroupListView mGroupListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_list_item;
    }

    @Override
    protected void initView() {
        mGroupListView = findViewById(R.id.groupListView);

        CommonListItemView normalItem = mGroupListView.createItemView(
                ContextCompat.getDrawable(ListItemActivity.this, R.mipmap.ic_launcher_round),
                "Item 1",
                null,
                CommonListItemView.HORIZONTAL,
                CommonListItemView.ACCESSORY_TYPE_NONE);
        normalItem.setOrientation(CommonListItemView.VERTICAL);

        int size = DisplayHelper.dp2px(ListItemActivity.this, 20);
        GroupListView.newSection(ListItemActivity.this)
                .setTitle("Section 1: 默认提供的样式")
                .setDescription("Section 1 的描述")
                .setLeftIconSize(size, ViewGroup.LayoutParams.WRAP_CONTENT)
                .addItemView(normalItem, null)
                .addTo(mGroupListView);
    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void loadData() {

    }
}
