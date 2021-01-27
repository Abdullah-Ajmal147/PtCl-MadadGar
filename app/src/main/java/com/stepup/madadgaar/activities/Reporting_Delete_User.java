package com.stepup.madadgaar.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.stepup.madadgaar.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Reporting_Delete_User extends AppCompatActivity {

    Button btnYes,btnNo;
    TextView txtName,txtEmpl_No,txtRegion,txtEmail,txtPassword,txtContactNumber,
    txtDomain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporting__delete__user);

        btnYes=findViewById(R.id.btnYes);
        btnNo=findViewById(R.id.btnNo);

        txtName=findViewById(R.id.txtName);
        txtEmpl_No=findViewById(R.id.txtEmpl_No);
        txtRegion=findViewById(R.id.txtRegion);
        txtEmail=findViewById(R.id.txtEmail);
        txtPassword=findViewById(R.id.txtPassword);
        txtContactNumber=findViewById(R.id.txtContactNumber);
        txtDomain=findViewById(R.id.txtDomain);

        String name=getIntent().getStringExtra("NAME");
        String empno=getIntent().getStringExtra("EMPNO");
        String region=getIntent().getStringExtra("REGION");
        String email=getIntent().getStringExtra("EMAIL");
        String password=getIntent().getStringExtra("PASSWORD");
        String mobile=getIntent().getStringExtra("CONTACT");
        String domain=getIntent().getStringExtra("DOMAIN");
        final String uid=getIntent().getStringExtra("UID");

        txtName.setText(name);
        txtEmpl_No.setText(empno);
        txtRegion.setText(region);
        txtEmail.setText(email);
        txtPassword.setText(password);
        txtContactNumber.setText(mobile);
        txtDomain.setText(domain);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Users").child(uid);
                databaseReference.removeValue();
                Toast.makeText(getApplicationContext(),"Successfully Deleted User",Toast.LENGTH_SHORT).show();
                Intent ne =new Intent(getApplicationContext(),Reporting_Screen.class);
                startActivity(ne);
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}