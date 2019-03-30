package com.kevin.xu.librarify;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;


public class MainActivity extends AppCompatActivity {

    private androidx.appcompat.widget.Toolbar mainToolBar;
    private DrawerLayout drawlayout;
    private BookViewModel mainActivityModel;
    private genInfo localGenInfo;
    private ImageView userImage;
    private Uri imageUri;
    private int clicked;

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
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Explain to the user why we need to read the contacts
            }

            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    42);

            // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
            // app-defined int constant that should be quite unique

        }
        mainActivityModel = ViewModelProviders.of(this).get(BookViewModel.class);
        LinearLayout headerLayout = headerView.findViewById(R.id.header_layout);
        localGenInfo = mainActivityModel.getGenInfo();
        ImageView userImg = headerLayout.findViewById(R.id.profileImage);
        TextView nameText = headerLayout.findViewById(R.id.nameTextView);
        if(localGenInfo!=null){
            Bitmap bmp = null;
            try {
                Uri temp = Uri.parse(localGenInfo.getPathImage());
                bmp = MediaStore.Images.Media.getBitmap(this.getContentResolver(),temp);
                userImg.setImageBitmap(bmp);
                nameText.setText(localGenInfo.getName());
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        headerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicked =0;
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


                    }
                });

                AlertDialog dialogFinal = userBuilder.create();
                dialogFinal.show();

                dialogFinal.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bitmap bmp = getBitmapFromVectorDrawable(getApplicationContext(), userImage.getDrawable());

                        if (clicked==0 ||  userName.getText().toString().equals("")) {
                            AlertDialog.Builder errorBuilder = new AlertDialog.Builder(MainActivity.this);
                            errorBuilder.setMessage("Please Enter Name and New Profile Picture").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).create().show();

                        }else {

                            genInfo newUpdateGen = new genInfo(userName.getText().toString(), imageUri.toString());
                            try {
                                Bitmap tempMap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                                userImg.setImageBitmap(tempMap);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            nameText.setText(userName.getText().toString());
                            mainActivityModel.insertGenInfo(newUpdateGen);
                            //Set Picture and Name, enter into genInfo DB
                            dialogFinal.dismiss();
                        }
                    }
                });
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
            imageUri = selectImg;
            try {
                clicked++;
                Bitmap bmp = MediaStore.Images.Media.getBitmap(this.getContentResolver(),selectImg);
                bmp = getResizedBitmap(bmp,300,300);
                userImage.setImageBitmap(bmp);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }
    public static Bitmap getBitmapFromVectorDrawable(Context context, Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
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

