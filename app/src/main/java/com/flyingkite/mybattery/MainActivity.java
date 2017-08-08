package com.flyingkite.mybattery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.flyingkite.util.Say;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Say.LogF("onCreate %s", savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Say.LogF("onResume");
    }

    private void init() {
        findViewById(R.id.myBattery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Say.LogF("start Service");
                Intent intent = new Intent(MainActivity.this, BatteryService.class);
                startService(intent);
                finish();
            }
        });
        findViewById(R.id.myFinish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Say.LogF("stop Service");
                Intent intent = new Intent(MainActivity.this, BatteryService.class);
                stopService(intent);
                finish();
            }
        });
    }
}
