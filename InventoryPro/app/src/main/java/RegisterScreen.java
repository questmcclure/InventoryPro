package com.zybooks.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterScreen extends AppCompatActivity {

    Button register, existingLoginButton;
    EditText username, password, rePassword;
    DBHelper DB;
    int counter = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);

        register = (Button) findViewById(R.id.registerButton);
        existingLoginButton = (Button) findViewById(R.id.existingloginButton);

        username = (EditText) findViewById(R.id.newUsernameText);
        password = (EditText) findViewById(R.id.newPasswordText);
        rePassword = (EditText) findViewById(R.id.reenterpasswordText);

        DB = new DBHelper(this);
        // listener for register button
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = rePassword.getText().toString();
                // if fields are empty, tell user
                if(username.equals("") || password.equals("") || repass.equals("")) {
                    Toast.makeText(getApplicationContext(),
                            "Fields cannot be empty.",Toast.LENGTH_SHORT).show();
                }
                else {
                    // If the passwords are the same
                    if (pass.equals(repass)) {
                        Boolean checkuser = DB.checkusername(user);
                        // Username is available
                        if (checkuser==false) {
                            // Add new user to database
                            Boolean insert = DB.insertData(user, pass);
                            if (insert==true) {
                                Toast.makeText(getApplicationContext(),
                                        "Registered Successfully!",Toast.LENGTH_SHORT).show();
                                // Send user back to login
                                Intent intent = new Intent(getApplicationContext(), LoginScreen.class);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(getApplicationContext(),
                                        "Registered Failed...",Toast.LENGTH_SHORT).show();
                            }
                        }
                        // User is in the system
                        else {
                            Toast.makeText(getApplicationContext(),
                                    "Username exists. Please sign in.",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(),
                                "Passwords do not match.",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        // Swaps back to login screen
        existingLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginScreen.class);
                startActivity(intent);
            }
        });
    }
}