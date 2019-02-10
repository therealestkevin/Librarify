package com.example.librarify;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import androidx.appcompat.app.AppCompatActivity;

public class BookViewActivity extends AppCompatActivity {
    private ImageView bookImg;
    private TextView bookDescription;
    private RatingBar bookRatingBar;
    private TextView starText;
    private Button sendJson;
    private Button toList;
    private String jsonString;
 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_view);
        bookImg = (ImageView) findViewById(R.id.bookImage);
        bookDescription = (TextView) findViewById(R.id.bookDescription);
        bookRatingBar = (RatingBar) findViewById(R.id.bookRatingBar);
        starText = (TextView) findViewById(R.id.starDisplayText);
        sendJson = (Button) findViewById(R.id.sendJson);
        toList = (Button) findViewById(R.id.goToList);
        bookRatingBar.setMax(5);
        bookRatingBar.setStepSize(.1f);


        toList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),bookList.class);
                startActivity(intent                                                                             );
            }
        });



        if(getIntent()!=null && getIntent().getExtras()!=null){
             Bundle bundle = getIntent().getExtras();
            if(!bundle.getString("imageURLPassed").equals("")){
                try {
                   RetrieveDrawableTask completeTask = new RetrieveDrawableTask(bundle.getString("imageURLPassed"));
                   Drawable d = completeTask.execute().get();
                   bookImg.setImageDrawable(d);
                }
                 catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }


            }
            if(!bundle.getString("bookDescriptionPassed").equals("")){
                String summary = bundle.getString("bookDescriptionPassed");
                bookDescription.setText(summary);
                Log.i("setText",summary);
            }
            if(bundle.getDouble("ratingStars")>-1&&bundle.getInt("numberRatings")>-1){
                double rating = bundle.getDouble("ratingStars");
                int ratingNum = bundle.getInt("numberRatings");
                Log.i("ratingDouble",""+rating);
                Log.i("ratingFloat","" +(float)rating);
                bookRatingBar.setRating((float)rating);
                starText.setText(""+rating+" / 5.0 with "+ratingNum+" Ratings");
            }

            if(bundle.getString("jsonString")!=null){
               jsonString = bundle.getString("jsonString");
                sendJson.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        Intent replyIntent = new Intent();

                        replyIntent.putExtra("key",jsonString);

                        if(TextUtils.isEmpty(jsonString)){
                            setResult(RESULT_CANCELED,replyIntent);
                        }else {

                            setResult(RESULT_OK,replyIntent);
                            Toast.makeText(getApplicationContext(),"Entry Success",Toast.LENGTH_LONG).show();
                        }
                    }
                });


            }


        }
    }
    static class RetrieveDrawableTask extends AsyncTask<String,Void,Drawable>{
        String urlString;

        private RetrieveDrawableTask(String urlString){
            this.urlString=urlString;
        }

        @Override
        protected Drawable doInBackground(String... strings) {

            try {
               URL url = new URL(urlString);
                InputStream content = (InputStream)url.getContent();
                Drawable d = Drawable.createFromStream(content , "imageSrc");
                return d;
            } catch (IOException e) {
                e.printStackTrace();
            }
           return null;
        }
    }
}
