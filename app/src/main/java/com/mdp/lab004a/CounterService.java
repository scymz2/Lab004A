package com.mdp.lab004a;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class CounterService extends Service {

    private final String TAG = this.getClass().getName();
    private final IBinder mServiceBinder = new CounterBinder();
    private Counter counter = new Counter();


    @Override
    public void onCreate(){
        Log.d(TAG, "Service Create");
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: Binding");
        return mServiceBinder;
    }

    public class CounterBinder extends Binder {
        void countUp(){
            counter.direction = true;
            if(!counter.running){
                //如果线程不存在就创建一个线程
                new Thread(counter).start();
                counter.running = true;
            }
            Log.d(TAG, "countUp: UP!");
        }

        void countDown(){
            Log.d(TAG, "countDown: DOWN!");
            counter.direction = false;
            if(!counter.running){
                //如果线程不存在就创建一个线程
                new Thread(counter).start();
                counter.running = true;
            }
        }

        void countStop(){
            Log.d(TAG, "countDown: STOP!");
            counter.running = false;
        }
    }

    protected class Counter extends Thread implements Runnable{
        private final String TAG = this.getClass().getName();
        boolean direction = true;
        int count = 0;
        boolean running = true;

        public Counter() {
            this.start();
        }
        @Override
        public void run() {
            while (this.running) {
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                    return;
                }
                if (direction) count++;
                else
                    count--;
                Log.d(TAG, String.format("run: Service Counter = %d", count));
            }
            Log.d(TAG, "run: Service Counter Thread is Exiting");
        }

    }
}

