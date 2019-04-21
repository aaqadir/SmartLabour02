package com.example.smartlabour01;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class C_Pending_Request_Labour extends AppCompatActivity {
    private BulkHireAdapter adapter;
    private List<BulkHire> bulkHireList;
    private DatabaseReference mDatabase,databaseReference;
    private FirebaseAuth mAuth;
    private String projectType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c__pending_request__labour);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Pending Requested Labour");
        setSupportActionBar(toolbar);

        mDatabase = FirebaseDatabase.getInstance().getReference("BulkLabourHire");
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("ContractorProjectTypes").child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());

        RecyclerView mInstaList = findViewById(R.id.contractorBulkRequestDetails);
        mInstaList.setHasFixedSize(true);
        mInstaList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        bulkHireList = new ArrayList<>();

        adapter = new BulkHireAdapter(this, bulkHireList);

        mInstaList.setAdapter(adapter);


        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Spinner spinner = findViewById(R.id.spinnerBulkProjectType);
        final List<String> categories = new ArrayList<String>();
        categories.add("Select Project Type");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               long count =  dataSnapshot.getChildrenCount();
               if (dataSnapshot.exists()){
                   for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                       String type = Objects.requireNonNull(snapshot.getValue()).toString();
                       categories.add(type);
                   }
               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,categories);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter1);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                projectType = parent.getItemAtPosition(position).toString();
                ((TextView)parent.getChildAt(0)).setTextSize(20);
                    PendingRequestForLabour();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


      //  fetch();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);

    }

    public void PendingRequestForLabour(){
        Query query = mDatabase;
        query.addListenerForSingleValueEvent(valueEventListener);

    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            bulkHireList.clear();
            DecimalFormat df = new DecimalFormat("00");
            long count =dataSnapshot.getChildrenCount();
            // inProgress.setText(df.format(count));

            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (Objects.requireNonNull(snapshot.child("ContractorUID").getValue()).equals(mAuth.getCurrentUser().getUid())) {
                        if (Objects.requireNonNull(snapshot.child("ProjectType").getValue()).equals(projectType)) {
                            BulkHire bulkHire = snapshot.getValue(BulkHire.class);
                            bulkHireList.add(bulkHire);
                        }
                    }
                }
                    if (bulkHireList.isEmpty() && !projectType.equals("Select Project Type")){
                        Toast.makeText(getApplicationContext(),"No Pending Request Found",Toast.LENGTH_LONG).show();
                    }
                adapter.notifyDataSetChanged();
            }else {
                if (!projectType.equals("Select Project Type")){
                Toast.makeText(getApplicationContext(),"No Pending Request Found",Toast.LENGTH_LONG).show();
                }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };


}
