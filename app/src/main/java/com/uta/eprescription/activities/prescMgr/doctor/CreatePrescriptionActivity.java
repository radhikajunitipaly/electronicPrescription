package com.uta.eprescription.activities.prescMgr.doctor;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.uta.eprescription.R;
import com.uta.eprescription.activities.authenticationMgr.MainActivity;
import com.uta.eprescription.activities.authenticationMgr.RegisterUserActivity;
import com.uta.eprescription.dao.dbMgr.UserDao;
import com.uta.eprescription.models.Prescription;

import java.net.FileNameMap;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CreatePrescriptionActivity extends AppCompatActivity {
    String p = "Prescription ";
    String pidNew;
    Calendar c;
    DatePickerDialog dp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_create_prescription );
        Button btn_save = findViewById( R.id.button_save ) ;
        final EditText date = (EditText)findViewById( R.id.sdate );
        final EditText edate = (EditText)findViewById( R.id.edate );
        edate.setOnClickListener((view) -> {
                c = Calendar.getInstance();
                int day = c.get( Calendar.DAY_OF_MONTH );
                int mon = c.get( Calendar.MONTH );
                int yr = c.get( Calendar.YEAR );
                dp = new DatePickerDialog( CreatePrescriptionActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int myear, int month, int d) {
                        month = month+1;
                        String months,days;
                        if(d<10) { days = "0"+d;}else{days = ""+d;}
                        if(month<10) { months = "0"+month;}else{months = ""+month;}
                        edate.setText( months +"/" + days + "/" + myear );

                    }
                },yr,mon,day );
                dp.show();
        });

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String formattedDate = df.format(c);
        date.setText( formattedDate );

        TextView sid = findViewById( R.id.student_id_view );
        if(getIntent().hasExtra("patientId")) {
            sid.setText(getIntent().getStringExtra("patientId"));
        }

        TextView studentName = findViewById( R.id.student_name_view );
        if(getIntent().hasExtra("patientName")) {
            studentName.setText(getIntent().getStringExtra("patientName"));
        }

        TextView studentAge = findViewById( R.id.student_age_view );
        if(getIntent().hasExtra("patientAge")) {
            studentAge.setText(getIntent().getStringExtra("patientAge"));
        }


        btn_save.setOnClickListener((view) -> {
            final EditText med  =   (EditText)findViewById( R.id.Med );
            final EditText pow  = (EditText)findViewById( R.id.Power );
            final EditText countmed = (EditText)findViewById( R.id.count ) ;

            date.setText( formattedDate );

            UserDao dbo = new UserDao();
            dbo.getPrescriptionsOfUserCount( (Long count) -> {
                count = count+1;
                pidNew = p+count;
                Prescription newPrescription = new Prescription(med.getText().toString(),pow.getText().toString(),date.getText().toString(),edate.getText().toString(),countmed.getText().toString(),pidNew,"Valid" );
                dbo.addPrescription( sid.getText().toString(),pidNew,newPrescription);
            },sid.getText().toString());
            Toast.makeText(CreatePrescriptionActivity.this,"Prescription created",
                    Toast.LENGTH_LONG).show();
            finish();
           // startActivity(new Intent(CreatePrescriptionActivity.this,
                   // DoctorActivity.class));
        });
    }
}