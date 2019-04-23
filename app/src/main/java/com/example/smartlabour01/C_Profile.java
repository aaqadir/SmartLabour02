package com.example.smartlabour01;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class C_Profile extends AppCompatActivity {

    private TextView gender,experience,phone,location,name,email;
    private DatabaseReference mDatabase,databaseReference;
    private FirebaseAuth mAuth;
    private CircleImageView circleImageView;
    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> arrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Profile");
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        listView = findViewById(R.id.c_profile_listView);
        name = findViewById(R.id.tvName);
        email = findViewById(R.id.tv_email);
        gender = findViewById(R.id.tv_gender);
        experience = findViewById(R.id.tv_experience);
        phone = findViewById(R.id.tv_phone);
        location = findViewById(R.id.tv_location);
        circleImageView = findViewById(R.id.ivProfile);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("ContractorUser");
        databaseReference = FirebaseDatabase.getInstance().getReference("ContractorProjectTypes").child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());

        getProfileInfo();
    }

    public void getProfileInfo(){
        mDatabase.child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String Image = (String) dataSnapshot.child("Image").getValue();
                String Name = (String) dataSnapshot.child("Name").getValue();
                String Email = mAuth.getCurrentUser().getEmail();
                String Contact = (String) dataSnapshot.child("Contact").getValue();
                String Gender = (String) dataSnapshot.child("Gender").getValue();
                String Location = (String) dataSnapshot.child("Location").getValue();
                String Experience = (String) dataSnapshot.child("Experience").getValue();
                if(!Objects.requireNonNull(Image).equals("NA")) {
                    Picasso.with(C_Profile.this).load(Image).into(circleImageView);
                }
                name.setText(Name);
                email.setText(Email);
                gender.setText(Gender);
                experience.setText(Experience);
                phone.setText(Contact);
                location.setText(Location);
               }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                        String value = Objects.requireNonNull(snapshot.getValue()).toString();
                        arrayList.add(value);
                    }
            }else {
                    arrayList.add("No Working Project Till Now");
                }
                arrayAdapter = new ArrayAdapter<>(C_Profile.this,R.layout.list_labour_skills, R.id.labour_skills,arrayList);
                listView.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.edit_profile) {
            Intent intent = new Intent(C_Profile.this,C_Edit_Profile.class);
            intent.putExtra("Profile", "pic");
            startActivity(intent);
        }

        if (item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit, menu);
        return true;
    }

}