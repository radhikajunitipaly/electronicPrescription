package com.uta.eprescription.activities.authenticationMgr;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.firebase.database.DatabaseReference;

import com.google.firebase.database.FirebaseDatabase;
import com.uta.eprescription.R;
import com.uta.eprescription.activities.prescMgr.patient.PatientActivity;
import com.uta.eprescription.models.TestDatabase;
import com.uta.eprescription.activities.prescMgr.doctor.DoctorActivity;
import com.uta.eprescription.activities.prescMgr.pharmacist.PharmacistActivity;


public class MainActivity extends AppCompatActivity {

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseReference = FirebaseDatabase.getInstance().getReference("doctor");

        Button btn = (Button)findViewById(R.id.button_sign_in);
        final EditText txtname = (EditText)findViewById(R.id.User_id);
        final EditText pass = (EditText)findViewById(R.id.login_password);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
                if(txtname.getText().toString().equals("Doctor") && pass.getText().toString().equals("password")) {
                    startActivity(new Intent(MainActivity.this, DoctorActivity.class)); }
                else if(txtname.getText().toString().equals("Pharmacist") && pass.getText().toString().equals("password")){
                    startActivity(new Intent(MainActivity.this, PharmacistActivity.class));
                }
                else if(txtname.getText().toString().equals("Student") && pass.getText().toString().equals("password")) {
                    startActivity(new Intent(MainActivity.this, PatientActivity.class));
                }
                else{
                    AlertDialog alert = new AlertDialog.Builder(MainActivity.this).create();
                    alert.setTitle("Alert");
                    alert.setMessage("Incorrect Username/Password");
                    alert.setButton("OK", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub

                        }
                    });
                    alert.show();
                }
            }

        });
    }

    private void addUser(){
        /*String name = editTextUserName.getText().toString().trim();
        String type = editTextUserType.getText().toString().trim();
        String email = editTextUserEmail.getText().toString().trim();*/

        String name = "Radhika";
        String type = "Pharmacist";
        String email = "deepikas@pharmacists.com";


        if(!TextUtils.isEmpty(email)) {
            String id = databaseReference.push().getKey();
            System.out.println("User id being added is : "+id);
            TestDatabase user = new TestDatabase(id, name, email, type);
            System.out.println("User being added is : "+user.getId()+ " "+user.getUserName()+ " "+user.getUserEmailId());
            databaseReference.child(id).setValue(user);

            Toast.makeText(this, "User added finally", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "The email Id field cannot be empty!!", Toast.LENGTH_SHORT ).show();
        }

    }
}
