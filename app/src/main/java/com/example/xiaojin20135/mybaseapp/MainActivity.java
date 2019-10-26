package com.example.xiaojin20135.mybaseapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.xiaojin20135.basemodule.activity.ToolBarActivity;
import com.example.xiaojin20135.basemodule.guidepage.GuidePageActivity;
import com.example.xiaojin20135.basemodule.menuitem.adapter.MenuItemAdapter;
import com.example.xiaojin20135.basemodule.menuitem.fragment.MenuItemFragment;
import com.example.xiaojin20135.basemodule.menuitem.listener.IMemuItemClick;
import com.example.xiaojin20135.basemodule.webview.BaseWebViewActivity;
import com.example.xiaojin20135.mybaseapp.alert.ItemAlertActivity;
import com.example.xiaojin20135.mybaseapp.approve.ApproveTestActivity;
import com.example.xiaojin20135.mybaseapp.bottom.MyBottomActivity;
import com.example.xiaojin20135.mybaseapp.datepicker.DatePickerActivity;
import com.example.xiaojin20135.mybaseapp.datepicker.wheel.WeelPickerActivity;
import com.example.xiaojin20135.mybaseapp.devicelabel.DeviceLabelActivity;
import com.example.xiaojin20135.mybaseapp.download.DownloadActivity;
import com.example.xiaojin20135.mybaseapp.file.SaveToFilesActivity;
import com.example.xiaojin20135.mybaseapp.image.PickImageActivity;
import com.example.xiaojin20135.mybaseapp.loaddata.LoadDataActivity;
import com.example.xiaojin20135.mybaseapp.mpchart.MyChartActivity;
import com.example.xiaojin20135.mybaseapp.normal.NormalEdittextActivity;
import com.example.xiaojin20135.mybaseapp.normal.NormalTextViewActivity;
import com.example.xiaojin20135.mybaseapp.recyclerview.MyRecyActivity;
import com.example.xiaojin20135.mybaseapp.security.SecurityActivity;
import com.example.xiaojin20135.mybaseapp.spinner.MySpinnerActivity;
import com.example.xiaojin20135.mybaseapp.tablayout.MyTabLayoutActivity;
import com.example.xiaojin20135.mybaseapp.update.CheckUpdateActivity;
import com.example.xiaojin20135.mybaseapp.view.IpAndPortActivity;
import com.example.xiaojin20135.mybaseapp.view.WaterMarkActivity;
import com.example.xiaojin20135.mybaseapp.xgPush.XgPushActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.xiaojin20135.basemodule.status.StatusHelp.setWindowStatusBarColor;
import static com.example.xiaojin20135.basemodule.util.ConstantUtil.MAP;
import static com.example.xiaojin20135.basemodule.util.ConstantUtil.URLWEB;

public class MainActivity extends ToolBarActivity implements View.OnClickListener{
    List<Map<String,Integer>> datas;
    MenuItemFragment menuItemFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleText(R.string.main_page);
//        initMenu();


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void loadData() {
//        Log.d(TAG,"in loadData");
    }

    @Override
    public void showProgress () {

    }

    @Override
    public void dismissProgress () {

    }

    @Override
    public void loadDataSuccess (Object tData) {

    }

    @Override
    public void loadError (Throwable throwable) {

    }

    @Override
    public void loadComplete () {

    }

    @Override
    public void onClick (View v) {
        Bundle bundle = new Bundle ();
        switch (v.getId ()){
            case R.id.sign_in_btn:
                Intent intent = new Intent (MainActivity.this, MyLoginActivity.class);
                startActivity (intent);
                break;
            case R.id.bottom_btn:
                canGo (MyBottomActivity.class);
                break;
            case R.id.recycler_btn:

                bundle.putString ("hh","jj");
                canGo (MyRecyActivity.class,bundle);
                break;
            case R.id.load_data_btn:
                canGo (LoadDataActivity.class);
                break;
            case R.id.chart_btn:
                canGo (MyChartActivity.class);
                break;
            case R.id.spiner_btn:
                canGo (MySpinnerActivity.class);
                break;
            case R.id.date_pick_btn:
                canGo (DatePickerActivity.class);
                break;
            case R.id.sel_dialog_btn:
                canGo (ItemAlertActivity.class);
                break;
            case R.id.load_data_fragment_btn:
                bundle.putInt ("index",0);
                canGo (FragmentActivity.class,bundle);
                break;
            case R.id.load_list_fragment_btn:
                bundle.putInt ("index",1);
                canGo (FragmentActivity.class,bundle);
                break;
            case R.id.download_file_btn:
                canGo (DownloadActivity.class);
                break;
            case R.id.check_update_btn:
                canGo (CheckUpdateActivity.class);
                break;
            case R.id.show_tap_btn:
                canGo (MyTabLayoutActivity.class);
                break;
            case R.id.menu_item_btn:
                bundle.putInt ("index",2);
                canGo (FragmentActivity.class,bundle);
                break;
            case R.id.device_label_btn:
                canGo (DeviceLabelActivity.class,bundle);
                break;
            case R.id.approve_test_btn:
                Map map = new HashMap<> ();
                map.put ("sourceId","1.808280000005E12");
                map.put ("mobileForm","transferInCompanyLoad");
                map.put ("approvalAction","../crm/transferIn_approval.action");
                map.put ("mobileDataAction","crm/transferIn_queryMobilApprovalData.action");
                map.put ("id","1.80828000054E12");
                bundle.putSerializable (MAP,(Serializable)map);
                canGo (ApproveTestActivity.class,bundle);
                break;
            case R.id.pick_iamge_btn:
                canGo (PickImageActivity.class);
                break;
            case R.id.ip_port_btn:
                canGo (IpAndPortActivity.class);
                break;
            case R.id.webView_btn:
                bundle.putString (URLWEB,"http://www.divcss5.com/html/h380.shtml");
                canGo (BaseWebViewActivity.class,bundle);
                break;
            case R.id.wheelview_btn:
                canGo (WeelPickerActivity.class);
                break;
            case R.id.xgpush_btn:
                canGo (XgPushActivity.class);
                break;
            case R.id.guide_page_btn:
                ArrayList imagesList = new ArrayList ();
                imagesList.add (R.mipmap.guide1);
                imagesList.add (R.mipmap.guide2);
                imagesList.add (R.mipmap.guide3);
                imagesList.add (R.mipmap.guide4);
                imagesList.add (R.mipmap.guide5);
                bundle.putIntegerArrayList ("imagesList",imagesList);
//                bundle.putString ("activityName",MyLoginActivity.class.getName ());
                canGo (GuidePageActivity.class,bundle);
                break;
            case R.id.security_btn:
                canGo (SecurityActivity.class);
                break;
            case R.id.save_files_btn:
                canGo(SaveToFilesActivity.class);
                break;
            case R.id.location_btn:
//                canGo(MyLocationActivity.class);
                break;
            case R.id.myinput_btn:
                canGo(NormalEdittextActivity.class);
                break;
            case R.id.watermark_btn:
                canGo(WaterMarkActivity.class);
                break;
            case R.id.normal_textvew_btn:
                canGo(NormalTextViewActivity.class);
                break;
        }
    }

    private void initMenu(){
        menuItemFragment = MenuItemFragment.getInstance (this, datas, myIMenuItemClick);
        menuItemFragment.setSpanCount (5); //每列图标个数
        getSupportFragmentManager ().beginTransaction ().replace (R.id.fragment_container,menuItemFragment).commit ();
    }

    IMemuItemClick myIMenuItemClick = new IMemuItemClick(){
        @Override
        public void itemClick (int index) {
            Toast.makeText (getApplicationContext (),"您点击了 : " + index,Toast.LENGTH_SHORT).show ();
            int count = datas.get (index).get (MenuItemAdapter.COUNT) - 1;
            datas.get (index).put (MenuItemAdapter.COUNT,count);
            Log.d (TAG,"datas = " + datas);
            menuItemFragment.updataInfo ();
        }
    };

    @Override
    protected void onStart () {
        super.onStart ();
        Log.d (TAG," onStart ");
    }

    @Override
    protected void onStop () {
        super.onStop ();
        Log.d (TAG," onStop ");
    }

    @Override
    protected void onDestroy () {
        super.onDestroy ();
        Log.d (TAG," onDestroy ");
    }

    @Override
    protected void onPause () {
        super.onPause ();
        Log.d (TAG," onPause ");
    }

    @Override
    protected void onResume () {
        super.onResume ();
        Log.d (TAG," onResume ");
    }

    @Override
    public void finish () {
        super.finish ();
        Log.d (TAG," finish ");
    }
}
