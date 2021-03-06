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
import com.example.notemanagement.entity.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    Context context;
    List<Category> listCategory;

    public CategoryAdapter(Context context, List<Category> listCategory) {
        this.context = context;
        this.listCategory = listCategory;
    }

    public void setData(List<Category> list){
        this.listCategory = list;
        notifyDataSetChanged();
    }

    class  ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        TextView name, createDate;
        ConstraintLayout itemCategory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ view
            name = itemView.findViewById(R.id.tvName);
            createDate = itemView.findViewById(R.id.tvDateCreate);

            itemCategory = itemView.findViewById(R.id.item_row);
            itemCategory.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(this.getAdapterPosition(),0,0,"Edit");
            menu.add(this.getAdapterPosition(),1,1,"Delete");
        }
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        Category category = listCategory.get(position);

        holder.name.setText("Name: " + category.getName());
        holder.createDate.setText("Create date: " + category.getCreateDate().toString());
    }

    @Override
    public int getItemCount() {
        return listCategory.size();
    }
}
