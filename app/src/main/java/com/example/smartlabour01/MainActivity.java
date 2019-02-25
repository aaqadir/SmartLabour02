package com.example.smartlabour01;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //startActivity(new Intent(MainActivity.this,UsersActivity.class));
                Intent intent = new Intent(MainActivity.this,WelcomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();

            }
        };
        Handler handler = new Handler();
        handler.postDelayed(runnable, 1500);

    }
}
