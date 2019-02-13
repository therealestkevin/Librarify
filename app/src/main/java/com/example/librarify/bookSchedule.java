package com.example.librarify;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.applandeo.materialcalendarview.CalendarView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class bookSchedule extends AppCompatActivity {
    private CalendarView bookSchedule;
    private Toolbar scheduleToolBar;
    private Button readingButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_schedule);
        readingButton = findViewById(R.id.readingButton);
        bookSchedule = (CalendarView) findViewById(R.id.bookSchedule);


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
