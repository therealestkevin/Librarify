package com.example.librarify;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private Button scanBtn;
    private Button viewBookBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scanBtn = (Button) findViewById(R.id.scanBtn);
        viewBookBtn = (Button) findViewById(R.id.viewBookBtn);
        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent scanIntent = new Intent(getApplicationContext(),cameraCapture.class);
                startActivity(scanIntent);
            }
        });

        viewBookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bookIntent = new Intent(getApplicationContext(),bookList.class);
                startActivity(bookIntent);
            }
        });


    }
}
