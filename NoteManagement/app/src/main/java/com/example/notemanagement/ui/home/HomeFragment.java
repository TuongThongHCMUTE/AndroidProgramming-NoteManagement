package com.example.notemanagement.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.notemanagement.Common;
import com.example.notemanagement.R;
import com.example.notemanagement.database.NoteDatabase;
import com.example.notemanagement.database.StatusDatabase;
import com.example.notemanagement.entity.Note;
import com.example.notemanagement.entity.Status;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private PieChart pieChart;
    private List<Note> notes;
    private List<Status> statuses;
    int[] color;
    private final int wrong = 0, pending = 1, processing = 2, done = 3;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        pieChart = root.findViewById(R.id.piechart);
        pieChart.setUsePercentValues(true);
        pieChart.setDragDecelerationFrictionCoef(0.99f);
        pieChart.setDrawHoleEnabled(false);

        setPieChartValue();

        return root;
    }

    public void setPieChartValue(){
        notes = NoteDatabase.getInstance(getContext()).NoteDAO().getListNote(Common.userId);
        statuses = StatusDatabase.getInstance(getContext()).StatusDAO().getListStatus();

        ArrayList<PieEntry> entries = new ArrayList<>();
        String temp;
        int count, checkStatusName, countColorID;

        color = new  int[statuses.size()];
        countColorID = 0;
    }
}