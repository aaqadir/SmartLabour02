package com.example.smartlabour01;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class L_FinishedWorkHistory extends AppCompatActivity {
    private TextView Name,Contact,ProjectType,WorkType,Location,StartDate,EndDate;
    private DatabaseReference mDatabase;
    private static final String SHARED_PREF_NAME = "labourPref";
    private static final String KEY_NAME = "phoneNumber";
    private String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_l__finished_work_history);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Work History");
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        user = sharedPreferences.getString(KEY_NAME,null);

        Name = findViewById(R.id.projectContractorName);
        Contact = findViewById(R.id.projectContractorContact);
        ProjectType =findViewById(R.id.projectType);
        WorkType = findViewById(R.id.projectWorkType);
        Location = findViewById(R.id.projectLocation);
        StartDate = findViewById(R.id.projectStartDate);
        EndDate = findViewById(R.id.projectEndDate);

        String Key = Objects.requireNonNull(getIntent().getExtras()).getString("Key");
        mDatabase = FirebaseDatabase.getInstance().getReference("LabourUser").child(user).child("CompletedHiredContractor").child(Objects.requireNonNull(Key));
            fetch();
    }

    public void fetch(){
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name,contact,projectType,location,workType,startDate,endDate;
                name = Objects.requireNonNull(dataSnapshot.child("Name").getValue()).toString();
                contact =Objects.requireNonNull(dataSnapshot.child("Contact").getValue()).toString();
                projectType = Objects.requireNonNull(dataSnapshot.child("ProjectType").getValue()).toString();
                location = Objects.requireNonNull(dataSnapshot.child("Location").getValue()).toString();
                workType =Objects.requireNonNull(dataSnapshot.child("WorkType").getValue()).toString();
                startDate =Objects.requireNonNull(dataSnapshot.child("StartDate").getValue()).toString();
                endDate =Objects.requireNonNull(dataSnapshot.child("EndDate").getValue()).toString();

                Name.setText(name);
                Contact.setText(contact);
                ProjectType.setText(projectType);
                Location.setText(location);
                WorkType.setText(workType);
                StartDate.setText(startDate);
                EndDate.setText(endDate);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.finish, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.finish_project) {
            //  finishProject(mDatabase,databaseReference,databaseReference1,key);
            deleteProject(mDatabase);
        }


        if (item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    public void deleteProject(DatabaseReference mDatabase){
       mDatabase.removeValue();
        Intent intent = new Intent(getApplicationContext(),L_MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        Toast.makeText(getApplicationContext(),"Project Deleted",Toast.LENGTH_LONG).show();
    }
}
