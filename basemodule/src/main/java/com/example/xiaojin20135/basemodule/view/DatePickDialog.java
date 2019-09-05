package com.example.xiaojin20135.basemodule.view;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.xiaojin20135.basemodule.R;
import com.example.xiaojin20135.basemodule.listener.DatePickListener;

import java.util.Calendar;

/**
 * Created by lixiaojin on 2018-07-19 18:34.
 * 功能描述：
 * 时间选择器视图
 */

public class DatePickDialog {

    private static final String TAG = "DatePickDialog";
    private Context context;
    private Dialog dialog;
    private LinearLayout lLayout_bg;
    private TextView txt_title;
    private ImageView time_pick_IV;
    private DatePicker date_pick_view;
    private TimePicker time_pick_view;
    private Button btn_neg;
    private Button btn_pos;
    private ImageView img_line;
    private Display display;
    private boolean showTitle = false;
    private boolean showPosBtn = false;
    private boolean showNegBtn = false;
    //显示时间
    private boolean showTime = false;
    //仅仅显示年、月
    private boolean showMonthOnly = false;

    //记录的日期
    private String dateValue = "";
    //记录的时间
    private String timeValue = "";


    public DatePickDialog(Context context,boolean showTime) {
        this.context = context;
        this.showTime = showTime;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }


    public DatePickDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(R.layout.date_view_alertdialog, null);

        // 获取自定义Dialog布局中的控件
        lLayout_bg = (LinearLayout) view.findViewById(R.id.lLayout_bg);
        txt_title = (TextView) view.findViewById(R.id.txt_title);
        txt_title.setVisibility(View.GONE);
        date_pick_view = (DatePicker) view.findViewById(R.id.date_pick_view);

        //时间选择器
        time_pick_view = (TimePicker)view.findViewById (R.id.time_pick_view);
        time_pick_IV = (ImageView)view.findViewById (R.id.time_pick_IV);
        if(showTime){
            time_pick_view.setVisibility (View.VISIBLE);
            time_pick_view.setIs24HourView (true);
            time_pick_IV.setVisibility (View.GONE);
        }
        btn_neg = (Button) view.findViewById(R.id.btn_neg);
        btn_neg.setVisibility(View.GONE);
        btn_pos = (Button) view.findViewById(R.id.btn_pos);
        btn_pos.setVisibility(View.GONE);
        img_line = (ImageView) view.findViewById(R.id.img_line);
        img_line.setVisibility(View.GONE);

        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.AlertDialogStyle);
        dialog.setContentView(view);


        //只显示年月
        if(showMonthOnly){
            ((ViewGroup)((ViewGroup)date_pick_view.getChildAt(0)).getChildAt(0)).getChildAt(1).setVisibility(View.GONE);
        }

        // 调整dialog背景大小
        lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (display.getWidth() * 0.85), LinearLayout.LayoutParams.WRAP_CONTENT));
        //初始化时间
        intDateValue();
        //初始化事件
        initEvents();
        return this;
    }

    /**
     * @author lixiaojin
     * @createon 2018-07-19 17:49
     * @Describe 初始化时间值
     */
    private void intDateValue(){
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get (Calendar.HOUR_OF_DAY);
        int minute = c.get (Calendar.MINUTE);
        dateValue = year + "-" + buling((month+1)+"",2) + "-" + buling(day +"",2);
        timeValue = buling (hour+"",2) + ":" + buling (minute+"",2);
    }

    /**
     * @author lixiaojin
     * @createon 2018-07-19 17:50
     * @Describe 初始化事件
     */
    private void initEvents(){
        // 获取当前的年、月、日、小时、分钟
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        date_pick_view.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Log.d(TAG, "year = " + year + "; month = " + buling((monthOfYear++) + "",2) + "; dayOfMonth = " + buling(dayOfMonth + "",2));
                dateValue = year + "-" + buling((monthOfYear++)+"",2) + "-" + buling(dayOfMonth+"",2);
                Log.d(TAG,"dateValue = " + dateValue);
                if(showTime){
                    txt_title.setText(dateValue + " " + timeValue);
                }else{
                    txt_title.setText(dateValue);
                }

            }
        });

        time_pick_view.setOnTimeChangedListener (new TimePicker.OnTimeChangedListener () {
            @Override
            public void onTimeChanged (TimePicker view, int hourOfDay, int minute) {
                timeValue = buling (hourOfDay+"",2) + ":" + buling (minute+"",2);
                if(showTime){
                    txt_title.setText(dateValue + " " + timeValue);
                }else{
                    txt_title.setText(dateValue);
                }
            }
        });
    }


    public DatePickDialog setTitle(String title) {
        showTitle = true;
        if ("".equals(title)) {
            txt_title.setText("选择时间");
        } else {
            txt_title.setText(title);
        }
        return this;
    }


    public DatePickDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public DatePickDialog setPositiveButton(String text, final DatePickListener listener) {
        showPosBtn = true;
        if ("".equals(text)) {
            btn_pos.setText("确定");
        } else {
            btn_pos.setText(text);
        }
        btn_pos.setOnClickListener(new DatePickListener () {
            @Override
            public void onClick(View v) {
                listener.setDateValue (dateValue);
                listener.setTimeValue (timeValue);
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    public DatePickDialog setNegativeButton(String text, final DatePickListener listener) {
        showNegBtn = true;
        if ("".equals(text)) {
            btn_neg.setText("取消");
        } else {
            btn_neg.setText(text);
        }
        btn_neg.setOnClickListener(new DatePickListener () {
            @Override
            public void onClick(View v) {
                listener.setDateValue ("");
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    private void setLayout() {
        if (showTitle) {
            txt_title.setVisibility(View.VISIBLE);
        }
        if (!showPosBtn && !showNegBtn) {
            btn_pos.setText("确定");
            btn_pos.setVisibility(View.VISIBLE);
            btn_pos.setBackgroundResource(R.drawable.alertdialog_single_selector);
            btn_pos.setOnClickListener(new View.OnClickListener () {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }
        if (showPosBtn && showNegBtn) {
            btn_pos.setVisibility(View.VISIBLE);
            btn_pos.setBackgroundResource(R.drawable.alertdialog_right_selector);
            btn_neg.setVisibility(View.VISIBLE);
            btn_neg.setBackgroundResource(R.drawable.alertdialog_left_selector);
            img_line.setVisibility(View.VISIBLE);
        }

        if (showPosBtn && !showNegBtn) {
            btn_pos.setVisibility(View.VISIBLE);
            btn_pos.setBackgroundResource(R.drawable.alertdialog_single_selector);
        }

        if (!showPosBtn && showNegBtn) {
            btn_neg.setVisibility(View.VISIBLE);
            btn_neg.setBackgroundResource(R.drawable.alertdialog_single_selector);
        }
    }

    public void show() {
        setLayout();
        dialog.show();
    }


    public String buling(String str,int len){
        while(str.length() < 2){
            str = "0" + str;
        }
        return str;
    }
}

