package com.example.xiaojin20135.basemodule.layout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.xiaojin20135.basemodule.R;
import com.example.xiaojin20135.basemodule.view.widget.alpha.AlphaFrameLayout;

import static com.example.xiaojin20135.basemodule.activity.BaseApplication.getActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class UIFrameLayout extends AlphaFrameLayout implements LayoutInf {

    public UIFrameLayout() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText(R.string.hello_blank_fragment);
        return textView;
    }

    @Override
    public void setShadowElevation(int evelation) {

    }

    @Override
    public int getShadowElevation() {
        return 0;
    }

    @Override
    public void setShadowAlapha(float shadowAlapha) {

    }

    @Override
    public float getShadowAlapha() {
        return 0;
    }

    @Override
    public void setShadowColor(int shadowColor) {

    }

    @Override
    public int getShadowColor() {
        return 0;
    }

    @Override
    public void setRadius(int radius) {

    }

    @Override
    public int getRadius() {
        return 0;
    }
}
