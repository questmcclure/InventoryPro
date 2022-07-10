package com.zybooks.project2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;
/*
    This class is the bread and butter of the SQLite database. It is instantiated as an object
    to allow basic CRUD functionality for the inventory as well as the login/registration system.
 */
public class DBHelper extends SQLiteOpenHelper{

    public static final String DBNAME = "Login.db";

    private Context context;

    public DBHelper(@Nullable Context context) {
        super(context, "Login.db", null, 1);
        this.context = context;
    }

    // executes database table with username - password - user_id - item name - item count
    // item name is used for displaying the user only their inventory items
    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table users(username TEXT primary key, password TEXT, user_id TEXT, item_name TEXT, item_count INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists users");
        onCreate(MyDB);
    }
    // Created new user
    public Boolean insertData(String username, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        long result = MyDB.insert("users", null, contentValues);
        if (result == -1) return false;
        else
            return true;
    }
    // Bool method for returning if the user is present in the database
    public Boolean checkusername(String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ?", new String[] {username});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }
    // Bool method for returning if the user's password is correct
    public Boolean checkusernamepassword(String username, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ? and password = ?", new String[] {username,password});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }
    // Adds an item into the user's inventory. Currently only allows item names and their count
    public void addItem(String username, String name, int count) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("user_id", username);
        cv.put("item_name", name);
        cv.put("item_count", count);

        long result = MyDB.insert("users", null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Item Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }
    // Cursor method for returning the cursor for the InventoryActivity class to display through an ArrayList
    Cursor readAllData(String username) {

        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("Select item_name, item_count from users WHERE user_id = ?", new String[] {username});

        if (cursor.getCount() > 0) {
            return cursor;
        }
        return null;
    }
    // Updates an item in the database
    void updateData(String username, String oldName, String name, String count) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("item_name", name);
        cv.put("item_count",count);

        long result = MyDB.update("users", cv, "user_id = ? AND item_name = ?", new String[] {username, oldName});
        if (result == -1) {
            Toast.makeText(context, "Failed to Update...", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Successfully Updated!", Toast.LENGTH_SHORT).show();
        }
    }
    // Deletes a selected row from the database.
    void deleteOneRow(String username, String name) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        long result = MyDB.delete("users", "user_id = ? AND item_name = ?", new String[] {username, name});
        if (result == -1) {
            Toast.makeText(context, "Failed to Delete...", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Successfully Deleted!", Toast.LENGTH_SHORT).show();
        }
    }
}
