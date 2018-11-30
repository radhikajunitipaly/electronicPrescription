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
        edate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();
                int day = c.get( Calendar.DAY_OF_MONTH );
                int mon = c.get( Calendar.MONTH );
                int yr = c.get( Calendar.YEAR );
                dp = new DatePickerDialog( CreatePrescriptionActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int myear, int month, int d) {
                        edate.setText( d + "/" + month + "/" + myear );

                    }
                },day,mon,yr );
                dp.show();

            }

        });

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = df.format(c);
        date.setText( formattedDate );
        btn_save.setOnClickListener((view) -> {
            final EditText med  =   (EditText)findViewById( R.id.Med );
            final EditText pow  = (EditText)findViewById( R.id.Power );
            final EditText countmed = (EditText)findViewById( R.id.count ) ;
            date.setText( formattedDate );
            final EditText sid = (EditText)findViewById( R.id.Studentid );
            UserDao dbo = new UserDao();
            dbo.getPrescriptionsOfUserCount( (Long count) -> {
                count = count+1;
                pidNew = p+count;
                Prescription newPrescription = new Prescription(med.getText().toString(),pow.getText().toString(),date.getText().toString(),edate.getText().toString(),countmed.getText().toString(),pidNew,"Valid" );
                dbo.addPrescription( sid.getText().toString(),pidNew,newPrescription);
            },sid.getText().toString());
            Toast.makeText(CreatePrescriptionActivity.this,"Prescription created",
                    Toast.LENGTH_LONG).show();
            startActivity(new Intent(CreatePrescriptionActivity.this,
                    DoctorActivity.class));
        });
    }
}