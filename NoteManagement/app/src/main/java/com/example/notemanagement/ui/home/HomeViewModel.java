package com.example.notemanagement.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.example.notemanagement.database.NoteDatabase;
import com.example.notemanagement.entity.Note;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<List<DataEntry>> mData;
    List<Note> notes;
    String [] statuses;
    int[] quantity;

    public HomeViewModel() {
        notes = new ArrayList<>();

        List<DataEntry> dataEntries = new ArrayList<>();
        for (int i = 0; i < statuses.length; i++){
            dataEntries.add(new ValueDataEntry(statuses[i], quantity[i]));
        }

        mData = new MutableLiveData<>();
        mData.setValue(dataEntries);
    }

    public LiveData<String> getText() {
        return mText;
    }
    public LiveData<List<DataEntry>> getData() {return mData; }
}