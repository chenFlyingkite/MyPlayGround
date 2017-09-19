package com.flyingkite.myplayground;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.SystemClock;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.flyingkite.myplayground.battery.BatteryService;
import com.flyingkite.myplayground.page.PagesActivity;
import com.flyingkite.util.Say;

import java.io.File;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutionException;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initAll();
    }


    private void initAll() {

        setContentView(R.layout.activity_main);

        initBattery();
        initRecord();
        String link = "http://i.imgur.com/uQ4lWgP.jpg"; // 0001 Mori
    }

    private void downloadAsFile(final String link) {
        new AsyncTask<Context, Void, Void>() {
            @Override
            protected Void doInBackground(Context... context) {

                try {
                    LogE("tic = " + System.currentTimeMillis());
                    File f = Glide.with(context[0]).load(link).downloadOnly(100, 100).get();
                    LogE("tac = " + System.currentTimeMillis());
                    LogE("f = " + f.getAbsolutePath());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute(MainActivity.this);

    }

    private void initBattery() {
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

    private void initRecord() {
        findViewById(R.id.recordVideo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RecordActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.goPages).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PagesActivity.class));
            }
        });
        findViewById(R.id.goPIP).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    enterPictureInPictureMode();
                } else {
                    Toast.makeText(MainActivity.this, "No PIP :)", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.tester).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK, "Your Tag");
                    wl.acquire(1000);
                } else {
                    Toast.makeText(MainActivity.this, "No PROXIMITY_SCREEN_OFF_WAKE_LOCK :)", Toast.LENGTH_SHORT).show();
                }

            }

            private void reflect(PowerManager pm) {
                try {
                    Method[] methods = pm.getClass().getDeclaredMethods();

                    for (Method m : methods) {
                        Say.LogF(" -> " + m);
                        if ("goToSleep".equals(m.getName())) {
                            Say.LogF(" o -> " + m);
                            m.setAccessible(true);
                            m.invoke(pm, SystemClock.uptimeMillis() - 2);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode);

        Say.LogF("onPictureInPictureModeChanged %s %s", Say.ox(isInPictureInPictureMode), getActionBar());
        if (isInPictureInPictureMode) {
            setContentView(R.layout.activity_main_pip);
        } else {
            initAll();
        }
    }
}
