package com.example.xiaojin20135.basemodule.util.ui;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.example.xiaojin20135.basemodule.R;
import com.example.xiaojin20135.basemodule.util.LogUtils;


public class ResHelper {
    private static final String TAG = "ResHelper";
    private static TypedValue sTmpValue;

    public static float getAttrFloatValue(Context context, int attr) {
        return getAttrFloatValue(context.getTheme(), attr);
    }

    public static float getAttrFloatValue(Resources.Theme theme, int attr) {
        if (sTmpValue == null) {
            sTmpValue = new TypedValue();
        }
        if (!theme.resolveAttribute(attr, sTmpValue, true)) {
            return 0;
        }
        return sTmpValue.getFloat();
    }

    public static int getAttrColor(Context context, int attrRes) {
        return getAttrColor(context.getTheme(), attrRes);
    }

    public static int getAttrColor(Resources.Theme theme, int attr) {
        if (sTmpValue == null) {
            sTmpValue = new TypedValue();
        }
        if (!theme.resolveAttribute(attr, sTmpValue, true)) {
            return 0;
        }
        if (sTmpValue.type == TypedValue.TYPE_ATTRIBUTE) {
            return getAttrColor(theme, sTmpValue.data);
        }
        return sTmpValue.data;
    }

    @Nullable
    public static ColorStateList getAttrColorStateList(Context context, int attrRes) {
        return getAttrColorStateList(context, context.getTheme(), attrRes);
    }

    @Nullable
    public static ColorStateList getAttrColorStateList(Context context, Resources.Theme theme, int attr) {
        if (attr == 0) {
            return null;
        }
        if (sTmpValue == null) {
            sTmpValue = new TypedValue();
        }
        if (!theme.resolveAttribute(attr, sTmpValue, true)) {
            return null;
        }
        if (sTmpValue.type >= TypedValue.TYPE_FIRST_COLOR_INT
                && sTmpValue.type <= TypedValue.TYPE_LAST_COLOR_INT) {
            return ColorStateList.valueOf(sTmpValue.data);
        }
        if (sTmpValue.type == TypedValue.TYPE_ATTRIBUTE) {
            return getAttrColorStateList(context, theme, sTmpValue.data);
        }
        if (sTmpValue.resourceId == 0) {
            return null;
        }
        return ContextCompat.getColorStateList(context, sTmpValue.resourceId);
    }

    @Nullable
    public static Drawable getAttrDrawable(Context context, int attr) {
        return getAttrDrawable(context, context.getTheme(), attr);
    }

    @Nullable
    public static Drawable getAttrDrawable(Context context, Resources.Theme theme, int attr) {
        if (attr == 0) {
            return null;
        }
        if (sTmpValue == null) {
            sTmpValue = new TypedValue();
        }
        if (!theme.resolveAttribute(attr, sTmpValue, true)) {
            return null;
        }
        if (sTmpValue.type >= TypedValue.TYPE_FIRST_COLOR_INT
                && sTmpValue.type <= TypedValue.TYPE_LAST_COLOR_INT) {
            return new ColorDrawable(sTmpValue.data);
        }
        if (sTmpValue.type == TypedValue.TYPE_ATTRIBUTE) {
            return getAttrDrawable(context, theme, sTmpValue.data);
        }

        if (sTmpValue.resourceId != 0) {
            return DrawableHelper.getVectorDrawable(context, sTmpValue.resourceId);
        }
        return null;
    }

    @Nullable
    public static Drawable getAttrDrawable(Context context, TypedArray typedArray, int index) {
        TypedValue value = typedArray.peekValue(index);
        if (value != null) {
            if (value.type != TypedValue.TYPE_ATTRIBUTE && value.resourceId != 0) {
                return DrawableHelper.getVectorDrawable(context, value.resourceId);
            }
        }
        return null;
    }

    public static int getAttrDimen(Context context, int attrRes) {
        if (sTmpValue == null) {
            sTmpValue = new TypedValue();
        }
        if (!context.getTheme().resolveAttribute(attrRes, sTmpValue, true)) {
            LogUtils.d(TAG,"getAttrDimen  is 0");
            return 0;
        }
        return TypedValue.complexToDimensionPixelSize(sTmpValue.data, DisplayHelper.getDisplayMetrics(context));
    }

    @Nullable
    public static String getAttrString(Context context, int attrRes) {
        if (sTmpValue == null) {
            sTmpValue = new TypedValue();
        }
        if (!context.getTheme().resolveAttribute(attrRes, sTmpValue, true)) {
            return null;
        }
        CharSequence str = sTmpValue.string;
        return str == null ? null : str.toString();
    }

    public static int getAttrInt(Context context, int attrRes) {
        if (sTmpValue == null) {
            sTmpValue = new TypedValue();
        }
        context.getTheme().resolveAttribute(attrRes, sTmpValue, true);
        return sTmpValue.data;
    }


    public static void assignTextViewWithAttr(TextView textView, int attrRes) {
        TypedArray a = textView.getContext().obtainStyledAttributes(null, R.styleable.TextCommonStyleDef, attrRes, 0);
        int count = a.getIndexCount();
        int paddingLeft = textView.getPaddingLeft(), paddingRight = textView.getPaddingRight(),
                paddingTop = textView.getPaddingTop(), paddingBottom = textView.getPaddingBottom();
        for (int i = 0; i < count; i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.TextCommonStyleDef_android_gravity) {
                textView.setGravity(a.getInt(attr, -1));
            } else if (attr == R.styleable.TextCommonStyleDef_android_textColor) {
                textView.setTextColor(a.getColorStateList(attr));
            } else if (attr == R.styleable.TextCommonStyleDef_android_textSize) {
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, a.getDimensionPixelSize(attr, 0));
            } else if (attr == R.styleable.TextCommonStyleDef_android_paddingLeft) {
                paddingLeft = a.getDimensionPixelSize(attr, 0);
            } else if (attr == R.styleable.TextCommonStyleDef_android_paddingRight) {
                paddingRight = a.getDimensionPixelSize(attr, 0);
            } else if (attr == R.styleable.TextCommonStyleDef_android_paddingTop) {
                paddingTop = a.getDimensionPixelSize(attr, 0);
            } else if (attr == R.styleable.TextCommonStyleDef_android_paddingBottom) {
                paddingBottom = a.getDimensionPixelSize(attr, 0);
            } else if (attr == R.styleable.TextCommonStyleDef_android_singleLine) {
                textView.setSingleLine(a.getBoolean(attr, false));
            } else if (attr == R.styleable.TextCommonStyleDef_android_ellipsize) {
                int ellipsize = a.getInt(attr, 3);
                switch (ellipsize) {
                    case 1:
                        textView.setEllipsize(TextUtils.TruncateAt.START);
                        break;
                    case 2:
                        textView.setEllipsize(TextUtils.TruncateAt.MIDDLE);
                        break;
                    case 3:
                        textView.setEllipsize(TextUtils.TruncateAt.END);
                        break;
                    case 4:
                        textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                        break;
                }
            } else if (attr == R.styleable.TextCommonStyleDef_android_maxLines) {
                textView.setMaxLines(a.getInt(attr, -1));
            } else if (attr == R.styleable.TextCommonStyleDef_android_background) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
                    ViewHelper.setBackgroundKeepingPadding(textView, a.getDrawable(attr));
                }
            } else if (attr == R.styleable.TextCommonStyleDef_android_lineSpacingExtra) {
                textView.setLineSpacing(a.getDimensionPixelSize(attr, 0), 1f);
            } else if (attr == R.styleable.TextCommonStyleDef_android_drawablePadding) {
                textView.setCompoundDrawablePadding(a.getDimensionPixelSize(attr, 0));
            } else if (attr == R.styleable.TextCommonStyleDef_android_textColorHint) {
                textView.setHintTextColor(a.getColor(attr, 0));
            } else if (attr == R.styleable.TextCommonStyleDef_android_textStyle) {
                int styleIndex = a.getInt(attr, -1);
                textView.setTypeface(null, styleIndex);
            }
        }
        textView.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
        a.recycle();
    }
    


}
