package com.example.practicaltest01var06;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class PracticalTest01Var06Service extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        int sum = intent.getIntExtra("sum", 0);

        ProcessingThread processingThread = new ProcessingThread(this, sum);
        processingThread.start();

        return Service.START_REDELIVER_INTENT;
    }


}
