package com.example.exerite_11;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;

public class GenericDietRVAdapter extends RecyclerView.Adapter<GenericDietRVAdapter.MyViewHolder> {
    ArrayList<DietModel> dietModels;
    Context context;

    public GenericDietRVAdapter(Context context, ArrayList<DietModel> dietModels){
        this.context = context;
        this.dietModels = dietModels;
    }

    @NonNull
    @Override
    public GenericDietRVAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.diet_row, parent,false);
        return new GenericDietRVAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenericDietRVAdapter.MyViewHolder holder, int position) {
        holder.Name.setText(dietModels.get(position).getDish());
        holder.Cal.setText(dietModels.get(position).getCalories());
    }
    public void seTilteredList(ArrayList<DietModel> dietModels ){
        this.dietModels = dietModels;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dietModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView Name;
        TextView Cal;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.diet);
            Cal = itemView.findViewById(R.id.calories);

        }
    }
}
