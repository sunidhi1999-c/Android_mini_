package com.teachbrowniesindia.shakti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.Locale;

public class Display extends AppCompatActivity {

    Cursor c;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // Customize the back button
        assert actionBar != null;
        actionBar.setHomeAsUpIndicator(R.drawable.mybutton);

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        SQLiteDatabase db;

        try{

            db=openOrCreateDatabase("NumDB", Context.MODE_PRIVATE, null);

            c = db.rawQuery("SELECT * FROM details", null);
            if(c.getCount() == 0){

                showMessage("Error", "No records found");
                return;
            }

            StringBuilder buffer = new StringBuilder();
            while(c.moveToNext()){
                buffer.append("Name: ").append(c.getString(0)).append("\n");
                buffer.append("Number: ").append(c.getString(1)).append("\n");
            }
            showMessage("Details", buffer.toString());
            Intent i_startservice = new Intent(Display.this, BgService.class);
            startActivity(i_startservice);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void showMessage(String title, String message) {

        Builder builder = new Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
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