package kevin.xu.librarify;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.github.tibolte.agendacalendarview.AgendaCalendarView;
import com.github.tibolte.agendacalendarview.CalendarPickerController;
import com.github.tibolte.agendacalendarview.models.BaseCalendarEvent;
import com.github.tibolte.agendacalendarview.models.CalendarEvent;
import com.github.tibolte.agendacalendarview.models.DayItem;
import com.xu.librarify.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

public class bookSchedule extends AppCompatActivity {
    private AgendaCalendarView bookSchedule;
    private Toolbar scheduleToolBar;
    private Button readingButton;
    private int BookPosition;
    private static List<CalendarEvent> eventList;
    private Calendar minDate;
    private Calendar maxDate;
    private CalendarPickerController bob;
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
        readingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startManage = new Intent(getApplicationContext(),scheduleManager.class);
                startManage.putExtra("BookPositionFinal",BookPosition);
                finish();
                startActivity(startManage);

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
                onBackPressed();
            }
        });

    }
    private void populateAgendaFromDB(List<CalendarEvent>eventList){
        ArrayList<BaseCalendarEvent> temp = BookAdapter.mBook.get(BookPosition).getScheduleData();
        eventList.clear();
        for(BaseCalendarEvent i : temp){
            eventList.add(i);
        }

    }
    private void mockList(List<CalendarEvent> eventList) {
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
