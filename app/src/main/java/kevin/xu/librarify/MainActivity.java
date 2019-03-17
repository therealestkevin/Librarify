package kevin.xu.librarify;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import com.google.android.material.navigation.NavigationView;
import com.xu.librarify.R;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


public class MainActivity extends AppCompatActivity {

    private androidx.appcompat.widget.Toolbar mainToolBar;
    private DrawerLayout drawlayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupMainWindowDisplayMode();
        drawlayout = findViewById(R.id.draw_layout);
        NavigationView navView = findViewById(R.id.nav_view);
        mainToolBar =  findViewById(R.id.mainToolBar);
        mainToolBar.setTitle("Librarify");
        setSupportActionBar(mainToolBar);
        ActionBar actbar = getSupportActionBar();
        actbar.setDisplayHomeAsUpEnabled(true);
        actbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);


        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
               menuItem.setChecked(true);

                Log.i("item Selected"," "+menuItem.getTitle().toString());
               String curItem = menuItem.getTitle().toString();

               switch (curItem){
                   case "My Books":{
                       Intent libraryIntent = new Intent(getApplicationContext(),bookList.class);
                       startActivity(libraryIntent);
                       break;

                   }
                   case "New Book":{
                       Intent cameraIntent = new Intent(getApplicationContext(), bookList.class);
                       cameraIntent.putExtra("GoBack", "yes");
                       startActivity(cameraIntent);
                       break;
                   }
                   case "My Schedule":{
                       Intent scheduleIntent = new Intent(getApplicationContext(),fullSchedule.class);
                       startActivity(scheduleIntent);
                       break;
                   }
                  default:{
                      drawlayout.closeDrawers();
                   }
               }

               return true;
            }
        });



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
               drawlayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onResume(){
        super.onResume();
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

