package com.uta.eprescription.activities.prescMgr.doctor;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.uta.eprescription.R;
import com.uta.eprescription.activities.authenticationMgr.MainActivity;
import com.uta.eprescription.activities.authenticationMgr.RegisterUserActivity;
import com.uta.eprescription.controllers.prescMgr.doctor.PrescriptionCountCall;
import com.uta.eprescription.dao.dbMgr.UserDao;
import com.uta.eprescription.models.Prescription;
import com.uta.eprescription.controllers.prescMgr.doctor.PrescriptionCountCall;

public class CreatePrescriptionActivity extends AppCompatActivity {
    String p = "p";
    String pidnew;
    Long dbPidcount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_create_prescription );
        Button btn_save = (Button)findViewById( R.id.button_save ) ;
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserDao dbo = new UserDao();
                dbo.getPrescriptionsOfUserCount( new PrescriptionCountCall<Long>() {
                    @Override
                    public void callback(Long count) {
                        count = count+1;
                        dbPidcount = count;
                        pidnew = "p"+dbPidcount;
                        Prescription newPresc = new Prescription( "med1","10","12","15","10",pidnew);
                        dbo.addPresc( "aaaa",pidnew,newPresc);
                    }
                },"aaaa");
                Toast.makeText(CreatePrescriptionActivity.this,"Prescription Saved",
                        Toast.LENGTH_LONG).show();
                }
        });


        }

}
