package com.zybooks.project2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/* This class is used to load inventory items into the recycler view and list their name + item count
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    Activity activity;
    private ArrayList item_name, item_count;

    CustomAdapter(Activity activity, Context context, ArrayList item_name, ArrayList item_count) {
        this.activity = activity;
        this.context = context;
        this.item_name = item_name;
        this.item_count = item_count;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflator = LayoutInflater.from(context);
        View view = inflator.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.item_name_txt.setText(String.valueOf(item_name.get(position)));
        holder.item_count_txt.setText(String.valueOf(item_count.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateItemActivity.class);
                intent.putExtra("name", String.valueOf(item_name.get(position)));
                intent.putExtra("count", String.valueOf(item_count.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return item_name.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView item_name_txt, item_count_txt;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_name_txt = itemView.findViewById(R.id.item_name_txt);
            item_count_txt = itemView.findViewById(R.id.item_count_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
