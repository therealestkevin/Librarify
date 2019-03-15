package kevin.xu.librarify;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.xu.librarify.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.ExecutionException;


public class summaryFrag extends androidx.fragment.app.Fragment {

    private int BookPosition;


    public summaryFrag() {

    }
    @SuppressLint("ValidFragment")
    public summaryFrag(int BookPosition){
        this.BookPosition=BookPosition;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =inflater.inflate(R.layout.fragment_summary, container, false);


        ImageView bookImg = (ImageView) v.findViewById(R.id.bookImage);
        TextView bookDescription = (TextView) v.findViewById(R.id.bookDescription);
        RatingBar bookRatingBar = (RatingBar) v.findViewById(R.id.bookRatingBar);
        TextView starText = (TextView) v.findViewById(R.id.starDisplayText);
        Button toList = (Button) v.findViewById(R.id.goToList);
        bookDescription.setMovementMethod(new ScrollingMovementMethod());

        bookRatingBar.setMax(5);
        bookRatingBar.setStepSize(.1f);
        toList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), bookList.class);
                startActivity(intent);
            }
        });
        try {
            bookImg.setImageDrawable(new BookViewActivity.RetrieveDrawableTask(BookAdapter.mBook.get(BookPosition).getBookList().getItems().get(0).getVolumeInfo()
                    .getImageLinks().getThumbnail()).execute().get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        bookDescription.setText(BookAdapter.mBook.get(BookPosition).getBookList().getItems().get(0).getVolumeInfo().getDescription());
        bookRatingBar.setRating((float)BookAdapter.mBook.get(BookPosition).getBookList().getItems().get(0).getVolumeInfo().getAverageRating());
        starText.setText(new StringBuilder("").append(BookAdapter.mBook.get(BookPosition).getBookList().getItems().get(0).getVolumeInfo().getAverageRating())
                .append(" / 5.0 with ").append(BookAdapter.mBook.get(BookPosition).getBookList().getItems().get(0).getVolumeInfo().getRatingsCount())
                .append(" Ratings").toString());
        return v;
    }
   
}