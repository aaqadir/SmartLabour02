package com.example.smartlabour01;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class C_Main_Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase,databaseReference;
    private TextView name,email;
    private TextView inProgress,completed;
    private RecyclerView mInstaList;
//  private FirebaseRecyclerAdapter adapter;
    private CircleImageView profileimage;
    private ProjectAdapter adapter;
    private Query query;
    private List<Project> projectList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Welcome");
        setSupportActionBar(toolbar);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        name = navigationView.getHeaderView(0).findViewById(R.id.contractorUserNameDisplay);
        profileimage = navigationView.getHeaderView(0).findViewById(R.id.contractorProfileImage);
        email = navigationView.getHeaderView(0).findViewById(R.id.contractorUserEmailDisplay);

        inProgress = findViewById(R.id.textViewInProgress);
        completed = findViewById(R.id.textViewCompleted);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("ContractorUser");
        mAuth = FirebaseAuth.getInstance();
        mInstaList = findViewById(R.id.contractorProjectDetails);
        mInstaList.setHasFixedSize(true);
        mInstaList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        projectList = new ArrayList<>();

        adapter = new ProjectAdapter(this, projectList);

        mInstaList.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("ContractorProjects").child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());;
          //  fetch();

        if (mAuth.getCurrentUser() == null) {
            FirebaseAuth.AuthStateListener mAuthListner = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    if (firebaseAuth.getCurrentUser() == null) {
                        Intent loginIntent = new Intent(C_Main_Activity.this, C_LoginActivity.class);
                        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(loginIntent);
                        finish();
                    }
                }
            };
            mAuth.addAuthStateListener(mAuthListner);
        } else {
            getCurrentinfo();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

    }

    @Override
    protected void onStart() {
        super.onStart();
            fetch();
    }

    public void fetch(){
        query = databaseReference;
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

                    Project project = snapshot.getValue(Project.class);

                    projectList.add(project);

                }
              long c=  adapter.getItemCount();
                inProgress.setText(df.format(c));
                adapter.notifyDataSetChanged();

            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };



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
        getMenuInflater().inflate(R.menu.cont_nav_, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(C_Main_Activity.this, C_Add_Projects.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.cont_profile) {
            startActivity(new Intent(C_Main_Activity.this, C_Profile.class));
            // finish();
        } else if (id == R.id.cont_project) {
            startActivity(new Intent(C_Main_Activity.this, C_Hire_Individually.class));

        } else if (id == R.id.cont_hire) {
            startActivity(new Intent(C_Main_Activity.this, C_Hire.class));

        } else if (id == R.id.cont_logout) {
            mAuth.signOut();
            startActivity(new Intent(C_Main_Activity.this, UserActivity.class));
            finish();
        } else if (id == R.id.cont_request) {
            startActivity(new Intent(C_Main_Activity.this, C_Pending_Request_Labour.class));

        } else if (id == R.id.cont_help) {
            startActivity(new Intent(C_Main_Activity.this, HelpActivity.class));

        } else if (id == R.id.cont_about) {
            startActivity(new Intent(C_Main_Activity.this, AboutActivity.class));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void getCurrentinfo(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mDatabase.child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    String contractorName = (String) dataSnapshot.child("Name").getValue();
                    name.setText(contractorName);
                    String contractorImage = (String) dataSnapshot.child("Image").getValue();
                    Picasso.with(C_Main_Activity.this).load(contractorImage).into(profileimage);
                    email.setText(mAuth.getCurrentUser().getEmail());

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
/*

    public void fetch(){
        Query query;
        query = databaseReference;
        FirebaseRecyclerOptions<ContractorProjects> options = new FirebaseRecyclerOptions.Builder<ContractorProjects>().setQuery(query, new SnapshotParser<ContractorProjects>() {
            @NonNull
            @Override
            public ContractorProjects parseSnapshot(@NonNull DataSnapshot snapshot) {
                return new ContractorProjects(Objects.requireNonNull(snapshot.child("ProjectName").getValue()).toString(),
                        Objects.requireNonNull(snapshot.child("ProjectType").getValue()).toString(),
                        Objects.requireNonNull(snapshot.child("ProjectLocation").getValue()).toString(),
                        Objects.requireNonNull(snapshot.child("ProjectStartDate").getValue()).toString());
            }
        }).build();
        adapter = new FirebaseRecyclerAdapter<ContractorProjects, ViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolder holder, final int position, @NonNull ContractorProjects model) {
                final String post_key = getRef(position).getKey();
                holder.setProjectName(model.getProjectName());
                holder.setProjectType(model.getProjectType());
                holder.setProjectLocation(model.getProjectLocation());
                holder.setProjectStartDate(model.getProjectStartDate());
              */
/*  holder.setImage(getApplicationContext(), model.getImage());
                holder.setImage(getApplicationContext(),model.getImage());*//*

                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("ShowToast")
                    @Override
                    public void onClick(View v) {
                        */
/*Intent intent = new Intent(getApplicationContext(),SingleBooksDonationDetails.class);
                        intent.putExtra("PostId",post_key);
                        startActivity(intent);*//*

                        Toast.makeText(getApplicationContext(), "" + post_key, Toast.LENGTH_LONG).show();
                    }

                });
                //swipeRefreshLayout.setRefreshing(false);

            }

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.contractor_project_card, viewGroup, false);
                return new ViewHolder(view);
            }
        };
        mInstaList.setAdapter(adapter);

    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        View mView;
        ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }
        void setProjectName(String projectName){
            TextView post_ProjectName = (TextView) mView.findViewById(R.id.contractorProjectName);
            post_ProjectName.setText(projectName);
        }
        void setProjectType(String projectType){
            TextView post_ProjectType = (TextView) mView.findViewById(R.id.contractorProjectType);
            post_ProjectType.setText(projectType);
        }
        void setProjectLocation(String projectLocation) {
            TextView post_ProjectLocation =  mView.findViewById(R.id.contractorProjectLocation);
            post_ProjectLocation.setText(projectLocation);
        }
        void setProjectStartDate(String projectStartDate){
            TextView post_ProjectStartDate = mView.findViewById(R.id.contractorProjectStartDate);
            post_ProjectStartDate.setText(projectStartDate);
        }


    }


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
*/


}
