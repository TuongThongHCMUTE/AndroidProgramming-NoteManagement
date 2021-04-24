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
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences("dataLogin", MODE_PRIVATE);

        setContentView(R.layout.activity_sign_in);
        email = findViewById(R.id.email_login);
        password = findViewById(R.id.password_login);
        login = findViewById(R.id.btnSignIn_Login);
        exit = findViewById(R.id.btnExit);
        checkBox = findViewById(R.id.checkBox);
        floatingbtnSignUp = findViewById(R.id.floatingbtnSignUp);

        rememberUser();

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
                                finish();

                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putInt("userID", user.getUserID());
                                editor.putString("userEmail", user.getEmail());
                                editor.putString("userPass",user.getPassword());
                                editor.putString("userFirstname", user.getFirstname());
                                editor.putString("userLastname", user.getLastname());

                                if (checkBox.isChecked()) {
                                    editor.putString("email", emailLogin);
                                    editor.putString("password", passLogin);
                                    editor.putBoolean("checked", true);
                                } else {
                                    editor.remove("email");
                                    editor.remove("password");
                                    editor.remove("checked");
                                }

                                startActivity(new Intent(SignInActivity.this, MainActivity.class));
                            }
                        }
                    }).start();
                }
            }
        });
    }

    public void rememberUser(){
        email.setText(sharedPreferences.getString("email",""));
        password.setText(sharedPreferences.getString("password",""));
        checkBox.setChecked(sharedPreferences.getBoolean("checked",false));
    }
}