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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button signInButton = (Button) findViewById(R.id.button_sign_in);
        Button main_reg_button = (Button) findViewById( R.id.main_reg_button );
        final EditText userIdField = (EditText) findViewById(R.id.User_id);
        final EditText passwordField = (EditText) findViewById(R.id.login_password);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserDao userDao = new UserDao();
                userDao.verifyUserIdAndPassword(new AuthenticationCallback<Boolean>() {
                    @Override
                    public void callback(boolean success, String userType) {
                        if (success) {
                            switch (userType) {
                                case "doctor":
                                    startActivity(new Intent(MainActivity.this,
                                            DoctorActivity.class));
                                    break;
                                case "pharmacist":
                                    startActivity(new Intent(MainActivity.this,
                                            PharmacistActivity.class));
                                    break;
                                case "student":
                                    startActivity(new Intent(MainActivity.this,
                                            PatientActivity.class));
                                    break;
                                default:
                                    AlertDialog alert = new AlertDialog.Builder(
                                            MainActivity.this).create();
                                    alert.setTitle("Alert");
                                    alert.setMessage("Error occurred while fetching user, " +
                                            "please contact administrator");
                                    alert.setButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    });
                                    alert.show();
                            }
                        } else {
                            AlertDialog alert = new AlertDialog.Builder(
                                    MainActivity.this).create();
                            alert.setTitle("Alert");
                            alert.setMessage("Incorrect userId/password");
                            alert.setButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                            alert.show();
                        }
                    }
                }, userIdField.getText().toString(), passwordField.getText().toString());
            }
        });
        main_reg_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,
                        RegisterUserActivity.class));
            }
        });

    }
}

