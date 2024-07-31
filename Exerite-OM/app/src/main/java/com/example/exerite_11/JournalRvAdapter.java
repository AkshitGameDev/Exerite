package com.example.exerite_11;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

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
// Log the data being set
       // Log.d("JournalRvAdapter", "Binding journal: " + journal.getTitle() + ", " + journal.getDescription());

        holder.lblTitle.setText(journal.getTitle());
            holder.txtDescription.setText(journal.getDescription());

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
       // View viewAdd;
       // View viewJournal;

        public JournalViewHolder(@NonNull View itemView) {
            super(itemView);
            lblTitle = itemView.findViewById(R.id.journal_title_tv);
            txtDescription = itemView.findViewById(R.id.journaldesc_tv);
           // viewAdd = itemView.findViewById(R.id.viewAdd);
            //viewJournal = itemView.findViewById(R.id.viewJournal);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(getAdapterPosition());
        }
    }
}

