package com.android.mylibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shawnlin.numberpicker.NumberPicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static android.content.ContentValues.TAG;

/**
 * Created by JanD4rk on 12/29/2017 4:11 PM .
 */

public class LibraryDialog extends FrameLayout {
    NumberPicker day, month, year;
    List<String> listYears;
    String[] months;
    RelativeLayout confirm, cancel;
    View main, divider,toolbar;
    String[] years;
    TextView backToday,cancelTv,confirmTv;
    boolean isSpinning1 = true, isSpinning3 = true, isSpinning2 = true;
    boolean todayPressed = false;
    Integer mainColor, dividerColor, textColor;
    Calendar calendar;
    TextPaint paint;
    ConfirmListener confirmListener;
    OnClickListener cancelClickListener;
    CancelListener cancelListener;
    Integer selectedDay;
    private static final int START_YEAR = 1990;

    public interface ConfirmListener {
        void onConfirm(int year, int month, int day);
    }

    interface CancelListener {
        void onCancel();
    }

    public LibraryDialog(@NonNull Context context) {

        super(context);
        onCreate(null);
    }

    public LibraryDialog(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        onCreate(attrs);
    }

    public LibraryDialog(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        onCreate(attrs);
    }

    public LibraryDialog(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        onCreate(attrs);
    }


    private void onCreate(AttributeSet attributeSet) {
        paint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.LINEAR_TEXT_FLAG);
        LayoutInflater.from(getContext()).inflate(R.layout.main_layout, this);
        listYears = new ArrayList<>();
        confirm = findViewById(R.id.confirm);
        day = findViewById(R.id.np_days);
        month = findViewById(R.id.np_months);
        year = findViewById(R.id.np_years);
        backToday = findViewById(R.id.back_today);
        cancel = findViewById(R.id.cancel);
        main = findViewById(R.id.main_layout);
        divider = findViewById(R.id.divider);
        toolbar=findViewById(R.id.toolbar);
        cancelTv=findViewById(R.id.cancel_text);
        confirmTv=findViewById(R.id.confirm_text);
        setColors(mainColor,dividerColor,textColor);

        TypedArray ta = getContext().obtainStyledAttributes(attributeSet, R.styleable.LibraryDialog);
        if (ta != null) {

            float f = ta.getDimension(R.styleable.LibraryDialog_fontSize, 10f);
            if (f != 10f) {
                //test
//                  day.setSelectedTextSize(f);
            }

        }


        calendar = Calendar.getInstance();


        int a = calendar.get(Calendar.YEAR) - 1990 + 2;

        months = new String[12];
        int aa = 0;

        while (aa < 12) {
            String statusName = "month_" + aa;
            months[aa] = getContext().getResources().getString(getResources().getIdentifier(statusName, "string", getContext().getPackageName()));
            aa++;
        }
        ;

//
//

        years = new String[a];
        for (int b = 0; b < a; b++) {
            years[b] = 1990 + b + "";
        }
        year.setMaxValue(a);
        year.setValue(a - 1);
        year.setWrapSelectorWheel(false);
        year.setDisplayedValues(years);
        month.setMaxValue(12);
        month.setValue(calendar.get(Calendar.MONTH) + 1);
        month.setDisplayedValues(months);
        day.setMaxValue(getMonthDays(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1));
        day.setValue(calendar.get(Calendar.DAY_OF_MONTH));
        month.setOnValueChangedListener(monthListener);
        year.setOnValueChangedListener(yearListener);
        backToday.setOnClickListener(backButtonListener);
        cancel.setOnClickListener(cancelButtonClickListener);
        confirm.setOnClickListener(confirmButtonClickListener);

        day.setOnScrollListener(new NumberPicker.OnScrollListener() {
            @Override
            public void onScrollStateChange(NumberPicker view, int scrollState) {
                isSpinning1 = scrollState == 0;
                if (todayPressed & isSpinning1) {
                    backToday.performClick();
                }
            }
        });
        month.setOnScrollListener(new NumberPicker.OnScrollListener() {
            @Override
            public void onScrollStateChange(NumberPicker view, int scrollState) {
                isSpinning2 = scrollState == 0;
                if (todayPressed & isSpinning2) {
                    backToday.performClick();
                }
            }
        });
        year.setOnScrollListener(new NumberPicker.OnScrollListener() {
            @Override
            public void onScrollStateChange(NumberPicker view, int scrollState) {
                isSpinning3 = scrollState == 0;
                if (todayPressed & isSpinning3) {
                    backToday.performClick();
                }
            }
        });


    }

    public void setSelectedDay(Integer selectedDay) {
        Calendar calendar = Calendar.getInstance();
        if (selectedDay != null) {
            long millis = calendar.getTimeInMillis();
            millis += TimeUnit.DAYS.toMillis(selectedDay);
            calendar.setTimeInMillis(millis);
            year.setValue(year.getMaxValue() - (this.calendar.get(Calendar.YEAR) - calendar.get(Calendar.YEAR)) - 1);
            month.setValue(calendar.get(Calendar.MONTH) + 1);
            day.setMaxValue(getMonthDays(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1));
            day.setValue(calendar.get(Calendar.DAY_OF_MONTH));
        }

    }

    /**
     * Back button listeners
     */

    public void setColors(Integer mainColor, Integer dividerColor, Integer textColor) {
        if (textColor != null)
            if (day != null & year != null & month != null) {
                day.setTextColor(textColor);
                year.setTextColor(textColor);
                month.setTextColor(textColor);

                day.setSelectedTextColor(textColor);
                year.setSelectedTextColor(textColor);
                month.setSelectedTextColor(textColor);

                cancelTv.setTextColor(textColor);
                confirmTv.setText(textColor);

            } else
                this.textColor = textColor;

        if (mainColor != null)
            if (main != null) {
                main.setBackgroundColor(mainColor);
                toolbar.setBackgroundColor(mainColor);
            }
            else this.mainColor = mainColor;

        if (dividerColor != null)
            if (divider != null)
                divider.setBackgroundColor(dividerColor);
            else this.dividerColor=dividerColor;


    }

    private OnClickListener backButtonListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            todayPressed = true;
            if (isSpinning1 & isSpinning2 & isSpinning3) {
                Calendar calendar = Calendar.getInstance();
                int thisYear = calendar.get(Calendar.YEAR);
                int thisMonth = calendar.get(Calendar.MONTH);
                int thisDay = calendar.get(Calendar.DAY_OF_MONTH);

                int monthDayCount = getMonthDays(thisYear, thisMonth + 1);
                month.setValue(thisMonth + 1);
                year.setValue(year.getMaxValue() - 1);
                day.setMaxValue(monthDayCount);
                day.setValue(thisDay);
                todayPressed = false;
            }


        }
    };
    /**
     * Number Picker listeners
     */
    private NumberPicker.OnValueChangeListener monthListener = new NumberPicker.OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

            int selectedYear = Integer.valueOf(years[year.getValue() - 1]);
            int selectedDay = getMonthDays(selectedYear, newVal);
            int oldDay = day.getValue();
            day.setMaxValue(selectedDay);
            day.setValue(getSelectedDayValue(selectedDay, oldDay));

        }
    };

    private NumberPicker.OnValueChangeListener yearListener = new NumberPicker.OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

            int selectedYear = Integer.valueOf(years[year.getValue() - 1]);
            int selectedDay = getMonthDays(selectedYear, month.getValue());
            int oldDay = day.getValue();

            day.setMaxValue(selectedDay);
            day.setValue(getSelectedDayValue(selectedDay, oldDay));

        }
    };


    private int getMonthDays(int year, int month) {
        int a = 0;

        switch (month) {
            case 1:
                a = 31;
                break;
            case 2:
                a = (year % 4 == 0 ? 29 : 28);
                break;
            case 3:
                a = 31;
                break;
            case 4:
                a = 30;
                break;
            case 5:
                a = 31;
                break;
            case 6:
                a = 30;
                break;
            case 7:
                a = 31;
                break;
            case 8:
                a = 31;
                break;
            case 9:
                a = 30;
                break;
            case 10:
                a = 31;
                break;
            case 11:
                a = 30;
                break;
            case 12:
                a = 31;
                break;
        }


        return a;
    }

    private int getSelectedDayValue(int monthDays, int oldDay) {
        return (monthDays >= oldDay ? oldDay : monthDays);
    }

    private View.OnClickListener cancelButtonClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (cancelListener != null) {
                cancelListener.onCancel();
            }
        }
    };

    private View.OnClickListener confirmButtonClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (confirmListener != null) {
                confirmListener.onConfirm(year.getValue(), month.getValue(), day.getValue());
            }
        }
    };


    public void setOnCancelListener(CancelListener cancelListener) {
        this.cancelListener = cancelListener;
    }


    public void setOnConfirmListener(ConfirmListener confirmListener) {
        this.confirmListener = confirmListener;
    }

    private float pxToSp(float px) {
        return px / getResources().getDisplayMetrics().scaledDensity;
    }
}
