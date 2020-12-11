package com.StepUP.madadgaar.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.StepUP.madadgaar.R;

public class AdminActivity extends AppCompatActivity {

    private Button btn_sr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin2);

        btn_sr = findViewById(R.id.btn_sr);

        btn_sr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminActivity.this,EmployeesActivity.class));
            }
        });

    }
}
