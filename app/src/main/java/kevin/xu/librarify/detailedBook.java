package kevin.xu.librarify;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xu.librarify.R;



/**
 * A simple {@link Fragment} subclass.
 */
public class detailedBook extends Fragment {
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

        View v = inflater.inflate(R.layout.fragment_detailed_book,container,false);


        return v;
    }

}
