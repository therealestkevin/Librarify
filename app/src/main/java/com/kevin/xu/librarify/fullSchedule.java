package kevin.xu.librarify;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.github.tibolte.agendacalendarview.AgendaCalendarView;
import com.github.tibolte.agendacalendarview.CalendarPickerController;
import com.github.tibolte.agendacalendarview.models.CalendarEvent;
import com.github.tibolte.agendacalendarview.models.DayItem;
import com.xu.librarify.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import kevin.xu.roomDB.Book;

public class fullSchedule extends AppCompatActivity {
    //Displays all scheduled events from all books
    private Toolbar fullScheduleToolBar;
    private AgendaCalendarView fullScheduleAgenda;
    private static List<CalendarEvent> eventList;
    private Calendar minDate;
    private Calendar maxDate;
    private CalendarPickerController bob;
    private BookViewModel fullModel;
    private List<Book> localBooks = new ArrayList<>();
    private TextView noteTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_schedule);
        setupMainWindowDisplayMode();

        fullScheduleAgenda = findViewById(R.id.fullScheduleAgenda);
        fullScheduleToolBar = findViewById(R.id.fullScheduleToolBar);
        setSupportActionBar(fullScheduleToolBar);
        fullScheduleToolBar.setTitle("Full Reading Schedule");
        fullModel = ViewModelProviders.of(this).get(BookViewModel.class);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        fullScheduleToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backIntent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(backIntent);
            }
        });
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
                AlertDialog.Builder bobBuilder = new AlertDialog.Builder(fullSchedule.this);
                LayoutInflater inflater = getLayoutInflater();
                View eventDialog = inflater.inflate(R.layout.event_dialog,null);


                bobBuilder.setView(eventDialog);
                noteTextView = eventDialog.findViewById(R.id.notesViewText);
                //Find out what event has been clicked and display the notification
                //Better solution that n^2 should be worked out in future
                for(Book i : localBooks){
                    for( simpleScheduleDisplay b : i.getCompleteData()){
                            if(b.getId() == event.getId()){
                                noteTextView.setText(b.getDescription());
                            }
                    }
                }

                bobBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = bobBuilder.create();
                dialog.show();
            }

            @Override
            public void onScrollToDate(Calendar calendar) {

            }
        };
        localBooks = fullModel.getBooksNonLive();
        if(localBooks.size()>0) {
            for (Book b : localBooks) {
                eventList.addAll(b.getScheduleData());
            }
        }
        //Going through all books
        Iterator<CalendarEvent> i = eventList.iterator();

      while(i.hasNext()){
          CalendarEvent temp = i.next();
          if(temp.getStartTime()==null){
              i.remove();
          }
      }

        fullScheduleAgenda.init(eventList,minDate,maxDate, Locale.getDefault(),bob);
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
