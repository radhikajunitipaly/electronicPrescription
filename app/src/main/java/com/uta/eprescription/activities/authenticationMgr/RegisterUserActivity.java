package com.uta.eprescription.activities.authenticationMgr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.uta.eprescription.R;
import com.uta.eprescription.dao.dbMgr.UserDao;
import com.uta.eprescription.models.User;

public class RegisterUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        Button Btn_register = (Button)findViewById(R.id.registerButton);
        Btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User("sxt8117", "password", "shakthi@prakash",
                        "Shakti","prakash", "pharmacist");
                UserDao userDao = new UserDao();
                userDao.addUser(user);
                Toast.makeText(RegisterUserActivity.this, "User Registered!",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
