package com.teachbrowniesindia.shakti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

    public void register(View view) {

        Intent i_register = new Intent(MainActivity.this, Register.class);
        startActivity(i_register);
    }

    public void display_no(View view) {

        Intent i_display = new Intent(MainActivity.this, Display.class);
        startActivity(i_display);

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