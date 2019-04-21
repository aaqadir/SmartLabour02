package com.example.smartlabour01;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class L_WorkHistory extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase,databaseReference;
    private RecyclerView mInstaList;
    private LabourFinishedProjectAdapter adapter;
    private Query query;
    private List<LabourFinishedProject> projectList;
    private static final String SHARED_PREF_NAME = "labourPref" ;
    private static final String KEY_NAME = "phoneNumber";
    private String user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_workhistory);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Work History");
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        mDatabase = FirebaseDatabase.getInstance().getReference("LabourUser");
        mAuth = FirebaseAuth.getInstance();
        mInstaList = findViewById(R.id.workHistoryRecyclerView);
        mInstaList.setHasFixedSize(true);
        mInstaList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        projectList = new ArrayList<>();

        adapter = new LabourFinishedProjectAdapter(this, projectList);

        mInstaList.setAdapter(adapter);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        user = sharedPreferences.getString(KEY_NAME,null);

        if (user!=null){
            WorkHistory();
        }

    }


    public void WorkHistory(){
        query = mDatabase.child(user).child("CompletedHiredContractor");
        query.addListenerForSingleValueEvent(valueEventListener);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            projectList.clear();

            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    LabourFinishedProject project = snapshot.getValue(LabourFinishedProject.class);
                    projectList.add(project);
                }/*
                long c=  adapter.getItemCount();
                inProgress.setText(df.format(c));*/
                adapter.notifyDataSetChanged();

            }else {
                Toast.makeText(getApplicationContext(),"No Completed Projects Found",Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);

    }
}
