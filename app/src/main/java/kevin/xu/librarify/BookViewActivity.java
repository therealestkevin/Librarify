package kevin.xu.librarify;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xu.librarify.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import kevin.xu.gsonParsing.OuterURL;

public class BookViewActivity extends AppCompatActivity {
    private ImageView bookImg;
    private TextView bookDescription;
    private RatingBar bookRatingBar;
    private TextView starText;
    //private Button sendJson;
    private Button toList;
    private int BookPosition2;
    private OuterURL infoOutput;
    private Toolbar bookViewToolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_view);
        setupMainWindowDisplayMode();
        bookImg = (ImageView) findViewById(R.id.bookImage);
        bookDescription = (TextView) findViewById(R.id.bookDescription);
        bookRatingBar = (RatingBar) findViewById(R.id.bookRatingBar);
        starText = (TextView) findViewById(R.id.starDisplayText);
        toList = (Button) findViewById(R.id.goToList);
        bookDescription.setMovementMethod(new ScrollingMovementMethod());
        bookViewToolbar = (Toolbar) findViewById(R.id.BookViewToolbar);
        setSupportActionBar(bookViewToolbar);
       bookViewToolbar.setTitle("Book Info");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        bookViewToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });
        bookRatingBar.setMax(5);
        bookRatingBar.setStepSize(.1f);



        toList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), bookList.class);
                startActivity(intent);
            }
        });
        if (getIntent() != null && getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            if(bundle.getInt("BookPosition2")>-1){
                BookPosition2 = bundle.getInt("BookPosition2");
            }
            if(!bundle.getString("jsonStuff").equals(null)){

                infoOutput = new Gson().fromJson(bundle.getString("jsonStuff"), OuterURL.class);

                try {
                    bookImg.setImageDrawable(new RetrieveDrawableTask(infoOutput.getItems().get(0).getVolumeInfo()
                    .getImageLinks().getThumbnail()).execute().get());
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                bookDescription.setText(infoOutput.getItems().get(0).getVolumeInfo().getDescription());
                bookRatingBar.setRating((float)infoOutput.getItems().get(0).getVolumeInfo().getAverageRating());
                starText.setText(new StringBuilder("").append(infoOutput.getItems().get(0).getVolumeInfo().getAverageRating())
                        .append(" / 5.0 with ").append( infoOutput.getItems().get(0).getVolumeInfo().getRatingsCount())
                        .append(" Ratings").toString());
            }
/*
        if (getIntent() != null && getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            if (!bundle.getString("imageURLPassed").equals("")) {
                try {
                    RetrieveDrawableTask completeTask = new RetrieveDrawableTask(bundle.getString("imageURLPassed"));
                    Drawable d = completeTask.execute().get();
                    bookImg.setImageDrawable(d);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }


            }
            if (!bundle.getString("bookDescriptionPassed").equals("")) {
                String summary = bundle.getString("bookDescriptionPassed");
                bookDescription.setText(summary);
                Log.i("setText", summary);
            }
            if (bundle.getDouble("ratingStars") > -1 && bundle.getInt("numberRatings") > -1) {
                double rating = bundle.getDouble("ratingStars");
                int ratingNum = bundle.getInt("numberRatings");
                Log.i("ratingDouble", "" + rating);
                Log.i("ratingFloat", "" + (float) rating);
                bookRatingBar.setRating((float) rating);
                starText.setText("" + rating + " / 5.0 with " + ratingNum + " Ratings");
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
                });*/


        }


    }
    private void setupMainWindowDisplayMode() {
        View decorView = setSystemUiVisibilityMode();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                setSystemUiVisibilityMode();
            }
        });
    }
    private View setSystemUiVisibilityMode() {
        View decorView = getWindow().getDecorView();
        int options;
        options =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        decorView.setSystemUiVisibility(options);
        return decorView;
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
    @Override
    public void onResume(){
        super.onResume();
    }
    public static class RetrieveDrawableTask extends AsyncTask<String, Void, Drawable> {
        String urlString;

        public RetrieveDrawableTask(String urlString) {
            this.urlString = urlString;
        }

        @Override
        protected Drawable doInBackground(String... strings) {

            try {
                URL url = new URL(urlString);
                InputStream content = (InputStream) url.getContent();
                Drawable d = Drawable.createFromStream(content, "imageSrc");
                return d;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}

