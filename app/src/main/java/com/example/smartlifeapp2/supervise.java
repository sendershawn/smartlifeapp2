package com.example.smartlifeapp2;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.VideoView;

public class supervise extends AppCompatActivity {
    private ImageButton back;
    private Button temperature;
    private Button humidity;
    private Button data;
    private Button healthy;
    private VideoView mVideoView;
    private MediaController mediaController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supervise);
        hideBottomUIMenu();
        getSupportActionBar().hide();
        iniUI();

        mediaController = new MediaController(this);
        String _filePath = "android.resource://"+getPackageName()+"/"+ R.raw.video;
        Log.d("videoAddress",_filePath);
        mVideoView.setVideoURI(Uri.parse(_filePath));
        mVideoView.setMediaController(mediaController);

        mVideoView.start();
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
                //hideBottomUIMenu();
                mp.setLooping(true);
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent().setClass(supervise.this,MainActivity.class));
                overridePendingTransition(0, 0);
                finish();
            }
        });
        temperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent().setClass(supervise.this,temperature.class));
                overridePendingTransition(0, 0);
                finish();
            }
        });
        humidity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent().setClass(supervise.this,humidity.class));
                overridePendingTransition(0, 0);
                finish();
            }
        });
        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent().setClass(supervise.this,data.class));
                overridePendingTransition(0, 0);
                finish();
            }
        });
        healthy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent().setClass(supervise.this,healthy.class));
                overridePendingTransition(0, 0);
                finish();
            }
        });
    }
    public void iniUI(){
        back = (ImageButton) findViewById(R.id.back);
        temperature = (Button) findViewById(R.id.temperature);
        healthy= (Button) findViewById(R.id.healthy);
        humidity = (Button) findViewById(R.id.humidity);
        data = (Button) findViewById(R.id.data);
        mVideoView=(VideoView)findViewById(R.id.videoView);
    }
    public Boolean onKeydown(int keyCode, KeyEvent event){
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
    protected void hideBottomUIMenu() {
        //?????????????????????????????????
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);

        }
    }
}
