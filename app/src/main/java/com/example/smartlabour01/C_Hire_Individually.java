package com.example.smartlabour01;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class C_Hire_Individually extends AppCompatActivity {
    private RecyclerView recyclerView;

    private ArtistsAdapter adapter;

    private List<Artist> artistList;
    private DatabaseReference dbArtists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c__hire__individually);

        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        artistList = new ArrayList<>();

        adapter = new ArtistsAdapter(this, artistList);

        recyclerView.setAdapter(adapter);

        dbArtists = FirebaseDatabase.getInstance().getReference("LabourUser");

        Query query,query1;
        query = dbArtists.orderByChild("Type1").equalTo("Mason");
        query1  = dbArtists.orderByChild("Experience");
      //  query1.addListenerForSingleValueEvent(valueEventListener);

        query.addListenerForSingleValueEvent(valueEventListener);
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
