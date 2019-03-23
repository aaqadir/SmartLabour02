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

import java.util.ArrayList;
import java.util.Objects;

public class HireIndividualLabour extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private StorageReference mStorageRef;
    private RecyclerView mInstaList;
    private FirebaseAuth.AuthStateListener mAuthListner;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FirebaseRecyclerAdapter adapter;
    private String skill;
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
        mDatabase = FirebaseDatabase.getInstance().getReference().child("LabourUser");
        skill = Objects.requireNonNull(getIntent().getExtras()).getString("Skill");
        fetch();
    }

    private void fetch() {
        //   swipeRefreshLayout.setRefreshing(true);
        String Skill;
        switch (skill) {
            case "Tradesman (Repairing Concrete,Finishing)":
                Skill = "Tradesman";
                break;
            case "Machine Operator (Temporary Lift , Bending Metal Rods)":
                Skill = "Machine Operator";
                break;
            default:
                Skill = skill;
                break;
        }

        Query query;
         query = mDatabase;
         query = query.orderByChild(Skill).equalTo("Yes");
      FirebaseRecyclerOptions<Labour> options = new FirebaseRecyclerOptions.Builder<Labour>().setQuery(query, new SnapshotParser<Labour>() {
            @NonNull
            @Override
            public Labour parseSnapshot(@NonNull DataSnapshot snapshot) {
        //        if (Objects.requireNonNull(snapshot.child("Type1").getValue()).equals(skill))
                    return new Labour(Objects.requireNonNull(snapshot.child("Name").getValue()).toString(),
                            Objects.requireNonNull(snapshot.child("Contact").getValue()).toString(),
                            Objects.requireNonNull(snapshot.child("Experience").getValue()).toString(),
                            Objects.requireNonNull(snapshot.child("Image").getValue()).toString());
                /*} else {
                    return new Labour( "NA","NA","NA","NA","NA");
                }*/
            }
        }).build();
            adapter = new FirebaseRecyclerAdapter<Labour, ViewHolder>(options) {
                @Override
                protected void onBindViewHolder(@NonNull ViewHolder holder, final int position, @NonNull Labour model) {
                    final String post_key = getRef(position).getKey();
                    holder.setName(model.getName());
                    holder.setContact(model.getContact());
                    holder.setExperience(model.getExperience());
                    holder.setImage(getApplicationContext(), model.getImage());
                    // holder.setImage(getApplicationContext(),model.getImage());
                    holder.mView.setOnClickListener(new View.OnClickListener() {
                        @SuppressLint("ShowToast")
                        @Override
                        public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(),C_Hire_Labour.class);
                        intent.putExtra("Skill",skill);
                        intent.putExtra("PostId",post_key);
                        startActivity(intent);
                         //   Toast.makeText(getApplicationContext(), "" + post_key, Toast.LENGTH_LONG).show();
                        }

                    });
                    //swipeRefreshLayout.setRefreshing(false);

                }

                @NonNull
                @Override
                public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                    View view = LayoutInflater.from(viewGroup.getContext())
                            .inflate(R.layout.labour_card, viewGroup, false);
/*
                    mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String key  = dataSnapshot.getKey();
                            Labour labour = dataSnapshot.getValue(Labour.class);
                            if (labour.getType1().equals(skill))
                                adapter.
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });*/
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
         void setName(String Name){
            TextView post_Name = (TextView) mView.findViewById(R.id.labour_card_name);
            post_Name.setText("Name : " + Name);
        }
         void setContact(String Contact){
            TextView post_Contact = (TextView) mView.findViewById(R.id.labour_card_contact);
            post_Contact.setText("Contact : " + Contact);
        }
           void setImage(Context context, String Image) {
               ImageView post_image = (ImageView) mView.findViewById(R.id.labour_card_profile_image);
               Picasso.with(context).load(Image).into(post_image);
           }
         void setExperience(String Experience){
            TextView post_Experience = mView.findViewById(R.id.labour_card_experience);
            post_Experience.setText("Experience : " + Experience +" years");
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
