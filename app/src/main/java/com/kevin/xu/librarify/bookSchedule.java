package com.kevin.xu.librarify;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.tibolte.agendacalendarview.AgendaCalendarView;
import com.github.tibolte.agendacalendarview.CalendarPickerController;
import com.github.tibolte.agendacalendarview.models.BaseCalendarEvent;
import com.github.tibolte.agendacalendarview.models.CalendarEvent;
import com.github.tibolte.agendacalendarview.models.DayItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xu.librarify.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

public class bookSchedule extends AppCompatActivity {
    private AgendaCalendarView bookSchedule;
    private Toolbar scheduleToolBar;
    private Button readingButton;
    private int BookPosition;
    private List<CalendarEvent> eventList;
    private Calendar minDate;
    private Calendar maxDate;
    private CalendarPickerController bob;
    public static final int NEW_RESET_CALENDAREVENT=1;
    private TextView noteTextView;
    private simpleScheduleDisplay temp = new simpleScheduleDisplay();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_schedule);
        setupMainWindowDisplayMode();
        readingButton = findViewById(R.id.readingButton);

        if (getIntent() != null && getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            if(bundle.getInt("BookPosition")>-1){
                BookPosition= bundle.getInt("BookPosition");
            }
        }
        bookList.bookModel.updateScheduleBool(BookAdapter.mBook.get(BookPosition).getId());
        //Marking the book as visited
        readingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startManage = new Intent(getApplicationContext(),scheduleManager.class);
                startManage.putExtra("BookPositionFinal",BookPosition);
                startActivityForResult(startManage,NEW_RESET_CALENDAREVENT);
                //Starting for the result of the stack of simpleSchedules within the ScheduleManager

            }
        });
        
        bookSchedule = findViewById(R.id.agenda_calendar_view);
        minDate = Calendar.getInstance();
        maxDate = Calendar.getInstance();

        minDate.add(Calendar.MONTH, -2);
        minDate.set(Calendar.DAY_OF_MONTH, 1);
        maxDate.add(Calendar.YEAR, 1);

        eventList = new ArrayList<>();
         bob = new CalendarPickerController() {
            @Override
            public void onDaySelected(DayItem dayItem) {

            }

            @Override
            public void onEventSelected(CalendarEvent event) {
                //Displaying the note upon the event being clicked
                AlertDialog.Builder bobBuilder = new AlertDialog.Builder(bookSchedule.this);
                LayoutInflater inflater = getLayoutInflater();
                    View eventDialog = inflater.inflate(R.layout.event_dialog,null);

                    bobBuilder.setView(eventDialog);
                    noteTextView = eventDialog.findViewById(R.id.notesViewText);

                    for(simpleScheduleDisplay i : BookAdapter.mBook.get(BookPosition).getCompleteData()){
                        if(event.getId()==i.getId()){
                            temp = i;
                            noteTextView.setText(i.getDescription());
                        }
                    }

                    bobBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    bobBuilder.setNeutralButton("Export", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            /*
                            ContentResolver cr = getContentResolver();
                            ContentValues values = new ContentValues();
                            values.put(CalendarContract.Events.DTSTART,temp.getFirstDay().getTimeInMillis());
                            values.put(CalendarContract.Events.DTEND,temp.getLastDay().getTimeInMillis());
                            values.put(CalendarContract.Events.TITLE,temp.getTitle());
                            values.put(CalendarContract.Events.DESCRIPTION,temp.getDescription());
                            values.put(CalendarContract.Events.CALENDAR_ID,1);
                            values.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());
                            Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI,values);
                            */
                            Toast.makeText(getApplicationContext(),"Google Calendar Exporting Coming Soon",Toast.LENGTH_LONG).show();

                        }
                    });
                   AlertDialog dialog = bobBuilder.create();
                   dialog.show();

            }

            @Override
            public void onScrollToDate(Calendar calendar) {

            }
        };


        populateAgendaFromDB(eventList);
        bookSchedule.init(eventList,minDate,maxDate, Locale.getDefault(),bob);




        scheduleToolBar = (Toolbar) findViewById(R.id.scheduleToolBar);
        setSupportActionBar( scheduleToolBar);
        scheduleToolBar.setTitle("Book Schedule");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        scheduleToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(getApplicationContext(),bookList.class);
                startActivity(backIntent);
            }
        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent dat){
        super.onActivityResult(requestCode,resultCode,dat);
        //Updates the schedule with the latest added as the AsyncTask will finish after going back to this activity

        if(requestCode == NEW_RESET_CALENDAREVENT && resultCode ==RESULT_OK){
               String jsonStringBaseCalendar = dat.getStringExtra("REPLY");
               Log.i("simpleCal",jsonStringBaseCalendar);

               ArrayList<BaseCalendarEvent> tempUpdate = new Gson().fromJson(jsonStringBaseCalendar,
                       new TypeToken<ArrayList<BaseCalendarEvent>>(){
                       }.getType());
            populateAgendaFromResult(tempUpdate);

            //Gson.fromJson for the resulting string for the updated CalendarEvent Arraylist
            //The scheduleManager class should pull the current CalendarEvent arralysit from the database
            // Then dynamically add the additional values to the end of the existing arraylist
            //Then, query the database to replace the old arraylist with the new, then setresult
            // the new arraylist to fix any refresh problems for the agendacalendar in order
            //to maintain the latest and most updated agenda
            //If this is the main solution from now on, populatAgendaFromDB method
            //will probably not be needed as updates are automatically being streamlined
            //from the schedulemanager into this class instead of having to look at the
            //the static arraylist of book entities each time to populate the local calendarevent
            //database, I will see if this works through further implementations
            //This seems to be a taxing solution but it has a high chance of working
            //will implement this function when I have time in maybe a day or too

            //UPDATES:

            //Will need to improve caching functions of DB in order to reduce wait time on entry query
            //This is especially pertinent to right after book entities are added
            //through the cameraCapture class, it seems to be involved with the Asynctask
            //The solution may be to swap out the Asynctask for a more traditional
            //runnable solution that will be faster and will function on a seperate thread
            //This is the task to be completed after the first task above

            //UPDATE 2:

            //As of now, I have fixed a part of the first task, the issue
            //now seems to be that if you do not add a scheduleevent
            //when you edit your reading schedule, then your previous valeus are
            //lost, I will have to fix this problem as well but
            //progress has been made on the greater task
        }else{
            Toast.makeText(getApplicationContext(),"Entry Failed",Toast.LENGTH_LONG).show();
        }
    }
    private void populateAgendaFromDB(List<CalendarEvent>eventList){
        //Querying data from database to update local calendar
        ArrayList<BaseCalendarEvent> temp = BookAdapter.mBook.get(BookPosition).getScheduleData();
        eventList.clear();
        for(BaseCalendarEvent i : temp){
            eventList.add(i);
        }

    }
    private void populateAgendaFromResult(ArrayList<BaseCalendarEvent>newEventList){
        //Used by onActivityResult
        eventList.clear();
        for(BaseCalendarEvent i : newEventList){
            eventList.add(i);
        }
        bookSchedule.init(eventList,minDate,maxDate,Locale.getDefault(),bob);
    }
    private void mockList(List<CalendarEvent> eventList) {
        //Test Code
        Calendar startTime1 = Calendar.getInstance();
        Calendar endTime1 = Calendar.getInstance();
        endTime1.add(Calendar.MONTH, 1);
        BaseCalendarEvent event1 = new BaseCalendarEvent("Notes From Underground", "", "Your Library",
                ContextCompat.getColor(this, R.color.blue_selected), startTime1, endTime1, false);

        eventList.add(event1);

        Calendar startTime2 = Calendar.getInstance();
        startTime2.add(Calendar.DAY_OF_YEAR, 1);
        Calendar endTime2 = Calendar.getInstance();
        endTime2.add(Calendar.DAY_OF_YEAR, 3);
        BaseCalendarEvent event2 = new BaseCalendarEvent("Crime and Punishment", "", "Your Library",
                ContextCompat.getColor(this, R.color.black_overlay), startTime2, endTime2, false);
        eventList.add(event2);

    }
    @Override
    public void onResume(){
        super.onResume();

    }

    @Override
    public void onRestart(){
        super.onRestart();

    }
    private void setupMainWindowDisplayMode() {
        //Maintain fullscreen
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

}
