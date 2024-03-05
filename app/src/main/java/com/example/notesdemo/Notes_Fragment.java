package com.example.notesdemo;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Notes_Fragment extends Fragment {

    TextInputEditText descripation, titletext;
    TextInputLayout titlelayout, descrpationlayout;

    ArrayList<Mymodule> list;

    Notesdb notesdb;
    Cursor getdatacursor;
    RecyclerView recyclerview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_, container, false);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        list = new ArrayList<>();

        titletext = view.findViewById(R.id.titletextmaine);
        descripation = view.findViewById(R.id.descripationmaine);

         recyclerview = view.findViewById(R.id.recyclerview);
        Button addnotesbutton = view.findViewById(R.id.addnotesbutton);
        Button displaybutton = view.findViewById(R.id.displaybutton);

        notesdb = new Notesdb(getContext());
        getdatafromsqlite();

        displaybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                getdatafromsqlite();

            }
        });






        // Get the current time in milliseconds since the epoch
        long currentTimeMillis = System.currentTimeMillis();

        // Convert milliseconds to a more readable format if needed
        Date currentDate = new Date(currentTimeMillis);
        String date = currentDate.getDay()+":"+currentDate.getDate()+":"+currentDate.getMonth()+":"+currentDate.getYear();
        String time = currentDate.getHours()+":"+currentDate.getMinutes();


        addnotesbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (titletext.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Please Enter Title", Toast.LENGTH_SHORT).show();


                } else if (descripation.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Please Enter descripation", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(getContext(), "Data Insert Successfully", Toast.LENGTH_SHORT).show();
                    notesdb.insertdata(titletext.getText().toString(), descripation.getText().toString(), date, time);
                    titletext.setText("");
                    descripation.setText("");
                }

            }
        });

        return view;
    }

    void getdatafromsqlite()
    {
        int i;
        getdatacursor = notesdb.getdata();
        // moving our cursor to first position.
        if (getdatacursor != null && getdatacursor.moveToFirst()) {
            do {
                i=0;
                // on below line we are adding the data from
                // cursor to our array list.
                list.add(new Mymodule(
                        getdatacursor.getInt(0),
                        getdatacursor.getString(1),
                        getdatacursor.getString(2),
                        getdatacursor.getString(3),
                        getdatacursor.getString(4))
                );
            } while (getdatacursor.moveToNext());
            // moving our cursor to next.

            recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
            MyAdapter adapter = new MyAdapter(list,getContext(),notesdb);
            recyclerview.setAdapter(adapter);
        }

        getdatacursor.close();

    }
    public void onBackPressed() {
        getActivity().finish();
    }






}

