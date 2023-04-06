package com.example.practicaltest01var06;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class PracticalTest01Var06MainActivity extends AppCompatActivity {

    private static String getRandom(String[] myArray) {
        Random generator = new Random();
        int randomIndex = generator.nextInt(myArray.length);
        return myArray[randomIndex];
    }

    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, intent.getStringExtra("msgContent"), Toast.LENGTH_SHORT).show();
            Log.d("RECEIVER", intent.getStringExtra("msgContent"));
        }
    }
    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private IntentFilter intentFilter = new IntentFilter();
    Button playButton;
    EditText firstNumber;
    EditText secondNumber;
    EditText thirdNumber;
    CheckBox box1;
    CheckBox box2;
    CheckBox box3;
    Integer score;
    Integer lastScore;

    boolean serviceStarted;


    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(messageBroadcastReceiver);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var06_main);

        score = 0;
        serviceStarted = false;
        playButton = findViewById(R.id.play);
        firstNumber = findViewById(R.id.numar1);
        secondNumber = findViewById(R.id.numar2);
        thirdNumber = findViewById(R.id.numar3);
        box1 = findViewById(R.id.hold1);
        box2 = findViewById(R.id.hold2);
        box3 = findViewById(R.id.hold3);
        intentFilter.addAction("BroadcastSum");

        playButton.setOnClickListener(it -> {
            String[] aux = {"1", "2", "3", "*"};
            if(!box1.isChecked())
                firstNumber.setText(getRandom(aux));
            if(!box2.isChecked())
                secondNumber.setText(getRandom(aux));
            if(!box3.isChecked())
                thirdNumber.setText(getRandom(aux));
            String result = String.valueOf(firstNumber.getText()) +
                    String.valueOf(secondNumber.getText()) + String.valueOf(thirdNumber.getText());
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getApplicationContext(), PracticalTest01Var06SecondaryActivity.class);
            intent.putExtra("n1", String.valueOf(firstNumber.getText()));
            intent.putExtra("n2", String.valueOf(secondNumber.getText()));
            intent.putExtra("n3", String.valueOf(thirdNumber.getText()));
            intent.putExtra("c1", box1.isChecked());
            intent.putExtra("c2", box2.isChecked());
            intent.putExtra("c3", box3.isChecked());

            startActivityForResult(intent, 1);
        });

    }


    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                score += Integer.parseInt(data.getData().toString());
                Toast.makeText(this, String.valueOf(score), Toast.LENGTH_SHORT).show();
                if(!serviceStarted && score > 100)
                {
                    serviceStarted = true;
                    Intent intent = new Intent(getApplicationContext(), PracticalTest01Var06Service.class);
                    intent.putExtra("sum", score);
                    getApplicationContext().startService(intent);
                }

            }
            else
            {
                Toast.makeText(this, "Eroare invocare activitate", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("score", score);
        outState.putBoolean("serviceStarted", serviceStarted);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        score = savedInstanceState.getInt("score");
        serviceStarted = savedInstanceState.getBoolean("serviceStarted");
        Toast.makeText(this, "score after restore " + score, Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(getApplicationContext(), PracticalTest01Var06Service.class);
        getApplicationContext().stopService(intent);
    }
}