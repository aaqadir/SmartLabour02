package com.example.smartlabour01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Help");
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        String user = Objects.requireNonNull(getIntent().getExtras()).getString("user");

        ExpandableListView expandableListView = findViewById(R.id.expandableListView);
        HashMap<String, List<String>> item = new HashMap<>();

        if (Objects.requireNonNull(user).equals("contractor")) {

            ArrayList<String> AddProjects = new ArrayList<>();
            AddProjects.add("1 : Click Menu Icon on the Homepage");
            AddProjects.add("2 : Click Add Projects");
            AddProjects.add("3 : Fill Project Details");
            AddProjects.add("4 : Click Add Button");

            item.put("How to Add Projects", AddProjects);

            ArrayList<String> HireInBulk = new ArrayList<>();
            HireInBulk.add("1 : Click Hire in Navigation Drawer");
            HireInBulk.add("2 : Select Project Type");
            HireInBulk.add("3 : Select Labour Type");
            HireInBulk.add("4 : Enter No of Required Labourer");
            HireInBulk.add("5 : Click Hire in Bulk Button");

            item.put("How to Hire in Bulk", HireInBulk);

            ArrayList<String> HireIndividually = new ArrayList<>();
            HireIndividually.add("1 : Click Hire in Navigation Drawer");
            HireIndividually.add("2 : Click Hire Individually Button");
            HireIndividually.add("3 : Select Type of Skills for Labour");
            HireIndividually.add("4 : Select Labour of your Choice");
            HireIndividually.add("5 : Select Project Type from Dropdown List");
            HireIndividually.add("6 : Click Hire Button");

            item.put("How to Hire Individually", HireIndividually);

            ArrayList<String> PendingLabourRequest = new ArrayList<>();
            PendingLabourRequest.add("1 : Click Pending Requested Labour in Navigation Drawer");
            PendingLabourRequest.add("2 : Select Project Type from Dropdown List");
            PendingLabourRequest.add("3 : Pending Request will be Displayed");

            item.put("How to Check Pending Labour Request", PendingLabourRequest);

            ArrayList<String> CurrentWorkingProject = new ArrayList<>();
            CurrentWorkingProject.add("1 : Make Sure Your Internet is Working");
            CurrentWorkingProject.add("2 : See Your Home Page");
            CurrentWorkingProject.add("3 : Project Details will be Shown");
            CurrentWorkingProject.add("4 : Click on it of your Project Choice");
            CurrentWorkingProject.add("5 : Details of Hired Labour will be shown");
            CurrentWorkingProject.add("6 : To Delete Project Click on Delete Icon");

            item.put("How to View Current Working Project", CurrentWorkingProject);

            ArrayList<String> UpdateProfile = new ArrayList<>();
            UpdateProfile.add("1 : Click Profile in Navigation Drawer");
            UpdateProfile.add("2 : Current Profile Details will be Displayed");
            UpdateProfile.add("3 : Click Pencil Icon at Top Right Corner");
            UpdateProfile.add("4 : Update the Profile");
            UpdateProfile.add("5 : Click Tick Icon at Top Right Corner");
            UpdateProfile.add("6 : Your Profile will be Updated");

            item.put("How to Update Profile", UpdateProfile);

        }else if(user.equals("labour")){

            ArrayList<String> UpdateProfile = new ArrayList<>();
            UpdateProfile.add("1 : Click Profile in Navigation Drawer");
            UpdateProfile.add("2 : Current Profile Details will be Displayed");
            UpdateProfile.add("3 : Click Pencil Icon at Top Right Corner");
            UpdateProfile.add("4 : Update the Profile");
            UpdateProfile.add("5 : Click Tick Icon at Top Right Corner");
            UpdateProfile.add("6 : Your Profile will be Updated");

            item.put("How to Update Profile", UpdateProfile);

            ArrayList<String> CompletedProject = new ArrayList<>();
            CompletedProject.add("1 : Click Work History in Navigation Drawer");
            CompletedProject.add("2 : Completed Project Details will be Shown");
            CompletedProject.add("3 : Click on it to see all Working Details");

            item.put("How to See Previous Completed Project", CompletedProject);

            ArrayList<String> CurrentWorkingProject = new ArrayList<>();
            CurrentWorkingProject.add("1 : Make Sure Your Internet is Working");
            CurrentWorkingProject.add("2 : See Your Home Page");
            CurrentWorkingProject.add("3 : Project Details will be Shown");

            item.put("How to View Details of Current Working Project", CurrentWorkingProject);

            ArrayList<String> UpdateSkills = new ArrayList<>();
            UpdateSkills.add("1 : Click Profile in Navigation Drawer");
            UpdateSkills.add("2 : Current Profile Details will be Displayed");
            UpdateSkills.add("3 : Click Pencil Icon at Top Right Corner");
            UpdateSkills.add("4 : Update the Skills by Clicking Checkbox");
            UpdateSkills.add("5 : Click Tick Icon at Top Right Corner");
            UpdateSkills.add("6 : Your Skills will be Updated");

            item.put("How to Update Skills", UpdateSkills);
        }

        MyExpandableListAdapter adapter = new MyExpandableListAdapter(item);
        expandableListView.setAdapter(adapter);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
