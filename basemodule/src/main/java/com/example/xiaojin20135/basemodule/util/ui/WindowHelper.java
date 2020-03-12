package com.example.xiaojin20135.basemodule.util.ui;

import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewParent;
import android.view.WindowManager;

import com.example.xiaojin20135.basemodule.BuildConfig;

import java.lang.reflect.Field;

public class WindowHelper {
    /**
     * 设置WindowManager.LayoutParams的type
     * <p>
     * 1.使用 type 值为 TYPE_PHONE 和TYPE_SYSTEM_ALERT 需要申请 SYSTEM_ALERT_WINDOW 权限
     * 2.type 值为 TYPE_TOAST 显示的 System overlay view 不需要权限，即可在任何平台显示。
     * 3.type 值为 TYPE_TOAST在API level 19 以下因无法接收无法接收触摸（点击)和按键事件
     * 4.Android 6.0 悬浮窗被默认被禁用，即使申请了 SYSTEM_ALERT_WINDOW 权限，应用也会crash,需要用户自己去开启
     * （开启路径：通用 -- 应用管理 -- 更多 -- 配置应用 --- 在其他应用的上层显示 --- 选择你的APP -- 运行在其他应用的上层显示）
     * 5. 不直接返回type而是传layoutParams是不想调用者增加 @SuppressWarnings({"ResourceType"}) 跳过编译器的检查
     */
    public static void setWindowType(WindowManager.LayoutParams layoutParams) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            layoutParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        } else {
            layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
    }


    @Nullable //可以传入null值
    @SuppressWarnings({"JavaReflectionMemberAccess"})
    public static Rect unSafeGetWindowVisibleInsets(@NonNull View view) {
        Object attachInfo = getAttachInfoFromView(view);
        if(attachInfo == null){
            return null;
        }
        try {
            // fortunately now it is in light greylist, just be warned.
            Field visibleInsetsField = attachInfo.getClass().getDeclaredField("mVisibleInsets");
            visibleInsetsField.setAccessible(true);
            Object visibleInsets = visibleInsetsField.get(attachInfo);
            if (visibleInsets instanceof Rect) {
                return (Rect) visibleInsets;
            }
        } catch (Throwable e) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Nullable
    @SuppressWarnings({"JavaReflectionMemberAccess"})
    public static Rect unSafeGetContentInsets(@NonNull View view) {
        Object attachInfo = getAttachInfoFromView(view);
        if(attachInfo == null){
            return null;
        }
        try {
            // fortunately now it is in light greylist, just be warned.
            Field visibleInsetsField = attachInfo.getClass().getDeclaredField("mContentInsets");
            visibleInsetsField.setAccessible(true);
            Object visibleInsets = visibleInsetsField.get(attachInfo);
            if (visibleInsets instanceof Rect) {
                return (Rect) visibleInsets;
            }
        } catch (Throwable e) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Object getAttachInfoFromView(@NonNull View view){
        Object attachInfo = null;
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.P){
            // Android 10+ can not reflect the View.mAttachInfo
            // fortunately now it is in light greylist in ViewRootImpl
            View rootView = view.getRootView();
            if(rootView != null){
                ViewParent vp = rootView.getParent();
                if(vp != null){
                    try {
                        Field field = vp.getClass().getDeclaredField("mAttachInfo");
                        field.setAccessible(true);
                        attachInfo = field.get(vp);
                    } catch (Throwable e) {
                        if (BuildConfig.DEBUG) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }else{
            try {
                //Android P 禁止了 被@hide修饰的字段信息的反射，幸运的是，他现在是在灰名单里面，仅仅会被警告
                Field field = View.class.getDeclaredField("mAttachInfo"); //返回View中声明为mAttachInfo 的字段信息
                field.setAccessible(true); //允许通过反射访问私有字段
                attachInfo = field.get(view);
            } catch (Throwable e) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace();
                }
            }
        }
        return attachInfo;
    }
}

