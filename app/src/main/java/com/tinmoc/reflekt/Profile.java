package com.tinmoc.reflekt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


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

        setProfile(currentuser,currentuser);
    }

    /**
     * set up activity_profile
     * @param user
     * @param selfuser yourself
     */
    private void setProfile(FirebaseUser user, FirebaseUser selfuser){
        String temp = user.getDisplayName();

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());

        TextView name = (TextView)findViewById(R.id.profileName);
        name.setText(temp);

        TextView ageNgender = (TextView)findViewById(R.id.ageNgender);

        //if view your own profile, delta dne
        TextView distance = (TextView)findViewById(R.id.distance);
        if(user == selfuser){
            distance.setText("");
        }

        TextView bio = (TextView)findViewById(R.id.bio);

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

