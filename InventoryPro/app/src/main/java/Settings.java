package com.zybooks.project2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
/*
    The SMS delivery system is very buggy. If permission is granted, it is hard to test if it
    works so uninstalling the app from the emulator is necessary.
 */
public class Settings extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;

    private EditText number, stockLimit;
    static int lim = 0;
    private Switch smsSwitch, stockNotifSwitch;

    static boolean notifOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        number = findViewById(R.id.phoneNumber);
        stockLimit = findViewById(R.id.stockLimit);
        lim = Integer.parseInt(stockLimit.getText().toString());

        smsSwitch = findViewById(R.id.smsSwitch);
        stockNotifSwitch = findViewById(R.id.stockNotifSwitch);

        // If the allow notifications switch is switched on, present user with permission statement
        smsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
                        if (ActivityCompat.shouldShowRequestPermissionRationale(Settings.this, Manifest.permission.SEND_SMS)) {

                        }
                        else {
                            ActivityCompat.requestPermissions(Settings.this, new String[] {Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
                        }
                    }
                    // Settings refreshes, need to debug first before making reoccuring settings changes
                    smsSwitch.setClickable(false);
                }

            }
        });
        // Listener for the notification toggle on to allow SMS message to send
        stockNotifSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    notifOn = true;
                }
                else {
                    notifOn = false;
                }
            }
        });


    }
    // Method for granting SMS permission
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission Granted.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission Denied.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    // VERY BUGGY - allows an sms to send based on inventory levels.
    public void sendSMS(String item) {

        String msg;
        String phoNumber, limit;
        // If phone or stock limit is null, whole app crashes so these
        // if statements are used to prevent that.
        if (number != null)
        {
            phoNumber = number.getText().toString();
        }
        else {
            phoNumber = "0000000000";
        }

        if (stockLimit != null)
        {
            limit = stockLimit.getText().toString();
        }
        else {
            limit = "0";
        }
        // If everything checks out, send SMS -kind of works. Message prints to console but not to phone
        // Using toast text crashes app
        if (notifOn && (phoNumber != null) && (limit != null) && MY_PERMISSIONS_REQUEST_SEND_SMS == 1) {
            msg = item + " is <= " + limit + " in your inventory!";
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoNumber, null, msg, null, null);
            //Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
            System.out.println(msg);
        }


    }
}