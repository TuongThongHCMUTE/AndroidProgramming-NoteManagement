package com.example.notemanagement.ui.changePassword;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notemanagement.Common;
import com.example.notemanagement.R;
import com.example.notemanagement.database.UserDatabase;
import com.example.notemanagement.entity.User;
import com.example.notemanagement.ui.home.HomeViewModel;

public class ChangePasswordFragment extends Fragment {

    private EditText etCurrentPassword;
    private EditText etNewPassword;
    private EditText etReNewPassword;
    private Button btnChangePassword;
    private Button btnHome;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_change_password, container, false);

        etCurrentPassword = root.findViewById(R.id.et_current_password);
        etNewPassword = root.findViewById(R.id.et_new_password);
        etReNewPassword = root.findViewById(R.id.et_renew_password);

        btnChangePassword = root.findViewById(R.id.btnChangePassword);
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentPassword = etCurrentPassword.getText().toString();
                String newPassword = etNewPassword.getText().toString();
                String reNewPassword = etReNewPassword.getText().toString();

                Log.e("new", newPassword);
                Log.e("renew", reNewPassword);

                int result = validateInput(Common.userId, currentPassword, newPassword, reNewPassword);
                processChangePassword(result, Common.userId, currentPassword);
            }
        });

        btnHome = root.findViewById(R.id.btnHomeChangepass);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return root;
    }

    public boolean changePassword(int uid, String newPassword)
    {
        try {
            User user = UserDatabase.getInstance(getContext()).userDao().getUserByID(uid);
            user.setPassword(newPassword);
            UserDatabase.getInstance(getContext()).userDao().updateUser(user);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public int validateInput(int uid, String currentPassword, String newPassword, String reNewPassword)
    {
        // One of input fields is empty
        if(currentPassword.isEmpty() || reNewPassword.isEmpty() || newPassword.isEmpty()){
            return -2;
        }

        // Current Password is Wrong
        User user = UserDatabase.getInstance(getContext()).userDao().getUserByID(uid);
        if(user.getPassword() == currentPassword){
            return -1;
        }

        // New Password is not equal Re New Password
        if(!(newPassword.equals(reNewPassword))){
            return 0;
        }

        // Valid Input
        return 1;
    }

    public void processChangePassword(int result, int uid, String currentPassword){
        switch (result){
            // One of input fields is empty
            case -2:
                Toast.makeText(this.getContext(), "Please complete all information!", Toast.LENGTH_LONG).show();
                break;
            // Current Password is Wrong
            case -1:
                Toast.makeText(this.getContext(), "Current Password is Wrong!", Toast.LENGTH_LONG).show();
                break;
            // New Password is not equal Re New Password
            case 0:
                Toast.makeText(this.getContext(), "Check Your ReNew Password again!", Toast.LENGTH_LONG).show();
                break;
            case 1:
                if(changePassword(uid, currentPassword)){
                    Toast.makeText(this.getContext(), "Change Password Successfully!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this.getContext(), "Change Password Fail!", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }
}