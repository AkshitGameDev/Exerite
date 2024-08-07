package com.example.exerite_11;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GenericDietRVAdapter extends RecyclerView.Adapter<GenericDietRVAdapter.DietViewHolder> {

    private final Context context;
    private ArrayList<DietModel> dietList;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public GenericDietRVAdapter(Context context, ArrayList<DietModel> dietList, OnItemClickListener listener) {
        this.context = context;
        this.dietList = dietList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DietViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.diet_row, parent, false);
        return new DietViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DietViewHolder holder, int position) {
        DietModel diet = dietList.get(position);

        holder.dishName.setText(diet.getDish());
        holder.calorieCount.setText(diet.getCalories());

        holder.dietDelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbHelper = new DBHelper(context);
                dbHelper.deleteDiet(diet.getDish()); // Using diet name for deletion
                dietList.remove(position);
                notifyItemRemoved(position);
            }
        });

        holder.itemView.setOnClickListener(v -> listener.onItemClick(position));
    }

    @Override
    public int getItemCount() {
        return dietList.size();
    }

    public void setFilteredList(ArrayList<DietModel> filteredList) {
        dietList = filteredList;
        notifyDataSetChanged();
    }

    public void updateList(ArrayList<DietModel> newList) {
        dietList = newList;
        notifyDataSetChanged();
    }

    class DietViewHolder extends RecyclerView.ViewHolder {

        TextView dishName;
        TextView calorieCount;
        ImageView dietDelBtn;

        public DietViewHolder(@NonNull View itemView) {
            super(itemView);
            dishName = itemView.findViewById(R.id.dish_name);
            calorieCount = itemView.findViewById(R.id.calorie_count);
            dietDelBtn = itemView.findViewById(R.id.journal_del_btn); // Ensure this matches your layout
        }
    }
}
