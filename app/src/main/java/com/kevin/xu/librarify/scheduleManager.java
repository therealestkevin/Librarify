package com.kevin.xu.librarify;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.DatePicker;
import com.applandeo.materialcalendarview.builders.DatePickerBuilder;
import com.applandeo.materialcalendarview.listeners.OnSelectDateListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.rits.cloning.Cloner;
import com.xu.librarify.R;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.kevin.xu.roomDB.Book;

public class scheduleManager extends AppCompatActivity {
    private Toolbar scheduleManagerToolbar;
    private Button addBtnSchedule;
    private EditText editTextTitle;
    private EditText editTextStartPage;
    private EditText editTextPageEnd;
    private EditText editTextNotes;
    private Button dateButton;
    private int BookPosition;
    private AlertDialog.Builder bobBuilder;
    private TextView editTextDateFrom;
    private TextView editTextDateTo;
    private ListView currentEvents;
    private scheduleAdapter adapter;
    private Calendar firstDay;
    private Calendar lastDay;
    private ArrayList<simpleScheduleDisplay> curStack = new ArrayList<>();
    private FloatingActionButton finishFloatingBtn;
    private FloatingActionButton exitFloatingBtn;
    private Book resetBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_manager);
        setupMainWindowDisplayMode();
        scheduleManagerToolbar = findViewById(R.id.scheduleManagerToolBar);
        addBtnSchedule = findViewById(R.id.addBtnSchedule);

        currentEvents = findViewById(R.id.currentEvents);
        finishFloatingBtn = findViewById(R.id.finishFloatingBtn);
        exitFloatingBtn = findViewById(R.id.exitFloatingBtn);

        setSupportActionBar(scheduleManagerToolbar);

        if (getIntent() != null && getIntent().getExtras() != null){
            Bundle bundle = getIntent().getExtras();
            if(bundle.getInt("BookPositionFinal")>-1){
                BookPosition = bundle.getInt("BookPositionFinal");
                Cloner cloner = new Cloner();
                    //Keeping copy of the book upon entering activity is the cancel button is clicked
                    resetBook =  cloner.deepClone(BookAdapter.mBook.get(BookPosition));
                }
            }


        finishFloatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //setResults
                //Request Codes and all
                        if(curStack.size()>0){
                            Toast.makeText(getApplicationContext(),"Schedule Saved", Toast.LENGTH_LONG);
                            finish();
                        } else{
                        Intent returnIntent = new Intent(getApplicationContext(),bookSchedule.class);
                        returnIntent.putExtra("BookPosition",BookPosition);
                        startActivity(returnIntent);
                    }
                    curStack.clear();
            }
        });
        exitFloatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent replyIntent = new Intent();
                    replyIntent.putExtra("REPLY", new Gson().toJson(resetBook.getScheduleData()));
                    setResult(RESULT_OK,replyIntent);
                    bookList.bookModel.resetScheduleData(resetBook);
                    curStack.clear();

                finish();
                adapter.clear();

            }
        });


        adapter = new scheduleAdapter(this, BookAdapter.mBook.get(BookPosition).getCompleteData());
        currentEvents.setAdapter(adapter);
        currentEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    addBtnSchedule.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {



            LayoutInflater inflater = getLayoutInflater();





            //Setting up big dialog for add event dialog
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
            editTextNotes = dialogCustomView.findViewById(R.id.editTextNotes);
            OnSelectDateListener listenerDates = new OnSelectDateListener() {
                @Override
                public void onSelect(List<Calendar> calendar) {

                            firstDay = calendar.get(0);
                            lastDay = calendar.get(calendar.size()-1);
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
                            dialog.dismiss();
                }
            });
                 AlertDialog dialog = bobBuilder.create();
                 dialog.show();

                 dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         Boolean closeDia = false;
                         String title = editTextTitle.getText().toString();
                         String dates = editTextDateFrom.getText().toString() + " — " + editTextDateTo.getText().toString();
                         String pages = editTextStartPage.getText().toString() + " — " + editTextPageEnd.getText().toString();
                         String description = editTextNotes.getText().toString();
                         if(dates.equals("No Current Date — No Current Date")){
                             AlertDialog.Builder  secondBuilder= new AlertDialog.Builder(scheduleManager.this);
                             secondBuilder.setMessage("Please Enter A Date").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                 @Override
                                 public void onClick(DialogInterface dialog, int which) {
                                     dialog.dismiss();
                                 }
                             }).create().show();
                         }else{
                             simpleScheduleDisplay temp = new simpleScheduleDisplay(title,dates,pages,firstDay,lastDay,description);
                             curStack.add(temp);
                            //Adding an event each time the button is clicked seems to be best method
                             //Other methods of doing this are a little clunky and this way still works
                             bookList.bookModel.updateCompleteData(curStack, BookAdapter.mBook.get(BookPosition).getId());
                             //adapter.add(temp);

                             //localData = BookAdapter.mBook.get(BookPosition).getCompleteData();
                             adapter.add(temp);
                             adapter.notifyDataSetChanged();

                             curStack.clear();
                             closeDia=true;
                         }

                        if(closeDia){
                            dialog.dismiss();
                        }
                     }
                 });



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
