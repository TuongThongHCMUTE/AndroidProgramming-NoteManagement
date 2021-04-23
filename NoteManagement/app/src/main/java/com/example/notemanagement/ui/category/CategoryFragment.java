package com.example.notemanagement.ui.category;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notemanagement.R;
import com.example.notemanagement.adapter.CategoryAdapter;
import com.example.notemanagement.database.CategoryDatabase;
import com.example.notemanagement.dialog.CategoryDialog;
import com.example.notemanagement.entity.Category;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CategoryFragment extends Fragment implements CategoryDialog.Custom_DialogInterFace {
    private  static final String TAG = "CategoryFragment";

    private CategoryViewModel categoryViewModel;

    RecyclerView recyclerView;
    List<Category> listCategory;
    CategoryAdapter categoryAdapter;

    Button openDialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        categoryViewModel =
                new ViewModelProvider(this).get(CategoryViewModel.class);

        listCategory = new ArrayList<>();

        View root = inflater.inflate(R.layout.fragment_category, container, false);

        setRecyclerView(root);

        openDialog =(Button) root.findViewById(R.id.btAddCategory);

        openDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(), "Add successfully", Toast.LENGTH_LONG).show();
                Log.d(TAG, "onClick: opening dialog");

                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                CategoryDialog categoryDialog = new CategoryDialog();
                categoryDialog.show(ft, "CategoryDialog");
            }
        });

        //listCategory =  CategoryDatabase.getInstance(getContext()).categoryDAO().getListCategory();
        //categoryAdapter.setData(listCategory);

        return root;
    }

    private void setRecyclerView(View root){
        recyclerView = root.findViewById(R.id.rvCategory);
        listCategory = CategoryDatabase.getInstance(getContext()).categoryDAO().getListCategory();
        categoryAdapter = new CategoryAdapter(getContext(), listCategory);

        categoryAdapter.setData(listCategory);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setAdapter(categoryAdapter);
    }

    public void addCategory(String categoryName){
        Date createDate = new Date();

        Category category = new Category(categoryName, createDate.toString());

        CategoryDatabase.getInstance(getContext()).categoryDAO().insertCategory(category);

        //Toast.makeText(getActivity(), "Add Category successfully!", Toast.LENGTH_SHORT).show();

        listCategory =  CategoryDatabase.getInstance(getContext()).categoryDAO().getListCategory();
        categoryAdapter.setData(listCategory);
    }
}