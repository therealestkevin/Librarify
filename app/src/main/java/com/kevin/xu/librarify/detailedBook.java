package com.kevin.xu.librarify;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.xu.librarify.R;



/**
 * A simple {@link Fragment} subclass.
 */
//Serves as the fragment that is viewed upon the detailed Tab being opened
public class detailedBook extends androidx.fragment.app.Fragment {
    private int BookPosition;

    public detailedBook() {
        // Required empty public constructor
    }
    @SuppressLint("ValidFragment")
    public detailedBook(int BookPosition){
        this.BookPosition=BookPosition;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //Setting Up all the Text
        View v = inflater.inflate(R.layout.fragment_detailed_book,container,false);
        TextView titleTextView = v.findViewById(R.id.titleTextView);

        titleTextView.setText(BookAdapter.mBook.get(BookPosition).getBookList().getItems().get(0).getVolumeInfo().getTitle());

        TextView authorViewText = v.findViewById(R.id.authorViewText);

        authorViewText.setText(BookAdapter.mBook.get(BookPosition).getBookList().getItems().get(0).getVolumeInfo().getAuthors().toString()
                .replace("[", "")
                .replace("]", "")
                .trim());

        TextView pageCountText = v.findViewById(R.id.pageCountText);

        pageCountText.setText(""+BookAdapter.mBook.get(BookPosition).getBookList().getItems().get(0).getVolumeInfo().getPageCount());

        TextView detailedDescription = v.findViewById(R.id.detailedDescription);
        detailedDescription.setMovementMethod(new ScrollingMovementMethod());
        detailedDescription.setText(BookAdapter.mBook.get(BookPosition).getAdditionalInfo().get(0).replace(
                "<p>","").replace("<b>","").replace("</b>","")
                .replace("<br>","").replace("</p>",""));
                //setText(BookAdapter.mBook.get(BookPosition).getBookList().getItems().get(0).getVolumeInfo().getDescription());



        TextView categories = v.findViewById(R.id.categories);

        StringBuilder sb = new StringBuilder("");
        //for(int i=2; i <BookAdapter.mBook.get(BookPosition).getBookList().getItems().get(0).getVolumeInfo().getCategories().size();i++){
        //    sb.append(BookAdapter.mBook.get(BookPosition).getBookList().getItems().get(0).getVolumeInfo().getCategories().get(i));
       // }
        for(int i=1; i <BookAdapter.mBook.get(BookPosition).getAdditionalInfo().size();i++){
            sb.append(BookAdapter.mBook.get(BookPosition).getAdditionalInfo().get(i));
        }

        categories.setText(sb.toString());

        TextView publishTextView = v.findViewById(R.id.publishTextView);

        publishTextView.setText(BookAdapter.mBook.get(BookPosition).getBookList().getItems().get(0).getVolumeInfo().getPublisher());

        TextView publishDateText = v.findViewById(R.id.publishDateText);

        publishDateText.setText(BookAdapter.mBook.get(BookPosition).getBookList().getItems().get(0).getVolumeInfo().getPublishedDate());

        TextView isbnTextView = v.findViewById(R.id.isbnTextView);

        isbnTextView.setText(BookAdapter.mBook.get(BookPosition).getISBN());

        TextView maturityText = v.findViewById(R.id.maturityText);

        maturityText.setText(BookAdapter.mBook.get(BookPosition).getBookList().getItems().get(0).getVolumeInfo().getMaturityRating());

        TextView languageText = v.findViewById(R.id.languageText);

        languageText.setText(BookAdapter.mBook.get(BookPosition).getBookList().getItems().get(0).getVolumeInfo().getLanguage());

        TextView infoLinkText = v.findViewById(R.id.infoLinkText);

        infoLinkText.setText(BookAdapter.mBook.get(BookPosition).getBookList().getItems().get(0).getVolumeInfo().getInfoLink());

        //infoLinkText.setMovementMethod(LinkMovementMethod.getInstance());

        TextView hasEbookText = v.findViewById(R.id.hasEbookText);
        boolean status = BookAdapter.mBook.get(BookPosition).getBookList().getItems().get(0).getAccessInfo().getEpub().isAvailable();
        if(status){
            hasEbookText.setText("Yes");
        }else{
            hasEbookText.setText("No");
        }

        return v;
    }

}
