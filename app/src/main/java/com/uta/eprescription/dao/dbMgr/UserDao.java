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
import java.util.HashMap;
import java.util.Map;

public class UserDao {
    // for data persistence
    FirebaseDatabase database = Utils.getDatabase();
    DatabaseReference databaseReference = database.getReference( "Users" );

    boolean success;
    String userType;
    long countofchild;
    String userName;

    final ArrayList<Prescription> userPrescriptions = new ArrayList<>();
    final Map<String, String> patientDetails= new HashMap<>();


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
                        userName = user.child("firstName").getValue( String.class ) + " "
                                +user.child("lastName").getValue( String.class );
                        success = true;
                    }
                }
                finishedCallback.callback( success, userType, userName );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        } );
    }

    public void getPrescriptionsOfUser(@NonNull final PrescriptionListCallback<ArrayList> finishedCallback,
                                       final String userId, final String dob) {
        databaseReference.keepSynced( true );
        databaseReference.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSnapshot.getChildren();
                if (dataSnapshot.hasChild( userId )) {
                    DataSnapshot userSnapshot = dataSnapshot.child( userId );
                    //if ((userSnapshot.child( "dob" ).getValue( String.class )).equals( dob )) {
                        patientDetails.put("patientName", userSnapshot.child("firstName").getValue( String.class ) + " "
                                +userSnapshot.child("lastName").getValue( String.class ));
                        patientDetails.put("patientDob", userSnapshot.child( "dob" ).getValue( String.class ));
                        DataSnapshot contentSnapshot = userSnapshot.child( "prescriptions" );
                        Iterable<DataSnapshot> prescriptionSnapshot = contentSnapshot.getChildren();
                        for (DataSnapshot prescription : prescriptionSnapshot) {
                            Prescription tempPrescription = prescription.getValue( Prescription.class );
                            userPrescriptions.add( tempPrescription );
                            success=true;
                        }
                    //}
                }
                else
                {
                    success = false;
                }
                finishedCallback.callback(userPrescriptions , patientDetails,success);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException(); // don't ignore errors
            }
        } );
    }

    public void getPrescriptionsOfUserCount(@NonNull final PrescriptionCountCall<Long> finishedCallback,
                                            final String userId) {
        databaseReference.keepSynced( true );
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

    public void updatePrescription(String userId, String pid, Prescription prescription) {
        databaseReference.keepSynced( true );
        databaseReference.child( userId ).child( "prescriptions" ).child( pid ).setValue( prescription );
    }

    public void addPrescription(String userId, String pid, Prescription prescription) {
        databaseReference.keepSynced( true );
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