package com.example.abhijeetsingh.learningfirebase2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Profile extends AppCompatActivity {


    private static final int PICK_IMAGE_REQUEST=234;

    TextView useremail;
    FirebaseAuth firebaseAuth;
    Button logoutButton,saveButton;
    DatabaseReference databaseReference;
    StorageReference mStorageReference;



    EditText name;
    EditText address;

    ImageView profileimage;
    Button upload;
    Button chose;

    Uri filepath;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
            filepath=data.getData();

            try
            {
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),filepath);

                Bitmap scaledBitmap=Bitmap.createScaledBitmap(bitmap,200,200,true);

                profileimage.setImageBitmap(scaledBitmap);
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

//        if(firebaseAuth.getCurrentUser()==null)
//        {
//            finish();
//            Intent i=new Intent(getApplicationContext(),LoginIn.class);
//            startActivity(i);
//        }


        useremail=(TextView)findViewById(R.id.welcometext);
        firebaseAuth=FirebaseAuth.getInstance();
        logoutButton=(Button)findViewById(R.id.logout);
        databaseReference= FirebaseDatabase.getInstance().getReference();


        mStorageReference= FirebaseStorage.getInstance().getReference();

        name=(EditText)findViewById(R.id.nameID);
        address=(EditText)findViewById(R.id.addressID);
        saveButton=(Button)findViewById(R.id.saveButtonID);

        profileimage=(ImageView)findViewById(R.id.profile_imageID);
        upload=(Button)findViewById(R.id.uploadButtonID);
        chose=(Button)findViewById(R.id.choseButtonID);



        FirebaseUser user=firebaseAuth.getCurrentUser();

        useremail.setText("Welcome "+user.getEmail());


        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firebaseAuth.signOut();
                finish();
                Intent i=new Intent(getApplicationContext(),LoginIn.class);
                startActivity(i);

            }
        });


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username=name.getText().toString().trim();
                String useraddress=address.getText().toString().trim();

                USER newuser =new USER(username,useraddress);

                FirebaseUser user=firebaseAuth.getCurrentUser();

                databaseReference.child(user.getUid()).setValue(newuser);

                Toast.makeText(getApplicationContext(),"information saved...",Toast.LENGTH_SHORT).show();

            }
        });



        chose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i,"select and image"),PICK_IMAGE_REQUEST);

            }
        });


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (filepath != null) {

                    final ProgressDialog progressDialog=new ProgressDialog(Profile.this);
                    progressDialog.setTitle("Uploading...");
                    progressDialog.show();

                    StorageReference riversRef = mStorageReference.child("images/profile.jpg");

                    riversRef.putFile(filepath)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    // Get a URL to the uploaded content
                                    Task<Uri> downloadUrl = mStorageReference.getDownloadUrl();
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(),"File Uploaded",Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle unsuccessful uploads
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(),"File Upload failed "+exception.getMessage(),Toast.LENGTH_SHORT).show();

                                    // ...
                                }


                            })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress=(100.0*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                            progressDialog.setMessage(((int)progress)+"% uploaded...");
                        }
                    })

                    ;


                }

                else
                {

                }
            }
        });



    }
}
