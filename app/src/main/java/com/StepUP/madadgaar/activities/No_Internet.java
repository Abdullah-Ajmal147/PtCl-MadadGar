package com.StepUP.madadgaar.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.StepUP.madadgaar.R;

public class No_Internet extends AppCompatActivity {
   Button RetryConnection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no__internet);
        RetryConnection=findViewById(R.id.RetryConnection);
        RetryConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkConnection();
            }
        });
    }
    private void checkConnection(){
        ConnectivityManager manager=(ConnectivityManager)getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork=manager.getActiveNetworkInfo();
        if(null!=activeNetwork){
            if(activeNetwork.getType()==ConnectivityManager.TYPE_WIFI){
                Toast.makeText(getApplicationContext(),"WIFI Enabled",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(getApplicationContext(),After_login.class);
                startActivity(intent);
            }
           else if(activeNetwork.getType()==ConnectivityManager.TYPE_MOBILE){
                Toast.makeText(getApplicationContext(),"Mobile Data Enabled",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(getApplicationContext(),After_login.class);
                startActivity(intent);
            }
        }else {
            Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}