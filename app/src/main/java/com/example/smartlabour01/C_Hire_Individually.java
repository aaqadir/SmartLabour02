package com.example.smartlabour01;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

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

public class C_Hire_Individually extends AppCompatActivity {
    private RecyclerView recyclerView;

    private ArtistsAdapter adapter;

    private List<Artist> artistList;
    private DatabaseReference dbArtists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c__hire__individually);

/*
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        artistList = new ArrayList<>();

        adapter = new ArtistsAdapter(this, artistList);

        recyclerView.setAdapter(adapter);

        dbArtists = FirebaseDatabase.getInstance().getReference("LabourUser");

        Query query,query1;
     //   query = dbArtists.orderByChild("Type1").equalTo("Mason");
        query1  = dbArtists.orderByChild("Experience");
      //  query1.addListenerForSingleValueEvent(valueEventListener);

      //  query.addListenerForSingleValueEvent(valueEventListener);
*/

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        dbArtists = FirebaseDatabase.getInstance().getReference("ContractorProjects").child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).child("Highway Construction").child("Hired Labours");
        Button button = findViewById(R.id.hire);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference db = dbArtists.push();
                db.child("Name").setValue("Shamshad");
                db.child("Contact").setValue("9930797445");
                db.child("Location").setValue("Mumbai");
                db.child("Experience").setValue("6");
                db.child("Skills").setValue("Electrician");
            }
        });
    }
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                artistList.clear();

                if (dataSnapshot.exists()) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        Artist artist = snapshot.getValue(Artist.class);

                        artistList.add(artist);

                    }

                    adapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

}
