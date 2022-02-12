package com.example.diary.database;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
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
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private List<MainData> dataList;
    private Activity context;
//    private RoomDB db;
    private int emojis[];

    public MainAdapter(Activity context, List<MainData> arList) {

        this.dataList = arList;
        this.context = context;

        emojis = new int[] {R.drawable.sample_emoji, R.drawable.calendar_icon, R.drawable.color_palette_icon, R.drawable.add_icon, R.drawable.cancel_icon, R.drawable.cancel_icon};

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
        holder.img.setImageResource(emojis[mainData.getMoodEmoji()]);

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
        ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.dateSingleItem);
            title = itemView.findViewById(R.id.mainTitleSingleItem);
            relativeLayout = itemView.findViewById(R.id.fullLayoutSingleItem);
            img = itemView.findViewById(R.id.emojiHolderRV);
        }
    }

//
//    private Filter records;
//
//    @Override
//    public Filter getFilter() {
//
//        if(records == null)
//            records = new RecordFilter();
//
//        return records;
//    }
//
//    private class RecordFilter extends Filter {
//        @Override
//        protected FilterResults performFiltering(CharSequence charSequence) {
//
//            FilterResults results = new FilterResults();
//            if(charSequence == null || charSequence.length() == 0) {
//
//                results.values = dataList;
//                results.count = dataList.size();
//            }
//            else {
//
//                List<MainData> newList = new ArrayList<MainData>();
//                for(MainData md : dataList) {
//
//                    if(md.getTitle().toUpperCase().trim().contains(charSequence.toString().toUpperCase().trim()) ||
//                            md.getDate().toUpperCase().trim().contains(charSequence.toString().toUpperCase().trim())) {
//                        newList.add(md);
//                    }
//                }
//
//                results.values = newList;
//                results.count = newList.size();
//            }
//
//            return results;
//        }
//
//        @Override
//        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//
//            dataList = (List<MainData>) filterResults.values;
//            notifyDataSetChanged();
//        }
//    }
}
