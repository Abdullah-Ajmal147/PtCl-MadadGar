package com.stepup.madadgaar.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.stepup.madadgaar.R;
import com.stepup.madadgaar.utils.UserPreferences;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Done_Activity extends AppCompatActivity {
TextView txt_Name,txt_SubCategory,btnNextCategory;
    ImageView Button_nav;
    DrawerLayout mdrawerlayout;
    TextView txthome,aboutUs,ContacUs,Logout;
    private UserPreferences cache;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done);

        txt_Name=findViewById(R.id.txt_Done_Name);
        btnNextCategory=findViewById(R.id.btnNextCategory);
        txt_SubCategory=findViewById(R.id.txt_SubCategory);
        Button_nav=findViewById(R.id.Button_nav);
        txthome=findViewById(R.id.home);
        aboutUs=findViewById(R.id.about);
        Logout=findViewById(R.id.logout);
        ContacUs=findViewById(R.id.Contact);
        mdrawerlayout=findViewById(R.id.Drawer);
        cache = UserPreferences.getInstance(this);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //int defaultValue = getResources().getInteger(R.integer.saved_high_score_default_key);
        String abc=sharedPreferences.getString("uniqueName","as");
        String CateGoryName=sharedPreferences.getString("subCategoryName","as");

        final String CateGory=sharedPreferences.getString("Title_Name","as");
        final String fb_CateGoryName=sharedPreferences.getString("fb_title_Name","as");

        txt_Name.setText(abc);
        txt_SubCategory.setText(CateGoryName);

        btnNextCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ne = new Intent(getApplicationContext(),Sub_Mark.class);
                Bundle bundle=new Bundle();
                String Radio=fb_CateGoryName;
                String txtRadio=CateGory;
                bundle.putString("title",txtRadio);
                bundle.putString("Radeio",Radio);
                ne.putExtras(bundle);
                startActivity(ne);
                finish();
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
                String contact = "+923347694700";
                String message ="Ashraf Javed\n" +
                        "Manager Training Admin \n";
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

        Button_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mdrawerlayout.openDrawer(Gravity.LEFT);
            }
        });


        /*SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Done_Activity.this);
        //int defaultValue = getResources().getInteger(R.integer.saved_high_score_default_key);

        String abc=sharedPreferences.getString("uniqueName","as");
        txt_Name.setText(abc);*/
    }

    @Override
    public void onBackPressed() {
        Intent ne=new Intent(getApplicationContext(),After_login.class);
        super.onBackPressed();
        startActivity(ne);
    }
}