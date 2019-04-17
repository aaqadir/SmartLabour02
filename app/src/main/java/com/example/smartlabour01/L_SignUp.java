package com.example.smartlabour01;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class L_SignUp extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private EditText name,phone,password,confirmPassword;
    private static final String SHARED_PREF_NAME = "labourPref" ;
    private static final String KEY_NAME = "phoneNumber";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_signup);

        name = findViewById(R.id.labourFullName);
        phone = findViewById(R.id.labourSignUpMobile);
        password = findViewById(R.id.labourSignUpPassword);
        confirmPassword = findViewById(R.id.labourSignUpConfirmPassword);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("LabourUser");
        Button signUp = findViewById(R.id.btn_labour_SignUp);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubmitButtonClicked();
            }
        });

        TextView signin = findViewById(R.id.tvlaboursignin);
      signin.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent(getApplicationContext(),L_SignIn.class);
              startActivity(intent);
              finish();

          }
      });
    }

    public void SubmitButtonClicked(){
        final String Name = name.getText().toString().trim();
        final String Contact = phone.getText().toString().trim();
        String Password = password.getText().toString().trim();
        String ConfirmPassword = confirmPassword.getText().toString().trim();

        if(!TextUtils.isEmpty(Name)&&!TextUtils.isEmpty(Contact)&&!TextUtils.isEmpty(Password) && !TextUtils.isEmpty(ConfirmPassword)){
            final ProgressDialog progressDialog = new ProgressDialog(L_SignUp.this,R.style.MyAlertDialogStyle);
            progressDialog.setTitle("Signup");
            progressDialog.setMessage("Processing...Plz wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            if (Password.equals(ConfirmPassword)) {

                            DatabaseReference current_user_db = mDatabase.child(Contact);
                            current_user_db.child("Name").setValue(Name);
                            current_user_db.child("Contact").setValue(Contact);
                            current_user_db.child("Password").setValue(Password.hashCode());
                            current_user_db.child("Age").setValue("NA");
                current_user_db.child("Experience").setValue("NA");
                current_user_db.child("Gender").setValue("NA");
                current_user_db.child("Image").setValue("NA");
                current_user_db.child("Location").setValue("NA");
                current_user_db.child("Welder").setValue("NA");
                current_user_db.child("Carpenter").setValue("NA");
                current_user_db.child("Electrician").setValue("NA");
                current_user_db.child("Mason").setValue("NA");
                current_user_db.child("Plumber").setValue("NA");
                current_user_db.child("TruckDriver").setValue("NA");
                current_user_db.child("PipeFitter").setValue("NA");
                current_user_db.child("Tradesman").setValue("NA");
                current_user_db.child("CraneOperator").setValue("NA");
                current_user_db.child("Smith").setValue("NA");
                current_user_db.child("MachineOperator").setValue("NA");
                current_user_db.child("Status").setValue("Available");
                current_user_db.child("HiredContractor").child("UID").setValue("NA");
                current_user_db.child("HiredContractor").child("ProjectType").setValue("NA");
                current_user_db.child("HiredContractor").child("WorkType").setValue("NA");
                current_user_db.child("HiredContractor").child("Location").setValue("NA");
                current_user_db.child("HiredContractor").child("StartDate").setValue("NA");
                current_user_db.child("HiredContractor").child("Name").setValue("NA");
                current_user_db.child("HiredContractor").child("Contact").setValue("NA");
                Toast.makeText(L_SignUp.this,"Successful",Toast.LENGTH_LONG).show();
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(KEY_NAME,Contact);
                editor.apply();
                progressDialog.cancel();
                            Intent mainIntent = new Intent(L_SignUp.this, L_Edit_Profile.class);
                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            mainIntent.putExtra("Profile","pic1");
                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(mainIntent);
                            finish();



            }
            else {
                progressDialog.cancel();
                Toast.makeText(L_SignUp.this,"Password is mismatched",Toast.LENGTH_LONG).show();

            }
        }
        else {
            Toast.makeText(L_SignUp.this,"Please fill the required details",Toast.LENGTH_LONG).show();
        }



    }
}
