package kevin.xu.librarify;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.xu.librarify.R;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class scheduleManager extends AppCompatActivity {
    private Toolbar scheduleManagerToolbar;
    private Button addBtnSchedule;
    private Button deleteBtnSchedule;
    private int BookPosition;
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
            LinearLayout layout = new LinearLayout(getApplicationContext());
                    EditText title = new EditText(getApplicationContext());
                    title.setHint(BookAdapter.mBook.get(BookPosition).getTitle());
                    layout.addView(title);
                    EditText pageNumberStart = new EditText(getApplicationContext());

                    pageNumberStart.setInputType(InputType.TYPE_CLASS_NUMBER);
                    pageNumberStart.setHint(""+1);
                    layout.addView(pageNumberStart);
                    EditText pageNumberEnd = new EditText(getApplicationContext());
                    pageNumberEnd.setInputType(InputType.TYPE_CLASS_NUMBER);
                    pageNumberEnd.setHint(BookAdapter.mBook.get(BookPosition).getBookList().getItems().get(0)
                    .getVolumeInfo().getPageCount());
                    layout.addView(pageNumberEnd);
            new AlertDialog.Builder(scheduleManager.this).setView(layout)
            .show();



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
