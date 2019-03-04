package com.example.smartlabour01;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class L_SignUp extends AppCompatActivity {
private EditText editTextCode;
private FirebaseAuth mAuth;
    private String verificationId;
    private TextView Signin;
  private   ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_signup);


      Signin =   findViewById(R.id.tvlaboursignin);
      Signin.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent(getApplicationContext(),L_SignIn.class);
              startActivity(intent);
              finish();

          }
      });
    }
}
