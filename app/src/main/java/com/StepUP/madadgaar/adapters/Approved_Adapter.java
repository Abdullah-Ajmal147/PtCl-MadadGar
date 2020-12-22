package com.StepUP.madadgaar.adapters;

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
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.StepUP.madadgaar.R;
import com.StepUP.madadgaar.activities.LoginActivity;
import com.StepUP.madadgaar.activities.Reporting_Delete_User;
import com.StepUP.madadgaar.activities.User_for_Approval;
import com.StepUP.madadgaar.models.Approved_Model;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;


public class Approved_Adapter extends RecyclerView.Adapter<Approved_Adapter.MyView> {

    Context c;
    List<Approved_Model>list;
    DatabaseReference rootref;
    String uid;
    private long tcc = 0;
    MyView myView1;
    List<Approved_Model> lstTopics;
    public Approved_Adapter(Context applicationContext, List<Approved_Model> lstTopics, DatabaseReference rootref) {
        this.c=applicationContext;
        this.list=lstTopics;
        this.rootref=rootref;


    }

    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(c).inflate(R.layout.pending_layout, viewGroup, false);
        return new MyView(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyView myView, int i) {
        myView1=myView;
        final Approved_Model AM=list.get(i);
        if(AM.getEmp_no()!=null &&AM.getEmp_no() != "null") {
            myView.txtemp_no.setText(AM.getEmp_no());
            myView.txtUserName.setText(AM.getFulName());
            myView.txtNumber.setText(Integer.toString(i + 1));
            String id2=AM.getUid();
         //   getTcc(id2);
            final DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference().child("Scores").child(id2);

            databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        Approved_Model approved_model=new Approved_Model();
                        Approved_Model approved_model1=new Approved_Model();
                        tcc=dataSnapshot.getChildrenCount();
                        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(c);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("tcc", (int) tcc);
                        editor.commit();
                        editor.apply();
                       /* approved_model.setTcc((int) tcc);
                        approved_model1.add(approved_model);*/
                         data();
                     //   SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(c);
                        int a=sharedPreferences.getInt("tcc",0);
                        myView.txtTCC.setText(" "+(a));
                        //
                    }else
                    {
                        myView1.txtTCC.setText(Integer.toString(00));
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
        uid = sharedPreferences.getString("uid","null");


        myView.removeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent ne =new Intent(c, Reporting_Delete_User.class);
                String name=AM.getFulName();
                String empNo=AM.getEmp_no();
                String region=AM.getRegion();
                String email=AM.getEmail();
                String password=AM.getPassword();
                String contact=AM.getMobileNumber();
                String uid1=AM.getUid();
                String domain=AM.getCategory();

                ne.putExtra("NAME",name);
                ne.putExtra("EMPNO",empNo);
                ne.putExtra("REGION",region);
                ne.putExtra("EMAIL",email);
                ne.putExtra("DOMAIN",domain);
                ne.putExtra("PASSWORD",password);
                ne.putExtra("CONTACT",contact);
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

    }

    private void getTcc(String id2) {

        final DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference().child("Scores").child(id2);

        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Approved_Model approved_model=new Approved_Model();
                    tcc=dataSnapshot.getChildrenCount();
                    SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(c);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("tcc", (int) tcc);
                    editor.commit();
                    editor.apply();
                    /*approved_model.setTcc((int) tcc);
                    list.add(approved_model);*/
                    data();
                   //
                }else
                {
                    myView1.txtTCC.setText(Integer.toString(0));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void data() {
        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(c);
        int a=sharedPreferences.getInt("tcc",0);
        myView1.txtTCC.setText(" "+(a));
        Approved_Model approved_model=new Approved_Model();
        String abc= String.valueOf(approved_model.getTcc());
//        Toast.makeText(c,a,Toast.LENGTH_LONG).show();
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
            txtTCC=itemView.findViewById(R.id.txtTCCs);

            removeLayout=itemView.findViewById(R.id.removeLayout);
        }
    }
}
