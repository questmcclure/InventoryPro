package com.zybooks.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddItemActivity extends AppCompatActivity {

    String username;    // Username is used to guarantee database items are bound to user's account
    EditText name, count;   // Text fields
    Button add;             // Hover button

    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        username = LoginScreen.theUser; // Grabs username that the user logged in with


        name = findViewById(R.id.itemName); // assigns EditText
        count = findViewById(R.id.itemCount);   // assigns EditText

        add = findViewById(R.id.addButton); // assigns button

        // Listener event, if add button is tapped, the item is added to inventory
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper DB = new DBHelper(AddItemActivity.this);   // init database object
                DB.addItem(username, name.getText().toString().trim(), Integer.valueOf(count.getText().toString().trim())); // calls addItem function
                Intent intent = new Intent(getApplicationContext(), InventoryActivity.class);   // loads intent to refresh
                startActivity(intent);
            }
        });
    }
}