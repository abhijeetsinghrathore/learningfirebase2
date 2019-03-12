package com.example.abhijeetsingh.learningfirebase2;

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

public class LoginIn extends AppCompatActivity {

    EditText email;
    EditText password;
    Button signInButton;
    TextView registertext;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_in);

        email=(EditText)findViewById(R.id.useremail_loginin);
        password=(EditText)findViewById(R.id.userpass_loginin);
        signInButton=(Button)findViewById(R.id.signinbutton);
        registertext=(TextView)findViewById(R.id.registertext);
        firebaseAuth=FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()!=null)
        {
            finish();
            Intent i=new Intent(LoginIn.this,Profile.class);
            startActivity(i);

        }



        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String emailtext=email.getText().toString().trim();
                String passwordtext=password.getText().toString().trim();



                if(TextUtils.isEmpty(emailtext))
                {
                    Toast.makeText(LoginIn.this,"enter email",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(passwordtext) )
                {
                    Toast.makeText(LoginIn.this,"enter password",Toast.LENGTH_SHORT).show();
                    return;
                }


                firebaseAuth.signInWithEmailAndPassword(emailtext,passwordtext).addOnCompleteListener(LoginIn.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            finish();
                         Intent i=new Intent(LoginIn.this,Profile.class);
                         startActivity(i);
                        }

                    }
                });

            }
        });


        registertext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(LoginIn.this,MainActivity.class);
                startActivity(i);

            }
        });
    }
}
