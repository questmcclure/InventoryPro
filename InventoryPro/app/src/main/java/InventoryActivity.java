package com.zybooks.project2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Set;

public class InventoryActivity extends AppCompatActivity {

    String theUser;
    FloatingActionButton add_button;
    DBHelper DB;
    ArrayList<String> item_name, item_count;
    RecyclerView recyclerView;
    CustomAdapter customAdapter;
    Activity activity;
    private Context context;

    int inventorySmsLimit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        theUser = LoginScreen.theUser;
        recyclerView = findViewById(R.id.recyclerView);
        add_button = findViewById(R.id.add_button);

        DB = new DBHelper(InventoryActivity.this);
        item_name = new ArrayList<>();
        item_count = new ArrayList<>();

        storeDataInArrays();    // called for pulling data from database
        // Links the customadapter
        customAdapter = new CustomAdapter(InventoryActivity.this,this, item_name, item_count);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(InventoryActivity.this));
        // event listener for the hover "add" button
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddItemActivity.class);
                startActivity(intent);
            }
        });
    }
    // Allows the updated inventory item to reflect in real time. Without this, the app
    // would require a restart
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            recreate();
        }
    }
    // reads database with the users username as a passed filter
    void storeDataInArrays() {
        Cursor cursor = DB.readAllData(theUser.toString());
        if (cursor == null) {
            Toast.makeText(this, "no data.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "no data.", Toast.LENGTH_SHORT).show();
        }
        else {
            while (cursor.moveToNext()) {
                item_name.add(cursor.getString(0));
                item_count.add(cursor.getString(1));

                /*
                inventorySmsLimit = Settings.lim;
                int count = Integer.parseInt(cursor.getString(1));
                */
                // This if statement controls if an SMS is sent out or not depending on
                // inventory level. Original plan was to have the user choose what amount
                // limit they wanted for each item, but still a WIP. very buggy :(
                if (cursor.getString(1).equals("0")) {
                    Settings s = new Settings();
                    s.sendSMS(cursor.getString(0)); // Sends the SMS -buggy-
                }

            }
        }
    }
    // Displays setting icon in top right
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    // Act intent upon selecting the settings button
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.settings)
        {
            Intent intent = new Intent(getApplicationContext(), Settings.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}