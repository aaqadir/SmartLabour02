package com.example.smartlabour01;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
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

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class C_Profile extends AppCompatActivity {

    private TextView gender,experience,phone,location,type1,type2,type3,type4,name,email;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private StorageReference storageReference;
    private CircleImageView circleImageView;
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

        name = findViewById(R.id.tvName);
        email = findViewById(R.id.tv_Email);
        gender = findViewById(R.id.tv_gender);
        experience = findViewById(R.id.tv_experience);
        phone = findViewById(R.id.tv_phone);
        location = findViewById(R.id.tv_location);
        type1 = findViewById(R.id.tv_type1);
        type2 = findViewById(R.id.tv_type2);
        type3 = findViewById(R.id.tv_type3);
        type4 = findViewById(R.id.tv_type4);
        storageReference = FirebaseStorage.getInstance().getReference();
        circleImageView = findViewById(R.id.ivProfile);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("ContractorUser");


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
                String Type1 = (String) dataSnapshot.child("Type1").getValue();
                String Type2 = (String) dataSnapshot.child("Type2").getValue();
                String Type3 = (String) dataSnapshot.child("Type3").getValue();
                String Type4 = (String) dataSnapshot.child("Type4").getValue();
                Picasso.with(C_Profile.this).load(Image).into(circleImageView);
                name.setText(Name);
                email.setText(Email);
                gender.setText(Gender);
                experience.setText(Experience);
                phone.setText(Contact);
                location.setText(Location);
                type1.setText(Type1);
                type2.setText(Type2);
                type3.setText(Type3);
                type4.setText(Type4);
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