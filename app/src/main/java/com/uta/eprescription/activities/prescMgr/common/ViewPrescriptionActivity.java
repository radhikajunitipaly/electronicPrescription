package com.uta.eprescription.activities.prescMgr.common;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Spinner;

import com.uta.eprescription.R;

public class ViewPrescription extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_prescription);
        Spinner spinner = (Spinner) findViewById(R.id.spinner_vw_status);
        spinner.setEnabled(false);
    }
}
