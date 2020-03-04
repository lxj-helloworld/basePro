package com.example.xiaojin20135.basemodule.layout;

public interface LayoutInf {
    int HIDE_RADIUS_SIDE_NONE = 0;
    int HIDE_RADIUS_SIDE_TOP = 1;
    int HIDE_RADIUS_SIDE_RIGHT = 2;
    int HIDE_RADIUS_SIDE_BOTTOM = 3;
    int HIDE_RADIUS_SIDE_LEFT = 4;


    void setShadowElevation(int evelation);

    int getShadowElevation();

    void setShadowAlapha(float shadowAlapha);

    float getShadowAlapha();

    void setShadowColor(int shadowColor);

    int getShadowColor();

    void setRadius(int radius);

    int getRadius();





}
