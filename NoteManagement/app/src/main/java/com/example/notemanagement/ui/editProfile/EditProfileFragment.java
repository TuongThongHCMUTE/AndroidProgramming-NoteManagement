package com.example.notemanagement.ui.editProfile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.notemanagement.Common;
import com.example.notemanagement.R;
import com.example.notemanagement.SignInActivity;
import com.example.notemanagement.database.UserDatabase;
import com.example.notemanagement.entity.User;

public class EditProfileFragment extends Fragment {

    private EditText edtFirstname;
    private EditText edtLastname;
    private EditText edtEmail;
    private Button btnChange;
    private Button btnHome;
    private SharedPreferences sharedPreferences;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        edtFirstname = (EditText) root.findViewById(R.id.edtFirstname);
        edtLastname = (EditText) root.findViewById(R.id.edtLastname);
        edtEmail = (EditText) root.findViewById(R.id.edtEmail);

        getProfile(Common.userId);

        btnChange = (Button) root.findViewById(R.id.btnChangeProfile);
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile(Common.userId);
            }
        });

        btnHome = (Button) root.findViewById(R.id.btnHomeProfile);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });
        return root;
    }

    public void updateProfile(int uid){
        String firstName = edtFirstname.getText().toString();
        String lastName = edtLastname.getText().toString();
        String email = edtEmail.getText().toString();

        User user = UserDatabase.getInstance(getContext()).userDao().getUserByID(uid);
        user.setFirstname(firstName);
        user.setLastname(lastName);
        user.setEmail(email);

        try {
            UserDatabase.getInstance(getContext()).userDao().updateUser(user);
            Toast.makeText(this.getContext(), "Update profile successfully!", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            Toast.makeText(this.getContext(), "Update profile failed!", Toast.LENGTH_SHORT).show();
        }

    }

    public void getProfile(int uid){
        User user = UserDatabase.getInstance(getContext()).userDao().getUserByID(uid);
        edtFirstname.setText(user.getFirstname());
        edtLastname.setText(user.getLastname());
        edtEmail.setText(user.getEmail());
    }
}