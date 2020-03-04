package com.example.xiaojin20135.basemodule.view.widget.popup;

import android.content.Context;
import android.view.View;

public class Popup extends NormalPopup<Popup>{

    public Popup(Context context, int width, int height) {
        super(context, width, height);
    }

    @Override
    public Popup show(View anchor) {
        return super.show(anchor);
    }
}
