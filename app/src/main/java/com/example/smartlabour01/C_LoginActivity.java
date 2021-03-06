package com.example.smartlabour01;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class C_LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_login);

        TextView textView = findViewById(R.id.tvsignin);
        Button button = findViewById(R.id.btn_signin);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(C_LoginActivity.this, C_Main_Activity.class);
                startActivity(intent);
                finish();
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(C_LoginActivity.this, C_RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
