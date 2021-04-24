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
import com.example.notemanagement.entity.User;

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
                User userEntity = new User(email.getText().toString(),
                        password.getText().toString(),
                        "",
                        "");

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

    private Boolean validateInput(User user, String repass, String emailCheck){
        if (user.getEmail().isEmpty() ||
                user.getPassword().isEmpty() ||
                repass.equals("")) {
            return false;
        }
        UserDatabase userDatabase1 = UserDatabase.getUserDatabase(getApplicationContext());
        UserDao userDao1 = userDatabase1.userDao();

        new Thread(new Runnable() {
            @Override
            public void run() {
                User userEntity = userDao1.checkExistence(emailCheck);
                if (userEntity != null) {
                    return ;
                }
            }
        }).start();
        return true;
    }
}