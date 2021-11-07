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
                    UserDatabase userDatabase = UserDatabase.getInstance(getApplicationContext());
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
            Toast.makeText(SignUpActivity.this, "Please complete all information!", Toast.LENGTH_SHORT).show();
            return false;
        }

        User userFromDB = UserDatabase.getInstance(this).userDao().checkExistence(user.getEmail());
        if(userFromDB != null){
            Toast.makeText(SignUpActivity.this, "Email already existed!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}