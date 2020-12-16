package com.StepUP.madadgaar.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.StepUP.madadgaar.R;
import com.google.firebase.database.DatabaseReference;

public class Reporting_Screen extends AppCompatActivity {
    RecyclerView recyerView_Approved;
    TextView txtApproved_user;
    private DatabaseReference rootRef,topicsRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporting__screen);

        recyerView_Approved=findViewById(R.id.recyerView_Approved);
        recyerView_Approved.setLayoutManager(new LinearLayoutManager(this));
        txtApproved_user=findViewById(R.id.txtApproved_user);


    }
}