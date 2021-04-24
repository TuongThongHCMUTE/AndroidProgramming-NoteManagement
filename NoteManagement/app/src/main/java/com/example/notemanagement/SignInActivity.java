package com.example.notemanagement;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notemanagement.dao.UserDao;
import com.example.notemanagement.database.UserDatabase;
import com.example.notemanagement.entity.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SignInActivity extends AppCompatActivity {

    public static String emailLogin, passLogin;
    EditText email, password;
    Button login, exit;
    CheckBox checkBox;
    FloatingActionButton floatingbtnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        email = findViewById(R.id.email_login);
        password = findViewById(R.id.password_login);
        login = findViewById(R.id.btnSignIn_Login);
        exit = findViewById(R.id.btnExit);
        checkBox = findViewById(R.id.checkBox);
        floatingbtnSignUp = findViewById(R.id.floatingbtnSignUp);

        SharedPreferences preferences = getSharedPreferences("checkBox", MODE_PRIVATE);
        String rememberMe = preferences.getString("checkBox", "");

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    SharedPreferences preferences = getSharedPreferences("checkBox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("checkBox", "true");
                    editor.apply();;
                    Toast.makeText(SignInActivity.this, "Checked", Toast.LENGTH_SHORT).show();
                }
                else if (!buttonView.isChecked()) {
                    SharedPreferences preferences = getSharedPreferences("checkBox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("checkBox", "false");
                    editor.apply();;
                    Toast.makeText(SignInActivity.this, "Unchecked", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //exit button
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });

        //floating button to open the sign up page
        floatingbtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailLogin = email.getText().toString();
                passLogin = password.getText().toString();

                if (emailLogin.isEmpty() || passLogin.isEmpty()) {
                    Toast.makeText(SignInActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    UserDatabase userDatabase = UserDatabase.getUserDatabase(getApplicationContext());
                    UserDao userDao = userDatabase.userDao();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            User user = userDao.login(emailLogin, passLogin);
                            if (user == null) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(SignInActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            else {
                                startActivity(new Intent(SignInActivity.this, MainActivity.class));
                            }
                        }
                    }).start();
                }
            }
        });
    }
}