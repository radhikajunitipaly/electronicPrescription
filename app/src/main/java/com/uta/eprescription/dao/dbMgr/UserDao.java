package com.uta.eprescription.dao.dbMgr;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uta.eprescription.models.User;

public class UserDao {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
    User user;

    public void addUser(User user) {
        if (!TextUtils.isEmpty(user.getUserId())) {
            databaseReference.child(user.getUserId()).setValue(user);
        } else {
            //uncomment below line when register User Activity is ready and pass it's context to this method while adding user
            // Toast.makeText(registerUserActivityContext, "The User Id field cannot be empty!!", Toast.LENGTH_SHORT ).show();
        }
    }

    public void updateUser(User user) {
        if (!TextUtils.isEmpty(user.getUserId())) {
            databaseReference.child(user.getUserId()).setValue(user);
        } else {
            //uncomment below line when Update User Activity is ready and pass it's context to this method while updating user
           // Toast.makeText(UpdateUserActivityContext, "The User Id field cannot be empty!!", Toast.LENGTH_SHORT ).show();
        }
    }

    public User getUser(final String userId) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.child(userId).getValue(User.class);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("UserListActivity", "Error occurred");
                // Do something about the error
                //uncomment below line when the Activities are ready and pass specific context to this method while getting user
                // Toast.makeText(specificActivity, "Error occurred while fetching the user", Toast.LENGTH_SHORT ).show();
            }
        });
        return user;
    }
}