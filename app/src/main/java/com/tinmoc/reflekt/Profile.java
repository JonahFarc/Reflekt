package com.tinmoc.reflekt;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //CHANGE LATER TO PROFILES OF MATCHED USERS
        FirebaseUser currentuser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentuser == null) {
            Log.d("LoginError","No user in profile");
            return;
        }

        setProfile(currentuser);

        FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Profile.this,EditProfile.class));
                finish();
            }
        });
    }

    /**
     * set up activity_profile
     * @param selfuser yourself
     */
    private void setProfile(FirebaseUser selfuser){

        final DatabaseReference currentUser = FirebaseDatabase.getInstance().getReference().child("Users").child(selfuser.getUid());
        Query q = currentUser;
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                TextView ageNgender = (TextView)findViewById(R.id.ageNgender);
                ageNgender.setText(dataSnapshot.child("Age").getValue().toString() + ", " + dataSnapshot.child("Gender").getValue().toString());

                TextView bio = (TextView)findViewById(R.id.bio);
                bio.setText(dataSnapshot.child("Bio").getValue().toString());

                TextView name = (TextView)findViewById(R.id.profileName);
                name.setText(dataSnapshot.child("Name").getValue().toString());

                TextView distance = (TextView)findViewById(R.id.distance);
                String temp = Double.toString(distance(30.1943957, -95.5052447, 30.180115, -95.58738699999998)*0.621371);
                temp = temp.substring(0,temp.indexOf(".")+3) + "miles";
                distance.setText(temp);

                TextView interest = (TextView)findViewById(R.id.interests);
                interest.setText(dataSnapshot.child("Interests").getValue().toString());

                TextView looking = (TextView)findViewById(R.id.lookingfor);
                looking.setText(dataSnapshot.child("Target_Gender").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void message(View view)
    {
        startActivity(new Intent(Profile.this, MessagingActivity.class));
        finish();
    }
    // Returns distance in (km)
    private static double distance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double a = Math.pow(Math.sin(dLat / 2),2) + Math.pow(Math.sin(dLon / 2),2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return 6372.8 * c;	//radius of earth at sealevel && lattitude 30.1943957(houston,texas) * c
    }
}

