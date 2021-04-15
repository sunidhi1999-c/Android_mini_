package com.teachbrowniesindia.shakti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    EditText name, number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // Customize the back button
        assert actionBar != null;
        actionBar.setHomeAsUpIndicator(R.drawable.mybutton);

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    public void display(View view) {

        Intent i_view = new Intent(Register.this, Display.class);
        startActivity(i_view);

    }

    public void instructions(View view) {

        Intent i_instruct = new Intent(Register.this, Instructions.class);
        startActivity(i_instruct);

    }

    public void storeInDB(View v) {

        Toast.makeText(this, "Saving the details", Toast.LENGTH_SHORT).show();
        name = findViewById(R.id.editText1);
        number = findViewById(R.id.editText2);
        String str_name = name.getText().toString();
        String str_number = number.getText().toString();

        SQLiteDatabase db = openOrCreateDatabase("NumDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS details(name VARCHAR, number VARCHAR);");

        try (Cursor c = db.rawQuery("SELECT * FROM details", null)) {
            if (c.getCount() < 2) {

                db.execSQL("INSERT INTO details VALUES('" + str_name + "','" + str_number + "');");
                Toast.makeText(getApplicationContext(), "Successfully Saved", Toast.LENGTH_SHORT).show();
            } else {

                db.execSQL("INSERT INTO details VALUES('" + str_name + "','" + str_number + "');");
                Toast.makeText(getApplicationContext(), "Maximun Numbers limit reached. Previous numbers are replaced.", Toast.LENGTH_SHORT).show();
                }
        }
        db.close();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}