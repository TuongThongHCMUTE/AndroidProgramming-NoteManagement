package com.example.notemanagement.ui.changePassword;

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

public class ChangePasswordFragment extends Fragment {

    private ChangePasswordViewModel changePasswordViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        changePasswordViewModel =
                new ViewModelProvider(this).get(ChangePasswordViewModel.class);
        View root = inflater.inflate(R.layout.fragment_change_password, container, false);
        /*final TextView textView = root.findViewById(R.id.text_changePassword);
        changePasswordViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        return root;
    }
}