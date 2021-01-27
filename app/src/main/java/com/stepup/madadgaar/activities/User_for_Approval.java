package com.stepup.madadgaar.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.stepup.madadgaar.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class User_for_Approval extends AppCompatActivity {

    Button btnYes,btnNo;
    TextView txtName,txtEmpl_No,txtRegion,txtEmail,txtPassword,txtContactNumber,txtdomain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_for__approval);

        btnYes=findViewById(R.id.btnYes);
        btnNo=findViewById(R.id.btnNo);

        txtName=findViewById(R.id.txtName);
        txtEmpl_No=findViewById(R.id.txtEmpl_No);
        txtRegion=findViewById(R.id.txtRegion);
        txtEmail=findViewById(R.id.txtEmail);
        txtPassword=findViewById(R.id.txtPassword);
        txtdomain=findViewById(R.id.txtdomain);
        txtContactNumber=findViewById(R.id.txtContactNumber);

        String name=getIntent().getStringExtra("NAME");
        String empno=getIntent().getStringExtra("EMPNO");
        String region=getIntent().getStringExtra("REGION");
        String email=getIntent().getStringExtra("EMAIL");
        String password=getIntent().getStringExtra("PASSWORD");
        String mobile=getIntent().getStringExtra("CONTACT");
        String domina=getIntent().getStringExtra("DOMINA");
        final String uid=getIntent().getStringExtra("UID");

        txtName.setText(name);
        txtEmpl_No.setText(empno);
        txtRegion.setText(region);
        txtEmail.setText(email);
        txtPassword.setText(password);
        txtdomain.setText(domina);
        txtContactNumber.setText(mobile);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Users").child(uid);
                databaseReference.child("status").setValue("true");
                Toast.makeText(getApplicationContext(),"Successfully Update User",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Users").child(uid);
                databaseReference.removeValue();
                Toast.makeText(getApplicationContext(),"Successfully Deleted User",Toast.LENGTH_SHORT).show();
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