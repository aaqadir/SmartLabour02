package com.example.smartlabour01;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class L_SignIn extends AppCompatActivity {
private TextView forgetPassword,SignUP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_signin);

        SignUP = findViewById(R.id.tvlaboursignUP);

        SignUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),L_SignUp.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
