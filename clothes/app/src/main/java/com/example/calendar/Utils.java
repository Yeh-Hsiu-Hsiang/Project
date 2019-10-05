/*
 * Copyright (c) 2016.
 * wb-lijinwei.a@alibaba-inc.com
 */

package com.example.calendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Scroller;

import com.example.calendar.component.CalendarAttr;
import com.example.calendar.model.CalendarDate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public final class Utils {

    private static HashMap<String, String> markData = new HashMap<>();

    private Utils() {

    }

    /**
     * 得到某一个月的具體天數
     *
     * @param year  年
     * @param month 月
     * @return int 月所包含的天數
     */
    public static int getMonthDays(int year, int month) {
        if (month > 12) {
            month = 1;
            year += 1;
        } else if (month < 1) {
            month = 12;
            year -= 1;
        }
        int[] monthDays = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int days = 0;
        // 閏年2月29天
        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
            monthDays[1] = 29;
        }
        try {
            days = monthDays[month - 1];
        } catch (Exception e) {
            e.getStackTrace();
        }
        return days;
    }

    public static int getYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public static int getMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    public static int getDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 得到当前月第一天在其周的位置
     *
     * @param year  當前年
     * @param month 當前月
     * @param type  周排列方式 0代表周一作為本周的第一天， 2代表周日作為本周的第一天
     * @return int 本月第一天在其周的位置
     */
    public static int getFirstDayWeekPosition(int year, int month, CalendarAttr.WeekArrayType type) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getDateFromString(year, month));
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (type == CalendarAttr.WeekArrayType.Sunday) {
            return week_index;
        } else {
            week_index = cal.get(Calendar.DAY_OF_WEEK) + 5;
            if (week_index >= 7) {
                week_index -= 7;
            }
        }
        return week_index;
    }

    /**
     * 將yyyy-MM-dd類型的字串轉化為對應的Date對象
     *
     * @param year  當前年
     * @param month 當前月
     * @return Date  對應的Date對象
     */
    @SuppressLint("SimpleDateFormat")
    public static Date getDateFromString(int year, int month) {
        String dateString = year + "-" + (month > 9 ? month : ("0" + month)) + "-01";
        Date date = new Date();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        return date;
    }

    /**
     * 計算日期月與當前月相差的月份數
     *
     * @param year        日期所在年
     * @param month       日期所在月
     * @param currentDate 當前月
     * @return int offset 相差月份數
     */
    public static int calculateMonthOffset(int year, int month, CalendarDate currentDate) {
        int currentYear = currentDate.getYear();
        int currentMonth = currentDate.getMonth();
        int offset = (year - currentYear) * 12 + (month - currentMonth);
        return offset;
    }

    /**
     * 删除方法, 這裡只会删除某个文件夹下的文件，如果傳入的directory是个文件，將不做處理
     *
     * @param context 上下文
     * @param dpi     dp为單位的尺寸
     * @return int 轉化而来的對應像素
     */
    public static int dpi2px(Context context, float dpi) {
        return (int) (context.getResources().getDisplayMetrics().density * dpi + 0.5f);
    }

    /**
     * 得到標記日期數據，可以通過該數據得到標記日期的信息，开发者可自定義格式
     * 目前HashMap<String, String>的组成仅仅是为了DEMO效果
     *
     * @return HashMap<String, String> 標記日期數據
     */
    public static HashMap<String, String> loadMarkData() {
        return markData;
    }

    /**
     * 设置标记日期数据
     *
     * @param data 標記日期數據
     * @return void
     */
    public static void setMarkData(HashMap<String, String> data) {
        markData = data;
    }

    public static void cleanMarkData(){
        markData.clear();
    }

    /**
     * 計算偏移距離
     *
     * @param offset 偏移值
     * @param min    最小偏移值
     * @param max    最大偏移值
     * @return int offset
     */
    private static int calcOffset(int offset, int min, int max) {
        if (offset > max) {
            return max;
        } else if (offset < min) {
            return min;
        } else {
            return offset;
        }
    }

    /**
     * 刪除方法, 這裡只会刪除某個文件夹下的文件，如果傳入的directory是個文件，將不做處理
     *
     * @param child     需要移動的View
     * @param dy        時技偏移量
     * @param minOffset 最小偏移量
     * @param maxOffset 最大偏移量
     * @return void
     */
    public static int scroll(View child, int dy, int minOffset, int maxOffset) {
        final int initOffset = child.getTop();
        int offset = calcOffset(initOffset - dy, minOffset, maxOffset) - initOffset;
        child.offsetTopAndBottom(offset);
        return -offset;
    }

    /**
     * 得到TouchSlop
     *
     * @param context 上下文
     * @return int touchSlop的具體值
     */
    public static int getTouchSlop(Context context) {
        return ViewConfiguration.get(context).getScaledTouchSlop();
    }

    /**
     * 得到目標日期所在周的周日
     *
     * @param seedDate 目標日期
     * @return CalendarDate 所在周周日
     */
    public static CalendarDate getSunday(CalendarDate seedDate) {// TODO: 16/12/12 得到一個CustomDate對象
        Calendar c = Calendar.getInstance();
        String dateString = seedDate.toString();
        Date date = new Date();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        c.setTime(date);
        if (c.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            c.add(Calendar.DAY_OF_MONTH, 7 - c.get(Calendar.DAY_OF_WEEK) + 1);
        }
        return new CalendarDate(c.get(Calendar.YEAR),
                c.get(Calendar.MONTH) + 1,
                c.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * 得到目標日期所在周的周六
     *
     * @param seedDate 目標日期
     * @return CalendarDate 所在周周六
     */
    public static CalendarDate getSaturday(CalendarDate seedDate) {// TODO: 16/12/12 得到一個CustomDate對象
        Calendar c = Calendar.getInstance();
        String dateString = seedDate.toString();
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, 7 - c.get(Calendar.DAY_OF_WEEK));
        return new CalendarDate(c.get(Calendar.YEAR),
                c.get(Calendar.MONTH) + 1,
                c.get(Calendar.DAY_OF_MONTH));
    }

    private static int top;
    private static boolean customScrollToBottom = false;

    /**
     * 判断上一次滑动改變周月日曆是向下滑還是向上滑 向下滑表示切换為月日曆模式 向上滑表示切换為周日曆模式
     *
     * @return boolean 是否是在向下滑動。(true: 已经收縮; false: 已经打開）
     */
    public static boolean isScrollToBottom() {
        return customScrollToBottom;
    }

    /**
     * 設置上一次滑動改變周月日曆是向下滑還是向上滑 向下滑表示切换為月日曆模式 向上滑表示切换為周日曆模式
     *
     * @return void
     */
    public static void setScrollToBottom(boolean customScrollToBottom) {
        Utils.customScrollToBottom = customScrollToBottom;
    }

    /**
     * 通過scrollTo方法完成協調布局的滑動，其中主要使用了ViewCompat.postOnAnimation
     *
     * @param parent   協調布局parent
     * @param child    協調布局協調滑動的child
     * @param y        滑動目標位置y軸數值
     * @param duration 滑動執行時間
     * @return void
     */
    public static void scrollTo(final CoordinatorLayout parent, final RecyclerView child, final int y, int duration) {
        final Scroller scroller = new Scroller(parent.getContext());
        scroller.startScroll(0, top, 0, y - top, duration);   //設置scroller的滾動偏移量
        ViewCompat.postOnAnimation(child, new Runnable() {
            @Override
            public void run() {
                //返回值為boolean，true說明滾動尚未完成，false說明滾動已經完成。
                // 這是一個很重要的方法，通常放在View.computeScroll()中，用来判斷是否滾動是否结束。
                if (scroller.computeScrollOffset()) {
                    int delta = scroller.getCurrY() - child.getTop();
                    child.offsetTopAndBottom(delta);
                    saveTop(child.getTop());
                    parent.dispatchDependentViewsChanged(child);
                    ViewCompat.postOnAnimation(child, this);
                }
            }
        });
    }

    public static void saveTop(int y) {
        top = y;
    }

    public static int loadTop() {
        return top;
    }
}
