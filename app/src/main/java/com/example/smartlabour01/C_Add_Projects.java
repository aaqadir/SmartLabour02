package com.example.smartlabour01;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Objects;

public class C_Add_Projects extends AppCompatActivity {
private EditText projectName,projectType,projectLocation,projectStartDate;
private Button buttonAddProjects;
private DatabaseReference mDatabase,databaseReference;
private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c__add__projects);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Add Project");
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("ContractorProjects").child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
        projectName = findViewById(R.id.c_add_project_name);
        projectType = findViewById(R.id.c_add_project_type);
        projectLocation = findViewById(R.id.c_add_project_location);
        projectStartDate = findViewById(R.id.c_add_project_startdate);
        buttonAddProjects = findViewById(R.id.buttonAddProjects);
        buttonAddProjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addButtonClicked();
            }
        });

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        projectStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(C_Add_Projects.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month+1;
                        String date = day+"/"+month+"/"+year;
                        projectStartDate.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });
        databaseReference = FirebaseDatabase.getInstance().getReference().child("ContractorProjectTypes").child(mAuth.getCurrentUser().getUid());

    }

    public void addButtonClicked(){
        final String ProjectName = projectName.getText().toString().trim();
        final String ProjectType = projectType.getText().toString().trim();
        String ProjectLocation = projectLocation.getText().toString().trim();
        String ProjectStartDate = projectStartDate.getText().toString().trim();
        final ProgressDialog progressDialog = new ProgressDialog(this,R.style.MyAlertDialogStyle);
        progressDialog.setTitle("Add Project");
        progressDialog.setMessage("Processing...Plz wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        if(!TextUtils.isEmpty(ProjectName)&&!TextUtils.isEmpty(ProjectType) && !TextUtils.isEmpty(ProjectLocation) && !TextUtils.isEmpty(ProjectStartDate)){
            progressDialog.cancel();
            DatabaseReference current_user_db = mDatabase.child(ProjectType);
            DatabaseReference db = databaseReference.push();
            db.setValue(ProjectType);
                            current_user_db.child("ProjectName").setValue(ProjectName);
                            current_user_db.child("ProjectType").setValue(ProjectType);
                            current_user_db.child("ProjectLocation").setValue(ProjectLocation);
                            current_user_db.child("ProjectStartDate").setValue(ProjectStartDate);
                            Toast.makeText(this,"Project Added Successfully",Toast.LENGTH_LONG).show();
                            Intent mainIntent = new Intent(this, C_Main_Activity.class);
                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                       //     mainIntent.putExtra("Profile","pic1");
                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(mainIntent);
                            finish();


        }
        else {
            progressDialog.cancel();
            Toast.makeText(this,"Please fill the required details",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);

    }

}
