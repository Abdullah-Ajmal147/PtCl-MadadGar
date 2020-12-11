package com.StepUP.madadgaar.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.StepUP.madadgaar.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.StepUP.madadgaar.utils.Constants;
import com.StepUP.madadgaar.utils.UserPreferences;

public class LoginActivity extends AppCompatActivity {

    //private TextView signup;
    private Button btn_login;
    private EditText password,empNo;
    LinearLayout signup;
    private UserPreferences cache;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        btn_login = findViewById(R.id.btnLogin);
        signup = findViewById(R.id.signup);
        password = findViewById(R.id.Pasword);
        empNo = findViewById(R.id.empNo);
        progressBar=findViewById(R.id.progressBar2);
        cache=UserPreferences.getInstance(this);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isAdminLogin()) {
                    startActivity(new Intent(LoginActivity.this,EmployeesActivity.class));
                }
                else {
                    longinUser();
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private boolean isAdminLogin() {

        if (password.getText().toString().equals(Constants.ADMIN_ID) && empNo.getText().toString().equals(Constants.ADMIN_PASSWORD)){
            return true;
        }
        return false;
    }

    private void longinUser() {

        if (!password.getText().toString().isEmpty() && !empNo.getText().toString().isEmpty()) {

            final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
           // final DatabaseReference databaseReferenceDlt = FirebaseDatabase.getInstance().getReference();
           // databaseReference.removeValue();

            final Query emp = databaseReference.orderByChild("emp_no").equalTo(empNo.getText().toString());


            emp.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){

                            /*if (!password.getText().toString().equals(dataSnapshot1.child("password").getValue()))
                            {
                                Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                                return;
                            }*/
                            String name=dataSnapshot1.child("txtfullName").getValue().toString();
                            String status=dataSnapshot1.child("status").getValue().toString();
                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("uid", dataSnapshot1.child("uid").getValue().toString());
                            editor.putString("uniqueName", name);
                            editor.commit();
                            Log.d("Name==", name);

                            if(status!="true"){
                                Toast.makeText(getApplicationContext(),"Waiting For Approval",Toast.LENGTH_LONG).show();
                                Intent ne=new Intent(getApplicationContext(),Alert_Screen.class);
                                progressBar.setVisibility(View.GONE);
                                startActivity(ne);
                            }
                            else {
                                cache.saveBoolean(UserPreferences.PREF_IS_SPLASH, true);
                                progressBar.setVisibility(View.GONE);
                                startActivity(new Intent(LoginActivity.this, After_login.class));
                                finish();
                            }
                        }
                    } else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(LoginActivity.this, "Enter Correct Employe ID", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else {
            Toast.makeText(LoginActivity.this, "Enter Values", Toast.LENGTH_LONG).show();
        }
    }
}
