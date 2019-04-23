package com.example.smartlabour01;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

public class C_Edit_Profile extends AppCompatActivity {
private EditText experience,phone,location;
private TextView name,email;
private DatabaseReference mDatabase;
private FirebaseAuth mAuth;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
private Uri resultUri;
private CircleImageView circleImageView;
private StorageReference storageReference;
private String image;
private int counter=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c__edit__profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Profile");
        setSupportActionBar(toolbar);

        name = findViewById(R.id.tv_edit_Name);
        email = findViewById(R.id.tv_edit_email);
        experience = findViewById(R.id.tv_edit_experience);
        phone = findViewById(R.id.tv_edit_phone);
        location = findViewById(R.id.tv_edit_location);
        storageReference = FirebaseStorage.getInstance().getReference();
        circleImageView = findViewById(R.id.ivEditProfile);
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileImageButtonClicked();
            }
        });
        radioGroup = findViewById(R.id.radioGroup);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("ContractorUser");
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        String profile = Objects.requireNonNull(getIntent().getExtras()).getString("Profile");
        if (Objects.requireNonNull(profile).equals("pic")) {
            final RadioButton male,female;
            male = findViewById(R.id.radioMale);
            female = findViewById(R.id.radioFemale);
            mDatabase.child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    image = (String) dataSnapshot.child("Image").getValue();
                    String Name = (String) dataSnapshot.child("Name").getValue();
                    String Email = mAuth.getCurrentUser().getEmail();
                    String Contact = (String) dataSnapshot.child("Contact").getValue();
                    String Gender = (String) dataSnapshot.child("Gender").getValue();
                    String Location = (String) dataSnapshot.child("Location").getValue();
                    String Experience = (String) dataSnapshot.child("Experience").getValue();
                    if (Objects.requireNonNull(Gender).equals("Male"))
                        radioGroup.check(male.getId());
                    if (Gender.equals("Female"))
                        radioGroup.check(female.getId());

                    String Type1 = (String) dataSnapshot.child("Type1").getValue();
                    String Type2 = (String) dataSnapshot.child("Type2").getValue();
                    String Type3 = (String) dataSnapshot.child("Type3").getValue();
                    String Type4 = (String) dataSnapshot.child("Type4").getValue();
                    if (!image.equals("NA")) {
                        Picasso.with(C_Edit_Profile.this).load(image).into(circleImageView);
                    }
                    name.setText(Name);
                    email.setText(Email);
                    experience.setText(Experience);
                    phone.setText(Contact);
                    location.setText(Location);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }else if (Objects.requireNonNull(profile).equals("pic1")){
                mDatabase.child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String Name = (String) dataSnapshot.child("Name").getValue();
                        String Email = mAuth.getCurrentUser().getEmail();
                        String Phone = (String) dataSnapshot.child("Contact").getValue();
                        name.setText(Name);
                        email.setText(Email);
                        phone.setText(Phone);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
        }
    }


    public void profileImageButtonClicked() {
        // GetImageFromGallery();
        CropImage.activity().setAspectRatio(1,1).setCropShape(CropImageView.CropShape.OVAL).start(C_Edit_Profile.this);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK) {
                    resultUri = Objects.requireNonNull(result).getUri();
                    File resultFile = null;
                    File file = new File(resultUri.getPath());
                    //  Bitmap bitmap ;
                  /*  try {
                      bitmap =  new Compressor(this).compressToBitmap(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
                    try {
                        resultFile = new Compressor(this).compressToFile(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    resultUri = Uri.fromFile(resultFile);
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

        if (id==R.id.edit_profile_done){
            doneButtonClicked();
        }

        if (item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.done, menu);
        return true;
    }

    public void doneButtonClicked(){
        final String Experience = experience.getText().toString().trim();
        final String Phone = phone.getText().toString().trim();
        final String Location = location.getText().toString().trim();
        int selectedId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(selectedId);
        final String Gender = radioButton.getText().toString().trim();


         if(!TextUtils.isEmpty(Experience) && !TextUtils.isEmpty(Phone) && !TextUtils.isEmpty(Location)){
             final ProgressDialog progressDialog = new ProgressDialog(C_Edit_Profile.this, R.style.MyAlertDialogStyle);
             progressDialog.setTitle("Profile Updating");
             progressDialog.setMessage("Uploading...Plz wait...");
             progressDialog.setCancelable(false);
             progressDialog.show();

            /* StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(image);
             storageReference.delete();
            */
            if (counter==1){
            final StorageReference filepath = storageReference.child("Contractor_Profile_Image").child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).child(Objects.requireNonNull(resultUri.getLastPathSegment()));
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
                 public void onComplete(@NonNull final Task<Uri> task) {
                     if (task.isSuccessful()) {
                         if(!image.equals("NA")){
                             StorageReference photoRef = FirebaseStorage.getInstance().getReferenceFromUrl(image);
                             photoRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                 @Override
                                 public void onSuccess(Void aVoid) {
                                     Uri downloadUrl = task.getResult();
                                     progressDialog.cancel();
                                     DatabaseReference database = mDatabase.child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
                                     database.child("Gender").setValue(Gender);
                                     database.child("Experience").setValue(Experience);
                                     database.child("Contact").setValue(Phone);
                                     database.child("Location").setValue(Location);
                                     database.child("Image").setValue(Objects.requireNonNull(downloadUrl).toString());
                                     Toast.makeText(getApplicationContext(), "Profile Updated Successfully", Toast.LENGTH_LONG).show();
                                     Intent intent = new Intent(getApplicationContext(),C_Main_Activity.class);
                                     intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                     intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                     startActivity(intent);
                                     finish();
                                 }
                             }).addOnFailureListener(new OnFailureListener() {
                                 @Override
                                 public void onFailure(@NonNull Exception exception) {

                                 }
                             });
                         }
                         Uri downloadUrl = task.getResult();
                         progressDialog.cancel();
                         DatabaseReference database = mDatabase.child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
                         database.child("Gender").setValue(Gender);
                         database.child("Experience").setValue(Experience);
                         database.child("Contact").setValue(Phone);
                         database.child("Location").setValue(Location);
                         database.child("Image").setValue(Objects.requireNonNull(downloadUrl).toString());
                         Toast.makeText(getApplicationContext(), "Profile Updated Successfully", Toast.LENGTH_LONG).show();
                         Intent intent = new Intent(getApplicationContext(),C_Main_Activity.class);
                         intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                         intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                         startActivity(intent);
                         finish();
                     }
                 }
             });
         }else {
                progressDialog.cancel();
                DatabaseReference database = mDatabase.child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
                database.child("Gender").setValue(Gender);
                database.child("Experience").setValue(Experience);
                database.child("Contact").setValue(Phone);
                database.child("Location").setValue(Location);
                Toast.makeText(getApplicationContext(), "Profile Updated Successfully", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), C_Main_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }

         }
         else{
             Toast.makeText(this,"Please fill details",Toast.LENGTH_LONG).show();
         }
    }
}
