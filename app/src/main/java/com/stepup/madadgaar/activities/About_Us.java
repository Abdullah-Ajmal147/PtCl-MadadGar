package com.stepup.madadgaar.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.stepup.madadgaar.R;
import com.stepup.madadgaar.utils.UserPreferences;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class About_Us extends AppCompatActivity {

    Button btnhelpCenter;
    ImageView Button_nav;
    DrawerLayout mdrawerlayout;
    TextView txthome,aboutUs,ContacUs,Logout;
    private UserPreferences cache;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        txthome =findViewById(R.id.home);
        aboutUs =findViewById(R.id.about);
        Logout=findViewById(R.id.logout);
        ContacUs=findViewById(R.id.Contact);
        Button_nav=findViewById(R.id.Button_nav);
        btnhelpCenter=findViewById(R.id.btnhelpCenter);
        mdrawerlayout=findViewById(R.id.Drawer_about);
        cache = UserPreferences.getInstance(this);

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
                Intent ne =new Intent(getApplicationContext(),After_login.class);
                startActivity(ne);
                finish();
            }
        });
        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ne = new Intent(getApplicationContext(), About_Us.class);
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
}