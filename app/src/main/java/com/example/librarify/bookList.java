package com.example.librarify;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class bookList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        getSupportActionBar().setTitle("My Books");
    }
}
