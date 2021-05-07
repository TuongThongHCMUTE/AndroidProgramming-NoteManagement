package com.example.notemanagement.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.example.notemanagement.R;
import com.example.notemanagement.database.NoteDatabase;
import com.example.notemanagement.database.StatusDatabase;
import com.example.notemanagement.entity.Note;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    String [] statuses;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        statuses = StatusDatabase.getInstance(getContext()).StatusDAO().getStatusName();

        List<DataEntry> dataEntries = new ArrayList<>();
        DataEntry entry;
        for (int i = 0; i < statuses.length; i++){
            entry = new ValueDataEntry(statuses[i],
                    NoteDatabase.getInstance(getContext()).NoteDAO().countQuantityByStatus(statuses[i]));
            dataEntries.add(entry);
        }

        AnyChartView pieChart = root.findViewById(R.id.pie_chart);
        Pie pie = AnyChart.pie();
        pie.data(dataEntries);
        pieChart.setChart(pie);

        return root;
    }
}