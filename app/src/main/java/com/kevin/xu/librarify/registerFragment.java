package com.kevin.xu.librarify;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.kevin.xu.roomDB.genInfo;
import com.xu.librarify.R;


public class registerFragment extends androidx.fragment.app.Fragment {


    public registerFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static registerFragment newInstance() {
        registerFragment fragment = new registerFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        view.setBackgroundColor(Color.WHITE);
        ImageView backgroundImage = view.findViewById(R.id.backgroundImageView);
        EditText userPassword = view.findViewById(R.id.userPassword);
        EditText userNameEditText = view.findViewById(R.id.userNameEditText);
        Button loginButon = view.findViewById(R.id.loginButton);
        EditText editTextPreferred = view.findViewById(R.id.editTextPreferredName);

        loginButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userPassword.getText().toString().length()>0&&userNameEditText.getText().toString().length()>0&&editTextPreferred.getText().toString().length()>0){
                    loginActivity.loginModel.insertGenInfo(new genInfo(editTextPreferred.getText().toString(),"",userNameEditText.getText().toString()
                    ,userPassword.getText().toString()));
                    Intent mainIntent = new Intent(getContext(),MainActivity.class);
                    startActivity(mainIntent);
                }else{
                    Toast.makeText(getContext(),"Please Enter All Fields",Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }


}
