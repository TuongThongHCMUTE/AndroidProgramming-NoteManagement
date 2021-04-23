package com.example.notemanagement.ui.editProfile;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notemanagement.R;
import com.example.notemanagement.dao.UserDao;
import com.example.notemanagement.database.UserDatabase;
import com.example.notemanagement.ui.home.HomeViewModel;

public class EditProfileFragment extends Fragment {

    private EditText edtFirstname;
    private EditText edtLastname;
    private EditText edtEmail;
    private Button btnChange;
    private SharedPreferences sharedPreferences;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_edit_profile, container, false);



        return root;
    }
}