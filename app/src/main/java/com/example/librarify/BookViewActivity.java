package com.example.librarify;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class BookViewActivity extends AppCompatActivity {
    private ImageView bookImg;
    private TextView bookDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_view);
        bookImg = (ImageView) findViewById(R.id.bookImage);
        bookDescription = (TextView) findViewById(R.id.bookDescription);

        if(getIntent()!=null && getIntent().getExtras()!=null){
            Bundle bundle = getIntent().getExtras();
           // if(!bundle.getString("picture").equals(null)){
                    byte[] returnB = bundle.getByteArray("picture");
                    Bitmap bmp = BitmapFactory.decodeByteArray(returnB, 0, returnB.length);
                    bookImg.setImageBitmap(bmp);
           //}
           // if(!bundle.getString("summary").equals(null)){
                String summary = bundle.getString("summary");
                bookDescription.setText(summary);
                Log.i("setText",summary);
            //}
        }
    }
}
