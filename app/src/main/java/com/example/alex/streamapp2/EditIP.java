package com.example.alex.streamapp2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditIP extends AppCompatActivity {
    EditText iploc;
    EditText portshh;
    EditText portvid;
    EditText ipsed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_ip2);

        String mString =getIntent().getStringExtra("global");
        String mString1 =getIntent().getStringExtra("local");
        String mint= String.valueOf(getIntent().getExtras().getInt("ssh"));
        String mint1= String.valueOf(getIntent().getExtras().getInt("vid"));

        ipsed = (EditText) findViewById(R.id.editText);
        iploc = (EditText) findViewById(R.id.editText2);
        portshh = (EditText) findViewById(R.id.sshport);
        portvid = (EditText) findViewById(R.id.vidport);
        ipsed.setText(mString);
        iploc.setText(mString1);
        String mints= String.valueOf(mint);
        String mint1s= String.valueOf(mint1);
        portshh.setText(mints);
        portvid.setText(mint1s);
    }

    public void Back(View view) {
        String ipglob;
        String iplocal;
        int pssh;
        int pvid;

        ipglob=ipsed.getText().toString();
        iplocal=iploc.getText().toString();
        pssh=Integer.parseInt(portshh.getText().toString());
        pvid=Integer.parseInt(portvid.getText().toString());


        Intent goingBack = new Intent();
        goingBack.putExtra("ipset", ipglob);
        goingBack.putExtra("iploc", iplocal);
        goingBack.putExtra("portssh", pssh);
        goingBack.putExtra("portvid", pvid);

        setResult(RESULT_OK,goingBack);
        finish();
    }

    public void helptut(View view) {
        Intent tut = new Intent(this,Tutorial.class);
        tut.putExtra("page","two");
        startActivity(tut);
    }
}
