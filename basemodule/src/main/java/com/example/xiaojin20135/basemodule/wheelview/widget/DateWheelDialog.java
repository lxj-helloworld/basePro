package com.example.xiaojin20135.basemodule.wheelview.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.xiaojin20135.basemodule.R;
import com.example.xiaojin20135.basemodule.util.TimeMethods;
import com.example.xiaojin20135.basemodule.wheelview.widget.adapters.CalendarTextAdapter;
import com.example.xiaojin20135.basemodule.wheelview.widget.views.OnDateListener;
import com.example.xiaojin20135.basemodule.wheelview.widget.views.OnWheelChangedListener;
import com.example.xiaojin20135.basemodule.wheelview.widget.views.OnWheelScrollListener;
import com.example.xiaojin20135.basemodule.wheelview.widget.views.WheelView;

import java.util.ArrayList;

/**
 * Created by lixiaojin on 2018-09-29 8:50.
 * 功能描述：
 */

public class DateWheelDialog extends Dialog implements View.OnClickListener {
    private static final String TAG = "DateWheelDialog";
    private TimeMethods timeMethods = TimeMethods.TIME_METHODS;
    private Context context;
    private TextView wheel_title_TV;
    private WheelView wvYear;
    private WheelView wvMonth;
    private WheelView wvDay;
    private WheelView wvHalfDay;
    private Button date_cancel_btn;
    private Button date_sure_btn;
    private ArrayList<String> arry_years = new ArrayList<String>();
    private ArrayList<String> arry_months = new ArrayList<String>();
    private ArrayList<String> arry_days = new ArrayList<String>();
    private ArrayList<String> arr_halfDatas = new ArrayList<> ();
    private CalendarTextAdapter mYearAdapter;
    private CalendarTextAdapter mMonthAdapter;
    private CalendarTextAdapter mDaydapter;
    private CalendarTextAdapter mHalfDayAdapter;
    private int day;
    private int currentYear = 0;
    private int currentMonth = 0;
    private int currentDay = 0;
    private int currentHalfDay = 0;
    private int maxTextSize = 22;
    private int minTextSize = 16;
    private boolean issetdata = false;
    private String selectYear;
    private String selectMonth;
    private String selectDay;
    private String selectHalfDay;
    private OnDateListener onDateListener;
    private String am = "上午";
    private String pm = "下午";
    private String amValue = "12:00:00";
    private String pmValue = "24:00:00";
    private boolean hideDay = false;
    private boolean hideHalfDay = false;

    public DateWheelDialog (Context context) {
        super (context,R.style.AlertDialogStyle);
        this.context = context;
    }

    public DateWheelDialog (Context context,boolean hideDay) {
        this(context);
        this.hideDay = hideDay;
    }

    public DateWheelDialog (Context context,boolean hideDay,boolean hideHalfDay) {
        this(context,hideDay);
        this.hideHalfDay = hideHalfDay;
    }

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView(R.layout.wheel_date);
        initView ();
        initEvents ();
    }


    private void initView(){
        wheel_title_TV = (TextView)findViewById (R.id.wheel_title_TV);
        wvYear = (WheelView) findViewById(R.id.year_WV);
        wvMonth = (WheelView) findViewById(R.id.month_WV);
        wvDay = (WheelView) findViewById(R.id.day_WV);
        wvHalfDay = (WheelView)findViewById (R.id.day_half_WV);
        date_cancel_btn = (Button) findViewById(R.id.date_cancel_btn);
        date_sure_btn = (Button) findViewById(R.id.date_sure_btn);

        if(hideDay){
            wvDay.setVisibility(View.GONE);
        }

        if(hideHalfDay){
            wvHalfDay.setVisibility(View.GONE);
        }

        //如果没有传递默认日期，设置为当前日期
        if (!issetdata) {
            currentYear = timeMethods.getYear();
            currentMonth =timeMethods.getMonth();
            currentDay = timeMethods.getDay();
            currentHalfDay = 0;
            setDate (currentYear,currentMonth,currentDay,currentHalfDay);
        }

        //初始化年份数组
        initYears();
        mYearAdapter = new CalendarTextAdapter(context, arry_years, setYear(currentYear), maxTextSize, minTextSize);
        wvYear.setViewAdapter(mYearAdapter);
        wvYear.setVisibleItems (3);
        wvYear.setCurrentItem(setYear(currentYear));
        wvYear.setCyclic (true); //循环滚动

        initMonths();
        mMonthAdapter = new CalendarTextAdapter(context, arry_months, setMonth(currentMonth), maxTextSize, minTextSize);
        wvMonth.setViewAdapter(mMonthAdapter);
        wvMonth.setCurrentItem(setMonth(currentMonth));
        wvMonth.setCyclic (true); //循环滚动

        //生成该月份天数数组
        initDays(day);
        mDaydapter = new CalendarTextAdapter(context, arry_days, currentDay - 1, maxTextSize, minTextSize);
        wvDay.setViewAdapter(mDaydapter);
        wvDay.setCurrentItem(currentDay - 1);
        wvDay.setCyclic (true); //循环滚动

        arr_halfDatas.add (am);
        arr_halfDatas.add (pm);
        mHalfDayAdapter = new CalendarTextAdapter (context,arr_halfDatas,currentHalfDay, maxTextSize, minTextSize);
        wvHalfDay.setViewAdapter (mHalfDayAdapter);
        wvHalfDay.setCurrentItem (0);
        wvHalfDay.setCyclic (true); //循环滚动
    }

    @Override
    public void onClick (View v) {
        if (v == date_sure_btn) {
            if (onDateListener != null) {
                if(selectHalfDay.equals (am)){
                    onDateListener.onClick(selectYear, selectMonth, selectDay,amValue);
                }else{
                    onDateListener.onClick(selectYear, selectMonth, selectDay,pmValue);
                }
            }
        }
        dismiss();
    }

    private void initEvents(){
        date_sure_btn.setOnClickListener(this);
        date_cancel_btn.setOnClickListener(this);
        //年变化监听器
        wvYear.addChangingListener(new OnWheelChangedListener () {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                yearChangeDone (wheel);
            }
        });
        //滚动监听器
        wvYear.addScrollingListener (new OnWheelScrollListener () {
            @Override
            public void onScrollingStarted (WheelView wheel) {

            }
            @Override
            public void onScrollingFinished (WheelView wheel) {
                yearChangeDone (wheel);
            }
        });
        //月变化
        wvMonth.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                monthChangeDone (wheel);
            }
        });
        wvMonth.addScrollingListener (new OnWheelScrollListener () {
            @Override
            public void onScrollingStarted (WheelView wheel) {

            }
            @Override
            public void onScrollingFinished (WheelView wheel) {
                monthChangeDone (wheel);
            }
        });
        //天变化
        wvDay.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                dayChangeDone(wheel);
            }
        });
        wvDay.addScrollingListener (new OnWheelScrollListener () {
            @Override
            public void onScrollingStarted (WheelView wheel) {

            }
            @Override
            public void onScrollingFinished (WheelView wheel) {
                dayChangeDone (wheel);
            }
        });
        //上午下午变化
        wvHalfDay.addChangingListener (new OnWheelChangedListener () {
            @Override
            public void onChanged (WheelView wheel, int oldValue, int newValue) {
                haflChangeDone (wheel);
            }
        });
        wvHalfDay.addScrollingListener (new OnWheelScrollListener () {
            @Override
            public void onScrollingStarted (WheelView wheel) {
            }
            @Override
            public void onScrollingFinished (WheelView wheel) {
                Log.d (TAG,"wvHalfDay 滚动结束");
               haflChangeDone (wheel);
            }
        });
    }

    /**
     * @author lixiaojin
     * @createon 2018-09-29 11:09
     * @Describe 初始化年份数组,年份从大到小排列
     */
    public void initYears() {
        int start = timeMethods.getYear() - 10;
        int end = timeMethods.getYear() + 1;
        for (int i = end; i > start; i--) {
            arry_years.add(i + "");
        }
    }

    /**
     * @author lixiaojin
     * @createon 2018-09-29 11:11
     * @Describe 初始化月份数组
     */
    public void initMonths() {
        arry_months.clear();
        for (int i = 1; i <= 12; i++) {
            arry_months.add(i + "");
        }
        Log.d (TAG,"月份数组：" + arry_months.toString ());
    }

    /**
     * @author lixiaojin
     * @createon 2018-09-29 11:11
     * @Describe 初始化天数组
     * day为该月份天数
     */
    public void initDays(int days) {
        arry_days.clear();
        for (int i = 1; i <= days; i++) {
            arry_days.add(i + "");
        }
    }


    /**
     * @author lixiaojin
     * @createon 2018-09-29 9:17
     * @Describe 设置指定的年月日
     */
    public void setDate(int year, int month, int day,int currentHalfDay) {
        selectYear = year + "";
        selectMonth = month + "";
        selectDay = day + "";
        if(currentHalfDay == 0){
            selectHalfDay = am;
        }else{
            selectHalfDay = pm;
        }
        issetdata = true;
        this.currentYear = year;
        this.currentMonth = month;
        this.currentDay = day;
        this.currentHalfDay = currentHalfDay;
        calDays(year, month);
    }

    /**
     * @author lixiaojin
     * @createon 2018-09-29 9:26
     * @Describe 设置年份，生成的年份数组从大到小排列
     */
    public int setYear(int year) {
        int start = timeMethods.getYear() - 10;
        int end = timeMethods.getYear() + 1;
        int yearIndex = 0;
        for (int i = end; i > start; i--) {
            if (i == year) {
                return yearIndex;
            }
            yearIndex++;
        }
        return yearIndex;
    }


    /**
     * @author lixiaojin
     * @createon 2018-09-29 9:27
     * @Describe 设置月份
     */
    public int setMonth(int month) {
        Log.d (TAG,"month = " + month);
        int monthIndex = 0;
        calDays(currentYear, month);
        for (int i = 1; i < month; i++) {
            if (month == i) {
                return monthIndex;
            } else {
                monthIndex++;
            }
        }
        return monthIndex;
    }

    /**
     * @author lixiaojin
     * @createon 2018-09-29 9:29
     * @Describe 计算每月多少天
     */
    public void calDays(int year, int month) {
        boolean leayyear = false;
        if (year % 4 == 0 && year % 100 != 0) {
            leayyear = true;
        } else {
            leayyear = false;
        }
        for (int i = 1; i <= 12; i++) {
            switch (month) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    this.day = 31;
                    break;
                case 2:
                    if (leayyear) {
                        this.day = 29;
                    } else {
                        this.day = 28;
                    }
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    this.day = 30;
                    break;
            }
        }
    }

    /**
     * @author lixiaojin
     * @createon 2018-09-29 9:30
     * @Describe 设置字体大小
     */
    public void setTextviewSize(String currentItemText, CalendarTextAdapter adapter) {
        ArrayList<View> arrayList = adapter.getTestViews();
        int size = arrayList.size();
        Log.d (TAG,"size = " + size);
        Log.d (TAG,"currentItemText = " + currentItemText);
        String currentText;
        for (int i = 0; i < size; i++) {
            TextView textvew = (TextView) arrayList.get(i);
            currentText = textvew.getText().toString();
            if (currentItemText.equals(currentText)) {
                Log.d (TAG,"大 currentText = " + currentText);
                textvew.setTextSize(maxTextSize);
            } else {
                textvew.setTextSize(minTextSize);
                Log.d (TAG,"小 currentText = " + currentText);
            }
        }
    }

    public void setOnDateListener (OnDateListener onDateListener) {
        this.onDateListener = onDateListener;
    }

    /**
     * @author lixiaojin
     * @createon 2018-09-29 14:38
     * @Describe 年选择完成
     */
    private void yearChangeDone(WheelView wheelView){
        String currentText = (String) mYearAdapter.getItemText(wheelView.getCurrentItem());
        selectYear = currentText;
        setTextviewSize(currentText, mYearAdapter);
        currentYear = Integer.parseInt(currentText);
        setYear(currentYear);
        updateDate();
    }

    /**
     * @author lixiaojin
     * @createon 2018-09-29 14:38
     * @Describe 月份改变完成
     */
    private void monthChangeDone(WheelView wheelView){
        String currentText = (String) mMonthAdapter.getItemText(wheelView.getCurrentItem());
        selectMonth = currentText;
        setTextviewSize(currentText, mMonthAdapter);
        setMonth(Integer.parseInt(currentText));
        //月份变化时重新生成天数数组
        initDays(day);
        mDaydapter = new CalendarTextAdapter(context, arry_days, 0, maxTextSize, minTextSize);
        wvDay.setViewAdapter(mDaydapter);
        wvDay.setCurrentItem(0);
        updateDate();

    }

    /**
     * @author lixiaojin
     * @createon 2018-09-29 14:39
     * @Describe 日改变完成
     */
    private void dayChangeDone(WheelView wheelView){
        String currentText = (String) mDaydapter.getItemText(wheelView.getCurrentItem());
        setTextviewSize(currentText, mDaydapter);
        selectDay = currentText;
        updateDate();
    }

    /**
     * @author lixiaojin
     * @createon 2018-09-29 14:39
     * @Describe 半天改变完成
     */
    private void haflChangeDone(WheelView wheelView){
        String currentText = (String)mHalfDayAdapter.getItemText (wheelView.getCurrentItem ());
        setTextviewSize (currentText,mHalfDayAdapter);
        selectHalfDay = currentText;
        updateDate();
    }

    private void updateDate(){

        String result = "";

        if(hideDay){
            result = selectYear + "-" + selectMonth;
        }else if(hideHalfDay && !hideDay){
            result = selectYear + "-" + selectMonth + "-" + selectDay;
        }else{
            result = selectYear + "-" + selectMonth + "-" + selectDay + " " + selectHalfDay;
        }

        wheel_title_TV.setText (result);

    }

}
