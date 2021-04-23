package com.example.notemanagement.ui.status;

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
import com.example.notemanagement.adapter.StatusAdapter;
import com.example.notemanagement.database.StatusDatabase;
import com.example.notemanagement.entity.Status;
import com.example.notemanagement.ui.home.HomeViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class StatusFragment extends Fragment {

    RecyclerView recyclerView;
    List<Status> listStatus;
    StatusAdapter statusAdapter;
    FloatingActionButton btnOpen;
    Button btAdd, btClose;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        listStatus = new ArrayList<>();

        View root = inflater.inflate(R.layout.fragment_status, container, false);

        setRecyclerView(root);

        btnOpen = (FloatingActionButton) root.findViewById(R.id.btOpenStatusDialog);
        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenStatusDialog(true, -1);
            }
        });

        return root;
    }

    private void setRecyclerView(View root){
        recyclerView = root.findViewById(R.id.rvStatus);
        recyclerView.setHasFixedSize(true);

        listStatus = StatusDatabase.getInstance(getContext()).StatusDAO().getListStatus();
        statusAdapter = new StatusAdapter(getContext(), listStatus);

        statusAdapter.setData(listStatus);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setAdapter(statusAdapter);
    }

    public void OpenStatusDialog(boolean isAddNew,@Nullable int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = getLayoutInflater().inflate(R.layout.dialog_status, null);
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
                EditText edtStatusName = view.findViewById(R.id.edtStatusName);
                String statusName = "Name: " + edtStatusName.getText().toString();

                String date = "Create Date: " + android.text.format.DateFormat.format("yyyy-MM-dd hh:mm:ss", new java.util.Date());

                if(isAddNew == true){
                    AddStatus(new Status(statusName, date));
                }
                else
                {
                    Status status = listStatus.get(position);
                    status.setName(statusName);
                    status.setCreateDate(date);
                    UpdateStatus(status);
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
                OpenStatusDialog(false, position);
                return true;
            case 1:
                return DeleteStatus(position);
        }
        return super.onContextItemSelected(item);
    }

    public void AddStatus(Status status){
        StatusDatabase.getInstance(getContext()).StatusDAO().insertStatus(status);

        listStatus = StatusDatabase.getInstance(getContext()).StatusDAO().getListStatus();
        statusAdapter.setData(listStatus);
    }

    public void UpdateStatus(Status status){
        StatusDatabase.getInstance(getContext()).StatusDAO().updateStatus(status);

        listStatus = StatusDatabase.getInstance(getContext()).StatusDAO().getListStatus();
        statusAdapter.setData(listStatus);
    }

    public boolean DeleteStatus(int position)
    {
        Status status = listStatus.get(position);
        StatusDatabase.getInstance(getContext()).StatusDAO().deleteStatus(status);
        listStatus.remove(position);
        statusAdapter.notifyItemRemoved(position);
        return true;
    }
}