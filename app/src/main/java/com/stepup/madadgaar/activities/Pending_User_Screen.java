package com.stepup.madadgaar.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.stepup.madadgaar.R;
import com.stepup.madadgaar.adapters.Pending_Adapter;
import com.stepup.madadgaar.models.Approved_Model;
import com.stepup.madadgaar.utils.UserPreferences;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Pending_User_Screen extends AppCompatActivity {

    Button btnhelpCenter;
    ImageView Button_nav;
    DrawerLayout mdrawerlayout;
    TextView txthome,aboutUs,ContacUs,Logout;
    private UserPreferences cache;

    RecyclerView recyerView_Approved;
    TextView txtApproved_user;
    List<Approved_Model> lstTopics;
    private Pending_Adapter adapter;
    private DatabaseReference rootRef,topicsRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending__user__screen);

        cache = UserPreferences.getInstance(this);
        txthome =findViewById(R.id.home);
        aboutUs =findViewById(R.id.about);
        Logout=findViewById(R.id.logout);
        ContacUs=findViewById(R.id.Contact);
        Button_nav=findViewById(R.id.Button_nav);
        btnhelpCenter=findViewById(R.id.btnhelpCenter);
        mdrawerlayout=findViewById(R.id.Drawer_about);
        lstTopics = new ArrayList<>();
        recyerView_Approved=findViewById(R.id.recyerView_pending);
        recyerView_Approved.setLayoutManager(new LinearLayoutManager(this));
        txtApproved_user=findViewById(R.id.txtpending_user);

        rootRef= FirebaseDatabase.getInstance().getReference().child("Users");

        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor1 = sharedPreferences.edit();
                editor1.putInt("i", 0);
                editor1.commit();

                for(final DataSnapshot da:dataSnapshot.getChildren()){
                    String status = String.valueOf(da.child("status").getValue());
                    if (status != null && status.equals("false")) {
                        Approved_Model approved_model=new Approved_Model();
                        approved_model.setFulName(String.valueOf(da.child("txtfullName").getValue()));
                        approved_model.setEmp_no(String.valueOf(da.child("emp_no").getValue()));
                        approved_model.setRegion(String.valueOf(da.child("region").getValue()));
                        approved_model.setEmail(String.valueOf(da.child("email").getValue()));
                        approved_model.setPassword(String.valueOf(da.child("password").getValue()));
                        approved_model.setMobileNumber(String.valueOf(da.child("mobile").getValue()));
                        approved_model.setCategory(String.valueOf(da.child("Category").getValue()));
                        approved_model.setUid(String.valueOf(da.child("uid").getValue()));

                        // SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        int i = sharedPreferences.getInt("i", 0);
                        //i=a+1;
                        i++;
                        //users.setText(String.valueOf(i));
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("i", i);
                        editor.apply();
                        editor.commit();
                        //txtApproved_user.setText(i);
                        lstTopics.add(approved_model);
                    }

                }

                SharedPreferences sharedPreferences1 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                int a=sharedPreferences1.getInt("i",0);
                txtApproved_user.setText(String.valueOf(a));
                //progressDialog.dismiss();

                adapter=new Pending_Adapter(getApplicationContext(),lstTopics,rootRef);
                recyerView_Approved.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Button_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mdrawerlayout.openDrawer(Gravity.LEFT);
            }
        });

        btnhelpCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contact = "+923335311123";
                String message ="Dear \n" +
                        "Raheem Zeeshan\n" +
                        "Manager (Training & Development)";
                String url = null;
                try {
                    url = "https://api.whatsapp.com/send?phone="+contact+"&text="+ URLEncoder.encode(message, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Intent i = new Intent(Intent.ACTION_VIEW);
              /* i.setAction(Intent.ACTION_SEND);
                i.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                i.setType("text/plain");*/
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        txthome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ne =new Intent(getApplicationContext(),Pending_User_Screen.class);
                startActivity(ne);
                finish();
            }
        });
        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ne = new Intent(getApplicationContext(), Reporting_Screen.class);
                startActivity(ne);
                finish();
            }
        });
        ContacUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contact = "+923335311123";
                String message ="Dear \n" +
                        "Raheem Zeeshan\n" +
                        "Manager (Training & Development)";
                String url = null;
                try {
                    url = "https://api.whatsapp.com/send?phone="+contact+"&text="+ URLEncoder.encode(message, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Intent i = new Intent(Intent.ACTION_VIEW);
              /* i.setAction(Intent.ACTION_SEND);
                i.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                i.setType("text/plain");*/
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cache.saveBoolean(UserPreferences.PREF_IS_SPLASH,false);
                Intent ne =new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(ne);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}