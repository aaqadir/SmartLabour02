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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class C_RegisterActivity extends AppCompatActivity {

private EditText name,email,phone,password,confirmPassword;
private TextView signin;
private Button signup;
private FirebaseAuth mAuth;
private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_register);

        signin = findViewById(R.id.tvsignup);
        signup   = findViewById(R.id.btn_signup);

        name = findViewById(R.id.fullName);
        email = findViewById(R.id.userEmailId);
        phone = findViewById(R.id.signupmobile);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("ContractorUser");
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubmitButtonClicked();
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(C_RegisterActivity.this, C_LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
        public void SubmitButtonClicked(){
            final String Name = name.getText().toString().trim();
            final String Contact = phone.getText().toString().trim();
            String Email = email.getText().toString().trim();
            String Password = password.getText().toString().trim();
            String ConfirmPassword = confirmPassword.getText().toString().trim();

            if(!TextUtils.isEmpty(Name)&&!TextUtils.isEmpty(Contact) && !TextUtils.isEmpty(Email) && !TextUtils.isEmpty(Password) && !TextUtils.isEmpty(ConfirmPassword)){
                final ProgressDialog progressDialog = new ProgressDialog(C_RegisterActivity.this,R.style.MyAlertDialogStyle);
                progressDialog.setTitle("Signup");
                progressDialog.setMessage("Processing...Plz wait...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                if (Password.equals(ConfirmPassword)) {
                    progressDialog.cancel();
                    mAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                            //    String user_id = mAuth.getCurrentUser().getUid();
                                DatabaseReference current_user_db = mDatabase.child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
                                current_user_db.child("Name").setValue(Name);
                                current_user_db.child("Contact").setValue(Contact);
                                current_user_db.child("Email").setValue(mAuth.getCurrentUser().getEmail());
                                Toast.makeText(C_RegisterActivity.this,"Successful",Toast.LENGTH_LONG).show();
                                Intent mainIntent = new Intent(C_RegisterActivity.this, C_Edit_Profile.class);
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                mainIntent.putExtra("Profile","pic1");
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(mainIntent);
                                finish();
                            }
                        }
                    });
                }
                else {
                    progressDialog.cancel();
                    Toast.makeText(C_RegisterActivity.this,"Password is mismatched",Toast.LENGTH_LONG).show();

                }
            }
            else {
                Toast.makeText(C_RegisterActivity.this,"Please fill the required details",Toast.LENGTH_LONG).show();
            }



        }
}
