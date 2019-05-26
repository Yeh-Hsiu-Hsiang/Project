package com.example.calendar.interf;

import android.graphics.Canvas;

import com.example.calendar.component.State;
import com.example.calendar.model.CalendarDate;
import com.example.calendar.view.Day;
import com.example.calendar.view.DayView;

/**
 * Created by ldf on 17/6/26.
 */

public interface IDayRenderer {

    void refreshContent();

    void drawDay(Canvas canvas, Day day);

    IDayRenderer copy();

}
