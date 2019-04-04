package com.kevin.xu.librarify;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        View view = inflater.inflate(R.layout.fragment_register,container,false);
        view.setBackgroundColor(Color.WHITE);
        return view;
    }



}
