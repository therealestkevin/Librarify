package com.kevin.xu.librarify;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.kevin.xu.roomDB.genInfo;
import com.xu.librarify.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;


public class MainActivity extends AppCompatActivity {

    private androidx.appcompat.widget.Toolbar mainToolBar;
    private DrawerLayout drawlayout;
    private BookViewModel mainActivityModel;
    private genInfo localGenInfo;
    private ImageView userImage;

    @SuppressLint("WrongThread")
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
        View headerView = navView.getHeaderView(0);

        mainActivityModel = ViewModelProviders.of(this).get(BookViewModel.class);
        LinearLayout headerLayout = headerView.findViewById(R.id.header_layout);
        localGenInfo = mainActivityModel.getGenInfo();
        ImageView userImg = headerLayout.findViewById(R.id.profileImage);
        TextView nameText = headerLayout.findViewById(R.id.nameTextView);
        if(localGenInfo!=null){
            Bitmap bmp = BitmapFactory.decodeByteArray(localGenInfo.getImage(),0,localGenInfo.getImage().length);
            userImg.setImageBitmap(bmp);
            nameText.setText(localGenInfo.getName());
        }
        headerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();
                    AlertDialog.Builder userBuilder = new AlertDialog.Builder(MainActivity.this);
                    View userDialog = inflater.inflate(R.layout.user_dialog,null);
                    userBuilder.setView(userDialog);
                    userImage= userDialog.findViewById(R.id.userImage);
                    userImage.setColorFilter(Color.WHITE);
                    EditText userName = userDialog.findViewById(R.id.editTextName);
                    Button browseImages = userDialog.findViewById(R.id.chooseImage);

                    browseImages.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent pickImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(pickImage,1);
                        }
                    });

                userBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Bitmap bmp = ((BitmapDrawable)userImage.getDrawable()).getBitmap();
                        ByteArrayOutputStream streamer = new ByteArrayOutputStream();
                        bmp.compress(Bitmap.CompressFormat.PNG,100,streamer);
                        byte[] image = streamer.toByteArray();
                            genInfo newUpdateGen = new genInfo(userName.getText().toString(),image);
                            userImg.setImageDrawable(userImage.getDrawable());
                            nameText.setText(userName.getText().toString());
                            mainActivityModel.insertGenInfo(newUpdateGen);
                            //Set Picture and Name, enter into genInfo DB
                            dialog.dismiss();
                    }
                });

                AlertDialog dialogFinal = userBuilder.create();
                dialogFinal.show();
            }
        });

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            //Sets up Navigation to all corners of the app through navDrawer
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
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturn){
        super.onActivityResult(requestCode,resultCode,imageReturn);
        userImage.clearColorFilter();
        if(resultCode == RESULT_OK){
            Uri selectImg = imageReturn.getData();
            try {
                Bitmap bmp = MediaStore.Images.Media.getBitmap(this.getContentResolver(),selectImg);
                bmp = getResizedBitmap(bmp,300,300);
                userImage.setImageBitmap(bmp);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }
    public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        // create a matrix for the manipulation
        Matrix matrix = new Matrix();

        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);

        // recreate the new Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);

        return resizedBitmap;
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

