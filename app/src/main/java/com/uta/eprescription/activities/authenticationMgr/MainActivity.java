package com.uta.eprescription.activities.authenticationMgr;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.uta.eprescription.R;
import com.uta.eprescription.activities.prescMgr.common.ViewPrescriptionActivity;
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

        signInButton.setOnClickListener((view) -> {
                UserDao userDao = new UserDao();
                if(userIdField.getText().toString().equals( "" )||passwordField.getText().toString().equals( "" ))
                {
                    AlertDialog alert = new AlertDialog.Builder(
                            MainActivity.this).create();
                    alert.setTitle("Alert");
                    alert.setMessage("Please Enter UserID/Password");
                    alert.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    alert.show();

                }
                else{
                    userDao.verifyUserIdAndPassword((boolean success, String userType, String userName) -> {
                        if (success) {
                            Intent intent;
                            switch (userType) {
                                case "Doctor":
                                    intent = new Intent(MainActivity.this,
                                            DoctorActivity.class);
                                    intent.putExtra("userNameForWelcome", userName);
                                    startActivity(intent);
                                    break;
                                case "Pharmacist":
                                    intent = new Intent(MainActivity.this,
                                            PharmacistActivity.class);
                                    intent.putExtra("userNameForWelcome", userName);
                                    startActivity(intent);
                                    break;
                                case "Student":
                                    intent = new Intent(MainActivity.this,
                                            PatientActivity.class);
                                    intent.putExtra("userNameForWelcome", userName);
                                    startActivity(intent);
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
                    }, userIdField.getText().toString(), passwordField.getText().toString());
                }

        });

        main_reg_button.setOnClickListener((view) -> {
                startActivity(new Intent(MainActivity.this,
                        RegisterUserActivity.class));
        });

    }
}

