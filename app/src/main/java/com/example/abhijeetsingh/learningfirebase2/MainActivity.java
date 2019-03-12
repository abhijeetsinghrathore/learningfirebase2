package com.example.abhijeetsingh.learningfirebase2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    EditText emailtext;
    EditText passText;
    Button registerButton;
    TextView signinview;
    ProgressDialog  progressDialog;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        emailtext=(EditText)findViewById(R.id.useremail);
        passText=(EditText)findViewById(R.id.userpass);
        registerButton=(Button)findViewById(R.id.registerbutton);
        signinview=(TextView)findViewById(R.id.signintext);
        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);

        if(firebaseAuth.getCurrentUser()!=null)
        {
            finish();
            Intent i=new Intent(MainActivity.this,Profile.class);
            startActivity(i);

        }


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email=emailtext.getText().toString().trim();
                String password=passText.getText().toString().trim();

                if(TextUtils.isEmpty(email))
                {
                    Toast.makeText(MainActivity.this,"enter email",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(password) )
                {
                    Toast.makeText(MainActivity.this,"enter password",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.length()<6)
                {
                    Toast.makeText(MainActivity.this,"password should atleast contain 6 characters",Toast.LENGTH_SHORT).show();
                }



//                progressDialog.setMessage("Registering User...");
//                progressDialog.show();

              firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                  @Override
                  public void onComplete(@NonNull Task<AuthResult> task) {

                      if(task.isSuccessful())
                      {
                          finish();
                          Intent i=new Intent(getApplicationContext(),Profile.class);
                          startActivity(i);
                          Toast.makeText(MainActivity.this,"registration successful",Toast.LENGTH_SHORT).show();

                      }
                      else
                      {
                          Toast.makeText(MainActivity.this,"registration unsuccessful",Toast.LENGTH_SHORT).show();
                      }

                  }
              });




            }
        });


        signinview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(MainActivity.this,LoginIn.class);
                startActivity(i);

            }
        });

    }
}
