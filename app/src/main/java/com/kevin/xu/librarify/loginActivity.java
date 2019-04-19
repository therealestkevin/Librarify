package com.kevin.xu.librarify;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.kevin.xu.roomDB.genInfo;
import com.xu.librarify.R;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

public class loginActivity extends AppCompatActivity {
    private EditText userPassword;
    private EditText userNameEditText;
    private Button logonButton;
    private TextView registerTextView;
    public static BookViewModel loginModel;
    private genInfo localGenInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().setNavigationBarColor(Color.BLACK);
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

                Fragment fragLogin = registerFragment.newInstance();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                transaction.add(R.id.constraintLogin,fragLogin);
                transaction.addToBackStack(null);
                transaction.commit();


                    //Insert New Login Info into the DB
                    //For now, ensure that this button is hidden after the first register
                    //Later, handle data for multiple users, research this
                    
            }
        });



    }



}
