package com.teachbrowniesindia.shakti;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.verify, menu);
        return true;
    }





    public void register(View view) {

        Intent i_register = new Intent(MainActivity.this, Register.class);
        startActivity(i_register);
    }
// read_phone_state, access fine location
    public void display_no(View view) {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.SEND_SMS)  == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                Intent i_display = new Intent(MainActivity.this, Display.class);
                startActivity(i_display);
            }
            else{
                requestPermissions(new String[] {Manifest.permission.SEND_SMS, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE}, 1);
            }
        }

    }

    public void instruct(View view) {

        Intent i_instruct = new Intent(MainActivity.this, Instructions.class);
        startActivity(i_instruct);
    }

    public void verify(View view) {

        Intent i_verify = new Intent(MainActivity.this, Verify.class);
        startActivity(i_verify);

    }



}