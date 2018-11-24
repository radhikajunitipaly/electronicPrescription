package com.uta.eprescription.activities.prescMgr.common;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Spinner;
import android.widget.TextView;

import com.uta.eprescription.R;

public class ViewPrescriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_prescription);
        Spinner spinner = (Spinner) findViewById(R.id.spinner_vw_status);
        spinner.setEnabled(false);
    }

    private void getInComingIntent() {
        if(getIntent().hasExtra("medicine") && getIntent().hasExtra("startDate") &&
                getIntent().hasExtra("endDate") && getIntent().hasExtra("count") &&
                getIntent().hasExtra("power") ){

            String medicine = getIntent().getStringExtra("medicine");
            String startDate = getIntent().getStringExtra("startDate");
            String endDate = getIntent().getStringExtra("endDate");
            String count = getIntent().getStringExtra("count");
            String power = getIntent().getStringExtra("power");

            displayPrescription(medicine, startDate, endDate, count, power);

        }
    }

    private void displayPrescription(String medicine, String startDate, String endDate,
                                     String count, String power) {
        TextView startDateField = findViewById(R.id.textView_data_st_date);
        TextView endDateField = findViewById(R.id.textView_data_exp_date);
        TextView medicineField = findViewById(R.id.textView_dat_med);
        TextView countField = findViewById(R.id.textView_dat_medcnt);
        TextView powerField = findViewById(R.id.textView_dat_pwr);

        startDateField.setText(medicine);
        endDateField.setText(startDate);
        medicineField.setText(endDate);
        countField.setText(count);
        powerField.setText(power);

    }
}
