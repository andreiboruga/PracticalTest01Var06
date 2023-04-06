package com.example.practicaltest01var06;

import android.content.Context;
import android.content.Intent;

import java.util.Date;

public class ProcessingThread extends Thread{

    private final Context context;
    private final int sum;

    public ProcessingThread(Context context, int sum) {
        this.context = context;
        this.sum = sum;
    }

    @Override
    public void run() {
        sleep();
        sendMessage();
    }

    private void sendMessage() {
        Intent intent = new Intent("BroadcastSum");
        intent.putExtra("msgContent",
                new Date(System.currentTimeMillis()) + " " + this.sum);
        context.sendBroadcast(intent);
    }

    private void sleep() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }
}

