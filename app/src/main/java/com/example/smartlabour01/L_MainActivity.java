package com.example.smartlabour01;

import android.content.Intent;
import android.graphics.Color;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.Objects;

public class L_MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
          MaterialSearchView searchview;
private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Home");
        toolbar.setTitleTextColor(Color.parseColor("#212121"));
         searchview = findViewById(R.id.search_view);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser()==null) {
            mAuthListner = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    if (firebaseAuth.getCurrentUser() == null) {
                        Intent loginIntent = new Intent(L_MainActivity.this, L_SignIn.class);
                        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(loginIntent);
                        finish();
                    }
                }
            };
            mAuth.addAuthStateListener(mAuthListner);
        }
        else {
            getCurrentinfo();
        }


    }

    public void getCurrentinfo(){

    }

    public void onBlueBtnClick(View view) {
        LinearLayout background = findViewById(R.id.btnlayout);
        background.setBackgroundColor(Color.WHITE);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item=menu.findItem(R.id.action_search);
        searchview.setMenuItem(item);
        return true;
    }

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
     //       mAuth.signOut();
            startActivity(new Intent(L_MainActivity.this, L_SignIn.class));
            finish();
        } else if (id == R.id.nav_settings) {
            startActivity(new Intent(L_MainActivity.this, L_SignIn.class));

        } else if (id == R.id.nav_help) {
            startActivity(new Intent(L_MainActivity.this, HelpActivity.class));

        } else if (id == R.id.nav_about) {
            startActivity(new Intent(L_MainActivity.this, AboutActivity.class));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
