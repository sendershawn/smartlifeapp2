package com.example.smartlifeapp2;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class watercontrol extends AppCompatActivity {
    private ImageButton back;
    private ImageButton start;
    private ImageButton auto;
    private ImageButton stop;
    private Button temperature;
    private Button humidity;
    private Button data;
    private Button healthy;
    private  Boolean ison=false;
    private Boolean startOnClick=false;
    private Boolean stopOnClick=false;
    private Boolean autoOnClick=false;
    private int nowMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watercontrol);
        hideBottomUIMenu();
        getSupportActionBar().hide();
        iniUI();

        final BlueToothService blueToothService = new BlueToothService();
        ison=blueToothService.BTSisOn;

        Log.d("BTisON",""+ison);
            new Thread() {
                public void run() {
                    while(ison==true){
                        ison=blueToothService.BTSisOn;
                        nowMode=blueToothService.nowMode;
                    switch (nowMode) {//1:stop 2:start 3:auto
                        case 1:
                            startOnClick = false;
                            stopOnClick = true;
                            autoOnClick = false;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    start.setImageDrawable(getResources().getDrawable(R.drawable.start));
                                    stop.setImageDrawable(getResources().getDrawable(R.drawable.stop_click));
                                    auto.setImageDrawable(getResources().getDrawable(R.drawable.auto));
                                }
                            });

                            break;
                        case 2:
                            startOnClick = true;
                            stopOnClick = false;
                            autoOnClick = false;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    start.setImageDrawable(getResources().getDrawable(R.drawable.start_click));
                                    stop.setImageDrawable(getResources().getDrawable(R.drawable.stop));
                                    auto.setImageDrawable(getResources().getDrawable(R.drawable.auto));
                                }
                            });
                            break;
                        case 3:
                            startOnClick = false;
                            stopOnClick = false;
                            autoOnClick = true;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    start.setImageDrawable(getResources().getDrawable(R.drawable.start));
                                    stop.setImageDrawable(getResources().getDrawable(R.drawable.stop));
                                    auto.setImageDrawable(getResources().getDrawable(R.drawable.auto_click));
                                }
                            });
                            break;
                     }
                        try {
                            Thread.sleep(600);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();


        if (ison==true){
            stop.setEnabled(true);
            start.setEnabled(true);
            auto.setEnabled(true);
        }else
        {
            stop.setEnabled(false);
            start.setEnabled(false);
            auto.setEnabled(false);
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent().setClass(watercontrol.this,MainActivity.class));
                overridePendingTransition(0, 0);
                finish();
            }
        });
        temperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent().setClass(watercontrol.this,temperature.class));
                overridePendingTransition(0, 0);
                finish();
            }
        });
        humidity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent().setClass(watercontrol.this,humidity.class));
                overridePendingTransition(0, 0);
                finish();
            }
        });
        healthy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent().setClass(watercontrol.this,healthy.class));
                overridePendingTransition(0, 0);
                finish();
            }
        });
        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent().setClass(watercontrol.this,data.class));
                overridePendingTransition(0, 0);
                finish();
            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(startOnClick==false){
                    BlueToothService.mConnectedThread.write("2");//2:START
                    startOnClick=true;
                    stopOnClick=false;
                    autoOnClick=false;
                    start.setImageDrawable(getResources().getDrawable(R.drawable.start_click));
                    stop.setImageDrawable(getResources().getDrawable(R.drawable.stop));
                    auto.setImageDrawable(getResources().getDrawable(R.drawable.auto));
                    blueToothService.nowMode=2;
                }else{
                    BlueToothService.mConnectedThread.write("1");
                    startOnClick=false;
                    stopOnClick=true;
                    autoOnClick=false;
                    start.setImageDrawable(getResources().getDrawable(R.drawable.start));
                    stop.setImageDrawable(getResources().getDrawable(R.drawable.stop_click));
                    auto.setImageDrawable(getResources().getDrawable(R.drawable.auto));
                    blueToothService.nowMode=1;
                }
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(stopOnClick==false){
                    BlueToothService.mConnectedThread.write("1");
                    startOnClick=false;
                    stopOnClick=true;
                    autoOnClick=false;
                    start.setImageDrawable(getResources().getDrawable(R.drawable.start));
                    stop.setImageDrawable(getResources().getDrawable(R.drawable.stop_click));
                    auto.setImageDrawable(getResources().getDrawable(R.drawable.auto));
                    blueToothService.nowMode=1;
                }else{
                    BlueToothService.mConnectedThread.write("1");
                    stop.setImageDrawable(getResources().getDrawable(R.drawable.stop));
                    stopOnClick=false;
                    Log.d("stop-stop","is normal");               }
            }
        });
        auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(autoOnClick==false){
                    BlueToothService.mConnectedThread.write("3");
                    startOnClick=false;
                    stopOnClick=false;
                    autoOnClick=true;
                    start.setImageDrawable(getResources().getDrawable(R.drawable.start));
                    stop.setImageDrawable(getResources().getDrawable(R.drawable.stop));
                    auto.setImageDrawable(getResources().getDrawable(R.drawable.auto_click));
                    blueToothService.nowMode=3;
                }else{
                    BlueToothService.mConnectedThread.write("1");
                    startOnClick=false;
                    stopOnClick=true;
                    autoOnClick=false;
                    start.setImageDrawable(getResources().getDrawable(R.drawable.start));
                    stop.setImageDrawable(getResources().getDrawable(R.drawable.stop_click));
                    auto.setImageDrawable(getResources().getDrawable(R.drawable.auto));
                    blueToothService.nowMode=1;
                }
            }
        });
    }
    public void iniUI(){
        back = (ImageButton) findViewById(R.id.back);
        stop = (ImageButton) findViewById(R.id.stop);
        start = (ImageButton) findViewById(R.id.start);
        auto = (ImageButton) findViewById(R.id.auto);
        temperature = (Button) findViewById(R.id.temperature);
        healthy= (Button) findViewById(R.id.healthy);
        humidity = (Button) findViewById(R.id.humidity);
        data = (Button) findViewById(R.id.data);
    }
    public Boolean onKeydown(int keyCode, KeyEvent event){
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void hideBottomUIMenu() {
        //隱藏虛擬按鍵，並且全屏
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
