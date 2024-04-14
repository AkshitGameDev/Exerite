package com.example.exerite_11;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.net.PortUnreachableException;
import java.util.ArrayList;

public class CardioRvAdapter extends RecyclerView.Adapter<ExersiseRvAdapter.MyViewHolder>{

    Context context;
    ArrayList<CardioModel> cardioModel;

    public CardioRvAdapter(Context context, ArrayList<CardioModel> cardioModels){
        this.context = context;
        this.cardioModel = cardioModels;
    }

    @NonNull
    @Override
    public ExersiseRvAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cardio_row, parent, false);
        return new  ExersiseRvAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExersiseRvAdapter.MyViewHolder holder, int position) {

        holder.Name.setText(cardioModel.get(position).getName());
        holder.Reps.setText(cardioModel.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return cardioModel.size();
    }

    public static  class MyViewHolder extends  RecyclerView.ViewHolder{

        TextView Name;
        TextView Time;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Name = itemView.findViewById(R.id.exe_title);
            Time = itemView.findViewById(R.id.exe_duration);
        }
    }
}
