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

public class JournalRvAdapter extends RecyclerView.Adapter<JournalRvAdapter.JournalViewHolder> {

    private final Context context;
    private ArrayList<JournalModel> journalList;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public JournalRvAdapter(Context context, ArrayList<JournalModel> journalList, OnItemClickListener listener) {
        this.context = context;
        this.journalList = journalList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public JournalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.journal_row, parent, false);
        return new JournalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JournalViewHolder holder, int position) {
        JournalModel journal = journalList.get(position); // Adjust for add button


        holder.lblTitle.setText(journal.getTitle());
        holder.txtDescription.setText(journal.getDescription());
        holder.journal_Del_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbHelper = new DBHelper(context);
                dbHelper.deleteJournal(journal.getId());
                journalList.remove(position);
                notifyItemRemoved(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return journalList.size(); // Extra item for add button
    }

    public void updateList(ArrayList<JournalModel> newList) {
        journalList = newList;
        notifyDataSetChanged();
    }

    class JournalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView lblTitle;
        TextView txtDescription;
        ImageView journal_Del_btn;


        public JournalViewHolder(@NonNull View itemView) {
            super(itemView);
            journal_Del_btn = itemView.findViewById(R.id.journal_del_btn);
            lblTitle = itemView.findViewById(R.id.journal_title_tv);
            txtDescription = itemView.findViewById(R.id.calorie_count);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(getAdapterPosition());
        }
    }
}


