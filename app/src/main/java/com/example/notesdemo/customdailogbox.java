package com.example.notesdemo;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Date;


public class customdailogbox extends DialogFragment {

    public static customdailogbox newInstance(int id, String title,String desc) {
        customdailogbox fragment = new customdailogbox();

        // Pass data to the fragment
        Bundle args = new Bundle();
        args.putInt("id", id);
        args.putString("title", title);
        args.putString("desc", desc);
        fragment.setArguments(args);

        return fragment;
    }

    Notesdb notesdb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_customdailogbox, container, false);
        // Retrieve data from arguments
        int id = getArguments().getInt("id");
        String titletext = getArguments().getString("title");
        String desctext = getArguments().getString("desc");
        notesdb = new Notesdb(getContext());

        TextInputEditText title = view.findViewById(R.id.title);
        TextInputEditText descripation = view.findViewById(R.id.descripation);

        title.setText(titletext);
        descripation.setText(desctext);
        Button updatebutton = view.findViewById(R.id.updateButton);

        long currentTimeMillis = System.currentTimeMillis();

        // Convert milliseconds to a more readable format if needed
        Date currentDate = new Date(currentTimeMillis);
        String date = currentDate.getDay()+":"+currentDate.getDate()+":"+currentDate.getMonth()+":"+currentDate.getYear();
        String time = currentDate.getHours()+":"+currentDate.getMinutes();

        updatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             int i=   notesdb.updateData(id,title.getText().toString(),descripation.getText().toString(),date,time);
                Toast.makeText(getContext(), "Date Updated "+i, Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setFullWidth();
    }

    private void setFullWidth() {
        if (getDialog().getWindow() != null) {
            WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            getDialog().getWindow().setAttributes(params);
        }
    }
}