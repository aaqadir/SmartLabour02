package com.example.smartlabour01;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class L_SignIn extends AppCompatActivity {
private TextView forgetPassword,SignUP;
private Button SignIn;
private DatabaseReference mDatabase;
private EditText contact,password;
    private static final String SHARED_PREF_NAME = "labourPref" ;
    private static final String KEY_NAME = "phoneNumber";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_signin);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("LabourUser");
        password = findViewById(R.id.labourLoginPassword);
        contact = findViewById(R.id.labourSignInPhoneNumber);
        SignUP = findViewById(R.id.tvlaboursignUP);
        SignIn = findViewById(R.id.btn_labour_SignIn);
        SignUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),L_SignUp.class);
                startActivity(intent);
                finish();
            }
        });

    SignIn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            signInButtonClicked();
        }
    });

    }
public void signInButtonClicked(){
final String Contact = contact.getText().toString().trim();
final String Password = password.getText().toString().trim();
    if(!TextUtils.isEmpty(Contact) && !TextUtils.isEmpty(Password)){
        final ProgressDialog progressDialog = new ProgressDialog(L_SignIn.this,R.style.MyAlertDialogStyle);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("Processing...Plz wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(Contact).exists()){
                    DataSnapshot db = dataSnapshot.child(Contact);
                   final long passcode = (long) db.child("Password").getValue();
                   long password = Password.hashCode();
                    if (passcode == password){
                        progressDialog.cancel();
                        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(KEY_NAME,Contact);
                        editor.apply();

                        Toast.makeText(L_SignIn.this,"Login Successful",Toast.LENGTH_LONG).show();
                        Intent mainIntent = new Intent(L_SignIn.this, L_MainActivity.class);
                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(mainIntent);
                        finish();

                    }else {
                        progressDialog.cancel();
                        Toast.makeText(L_SignIn.this,"Password Mismatch",Toast.LENGTH_LONG).show();

                    }
                }else {
                    progressDialog.cancel();
                    Toast.makeText(L_SignIn.this,"User Not Found",Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    else {
        Toast.makeText(L_SignIn.this,"Enter Both Fields",Toast.LENGTH_LONG).show();
        }
    }
}
