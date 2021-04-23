package com.example.notemanagement.ui.category;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notemanagement.R;
import com.example.notemanagement.adapter.CategoryAdapter;
import com.example.notemanagement.database.CategoryDatabase;
import com.example.notemanagement.entity.Category;

import java.util.Date;
import java.util.List;

public class CategoryFragment extends Fragment {

    private CategoryViewModel categoryViewModel;
    RecyclerView recyclerView;
    List<Category> listCategory;
    CategoryAdapter categoryAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        categoryViewModel =
                new ViewModelProvider(this).get(CategoryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_category, container, false);

        recyclerView = root.findViewById(R.id.rvCategory);
        listCategory = CategoryDatabase.getInstance(getContext()).categoryDAO().getListCategory();
        categoryAdapter = new CategoryAdapter(getContext(), listCategory);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setAdapter(categoryAdapter);

        return root;
    }

    private void addCategory(){
        String name = "Task 1";
        Date createDate = new Date();

        Category category = new Category(name, createDate.toString());
        CategoryDatabase.getInstance(getContext()).categoryDAO().insertCategory(category);
        Toast.makeText(getContext(), "Add successfully", Toast.LENGTH_LONG).show();
    }
}