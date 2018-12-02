package com.uta.eprescription.activities.prescMgr.doctor;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uta.eprescription.R;
import com.uta.eprescription.activities.authenticationMgr.MainActivity;
import com.uta.eprescription.activities.authenticationMgr.RegisterUserActivity;
import com.uta.eprescription.activities.prescMgr.patient.PatientActivity;
import com.uta.eprescription.activities.prescMgr.pharmacist.PharmacistActivity;
import com.uta.eprescription.dao.dbMgr.RecyclerViewAdapter;
import com.uta.eprescription.dao.dbMgr.UserDao;
import com.uta.eprescription.models.Prescription;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

public class DoctorActivity extends AppCompatActivity {
    public RelativeLayout relativeLayout;

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
        setContentView(R.layout.activity_doctor_page);
        relativeLayout= findViewById(R.id.main_layout_doctor);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Button CreateNewPresc = (Button)findViewById( R.id.button_create );
        ImageButton logout = (ImageButton)findViewById( R.id.imageButton );

        final TextView userNameForWelcome = findViewById(R.id.dynam_doc);

        final EditText studentId = (EditText) findViewById(R.id.stid_ip_txt);
        final EditText dob = (EditText) findViewById(R.id.dob_ip_txt);

        TextView studentName = findViewById(R.id.student_name_view);
        TextView studentAge = findViewById(R.id.student_age_view);
        studentName.setVisibility( View.INVISIBLE );
        studentAge.setVisibility( View.INVISIBLE );

        TextView studentNameHeading = findViewById(R.id.student_name_heading);
        TextView studentAgeHeading = findViewById(R.id.student_age_heading);
        studentNameHeading.setVisibility( View.INVISIBLE );
        studentAgeHeading.setVisibility( View.INVISIBLE );

        Button prescriptionButton = (Button) findViewById(R.id.button_srch);
        prescriptionList = new ArrayList<>();

        if(getIntent().hasExtra("userNameForWelcome")) {
            userNameForWelcome.setText(getIntent().getStringExtra("userNameForWelcome"));
        }

        Button reset = findViewById(R.id.button_cnl);
        reset.setOnClickListener((view) -> {
            studentId.setText("");
            dob.setText("");
        });


        prescriptionButton.setOnClickListener((view) -> {
            if(studentId.getText().toString().equals( "" )||dob.getText().toString().equals( "" ))
            {
                AlertDialog alert = new AlertDialog.Builder(
                        DoctorActivity.this ).create();
                alert.setTitle( "Alert" );
                alert.setMessage( "Please enter Student ID or Date of Birth" );
                alert.setButton( "OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                } );
                alert.show();

            }
            else {
                UserDao userDao = new UserDao();
                userDao.getPrescriptionsOfUser(
                        (ArrayList prescriptionListTemp, Map patientDetails, boolean success) -> {
                            if (success) {
                                patientDisplayName = patientDetails.get("patientName").toString();
                                String patientDob = patientDetails.get("patientDob").toString();
                                patientDisplayAge = String.valueOf(2018 - Integer.parseInt(
                                        patientDob.substring(patientDob.length() - 4)));
                                prescriptionList = prescriptionListTemp;
                                recyclerView = findViewById(R.id.recycler_view);
                                recyclerViewAdapter = new RecyclerViewAdapter(
                                        DoctorActivity.this, prescriptionList,
                                        studentId.getText().toString(), patientDisplayName, patientDisplayAge);
                                recyclerView.setAdapter(recyclerViewAdapter);
                                recyclerView.addItemDecoration(new DividerItemDecoration(DoctorActivity.this,
                                        DividerItemDecoration.VERTICAL));
                                recyclerView.setLayoutManager(new LinearLayoutManager(DoctorActivity.this));
                                studentName.setText(patientDisplayName);
                                studentAge.setText(patientDisplayAge);
                            } else {
                                    AlertDialog alert = new AlertDialog.Builder(
                                            DoctorActivity.this ).create();
                                    alert.setTitle( "Alert" );
                                    alert.setMessage( "Please enter valid Student ID or Date of Birth" );
                                    alert.setButton( "OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    } );
                                    alert.show();
                                }
                            }, studentId.getText().toString(), dob.getText().toString()
                    );
                    studentNameHeading.setVisibility( View.VISIBLE );
                    studentAgeHeading.setVisibility( View.VISIBLE );
                    studentName.setVisibility( View.VISIBLE );
                    studentAge.setVisibility( View.VISIBLE );
                }
        });
        dob.setOnClickListener((view ) -> {
                c = Calendar.getInstance();
                int day = c.get( Calendar.DAY_OF_MONTH );
                int mon = c.get( Calendar.MONTH );
                int yr = c.get( Calendar.YEAR );
                dp = new DatePickerDialog( DoctorActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int myear, int month, int d) {
                        month=month+1;
                        dob.setText( month + "/" + d + "/" + myear );

                    }
                },yr,mon,day);
                dp.show();
        });

        CreateNewPresc.setOnClickListener((view) -> {
            Intent intent = new Intent(DoctorActivity.this,
                    CreatePrescriptionActivity.class);
            intent.putExtra("patientId", studentId.getText().toString());
            intent.putExtra("patientName", patientDisplayName);
            intent.putExtra("patientAge", patientDisplayAge);
            startActivity(intent);
        });
        logout.setOnClickListener((view) -> {
            startActivity(new Intent(DoctorActivity.this,
                    MainActivity.class));
        });
    }
}
