package com.example.diary.database;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diary.R;
import com.example.diary.makeJournal;

//import org.antlr.v4.runtime.misc.NotNull;
//import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private List<MainData> dataList;
    private Activity context;
//    private RoomDB db;

    public MainAdapter(Activity context, List<MainData> arList) {

        this.dataList = arList;
        this.context = context;

        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_list_singleitem, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  ViewHolder holder, int position) {

        // get stuff from dataList

        MainData mainData = dataList.get(position);

        // set text on text view

        holder.date.setText(mainData.getDate());
        holder.title.setText(mainData.getTitle());

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, makeJournal.class);
                intent.putExtra("mainData_Object", mainData);

                context.startActivity(intent);


//                context.startActivity(new Intent(context, makeJournal.class));
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // get variables and view here

        TextView date, title;
        RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.dateSingleItem);
            title = itemView.findViewById(R.id.mainTitleSingleItem);
            relativeLayout = itemView.findViewById(R.id.fullLayoutSingleItem);

        }
    }
}