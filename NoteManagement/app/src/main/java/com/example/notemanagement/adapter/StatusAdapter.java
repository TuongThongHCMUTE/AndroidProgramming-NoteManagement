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
import com.example.notemanagement.entity.Status;

import java.util.List;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.ViewHolder> {
    Context context;
    List<Status> listStatus;

    public StatusAdapter(Context context, List<Status> listStatus) {
        this.context = context;
        this.listStatus = listStatus;
    }

    public void setData(List<Status> list){
        this.listStatus = list;
        notifyDataSetChanged();
    }

    class  ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        TextView name, createDate;
        ConstraintLayout itemStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ view
            name = itemView.findViewById(R.id.tvName);
            createDate = itemView.findViewById(R.id.tvDateCreate);

            itemStatus = itemView.findViewById(R.id.item_row);
            itemStatus.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(this.getAdapterPosition(),0,0,"Edit");
            menu.add(this.getAdapterPosition(),1,1,"Delete");
        }
    }

    @NonNull
    @Override
    public StatusAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatusAdapter.ViewHolder holder, int position) {
        Status status = listStatus.get(position);

        holder.name.setText(status.getName());
        holder.createDate.setText(status.getCreateDate().toString());
    }

    @Override
    public int getItemCount() {
        return listStatus.size();
    }
}
