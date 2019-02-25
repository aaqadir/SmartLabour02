package com.example.smartlabour01;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class C_LoginActivity extends AppCompatActivity {
private FirebaseAuth mAuth;
private DatabaseReference mDatabase;
private EditText emailid,password;
private Button signin;
private TextView signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_login);

        signup = findViewById(R.id.tvsignin);
        signin = findViewById(R.id.btn_signin);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("ContractorUser");
        emailid = findViewById(R.id.userEmailId);
        password = findViewById(R.id.loginpassword);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButtonClicked();

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(C_LoginActivity.this, C_RegisterActivity.class);
                startActivity(intent);
                finish();       }
        });
    }

    public void loginButtonClicked(){
        String email = emailid.getText().toString().trim();
        String pass = password.getText().toString().trim();
        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass)){
            final ProgressDialog progressDialog = new ProgressDialog(C_LoginActivity.this,R.style.MyAlertDialogStyle);
            progressDialog.setTitle("Login");
            progressDialog.setMessage("Processing...Plz wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressDialog.cancel();
                    if (task.isSuccessful()){
                        checkUserExists();
                    }
                    else {
                        Toast.makeText(C_LoginActivity.this,"Email or Password is Incorrect",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        else {
            Toast.makeText(C_LoginActivity.this,"Enter Both Fields",Toast.LENGTH_LONG).show();
        }
    }
    public void checkUserExists(){
     //   final String user_id = mAuth.getCurrentUser().getUid();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(Objects.requireNonNull(mAuth.getCurrentUser()).getUid())){
                    Intent loginIntent = new Intent(C_LoginActivity.this,C_Main_Activity.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(loginIntent);
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(C_LoginActivity.this,"Session Logged out Successfully", Toast.LENGTH_LONG).show();
            }
        });
    }

}
