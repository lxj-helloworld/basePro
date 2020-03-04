package com.example.xiaojin20135.basemodule.skin;

import android.view.View;

import com.example.xiaojin20135.basemodule.R;

public class SkinHelper {
    public static SkinValueBuilder sSkinValueBuilder = SkinValueBuilder.acquire();

    public static void setSkinValue(View view, SkinValueBuilder skinValueBuilder){
        setSkinValue(view,skinValueBuilder.build());
    }

    public static void setSkinValue(View view,String value){
        view.setTag(R.id.skin_value,value);
    }
}
