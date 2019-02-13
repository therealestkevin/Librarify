package com.example.librarify;

import android.os.Bundle;
import android.widget.CalendarView;

import androidx.appcompat.app.AppCompatActivity;

public class bookSchedule extends AppCompatActivity {
    private CalendarView bookSchedule;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_schedule);
        bookSchedule = findViewById(R.id.bookSchedule);
    }
}
