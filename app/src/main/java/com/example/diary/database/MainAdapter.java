package com.example.diary.database;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diary.R;
import com.example.diary.makeJournal;

//import org.antlr.v4.runtime.misc.NotNull;
//import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private List<MainData> dataList;
    private Activity context;
//    private RoomDB db;
    private int emojis[];

    public MainAdapter(Activity context, List<MainData> arList) {

        this.dataList = arList;
        this.context = context;

        emojis = new int[] {R.drawable.cool_emoji, R.drawable.happy_emoji, R.drawable.meh_emoji, R.drawable.sad_emoji, R.drawable.angry_emoji};

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
        Boolean titleSet = false;
        String currTitle = mainData.getTitle();
        int currLen = currTitle.length();

        if(currLen > 30) {

            char[] charAr = currTitle.toCharArray();
            for(int i = 1; i <= 3; i++)
                charAr[30 - i] = '.';

            String newTitle = new String(charAr);
            holder.title.setText(newTitle);

            Log.d("new title", newTitle);

            titleSet = true;
        }
        else if(currLen > 20)
            holder.title.setTextSize(15);

        holder.date.setText(mainData.getDate());
        if(!titleSet) holder.title.setText(currTitle);


        holder.img.setImageResource(emojis[mainData.getMoodEmoji()]);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
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
        LinearLayout linearLayout;
        ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.dateSingleItem);
            title = itemView.findViewById(R.id.mainTitleSingleItem);
            linearLayout = itemView.findViewById(R.id.fullLayoutSingleItem);
            img = itemView.findViewById(R.id.emojiHolderRV);
        }
    }

}
