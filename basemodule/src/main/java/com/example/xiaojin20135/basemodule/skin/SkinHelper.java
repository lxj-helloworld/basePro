package com.example.xiaojin20135.basemodule.skin;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.View;

import com.example.xiaojin20135.basemodule.R;
import com.example.xiaojin20135.basemodule.util.ui.ResHelper;

public class SkinHelper {
    public static SkinValueBuilder sSkinValueBuilder = SkinValueBuilder.acquire();

    public static void setSkinValue(View view, SkinValueBuilder skinValueBuilder){
        setSkinValue(view,skinValueBuilder.build());
    }

    public static void setSkinValue(View view,String value){
        view.setTag(R.id.skin_value,value);
    }

    @Nullable
    public static Drawable getSkinDrawable(@NonNull View view, int drawableAttr) {
        return ResHelper.getAttrDrawable(view.getContext(), view.getContext().getTheme(), drawableAttr);
    }
}
