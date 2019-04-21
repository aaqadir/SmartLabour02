package com.example.smartlabour01;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class L_MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
       //   MaterialSearchView searchview;
    private static final String SHARED_PREF_NAME = "labourPref" ;
    private static final String KEY_NAME = "phoneNumber";
    private DatabaseReference mDatabase,databaseReference;
    private TextView name,phone,status,completed,projectType,workType,ContractorName,projectLocation,contractorPhone,projectStartDate;
    private CircleImageView profileimage;
    private String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Home");
        toolbar.setTitleTextColor(Color.parseColor("#212121"));
     //    searchview = findViewById(R.id.search_view);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        name = navigationView.getHeaderView(0).findViewById(R.id.labourUserNameDisplay);
        profileimage = navigationView.getHeaderView(0).findViewById(R.id.labourProfileImage);
        phone = navigationView.getHeaderView(0).findViewById(R.id.labourUserContactDisplay);
        status = findViewById(R.id.labour_dashboard_Status);
        completed = findViewById(R.id.labour_project_completed);
        projectType = findViewById(R.id.inProgressProjectType);
        workType = findViewById(R.id.inProgressWorkType);
        ContractorName = findViewById(R.id.inProgressContractorName);
        projectLocation = findViewById(R.id.inProgressProjectLocation);
        contractorPhone = findViewById(R.id.inProgressContractorPhone);
        projectStartDate = findViewById(R.id.inProgressProjectStartDate);

       /* final TextView textView2 = findViewById(R.id.textView6);
        Switch sw = (Switch) findViewById(R.id.switch1);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    textView2.setText("Engaged");
                } else {
                    // The toggle is disabled
                    textView2.setText("Available");
                }
            }
        });*/

         mDatabase = FirebaseDatabase.getInstance().getReference().child("LabourUser");
         databaseReference = FirebaseDatabase.getInstance().getReference("ContractorUser");

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        user = sharedPreferences.getString(KEY_NAME,null);
        if (user==null) {
                Intent intent = new Intent(L_MainActivity.this, L_SignIn.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
        }else {
            getCurrentInfo();

        }


    }

    public void getCurrentInfo(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mDatabase.child(Objects.requireNonNull(user)).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                     final String contractorName = (String) dataSnapshot.child("Name").getValue();
                    name.setText(contractorName);
                    String contractorImage = (String) dataSnapshot.child("Image").getValue();
                    Picasso.with(L_MainActivity.this).load(contractorImage).into(profileimage);
                    String Contact = (String) dataSnapshot.child("Contact").getValue();
                    phone.setText(Contact);
                    String Status = (String) dataSnapshot.child("Status").getValue();
                    status.setText(Status);
                    String ContractorUID = (String) dataSnapshot.child("HiredContractor").child("UID").getValue();
                    String ProjectType = (String) dataSnapshot.child("HiredContractor").child("ProjectType").getValue();
                    String WorkType = (String) dataSnapshot.child("HiredContractor").child("WorkType").getValue();
                    String Location = (String) dataSnapshot.child("HiredContractor").child("Location").getValue();
                    String StartDate = (String) dataSnapshot.child("HiredContractor").child("StartDate").getValue();
                    String Contractor_Name = (String) dataSnapshot.child("HiredContractor").child("Name").getValue();
                    String Contractor_Contact = (String) dataSnapshot.child("HiredContractor").child("Contact").getValue();
                    if (!Objects.requireNonNull(ContractorUID).equals("NA")) {
                        projectType.setText(ProjectType);
                        workType.setText(WorkType);
                        projectLocation.setText(Location);
                        projectStartDate.setText(StartDate);
                        ContractorName.setText(Contractor_Name);
                        contractorPhone.setText(Contractor_Contact);
                    }else {
                        projectType.setText("-");
                        workType.setText("-");
                        projectLocation.setText("-");
                        projectStartDate.setText("-");
                        ContractorName.setText("-");
                        contractorPhone.setText("-");
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            mDatabase.child(Objects.requireNonNull(user)).child("CompletedHiredContractor").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){
                        DecimalFormat df = new DecimalFormat("00");
                       long count =  dataSnapshot.getChildrenCount();
                       completed.setText(df.format(count));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item=menu.findItem(R.id.action_search);
        searchview.setMenuItem(item);
        return true;
    }*/

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_dashboard) {
            startActivity(new Intent(L_MainActivity.this, L_Profile.class));
            // finish();
        }  else if (id == R.id.nav_workhistory) {
            startActivity(new Intent(L_MainActivity.this, L_WorkHistory.class));

        } else if (id == R.id.nav_logout) {
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(KEY_NAME,null);
            editor.apply();
            Intent intent = new Intent(L_MainActivity.this, L_SignIn.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_help) {
            Intent intent = new Intent(getApplicationContext(),HelpActivity.class);
            intent.putExtra("user","labour");
            startActivity(intent);
        } else if (id == R.id.nav_about) {
            startActivity(new Intent(L_MainActivity.this, AboutActivity.class));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
