package com.uta.eprescription.activities.authenticationMgr;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.uta.eprescription.R;
import com.uta.eprescription.activities.prescMgr.patient.PatientActivity;
import com.uta.eprescription.dao.dbMgr.UserDao;
import com.uta.eprescription.activities.prescMgr.doctor.DoctorActivity;
import com.uta.eprescription.activities.prescMgr.pharmacist.PharmacistActivity;
import com.uta.eprescription.models.User;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button signInButton = (Button) findViewById(R.id.button_sign_in);
        Button registerButton = (Button) findViewById(R.id.button2);
        final EditText userId = (EditText) findViewById(R.id.User_id);
        final EditText password = (EditText) findViewById(R.id.login_password);
        signInButton.setOnClickListener(new View.OnClickListener() {
            UserDao userDao = new UserDao();
            User user = userDao.getUser("sxt8113");
            @Override
            public void onClick(View v) {
                if(userId.getText().toString().equals("Doctor") && password.getText().toString().equals("password")) {
                    startActivity(new Intent(MainActivity.this, DoctorActivity.class)); }
                else if(userId.getText().toString().equals("Pharmacist") && password.getText().toString().equals("password")){
                    startActivity(new Intent(MainActivity.this, PharmacistActivity.class));
                }
                else if(userId.getText().toString().equals("Student") && password.getText().toString().equals("password")) {
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
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterUserActivity.class));
            }
        });
    }
}
