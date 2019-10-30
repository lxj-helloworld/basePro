package com.example.xiaojin20135.basemodule.status;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;
import android.view.ViewConfiguration;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeviceUtils {
    public void DeviceUtil() {
    }

    public static boolean isMIUI() {
        try {
            String rom = getDeviceProp("ro.miui.ui.version.name");
            return (DeviceUtils.ROM_MIUI_V5.equals(rom) || DeviceUtils.ROM_MIUI_V6.equals(rom) || DeviceUtils.ROM_MIUI_V7.equals(rom));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String ROM_MIUI_V5 = "V5";
    public static String ROM_MIUI_V6 = "V6";
    public static String ROM_MIUI_V7 = "V7";


    public static String getDeviceProp(String key) throws JSONException {
        List<String> list = runComm("getprop");
        if (list == null || list.size() == 0) {
            list = runComm("cat /system/build.prop");
        }
        Map<String, String> m = new HashMap<String, String>();
        for (String item : list) {
            if (item.startsWith("[")) {
                String[] strings = item.split("\\]: \\[");
                if (strings.length == 2) m.put(strings[0].substring(1), strings[1].substring(0, strings[1].length() - 1));
            } else {
                String[] strings = item.split("=");
                if (strings.length == 2) m.put(strings[0], strings[1]);
            }
        }

        return getV(m, key);
    }

    private static String getV(Map<String, String> mapSource, String key) {
        String value = mapSource.get(key);
        if (TextUtils.isEmpty(value)) value = "";
        return value;
    }

    public static ArrayList<String> runComm(String comm) {
        Process process = null;
        ArrayList<String> resultStr = new ArrayList<String>();
        String line;
        BufferedReader brout = null;
        try {
            process = Runtime.getRuntime().exec(comm);
            InputStream outs = process.getInputStream();
            InputStreamReader isrout = new InputStreamReader(outs);
            brout = new BufferedReader(isrout, 8 * 1024);
            while ((line = brout.readLine()) != null) {
                resultStr.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (brout != null) {
                    brout.close();
                }
                if (process != null) {
                    process.destroy();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultStr;
    }

}
