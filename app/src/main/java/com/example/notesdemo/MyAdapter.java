package com.example.notesdemo;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.viewholder> {

    private ArrayList<Mymodule> list;
    private Context context;  // Added context variable
    private Notesdb notesdb;

    public MyAdapter(ArrayList<Mymodule> list, Context context, Notesdb notesdb) {
        this.list = list;
        this.context = context;
        this.notesdb = notesdb;
    }

    @NonNull
    @Override
    public MyAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.viewholder holder, int position) {
        Mymodule module = list.get(position);
        holder.title.setText(module.getTitle());
        holder.description.setText(module.getDescription());
        holder.time.setText(module.getTime());
        holder.date.setText(module.getDate());

        holder.deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    // Ensure the position is valid before attempting to delete
                    notesdb.deleteData(list.get(position).getId());
                    list.remove(position);
                    notifyItemRemoved(position);
                    Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.editbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Mymodule selectedModule = list.get(position);

                    String title = selectedModule.getTitle();
                    String description = selectedModule.getDescription();

                    // Open the custom dialog
                    customdailogbox dialogFragment = customdailogbox.newInstance(selectedModule.getId(), title,description);
                    dialogFragment.show(((AppCompatActivity) context).getSupportFragmentManager(), null);
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView title ,description,date,time;
        ImageView editbutton,deletebutton;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.time);
            date = itemView.findViewById(R.id.date);
            description = itemView.findViewById(R.id.descripation);
            title = itemView.findViewById(R.id.titletext);
            deletebutton = itemView.findViewById(R.id.deletebutton);
            editbutton = itemView.findViewById(R.id.editbutton);

        }
    }


}
