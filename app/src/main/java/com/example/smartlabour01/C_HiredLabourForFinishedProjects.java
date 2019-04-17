package com.example.smartlabour01;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class C_HiredLabourForFinishedProjects extends AppCompatActivity {
    private DatabaseReference mDatabase,databaseReference,databaseReference1;
    private FirebaseAuth mAuth;
    private RecyclerView mInstaList;
    //   private FirebaseRecyclerAdapter adapter;
    private HiredAdapter adapter;
    private Query query;
    private List<Hired> hiredList;

    String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c__hired_labour_for_finished_projects);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Hired Labourers");
        setSupportActionBar(toolbar);
      /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getColor(R.color.black));
        }
*/
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        mAuth = FirebaseAuth.getInstance();
        key =  Objects.requireNonNull(getIntent().getExtras()).getString("ProjectType");
        databaseReference1= FirebaseDatabase.getInstance().getReference().child("ContractorProjectTypes").child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
        mDatabase = FirebaseDatabase.getInstance().getReference("ContractorFinishedProjects").child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).child(key);
        databaseReference = FirebaseDatabase.getInstance().getReference("ContractorFinishedProjects").child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).child(key);
        mInstaList = findViewById(R.id.hiredLabourForProject);
        mInstaList.setHasFixedSize(true);
        mInstaList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        //    fetch();
        hiredList = new ArrayList<>();

        adapter = new HiredAdapter(this, hiredList);

        mInstaList.setAdapter(adapter);

            fetch();
    }

    public void fetch(){
        query = mDatabase.child("HiredLabours");
        query.addListenerForSingleValueEvent(valueEventListener);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.finish, menu);
        return true;
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            hiredList.clear();

            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Hired hired = snapshot.getValue(Hired.class);

                    hiredList.add(hired);

                }
                adapter.notifyDataSetChanged();

            }else {
                Toast.makeText(getApplicationContext(),"No Labour Found",Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.finish_project) {
          //  finishProject(mDatabase,databaseReference,databaseReference1,key);
            deleteProject(mDatabase);
        }

        if (item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    public void deleteProject(DatabaseReference mDatabase){
        mDatabase.removeValue();
        Intent intent = new Intent(getApplicationContext(),C_Main_Activity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

}
