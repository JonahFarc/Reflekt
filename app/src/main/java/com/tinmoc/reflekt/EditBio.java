package com.tinmoc.reflekt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditBio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_bio);

        FirebaseUser currentuser = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users").child(currentuser.getUid());

        //users bio
        String bio = "";
        //Edit Text
        EditText edit = (EditText)findViewById(R.id.bio);
        edit.append(bio);

        Button clickButton = (Button) findViewById(R.id.button3);
        clickButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText edit = (EditText)findViewById(R.id.bio);
                // How do I upload new bio of the "currentuser" to database
                edit.getText().toString();

            }
        });
    }


}
