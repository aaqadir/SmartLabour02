package com.example.smartlabour01;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class C_Hire_Labour extends AppCompatActivity {
    private TextView gender, experience, location, age , name, phone , status;
    private DatabaseReference mDatabase,databaseReference,databaseReference1,databaseReference2;
    private FirebaseAuth mAuth;
    private CircleImageView circleImageView;
    private EditText projectLocation,projectDate;
    private String projectType;
    String Status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c__hire__labour);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Hire");
        setSupportActionBar(toolbar);

        projectLocation = findViewById(R.id.editTextProjectLocation);
        projectDate = findViewById(R.id.editTextProjectDate);

        name = findViewById(R.id.labour_profile_Name);
        gender = findViewById(R.id.labour_profile_gender);
        experience = findViewById(R.id.labour_profile_experience);
        phone = findViewById(R.id.labour_profile_Phone);
        age = findViewById(R.id.labour_profile_Age);
        status = findViewById(R.id.labour_profile_Status);
        location = findViewById(R.id.labour_profile_location);
        circleImageView = findViewById(R.id.labourProfile);
        mAuth = FirebaseAuth.getInstance();

        databaseReference2 = FirebaseDatabase.getInstance().getReference("ContractorProjects").child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());

        databaseReference1 = FirebaseDatabase.getInstance().getReference("ContractorProjectTypes").child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
        mDatabase = FirebaseDatabase.getInstance().getReference().child("LabourUser");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("IndividualLabourHire");
        final String User = Objects.requireNonNull(getIntent().getExtras()).getString("PostId");
        final String Skill = Objects.requireNonNull(getIntent().getExtras()).getString("Skill");
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        getProfileInfo(User);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        projectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(C_Hire_Labour.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month+1;
                        String date = day+"/"+month+"/"+year;
                        projectDate.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        Spinner spinner = findViewById(R.id.spinnerProjectType);

        final List<String> categories = new ArrayList<String>();
        categories.add("Select Project Type");

        databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    String type = Objects.requireNonNull(snapshot.getValue()).toString();
                    categories.add(type);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                projectType = parent.getItemAtPosition(position).toString();
                ((TextView)parent.getChildAt(0)).setTextSize(20);
                if (!projectType.equals("Select Project Type"))
                    fetch(projectType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Button hire = findViewById(R.id.button_Hire);
        hire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hireButtonClicked(Skill,User);
            }
        });
    }


    public void fetch(String projectType) {
        DatabaseReference mDatabase1 = databaseReference2.child(projectType);
        mDatabase1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String Location = Objects.requireNonNull(dataSnapshot.child("ProjectLocation").getValue()).toString();
                String Date = Objects.requireNonNull(dataSnapshot.child("ProjectStartDate").getValue()).toString();
                projectLocation.setText(Location);
                projectDate.setText(Date);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void hireButtonClicked(String skill,String User){

        final String Skill;
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

        final String Location = projectLocation.getText().toString().trim();
        final String Date = projectDate.getText().toString().trim();

            if (!projectType.equals("Select Project Type") && !TextUtils.isEmpty(Location) && !TextUtils.isEmpty(Date)) {

                final DatabaseReference post = databaseReference2.child(projectType).child("HiredLabours").push();
                final DatabaseReference reference = mDatabase.child(User);

                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        post.child("Image").setValue(dataSnapshot.child("Image").getValue());
                        post.child("Location").setValue(dataSnapshot.child("Location").getValue());
                        post.child("Experience").setValue(dataSnapshot.child("Experience").getValue());
                        post.child("Skills").setValue(Skill);
                        post.child("Name").setValue(dataSnapshot.child("Name").getValue());
                        post.child("Contact").setValue(dataSnapshot.child("Contact").getValue());
                        reference.child("Status").setValue("Hired");
                        reference.child("HiredContractor").child("UID").setValue(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
                        reference.child("HiredContractor").child("ProjectType").setValue(projectType);
                        reference.child("HiredContractor").child("WorkType").setValue(Skill);
                        reference.child("HiredContractor").child("Location").setValue(Location);
                        reference.child("HiredContractor").child("StartDate").setValue(Date);
                        Toast.makeText(getApplicationContext(),"Labour Hired for "+projectType,Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(),C_Main_Activity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            } else {
                Toast.makeText(getApplicationContext(), "Please fill details", Toast.LENGTH_LONG).show();
            }

    }

    public void getProfileInfo(String user){
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
                Status = (String) dataSnapshot.child("Status").getValue();
                Picasso.with(C_Hire_Labour.this).load(Image).into(circleImageView);
                name.setText(Name);
                age.setText(Age);
                gender.setText(Gender);
                experience.setText(Experience);
                phone.setText(Contact);
                location.setText(Location);
                status.setText(Status);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);

    }


}
