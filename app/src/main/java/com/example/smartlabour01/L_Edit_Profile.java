package com.example.smartlabour01;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class L_Edit_Profile extends AppCompatActivity {
    private EditText age, experience, location;
    private TextView name, phone;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private CheckBox welder,carpenter,electrician,mason,plumber,truckdriver,pipefitter,tradesman,craneoperator,smith,machineoperator;
    private DatabaseReference mDatabase;
    private Uri resultUri;
    private CircleImageView circleImageView;
    private StorageReference storageReference;
    private String image;
    private int counter = 0;
    private static final String SHARED_PREF_NAME = "labourPref";
    private static final String KEY_NAME = "phoneNumber";
    private String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_l__edit__profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Profile");
        setSupportActionBar(toolbar);

        name = findViewById(R.id.labour_profile_edit_Name);
        experience = findViewById(R.id.labour_profile_edit_experience);
        phone = findViewById(R.id.labour_profile_edit_Phone);
        age = findViewById(R.id.labour_profile_edit_Age);
        location = findViewById(R.id.labour_profile_edit_location);
        storageReference = FirebaseStorage.getInstance().getReference();
        circleImageView = findViewById(R.id.labourEditProfile);
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileImageButtonClicked();
            }
        });
        mDatabase = FirebaseDatabase.getInstance().getReference().child("LabourUser");
        radioGroup = findViewById(R.id.radioGroup);

        welder = findViewById(R.id.checkboxWelder);
        carpenter = findViewById(R.id.checkboxCarpenter);
        electrician = findViewById(R.id.checkboxElectrician);
        mason = findViewById(R.id.checkboxMason);
        plumber = findViewById(R.id.checkboxPlumber);
        truckdriver = findViewById(R.id.checkboxTruckDriver);
        pipefitter = findViewById(R.id.checkboxPipeFitter);
        tradesman = findViewById(R.id.checkboxTradesman);
        craneoperator = findViewById(R.id.checkboxCraneOperator);
        smith = findViewById(R.id.checkboxSmith);
        machineoperator = findViewById(R.id.checkboxMachineOperator);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
         final RadioButton male,female;
        male = findViewById(R.id.radioMale);
        female = findViewById(R.id.radioFemale);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        user = sharedPreferences.getString(KEY_NAME, null);

        String profile = Objects.requireNonNull(getIntent().getExtras()).getString("Profile");
        if (Objects.requireNonNull(profile).equals("pic")) {
            mDatabase.child(Objects.requireNonNull(user)).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    image = (String) dataSnapshot.child("Image").getValue();
                    String Name = (String) dataSnapshot.child("Name").getValue();
                    String Contact = (String) dataSnapshot.child("Contact").getValue();
                    String Age = (String) dataSnapshot.child("Age").getValue();
                    String Location = (String) dataSnapshot.child("Location").getValue();
                    String Experience = (String) dataSnapshot.child("Experience").getValue();
                    Picasso.with(L_Edit_Profile.this).load(image).into(circleImageView);
                    String Gender = (String) dataSnapshot.child("Gender").getValue();
                    if (Objects.requireNonNull(Gender).equals("Male"))
                        radioGroup.check(male.getId());
                    if (Gender.equals("Female"))
                        radioGroup.check(female.getId());

                    String Welder,Carpenter,Electrician,Mason,Plumber,Truckdriver,Pipefitter,Tradesman,Craneoperator,Smith,Machineoperator;
                    Welder = (String) dataSnapshot.child("Welder").getValue();
                    Carpenter = (String) dataSnapshot.child("Carpenter").getValue();
                    Electrician = (String) dataSnapshot.child("Electrician").getValue();
                    Mason = (String) dataSnapshot.child("Mason").getValue();
                    Plumber = (String) dataSnapshot.child("Plumber").getValue();
                    Truckdriver = (String) dataSnapshot.child("TruckDriver").getValue();
                    Pipefitter = (String) dataSnapshot.child("PipeFitter").getValue();
                    Tradesman = (String) dataSnapshot.child("Tradesman").getValue();
                    Craneoperator = (String) dataSnapshot.child("CraneOperator").getValue();
                    Smith = (String) dataSnapshot.child("Smith").getValue();
                    Machineoperator = (String) dataSnapshot.child("MachineOperator").getValue();

                    if (Objects.requireNonNull(Welder).equals("Yes"))
                        welder.setChecked(true);
                    if (Objects.requireNonNull(Carpenter).equals("Yes"))
                        carpenter.setChecked(true);
                    if (Objects.requireNonNull(Electrician).equals("Yes"))
                        electrician.setChecked(true);
                    if (Objects.requireNonNull(Mason).equals("Yes"))
                        mason.setChecked(true);
                    if (Objects.requireNonNull(Plumber).equals("Yes"))
                        plumber.setChecked(true);
                    if (Objects.requireNonNull(Truckdriver).equals("Yes"))
                        truckdriver.setChecked(true);
                    if (Objects.requireNonNull(Pipefitter).equals("Yes"))
                        pipefitter.setChecked(true);
                    if (Objects.requireNonNull(Tradesman).equals("Yes"))
                        tradesman.setChecked(true);
                    if (Objects.requireNonNull(Craneoperator).equals("Yes"))
                        craneoperator.setChecked(true);
                    if (Objects.requireNonNull(Smith).equals("Yes"))
                        smith.setChecked(true);
                    if (Objects.requireNonNull(Machineoperator).equals("Yes"))
                        machineoperator.setChecked(true);

                    name.setText(Name);
                    experience.setText(Experience);
                    phone.setText(Contact);
                    location.setText(Location);
                    age.setText(Age);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else if (Objects.requireNonNull(profile).equals("pic1")) {
            mDatabase.child(Objects.requireNonNull(user)).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String Name = (String) dataSnapshot.child("Name").getValue();
                    String Phone = (String) dataSnapshot.child("Contact").getValue();
                    name.setText(Name);
                    phone.setText(Phone);
                    radioGroup.check(male.getId());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    public void profileImageButtonClicked() {
        CropImage.activity().setAspectRatio(1, 1).setCropShape(CropImageView.CropShape.OVAL).start(L_Edit_Profile.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK) {
                    resultUri = Objects.requireNonNull(result).getUri();
                    circleImageView.setImageURI(resultUri);
                    counter++;
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = Objects.requireNonNull(result).getError();
                    Toast.makeText(getApplicationContext(), "" + error, Toast.LENGTH_LONG).show();
                }
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.edit_profile_done) {
            doneButtonClicked();
        }

        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.done, menu);
        return true;
    }

    public void doneButtonClicked() {
            final String Experience = experience.getText().toString().trim();
            final String Age = age.getText().toString().trim();
            final String Location = location.getText().toString().trim();
            final String Welder,Carpenter,Electrician,Mason,Plumber,Truckdriver,Pipefitter,Tradesman,Craneoperator,Smith,Machineoperator;
            int selectedId = radioGroup.getCheckedRadioButtonId();
            radioButton = findViewById(selectedId);
        final String Gender = radioButton.getText().toString().trim();

        if (welder.isChecked())
                Welder = "Yes";
            else
                Welder = "NA";

            if (carpenter.isChecked())
                Carpenter = "Yes";
            else
                Carpenter = "NA";

        if (electrician.isChecked())
            Electrician = "Yes";
        else
            Electrician = "NA";

        if (mason.isChecked())
            Mason = "Yes";
        else
            Mason = "NA";

        if (plumber.isChecked())
            Plumber = "Yes";
        else
            Plumber = "NA";

        if (truckdriver.isChecked())
            Truckdriver = "Yes";
        else
            Truckdriver = "NA";

        if (pipefitter.isChecked())
            Pipefitter = "Yes";
        else
            Pipefitter = "NA";

        if (tradesman.isChecked())
           Tradesman = "Yes";
        else
            Tradesman = "NA";

        if (craneoperator.isChecked())
            Craneoperator = "Yes";
        else
            Craneoperator = "NA";

        if (smith.isChecked())
            Smith = "Yes";
        else
            Smith = "NA";

        if (machineoperator.isChecked())
            Machineoperator = "Yes";
        else
            Machineoperator = "NA";

            if (!TextUtils.isEmpty(Experience) && !TextUtils.isEmpty(Age) && !TextUtils.isEmpty(Location)) {
                final ProgressDialog progressDialog = new ProgressDialog(L_Edit_Profile.this, R.style.MyAlertDialogStyle);
                progressDialog.setTitle("Profile Updating");
                progressDialog.setMessage("Uploading...Plz wait...");
                progressDialog.setCancelable(false);
                progressDialog.show();

            /* StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(image);
             storageReference.delete();
            */
                if (counter == 1) {
                    final StorageReference filepath = storageReference.child("Labour_Profile_Image").child(Objects.requireNonNull(user)).child(Objects.requireNonNull(resultUri.getLastPathSegment()));
                    filepath.putFile(resultUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw Objects.requireNonNull(task.getException());
                            }

                            // Continue with the task to get the download URL
                            return filepath.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                Uri downloadUrl = task.getResult();
                                //                      Toast.makeText(getApplicationContext(),"Upload Complete",Toast.LENGTH_LONG).show();
                                progressDialog.cancel();
                                DatabaseReference database = mDatabase.child(Objects.requireNonNull(user));
                                database.child("Experience").setValue(Experience);
                                database.child("Age").setValue(Age);
                                database.child("Gender").setValue(Gender);
                                database.child("Location").setValue(Location);
                                database.child("Image").setValue(Objects.requireNonNull(downloadUrl).toString());
                                database.child("Welder").setValue(Welder);
                                database.child("Carpenter").setValue(Carpenter);
                                database.child("Electrician").setValue(Electrician);
                                database.child("Mason").setValue(Mason);
                                database.child("Plumber").setValue(Plumber);
                                database.child("TruckDriver").setValue(Truckdriver);
                                database.child("PipeFitter").setValue(Pipefitter);
                                database.child("Tradesman").setValue(Tradesman);
                                database.child("CraneOperator").setValue(Craneoperator);
                                database.child("Smith").setValue(Smith);
                                database.child("MachineOperator").setValue(Machineoperator);
                                Toast.makeText(getApplicationContext(), "Profile Updated Successfully", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), L_MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                } else {
                    progressDialog.cancel();
                    DatabaseReference database = mDatabase.child(Objects.requireNonNull(user));
                    database.child("Experience").setValue(Experience);
                    database.child("Age").setValue(Age);
                    database.child("Gender").setValue(Gender);
                    database.child("Location").setValue(Location);
                    database.child("Welder").setValue(Welder);
                    database.child("Carpenter").setValue(Carpenter);
                    database.child("Electrician").setValue(Electrician);
                    database.child("Mason").setValue(Mason);
                    database.child("Plumber").setValue(Plumber);
                    database.child("TruckDriver").setValue(Truckdriver);
                    database.child("PipeFitter").setValue(Pipefitter);
                    database.child("Tradesman").setValue(Tradesman);
                    database.child("CraneOperator").setValue(Craneoperator);
                    database.child("Smith").setValue(Smith);
                    database.child("MachineOperator").setValue(Machineoperator);
                    Toast.makeText(getApplicationContext(), "Profile Updated Successfully", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), L_MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }

            } else {
                Toast.makeText(this, "Please fill details", Toast.LENGTH_LONG).show();
            }
        }
    }
