package com.example.smartlabour01;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class C_WorkHistory extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase,databaseReference;
    private RecyclerView mInstaList;
    private FinishedProjectAdapter adapter;
    private Query query;
    private List<FinishedProject> projectList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_work_history);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Project History");
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mInstaList = findViewById(R.id.contractorFinishedProjectDetails);
        mInstaList.setHasFixedSize(true);
        mInstaList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        projectList = new ArrayList<>();

        adapter = new FinishedProjectAdapter(this, projectList);

        mInstaList.setAdapter(adapter);

        fetch();

    }

    public void fetch(){
        query = mDatabase.child("ContractorFinishedProjects").child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
        query.addListenerForSingleValueEvent(valueEventListener);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            projectList.clear();
            DecimalFormat df = new DecimalFormat("00");
            long count =dataSnapshot.getChildrenCount();
            // inProgress.setText(df.format(count));

            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    FinishedProject project = snapshot.getValue(FinishedProject.class);

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
