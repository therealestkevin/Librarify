package com.kevin.xu.librarify;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.xu.librarify.R;
import java.util.ArrayList;


public class scheduleAdapter extends ArrayAdapter<simpleScheduleDisplay> {
    private ArrayList<simpleScheduleDisplay> holdData;
    private Activity fromActivity;
    private static LayoutInflater inflater =null;

    public scheduleAdapter(Activity activity, ArrayList<simpleScheduleDisplay> objects) {
        super(activity, 0, objects);
        try{
            this.holdData=objects;
            this.fromActivity=activity;
            inflater = (LayoutInflater) fromActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }catch(Exception e){

        }
    }

    public int getCount(){
        return holdData.size();
    }

    public simpleScheduleDisplay getItem(int position){
        return holdData.get(position);
    }
    public static class ViewHolderSchedule{
        public TextView textViewTitleRow;
        public TextView textViewDate;
        public TextView textViewPages;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        //Displaying information for the listview within the scheduleManager activity
        simpleScheduleDisplay cur = getItem(position);
        View tempView = convertView;
        final ViewHolderSchedule holder;
        if(convertView==null){
            tempView= inflater.inflate(R.layout.schedule_row, parent,false);
            holder = new ViewHolderSchedule();
            holder.textViewTitleRow = tempView.findViewById(R.id.textViewTitleRow);
            holder.textViewDate = tempView.findViewById(R.id.textViewDates);
            holder.textViewPages = tempView.findViewById(R.id.textViewPages);
            tempView.setTag(holder);
        }else{
            holder = (ViewHolderSchedule)tempView.getTag();
        }


        holder.textViewTitleRow.setText(cur.getTitle());
        holder.textViewDate.setText("Dates: "+cur.getDateRange());
        holder.textViewPages.setText("Pages: "+cur.getPages());

        return tempView;
    }
    public ArrayList<simpleScheduleDisplay> getList(){
        return holdData;
    }
}