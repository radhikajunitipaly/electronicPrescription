package com.uta.eprescription.activities.prescMgr.doctor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.uta.eprescription.R;
import com.uta.eprescription.dao.dbMgr.UserDao;
import com.uta.eprescription.models.Prescription;

import java.net.FileNameMap;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CreatePrescriptionActivity extends AppCompatActivity {
    String p = "Prescription ";
    String pidNew;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_create_prescription );
        Button btn_save = findViewById( R.id.button_save ) ;
        final EditText date = (EditText)findViewById( R.id.sdate );

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = df.format(c);
        date.setText( formattedDate );
        btn_save.setOnClickListener((view) -> {

            final EditText sid = (EditText)findViewById( R.id.Studentid );

                UserDao dbo = new UserDao();
                dbo.getPrescriptionsOfUserCount( (Long count) -> {
                    final EditText edate = (EditText)findViewById( R.id.edate );
                    final EditText med  =   (EditText)findViewById( R.id.Med );
                    final EditText pow  = (EditText)findViewById( R.id.Power );
                    final EditText countmed = (EditText)findViewById( R.id.count ) ;
                    date.setText( formattedDate );
                        count = count+1;
                        pidNew = p+count;
                        Prescription newPrescription = new Prescription(med.getText().toString(),
                                pow.getText().toString(), date.getText().toString(),
                                edate.getText().toString(), countmed.getText().toString(),
                                pidNew,"Valid");
                        dbo.addPrescription( sid.toString(),pidNew,newPrescription);
                },sid.getText().toString());
                Toast.makeText(CreatePrescriptionActivity.this,"Prescription Saved",
                        Toast.LENGTH_LONG).show();
        });
    }
}
