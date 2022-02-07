package com.example.diary.frags;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.example.diary.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.Collection;
import java.util.HashSet;

public class CalendarDecorator implements DayViewDecorator {

    private final int color;
    private final HashSet<CalendarDay> set;
    Drawable drawable;

    public CalendarDecorator(Context context, int color, HashSet<CalendarDay> dates) {

        this.set = dates;
        this.color = color;
        this.drawable = ContextCompat.getDrawable(context, R.drawable.tick_icon);
        drawable.setTint(-7617718);

    }


    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return set.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setBackgroundDrawable(drawable);
    }
}
