package com.uta.eprescription.activities.prescMgr.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.uta.eprescription.R;
import com.uta.eprescription.activities.prescMgr.doctor.CreatePrescriptionActivity;
import com.uta.eprescription.activities.prescMgr.doctor.DoctorActivity;
import com.uta.eprescription.dao.dbMgr.UserDao;
import com.uta.eprescription.models.Prescription;
import com.uta.eprescription.models.User;

public class ViewPrescriptionActivity extends AppCompatActivity {

    String userTypeToEdit;
    EditText startDateField;
    EditText endDateField;
    EditText medicineField;
    EditText countField;
    EditText powerField;
    Spinner statusField;
    Button editPrescriptionButton;
    Button saveButton;

    String status;
    String medicine;
    String startDate;
    String endDate;
    String count;
    String power;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_prescription);

        editPrescriptionButton = findViewById(R.id.button_edit_ps);
        startDateField = findViewById(R.id.textView_data_st_date);
        endDateField = findViewById(R.id.textView_data_exp_date);
        medicineField = findViewById(R.id.textView_dat_med);
        countField = findViewById(R.id.textView_dat_medcnt);
        powerField = findViewById(R.id.textView_dat_pwr);
        statusField = findViewById(R.id.spinner_vw_status);
        saveButton = findViewById( R.id.button_save );
        saveButton.setVisibility( View.INVISIBLE );

        getInComingIntent();

        TextView sid = findViewById( R.id.student_id_view );
        if(getIntent().hasExtra("userId")) {
            sid.setText(getIntent().getStringExtra("userId"));
        }

        TextView studentName = findViewById( R.id.student_name_view );
        if(getIntent().hasExtra("userName")) {
            studentName.setText(getIntent().getStringExtra("userName"));
        }

        TextView studentAge = findViewById( R.id.student_age_view );
        if(getIntent().hasExtra("userAge")) {
            studentAge.setText(getIntent().getStringExtra("userAge"));
        }



        editPrescriptionButton.setOnClickListener((view) -> {
            editPrescriptionButton.setVisibility( View.INVISIBLE );
            saveButton.setVisibility( View.VISIBLE );
                if (userTypeToEdit.contains("Doctor")) {
                    editPrescriptionButton.setEnabled(true);
                    statusField.setEnabled(true);
                    powerField.setEnabled(true);
                    countField.setEnabled(true);
                    medicineField.setEnabled(true);
                    endDateField.setEnabled(true);
                    startDateField.setEnabled(true);
                }
                if (userTypeToEdit.contains("Pharmacist") && status.equals("Valid")) {
                    statusField.setEnabled(true);
                }
        });

        saveButton.setOnClickListener((view) -> {
            UserDao userDao = new UserDao();
            userDao.updatePrescription(getIntent().getStringExtra("userId"),
                    getIntent().getStringExtra("pid"), new Prescription(medicineField.getText().toString(),
                            powerField.getText().toString(), startDateField.getText().toString(),
                            endDateField.getText().toString(), countField.getText().toString(),
                            getIntent().getStringExtra("pid"),statusField.getSelectedItem().toString()));
            medicine = medicineField.getText().toString();
            startDate = startDateField.getText().toString();
            endDate = endDateField.getText().toString();
            count = countField.getText().toString();
            power = powerField.getText().toString();
            status = statusField.getSelectedItem().toString();
            displayPrescription();
            Toast.makeText(ViewPrescriptionActivity.this,"Prescription Saved",
                    Toast.LENGTH_LONG).show();
            Intent intent = new Intent(ViewPrescriptionActivity.this,
                    DoctorActivity.class);
            intent.putExtra("patientIdFromCreate", getIntent().getStringExtra("userId"));
            intent.putExtra("dobFromCreate", getIntent().getStringExtra("userDob"));
            startActivity(intent);
        });
    }

    private void getInComingIntent() {

        if(getIntent().hasExtra("userType") && getIntent().hasExtra("medicine") &&
                getIntent().hasExtra("startDate") &&
                getIntent().hasExtra("endDate") && getIntent().hasExtra("count") &&
                getIntent().hasExtra("power") && getIntent().hasExtra("status") ){

            medicine = getIntent().getStringExtra("medicine");
            startDate = getIntent().getStringExtra("startDate");
            endDate = getIntent().getStringExtra("endDate");
            count = getIntent().getStringExtra("count");
            power = getIntent().getStringExtra("power");
            status = getIntent().getStringExtra("status");

            userTypeToEdit = getIntent().getStringExtra("userType");

            displayPrescription();
        }
    }

    private void displayPrescription() {

        editPrescriptionButton.setVisibility( View.VISIBLE );
        saveButton.setVisibility( View.INVISIBLE );
        if(userTypeToEdit.contains("Pharmacist") || userTypeToEdit.contains("Doctor")) {
            editPrescriptionButton.setEnabled(true);
        } else if(userTypeToEdit.contains("Patient")) {
            editPrescriptionButton.setVisibility(View.INVISIBLE);
        }
        startDateField.setEnabled(false);
        endDateField.setEnabled(false);
        medicineField.setEnabled(false);
        countField.setEnabled(false);
        powerField.setEnabled(false);
        statusField.setEnabled(false);

        startDateField.setText(startDate);
        endDateField.setText(endDate);
        medicineField.setText(medicine);
        countField.setText(count);
        powerField.setText(power);
        int statusValue = -1;
        if("Medicine Dispatched".equals(status)){
            statusValue = 0;
        } else if("Valid".equals(status)){
            statusValue = 1;
        } else if("Invalid".equals(status)){
            statusValue = 2;
        } else if("Medicine Unavailable".equals(status)){
            statusValue = 3;
        }
        if(statusValue != -1)
            statusField.setSelection(statusValue);

    }
}
