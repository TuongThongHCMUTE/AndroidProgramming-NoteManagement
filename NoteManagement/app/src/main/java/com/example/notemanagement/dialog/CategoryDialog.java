package com.example.notemanagement.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.notemanagement.R;
import com.example.notemanagement.ui.category.CategoryFragment;

public class CategoryDialog extends DialogFragment {
    private  static final String TAG = "CategoryDialog";

    TextView header;
    EditText categoryName;
    Custom_DialogInterFace dialogInterface;
    Button actionAdd, actionClose;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.category_dialog, container, false);

        actionClose = root.findViewById(R.id.btClose);
        actionAdd = root.findViewById(R.id.btAdd);
        categoryName = root.findViewById(R.id.edtCategoryName);
        header = root.findViewById(R.id.tvHeader);

        actionClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: closing dialog");
                getDialog().dismiss();
            }
        });

        actionAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: add category");

                String name = categoryName.getText().toString();
                if(!inflater.equals("")) {
                    //dialogInterface.addCategory(name);
                    CategoryFragment categoryFragment = new CategoryFragment();
                    categoryFragment.addCategory(name);
                }
                getDialog().dismiss();
            }
        });
        return root;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            dialogInterface = (Custom_DialogInterFace) getTargetFragment();
        }catch (ClassCastException e){
            Log.e(TAG, "onAttach: ClassCastException : " + e.getMessage());
        }

    }

    public interface Custom_DialogInterFace{
        void addCategory(String categoryName);
    }
}


