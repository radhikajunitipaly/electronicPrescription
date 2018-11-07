package com.uta.eprescription.dao.dbMgr;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uta.eprescription.activities.authenticationMgr.AuthenticationCallback;
import com.uta.eprescription.models.User;

public class UserDao {
    // for data persistence
    FirebaseDatabase database = Utils.getDatabase();
    DatabaseReference databaseReference = database.getReference("Users");
    boolean success;
    String userType;

    public void addUser(User user) {
        if (!TextUtils.isEmpty(user.getUserId())) {
            databaseReference.child("user").setValue(user);
        } else {
            //uncomment below line when register User Activity is ready and pass it's context to this method while adding user
            // Toast.makeText(registerUserActivityContext, "The User Id field cannot be empty!!", Toast.LENGTH_SHORT ).show();
        }
    }

    public void verifyUserIdAndPassword(@NonNull final AuthenticationCallback<Boolean> finishedCallback,
                                        final String loginId, final String loginPassword){
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if( dataSnapshot.hasChild(loginId) ) {
                    DataSnapshot user = dataSnapshot.child(loginId);
                    if((user.child("password").getValue(String.class)).equals(loginPassword)) {
                        userType = user.child("userType").getValue(String.class);
                        success = true;
                    }
                }
                finishedCallback.callback(success, userType);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

class Utils {
    private static FirebaseDatabase mDatabase;

    public static FirebaseDatabase getDatabase() {
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance();
            mDatabase.setPersistenceEnabled(true);
        }
        return mDatabase;
    }

}