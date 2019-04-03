package com.kevin.xu.librarify;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kevin.xu.roomDB.genInfo;
import com.xu.librarify.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

public class loginActivity extends AppCompatActivity {
    private EditText userPassword;
    private EditText userNameEditText;
    private Button logonButton;
    private TextView registerTextView;
    private BookViewModel loginModel;
    private genInfo localGenInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupMainWindowDisplayMode();

        userPassword = findViewById(R.id.userPassword);
        userNameEditText = findViewById(R.id.userNameEditText);
        logonButton = findViewById(R.id.loginButton);
        registerTextView = findViewById(R.id.registerTextView);
        loginModel = ViewModelProviders.of(this).get(BookViewModel.class);
        localGenInfo = loginModel.getGenInfo();
        logonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userPassword.getText().toString().length()>0&&userNameEditText.getText().toString().length()>0){
                    String curPass = userPassword.getText().toString();
                    String curUser = userNameEditText.getText().toString();
                    if(localGenInfo.getUserName().equals(curUser)&&localGenInfo.getPassWord().equals(curPass)){
                        Intent startMainActivity = new Intent(getApplicationContext(),MainActivity.class);
                    }
                }else if(userPassword.getText().toString().length()>0 && userNameEditText.getText().toString().length()<1){
                    Toast.makeText(getApplicationContext(),"Please Enter a User Name ",Toast.LENGTH_LONG).show();
                }else if(userPassword.getText().toString().length()<1 && userNameEditText.getText().toString().length()>0){
                    Toast.makeText(getApplicationContext(),"Please Enter a Password",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Please Enter Your Password and Username", Toast.LENGTH_LONG).show();
                }
            }
        });

        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    //Insert New Login Info into the DB
                    //For now, ensure that this button is hidden after the first register
                    //Later, handle data for multiple users, research this
                    
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
