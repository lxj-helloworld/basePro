package com.example.xiaojin20135.basemodule.view.widget.link;

import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.method.Touch;
import android.view.MotionEvent;
import android.widget.TextView;

public class LinkTouchMovementMethod extends LinkMovementMethod {

    @Override
    public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
        return sHelper.onTouchEvent(widget, buffer, event) || Touch.onTouchEvent(widget, buffer, event);
    }

    public static MovementMethod getInstance() {
        if (sInstance == null)
            sInstance = new LinkTouchMovementMethod();

        return sInstance;
    }

    private static LinkTouchMovementMethod sInstance;
    private static LinkTouchDecorHelper sHelper = new LinkTouchDecorHelper();

}

