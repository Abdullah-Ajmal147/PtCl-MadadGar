package com.stepup.madadgaar.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.stepup.madadgaar.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private Button btn_register;
    private EditText txtfullName,txtEmployeeNo,txtPassword,txtRepassword,txtemail,txtContactNo;
    private EditText name, mobile, emp_no, repeat_emp;
    //    private CheckBox checkBox;
    private ProgressDialog progressDialog;
    //private Spinner region;
    private Spinner spinner;
    private EditText region;
    private List<String> regionList = new ArrayList<>();
    private String empName, empNo, empMobile, empPassword, empRepassword,empRegion, uid,empEmail,region_check;
    private DatabaseReference databaseReference,Reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        txtfullName=findViewById(R.id.txtfullName);
        txtPassword=findViewById(R.id.txtPassword);
        txtRepassword=findViewById(R.id.txtRepassword);
        txtemail=findViewById(R.id.txtemail);
        txtContactNo=findViewById(R.id.txtContactNo);

        btn_register = findViewById(R.id.btnRegister);

      //  name = findViewById(R.id.name);
        txtEmployeeNo = findViewById(R.id.txtEmployeeNo);
        //repeat_emp = findViewById(R.id.repeatEmpNo);
        region = findViewById(R.id.region_written);

      spinner = (Spinner) findViewById(R.id.spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Main_Topic, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
spinner.setAdapter(adapter);


       // mobile = findViewById(R.id.mobile);
//        checkBox = findViewById(R.id.checkBox);
      /* spinner.add("jsgbf");
        regionList.add("LTR-N");
        regionList.add("CTR");
        regionList.add("GTR");
        regionList.add("MTR");
        regionList.add("FTR");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.region_list_layout, spinner);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);*/

        /*regionList.add("ریجن");
        regionList.add("LTR-S");
        regionList.add("LTR-N");
        regionList.add("CTR");
        regionList.add("GTR");
        regionList.add("MTR");
        regionList.add("FTR");
        regionList.add("KTR-1");
        regionList.add("KTR-2");
        regionList.add("KTR-3");
        regionList.add("HYTR");
        regionList.add("STR");
        regionList.add("QTR");
        regionList.add("RTR");
        regionList.add("ITR");
        regionList.add("HTR");
        regionList.add("NTR-1");
        regionList.add("NTR-2");
        regionList.add("AJK");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.region_list_layout, regionList);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        region.setAdapter(adapter);*/

        progressDialog = new ProgressDialog(this);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewUser();
            }
        });
    }

    private void registerNewUser() {

        progressDialog.setTitle("Please wait...");
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();

        empName = txtfullName.getText().toString().trim();
        empNo = txtEmployeeNo.getText().toString().trim();
        empPassword = txtPassword.getText().toString().trim();
        empRepassword = txtRepassword.getText().toString().trim();
        empMobile = txtContactNo.getText().toString().trim();
        empEmail=txtemail.getText().toString().trim();
        region_check=region.getText().toString().trim();
        empRegion = spinner.getSelectedItem().toString().trim();

        if (!empName.isEmpty() && !empNo.isEmpty() && !empPassword.isEmpty() && !empRepassword.isEmpty() && !empMobile.isEmpty() && !empRegion.isEmpty() && !empEmail.isEmpty() ) {

            databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
            //Reference = FirebaseDatabase.getInstance().getReference().child("Scores");
          //  Reference.removeValue();

            uid = databaseReference.push().getKey();

            if (empRepassword.equals(empPassword)) {
                isEmployeeExists(empNo, empMobile);
            } else {
                progressDialog.dismiss();
                Toast.makeText(RegisterActivity.this, "Password and repeat Password must be same!", Toast.LENGTH_LONG).show();
            }
        } else {
            progressDialog.dismiss();
            Toast.makeText(RegisterActivity.this, "Enter Correct Values", Toast.LENGTH_LONG).show();

        }
    }

    private void saveNewUser() {
        String status="false";
        final Map<String, Object> newUser = new HashMap<>();
        newUser.put("txtfullName", empName);
        newUser.put("emp_no", empNo);
        newUser.put("email", empEmail);
        newUser.put("password", empPassword);
        newUser.put("mobile", empMobile);
        newUser.put("Category", empRegion);
        newUser.put("region", region_check);
        newUser.put("uid", uid);
        newUser.put("status",status);

        databaseReference.child(uid).setValue(newUser).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                Toast.makeText(RegisterActivity.this, "Success", Toast.LENGTH_LONG).show();
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    private void isMobileExists(String mobile) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        Query query = databaseReference.orderByChild("mobile").equalTo(mobile);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    progressDialog.dismiss();
                     Toast.makeText(RegisterActivity.this, "Mobile No. already exists.", Toast.LENGTH_LONG).show();
                } else {
                    saveNewUser();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void isEmployeeExists(String empNo, final String mobile) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
         Query query = databaseReference.orderByChild("emp_no").equalTo(empNo);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, "Employee No. already exists.", Toast.LENGTH_LONG).show();
                } else {
                    isMobileExists(mobile);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(getApplicationContext(),LoginActivity.class);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}
