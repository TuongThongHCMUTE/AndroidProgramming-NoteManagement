package com.example.notemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notemanagement.dao.UserDao;
import com.example.notemanagement.database.UserDatabase;
import com.example.notemanagement.entity.UserEntity;

public class SignUpActivity extends AppCompatActivity {

    EditText email, password, repassword;
    Button register, login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        repassword = findViewById(R.id.repassword);
        register = findViewById(R.id.btnSignUp);
        login = findViewById(R.id.btnSignIn);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserEntity userEntity = new UserEntity();
                userEntity.setEmail(email.getText().toString());
                userEntity.setPassword(password.getText().toString());
                String repass = repassword.getText().toString();
                String emailCheck = email.getText().toString();

                if (validateInput(userEntity,repass,emailCheck)) {
                    UserDatabase userDatabase = UserDatabase.getUserDatabase(getApplicationContext());
                    UserDao userDao = userDatabase.userDao();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            userDao.registerUser(userEntity);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(SignUpActivity.this, "Registered Successfully!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).start();
                }
                else {
                    Toast.makeText(SignUpActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                }

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
            }
        });
    }

    private Boolean validateInput(UserEntity userEntity, String repass, String emailCheck){
        if (userEntity.getEmail().isEmpty() ||
                userEntity.getPassword().isEmpty() ||
                repass.equals("")) {
            return false;
        }
        UserDatabase userDatabase1 = UserDatabase.getUserDatabase(getApplicationContext());
        UserDao userDao1 = userDatabase1.userDao();

        new Thread(new Runnable() {
            @Override
            public void run() {
                UserEntity userEntity1 = userDao1.checkExistence(emailCheck);
                if (userEntity1 != null) {
                    return ;
                }
            }
        }).start();
        return true;
    }
}