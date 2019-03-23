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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
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
    private TextView gender, experience, location, age;
    private TextView name, phone, skill1,skill2,skill3,skill4,skill5,skill6,skill7,skill8,skill9,skill10,skill11,skill12;
    private DatabaseReference mDatabase;
    private Uri resultUri;
    private CircleImageView circleImageView;
    private String image;
    private int counter = 0;
    private static final String SHARED_PREF_NAME = "labourPref";
    private static final String KEY_NAME = "phoneNumber";
    private String user;
  //  private ListView listView;
    private ArrayAdapter adapter;

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
        circleImageView = findViewById(R.id.labourProfile);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("LabourUser");
        skill1 = findViewById(R.id.skill_1);
        skill2 = findViewById(R.id.skill_2);
        skill3 = findViewById(R.id.skill_3);
        skill4 = findViewById(R.id.skill_4);
        skill5 = findViewById(R.id.skill_5);
        skill6 = findViewById(R.id.skill_6);
        skill7 = findViewById(R.id.skill_7);
        skill8 = findViewById(R.id.skill_8);
        skill9 = findViewById(R.id.skill_9);
        skill10 = findViewById(R.id.skill_10);
        skill11 = findViewById(R.id.skill_11);
        skill12 = findViewById(R.id.skill_12);
        adapter = new ArrayAdapter<String>(this, R.layout.list_labour_skills, R.id.labour_skills);

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
                String Welder,Carpenter,Electrician,Mason,Plumber,Truckdriver,Pipefitter,Tradesman,Craneoperator,Smith,Machineoperator;
                Welder = (String) dataSnapshot.child("Welder").getValue();
                Carpenter = (String) dataSnapshot.child("Carpenter").getValue();
                Electrician = (String) dataSnapshot.child("Electrician").getValue();
                Mason = (String) dataSnapshot.child("Mason").getValue();
                Plumber = (String) dataSnapshot.child("Plumber").getValue();
                Truckdriver = (String) dataSnapshot.child("Truck Driver").getValue();
                Pipefitter = (String) dataSnapshot.child("Pipe Fitter").getValue();
                Tradesman = (String) dataSnapshot.child("Tradesman").getValue();
                Craneoperator = (String) dataSnapshot.child("Crane Operator").getValue();
                Smith = (String) dataSnapshot.child("Smith").getValue();
                Machineoperator = (String) dataSnapshot.child("Machine Operator").getValue();

                if (Objects.requireNonNull(Welder).equals("Yes"))
                    adapter.add("Welder");
                if (Objects.requireNonNull(Carpenter).equals("Yes"))
                    adapter.add("Carpenter");
                if (Objects.requireNonNull(Electrician).equals("Yes"))
                    adapter.add("Electrician");
                if (Objects.requireNonNull(Mason).equals("Yes"))
                    adapter.add("Mason");
                if (Objects.requireNonNull(Plumber).equals("Yes"))
                    adapter.add("Plumber");
                if (Objects.requireNonNull(Truckdriver).equals("Yes"))
                    adapter.add("Truck Driver");
                if (Objects.requireNonNull(Pipefitter).equals("Yes"))
                    adapter.add("Pipe Fitter");
                if (Objects.requireNonNull(Tradesman).equals("Yes"))
                   adapter.add("Tradesman");
                if (Objects.requireNonNull(Craneoperator).equals("Yes"))
                    adapter.add("Crane Operator");
                if (Objects.requireNonNull(Smith).equals("Yes"))
                    adapter.add("Smith");
                if (Objects.requireNonNull(Machineoperator).equals("Yes"))
                    adapter.add("Machine Operator");

                adapter.add("");adapter.add("");adapter.add("");adapter.add("");adapter.add("");
                adapter.add("");adapter.add("");adapter.add("");adapter.add("");adapter.add("");

                skill1.setText(Objects.requireNonNull(adapter.getItem(0)).toString());
                skill2.setText(Objects.requireNonNull(adapter.getItem(1)).toString());
                skill3.setText(Objects.requireNonNull(adapter.getItem(2)).toString());
                skill4.setText(Objects.requireNonNull(adapter.getItem(3)).toString());
                skill5.setText(Objects.requireNonNull(adapter.getItem(4)).toString());
                skill6.setText(Objects.requireNonNull(adapter.getItem(5)).toString());
                skill7.setText(Objects.requireNonNull(adapter.getItem(6)).toString());
                skill8.setText(Objects.requireNonNull(adapter.getItem(7)).toString());
                skill9.setText(Objects.requireNonNull(adapter.getItem(8)).toString());
                skill10.setText(Objects.requireNonNull(adapter.getItem(9)).toString());
                skill11.setText(Objects.requireNonNull(adapter.getItem(10)).toString());
                skill12.setText(Objects.requireNonNull(adapter.getItem(11)).toString());
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
