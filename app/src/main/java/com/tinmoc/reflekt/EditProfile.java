package com.tinmoc.reflekt;

/**
 * Created by M3800 on 2/25/2018.
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class EditProfile extends AppCompatActivity {
    private DatabaseReference database;
    String suggestion;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mDatabaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        //initialize firebase database
        FirebaseApp.initializeApp(getApplicationContext());

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users").child(mUser.getUid());
        database = FirebaseDatabase.getInstance().getReference().child("AutoCompleteOptions");
        //Create a new ArrayAdapter with your context and the simple layout for the dropdown menu provided by Android
        final ArrayAdapter<String> autoComplete = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1);
        //Child the root before all the push() keys are found and add a ValueEventListener()
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                autoComplete.clear();
                //Basically, this says "For each DataSnapshot *Data* in dataSnapshot, do what's inside the method.
                for (DataSnapshot suggestionSnapshot : dataSnapshot.getChildren()){
                    //Get the suggestion by childing the key of the string you want to get.
                    suggestion = suggestionSnapshot.child("Interest").getValue(String.class);
                    //Add the retrieved string to the list
                    autoComplete.add(suggestion);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        AutoCompleteTextView ACTV = (AutoCompleteTextView)findViewById(R.id.autoCompleteTextView);
        //users have to have at least 1 character before the list starts showing suggestions
        ACTV.setThreshold(1);
        ACTV.setAdapter(autoComplete);
    }

    protected void backButtonClicked(View view)
    {
        startActivity(new Intent(EditProfile.this,Profile.class));
        finish();
    }
    //button press
    protected void aah(View view) {
        AutoCompleteTextView ACTV = (AutoCompleteTextView)findViewById(R.id.autoCompleteTextView);
        final String interestVal = ACTV.getText().toString().toLowerCase().trim();
        if(!TextUtils.isEmpty(interestVal))
        {
            database.child(interestVal).child("Interest").setValue(interestVal);
            ACTV.setText("");
        }
        Query q = mDatabaseUser;
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(addInterest(dataSnapshot.child("Interests").getValue().toString(),interestVal)){
                      mDatabaseUser.child("Interests").setValue(dataSnapshot.child("Interests").getValue().toString().toLowerCase()+", "+interestVal);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    private static boolean addInterest(String current, String addInt) {
        int i = current.toLowerCase().indexOf(addInt.toLowerCase());
        return i == -1;
    }

}