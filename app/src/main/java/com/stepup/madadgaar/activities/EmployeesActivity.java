package com.stepup.madadgaar.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.stepup.madadgaar.R;
import com.stepup.madadgaar.models.Model;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class EmployeesActivity extends AppCompatActivity {

    private List<Model> printList = new ArrayList<>();
    private Spinner region;
    private List<String> regionList = new ArrayList<>();
    private TextView emp_no, name, mobile, index, last, user_tcc, users, search, btn_lr, btn_sr;
    private TableLayout tableLayout;
    private TableRow tableRow;
    private int srNo;
    private long tcc = 0;
    private Cell cell;
    private ProgressDialog progressDialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employees);

        tableLayout = findViewById(R.id.tableLayout);
        tableRow = findViewById(R.id.tableRow);
        emp_no = findViewById(R.id.emp_no);
        name = findViewById(R.id.name);
        //mobile = findViewById(R.id.mobile);
        users = findViewById(R.id.users);
        region = findViewById(R.id.regions);
        search = findViewById(R.id.btn_search);
        btn_sr = findViewById(R.id.downloadSr);
        user_tcc = findViewById(R.id.cc);
        index = findViewById(R.id.index);
        last = findViewById(R.id.last);

        progressDialog = new ProgressDialog(EmployeesActivity.this);
        getEmployess();
        regionInList();
        applyFilter();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (region.getSelectedItem().toString().equals("Other")){
                    getEmployess();
                }
                else {
                    applyFilter();
                }

            }
        });
        btn_sr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadReport();
            }
        });
    }
    private void getEmployess() {
       final int a=0;
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        //final int[] finalArr = arr;
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                SharedPreferences sharedPreferences1 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                if (dataSnapshot.exists()) {
                    srNo = 0;
                    //users.setText(String.valueOf(dataSnapshot.getChildrenCount()));
                    // users.setText(String.valueOf(dataSnapshot.getChildrenCount()));
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor1 = sharedPreferences1.edit();
                    editor1.putInt("i", 0);
                    editor1.commit();
                    printList.clear();
                    for (DataSnapshot empList : dataSnapshot.getChildren()) {

                        Model model = new Model();
                        model.setEmp_no(empList.child("emp_no").getValue(String.class));
                        model.setName(empList.child("txtfullName").getValue(String.class));
                        model.setMobile(empList.child("mobile").getValue(String.class));
                        getTcc(empList.child("uid").getValue(String.class), model);
                        String status = String.valueOf(empList.child("status").getValue());
                        if (status != null && status.equals("true")) {
                            // SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            int i = sharedPreferences.getInt("i", 0);
                            //i=a+1;
                            i++;
                            //users.setText(String.valueOf(i));
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putInt("i", i);
                            editor.apply();
                            editor.commit();

                        }
                        //model.setStatus(empList.child("status").get());

                       /*if ((dataSnapshot.child("status").getValue(false)) {
                           Toast.makeText(getApplicationContext(),"testing",Toast.LENGTH_LONG).show();
                        } else {

                        }*/
                    }

                }
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                int a=sharedPreferences.getInt("i",0);
                users.setText(String.valueOf(a));
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void getTcc(final String id2, final Model model2) {

        DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference().child("Scores").child(id2);

        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    tcc = dataSnapshot.getChildrenCount();
                    model2.setTcc((int) tcc);
                    showEmployee(model2);
                    printList.add(model2);
                } else {
                    model2.setTcc(0);
                    showEmployee(model2);
                    printList.add(model2);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void showEmployee(Model model) {
        srNo++;

        index = new TextView(EmployeesActivity.this);
        emp_no = new TextView(EmployeesActivity.this);
        name = new TextView(EmployeesActivity.this);
        mobile = new TextView(EmployeesActivity.this);
        user_tcc = new TextView(EmployeesActivity.this);

        index.setText(srNo + "   ");
        emp_no.setText(model.getEmp_no());
        name.setText(model.getName() + "     ");
        mobile.setText(model.getMobile() + "   ");
        user_tcc.setText("    " + (model.getTcc()));

        tableRow = new TableRow(EmployeesActivity.this);

        tableRow.addView(index);
        tableRow.addView(emp_no);
        tableRow.addView(name);
        //tableRow.addView(mobile);
        tableRow.addView(user_tcc);

        tableRow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                TableRow t = (TableRow) view;
                TextView firstTextView = (TextView) t.getChildAt(2);
                Toast.makeText(getApplicationContext(),firstTextView.getText().toString(),Toast.LENGTH_SHORT).show();
            }
        });
        tableLayout.addView(tableRow);
    }
    private void applyFilter() {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        Query query = databaseReference.orderByChild("region").equalTo(region.getSelectedItem().toString());

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    tableLayout.removeAllViews();
                    srNo = 0;
                    addColumnsHeader();
                    printList.clear();
                    users.setText(String.valueOf(dataSnapshot.getChildrenCount()));
                    for (DataSnapshot empList : dataSnapshot.getChildren()) {

                        Model model = new Model();
                        model.setEmp_no(empList.child("emp_no").getValue(String.class));
                        model.setName(empList.child("name").getValue(String.class));
                        model.setMobile(empList.child("mobile").getValue(String.class));

                        getTcc(empList.child("uid").getValue(String.class), model);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void regionInList() {

        regionList.add("LTR-S");
        regionList.add("LTR-N");
        regionList.add("CTR");
        regionList.add("GTR");
        regionList.add("MTR");
        regionList.add("FTR");
        regionList.add("KTR-1");
        regionList.add("KTR-2");
        regionList.add("KTR-3");
        regionList.add("HYTR");
        regionList.add("STR");
        regionList.add("QTR");
        regionList.add("RTR");
        regionList.add("ITR");
        regionList.add("HTR");
        regionList.add("NTR-1");
        regionList.add("NTR-2");
        regionList.add("AJK");
        regionList.add("Other");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.region_list_layout_admin, regionList);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        region.setAdapter(adapter);

    }
    private void addColumnsHeader() {

        index = new TextView(EmployeesActivity.this);
        emp_no = new TextView(EmployeesActivity.this);
        name = new TextView(EmployeesActivity.this);
        mobile = new TextView(EmployeesActivity.this);
        user_tcc = new TextView(EmployeesActivity.this);
        last = new TextView(EmployeesActivity.this);

        designView(index);
        designView(emp_no);
        designView(name);
        designView(mobile);
        designView(user_tcc);

        index.setText("Sr.#");
        emp_no.setText("Employee No.");
        name.setText("Name");
        mobile.setText("Mobile");
        user_tcc.setText("TCC");
        last.setText("      ");

        tableRow = new TableRow(EmployeesActivity.this);

        tableRow.addView(index);
        tableRow.addView(emp_no);
        tableRow.addView(name);
        tableRow.addView(mobile);
        tableRow.addView(user_tcc);
        tableRow.addView(last);

        tableLayout.addView(tableRow);

    }

    private void designView(TextView textView) {

        textView.setTextSize(18);
        textView.setBackgroundResource(R.drawable.border_corner);
        textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        textView.setGravity(Gravity.CENTER);
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
        cell.setCellValue("Mobile");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(3);
        cell.setCellValue("TCC");
        cell.setCellStyle(cellStyle);

        sheet.setColumnWidth(0, (10 * 330));
        sheet.setColumnWidth(1, (10 * 700));
        sheet.setColumnWidth(2, (10 * 400));
        sheet.setColumnWidth(3, (10 * 200));
        int row_num = 1;

        for (int i = 0; i < printList.size(); i++) {

            row = sheet.createRow(row_num);

            cell = row.createCell(0);
            cell.setCellValue(printList.get(i).getEmp_no());
            cell.setCellStyle(cellStyle);

            cell = row.createCell(1);
            cell.setCellValue(printList.get(i).getName());
            cell.setCellStyle(cellStyle);

            cell = row.createCell(2);
            cell.setCellValue(printList.get(i).getMobile());
            cell.setCellStyle(cellStyle);

            cell = row.createCell(3);
            cell.setCellValue(String.valueOf(printList.get(i).getTcc()));
            cell.setCellStyle(cellStyle);

            sheet.setColumnWidth(0, (10 * 330));
            sheet.setColumnWidth(1, (10 * 700));
            sheet.setColumnWidth(2, (10 * 400));
            sheet.setColumnWidth(3, (10 * 200));
            row_num++;
        }
        reportDownload(wb, "short_report.xls");

    }
    private void reportDownload(Workbook wb, String name) {

        String folder = "Documents";
        String report_name = name;
        if (name.equals("long_report.xls")) {
            report_name = name;
        } else if (region.getSelectedItem().toString().equals("Select Region")) {

        } else {
            report_name = region.getSelectedItem().toString() + ".xls";
        }

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), report_name);

        if (!file.exists()) {
            file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), report_name);
            folder = "Downloads";
        }

        String path = file.getAbsolutePath();
        FileOutputStream outputStream = null;

        try {
            outputStream = new FileOutputStream(file);
            wb.write(outputStream);
            Toast.makeText(getApplicationContext(), "Report downloaded in " + folder + " folder.", Toast.LENGTH_LONG).show();
        } catch (java.io.IOException e) {
            e.printStackTrace();

            Toast.makeText(getApplicationContext(), "NOt OK", Toast.LENGTH_LONG).show();

            try {
                outputStream.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }

    private void request_permission() {

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
    }

    public void downloadReport() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            printEmployees();
        } else {
            request_permission();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 100 & grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                printEmployees();
            } else {
                Toast.makeText(EmployeesActivity.this, "Permission Denide", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
