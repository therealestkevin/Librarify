package com.example.librarify;

import android.os.Bundle;
import android.view.View;

import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class bookSchedule extends AppCompatActivity {
    private MaterialCalendarView bookSchedule;
    private Toolbar scheduleToolBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_schedule);
        bookSchedule = (MaterialCalendarView) findViewById(R.id.calendarView);
        scheduleToolBar = (Toolbar) findViewById(R.id.scheduleToolBar);
        setSupportActionBar( scheduleToolBar);
        scheduleToolBar.setTitle("Book Schedule");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        scheduleToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
