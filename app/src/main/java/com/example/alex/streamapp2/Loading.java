package com.example.alex.streamapp2;

import android.app.Activity;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

public class Loading extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading2);
        final TextView Loading = (TextView) findViewById(R.id.textView3);

        final Handler text1 = new Handler();
        text1.postDelayed(new Runnable() {
            @Override
            public void run() {
                Loading.setText("Loading...");
            }
        }, 1600);

        final Handler mp0lol = new Handler();
        mp0lol.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 3400);
    }

    @Override
    public void onBackPressed() {
        // Avoid Back Button pressed
    }
}
