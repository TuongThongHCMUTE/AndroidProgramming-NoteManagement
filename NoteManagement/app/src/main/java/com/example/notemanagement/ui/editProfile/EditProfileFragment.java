package com.example.notemanagement.ui.editProfile;

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

public class EditProfileFragment extends Fragment {

    private EditProfileViewModel editProfileViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        editProfileViewModel =
                new ViewModelProvider(this).get(EditProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        final TextView textView = root.findViewById(R.id.text_editProfile);
        editProfileViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

}