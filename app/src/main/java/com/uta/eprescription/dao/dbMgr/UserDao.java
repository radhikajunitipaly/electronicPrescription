package com.uta.eprescription.dao.dbMgr;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uta.eprescription.activities.authenticationMgr.AuthenticationCallback;
import com.uta.eprescription.activities.prescMgr.common.PrescriptionListCallback;
import com.uta.eprescription.models.Prescription;
import com.uta.eprescription.models.User;
import com.uta.eprescription.activities.prescMgr.doctor.PrescriptionCountCall;
import java.util.ArrayList;

public class UserDao {
    // for data persistence
    FirebaseDatabase database = Utils.getDatabase();
    DatabaseReference databaseReference = database.getReference( "Users" );

    boolean success;
    String userType;
    long countofchild;

    final ArrayList<Prescription> userPrescriptions = new ArrayList<>();

    public void addUser(User user) {
        if (!TextUtils.isEmpty( user.getUserId() )) {
            databaseReference.child( user.getUserId() ).setValue( user );
        } else {
            //uncomment below line when register User Activity is ready and pass it's context to this method while adding user
            // Toast.makeText(registerUserActivityContext, "The User Id field cannot be empty!!", Toast.LENGTH_SHORT ).show();
        }
    }

    public void verifyUserIdAndPassword(@NonNull final AuthenticationCallback<Boolean> finishedCallback,
                                        final String loginId, final String loginPassword) {
        databaseReference.keepSynced( true );
        databaseReference.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild( loginId )) {
                    DataSnapshot user = dataSnapshot.child( loginId );
                    if ((user.child( "password" ).getValue( String.class )).equals( loginPassword )) {
                        userType = user.child( "userType" ).getValue( String.class );
                        success = true;
                    }
                }
                finishedCallback.callback( success, userType );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        } );
    }

    public void getPrescriptionsOfUser(@NonNull final PrescriptionListCallback<ArrayList> finishedCallback,
                                       final String userId, final String dob) {
        databaseReference.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSnapshot.getChildren();
                if (dataSnapshot.hasChild( userId )) {
                    DataSnapshot userSnapshot = dataSnapshot.child( userId );
                    if ((userSnapshot.child( "DOB" ).getValue( String.class )).equals( dob )) {
                        DataSnapshot contentSnapshot = userSnapshot.child( "prescriptions" );
                        Iterable<DataSnapshot> prescriptionSnapshot = contentSnapshot.getChildren();
                        for (DataSnapshot prescription : prescriptionSnapshot) {
                            Prescription tempPrescription = prescription.getValue( Prescription.class );
                            userPrescriptions.add( tempPrescription );
                        }
                    }
                }
                finishedCallback.callback( userPrescriptions );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException(); // don't ignore errors
            }
        } );
    }

    public void getPrescriptionsOfUserCount(@NonNull final PrescriptionCountCall<Long> finishedCallback,
                                            final String userId) {
        databaseReference.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSnapshot.getChildren();
                if (dataSnapshot.hasChild( userId )) {
                    DataSnapshot userSnapshot = dataSnapshot.child( userId );
                    DataSnapshot contentSnapshot = userSnapshot.child( "prescriptions" );
                    long childcount = contentSnapshot.getChildrenCount();
                    countofchild = childcount;
                }
                finishedCallback.callback( countofchild );
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException(); // don't ignore errors
            }
        } );
    }
    public void addPrescription(String userId, String pid, Prescription prescription)
    {
        databaseReference.child( userId ).child( "prescriptions" ).child( pid ).setValue( prescription );
    }

}
    class Utils {
        private static FirebaseDatabase mDatabase;

        public static FirebaseDatabase getDatabase() {
            if (mDatabase == null) {
                mDatabase = FirebaseDatabase.getInstance();
                mDatabase.setPersistenceEnabled( true );
            }
            return mDatabase;
        }

    }
