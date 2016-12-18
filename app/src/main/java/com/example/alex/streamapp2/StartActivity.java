package com.example.alex.streamapp2;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.ByteArrayOutputStream;
import java.util.Properties;


public class StartActivity extends Activity {
    protected boolean pressed;




    protected String ip;
    protected String iplocal;
    protected String ipglobal;
    protected int port;
    protected int portvid;
    TextView ipad;
    Spinner dropdown;
    Vibrator vib;
    int timevib=30;
    int timeviber=300;
    protected String loginame;
    protected String pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_layout2);

        //edit manually login name and password because i have make it default on my application, for future extension i can add them as i add ip port,etc

        loginame="loginame";
        pass="pass";

        SharedPreferences mPrefs = getSharedPreferences("label", 0);
        final String mString = mPrefs.getString("tag", "192.168.1.4");
        final String mString1 = mPrefs.getString("tag15", "192.168.1.5");
        boolean mBoolean =mPrefs.getBoolean("tag1",false);
        boolean tutorial =mPrefs.getBoolean("tag2",false);
        final int mint=mPrefs.getInt("tag3",4000);
        final int mint1=mPrefs.getInt("tag4",9000);

        if(!tutorial){
            Intent tut = new Intent(this,Tutorial.class);
            tut.putExtra("page","one");
            startActivityForResult(tut,2);
        }

        Intent load= new Intent(this,Loading.class);
        startActivityForResult(load,3);

        dropdown = (Spinner)findViewById(R.id.spinner);
        String[] items = new String[]{"45s", "2m", "5m"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        portvid=mint1;
        port=mint;
        pressed=mBoolean;
        ip=mString;
        iplocal=mString1;
        ipglobal=ip;
        vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        ipad=(TextView) findViewById(R.id.textView);
        ipad.setText("IP:"+ip);

        ipad.isClickable();

        ipad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ip.equals(ipglobal))
                {ip=iplocal;}
                else
                {ip=ipglobal;}
                ipad.setText("IP:"+ip);
            }
        });

        ipad.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent edit=new Intent(v.getContext(),EditIP.class);
                edit.putExtra("global",mString);
                edit.putExtra("local",mString1);
                edit.putExtra("ssh",mint);
                edit.putExtra("vid",mint1);
                startActivityForResult(edit,1);
                return true;
            }
        });

        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (mWifi.isConnected()) {
            callDiaolog();
        }


    }

    private void callDiaolog() {
        final Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.dialog);
        dialog.setTitle("Wifi is Connected");
        dialog.show();



        Button okb =(Button) dialog.findViewById(R.id.dialog_ok);
        Button canb =(Button) dialog.findViewById(R.id.dialog_cancel);


        okb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ip=iplocal;
                ipad.setText("IP:"+ip);
                dialog.hide();
            }
        });

        canb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.hide();
            }
        });

    }

    public void OpenStream(View view) {
        vib.vibrate(timevib);
        Intent pame=new Intent(this,VideoStreamActivity.class);
        pame.putExtra("address",ip);
        pame.putExtra("portvid",portvid);
        startActivity(pame);
    }

    public void StartStream(View view) {
    if(!pressed) {
        vib.vibrate(timevib);
        pressed=true;
        new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... params) {
                try {
                    executeRemoteCommand(loginame, pass, ip, port, "./rpicam/stream.sh");

                } catch (Exception e) {
                    e.printStackTrace();

                }
                return null;
            }
        }.execute(1);
    }
        else
        {
        Toast.makeText(this, "ONLY ONE COMMAND", Toast.LENGTH_SHORT).show();
        vib.vibrate(timeviber);
        }

    }

    public void CancelStop(View view) {
        pressed=false;
        vib.vibrate(timevib);
        new AsyncTask<Integer, Void, Void>() {
                @Override
                protected Void doInBackground(Integer... params) {
                    try {
                        executeRemoteCommand(loginame, pass, ip, port, "./rpicam/killer.sh");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            }.execute(1);

        }


    public void StartSYSTEM(View view) {
        if (!pressed) {
            vib.vibrate(timevib);
            pressed = true;
            new AsyncTask<Integer, Void, Void>() {
                @Override
                protected Void doInBackground(Integer... params) {
                    try {
                        executeRemoteCommand(loginame, pass, ip, port, "python rpicam/motion_detect.py");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            }.execute(1);
        }
        else
        {
            Toast.makeText(this, "ONLY ONE COMMAND", Toast.LENGTH_SHORT).show();
            vib.vibrate(timeviber);
        }
    }


    public void Exit(View view) {
        vib.vibrate(timevib);
        finish();
    }

    public void dropbox(View view) {
        //try/catch needed if dropbox not installed on app
        vib.vibrate(timevib);
        Intent drop=getPackageManager().getLaunchIntentForPackage("com.dropbox.android");
        startActivity(drop);
    }


    public void RecordSaveCloud(View view) {
        if (!pressed) {
            vib.vibrate(timevib);
            pressed = true;
            final String time= (String) dropdown.getSelectedItem();
            new AsyncTask<Integer, Void, Void>() {
                @Override
                protected Void doInBackground(Integer... params) {
                    try {
                        executeRemoteCommand(loginame, pass, ip, port, "python rpicam/record_cloud"+ time +".py");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            }.execute(1);
        }
        else
        {
            Toast.makeText(this, "ONLY ONE COMMAND", Toast.LENGTH_SHORT).show();
            vib.vibrate(timeviber);
        }
    }

    public void StreamSaveLocal(View view) {
        if (!pressed) {
            vib.vibrate(timevib);
            pressed = true;
            new AsyncTask<Integer, Void, Void>() {
                @Override
                protected Void doInBackground(Integer... params) {
                    try {
                        executeRemoteCommand(loginame, pass, ip, port, " ./rpicam/stream_save_local.sh");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            }.execute(1);
        }
        else
        {
            Toast.makeText(this, "ONLY ONE COMMAND", Toast.LENGTH_SHORT).show();
            vib.vibrate(timeviber);
        }
    }

    public void SudoReboot(View view) {
        pressed=false;
        vib.vibrate(timevib);
        new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... params) {
                try {
                    executeRemoteCommand(loginame, pass,ip, port, "sudo reboot");

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute(1);
    }

    public static String executeRemoteCommand(String username,String password,String hostname,int port,String command)
            throws Exception {
        JSch jsch = new JSch();
        Session session = jsch.getSession(username, hostname, port);
        session.setPassword(password);

        // Avoid asking for key confirmation
        Properties prop = new Properties();
        prop.put("StrictHostKeyChecking", "no");
        session.setConfig(prop);

        session.connect();

        // SSH Channel
        ChannelExec channelssh = (ChannelExec)
                session.openChannel("exec");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        channelssh.setOutputStream(baos);

        // Execute command
        channelssh.setCommand(command);
        channelssh.connect();
        channelssh.disconnect();

        return baos.toString();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String resultip=data.getStringExtra("ipset");
                String resultiploc=data.getStringExtra("iploc");
                int resultpssh=data.getExtras().getInt("portssh");
                int resultpvid=data.getExtras().getInt("portvid");

                ipglobal=resultip;
                iplocal=resultiploc;
                portvid=resultpvid;
                port=resultpssh;

                ip=ipglobal;
                ipad.setText("IP:"+ip);

                SharedPreferences mPrefs = getSharedPreferences("label", 0);
                SharedPreferences.Editor mEditor = mPrefs.edit();
                mEditor.putString("tag", ip).commit();
                mEditor.putString("tag15", iplocal).commit();
                mEditor.putInt("tag3", port).commit();
                mEditor.putInt("tag4", portvid).commit();

            }

            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "No IP/Port Entered", Toast.LENGTH_SHORT).show();
            }


        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences mPrefs = getSharedPreferences("label", 0);
        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putBoolean("tag1", pressed).commit();
    }


    public void helptut(View view) {
        Intent tut = new Intent(this,Tutorial.class);
        tut.putExtra("page","one");
        startActivity(tut);
    }

    public void RpiCom(View view) {
        Uri uri = Uri.parse("https://www.raspberrypi.org/");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}

