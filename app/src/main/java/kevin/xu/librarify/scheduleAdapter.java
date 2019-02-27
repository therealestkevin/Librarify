package kevin.xu.librarify;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.xu.librarify.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;


public class scheduleAdapter extends ArrayAdapter<simpleScheduleDisplay> {
    private ArrayList<simpleScheduleDisplay> holdData;

    public scheduleAdapter(@NonNull Context context,@NonNull List<simpleScheduleDisplay> objects) {
        super(context, 0, objects);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        simpleScheduleDisplay cur = getItem(position);

        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.schedule_row, parent,false);
        }

        TextView  textViewTitleRow =  convertView.findViewById(R.id.textViewTitleRow);
        TextView  textViewDate =  convertView.findViewById(R.id.textViewDates);
        TextView textViewPages = convertView.findViewById(R.id.textViewPages);
        textViewTitleRow.setText(cur.getTitle());
        textViewDate.setText(cur.getDateRange());
        textViewPages.setText(cur.getPages());

        return convertView;
    }
    public ArrayList<simpleScheduleDisplay> getList(){
        return holdData;
    }
}