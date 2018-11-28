package com.uta.eprescription.activities.prescMgr.pharmacist;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.uta.eprescription.R;
import com.uta.eprescription.activities.prescMgr.doctor.DoctorActivity;
import com.uta.eprescription.dao.dbMgr.RecyclerViewAdapter;
import com.uta.eprescription.dao.dbMgr.UserDao;
import com.uta.eprescription.models.Prescription;

import java.util.ArrayList;

public class PharmacistActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    ArrayList<Prescription> prescriptionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacist_page);

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
                        recyclerViewAdapter = new RecyclerViewAdapter(
                                PharmacistActivity.this, prescriptionList, studentId.getText().toString());
                        recyclerView.setAdapter(recyclerViewAdapter);
                        recyclerView.addItemDecoration(new DividerItemDecoration(
                                PharmacistActivity.this, DividerItemDecoration.VERTICAL));
                        recyclerView.setLayoutManager(new LinearLayoutManager(PharmacistActivity.this));
                    }, studentId.getText().toString(), dob.getText().toString()
            );
        });
    }
}
