package com.example.smartlabour01;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

public class HireIndividully extends AppCompatActivity {
private MaterialSearchView searchView;
private ListView listView;
private ArrayAdapter adapter;
    String[] products;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hire_individully);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getColor(R.color.black));
        }
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        searchView = findViewById(R.id.search_view);
         products = new String[]{"Welder", "Carpenter", "Electrician", "Mason", "Plumber",
                 "Truck Driver", "Pipe Fitter",
                 "Tradesman (Repairing Concrete,Finishing)", "Crane Operator", "Smith", "Machine Operator (Temporary Lift , Bending Metal Rods)"};

        listView = (ListView) findViewById(R.id.listview);
        adapter = new ArrayAdapter<String>(this, R.layout.list_labour_skills, R.id.labour_skills, products);
        listView.setAdapter(adapter);

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {
                listView = (ListView) findViewById(R.id.listview);
                adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_labour_skills, R.id.labour_skills, products);
                listView.setAdapter(adapter);

            }
        });

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText!=null && !newText.isEmpty()){
                    List<String> listfound = new ArrayList<>();
                    for (String item:products){
                        String item1 = item.toLowerCase();
                        if (item.contains(newText) || item1.contains(newText))

                            listfound.add(item);
                     ArrayAdapter   adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_labour_skills, R.id.labour_skills, listfound);
                        listView.setAdapter(adapter);
                    }
                }else {

                    ArrayAdapter   adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_labour_skills, R.id.labour_skills, products);
                    listView.setAdapter(adapter);
                }
                    return true;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String skill = listView.getItemAtPosition(position).toString();
                Intent intent = new Intent(getApplicationContext(),HireIndividualLabour.class);
                intent.putExtra("Skill", skill);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item=menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }
}
