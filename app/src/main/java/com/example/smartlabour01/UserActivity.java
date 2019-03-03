package com.example.smartlabour01;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Objects;

public class UserActivity extends AppCompatActivity {
    LinearLayout l1,l2;
    TextView labour,contractor;
    Animation uptodown,downtoup;
    private static final String SHARED_PREF_NAME = "mysharedpref" ;
    private static final String KEY_NAME = "keyname";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getColor(R.color.black));
        }
        labour = findViewById(R.id.labour);
        contractor = findViewById(R.id.contractor);

        l1 = (LinearLayout) findViewById(R.id.l1);
        l2 = (LinearLayout) findViewById(R.id.l2);
        uptodown = AnimationUtils.loadAnimation(this,R.anim.uptodown);
        downtoup = AnimationUtils.loadAnimation(this,R.anim.downtoup);
        l1.setAnimation(uptodown);
        l2.setAnimation(downtoup);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        String user = sharedPreferences.getString(KEY_NAME,null);
       if (user!=null) {
           if (Objects.requireNonNull(user).equals(getString(R.string.labour))) {
               Intent intent = new Intent(UserActivity.this, L_MainActivity.class);
               intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
               intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
               startActivity(intent);
               finish();
           }
           if (Objects.requireNonNull(user).equals(getString(R.string.contractor))) {
               Intent intent = new Intent(UserActivity.this, C_Main_Activity.class);
               intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
               intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
               startActivity(intent);
               finish();
           }
       }else {

           labour.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   labourButtonClicked();
               }
           });
           contractor.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   contractorButtonClicked();
               }
           });
       }
    }

public void labourButtonClicked(){
   String labour = "labour";
   SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
   SharedPreferences.Editor editor = sharedPreferences.edit();
   editor.putString(KEY_NAME,labour);
   editor.apply();
   Intent intent = new Intent(UserActivity.this,L_MainActivity.class);
   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
   startActivity(intent);
   finish();
}

public void contractorButtonClicked(){
    String contractor = "contractor";
    SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString(KEY_NAME,contractor);
    editor.apply();
    Intent intent = new Intent(UserActivity.this,C_Main_Activity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    startActivity(intent);
    finish();
}

}