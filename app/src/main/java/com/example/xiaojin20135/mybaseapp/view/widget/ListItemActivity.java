package com.example.xiaojin20135.mybaseapp.view.widget;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

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

        //带图标以及文字
        CommonListItemView normalItem = mGroupListView.createItemView(
                ContextCompat.getDrawable(ListItemActivity.this, R.mipmap.ic_launcher_round),
                "这是内容标题 1",
                null,
                CommonListItemView.HORIZONTAL,
                CommonListItemView.ACCESSORY_TYPE_NONE);
        normalItem.setOrientation(CommonListItemView.VERTICAL);
        
//        //图标文字以及右侧详细信息
//        CommonListItemView itemWithDetail = mGroupListView.createItemView(
//                ContextCompat.getDrawable(ListItemActivity.this, R.mipmap.ic_launcher_round),
//                "这是内容标题 2",
//                null,
//                CommonListItemView.HORIZONTAL,
//                CommonListItemView.ACCESSORY_TYPE_NONE);
//        CommonListItemView.SkinConfig skinConfig = new CommonListItemView.SkinConfig();
//        skinConfig.iconTintColorRes = 0;
//        itemWithDetail.setSkinConfig(skinConfig);
//        itemWithDetail.setDetailText("在右方的详细信息");

        //文字以及下面的详细信息
        CommonListItemView itemWithDetailBelow = mGroupListView.createItemView(
                ContextCompat.getDrawable(ListItemActivity.this, R.mipmap.ic_launcher_round),
                "这是内容标题 3",
                null,
                CommonListItemView.HORIZONTAL,
                CommonListItemView.ACCESSORY_TYPE_NONE);
        itemWithDetailBelow.setOrientation(CommonListItemView.VERTICAL);
        itemWithDetailBelow.setDetailText("在标题下方的详细信息");



        CommonListItemView itemWithChevron = mGroupListView.createItemView(
                ContextCompat.getDrawable(ListItemActivity.this, R.mipmap.ic_launcher_round),
                "这是内容标题 4",
                null,
                CommonListItemView.HORIZONTAL,
                CommonListItemView.ACCESSORY_TYPE_NONE);
        itemWithChevron.setAccessoryType(CommonListItemView.ACCESSORY_TYPE_CHEVRON);

        CommonListItemView itemWithSwitch = mGroupListView.createItemView(
                ContextCompat.getDrawable(ListItemActivity.this, R.mipmap.ic_launcher_round),
                "这是内容标题 5",
                null,
                CommonListItemView.HORIZONTAL,
                CommonListItemView.ACCESSORY_TYPE_NONE);
        itemWithSwitch.setAccessoryType(CommonListItemView.ACCESSORY_TYPE_SWITCH);
        itemWithSwitch.getSwitch().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(ListItemActivity.this, "checked = " + isChecked, Toast.LENGTH_SHORT).show();
            }
        });

        CommonListItemView longTitleAndDetail = mGroupListView.createItemView(null,
                "标题有点长；标题有点长；标题有点长；标题有点长；标题有点长；标题有点长",
                "详细信息有点长; 详细信息有点长；详细信息有点长；详细信息有点长;详细信息有点长",
                CommonListItemView.VERTICAL,
                CommonListItemView.ACCESSORY_TYPE_CHEVRON,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        int paddingVer = DisplayHelper.dp2px(ListItemActivity.this, 12);
        longTitleAndDetail.setPadding(longTitleAndDetail.getPaddingLeft(), paddingVer,
                longTitleAndDetail.getPaddingRight(), paddingVer);
        
        
        //将Item添加到List中
        int size = DisplayHelper.dp2px(ListItemActivity.this, 20);
        GroupListView.newSection(ListItemActivity.this)
                .setTitle("Section Top")
                .setDescription("Section Bottom")
                .setLeftIconSize(size, ViewGroup.LayoutParams.WRAP_CONTENT)
                .addItemView(normalItem, null)
//                .addItemView(itemWithDetail, null)
                .addItemView(itemWithDetailBelow,null)
                .addItemView(itemWithChevron,null)
                .addItemView(itemWithSwitch,null)
                .addItemView(longTitleAndDetail,null)
                .addTo(mGroupListView);
    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void loadData() {

    }
}
