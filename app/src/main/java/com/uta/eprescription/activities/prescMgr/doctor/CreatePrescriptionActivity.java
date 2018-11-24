package com.uta.eprescription.activities.prescMgr.doctor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.uta.eprescription.R;
import com.uta.eprescription.dao.dbMgr.UserDao;
import com.uta.eprescription.models.Prescription;

public class CreatePrescriptionActivity extends AppCompatActivity {
    String p = "p";
    String pidNew;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_create_prescription );
        Button btn_save = findViewById( R.id.button_save ) ;
        btn_save.setOnClickListener((view) -> {
                UserDao dbo = new UserDao();
                dbo.getPrescriptionsOfUserCount( (Long count) -> {
                        count = count+1;
                        pidNew = p+count;
                        Prescription newPrescription = new Prescription( "med1","10",
                                "12","15","10",pidNew, "Valid");
                        dbo.addPrescription( "aaaa",pidNew,newPrescription);
                },"aaaa");
                Toast.makeText(CreatePrescriptionActivity.this,"Prescription Saved",
                        Toast.LENGTH_LONG).show();
        });
    }
}
