package com.stepup.madadgaar.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.stepup.madadgaar.R;
import com.stepup.madadgaar.utils.UserPreferences;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class After_login extends AppCompatActivity {
    ImageView txtRadio,txtMeter,txtSwitich,txtTransport,txtPower,txtfmc;
         TextView txtName;
    private DatabaseReference rootRef,reference;
    private DrawerLayout mdrawerlayout;
    TextView txthome,aboutUs,ContacUs,Logout;
    Button btnhelpCenter;
    private UserPreferences cache;
    ImageView Button_nav;
    private int REQUEST_CODE=11;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);


        rootRef = FirebaseDatabase.getInstance().getReference();
        Button_nav=findViewById(R.id.Button_nav);
        txtRadio=findViewById(R.id.txtRadio);
        btnhelpCenter=findViewById(R.id.btnhelpCenter);
        txtTransport=findViewById(R.id.txtTransport);
        txtMeter=findViewById(R.id.txtMetro);
        txtPower=findViewById(R.id.txtPower);
        txtSwitich=findViewById(R.id.txtSwitich);
        txtfmc=findViewById(R.id.txtfmc);
        txtName=findViewById(R.id.txtName);
        mdrawerlayout=findViewById(R.id.Drawer);
        txthome=findViewById(R.id.home);
        aboutUs=findViewById(R.id.about);
        Logout=findViewById(R.id.logout);
        ContacUs=findViewById(R.id.Contact);
        cache = UserPreferences.getInstance(this);

        checkConnection();
        checkUpdate();

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

      //  String Radio =txtRadio.getText().toString();
       // String Transport =txtTransport.getText().toString();
       // String Metro =txtMeter.getText().toString();
       // String Power =txtPower.getText().toString();
       // String Switich =txtSwitich.getText().toString();

        reference=rootRef.child("Users");
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(After_login.this);
        //int defaultValue = getResources().getInteger(R.integer.saved_high_score_default_key);
        String abc=sharedPreferences.getString("uniqueName","as");
        txtName.setText(abc);

        //Toast.makeText(getApplicationContext(),"show"+abc,Toast.LENGTH_LONG).show();
        Button_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mdrawerlayout.openDrawer(Gravity.LEFT);
            }
        });
       /* mToggle =new ActionBarDrawerToggle(this,mdrawerlayout,R.string.open,R.string.close);
        mdrawerlayout.addDrawerListener(mToggle);
        mToggle.syncState();*/
/*rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        String name=String.valueOf(dataSnapshot.child("txtfullName").getValue());
        txtName.setText(name);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
});*/
        txtRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ne = new Intent(getApplicationContext(),Sub_Mark.class);
                Bundle bundle=new Bundle();
                String Radio="Radio";
                String txtRadio="Radio Wireless System";
                bundle.putString("title",txtRadio);
                bundle.putString("Radeio",Radio);
                ne.putExtras(bundle);
                startActivity(ne);
                finish();
            }
        });
        txtTransport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ne = new Intent(getApplicationContext(),Sub_Mark.class);
                Bundle bundle=new Bundle();
                String txtTransport="Transport Network";
                String Transport="Transport";
                bundle.putString("title",txtTransport);
                bundle.putString("Radeio",Transport);
                ne.putExtras(bundle);
                startActivity(ne);
                finish();
            }
        });
        txtMeter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ne = new Intent(getApplicationContext(),Sub_Mark.class);
                Bundle bundle=new Bundle();
                String Metro="Metro";
                String txtMetro="Metero Transmission";
                bundle.putString("title",txtMetro);
                bundle.putString("Radeio",Metro);
                ne.putExtras(bundle);
                startActivity(ne);
                finish();
            }
        });
        txtPower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ne = new Intent(getApplicationContext(),Sub_Mark.class);
                Bundle bundle=new Bundle();
                String Power="Power";
                String txtPower="Power Plant";
                bundle.putString("title",txtPower);
                bundle.putString("Radeio",Power);
                ne.putExtras(bundle);
                startActivity(ne);
                finish();
            }
        });
        txtSwitich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ne = new Intent(getApplicationContext(),Sub_Mark.class);
                Bundle bundle=new Bundle();
                String Switich="Switich";
                String txtSwitich="Switching";
                bundle.putString("title",txtSwitich);
                bundle.putString("Radeio",Switich);
                ne.putExtras(bundle);
                startActivity(ne);
                finish();
            }
        });
        txtfmc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ne = new Intent(getApplicationContext(),Sub_Mark.class);
                Bundle bundle=new Bundle();
                String Switich="FMC";
                String txtSwitich="FMC";
                bundle.putString("title",txtSwitich);
                bundle.putString("Radeio",Switich);
                ne.putExtras(bundle);
                startActivity(ne);
                finish();
            }
        });

    }
    private void checkUpdate(){

        final AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(getApplicationContext());

// Returns an intent object that you use to check for an update.
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

// Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo appUpdateInfo) {
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                        // For a flexible update, use AppUpdateType.FLEXIBLE
                        && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                    try {
                        appUpdateManager.startUpdateFlowForResult(appUpdateInfo,AppUpdateType.IMMEDIATE,After_login.this, REQUEST_CODE);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                    // Request the update.
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    if(requestCode==REQUEST_CODE)
    {
        Toast.makeText(getApplicationContext(),"Start Download",Toast.LENGTH_LONG).show();
        if(resultCode!=REQUEST_CODE){
            Toast.makeText(getApplicationContext(),"Update Flow failed "+resultCode,Toast.LENGTH_LONG).show();
        }
    }

    }

    private void checkConnection(){
        ConnectivityManager manager=(ConnectivityManager)getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork=manager.getActiveNetworkInfo();
        if(null!=activeNetwork){
            if(activeNetwork.getType()==ConnectivityManager.TYPE_WIFI){
                Toast.makeText(getApplicationContext(),"WIFI Enabled",Toast.LENGTH_SHORT).show();
            }
            else if(activeNetwork.getType()==ConnectivityManager.TYPE_MOBILE){
                Toast.makeText(getApplicationContext(),"Mobile Data Enabled",Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
            Intent ne = new Intent(getApplicationContext(),No_Internet.class);
            startActivity(ne);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}