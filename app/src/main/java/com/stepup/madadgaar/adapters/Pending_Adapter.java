package com.stepup.madadgaar.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.stepup.madadgaar.R;
import com.stepup.madadgaar.activities.User_for_Approval;
import com.stepup.madadgaar.models.Approved_Model;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;


public class Pending_Adapter extends RecyclerView.Adapter<Pending_Adapter.MyView> {

    Context c;
    List<Approved_Model>list;
    DatabaseReference rootref;
    String uid;

    public Pending_Adapter(Context applicationContext, List<Approved_Model> lstTopics, DatabaseReference rootref) {
        this.c=applicationContext;
        this.list=lstTopics;
        this.rootref=rootref;

    }

    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(c).inflate(R.layout.approve_layout, viewGroup, false);
        return new MyView(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyView myView, int i) {

        final Approved_Model AM=list.get(i);
        if(AM.getEmp_no()!=null &&AM.getEmp_no() != "null") {
            myView.txtemp_no.setText(AM.getEmp_no());
            myView.txtUserName.setText(AM.getFulName());
            myView.txtNumber.setText(Integer.toString(i + 1));
        }

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
        uid = sharedPreferences.getString("uid","null");



        myView.removeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent ne =new Intent(c, User_for_Approval.class);
                String name=AM.getFulName();
                String empNo=AM.getEmp_no();
                String region=AM.getRegion();
                String email=AM.getEmail();
                String password=AM.getPassword();
                String contact=AM.getMobileNumber();
                String domain=AM.getCategory();
                String uid1=AM.getUid();

                ne.putExtra("NAME",name);
                ne.putExtra("EMPNO",empNo);
                ne.putExtra("REGION",region);
                ne.putExtra("EMAIL",email);
                ne.putExtra("PASSWORD",password);
                ne.putExtra("CONTACT",contact);
                ne.putExtra("DOMINA",domain);
                ne.putExtra("UID",uid1);
                ne.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                c.startActivity(ne);
            }




                private void DeleteData() {
                String abc=AM.getUid();
                rootref= FirebaseDatabase.getInstance().getReference("Users").child(abc);
                rootref.removeValue();
                Toast.makeText(c, "Delete Successfully", Toast.LENGTH_LONG).show();
            }
        });
       /* myView.txtemp_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent ne =new Intent(c, User_for_Approval.class);
                String name=AM.getFulName();
                String empNo=AM.getEmp_no();
                String region=AM.getRegion();
                String email=AM.getEmail();
                String password=AM.getPassword();
                String contact=AM.getMobileNumber();

                ne.putExtra("NAME",name);
                ne.putExtra("EMPNO",empNo);
                ne.putExtra("REGION",region);
                ne.putExtra("EMAIL",email);
                ne.putExtra("PASSWORD",password);
                ne.putExtra("CONTACT",contact);
                ne.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                c.startActivity(ne);
            }

        });*/
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyView extends RecyclerView.ViewHolder{

        LinearLayout removeLayout;
        TextView txtNumber,txtemp_no,txtUserName,txtTCC;

        public MyView(@NonNull View itemView) {
            super(itemView);
            txtNumber=itemView.findViewById(R.id.txtNumber);
            txtemp_no=itemView.findViewById(R.id.txtemp_no);
            txtUserName=itemView.findViewById(R.id.txtUserName);
            txtTCC=itemView.findViewById(R.id.txtTCC);

            removeLayout=itemView.findViewById(R.id.removeLayout);
        }
    }
}
