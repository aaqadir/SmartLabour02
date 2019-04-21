package com.example.smartlabour01;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HireIndividualLabour extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private StorageReference mStorageRef;
    private RecyclerView mInstaList;
    private FirebaseAuth.AuthStateListener mAuthListner;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LabourAdapter adapter;
    private String skill;
    String Skill;
    private List<Labour1> projectList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hire_individual_labour);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getColor(R.color.black));
        }
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mInstaList = findViewById(R.id.hireIndividualLabor);
        mInstaList.setHasFixedSize(true);
        mInstaList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        projectList = new ArrayList<>();
        skill = Objects.requireNonNull(getIntent().getExtras()).getString("Skill");
        adapter = new LabourAdapter(this, projectList,skill);
        mInstaList.setAdapter(adapter);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("LabourUser");

        fetch();
    }

    private void fetch() {
        //   swipeRefreshLayout.setRefreshing(true);

        switch (skill) {
            case "Truck Driver":
                Skill = "TruckDriver";
                break;
            case "Pipe Fitter":
                Skill = "PipeFitter";
                break;
            case "Crane Operator":
                Skill = "CraneOperator";
                break;
            case "Tradesman (Repairing Concrete,Finishing)":
                Skill = "Tradesman";
                break;
            case "Machine Operator (Temporary Lift , Bending Metal Rods)":
                Skill = "MachineOperator";
                break;
            default:
                Skill = skill;
                break;
        }

        Query query;
        query = mDatabase;

        query.addListenerForSingleValueEvent(valueEventListener);
    }


   @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }


    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            projectList.clear();
            long count = 0;

            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
               // count2++;
                    String Skills = "NA";
                        Labour1 labour1 = snapshot.getValue(Labour1.class);
                       if (Skill.equals("Welder")) {
                           Skills =   Objects.requireNonNull(labour1).Welder;
                       }
                       if (Skill.equals("Carpenter")) {
                           Skills =   Objects.requireNonNull(labour1).Carpenter;
                       }
                       if (Skill.equals("Electrician")){
                           Skills =   Objects.requireNonNull(labour1).Electrician;
                       }
                    if (Skill.equals("Mason")){
                        Skills =   Objects.requireNonNull(labour1).Mason;
                    }
                    if (Skill.equals("Plumber")){
                        Skills =   Objects.requireNonNull(labour1).Plumber;
                    }
                    if (Skill.equals("TruckDriver")){
                        Skills =   Objects.requireNonNull(labour1).TruckDriver;
                    }
                    if (Skill.equals("PipeFitter")){
                        Skills=   Objects.requireNonNull(labour1).PipeFitter;
                    }
                    if (Skill.equals("Tradesman")){
                        Skills =   Objects.requireNonNull(labour1).Tradesman;
                    }
                    if (Skill.equals("CraneOperator")){
                        Skills=   Objects.requireNonNull(labour1).CraneOperator;
                    }
                    if (Skill.equals("Smith")){
                        Skills =   Objects.requireNonNull(labour1).Smith;
                    }
                    if (Skill.equals("MachineOperator")){
                        Skills =   Objects.requireNonNull(labour1).MachineOperator;
                    }
                        if (Objects.requireNonNull(labour1).Status.contains("Available") && Objects.requireNonNull(Skills).contains("Yes")) {
                            count++;
                            projectList.add(labour1);
                        }
                }

           //    String a= String.valueOf(projectList.get(0));
                if (count==0){
                    Toast.makeText(getApplicationContext(),"No Labour Available For Hire With Skill as "+skill,Toast.LENGTH_LONG).show();
                  }

                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };


}
