package com.example.xiaojin20135.basemodule.skin;

import android.support.v4.util.SimpleArrayMap;

/*
* @author lixiaojin
* create on 2020-04-09 16:13
* description: 
*/
public class SkinSimpleDefaultAttrProvider implements SkinDefaultAttrProvider {

    private SimpleArrayMap<String, Integer> mSkinAttrs = new SimpleArrayMap<>();

    public void setDefaultSkinAttr(String name, int attr) {
        mSkinAttrs.put(name, attr);
    }

    @Override
    public SimpleArrayMap<String, Integer> getDefaultSkinAttrs() {
        return mSkinAttrs;
    }
}
