package com.example.xiaojin20135.basemodule.view;

import android.graphics.Paint;

/*
* @author lixiaojin
* create on 2020-03-02 15:15
* description: 自定义视图帮助类
*/
public class ViewUtils {

    /*
    * @author lixiaojin
    * create on 2020-03-02 15:17
    * description: 生成画笔帮助方法
    */
    public static Paint generatePaint(int color, int width, Paint.Style style){
        Paint paint = new Paint();
        paint.setColor(color); //设置画笔颜色
        paint.setStrokeWidth(width); //设置画笔宽度
        paint.setStyle(style); //设置填充样式
        paint.setAntiAlias(true); //默认消除锯齿
        return paint;
    }

}
