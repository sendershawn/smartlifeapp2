package com.example.smartlifeapp2;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class temperature extends AppCompatActivity {
    private ImageButton back;
    private Button temperature;
    private Button humidity;
    private Button data;
    private Button healthy;
    private TextView TemperatureDisplay;
    private Boolean isEnable=true;
    private String getdata="20";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);
        hideBottomUIMenu();
        getSupportActionBar().hide();
        iniUI();

        final BlueToothService blueToothService = new BlueToothService();
        getdata=blueToothService.returnTemperature();//取得資料
        new Thread(){
            public void run(){
                while(isEnable==true){

                getdata=blueToothService.returnTemperature();//取得資料
                if (getdata==null){
                    iniUI();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TemperatureDisplay.setText("no data");
                        }
                    });
                }else{
                    iniUI();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TemperatureDisplay.setText(getdata+"℃");
                        }
                    });
                }
                    try {
                        Thread.sleep(600);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();




        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent().setClass(temperature.this,MainActivity.class));
                isEnable=false;
                overridePendingTransition(0, 0);
                finish();
            }
        });
        healthy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent().setClass(temperature.this,healthy.class));
                isEnable=false;
                overridePendingTransition(0, 0);
                finish();
            }
        });
        humidity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent().setClass(temperature.this,humidity.class));
                isEnable=false;
                overridePendingTransition(0, 0);
                finish();
            }
        });
        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent().setClass(temperature.this,data.class));
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
        TemperatureDisplay = (TextView)findViewById(R.id.TemperatureDisplay);
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
