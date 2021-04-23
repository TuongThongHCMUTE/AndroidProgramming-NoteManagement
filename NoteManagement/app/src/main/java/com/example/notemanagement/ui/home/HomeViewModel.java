package com.example.notemanagement.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<List<DataEntry>> mData;
    String [] months = {"Done","Processing","Pending"};
    int[] earning = {2, 6, 2};

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");

        List<DataEntry> dataEntries = new ArrayList<>();
        for (int i = 0; i < months.length; i++){
            dataEntries.add(new ValueDataEntry(months[i], earning[i]));
        }

        mData = new MutableLiveData<>();
        mData.setValue(dataEntries);
    }

    public LiveData<String> getText() {
        return mText;
    }
    public LiveData<List<DataEntry>> getData() {return mData; }
}