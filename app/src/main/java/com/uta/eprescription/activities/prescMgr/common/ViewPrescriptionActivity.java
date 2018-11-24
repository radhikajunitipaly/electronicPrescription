package com.uta.eprescription.activities.prescMgr.common;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.uta.eprescription.R;

public class ViewPrescriptionActivity extends AppCompatActivity {

    String userTypeToEdit;
    TextView startDateField;
    TextView endDateField;
    TextView medicineField;
    TextView countField;
    TextView powerField;
    Spinner statusField;
    Button editPrescriptionButton;

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

        getInComingIntent();

        editPrescriptionButton.setOnClickListener((view) -> {
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

        if(userTypeToEdit.contains("Pharmacist") || userTypeToEdit.contains("Doctor")) {
            editPrescriptionButton.setEnabled(true);
        } else if(userTypeToEdit.contains("Patient")) {
            editPrescriptionButton.setEnabled(false);
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
