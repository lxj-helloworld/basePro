package com.example.xiaojin20135.basemodule.status;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.xiaojin20135.basemodule.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static com.example.xiaojin20135.basemodule.activity.BaseActivity.getStatusBarHeight;

public enum StatusHelp {
    STATUS_HELP;

    private static final String TAG = "StatusHelp";


    //有色状态栏，想要和导航栏颜色不一样就设置不一样的颜色即可
    public static void setWindowStatusBarColor(Activity activity, int color, boolean isLightMode) {
        Log.d(TAG, "Build.VERSION.SDK_INT:" + Build.VERSION.SDK_INT);
        int statusColor = color;
        //如果是浅色主题  可将状态栏图标和文字内容改为黑色样式 实现浅色主题调背景的状态栏效果
        //只应用于4.4以上版本MIUIV、Flyme和6.0（API 23）以上版本其他Android
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (isLightMode) {
                int mode = StatusBarLightMode(activity);
                //0代表浅色主题没有设置成功
                if (mode == 0) {
                    statusColor = activity.getResources().getColor(R.color.white);
                }
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //使用setStatusBarColor的前提条件 取消FLAG_TRANSLUCENT_STATUS
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
            return;
        }
        //4.4以上，5.0以下
        //原理：只要在根布局去设置一个与状态栏等高的View，设置背景色为我们期望的颜色就可以
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
            View statusBarView = new View(activity);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity));
            statusBarView.setBackgroundColor(statusColor);
            contentView.addView(statusBarView, lp);
        }
    }


    /**
     * 设置状态栏黑色字体图标，
     * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
     *
     * @param activity
     * @return 1:MIUUI 2:Flyme 3:android6.0 0：为没有设置浅色主题成功
     */
    public static int StatusBarLightMode(Activity activity) {
        int result = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            if (DeviceUtils.isMIUI() && MIUISetStatusBarLightMode(activity.getWindow(), true)) {
                result = 1;
                //miui android 7.0无法通过反射修改
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                    result = 3;
                    Log.d(TAG, "api method");
                }
            } else if (FlymeSetStatusBarLightMode(activity.getWindow(), true)) {
                result = 2;
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                result = 3;
            }
        }
        return result;
    }


    /**
     * 设置状态栏图标为深色和魅族特定的文字风格
     * 可以用来判断是否为Flyme用户
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static boolean FlymeSetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }


    /**
     * 设置状态栏字体图标为深色，需要MIUIV6以上
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static boolean MIUISetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                    Log.d(TAG, "miui method: 1");
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                    Log.d(TAG, "miui method: 2");
                }
                result = true;

            } catch (Exception e) {
                Log.d(TAG, "miui method: error");
            }
        }
        return result;
    }



}
