package com.example.practicaltest01var06;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

public class PracticalTest01Var06SecondaryActivity extends AppCompatActivity {

    private int boolToInt(boolean foo) {
        return (foo) ? 1 : 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent data = new Intent();
        Bundle extras = getIntent().getExtras();
        String n1 = extras.getString("n1");
        String n2 = extras.getString("n2");
        String n3 = extras.getString("n3");
        Boolean c1 = extras.getBoolean("c1");
        Boolean c2 = extras.getBoolean("c2");
        Boolean c3 = extras.getBoolean("c3");
        int gained = 0;
        boolean ok = false;
        if(n1.equals("*") || n2.equals("*") || n1.equals(n2))
            if(n1.equals("*") || n3.equals("*") || n1.equals(n3))
                if(n2.equals("*") || n3.equals("*") || n2.equals(n3))
                    ok = true;
        if(ok)
        {
            int count = boolToInt(c1) + boolToInt(c2) + boolToInt(c3);
            if(count == 0)
                gained = 100;
            else if(count == 1)
                gained = 50;
            else if(count == 2)
                gained = 10;
        }
        data.setData(Uri.parse(String.valueOf(gained)));
        setResult(RESULT_OK, data);
        //Toast.makeText(this, "Gained " + String.valueOf(gained), Toast.LENGTH_SHORT).show();
        finish();
    }
}