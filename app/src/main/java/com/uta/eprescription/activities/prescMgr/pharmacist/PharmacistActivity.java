package com.uta.eprescription.activities.prescMgr.pharmacist;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uta.eprescription.R;
import com.uta.eprescription.activities.authenticationMgr.MainActivity;
import com.uta.eprescription.activities.prescMgr.doctor.CreatePrescriptionActivity;
import com.uta.eprescription.activities.prescMgr.doctor.DoctorActivity;
import com.uta.eprescription.dao.dbMgr.RecyclerViewAdapter;
import com.uta.eprescription.dao.dbMgr.UserDao;
import com.uta.eprescription.models.Prescription;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

public class PharmacistActivity extends AppCompatActivity {
    Calendar c;
    DatePickerDialog dp;

    public RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    ArrayList<Prescription> prescriptionList;
    String patientDisplayName;
    String patientDisplayAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacist_page);
        ImageButton logout = (ImageButton)findViewById( R.id.imageButton );

        final TextView userNameForWelcome = findViewById(R.id.dynam_doc);

        final EditText studentId = (EditText) findViewById(R.id.stid_ip_txt);
        final EditText dob = (EditText) findViewById(R.id.dob_ip_txt);

        Button prescriptionButton = (Button) findViewById(R.id.button_srch);
        prescriptionList = new ArrayList<>();

        if(getIntent().hasExtra("userNameForWelcome")) {
            userNameForWelcome.setText(getIntent().getStringExtra("userNameForWelcome"));
        }

        TextView studentName = findViewById(R.id.student_name_view);
        TextView studentAge = findViewById(R.id.student_age_view);
        studentName.setVisibility( View.INVISIBLE );
        studentAge.setVisibility( View.INVISIBLE );

        TextView studentNameHeading = findViewById(R.id.student_name_heading);
        TextView studentAgeHeading = findViewById(R.id.student_age_heading);
        studentNameHeading.setVisibility( View.INVISIBLE );
        studentAgeHeading.setVisibility( View.INVISIBLE );

        if(getIntent().hasExtra("patientIdFromCreate") && getIntent().hasExtra("dobFromCreate")) {
            studentId.setText(getIntent().getStringExtra("patientIdFromCreate"));
            dob.setText(getIntent().getStringExtra("dobFromCreate"));
            fetchPatient(studentId, dob, studentName, studentAge, studentNameHeading, studentAgeHeading);
        }

        Button reset = findViewById(R.id.button_cnl);
        reset.setOnClickListener((view) -> {
            studentId.setText("");
            dob.setText("");
        });
        boolean success;

        prescriptionButton.setOnClickListener((view) -> {
            fetchPatient(studentId, dob, studentName, studentAge, studentNameHeading, studentAgeHeading);

        });
        dob.setOnClickListener((view ) -> {
                c = Calendar.getInstance();
                int day = c.get( Calendar.DAY_OF_MONTH );
                int mon = c.get( Calendar.MONTH );
                int yr = c.get( Calendar.YEAR );
                dp = new DatePickerDialog( PharmacistActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int myear, int month, int d) {
                        month=month+1;
                        dob.setText( month + "/" + d + "/" + myear );

                    }
                },yr,mon,day );
                dp.show();
        });

        logout.setOnClickListener((view) -> startActivity(new Intent(PharmacistActivity.this,
                MainActivity.class)));
    }

    private void fetchPatient(EditText studentId, EditText dob, TextView studentName, TextView studentAge, TextView studentNameHeading, TextView studentAgeHeading) {
        if(studentId.getText().toString().equals( "" )||dob.getText().toString().equals( "" ))
        {
            AlertDialog alert = new AlertDialog.Builder(
                    PharmacistActivity.this).create();
            alert.setTitle("Alert");
            alert.setMessage("Please enter Student ID or Date of Birth");
            alert.setButton("OK", (dialog, which) -> {
            });
            alert.show();
        }
        else
        {
            UserDao userDao = new UserDao();
            userDao.getPrescriptionsOfUser(
                    (ArrayList prescriptionListTemp, Map patientDetails, boolean sucess) -> {
                        if(sucess){
                            patientDisplayName = patientDetails.get("patientName").toString();
                            String patientDob = patientDetails.get("patientDob").toString();
                            patientDisplayAge = String.valueOf(2018 - Integer.parseInt(
                                    patientDob.substring(patientDob.length() - 4)));
                            prescriptionList = prescriptionListTemp;
                            recyclerView = findViewById(R.id.recycler_view);
                            recyclerViewAdapter = new RecyclerViewAdapter(
                                    PharmacistActivity.this, prescriptionList,
                                    studentId.getText().toString(), patientDisplayName, patientDisplayAge,
                                    patientDob, getIntent().getStringExtra("userNameForWelcome"));
                            recyclerView.setAdapter(recyclerViewAdapter);
                            recyclerView.addItemDecoration(new DividerItemDecoration(
                                    PharmacistActivity.this, DividerItemDecoration.VERTICAL));
                            recyclerView.setLayoutManager(new LinearLayoutManager(PharmacistActivity.this));
                            studentName.setText(patientDisplayName);
                            studentAge.setText(patientDisplayAge);
                        }
                        else
                        {
                            AlertDialog alert = new AlertDialog.Builder(
                                    PharmacistActivity.this).create();
                            alert.setTitle("Alert");
                            alert.setMessage("Please enter Valid student ID or Date of Birth");
                            alert.setButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                            alert.show();
                        }

                    },studentId.getText().toString(), dob.getText().toString()
            );
            studentNameHeading.setVisibility( View.VISIBLE );
            studentAgeHeading.setVisibility( View.VISIBLE );
            studentName.setVisibility(View.VISIBLE);
            studentAge.setVisibility(View.VISIBLE);

        }
    }
}
