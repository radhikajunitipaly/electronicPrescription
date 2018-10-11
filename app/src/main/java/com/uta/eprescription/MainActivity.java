package com.uta.eprescription;

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
import com.uta.eprescription.Doctor_page;
import com.uta.eprescription.Pharmacist_page;
import com.uta.eprescription.authenticationMgr.TestDatabase;
import com.uta.eprescription.authenticationMgr.User;


public class MainActivity extends AppCompatActivity {

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseReference = FirebaseDatabase.getInstance().getReference("testsDatabase");

        Button btn = (Button)findViewById(R.id.button_sign_in);
        final EditText txtname = (EditText)findViewById(R.id.User_id);
        final EditText pass = (EditText)findViewById(R.id.login_password);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
                if(txtname.getText().toString().equals("doctor") && pass.getText().toString().equals("password")) {
                    startActivity(new Intent(MainActivity.this, Doctor_page.class)); }
                else if(txtname.getText().toString().equals("pharmacist") && pass.getText().toString().equals("password")){
                    startActivity(new Intent(MainActivity.this, Pharmacist_page.class));
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
        String type = "Doctor";
        String email = "radhikaemail@doctor.com";


        if(!TextUtils.isEmpty(email)) {
            String id = databaseReference.push().getKey();

            TestDatabase user = new TestDatabase(id, name, email, type);

            databaseReference.child(id).setValue(user);

            Toast.makeText(this, "User added", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "The email Id field cannot be empty!!", Toast.LENGTH_SHORT ).show();
        }

    }
}
