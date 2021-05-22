package com.example.smartlifeapp2;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class data extends AppCompatActivity {
    private ImageButton back;
    private Button temperature;
    private Button humidity;
    private Button data;
    private Button healthy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        hideBottomUIMenu();
        getSupportActionBar().hide();
        iniUI();


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent().setClass(data.this,MainActivity.class));
                overridePendingTransition(0, 0);
                finish();
            }
        });
        temperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent().setClass(data.this,temperature.class));
                overridePendingTransition(0, 0);
                finish();
            }
        });
        humidity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent().setClass(data.this,humidity.class));
                overridePendingTransition(0, 0);
                finish();
            }
        });
        healthy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent().setClass(data.this,healthy.class));
                overridePendingTransition(0, 0);
                finish();
            }
        });
    }
    public Boolean onKeydown(int keyCode, KeyEvent event){
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
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
