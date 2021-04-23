package com.example.notemanagement.adapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notemanagement.R;
import com.example.notemanagement.entity.Priority;

import java.util.List;

public class PriorityAdapter extends RecyclerView.Adapter<PriorityAdapter.ViewHolder> {
    Context context;
    List<Priority> listPriority;

    public PriorityAdapter(Context context, List<Priority> listPriority) {
        this.context = context;
        this.listPriority = listPriority;
    }

    public void setData(List<Priority> list){
        this.listPriority = list;
        notifyDataSetChanged();
    }

    class  ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        TextView name, createDate;
        ConstraintLayout itemPriority;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ view
            name = itemView.findViewById(R.id.tvName);
            createDate = itemView.findViewById(R.id.tvDateCreate);

            itemPriority = itemView.findViewById(R.id.item_row);
            itemPriority.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(this.getAdapterPosition(),0,0,"Edit");
            menu.add(this.getAdapterPosition(),1,1,"Delete");
        }
    }

    @NonNull
    @Override
    public PriorityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PriorityAdapter.ViewHolder holder, int position) {
        Priority priority = listPriority.get(position);

        holder.name.setText(priority.getName());
        holder.createDate.setText(priority.getCreateDate().toString());
    }

    @Override
    public int getItemCount() {
        return listPriority.size();
    }
}
