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

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.xu.librarify.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import kevin.xu.gsonParsing.OuterURL;

public class BookViewActivity extends AppCompatActivity {
    private int BookPosition2;
    private Toolbar bookViewToolbar;
    private TabLayout tablayout;
    private ViewPager viewpager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_view);
        setupMainWindowDisplayMode();

        bookViewToolbar = (Toolbar) findViewById(R.id.BookViewToolbar);
        setSupportActionBar(bookViewToolbar);
       bookViewToolbar.setTitle("Book Info");
       viewpager = (ViewPager) findViewById(R.id.view_pager);
       setViewPager(viewpager);

       tablayout = (TabLayout) findViewById(R.id.tab_layout);
       tablayout.setupWithViewPager(viewpager);
       tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
           @Override
           public void onTabSelected(TabLayout.Tab tab) {
                    viewpager.setCurrentItem(tab.getPosition());


           }

           @Override
           public void onTabUnselected(TabLayout.Tab tab) {

           }

           @Override
           public void onTabReselected(TabLayout.Tab tab) {

           }
       });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        bookViewToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });





        if (getIntent() != null && getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            if(bundle.getInt("BookPosition2")>-1){
                BookPosition2 = bundle.getInt("BookPosition2");
            }

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

    private void setViewPager(ViewPager viewpager) {
        BookViewAdapter adapter = new BookViewAdapter(getSupportFragmentManager());
        adapter.addFragment(new summaryFrag(BookPosition2),"Book Summary");
        adapter.addFragment(new detailedBook(BookPosition2),"Detailed Info");

        viewpager.setAdapter(adapter);
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

