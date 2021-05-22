package com.example.smartlifeapp2;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class currentwater extends AppCompatActivity {
    private ImageButton back;
    private Button temperature;
    private Button humidity;
    private Button data;
    private Button healthy;
    private ImageView waterZero;
    private ImageView waterOne;
    private ImageView LowWater;
    private ImageView HighWater;
    private Button  test;
    private static String waternow="";
    private Boolean isEnable=true;
    private ImageButton start;
    private  Boolean ison=false;
    private Boolean startOnClick=false;
    private int f=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currentwater);
        hideBottomUIMenu();
        getSupportActionBar().hide();
        iniUI();

        isEnable=true;
        final BlueToothService blueToothService = new BlueToothService ();
        ison=blueToothService.BTSisOn;
        if (ison==true){
            start.setEnabled(true);
        }else
        {
            start.setEnabled(false);
        }



        new Thread(){
              public void run(){
                  while(isEnable==true){
                      waternow=blueToothService.returnWater();
                      f=Integer.parseInt(waternow);
                  if (f==1){
                      iniUI();
                      runOnUiThread(new Runnable() {
                          @Override
                          public void run() {
                              start.setImageDrawable(getResources().getDrawable(R.drawable.stop));
                              waterOne.setVisibility(View.VISIBLE);
                              waterZero.setVisibility(View.GONE);
                              HighWater.setVisibility(View.VISIBLE);
                              LowWater.setVisibility(View.GONE);
                              start.setVisibility(View.VISIBLE);
                          }
                      });
                      Log.d("current water","now high water");
                  }else if(f==0){
                      iniUI();
                      runOnUiThread(new Runnable() {
                          @Override
                          public void run() {
                              start.setImageDrawable(getResources().getDrawable(R.drawable.start));
                              waterOne.setVisibility(View.GONE);
                              waterZero.setVisibility(View.VISIBLE);
                              HighWater.setVisibility(View.GONE);
                              LowWater.setVisibility(View.VISIBLE);
                              start.setVisibility(View.VISIBLE);
                          }
                      });
                      Log.d("current water","now low water");}
                      try {
                          Thread.sleep(600);
                      } catch (InterruptedException e) {
                          e.printStackTrace();
                      }
                  }
              }
        }.start();





        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (f==1){
                    Log.d("current water","now high water");
                    f=0;
                }else if(f==0){
                    Log.d("current water","now low water");
                    f=1;
                }
            }
        });


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (f==0) {
                    start.setImageDrawable(getResources().getDrawable(R.drawable.start));
                    if (startOnClick == false) {
                        BlueToothService.mConnectedThread.write("3");
                        startOnClick = true;
                        start.setImageDrawable(getResources().getDrawable(R.drawable.start_click));
                        blueToothService.nowMode = 3;

                    } else {
                        BlueToothService.mConnectedThread.write("1");
                        start.setImageDrawable(getResources().getDrawable(R.drawable.start));
                        startOnClick = false;
                        blueToothService.nowMode = 1;
                    }
                }else if(f==1){
                    start.setImageDrawable(getResources().getDrawable(R.drawable.stop));
                    if (startOnClick == false) {
                        BlueToothService.mConnectedThread.write("1");
                        startOnClick = true;
                        start.setImageDrawable(getResources().getDrawable(R.drawable.stop_click));
                        blueToothService.nowMode = 1;

                    } else {
                        BlueToothService.mConnectedThread.write("1");
                        start.setImageDrawable(getResources().getDrawable(R.drawable.stop));
                        startOnClick = false;
                        blueToothService.nowMode = 1;
                    }
                }

            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent().setClass(currentwater.this,MainActivity.class));
                isEnable=false;
                overridePendingTransition(0, 0);
                finish();
            }
        });
        temperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent().setClass(currentwater.this,temperature.class));
                isEnable=false;
                overridePendingTransition(0, 0);
                finish();
            }
        });
        humidity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent().setClass(currentwater.this,humidity.class));
                isEnable=false;
                overridePendingTransition(0, 0);
                finish();
            }
        });
        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent().setClass(currentwater.this,data.class));
                isEnable=false;
                overridePendingTransition(0, 0);
                finish();
            }
        });
        healthy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent().setClass(currentwater.this,healthy.class));
                isEnable=false;
                overridePendingTransition(0, 0);
                finish();
            }
        });

    }
    public Boolean onKeydown(int keyCode, KeyEvent event){
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            isEnable=false;
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
    public void iniUI(){
        back = (ImageButton) findViewById(R.id.back);
        temperature = (Button) findViewById(R.id.temperature);
        healthy= (Button) findViewById(R.id.healthy);
        humidity = (Button) findViewById(R.id.humidity);
        data = (Button) findViewById(R.id.data);
        waterOne = (ImageView)findViewById(R.id.waterOne);
        waterZero = (ImageView)findViewById(R.id.waterZero);
        test = (Button)findViewById(R.id.test);
        LowWater = (ImageView)findViewById(R.id.LowWater);
        HighWater = (ImageView)findViewById(R.id.HighWater);
        start = (ImageButton)findViewById(R.id.start);
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
