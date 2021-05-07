package com.example.notemanagement.ui.category;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

import com.example.notemanagement.Common;
import com.example.notemanagement.R;
import com.example.notemanagement.adapter.CategoryAdapter;
import com.example.notemanagement.database.CategoryDatabase;
import com.example.notemanagement.database.NoteDatabase;
import com.example.notemanagement.entity.Category;
import com.example.notemanagement.entity.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CategoryFragment extends Fragment {

    RecyclerView recyclerView;
    List<Category> listCategory;
    CategoryAdapter categoryAdapter;
    FloatingActionButton btnOpen;
    Button btAdd, btClose;
    EditText edtCategoryName;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        listCategory = new ArrayList<>();

        View root = inflater.inflate(R.layout.fragment_category, container, false);

        setRecyclerView(root);

        btnOpen = (FloatingActionButton) root.findViewById(R.id.btOpenCategoryDialog);
        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenCategoryDialog(true, -1);
            }
        });

        return root;
    }

    private void setRecyclerView(View root){
        recyclerView = root.findViewById(R.id.rvCategory);
        recyclerView.setHasFixedSize(true);

        listCategory = CategoryDatabase.getInstance(getContext()).categoryDAO().getListCategory(Common.userId);
        categoryAdapter = new CategoryAdapter(getContext(), listCategory);

        categoryAdapter.setData(listCategory);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setAdapter(categoryAdapter);
    }

    public void OpenCategoryDialog(boolean isAddNew,@Nullable int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = getLayoutInflater().inflate(R.layout.dialog_category, null);
        AlertDialog alertDialog = builder.create();

        alertDialog.setView(view);

        alertDialog.show();

        btClose = view.findViewById(R.id.btClose);
        btClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });

        btAdd = view.findViewById(R.id.btAdd);

        if(isAddNew == false)
        {
            EditText edtCategoryName = view.findViewById(R.id.edtCategoryName);
            Category category = listCategory.get(position);
            btAdd.setText("Update");
            edtCategoryName.setText(category.getName());
        }

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edtCategoryName = view.findViewById(R.id.edtCategoryName);
                String categoryName = edtCategoryName.getText().toString();

                String date = android.text.format.DateFormat.format("yyyy-MM-dd hh:mm:ss", new java.util.Date()).toString();

                String error = "";

                if(isAddNew == true){
                    error = validateCategory(categoryName, "");
                    if(error.isEmpty()){
                        AddCategory(new Category(categoryName, date, Common.userId));
                    } else {
                        edtCategoryName.setError(error);
                        return;
                    }
                }
                else
                {
                    Category category = listCategory.get(position);

                    error = validateCategory(categoryName, category.getName());
                    if(error.isEmpty()){
                        NoteDatabase.getInstance(getContext()).NoteDAO().changeCategoryName(categoryName, category.getName(), Common.userId);
                        category.setName(categoryName);
                        UpdateCategory(category);
                    } else {
                        edtCategoryName.setError(error);
                        return;
                    }
                }

                alertDialog.cancel();
            }
        });
    }
    public String validateCategory(String name, String oldName){
        String[] listNames = CategoryDatabase.getInstance(getContext()).categoryDAO().getCategoryName(Common.userId);

        if(!name.equals("Working") && !name.equals("Relax") && !name.equals("Study")){
            return "Name must be Working, Relax or Study";
        }

        if (oldName.isEmpty()){
            for (int i=0;i<listNames.length;i++){
                if (listNames[i].equals(name)){
                    return "Category is duplicated";
                }
            }
        }

        if (!name.equals(oldName)) {
            for (int i=0;i<listNames.length;i++){
                if (listNames[i].equals(name)){
                    return "Category is duplicated";
                }
            }
        }
        return "";
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position = item.getGroupId();
        switch (item.getItemId()){
            case 0:
                OpenCategoryDialog(false, position);
                return true;
            case 1:
                return DeleteCategory(position);
        }
        return super.onContextItemSelected(item);
    }

    public void AddCategory(Category category){
        CategoryDatabase.getInstance(getContext()).categoryDAO().insertCategory(category);

        listCategory = CategoryDatabase.getInstance(getContext()).categoryDAO().getListCategory(Common.userId);
        categoryAdapter.setData(listCategory);
    }

    public void UpdateCategory(Category category){
        CategoryDatabase.getInstance(getContext()).categoryDAO().updateCategory(category);

        listCategory = CategoryDatabase.getInstance(getContext()).categoryDAO().getListCategory(Common.userId);
        categoryAdapter.setData(listCategory);
    }

    public boolean DeleteCategory(int position)
    {
        Category category = listCategory.get(position);
        CategoryDatabase.getInstance(getContext()).categoryDAO().deleteCategory(category);
        listCategory.remove(position);
        categoryAdapter.notifyItemRemoved(position);
        return true;
    }
}