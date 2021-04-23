package com.example.notemanagement.ui.note;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.notemanagement.R;
import com.example.notemanagement.ui.home.HomeViewModel;

public class NoteFragment extends Fragment {

    private NoteViewModel noteViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        noteViewModel =
                new ViewModelProvider(this).get(NoteViewModel.class);
        View root = inflater.inflate(R.layout.fragment_note, container, false);
        final TextView textView = root.findViewById(R.id.text_note);
        noteViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

}