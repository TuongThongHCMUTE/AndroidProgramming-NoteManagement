package com.example.notemanagement.ui.priority;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.notemanagement.R;
import com.example.notemanagement.adapter.PriorityAdapter;
import com.example.notemanagement.database.PriorityDatabase;
import com.example.notemanagement.entity.Priority;
import com.example.notemanagement.ui.home.HomeViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PriorityFragment extends Fragment {

    RecyclerView recyclerView;
    List<Priority> listPriority;
    PriorityAdapter priorityAdapter;
    FloatingActionButton btnOpen;
    Button btAdd, btClose;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        listPriority = new ArrayList<>();

        View root = inflater.inflate(R.layout.fragment_priority, container, false);

        setRecyclerView(root);

        btnOpen = (FloatingActionButton) root.findViewById(R.id.btOpenPriorityDialog);
        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenPriorityDialog(true, -1);
            }
        });

        return root;
    }

    private void setRecyclerView(View root){
        recyclerView = root.findViewById(R.id.rvPriority);
        recyclerView.setHasFixedSize(true);

        listPriority = PriorityDatabase.getInstance(getContext()).PriorityDAO().getListPriority();
        priorityAdapter = new PriorityAdapter(getContext(), listPriority);

        priorityAdapter.setData(listPriority);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setAdapter(priorityAdapter);
    }

    public void OpenPriorityDialog(boolean isAddNew,@Nullable int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = getLayoutInflater().inflate(R.layout.dialog_priority, null);
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
            btAdd.setText("Update");
        }

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edtPriorityName = view.findViewById(R.id.edtPriorityName);
                String priorityName = edtPriorityName.getText().toString();

                String date = android.text.format.DateFormat.format("yyyy-MM-dd hh:mm:ss", new java.util.Date()).toString();

                if(isAddNew == true){
                    AddPriority(new Priority(priorityName, date));
                }
                else
                {
                    Priority priority = listPriority.get(position);
                    priority.setName(priorityName);
                    priority.setCreateDate(date);
                    UpdatePriority(priority);
                }

                alertDialog.cancel();
            }
        });
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position = item.getGroupId();
        switch (item.getItemId()){
            case 0:
                OpenPriorityDialog(false, position);
                return true;
            case 1:
                return DeletePriority(position);
        }
        return super.onContextItemSelected(item);
    }

    public void AddPriority(Priority Priority){
        PriorityDatabase.getInstance(getContext()).PriorityDAO().insertPriority(Priority);

        listPriority = PriorityDatabase.getInstance(getContext()).PriorityDAO().getListPriority();
        priorityAdapter.setData(listPriority);
    }

    public void UpdatePriority(Priority Priority){
        PriorityDatabase.getInstance(getContext()).PriorityDAO().updatePriority(Priority);

        listPriority = PriorityDatabase.getInstance(getContext()).PriorityDAO().getListPriority();
        priorityAdapter.setData(listPriority);
    }

    public boolean DeletePriority(int position)
    {
        Priority priority = listPriority.get(position);
        PriorityDatabase.getInstance(getContext()).PriorityDAO().deletePriority(priority);
        listPriority.remove(position);
        priorityAdapter.notifyItemRemoved(position);
        return true;
    }
}