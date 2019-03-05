package com.example.smartlabour01;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

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

public class L_Profile extends AppCompatActivity {
    private TextView gender, experience, location, type1, type2, type3, type4, age;
    private TextView name, phone;
    private DatabaseReference mDatabase;
    private Uri resultUri;
    private CircleImageView circleImageView;
    private String image;
    private int counter = 0;
    private static final String SHARED_PREF_NAME = "labourPref";
    private static final String KEY_NAME = "phoneNumber";
    private String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Profile");
        setSupportActionBar(toolbar);

        name = findViewById(R.id.labour_profile_Name);
        gender = findViewById(R.id.labour_profile_gender);
        experience = findViewById(R.id.labour_profile_experience);
        phone = findViewById(R.id.labour_profile_Phone);
        age = findViewById(R.id.labour_profile_Age);
        location = findViewById(R.id.labour_profile_location);
        type1 = findViewById(R.id.labour_profile_skill_1);
        type2 = findViewById(R.id.labour_profile_skill_2);
        type3 = findViewById(R.id.labour_profile_skill_3);
        type4 = findViewById(R.id.labour_profile_skill_4);
        circleImageView = findViewById(R.id.labourProfile);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("LabourUser");

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        user = sharedPreferences.getString(KEY_NAME, null);

        getProfileInfo();
}

    public void getProfileInfo(){
        mDatabase.child(Objects.requireNonNull(user)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String Image = (String) dataSnapshot.child("Image").getValue();
                String Name = (String) dataSnapshot.child("Name").getValue();
                String Age = (String) dataSnapshot.child("Age").getValue();
                String Contact = (String) dataSnapshot.child("Contact").getValue();
                String Gender = (String) dataSnapshot.child("Gender").getValue();
                String Location = (String) dataSnapshot.child("Location").getValue();
                String Experience = (String) dataSnapshot.child("Experience").getValue();
                String Type1 = (String) dataSnapshot.child("Type1").getValue();
                String Type2 = (String) dataSnapshot.child("Type2").getValue();
                String Type3 = (String) dataSnapshot.child("Type3").getValue();
                String Type4 = (String) dataSnapshot.child("Type4").getValue();
                Picasso.with(L_Profile.this).load(Image).into(circleImageView);
                name.setText(Name);
                age.setText(Age);
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
            Intent intent = new Intent(L_Profile.this,L_Edit_Profile.class);
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
