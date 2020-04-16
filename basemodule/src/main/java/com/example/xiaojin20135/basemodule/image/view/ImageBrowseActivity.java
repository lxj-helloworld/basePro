package com.example.xiaojin20135.basemodule.image.view;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.xiaojin20135.basemodule.R;
import com.example.xiaojin20135.basemodule.activity.BaseActivity;
import com.example.xiaojin20135.basemodule.dialog.MyItemDialog;
import com.example.xiaojin20135.basemodule.download.listener.MyDownloadListener;
import com.example.xiaojin20135.basemodule.download.util.DownloadUtils;
import com.example.xiaojin20135.basemodule.image.adapter.ImageBrowseAdapter;
import com.example.xiaojin20135.basemodule.image.listener.ImageLongClick;
import com.example.xiaojin20135.basemodule.util.MethodsUtils;
import com.example.xiaojin20135.basemodule.view.MyViewPager;
import com.example.xiaojin20135.basemodule.view.SlideCloseLayout;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;

import static com.example.xiaojin20135.basemodule.image.ImageConstant.FROMNET;

public class ImageBrowseActivity extends BaseActivity implements ImageLongClick {
    private ProgressBar loading_progress;//下载进度条
    private TextView mNumberTextView;
    private ArrayList<String> imageList = new ArrayList<> ();
    private MyViewPager imageBrowseViewPager;
    private ImageBrowseAdapter imageBrowseAdapter;
    private int currentIndex = 0;
    //是否是网络图片
    private boolean fromNet = false;
    //是否启用长按监听事件
    private boolean enableLongClick = false;
    private PhotoView photoView;
    private ImageView left;
    private ImageView right;
    private boolean isleft;
    private boolean isright;
    private SlideCloseLayout slideCloseLayout;

    //删除
    private ImageView delete_iv;
    //下载
    private ImageView download_iv;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        //出现动画
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        //全屏
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.black));
        }


        //展示数据
        showData();

        //初始化下载和删除功能
        initDowns();
    }

    private void setSystemUIVisible(boolean show) {
        if (show) {
            int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            uiFlags |= 0x00001000;
            getWindow().getDecorView().setSystemUiVisibility(uiFlags);
        } else {
            int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
            uiFlags |= 0x00001000;
            getWindow().getDecorView().setSystemUiVisibility(uiFlags);
        }
    }


    @Override
    protected int getLayoutId () {
        return R.layout.activity_image_browse;
    }

    @Override
    protected void initView () {
        getWindow().getDecorView().setBackgroundColor(Color.BLACK);

        slideCloseLayout=findViewById(R.id.slideLayout);
        slideCloseLayout.setGradualBackground(getWindow().getDecorView().getBackground());
        slideCloseLayout.setLayoutScrollListener(new SlideCloseLayout.LayoutScrollListener() {
            @Override
            public void onLayoutClosed() {
                back();
            }

            @Override
            public void onLayoutScrolling(float alpha) {
//                getWindow().getDecorView().setAlpha(alpha);
            }

            @Override
            public void onLayoutScrollRevocer() {
//                backgroundAlpha(1f);

            }
        });
        loading_progress = (ProgressBar)findViewById(R.id.loading_progress);
        mNumberTextView = (TextView)findViewById(R.id.number_textview);
        left = (ImageView) findViewById(R.id.roat_left);
        right = (ImageView) findViewById(R.id.roat_right);
        imageBrowseViewPager = (MyViewPager)findViewById (R.id.imageBrowseViewPager);
        if(enableLongClick){
            imageBrowseAdapter = new ImageBrowseAdapter (this,imageList,this);
        }else{
            imageBrowseAdapter = new ImageBrowseAdapter (this,imageList);
        }
        imageBrowseViewPager.setAdapter (imageBrowseAdapter);
        imageBrowseViewPager.setCurrentItem (currentIndex);

        delete_iv = findViewById(R.id.delete_iv);
        download_iv = findViewById(R.id.download_iv);
    }

    @Override
    protected void initEvents () {

        imageBrowseViewPager.addOnPageChangeListener (new ViewPager.OnPageChangeListener () {
            @Override
            public void onPageScrolled (int position, float positionOffset, int positionOffsetPixels) {
                currentIndex = position;
                Log.d (TAG,"currentIndex = " + currentIndex);
                updateBottomIndex(position + 1);
            }
            @Override
            public void onPageSelected (int position) {
                updateBottomIndex(position + 1);
            }
            @Override
            public void onPageScrollStateChanged (int state) {

            }
        });
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isleft){
                    isleft=true;
                    imageBrowseAdapter.getView().animate().rotationBy(-90).setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            isleft=false;
                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animator) {

                        }
                    }).start();

                }
            }
        });
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isright){
                    isright=true;
                    imageBrowseAdapter.getView().animate().rotationBy(90).setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            isright=false;
                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animator) {

                        }
                    }).start();

                }
            }
        });

        //下载
        download_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downLoadImage();
            }
        });

        //删除
        delete_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteImage();
            }
        });

    }

    @Override
    protected void loadData () {
        Intent intent = getIntent ();
        if(intent != null){
            currentIndex = intent.getIntExtra("index", 0);
            fromNet = intent.getBooleanExtra(FROMNET,false);
            imageList = (ArrayList<String>) intent.getStringArrayListExtra("imageList");
//            enableLongClick = intent.getBooleanExtra(ENABLELONGCLICK,true);
        }
    }

    /*
    * @author lixiaojin
    * create on 2019/8/1 16:17
    * description: 展示数据
    */
    private void showData(){
        if(imageList.size() > 0) {
            updateBottomIndex(currentIndex + 1);
        }
    }

    /*
    * @author lixiaojin
    * create on 2019/8/1 16:13
    * description: 底部数字索引
    */
    private void updateBottomIndex(int count){
        mNumberTextView.setText(count + "/" + imageList.size());
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.deletemenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        if(item.getItemId () == R.id.delete_item){
//            if(imageList != null && imageList.size () > currentIndex){
//                imageList.remove (currentIndex);
//                imageBrowseAdapter.notifyDataSetChanged ();
//            }
//            if(imageList == null || imageList.size () == 0){
//                back();
//            }
        }
        return super.onOptionsItemSelected (item);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(KeyEvent.KEYCODE_BACK == keyCode){
            back();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void back(){
        Intent intent = new Intent ();
        intent.putStringArrayListExtra ("imageList",imageList);
        setResult (RESULT_OK,intent);
        this.finish ();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_in_disappear,R.anim.fade_out_disappear);
    }

    @Override
    public void longClickImage(int position) {
        //如果是网络图片
        if(fromNet){
            String[] items = new String[]{"下载","取消"};
            MyItemDialog myItemDialog = new MyItemDialog(this, items, 1, new MyItemDialog.OnDialogItemClickListener() {
                @Override
                public void onDialogItemClick(int requestCode, int position, String item) {
                    if(position == 0){
                        downLoadImage();
                    }
                }
            });
            myItemDialog.show();
        }else{//如果是本地图片预览
            String[] items = new String[]{"删除","取消"};
            MyItemDialog myItemDialog = new MyItemDialog(this, items, 1, new MyItemDialog.OnDialogItemClickListener() {
                @Override
                public void onDialogItemClick(int requestCode, int position, String item) {
                    if(position == 0){
                        deleteImage();
                    }
                }
            });
            myItemDialog.show();
        }
    }

    /*
    * @author lixiaojin
    * create on 2019-11-05 11:28
    * description: 初始化删除和下载功能
    */
    private void initDowns(){
        if(fromNet){ //如果是网络图片，显示下载按钮，隐藏删除按钮
            delete_iv.setVisibility(View.GONE);
            download_iv.setVisibility(View.VISIBLE);
        }else{ //如果是本地图片，显示删除按钮，隐藏下载按钮
            delete_iv.setVisibility(View.VISIBLE);
            download_iv.setVisibility(View.GONE);
        }
    }


    /*
    * @author lixiaojin
    * create on 2019-11-05 11:34
    * description: 删除图片
    */
    private void deleteImage(){
        if(imageList != null && imageList.size () > currentIndex){
            imageList.remove (currentIndex);
            imageBrowseAdapter.notifyDataSetChanged ();
        }
        if(imageList == null || imageList.size () == 0){
            back();
        }
    }

    /*
    * @author lixiaojin
    * create on 2019/8/1 20:55
    * description: 下载图片
    */
    public void downLoadImage(){
        DownloadUtils downloadUtils = new DownloadUtils (MethodsUtils.METHODS_UTILS.getHostFromUrl(imageList.get(currentIndex)));
        downloadUtils.downloadFile(imageList.get(currentIndex), new MyDownloadListener() {
            @Override
            public void onStart() {
                loading_progress.setVisibility(View.VISIBLE);
            }
            @Override
            public void onProgress(final int currentLength) {
                if(currentLength <= 100){
                    loading_progress.setProgress(currentLength);
                }else{
                    loading_progress.setProgress(100);
                }
            }
            @Override
            public void onFinish(String localPath) {
                String result = "下载完成\r\n";
                loading_progress.setVisibility(View.GONE);
                showToast(ImageBrowseActivity.this,"已下载至：" + localPath);
            }
            @Override
            public void onFailure(final String errorInfo) {
                loading_progress.setVisibility(View.GONE);
                showToast(ImageBrowseActivity.this,"下载失败：" + errorInfo);
            }
        });
    }

}
