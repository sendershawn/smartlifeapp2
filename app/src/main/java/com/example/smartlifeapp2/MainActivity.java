package com.example.smartlifeapp2;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private ImageButton supervise;
    private ImageButton current_water;
    private Button water_control;
    private ImageButton user;
    private ImageButton connect;
    private Button temperature;
    private Button humidity;
    private Button data;
    private Button healthy;
    private  Boolean ison=false;
    private ImageView isConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        //設定隱藏狀態
        hideBottomUIMenu();  //測試手機模式
        iniUI();

        final BlueToothService blueToothService = new BlueToothService ();
        ison=blueToothService.BTSisOn;
        if (ison==true){
            isConnect.setVisibility(View.GONE);
        }else
        {
            isConnect.setVisibility(View.VISIBLE);
        }




        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent().setClass(MainActivity.this,connect.class));
                overridePendingTransition(0, 0);
            }
        });
        supervise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent().setClass(MainActivity.this,supervise.class));
                overridePendingTransition(0, 0);
            }
        });

        current_water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent().setClass(MainActivity.this,currentwater.class));
                overridePendingTransition(0, 0);
            }
        });

        water_control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent().setClass(MainActivity.this,watercontrol.class));
                overridePendingTransition(0, 0);
            }
        });
        temperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent().setClass(MainActivity.this,temperature.class));
                overridePendingTransition(0, 0);
            }
        });
        humidity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent().setClass(MainActivity.this,humidity.class));
                overridePendingTransition(0, 0);
            }
        });
        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent().setClass(MainActivity.this,data.class));
                overridePendingTransition(0, 0);
            }
        });
        healthy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent().setClass(MainActivity.this,healthy.class));
                overridePendingTransition(0, 0);
            }
        });
    }

    public void iniUI(){
        supervise = (ImageButton)findViewById(R.id.supervise);
        current_water = (ImageButton)findViewById(R.id.current_water);
        water_control = (Button)findViewById(R.id.water_control);
        user = (ImageButton)findViewById(R.id.user);
        temperature = (Button) findViewById(R.id.temperature);
        healthy= (Button) findViewById(R.id.healthy);
        humidity = (Button) findViewById(R.id.humidity);
        data = (Button) findViewById(R.id.data);
        connect = (ImageButton)findViewById(R.id.connect);
        isConnect = (ImageView)findViewById(R.id.isconnect);
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
