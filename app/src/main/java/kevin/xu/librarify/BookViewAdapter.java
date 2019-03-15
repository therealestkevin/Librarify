package kevin.xu.librarify;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import kevin.xu.roomDB.Book;

public class BookViewAdapter extends FragmentPagerAdapter {
   private final List<Fragment> FragmentList = new ArrayList<>();
   private Book holdBook;

    public BookViewAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentList.get(position);
    }

    @Override
    public int getCount() {

        return FragmentList.size();
    }


    public void addFragment(Fragment frag,Book book){
        holdBook=book;
        FragmentList.add(frag);
    }


}
