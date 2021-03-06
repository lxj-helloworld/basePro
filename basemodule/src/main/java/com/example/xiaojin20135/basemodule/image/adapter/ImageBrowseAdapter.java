package com.example.xiaojin20135.basemodule.image.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.xiaojin20135.basemodule.R;
import com.example.xiaojin20135.basemodule.activity.BaseActivity;
import com.example.xiaojin20135.basemodule.image.listener.ImageLongClick;
import com.example.xiaojin20135.basemodule.image.view.ImageBrowseActivity;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;

/**
 * Created by lixiaojin on 2018-09-28 11:26.
 * 功能描述：
 */

public class ImageBrowseAdapter extends PagerAdapter {
    private static final String TAG = "ImageBrowseAdapter";
    BaseActivity context;
    private ArrayList<String> imageList;
    //图片长按事件
    private ImageLongClick imageLongClick;


    private PhotoView mCurrentView;
    public ImageBrowseAdapter(BaseActivity context,ArrayList<String> imageList){
        Log.d (TAG,"ImageBrowseAdapter");
        this.context = context;
        this.imageList = imageList;

    }

    public ImageBrowseAdapter(BaseActivity context,ArrayList<String> imageList,ImageLongClick imageLongClick){
        this(context,imageList);
        this.imageLongClick = imageLongClick;
    }

    @Override
    public int getCount () {
        Log.d (TAG,"getCount = " + imageList.size());
        return imageList.size();
    }

    @Override
    public boolean isViewFromObject (@NonNull View view, @NonNull Object object) {
        Log.d (TAG,"isViewFromObject + " + (view == object));
        return view == object;
    }

    @Override
    public int getItemPosition(Object object) {
        Log.d (TAG,"getItemPosition");
        return POSITION_NONE;
    }

    public View instantiateItem(final ViewGroup container, final int position){
        Log.d (TAG,"instantiateItem position = " + position + " :" + imageList.get (position));
//        final PhotoView image = new PhotoView (context);
//        // 开启图片缩放功能
//        image.enable();
//        // 设置缩放类型
//        image.setScaleType (ImageView.ScaleType.CENTER_INSIDE);
//        // 设置最大缩放倍数
//        image.setMaxScale (2.5f);
//        //参数设置
//        RequestOptions requestOptions = new RequestOptions()
//                .placeholder(R.drawable.loading)
//                .error(R.drawable.image_error);
//        // 加载图片
//        Glide.with(context)
//                .load(imageList.get (position))
//                .apply(requestOptions)
//                .into(image);
//
//        // 单击图片，返回
//        image.setOnClickListener (new View.OnClickListener () {
//            @Override
//            public void onClick (View v) {
//                image.disenable();
//                context.finish();
//            }
//        });
//        //长按图片
//        image.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                if(imageLongClick != null){
//                    imageLongClick.longClickImage(position);
//                }
//                return true;
//            }
//        });
//        container.addView(image);

        final PhotoView image = new PhotoView(context);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        image.setLayoutParams(layoutParams);
                RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.loading)
                .error(R.drawable.image_error);
        // 加载图片
        Glide.with(context)
                .load(imageList.get (position))
                .apply(requestOptions)
                .into(image);

        // 单击图片，返回
        image.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                if(context instanceof ImageBrowseActivity){
                    ((ImageBrowseActivity)context).back();
                }else{
                    context.finish();
                }

            }
        });
        //长按图片
        image.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(imageLongClick != null){
                    imageLongClick.longClickImage(position);
                }
                return true;
            }
        });
        container.addView(image);
        return image;

    }

    @Override
    public void destroyItem (@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView ((View) object);

    }

    public PhotoView getView(){
        return mCurrentView;
    }

    @Override
    public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
       mCurrentView= (PhotoView) object;
    }
}