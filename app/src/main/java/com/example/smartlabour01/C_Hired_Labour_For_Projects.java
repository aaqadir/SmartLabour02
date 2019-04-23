package com.example.smartlabour01;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class C_Hired_Labour_For_Projects extends AppCompatActivity {
private DatabaseReference mDatabaseContractorProjects,mDatabaseContractorFinishedProjects,mDatabaseContractorProjectTypes,mDatabaseLabours;
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
        setContentView(R.layout.activity_c__hired__labour__for__projects);
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
        mDatabaseContractorProjectTypes=FirebaseDatabase.getInstance().getReference().child("ContractorProjectTypes").child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
        mDatabaseContractorProjects = FirebaseDatabase.getInstance().getReference("ContractorProjects").child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).child(key);
        mDatabaseContractorFinishedProjects = FirebaseDatabase.getInstance().getReference("ContractorFinishedProjects").child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).child(key);
        mDatabaseLabours = FirebaseDatabase.getInstance().getReference("LabourUser");
        mInstaList = findViewById(R.id.hiredLabourForProject);
        mInstaList.setHasFixedSize(true);
        mInstaList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    //    fetch();
        hiredList = new ArrayList<>();

        adapter = new HiredAdapter(this, hiredList);

        mInstaList.setAdapter(adapter);


    }

    @Override
    protected void onStart() {
        super.onStart();
        fetch();
    }

    public void fetch(){
        query = mDatabaseContractorProjects.child("HiredLabours");
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
                for (int i = 0; i<hiredList.size() ; i++) {
                    for (int j=i+1;j<hiredList.size();j++){
                        if(Integer.parseInt(hiredList.get(j).Experience) > Integer.parseInt(hiredList.get(i).Experience)){
                            Hired a = hiredList.get(j);
                            hiredList.set(j,hiredList.get(i));
                            hiredList.set(i,a);
                        }
                    }
                }
                adapter.notifyDataSetChanged();

            }else{
                Toast.makeText(getApplicationContext(),"No Labourer Hired",Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };



    /*  private void fetch() {
        //   swipeRefreshLayout.setRefreshing(true);
        Query query;
        query = mDatabaseContractorProjects.child(key).child("HiredLabours");
        FirebaseRecyclerOptions<HiredLabour> options = new FirebaseRecyclerOptions.Builder<HiredLabour>().setQuery(query, new SnapshotParser<HiredLabour>() {
            @NonNull
            @Override
            public HiredLabour parseSnapshot(@NonNull DataSnapshot snapshot) {
                //        if (Objects.requireNonNull(snapshot.child("Type1").getValue()).equals(skill))
                return new HiredLabour(Objects.requireNonNull(snapshot.child("Name").getValue()).toString(),
                        Objects.requireNonNull(snapshot.child("Contact").getValue()).toString(),
                        Objects.requireNonNull(snapshot.child("Experience").getValue()).toString(),
                        Objects.requireNonNull(snapshot.child("Image").getValue()).toString(),
                        Objects.requireNonNull(snapshot.child("Location").getValue()).toString(),
                        Objects.requireNonNull(snapshot.child("Skills").getValue()).toString());
                *//*} else {
                    return new HiredLabour( "NA","NA","NA","NA","NA");
                }*//*
            }
        }).build();
        adapter = new FirebaseRecyclerAdapter<HiredLabour, ViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolder holder, final int position, @NonNull HiredLabour model) {
                final String post_key = getRef(position).getKey();
                holder.setName(model.getName());
                holder.setContact(model.getContact());
                holder.setSkills(model.getSkills());
                holder.setImage(getApplicationContext(), model.getImage());
                // holder.setImage(getApplicationContext(),model.getImage());
                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("ShowToast")
                    @Override
                    public void onClick(View v) {
                        *//*Intent intent = new Intent(getApplicationContext(),SingleBooksDonationDetails.class);
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
                        .inflate(R.layout.hired_labour_card, viewGroup, false);
*//*
                    mDatabaseContractorProjects.addListenerForSingleValueEvent(new ValueEventListener() {
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
                    });*//*
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
            TextView post_Name = (TextView) mView.findViewById(R.id.hired_labour_card_name);
            post_Name.setText("Name : " + Name);
        }
        void setContact(String Contact){
            TextView post_Contact = (TextView) mView.findViewById(R.id.hired_labour_card_contact);
            post_Contact.setText("Contact : " + Contact);
        }
        void setImage(Context context, String Image) {
            ImageView post_image = (ImageView) mView.findViewById(R.id.hired_labour_card_profile_image);
            Picasso.with(context).load(Image).into(post_image);
        }
        void setSkills(String Skills){
            TextView post_Skills = mView.findViewById(R.id.hired_labour_card_skills);
            post_Skills.setText("Skills : " + Skills);
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.finish_project) {
            finishProject(mDatabaseContractorProjects,mDatabaseContractorFinishedProjects,mDatabaseContractorProjectTypes,key);
        }

        if (item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    public void finishProject(final DatabaseReference mDatabaseContractorProject, final DatabaseReference mDatabaseContractorFinishedProject, final DatabaseReference mDatabaseContractorProjectTypes, final String projectType){
        mDatabaseContractorProject.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot)
            {
                mDatabaseContractorFinishedProject.setValue(dataSnapshot.getValue(), new DatabaseReference.CompletionListener()
                {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull final DatabaseReference mDatabaseContractorFinishedProjects) {
                        if (databaseError != null)
                        {
                            Toast.makeText(getApplicationContext(),""+databaseError,Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Date c = Calendar.getInstance().getTime();
                            System.out.println("Current time => " + c);

                            @SuppressLint("SimpleDateFormat")
                            SimpleDateFormat df = new SimpleDateFormat("dd/M/yyyy");
                            final String formattedDate = df.format(c);

                            for (DataSnapshot snapshot:dataSnapshot.child("HiredLabours").getChildren()){
                                String contact = Objects.requireNonNull(snapshot.child("Contact").getValue()).toString();
                                ChangeStatusOfLabours(contact,formattedDate);
                            }

                            mDatabaseContractorProjectTypes.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                                        if (Objects.requireNonNull(snapshot.getValue()).equals(projectType)){
                                            snapshot.getRef().removeValue();
                                            mDatabaseContractorProject.removeValue();
                                            mDatabaseContractorFinishedProject.child("ProjectEndDate").setValue(formattedDate);
                                            Toast.makeText(getApplicationContext(),"Project Completed",Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(getApplicationContext(),C_Main_Activity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),""+databaseError,Toast.LENGTH_LONG).show();
            }

        });
    }

    public void ChangeStatusOfLabours(String Contact, final String FinishedDate){
        final DatabaseReference reference = mDatabaseLabours.child(Contact);
        final DatabaseReference reference1 = mDatabaseLabours.child(Contact).child("CompletedHiredContractor").push();
        reference.child("HiredContractor").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                reference1.setValue(dataSnapshot.getValue(), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        if (databaseError != null)
                        {
                            Toast.makeText(getApplicationContext(),""+databaseError,Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                           String key =  databaseReference.getKey();
                            databaseReference.child("EndDate").setValue(FinishedDate);
                            databaseReference.child("Key").setValue(key);
                            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    reference.child("Status").setValue("Available");
                                    for (DataSnapshot snapshot: dataSnapshot.child("HiredContractor").getChildren()){
                                        String key = snapshot.getKey();
                                        reference.child("HiredContractor").child(Objects.requireNonNull(key)).setValue("NA");
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

}
