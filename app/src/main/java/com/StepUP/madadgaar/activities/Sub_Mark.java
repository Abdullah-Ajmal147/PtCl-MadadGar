package com.StepUP.madadgaar.activities;

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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.StepUP.madadgaar.R;
import com.StepUP.madadgaar.adapters.SubCattegoryAdapter;
import com.StepUP.madadgaar.models.Video_id_Model;
import com.StepUP.madadgaar.utils.UserPreferences;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Sub_Mark extends AppCompatActivity {
    TextView txtwelcome;
    RecyclerView myRecyclerView;
    private DatabaseReference rootRef,topicsRef;
    List<Video_id_Model> lstTopics;
    private SubCattegoryAdapter adapter;
    ImageView Button_nav;
    DrawerLayout mdrawerlayout;
    TextView txthome,aboutUs,ContacUs,Logout;
    Button btnhelpCenter;
    private UserPreferences cache;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_mark);
        txtwelcome=findViewById(R.id.txtName1);
        Bundle bundle = getIntent().getExtras();
        String Name=bundle.getString("title");
        String Fb_child=bundle.getString("Radeio");

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Title_Name",Name);
        editor.putString("fb_title_Name",Fb_child);

        editor.commit();
        //

        txtwelcome.setText(Name);
        myRecyclerView=findViewById(R.id.myrecycler);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        lstTopics = new ArrayList<>();
        rootRef = FirebaseDatabase.getInstance().getReference();
        topicsRef = rootRef.child(Fb_child);
        Button_nav=findViewById(R.id.Button_nav);
        txthome=findViewById(R.id.home);
        aboutUs=findViewById(R.id.about);
        Logout=findViewById(R.id.logout);
        ContacUs=findViewById(R.id.Contact);
        mdrawerlayout=findViewById(R.id.Drawer);
        btnhelpCenter=findViewById(R.id.btnhelpCenter);
        cache = UserPreferences.getInstance(this);

        btnhelpCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contact = "+923088743044";
                String message ="Hi PTCL...";
                String url = null;
                try {
                    url = "https://api.whatsapp.com/send?phone="+contact+"&text="+ URLEncoder.encode(message, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Intent i = new Intent(Intent.ACTION_VIEW);
              /*  i.setAction(Intent.ACTION_SEND);
                i.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                i.setType("text/plain");*/
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        txthome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ne =new Intent(getApplicationContext(),After_login.class);
                startActivity(ne);
            }
        });
        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ne =new Intent(getApplicationContext(),About_Us.class);
                startActivity(ne);
            }
        });
        ContacUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contact = "+923088743044";
                String message ="Hi PTCL...";
                String url = null;
                try {
                    url = "https://api.whatsapp.com/send?phone="+contact+"&text="+ URLEncoder.encode(message, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Intent i = new Intent(Intent.ACTION_VIEW);
              /*  i.setAction(Intent.ACTION_SEND);
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

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Radio");
        //databaseReference.removeValue();
        Button_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mdrawerlayout.openDrawer(Gravity.LEFT);
            }
        });

        topicsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (final DataSnapshot item : dataSnapshot.getChildren()) {
                    Video_id_Model title = new Video_id_Model();

                    title.setSub_title1(String.valueOf(item.child("sub_title1").getValue()));
                    title.setSub_playlist1(String.valueOf(item.child("sub_playlist1").getValue()));
                    title.setVideo_id(String.valueOf(item.child("video_id").getValue()));
                    title.setVideoTitle(String.valueOf(item.child("videoTitle").getValue()));
                    title.setVideo_Counter(String.valueOf(item.child("video_Counter").getValue()));
                    title.setQuiz(String.valueOf(item.child("quiz").getValue()));
                   // title.set(String.valueOf(item.child("sub_playlist2").getValue()));

                    lstTopics.add(title);
                }
                adapter = new SubCattegoryAdapter(getApplicationContext(), lstTopics);
                myRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}