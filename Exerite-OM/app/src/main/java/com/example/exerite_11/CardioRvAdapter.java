package com.example.exerite_11;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.jar.Attributes;

public class CardioRvAdapter extends RecyclerView.Adapter<CardioRvAdapter.MyViewHolder>{
    Context context;
    ArrayList<CardioModel> cardioModels;
    public CardioRvAdapter (Context context,ArrayList<CardioModel> cardioModels){
        this.context = context;
        this.cardioModels = cardioModels;
    }
    @NonNull
    @Override
    public CardioRvAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cardio_row, parent, false);
        return new CardioRvAdapter.MyViewHolder(view);
    }
    public void seTilteredList(ArrayList<CardioModel> filteredExersiseModels ){
        this.cardioModels = filteredExersiseModels;
        notifyDataSetChanged();
    }
    @Override
        public void onBindViewHolder(@NonNull CardioRvAdapter.MyViewHolder holder, int position) {
        holder.Name.setText(cardioModels.get(position).getName());
        holder.Time.setText(cardioModels.get(position).getTime());
        }
    @Override
    public int getItemCount() {
        return cardioModels.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
    TextView Name;
    TextView Time;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.car_title);
            Time = itemView.findViewById(R.id.car_duration);
        }
    }
}
