package kevin.xu.librarify;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.DatePicker;
import com.applandeo.materialcalendarview.builders.DatePickerBuilder;
import com.applandeo.materialcalendarview.listeners.OnSelectDateListener;
import com.xu.librarify.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class scheduleManager extends AppCompatActivity {
    private Toolbar scheduleManagerToolbar;
    private Button addBtnSchedule;
    private Button deleteBtnSchedule;
    private EditText editTextTitle;
    private EditText editTextStartPage;
    private EditText editTextPageEnd;
    private Button dateButton;
    private int BookPosition;
    private AlertDialog.Builder bobBuilder;
    private TextView editTextDateFrom;
    private TextView editTextDateTo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_manager);
        setupMainWindowDisplayMode();
        scheduleManagerToolbar = findViewById(R.id.scheduleManagerToolBar);
        addBtnSchedule = findViewById(R.id.addBtnSchedule);
        deleteBtnSchedule = findViewById(R.id.deleteBtnSchedule);
        setSupportActionBar(scheduleManagerToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        scheduleManagerToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        if (getIntent() != null && getIntent().getExtras() != null){
            Bundle bundle = getIntent().getExtras();
            if(bundle.getInt("BookPositionFinal")>-1){
                BookPosition = bundle.getInt("BookPositionFinal");
            }
        }
    addBtnSchedule.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {



            LayoutInflater inflater = getLayoutInflater();







            bobBuilder = new AlertDialog.Builder(scheduleManager.this);
            View dialogCustomView = inflater.inflate(R.layout.custom_dialog,null);
            bobBuilder.setView(dialogCustomView);
            editTextTitle= dialogCustomView.findViewById(R.id.editTextTitle);

            editTextTitle.setText(BookAdapter.mBook.get(BookPosition).getTitle());

            editTextStartPage = dialogCustomView.findViewById(R.id.editTextStartPage);

            editTextStartPage.setHint(""+BookAdapter.mBook.get(BookPosition).getBookList().getItems().get(0).getVolumeInfo().getPageCount()
                    +" Pages In Total");

            editTextPageEnd = dialogCustomView.findViewById(R.id.editTextPageEnd);

            editTextPageEnd.setHint(""+BookAdapter.mBook.get(BookPosition).getBookList().getItems().get(0).getVolumeInfo().getPageCount()
                    +" Pages In Total");
            editTextDateFrom = dialogCustomView.findViewById(R.id.textViewDateFrom);
            editTextDateTo = dialogCustomView.findViewById(R.id.textViewDateTo);
            dateButton = dialogCustomView.findViewById(R.id.dateButton);
            OnSelectDateListener listenerDates = new OnSelectDateListener() {
                @Override
                public void onSelect(List<Calendar> calendar) {

                            Calendar firstDay = calendar.get(0);
                            Calendar lastDay = calendar.get(calendar.size()-1);
                            Date firstDate = firstDay.getTime();
                            Date lastDate = lastDay.getTime();
                            SimpleDateFormat formatter = new SimpleDateFormat("MMM, dd, yyyy");
                            String dateFirst = formatter.format(firstDate);
                            String dateLast = formatter.format(lastDate);
                            editTextDateFrom.setText(dateFirst);
                            editTextDateTo.setText(dateLast);

                        }


            };
            dateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatePickerBuilder builderBob2 = new DatePickerBuilder(scheduleManager.this, listenerDates).pickerType(CalendarView.RANGE_PICKER);
                    DatePicker realDatePicker = builderBob2.build().show();
                    /*OnSelectDateListener listenDatePick = new OnSelectDateListener() {
                        @Override
                        public void onSelect(List<Calendar> calendar) {
                            Calendar firstDay = calendar.get(0);
                            Calendar lastDay = calendar.get(calendar.size()-1);
                            Date firstDate = firstDay.getTime();
                            Date lastDate = lastDay.getTime();
                            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
                            String dateFirst = formatter.format(firstDate);
                            String dateLast = formatter.format(lastDate);
                            editTextDateFrom.setText(dateFirst);
                            editTextDateTo.setText(dateLast);

                        }
                    };*/
                }
            });

                 bobBuilder.setPositiveButton("Create",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            })
            .create().show();



        }
    });
    deleteBtnSchedule.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    });
    }

    private void setupMainWindowDisplayMode() {
        View decorView = setSystemUiVisibilityMode();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                setSystemUiVisibilityMode(); // Needed to avoid exiting immersive_sticky when keyboard is displayed
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
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
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
