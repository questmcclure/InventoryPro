package com.zybooks.project2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
/*
    Update inventory item class, uses DBHelper update data function and deleteOneRow (asks for permission)
 */
public class UpdateItemActivity extends AppCompatActivity {

    EditText name_input, count_input;
    Button update_button, delete_button;

    String username, name, count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_item);

        username = LoginScreen.theUser;

        name_input = findViewById(R.id.updateItemName);
        count_input = findViewById(R.id.updateItemCount);
        update_button = findViewById(R.id.updateButton);
        delete_button = findViewById(R.id.deleteButton);
        getAndSetIntentData();

        // Set action bar title
        ActionBar ab = getSupportActionBar();
        ab.setTitle(name);
        // update button listener
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // init DB
                DBHelper DB = new DBHelper(UpdateItemActivity.this);
                // Update data based on the user, new name and count
                DB.updateData(username, name, name_input.getText().toString(), count_input.getText().toString());
                // Return to main inventory screen
                Intent intent = new Intent(getApplicationContext(), InventoryActivity.class);
                startActivity(intent);
            }
        });
        // delete button listener, sends to confirm delete dialog
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });

    }
    // required to show updated data
    void getAndSetIntentData() {
        if (getIntent().hasExtra("name") && getIntent().hasExtra("count")) {
            // Getting data from Intent
            name = getIntent().getStringExtra("name");
            count = getIntent().getStringExtra("count");

            // Setting Intent Data
            name_input.setText(name);
            count_input.setText(count);
        }
        else {
            Toast.makeText(this, "No data available.", Toast.LENGTH_SHORT).show();
        }
    }
    // Asks the user if they are sure they want to delete, deletes the item if so
    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + name + "?");
        builder.setMessage("Item deletion is irreversible.");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DBHelper DB = new DBHelper(UpdateItemActivity.this);
                DB.deleteOneRow(username, name);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
}