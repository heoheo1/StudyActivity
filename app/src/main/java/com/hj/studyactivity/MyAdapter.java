package com.hj.studyactivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    ArrayList<Item> itemArrayList;

    public MyAdapter(ArrayList<Item> itemArrayList) {
        this.itemArrayList = itemArrayList;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view,parent,false);


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(itemArrayList.get(position).getTitle());
        holder.contents.setText(itemArrayList.get(position).getContents());
        holder.imageView.setImageURI(itemArrayList.get(position).getUri());

    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView contents;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.txt_card_title);
            this.contents = itemView.findViewById(R.id.txt_card_contents);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
