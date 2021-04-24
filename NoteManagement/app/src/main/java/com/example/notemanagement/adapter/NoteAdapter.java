package com.example.notemanagement.adapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notemanagement.R;
import com.example.notemanagement.entity.Note;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    Context context;
    List<Note> listNote;

    public NoteAdapter(Context context, List<Note> listNote) {
        this.context = context;
        this.listNote = listNote;
    }

    public void setData(List<Note> list){
        this.listNote = list;
        notifyDataSetChanged();
    }

    class  ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        TextView name, planDate, createDate, category, priority, status;
        ConstraintLayout itemNote;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ view
            name = itemView.findViewById(R.id.tvName);
            category = itemView.findViewById(R.id.tvCategory);
            priority = itemView.findViewById(R.id.tvPriority);
            status = itemView.findViewById(R.id.tvStatus);
            planDate = itemView.findViewById(R.id.tvPlanDate);
            createDate = itemView.findViewById(R.id.tvDateCreate);

            itemNote = itemView.findViewById(R.id.item_note_row);
            itemNote.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(this.getAdapterPosition(),0,0,"Edit");
            menu.add(this.getAdapterPosition(),1,1,"Delete");
        }
    }

    @NonNull
    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_note_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.ViewHolder holder, int position) {
        Note note = listNote.get(position);

        holder.name.setText("Name: " + note.getName());
        holder.category.setText("Category: " + note.getCategory());
        holder.priority.setText("Priority: " + note.getPriority());
        holder.status.setText("Status: " + note.getStatus());
        holder.planDate.setText("Plan date: " + note.getPlanDate());
        holder.createDate.setText("Create date: " + note.getCreateDate());
    }

    @Override
    public int getItemCount() {
        return listNote.size();
    }
}
