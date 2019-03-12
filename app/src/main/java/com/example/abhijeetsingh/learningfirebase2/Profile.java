package com.example.abhijeetsingh.learningfirebase2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Profile extends AppCompatActivity {

    TextView useremail;
    FirebaseAuth firebaseAuth;
    Button logoutButton;

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
    }
}
