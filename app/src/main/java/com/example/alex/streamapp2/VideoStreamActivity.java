package com.example.alex.streamapp2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoStreamActivity extends Activity {

    ProgressDialog pDialog;
    VideoView videoview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_stream_layout);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        if(Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);}

        String ip =getIntent().getStringExtra("address");
        String portvid= String.valueOf(getIntent().getExtras().getInt("portvid"));

        ip="http://"+ip+":"+portvid;

        videoview = (VideoView) findViewById(R.id.streamview);

        // Create a progressbar
        pDialog = new ProgressDialog(this);
        pDialog.setTitle("Video Streaming");
        pDialog.setMessage("Buffering...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();

        try {
            // Start the MediaController
            MediaController mediaController = new MediaController(this);
            mediaController.setAnchorView(videoview);
            // Get the URL from String VideoURL
            Uri video = Uri.parse(ip);
            videoview.setMediaController(mediaController);
            videoview.setVideoURI(video);

        } catch (Exception e) {
            // TODO: handle exception
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        videoview.requestFocus();
        videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            // Close the progress bar and play the video
            @Override
            public void onPrepared(MediaPlayer mp) {
                // TODO Auto-generated method stub
                pDialog.dismiss();
                videoview.start();
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        videoview.stopPlayback();
    }

}
