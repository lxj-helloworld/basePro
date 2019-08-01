package com.example.xiaojin20135.mybaseapp.image;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import com.example.xiaojin20135.basemodule.activity.ToolBarActivity;
import com.example.xiaojin20135.basemodule.image.ImageConstant;
import com.example.xiaojin20135.basemodule.image.adapter.MyPhotoAdapter;
import com.example.xiaojin20135.basemodule.image.inter.WechatImagePicker;
import com.example.xiaojin20135.basemodule.image.inter.ZhihuImagePicker;
import com.example.xiaojin20135.basemodule.image.listener.RecyclerItemClickListener;
import com.example.xiaojin20135.basemodule.image.view.ImageBrowseActivity;
import com.example.xiaojin20135.basemodule.util.FileHelp;
import com.example.xiaojin20135.basemodule.util.MethodsUtils;
import com.example.xiaojin20135.mybaseapp.R;
import com.gengqiquan.result.RxActivityResult;
import com.qingmei2.rximagepicker.core.RxImagePicker;
import com.qingmei2.rximagepicker.entity.Result;
import com.qingmei2.rximagepicker.ui.SystemImagePicker;
import com.qingmei2.rximagepicker_extension.MimeType;
import com.qingmei2.rximagepicker_extension_wechat.WechatConfigrationBuilder;


import java.io.File;
import java.util.ArrayList;

import io.reactivex.functions.Consumer;

import static com.example.xiaojin20135.basemodule.image.ImageConstant.FROMNET;

public class PickImageActivity extends ToolBarActivity {
    WechatImagePicker wechatImagePicker;
    SystemImagePicker systemImagePicker;
    ZhihuImagePicker zhihuImagePicker;
    RecyclerView recyclerView;
    private MyPhotoAdapter myPhotoAdapter;
    private ArrayList<String> selectedPhotos = new ArrayList<>();
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        wechatImagePicker = RxImagePicker.INSTANCE.create (WechatImagePicker.class);
        systemImagePicker = RxImagePicker.INSTANCE.create();
    }

    @Override
    protected int getLayoutId () {
        return R.layout.activity_pick_image;
    }

    @Override
    protected void initView () {
        zhihuImagePicker = RxImagePicker.INSTANCE.create(ZhihuImagePicker.class);
        recyclerView = findViewById(R.id.recycler_view);
        myPhotoAdapter = new MyPhotoAdapter(this, selectedPhotos);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager (4, OrientationHelper.VERTICAL));
        recyclerView.setAdapter(myPhotoAdapter);
    }

    @Override
    protected void initEvents () {
        recyclerView.addOnItemTouchListener (new RecyclerItemClickListener (this, new RecyclerItemClickListener.OnItemClickListener () {
            @Override
            public void onItemClick (View view, int position) {
                if (myPhotoAdapter.getItemViewType(position) == MyPhotoAdapter.TYPE_ADD) { //新增
                    zhihuGalleryOpen();
                } else { //预览
                    Bundle bundle = new Bundle ();
                    bundle.putStringArrayList (ImageConstant.imageList, selectedPhotos);
                    RxActivityResult.with(PickImageActivity.this).putAll (bundle)
                        .startActivityWithResult(new Intent (PickImageActivity.this, ImageBrowseActivity.class))
                        .subscribe (new Consumer<com.gengqiquan.result.Result> () {
                            @Override
                            public void accept (com.gengqiquan.result.Result result) throws Exception {
                                selectedPhotos = result.data.getStringArrayListExtra ("imageList");
                                if(selectedPhotos == null){
                                    selectedPhotos = new ArrayList<> ();
                                }
                                myPhotoAdapter.addAll (selectedPhotos);
                            }
                        }, new Consumer<Throwable> () {
                            @Override
                            public void accept (Throwable throwable) throws Exception {
                                throwable.printStackTrace ();
                            }
                        });
                }
            }
        }));
    }

    @Override
    protected void loadData () {

    }

    public void onClick (View view) {
        switch (view.getId ()){
            case R.id.net_image_btn:
                showNetImages();
                break;
            case R.id.system_gallery_btn:
                systemGalleryOpen ();
                break;
            case R.id.system_camera_btn:
                systemCameraOpen();
                break;
            case R.id.weixin_gallery_btn:
                weixinGalleryOpen ();
                break;
            case R.id.weixin_camera_btn:
                weixinCameraOpen ();
                break;
            case R.id.zhihu_gallery_btn:
                zhihuGalleryOpen ();
                break;
            case R.id.zhihu_camera_btn:
                zhihuCameraOpen ();
                break;
        }
    }

    private void showNetImages(){
        ArrayList<String> photos = new ArrayList<>();
        photos.add("http://pic23.nipic.com/20120814/3173351_101349962000_2.jpg");
        photos.add("http://img5.imgtn.bdimg.com/it/u=3156671389,2646079670&fm=26&gp=0.jpg");
        photos.add("http://img0.imgtn.bdimg.com/it/u=198375760,2973382315&fm=27&gp=0.jpg");
        photos.add("http://img4.duitang.com/uploads/item/201510/07/20151007000710_mWynE.thumb.700_0.jpeg");
        Bundle bundle = new Bundle ();
        bundle.putStringArrayList (ImageConstant.imageList, photos);
        bundle.putInt("index",2);
        bundle.putBoolean(FROMNET,true);
        canGo(ImageBrowseActivity.class,bundle);
    }



    private void systemGalleryOpen(){
        systemImagePicker.openGallery(this)               //打开系统相册选取图片
            .subscribe(new Consumer<Result>() {
                @Override
                public void accept(Result result) throws Exception {
                    // 做您想做的，比如将选取的图片展示在ImageView中
                    Log.d (TAG,"result.getUri ().getPath ()  = " + result.getUri ().getPath ());
                    selectedPhotos.add (FileHelp.FILE_HELP.getFilePath (PickImageActivity.this,result.getUri ()));
                    myPhotoAdapter.notifyDataSetChanged();
                }
            });
    }

    private void systemCameraOpen(){
        systemImagePicker.openCamera (this)
            .subscribe(new Consumer<Result>() {
                @Override
                public void accept(Result result) throws Exception {
                    // 做您想做的，比如将选取的图片展示在ImageView中
                    Log.d (TAG,"result.getUri ().getPath ()  = " + result.getUri ().getPath ());
                }
            });
    }

    private void weixinGalleryOpen(){
        wechatImagePicker.openGallery(this,
            new WechatConfigrationBuilder(MimeType.INSTANCE.ofImage(), false)
                .maxSelectable(9)
                .countable(true)
                .spanCount(3)
                .countable(false)
                .build())             //打开微信相册选取图片
            .subscribe(new Consumer<Result>() {
                @Override
                public void accept(Result result) throws Exception {
                    // 做您想做的，比如将选取的图片展示在ImageView中
                    Log.d (TAG,"result.getUri() = " + result.getUri().toString ());
                    selectedPhotos.add (FileHelp.FILE_HELP.getFilePath (PickImageActivity.this,result.getUri ()));
                    myPhotoAdapter.notifyDataSetChanged();
                }
            });
    }

    private void weixinCameraOpen(){
        wechatImagePicker.openCamera(this)             //打开微信相册选取图片
            .subscribe(new Consumer<Result>() {
                @Override
                public void accept(Result result) throws Exception {
                    // 做您想做的，比如将选取的图片展示在ImageView中
                    Log.d (TAG,"result.getUri() = " + result.getUri().toString ());
                }
            });
    }

    private void zhihuGalleryOpen(){
        zhihuImagePicker.openGallery(this,
            new WechatConfigrationBuilder (MimeType.INSTANCE.ofImage(), false)
                .maxSelectable(9)
                .countable(true)
                .spanCount(3)
                .build())             //打开知乎相册选取图片
            .subscribe(new Consumer<Result>() {
                @Override
                public void accept(Result result) throws Exception {
                    // 做您想做的，比如将选取的图片展示在ImageView中
                    selectedPhotos = MethodsUtils.METHODS_UTILS.addNewItem (selectedPhotos,FileHelp.FILE_HELP.getFilePath(PickImageActivity.this,result.getUri ()));
                    myPhotoAdapter.addAll (selectedPhotos);
                }
            });
    }

    private void zhihuCameraOpen(){
        zhihuImagePicker.openCamera(this)  //打开相机
            .subscribe(new Consumer<Result>() {
                @Override
                public void accept(Result result) throws Exception {
                    // 做您想做的，比如将选取的图片展示在ImageView中
                    Log.d (TAG,"result.getUri() = " + result.getUri().toString ());
                    selectedPhotos = MethodsUtils.METHODS_UTILS.addNewItem (selectedPhotos,FileHelp.FILE_HELP.getFilePath(PickImageActivity.this,result.getUri ()));
                    myPhotoAdapter.addAll (selectedPhotos);
                }
            });
    }

}
