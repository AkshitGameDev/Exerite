package com.example.exerite_11;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ExersiseRvAdapter extends RecyclerView.Adapter<ExersiseRvAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<ExersiseModel> exersiseModels;

    public ExersiseRvAdapter(Context context, ArrayList<ExersiseModel> exersiseModels) {
        this.context = context;
        this.exersiseModels = exersiseModels;
    }

    public void setFilteredList(ArrayList<ExersiseModel> filteredExersiseModels) {
        this.exersiseModels = filteredExersiseModels;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.exersise_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ExersiseModel model = exersiseModels.get(position);
        holder.name.setText(model.getWorkout_name());
        holder.reps.setText(model.getWorkout_description());
        // Optionally, display category if needed
     //   holder.category.setText(model.getCategory());
    }

    @Override
    public int getItemCount() {
        return exersiseModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView reps;
        //TextView category; // Add this if you want to display the category

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.exe_title);
            reps = itemView.findViewById(R.id.exe_duration);
            //category = itemView.findViewById(R.id.exe_category); // Make sure this view exists in exersise_row.xml
        }
    }
}
