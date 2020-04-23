package com.example.xiaojin20135.mybaseapp.bottomsheet;

import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.example.xiaojin20135.basemodule.activity.ToolBarActivity;
import com.example.xiaojin20135.basemodule.util.LogUtils;
import com.example.xiaojin20135.basemodule.view.widget.bottom.MyBottomSheet;
import com.example.xiaojin20135.mybaseapp.R;

/*
* @author lixiaojin
* create on 2020-04-08 10:12
* description: BottomSheet Demo类
*/
public class SimpleBottomSheetActivity extends ToolBarActivity {

    private int lastIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_simple_bottom_sheet;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void loadData() {

    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.simple_btn:
                showSimpleBottomSheetList(
                        false, false, false, null,
                        3, false, false);
                break;
            case R.id.simple_with_icon_btn:
                showSimpleBottomSheetList(
                        false, false, true, null,
                        3, false, false);
                break;
            case R.id.simple_with_gravity_btn:
                showSimpleBottomSheetList(
                        true, false, true, null,
                        3, false, false);
                break;
            case R.id.simple_with_title_btn:
                showSimpleBottomSheetList(
                        true, false, true, "this is the title",
                        3, false, false);
                break;
            case R.id.simple_with_cancle_btn:
                showSimpleBottomSheetList(
                        true, true, true, null,
                        3, false, false);
                break;
            case R.id.simple_with_mark_btn:
                showSimpleBottomSheetList(
                        true, true, true, null,
                        3, false, true);
                break;
            case R.id.simple_with_grid_btn:
                showSimpleBottomSheetGrid();
                break;
        }
    }

    private void showSimpleBottomSheetList(boolean gravityCenter,
                                           boolean addCancelBtn,
                                           boolean withIcon,
                                           CharSequence title,
                                           int itemCount,
                                           boolean allowDragDismiss,
                                           final boolean withMark) {
        MyBottomSheet.BottomListSheetBuilder builder = new MyBottomSheet.BottomListSheetBuilder(SimpleBottomSheetActivity.this);
        builder.setGravityCenter(gravityCenter)
                .setTitle(title)
                .setAddCancelBtn(addCancelBtn)
                .setAllowDrag(allowDragDismiss)
                .setNeedRightMark(withMark)
                .setOnSheetItemClickListener(new MyBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener() {
                    @Override
                    public void onClick(MyBottomSheet dialog, View itemView, int position, String tag) {
                        LogUtils.d(TAG,"触发item 点击事件  position = " + position + " tag = " + tag);
                        dialog.dismiss();
                        Toast.makeText(SimpleBottomSheetActivity.this, "Item " + (position + 1), Toast.LENGTH_SHORT).show();
                        if(withMark){
                            lastIndex = position;
                        }
                     }
                });
        if(withMark){
            builder.setCheckedIndex(lastIndex);
        }
        for (int i = 1; i <= itemCount; i++) {
            if(withIcon){
                builder.addItem(ContextCompat.getDrawable(SimpleBottomSheetActivity.this, R.mipmap.ic_launcher_round), "this is the " + i + "item");
            }else{
                builder.addItem("this is the " + i + " item, hello world.");
            }
        }
        builder.build().show();
    }

    /*
    * @author lixiaojin
    * create on 2020-04-09 09:38
    * description: 显示
    */
    private void showSimpleBottomSheetGrid() {
        final int TAG_SHARE_WECHAT_FRIEND = 0;
        final int TAG_SHARE_WECHAT_MOMENT = 1;
        final int TAG_SHARE_WEIBO = 2;
        final int TAG_SHARE_CHAT = 3;
        final int TAG_SHARE_LOCAL = 4;
        MyBottomSheet.BottomGridSheetBuilder builder = new MyBottomSheet.BottomGridSheetBuilder(SimpleBottomSheetActivity.this);
        builder.addItem(R.mipmap.ic_launcher_round, "分享到微信", TAG_SHARE_WECHAT_FRIEND, MyBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .addItem(R.mipmap.ic_launcher_round, "分享到朋友圈", TAG_SHARE_WECHAT_MOMENT, MyBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .addItem(R.mipmap.ic_launcher_round, "分享到微博", TAG_SHARE_WEIBO, MyBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .addItem(R.mipmap.ic_launcher_round, "分享到QQ", TAG_SHARE_CHAT, MyBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .addItem(R.mipmap.ic_launcher_round, "保存到本地", TAG_SHARE_LOCAL, MyBottomSheet.BottomGridSheetBuilder.SECOND_LINE)
                .setAddCancelBtn(true)
                .setOnSheetItemClickListener(new MyBottomSheet.BottomGridSheetBuilder.OnSheetItemClickListener() {
                    @Override
                    public void onClick(MyBottomSheet dialog, View itemView) {
                        dialog.dismiss();
                        int tag = (int) itemView.getTag();
                        switch (tag) {
                            case TAG_SHARE_WECHAT_FRIEND:
                                Toast.makeText(SimpleBottomSheetActivity.this, "分享到微信", Toast.LENGTH_SHORT).show();
                                break;
                            case TAG_SHARE_WECHAT_MOMENT:
                                Toast.makeText(SimpleBottomSheetActivity.this, "分享到朋友圈", Toast.LENGTH_SHORT).show();
                                break;
                            case TAG_SHARE_WEIBO:
                                Toast.makeText(SimpleBottomSheetActivity.this, "分享到微博", Toast.LENGTH_SHORT).show();
                                break;
                            case TAG_SHARE_CHAT:
                                Toast.makeText(SimpleBottomSheetActivity.this, "分享到QQ", Toast.LENGTH_SHORT).show();
                                break;
                            case TAG_SHARE_LOCAL:
                                Toast.makeText(SimpleBottomSheetActivity.this, "保存到本地", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                }).build().show();
    }
}
