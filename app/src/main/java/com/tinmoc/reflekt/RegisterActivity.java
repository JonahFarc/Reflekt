package com.tinmoc.reflekt;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by M3800 on 2/24/2018.
 */

public class RegisterActivity extends AppCompatActivity {
    private EditText name, email, password;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        name = (EditText)findViewById(R.id.editUsername);
        email = (EditText)findViewById(R.id.editEmail);
        password = (EditText)findViewById(R.id.editPassword);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
    }

    public void signupButtonClicked(View view) {
        final String name_content, password_content, email_content;
        name_content = name.getText().toString().trim();
        password_content = password.getText().toString().trim();
        email_content = email.getText().toString().trim();
        if(!TextUtils.isEmpty(email_content) && !TextUtils.isEmpty(password_content) && !TextUtils.isEmpty(name_content)) {
            mAuth.createUserWithEmailAndPassword(email_content, password_content).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        String user_id = mAuth.getCurrentUser().getUid();
                        final DatabaseReference current_user_db = mDatabase.child(user_id);
                        current_user_db.child("Name").setValue(name_content);
                        current_user_db.child("Age").setValue("23");
                        current_user_db.child("Target_Age").setValue("20,30");
                        current_user_db.child("Target_Distance").setValue("15");
                        current_user_db.child("Location").setValue("Long: 55.12343 Lat: 23.1234123");
                        current_user_db.child("Bio").setValue("I'm a senior studying CS at UTD and I really like eating salads. Cooking is my favorite past time and I went to culinary school so I'm guaranteed to make you a great dinner.");
                        current_user_db.child("Gender").setValue("Male");
                        current_user_db.child("Target_Gender").setValue("Female, Nonbinary");
                        current_user_db.child("Interests").setValue("League of Legends, Gym, Street Wear, Classic Novels, Guitar, Eating Out");


                        startActivity(new Intent(RegisterActivity.this,MessagingActivity.class));
                    }
                }
            });
        }
    }
}
