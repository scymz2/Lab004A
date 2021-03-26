package com.mdp.lab004a;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getName();

    private CounterService.CounterBinder myService = null;
    private Boolean mIsBound = false;

    private Button button;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            Log.d(TAG, "onServiceConnected: MainActivity");
            myService = (CounterService.CounterBinder) service;
            mIsBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(TAG, "onServiceDisconnected: onServiceDisconnected");
            mIsBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //bindService 会随着activity的销毁失效，这边改成startService就不会了
        this.bindService(new Intent(this, CounterService.class),
                mServiceConnection, Context.BIND_AUTO_CREATE);

        button = findViewById(R.id.count_up);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
        mIsBound = false;
    }

}