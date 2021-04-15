package com.teachbrowniesindia.shakti;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.widget.Toast;

import java.security.Provider;

import androidx.annotation.NonNull;

public class BgService extends Service implements AccelerometerListener {


    String str_address;

    private ServiceHandler mServideHandler;

    private static final class ServiceHandler extends Handler {

        public ServiceHandler(Looper looper){
            super(looper);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
        }
    }

    @Override
    public IBinder onBind(Intent arg0) {

        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if(AccelerometerManager.isSupported(this)){
            AccelerometerManager.startListening(this);
        }
        HandlerThread thread = new HandlerThread("Service Start Argument", Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();

        Looper mServiceLooper = thread.getLooper();
        mServideHandler = new ServiceHandler(mServiceLooper);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Message msg = mServideHandler.obtainMessage();
        msg.arg1 = startId;
        mServideHandler.sendMessage(msg);
        return START_STICKY;

    }


    public class GeocoderHandler extends Handler{

        @Override
        public void handleMessage(@NonNull Message msg) {

            Toast.makeText(getApplicationContext(), "geoCoderHandler Started", Toast.LENGTH_LONG).show();

            switch (msg.what){
                case 1:
                    Bundle bundle = msg.getData();
                    str_address = bundle.getString("address");

                    SQLiteDatabase db;
                    db=openOrCreateDatabase("NUMDB", Context.MODE_PRIVATE, null);
                    Cursor c = db.rawQuery("SELECT * FROM details", null);
                    Cursor c1 = db.rawQuery("SELECT * FROM SOURCE", null);

                    String source_ph_number = c1.getString(0);
                    while(c.moveToNext()){

                        String target_ph_number = c.getString(1);
                        Toast.makeText(BgService.this, "Source : "+source_ph_number+" Target : "+target_ph_number, Toast.LENGTH_SHORT).show();

                    }
                        db.close();
                    break;
                default:
                    str_address = null;
            }
            Toast.makeText(BgService.this, str_address, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onAccelerationChanged(float x, float y, float z) {
        // TODO Auto-generated method stub
    }
    @Override
    public void onShake(float force) {

        GPSTracker gps;
        gps = new GPSTracker(BgService.this);
        if(gps.canGetLocation()){

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            RGeocoder RGeocoder = new RGeocoder();
            RGeocoder.getAddressFromLocation(latitude, longitude,getApplicationContext(), new GeocoderHandler());
            Toast.makeText(getApplicationContext(), "onShake", Toast.LENGTH_SHORT).show();

        }
        else{
            gps.showSettingsAlert();
        }

    }

}
