package com.example.xiaojin20135.basemodule.util.ui;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;

import com.example.xiaojin20135.basemodule.skin.SkinValueBuilder;

public class ResHelper {
    private static TypedValue sTypedValue;




    public static int getAttrColor(Context context, int attrRes){
        return getAttrColor(context.getTheme(),attrRes);
    }


    /*
    * @author lixiaojin
    * create on 2020-03-04 14:13
    * description: 默认对象初始化
    */
    private static void init(){
        if(sTypedValue == null){
            sTypedValue = new TypedValue();
        }
    }

    /*
    * @author lixiaojin
    * create on 2020-03-04 13:59
    * description: 获取Attribute属性的值
    */
    public static int getAttrColor(Resources.Theme theme,int attr){
        init();
        if(!theme.resolveAttribute(attr,sTypedValue,true)){
            return 0;
        }
        if(sTypedValue.type == TypedValue.TYPE_ATTRIBUTE){
            return getAttrColor(theme,sTypedValue.data);
        }
        return sTypedValue.data;
    }

    /*
    * @author lixiaojin
    * create on 2020-03-04 14:12
    * description: 获取属性值，大小
    */
    public static int getAttrDimen(Context context, int attrRes){
        init();
        if(!context.getTheme().resolveAttribute(attrRes,sTypedValue,true)){
            return 0;
        }
        return TypedValue.complexToDimensionPixelSize(sTypedValue.data,DisplayHelper.getDisplayMetris(context));
    }

    /*
    * @author lixiaojin
    * create on 2020-03-04 16:40
    * description: 获取属性值，float类型
    */
    public static float getAttrFloatValue(Context context,int attr){
        return getAttrFloatValue(context.getTheme(),attr);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-04 16:43
    * description: 获取属性值，float类型
    */
    public static float getAttrFloatValue(Resources.Theme theme,int attr){
        init();
        if(!theme.resolveAttribute(attr,sTypedValue,true)){
            return 0;
        }
        return sTypedValue.getFloat();
    }


}
