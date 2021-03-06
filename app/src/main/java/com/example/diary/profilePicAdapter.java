package com.example.diary;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diary.database.MainAdapter;

public class profilePicAdapter extends RecyclerView.Adapter<profilePicAdapter.ViewHolder> {

    public Context context;
    public int pics[];
    SharedPreferences sharedPreferences;

    public profilePicAdapter(Context context, int pics[]) {

        this.context = context;
        this.pics = pics;
        this.sharedPreferences = context.getSharedPreferences("diaryPref", Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_item_picpicker_recyclerview, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.img.setImageResource(pics[position]);

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sharedPreferences.edit().putInt("profilePicIndex", position).apply();
            }
        });
    }

    @Override
    public int getItemCount() {
        return pics.length;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        // get variables and view here
        ImageView img;
        RecyclerView rv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.picHolderSingleItemPicPicker);
        }
    }
}
