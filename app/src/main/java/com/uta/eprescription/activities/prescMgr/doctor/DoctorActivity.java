package com.uta.eprescription.activities.prescMgr.doctor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.uta.eprescription.R;
import com.uta.eprescription.dao.dbMgr.RecyclerViewAdapter;
import com.uta.eprescription.dao.dbMgr.UserDao;
import com.uta.eprescription.models.Prescription;

import java.util.ArrayList;

public class DoctorActivity extends AppCompatActivity {
    public RelativeLayout relativeLayout;

    public RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    ArrayList<Prescription> prescriptionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_page);
        relativeLayout= findViewById(R.id.main_layout_doctor);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final EditText studentId = (EditText) findViewById(R.id.stid_ip_txt);
        final EditText dob = (EditText) findViewById(R.id.dob_ip_txt);

        Button prescriptionButton = (Button) findViewById(R.id.button_srch);
        prescriptionList = new ArrayList<>();

        prescriptionButton.setOnClickListener((view) -> {
                UserDao userDao = new UserDao();
                userDao.getPrescriptionsOfUser(
                        (ArrayList prescriptionListTemp) -> {
                                prescriptionList = prescriptionListTemp;
                                recyclerView = findViewById(R.id.recycler_view);
                                recyclerViewAdapter = new RecyclerViewAdapter(DoctorActivity.this, prescriptionList);
                                recyclerView.setAdapter(recyclerViewAdapter);
                                recyclerView.addItemDecoration(new DividerItemDecoration(DoctorActivity.this, DividerItemDecoration.VERTICAL));
                                recyclerView.setLayoutManager(new LinearLayoutManager(DoctorActivity.this));
                        }, studentId.getText().toString(), dob.getText().toString()
                );
        });
    }
}
