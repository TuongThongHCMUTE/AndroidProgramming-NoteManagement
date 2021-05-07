package com.example.notemanagement.ui.home;

import android.graphics.Color;
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
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
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
        //pieChart.setDragDecelerationFrictionCoef(0.99f);
        pieChart.setDrawHoleEnabled(false);

        setPieChartValue();

        return root;
    }

    public void setPieChartValue(){
        notes = NoteDatabase.getInstance(getContext()).NoteDAO().getListNote(Common.userId);
        statuses = StatusDatabase.getInstance(getContext()).StatusDAO().getListStatus(Common.userId);

        ArrayList<PieEntry> entries = new ArrayList<>();
        String temp;
        int count, checkStatusName, countColorID;

        color = new  int[statuses.size()];
        countColorID = 0;

        for (Status i:statuses){
            temp = i.getName();
            checkStatusName = checkStatusName(temp);
            setColorPieChart(countColorID++, checkStatusName);
            count = NoteDatabase.getInstance(getContext()).NoteDAO().countNotesOfUserByStatusID(Common.userId, temp);
            if(count > 0){
                entries.add(new PieEntry(count, temp));
            }
        }


        PieDataSet dataset = new PieDataSet(entries, "test");

        dataset.setSliceSpace(3f);
        dataset.setSelectionShift(5f);
        dataset.setColors(color);

        PieData data= new PieData((dataset));
        data.setValueTextSize(14f);
        data.setValueTextColor(Color.WHITE);

        pieChart.setData(data);
    }

    private void setColorPieChart(int i, int checkStatusName) {
        if(checkStatusName == wrong)
            return;
        if(checkStatusName == done)
            color[i] = Color.rgb(0,0,255);
        if(checkStatusName == processing)
            color[i] = Color.rgb(153,153,153);
        if(checkStatusName == pending)
            color[i] = Color.rgb(255,0,0);
    }


    public int checkStatusName(String statusName){
        if(statusName.equals("Done"))
            return done;
        if(statusName.equals("Processing"))
            return processing;
        if(statusName.equals("Pending"))
            return pending;
        return wrong;
    }
}