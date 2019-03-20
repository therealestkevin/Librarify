package kevin.xu.librarify;

import java.util.ArrayList;
import java.util.List;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class BookViewAdapter extends FragmentPagerAdapter {
   private final List<Fragment> FragmentList = new ArrayList<>();
   private final List<String> titles = new ArrayList<>();
   //Manages Fragments information and Fragment Names
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

    @Override
    public CharSequence getPageTitle(int position){
        return titles.get(position);
    }
    public void addFragment(Fragment frag,String title){
        titles.add(title);
        FragmentList.add(frag);
    }


}
