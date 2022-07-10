package com.zybooks.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginScreen extends AppCompatActivity {

    static String theUser;
    Button loginButton, signupButton;
    EditText username, password;
    DBHelper DB;
    int counter = 3; // original intent was for incorrect login attempts (not used currently)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = (Button)findViewById(R.id.loginButton);
        signupButton = (Button)findViewById(R.id.signupButton);

        username = (EditText)findViewById(R.id.usernameText);
        password = (EditText)findViewById(R.id.passwordText);

        DB = new DBHelper(this);
        // event listener for login
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                // if the inputs are empty
                if(username.equals("") || password.equals("")) {
                    Toast.makeText(getApplicationContext(),
                            "Fields cannot be empty.",Toast.LENGTH_SHORT).show();
                }
                else {
                    // checks out, check database for the user
                    Boolean checkuserpass = DB.checkusernamepassword(user, pass);
                    // If found, sign in and load InventoryActivity intent
                    if (checkuserpass==true) {
                        Toast.makeText(getApplicationContext(),
                                "Sign in Successful!",Toast.LENGTH_SHORT).show();
                        theUser = user;
                        Intent intent = new Intent(getApplicationContext(), InventoryActivity.class);
                        startActivity(intent);
                    }
                    // If failed, something is entered wrong
                    else {
                        Toast.makeText(getApplicationContext(),
                                "Invalid Credentials.",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        // Sends the user to the sign up page
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterScreen.class);
                startActivity(intent);
            }
        });
    }
}