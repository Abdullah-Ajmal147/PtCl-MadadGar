package com.stepup.madadgaar.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.stepup.madadgaar.PdfCreatorActivity;
import com.stepup.madadgaar.R;
import com.stepup.madadgaar.adapters.Approved_Adapter;
import com.stepup.madadgaar.models.Approved_Model;
import com.stepup.madadgaar.models.Realm_Model;
import com.stepup.madadgaar.models.TccModel;
import com.stepup.madadgaar.models.Tcc_realm;
import com.stepup.madadgaar.utils.UserPreferences;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tejpratapsingh.pdfcreator.activity.PDFCreatorActivity;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class Reporting_Screen extends AppCompatActivity {

    Button btnhelpCenter,download;
    ImageView Button_nav;
    DrawerLayout mdrawerlayout;
    TextView txthome,aboutUs,ContacUs,Logout;
    RecyclerView recyerView_Approved;
    TextView txtApproved_user;
    List<Approved_Model> lstTopics;
    private Approved_Adapter adapter;
    SharedPreferences sharedPreferences;
    ProgressBar progressBar;
    private Cell cell;
    private long tcc = 0;
    Realm realm,realm1;

    private DatabaseReference rootRef,tccName;
    private UserPreferences cache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporting__screen);

        Realm.init(this);
        realm=Realm.getDefaultInstance();
        //realm1=Realm.getDefaultInstance();
        /*RealmConfiguration config =
                new RealmConfiguration.Builder()
                        .deleteRealmIfMigrationNeeded()
                        .build();

        Realm.setDefaultConfiguration(config);*/
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        cache = UserPreferences.getInstance(this);
        txthome =findViewById(R.id.home);
        aboutUs =findViewById(R.id.about);
        Logout=findViewById(R.id.logout);
        ContacUs=findViewById(R.id.Contact);
        Button_nav=findViewById(R.id.Button_nav);
        btnhelpCenter=findViewById(R.id.btnhelpCenter);
        mdrawerlayout=findViewById(R.id.Drawer_about);
        download=findViewById(R.id.download);
        progressBar=findViewById(R.id.progressRealm);
        lstTopics = new ArrayList<>();
        recyerView_Approved=findViewById(R.id.recyerView_Approved);
        recyerView_Approved.setLayoutManager(new LinearLayoutManager(this));
        recyerView_Approved.setHasFixedSize(true);
        txtApproved_user=findViewById(R.id.txtApproved_user);
        progressBar.setVisibility(View.VISIBLE);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        /*realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.deleteAll();
            }
        });*/

        rootRef= FirebaseDatabase.getInstance().getReference().child("Users");
        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor1 = sharedPreferences.edit();
                editor1.putInt("i", 0);
                editor1.apply();

                for(final DataSnapshot da:dataSnapshot.getChildren()){
                    String status = String.valueOf(da.child("status").getValue());
                    if (status != null && status.equals("true")) {
                        final Approved_Model approved_model=new Approved_Model();

                        approved_model.setFulName(String.valueOf(da.child("txtfullName").getValue()));
                    approved_model.setEmp_no(String.valueOf(da.child("emp_no").getValue()));
                    approved_model.setRegion(String.valueOf(da.child("region").getValue()));
                    approved_model.setEmail(String.valueOf(da.child("email").getValue()));
                    approved_model.setPassword(String.valueOf(da.child("password").getValue()));
                    approved_model.setMobileNumber(String.valueOf(da.child("mobile").getValue()));
                    approved_model.setCategory(String.valueOf(da.child("Category").getValue()));
                    approved_model.setUid(String.valueOf(da.child("uid").getValue()));

                        /*realm_model.setUsername(String.valueOf(da.child("txtfullName").getValue()));
                        realm_model.setEmpnum(String.valueOf(da.child("emp_no").getValue()));
                        realm_model.setEmail(String.valueOf(da.child("email").getValue()));
                        realm_model.setPassword(String.valueOf(da.child("password").getValue()));
                        realm_model.setUid(String.valueOf(da.child("uid").getValue()));

                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {

                                realm.insertOrUpdate(realm_model);
                                //realm.commitTransaction();
                            }
                        });
                        //realm.close();
                        progressBar.setVisibility(View.GONE);*/


                        // SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        int i = sharedPreferences.getInt("i", 0);
                        //i=a+1;
                        i++;
                        //users.setText(String.valueOf(i));
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("i", i);
                        editor.apply();
                        lstTopics.add(approved_model);
                    }

                }
                SharedPreferences sharedPreferences1 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                int a=sharedPreferences1.getInt("i",0);
                if(a=='0'||a=='1'||a=='2'||a=='3'||a=='4'||a=='5'||a=='6'||a=='7'||a=='8'||a=='9'){
                    txtApproved_user.setText(String.valueOf("0"+a));
                }else
                txtApproved_user.setText(String.valueOf(a));
                //progressDialog.dismiss();

                adapter=new Approved_Adapter(getApplicationContext(),lstTopics,rootRef,realm,sharedPreferences);
                recyerView_Approved.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


       tccName= FirebaseDatabase.getInstance().getReference().child("Users");
        //   realm.deleteAll();

        tccName.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(final DataSnapshot da:dataSnapshot.getChildren()) {

                    final Realm_Model realm_model = new Realm_Model();
                    realm_model.setUsername(String.valueOf(da.child("txtfullName").getValue()));
                    realm_model.setEmpnum(String.valueOf(da.child("emp_no").getValue()));
                    realm_model.setEmail(String.valueOf(da.child("email").getValue()));
                    realm_model.setPassword(String.valueOf(da.child("password").getValue()));
                    realm_model.setUid(String.valueOf(da.child("uid").getValue()));
                     realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {

                                realm.insertOrUpdate(realm_model);
                                //realm.commitTransaction();
                            }
                        });
                        //realm.close();
                        progressBar.setVisibility(View.GONE);

                    }
                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        Button_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mdrawerlayout.openDrawer(Gravity.LEFT);
            }
        });
/*
        final DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference().child("Scores");

        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot da:snapshot.getChildren()){
                    tcc=da.getChildrenCount();
                }
                final Realm_Model realm_model = realm.where(Realm_Model.class).equalTo("uid",snapshot.getKey()).findFirst();
                if(realm_model!=null) {

                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            realm_model.setTcc(tcc);
                            realm.insertOrUpdate(realm_model);
                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

        btnhelpCenter.setOnClickListener(new View.OnClickListener() {
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
                Intent ne = new Intent(getApplicationContext(), Pending_User_Screen.class);
                startActivity(ne);
                finish();
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
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent ne =new Intent(getApplicationContext(), PdfCreatorActivity.class);
                startActivity(ne);*/
                downloadReport();
            }
        });
//        txtApproved_user.setText(total);
    }

    private void downloadReport() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            printEmployees();
        } else {
            request_permission();
        }
    }

    private void printEmployees() {
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = null;
        CellStyle cellStyle = wb.createCellStyle();

        sheet = wb.createSheet("SR");
        Row row = sheet.createRow(0);

        cell = row.createCell(0);
        cell.setCellValue("Employee No.");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(1);
        cell.setCellValue("Name");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(2);
        cell.setCellValue("Email");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(3);
        cell.setCellValue("Password");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(4);
        cell.setCellValue("Tcc");
        cell.setCellStyle(cellStyle);

        sheet.setColumnWidth(0, (10 * 330));
        sheet.setColumnWidth(1, (10 * 700));
        sheet.setColumnWidth(2, (10 * 400));
        sheet.setColumnWidth(3, (10 * 200));
        sheet.setColumnWidth(4, (10 * 200));
        int row_num = 1;
        List<Realm_Model>realm_models=realm.where(Realm_Model.class).findAll();
        //List<Tcc_realm>tccRealms=realm.where(Tcc_realm.class).findAll();
        for (int i = 0; i < realm_models.size(); i++) {

            row = sheet.createRow(row_num);

            cell = row.createCell(0);
            cell.setCellValue(realm_models.get(i).getEmpnum());
            cell.setCellStyle(cellStyle);

            cell = row.createCell(1);
            cell.setCellValue(realm_models.get(i).getUsername());
            cell.setCellStyle(cellStyle);

            cell = row.createCell(2);
            cell.setCellValue(realm_models.get(i).getEmail());
            cell.setCellStyle(cellStyle);

            cell = row.createCell(3);
            cell.setCellValue(String.valueOf(realm_models.get(i).getPassword()));
            cell.setCellStyle(cellStyle);

            cell = row.createCell(4);
            cell.setCellValue(String.valueOf(realm_models.get(i).getTcc()));
            cell.setCellStyle(cellStyle);

            sheet.setColumnWidth(0, (10 * 330));
            sheet.setColumnWidth(1, (10 * 700));
            sheet.setColumnWidth(2, (10 * 700));
            sheet.setColumnWidth(3, (10 * 200));
            sheet.setColumnWidth(4, (10 * 200));
            row_num++;
        }
        reportDownload(wb, "short_report.xls");

    }

    private void reportDownload(Workbook wb, String name) {
        String folder = "Documents";
        String report_name = name;
        if (name.equals("long_report.xls")) {
            report_name = name;
        } /*else if (region.getSelectedItem().toString().equals("Select Region")) {

        }*/ else {
            report_name =  "StepUp.xls";
        }

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), report_name);

        if (!file.exists()) {
            file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), report_name);
            folder = "Downloads";
        }

        String path = file.getAbsolutePath();
        //  FileOutputStream outputStream = null;

        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            wb.write(outputStream);
            Toast.makeText(getApplicationContext(), "Report downloaded in " + folder + " folder.", Toast.LENGTH_LONG).show();
        } catch (java.io.IOException e) {
            e.printStackTrace();

            Toast.makeText(getApplicationContext(), "NOt OK", Toast.LENGTH_LONG).show();

            try {
                // outputStream.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void request_permission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
    }

    @Override
    public void onBackPressed() {
        Intent ne =new Intent(getApplicationContext(),Pending_User_Screen.class);
        startActivity(ne);
        finish();
    }
}