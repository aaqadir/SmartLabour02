package com.example.smartlabour01;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
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
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class C_Hire extends AppCompatActivity{
private DatabaseReference mDatabase,databaseReference,databaseReference1;
private FirebaseAuth mAuth;
private Button hireIndividually,bulkHire;
private String projectType,labourType;
private EditText projectLabourCount,projectLocation,projectDate;
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_hire);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Hire");
        setSupportActionBar(toolbar);

        projectLabourCount = findViewById(R.id.editTextLabourCount);
        projectLocation = findViewById(R.id.editTextProjectLocation);
        projectDate = findViewById(R.id.editTextProjectDate);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("ContractorProjectTypes").child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
        databaseReference = FirebaseDatabase.getInstance().getReference("ContractorProjects").child(mAuth.getCurrentUser().getUid());
        databaseReference1 = FirebaseDatabase.getInstance().getReference().child("BulkLabourHire").push();
            Calendar calendar = Calendar.getInstance();
            final int year = calendar.get(Calendar.YEAR);
            final int month = calendar.get(Calendar.MONTH);
            final int day = calendar.get(Calendar.DAY_OF_MONTH);
        projectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(C_Hire.this, new DatePickerDialog.OnDateSetListener() {
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
        Spinner spinner1 = findViewById(R.id.spinnerLabourType);
        final List<String> categories = new ArrayList<String>();
            categories.add("Select Project Type");

            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
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

            final List<String> categories1 = new ArrayList<String>();
            categories1.add("Select Labour Type");
            categories1.add("Welder");
            categories1.add("Carpenter");
            categories1.add("Electrician");
            categories1.add("Mason");
            categories1.add("Plumber");
            categories1.add("Truck Driver");
            categories1.add("Pipe Fitter");
            categories1.add("Tradesman (Repairing Concrete,Finishing)");
            categories1.add("Crane Operator");
            categories1.add("Smith");
            categories1.add("Machine Operator (Temporary Lift , Bending Metal Rods)");

            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,categories1);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner1.setAdapter(adapter1);

            spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    labourType = parent.getItemAtPosition(position).toString();
                   // Toast.makeText(getApplicationContext(),""+labourType,Toast.LENGTH_LONG).show();
                    ((TextView)parent.getChildAt(0)).setTextSize(20);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        hireIndividually = findViewById(R.id.button_Individual_Hire);
        hireIndividually.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),HireIndividully.class);
                startActivity(intent);
            }
        });

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

            bulkHire = findViewById(R.id.button_Bulk_Hire);
            bulkHire.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bulkHireButtonClicked();
                }
            });
        }

    public void fetch(String projectType) {
        DatabaseReference mDatabase1 = databaseReference.child(projectType);
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

    public void bulkHireButtonClicked(){
            String Location = projectLocation.getText().toString().trim();
            String Date = projectDate.getText().toString().trim();
            String labourCount = projectLabourCount.getText().toString().trim();
            if (!projectType.equals("Select Project Type") && !labourType.equals("Select Labour Type") && !TextUtils.isEmpty(Location)&& !TextUtils.isEmpty(Date)&& !TextUtils.isEmpty(labourCount)){
                DatabaseReference databaseReference2 = databaseReference1;
                databaseReference2.child("ProjectType").setValue(projectType);
                databaseReference2.child("LabourType").setValue(labourType);
                databaseReference2.child("ProjectLocation").setValue(Location);
                databaseReference2.child("ProjectStartDate").setValue(Date);
                databaseReference2.child("LabourCount").setValue(labourCount);
                databaseReference2.child("ContractorUID").setValue(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
                Toast.makeText(getApplicationContext(),"Successful",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(),C_Main_Activity.class);
                startActivity(intent);
                finish();
            }else {
                Toast.makeText(getApplicationContext(),"Please fill details",Toast.LENGTH_LONG).show();
            }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);

    }

}
