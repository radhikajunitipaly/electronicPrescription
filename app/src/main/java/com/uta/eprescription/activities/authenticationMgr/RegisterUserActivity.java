package com.uta.eprescription.activities.authenticationMgr;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.uta.eprescription.R;
import com.uta.eprescription.activities.prescMgr.common.ViewPrescriptionActivity;
import com.uta.eprescription.activities.prescMgr.doctor.CreatePrescriptionActivity;
import com.uta.eprescription.activities.prescMgr.doctor.DoctorActivity;
import com.uta.eprescription.dao.dbMgr.UserDao;
import com.uta.eprescription.models.Prescription;
import com.uta.eprescription.models.User;

import java.util.ArrayList;
import java.util.Calendar;

public class RegisterUserActivity extends AppCompatActivity {
    Calendar c;
    DatePickerDialog dp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        Button Btn_register = (Button)findViewById(R.id.registerButton);
        Button Btn_cancel = (Button)findViewById( R.id.Cancel );
        final EditText DOB = (EditText)findViewById( R.id.DOB );

        Btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText UserID = (EditText)findViewById( R.id.UserIDText );
                final  EditText pass = (EditText)findViewById( R.id.UserPassword );
                final EditText LastName = (EditText)findViewById( R.id.Lname );
                final EditText FirstName = (EditText)findViewById( R.id.Fname );
                final EditText DOB = (EditText)findViewById( R.id.DOB );
                final EditText phone = (EditText)findViewById( R.id.Phone ) ;
                final RadioGroup rdgrp = (RadioGroup)findViewById( R.id.RadioGrp );
                int selectedId = rdgrp.getCheckedRadioButtonId();
                final  RadioButton selectedRadio = (RadioButton)findViewById( selectedId);
                final ArrayList<Prescription> prescriptionArrayList = new ArrayList<>();

               User user = new User(UserID.getText().toString(),pass.getText().toString(),
                       phone.getText().toString(),FirstName.getText().toString(),
                       LastName.getText().toString(),selectedRadio.getText().toString(),
                       DOB.getText().toString(), prescriptionArrayList);
               UserDao userDao = new UserDao();
               userDao.addUser(user);
                showCustomAlert();
                startActivity(new Intent(RegisterUserActivity.this,
                        MainActivity.class));
            }
        });
        DOB.setOnClickListener((view) -> {
                c = Calendar.getInstance();
                int day = c.get( Calendar.DAY_OF_MONTH );
                int mon = c.get( Calendar.MONTH );
                int yr = c.get( Calendar.YEAR );
                dp = new DatePickerDialog( RegisterUserActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int myear, int month, int d) {
                        month=month+1;
                        DOB.setText( month + "/" + d + "/" + myear );

                    }
                },yr,mon,day );
                dp.show();
        });

        Btn_cancel.setOnClickListener((view) -> {
                final EditText UserID = (EditText)findViewById( R.id.UserIDText );
                final  EditText pass = (EditText)findViewById( R.id.UserPassword );
                final EditText LastName = (EditText)findViewById( R.id.Lname );
                final EditText FirstName = (EditText)findViewById( R.id.Fname );
                final EditText dob = (EditText)findViewById( R.id.DOB );
                final EditText phone = (EditText)findViewById( R.id.Phone ) ;
                final RadioGroup rdgrp = (RadioGroup)findViewById( R.id.RadioGrp );
                int selectedId = rdgrp.getCheckedRadioButtonId();
                final  RadioButton selectedRadio = (RadioButton)findViewById( selectedId);
                UserID.setText("");
                pass.setText("");
                LastName.setText("");
                FirstName.setText("");
                dob.setText("");
                phone.setText("");
                rdgrp.clearCheck();
        });


    }

    public void showCustomAlert()
    {
        Context context = RegisterUserActivity.this;
        // Create layout inflator object to inflate toast.xml file
        LayoutInflater inflater = getLayoutInflater();

        // Call toast.xml file for toast layout
        View toast = inflater.inflate(R.layout.custom_toast_register, null);

        Toast toast1 = new Toast(context);

        // Set layout to toast
        toast1.setView(toast);
        //toast1.makeText(context, "Prescription status saved successfully", Toast.LENGTH_LONG);
        toast1.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM,
                0, 40);
        toast1.setDuration(Toast.LENGTH_LONG);
        toast1.show();
    }
}
