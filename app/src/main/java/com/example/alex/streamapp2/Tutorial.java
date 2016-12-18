package com.example.alex.streamapp2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;

public class Tutorial extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        ImageView image=(ImageView) findViewById(R.id.imagetut);
        Button but=(Button) findViewById(R.id.buttut);
        final CheckBox check=(CheckBox) findViewById(R.id.checkBox);

        SharedPreferences mPrefs = getSharedPreferences("label", 0);
        boolean cachedcheck =mPrefs.getBoolean("tag2",false);

        check.setChecked(cachedcheck);
        String page =getIntent().getStringExtra("page");
        if(page.equals("one")){
            but.setText("Next");
            image.setBackgroundResource(R.drawable.tut1);

            check.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(((CheckBox) view).isChecked()){
                        SharedPreferences mPrefs = getSharedPreferences("label", 0);
                        SharedPreferences.Editor mEditor = mPrefs.edit();
                        mEditor.putBoolean("tag2", true).commit();

                    }
                    else{
                        SharedPreferences mPrefs = getSharedPreferences("label", 0);
                        SharedPreferences.Editor mEditor = mPrefs.edit();
                        mEditor.putBoolean("tag2", false).commit();
                    }
                }
            });

            but.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent tut = new Intent(view.getContext(),Tutorial.class);
                    tut.putExtra("page","two");
                    startActivity(tut);
                    overridePendingTransition(R.anim.enterr,R.anim.exitl);
                    finish();
                }
            });
        }
        else{
            but.setText("Finish");
            image.setBackgroundResource(R.drawable.tut2);

            check.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(((CheckBox) view).isChecked()){
                        SharedPreferences mPrefs = getSharedPreferences("label", 0);
                        SharedPreferences.Editor mEditor = mPrefs.edit();
                        mEditor.putBoolean("tag2", true).commit();

                    }
                    else{
                        SharedPreferences mPrefs = getSharedPreferences("label", 0);
                        SharedPreferences.Editor mEditor = mPrefs.edit();
                        mEditor.putBoolean("tag2", false).commit();
                    }
                }
            });

            but.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
    // Avoid Back Button pressed
    }
}
