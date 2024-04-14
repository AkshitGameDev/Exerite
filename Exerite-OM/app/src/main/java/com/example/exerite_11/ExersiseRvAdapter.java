package com.example.exerite_11;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
public class ExersiseRvAdapter extends RecyclerView.Adapter<ExersiseRvAdapter.MyViewHolder>{
    Context context;
    Fragment fragment;
    ArrayList<ExersiseModel> exersiseModels;
     public ExersiseRvAdapter(Context context , ArrayList<ExersiseModel> exersiseModels){
         this.context = context;
         this.exersiseModels = exersiseModels;
     }
     public void seTilteredList(ArrayList<ExersiseModel> filteredExersiseModels ){
         this.exersiseModels = filteredExersiseModels;
         notifyDataSetChanged();
     }
    @NonNull
    @Override
    public ExersiseRvAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.exersise_row, parent, false);
        return new  ExersiseRvAdapter.MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ExersiseRvAdapter.MyViewHolder holder, int position) {
         holder.Name.setText(exersiseModels.get(position).getName());
         holder.Reps.setText(exersiseModels.get(position).getReps());
    }
    @Override
    public int getItemCount() {
        return exersiseModels.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView Name;
        TextView Reps;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.exe_title);
            Reps = itemView.findViewById(R.id.exe_duration);
        }
    }
}
