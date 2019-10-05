package com.example.calendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.calendar.component.CalendarAttr;
import com.example.calendar.component.CalendarViewAdapter;
import com.example.calendar.interf.OnSelectDateListener;
import com.example.calendar.model.CalendarDate;
import com.example.calendar.view.Calendar;
import com.example.calendar.view.MonthPager;
import com.example.clothes.ExampleAdapter;
import com.example.clothes.MainActivity;
import com.example.clothes.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ldf on 16/11/4.
 */

@SuppressLint("SetTextI18n")
public class AddSchedule extends AppCompatActivity {
    TextView tvYear;
    TextView tvMonth;
    TextView backToday;
    CoordinatorLayout content;
    MonthPager monthPager;
    RecyclerView rvToDoList;
    TextView scrollSwitch;
    TextView themeSwitch;
    TextView nextMonthBtn;
    TextView lastMonthBtn;

    private ArrayList<Calendar> currentCalendars = new ArrayList<>();
    private CalendarViewAdapter calendarAdapter;
    private OnSelectDateListener onSelectDateListener;
    private int mCurrentPage = MonthPager.CURRENT_DAY_INDEX;
    private Context context;
    private CalendarDate currentDate;
    private boolean initiated = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_add_schedule);
        context = this;
        content = (CoordinatorLayout) findViewById(R.id.content);
        monthPager = (MonthPager) findViewById(R.id.calendar_view);
        monthPager.setViewHeight(Utils.dpi2px(context, 270));
        tvYear = (TextView) findViewById(R.id.show_year_view);
        tvMonth = (TextView) findViewById(R.id.show_month_view);
        backToday = (TextView) findViewById(R.id.back_today_button);
        scrollSwitch = (TextView) findViewById(R.id.scroll_switch);
        themeSwitch = (TextView) findViewById(R.id.theme_switch);
        nextMonthBtn = (TextView) findViewById(R.id.next_month);
        lastMonthBtn = (TextView) findViewById(R.id.last_month);
        rvToDoList = (RecyclerView) findViewById(R.id.list);
        rvToDoList.setHasFixedSize(true);
        rvToDoList.setLayoutManager(new LinearLayoutManager(this));
        rvToDoList.setAdapter(new ExampleAdapter(this));
        initCurrentDate();
        initCalendarView();
        initToolbarClickListener();
        Log.e("example","OnCreated");
    }

    /**
     * @return void
     */
    @Override
    //選擇月份
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && !initiated) {
            refreshMonthPager();
            initiated = true;
        }
    }

    /*
    @Override
    protected void onResume() {
        super.onResume();
    }
     */
    //監聽日期按鍵
    private void initToolbarClickListener() {
        backToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickBackToDayBtn();
            }
        });
        scrollSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (calendarAdapter.getCalendarType() == CalendarAttr.CalendarType.WEEK) {
                    Utils.scrollTo(content, rvToDoList, monthPager.getViewHeight(), 200);
                    calendarAdapter.switchToMonth();
                } else {
                    Utils.scrollTo(content, rvToDoList, monthPager.getCellHeight(), 200);
                    calendarAdapter.switchToWeek(monthPager.getRowIndex());
                }
            }
        });
        themeSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshSelectBackground();
            }
        });
        nextMonthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                monthPager.setCurrentItem(monthPager.getCurrentPosition() + 1);
            }
        });
        lastMonthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                monthPager.setCurrentItem(monthPager.getCurrentPosition() - 1);
            }
        });
    }

   //現在日期
    private void initCurrentDate() {
        currentDate = new CalendarDate();
        tvYear.setText(currentDate.getYear() + "年");
                tvMonth.setText(currentDate.getMonth() + "");
    }

    //使用者自選定日期
    private void initCalendarView() {
        initListener();
        CustomDayView customDayView = new CustomDayView(context, R.layout.custom_day);
        calendarAdapter = new CalendarViewAdapter(
                context,
                onSelectDateListener,
                CalendarAttr.WeekArrayType.Monday,
                customDayView);
        calendarAdapter.setOnCalendarTypeChangedListener(new CalendarViewAdapter.OnCalendarTypeChanged() {
            @Override
            public void onCalendarTypeChanged(CalendarAttr.CalendarType type) {
                rvToDoList.scrollToPosition(0);
            }
        });
        initMarkData();
        initMonthPager();
    }
////設定數據
    private void initMarkData() {
        HashMap<String, String> markData = new HashMap<>();
        markData.put("2018-8-9", "1");
        markData.put("2018-7-9", "0");
        markData.put("2018-6-9", "1");
        markData.put("2018-6-10", "0");
        calendarAdapter.setMarkData(markData);
    }

    private void initListener() {
        onSelectDateListener = new OnSelectDateListener() {
            @Override
            public void onSelectDate(CalendarDate date) {
                refreshClickDate(date);
            }

            @Override
            public void onSelectOtherMonth(int offset) {
                monthPager.selectOtherMonth(offset);
            }
        };
    }

//年份設定
    private void refreshClickDate(CalendarDate date) {
        currentDate = date;
        tvYear.setText(date.getYear() + "年");
                tvMonth.setText(date.getMonth() + "");
    }

    /**
     *
     * @return void
     */
    private void initMonthPager() {
        monthPager.setAdapter(calendarAdapter);
        monthPager.setCurrentItem(MonthPager.CURRENT_DAY_INDEX);
        monthPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                position = (float) Math.sqrt(1 - Math.abs(position));
                page.setAlpha(position);
            }
        });
        monthPager.addOnPageChangeListener(new MonthPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPage = position;
                currentCalendars = calendarAdapter.getPagers();
                if (currentCalendars.get(position % currentCalendars.size()) != null) {
                    CalendarDate date = currentCalendars.get(position % currentCalendars.size()).getSeedDate();
                    currentDate = date;
                    tvYear.setText(date.getYear() + "月");
                            tvMonth.setText(date.getMonth() + "");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    public void onClickBackToDayBtn() {
        refreshMonthPager();
    }

    private void refreshMonthPager() {
        CalendarDate today = new CalendarDate();
        calendarAdapter.notifyDataChanged(today);
        tvYear.setText(today.getYear() + "日");
                tvMonth.setText(today.getMonth() + "");
    }

    private void refreshSelectBackground() {
        //ThemeDayView themeDayView = new ThemeDayView(context, R.layout.custom_day_focus);
        //calendarAdapter.setCustomDayRenderer(themeDayView);
        calendarAdapter.notifyDataSetChanged();
        calendarAdapter.notifyDataChanged(new CalendarDate());
    }

    // 回到主頁按鈕
    public void toHome(View view) {
        Intent intent = new Intent();
        intent.setClass( AddSchedule.this  , MainActivity.class);
        startActivity(intent);
        AddSchedule.this.finish();
    }

    // 重新整理按鈕
    public void reLoad(View view) {
        Intent intent=new Intent(this, AddSchedule.class);
        startActivity(intent);
        finish(); // 關閉此檔案
        overridePendingTransition(0, 0);
    }
}

