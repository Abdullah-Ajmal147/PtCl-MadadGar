package com.stepup.madadgaar.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.stepup.madadgaar.R;
import com.stepup.madadgaar.activities.Reporting_Delete_User;
import com.stepup.madadgaar.models.Approved_Model;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.stepup.madadgaar.models.Realm_Model;
import com.stepup.madadgaar.models.TccModel;

import java.util.List;

import io.realm.Realm;


public class Approved_Adapter extends RecyclerView.Adapter<Approved_Adapter.MyView> {

    Context c;
    List<Approved_Model>list;
    boolean test1=true;
    DatabaseReference rootref;
    String uid;
    private long tcc = 0;
    private int test=0;
    Realm realm;
    int a=0,b=1,ca=3,d=4,e=5,f=6,g=7,h=8,i=9;
    MyView myView1;
    SharedPreferences sharedPreferences;
    List<Approved_Model> lstTopics;
    public Approved_Adapter(Context applicationContext, List<Approved_Model> lstTopics, DatabaseReference rootref, Realm realm, SharedPreferences shared1) {
        this.c=applicationContext;
        this.list=lstTopics;
        this.rootref=rootref;
        this.realm=realm;
        this.sharedPreferences=shared1;

    }

    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(c).inflate(R.layout.pending_layout, viewGroup, false);
        return new MyView(v);
    }
    //final DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference().child("Scores");
    @Override
    public void onBindViewHolder(@NonNull final MyView myView, final int i) {
        myView1=myView;
        final MyView localView=myView;
        final Approved_Model AM=list.get(i);
        String id2=AM.getUid();
        if(AM.getEmp_no()!=null &&AM.getEmp_no() != "null") {
            myView.txtemp_no.setText(AM.getEmp_no());
            myView.txtUserName.setText(AM.getFulName());
            myView.txtNumber.setText(Integer.toString(i + 1));
            
            recoverydata(list);




        /*databaseReference2.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(final DataSnapshot da:dataSnapshot.getChildren()) {
                    if(da.exists()) {
                        int data = (int) da.getChildrenCount();
                        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(c);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("tcc", data);
                        editor.apply();
                        data();

                        int a=sharedPreferences.getInt("tcc", 0);

                           myView.txtTCC.setText(Integer.toString(a));
                         //myView.txtTCC.setText(""+(a));
                        *//*if(data>0) {
                            myView.txtTCC.setText(Integer.toString(`   data));
                        }else
                            myView.txtTCC.setText(Integer.toString(00));*//*
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/



           final DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference().child("Scores").child(id2);

            databaseReference2.addValueEventListener(new ValueEventListener() {
                @SuppressLint("SetTextI18n")
                //addListenerForSingleValueEvent
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        tcc=dataSnapshot.getChildrenCount();
                     /*   final Realm_Model realm_model = realm.where(Realm_Model.class).equalTo("uid",dataSnapshot.getKey()).findFirst();
                        if(realm_model!=null) {

                            realm.executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    realm_model.setTcc(tcc);
                                    realm.insertOrUpdate(realm_model);
                                }
                            });
                        }*/

                      /* SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(c);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("tcc", (int) tcc);
                        editor.commit();
                        editor.apply();*/
                     //   approved_model.setTcc((int) tcc);
                        //approved_model1.add(approved_model);
                         //data();
                     //   SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(c);
                       // int a=sharedPreferences.getInt("tcc",00);

                        //||tcc=='1'||tcc=='2'||tcc=='3'||tcc=='4'||tcc=='5'||tcc=='6'||tcc=='7'||tcc=='8'||tcc=='9'
                        if(tcc == a){
                            localView.txtTCC.setText("0" + (tcc));
                        }else if(tcc == b){
                            localView.txtTCC.setText("0" + (tcc));
                        }
                        else if(tcc == ca){
                            localView.txtTCC.setText("0" + (tcc));
                        }
                        else if(tcc == d){
                            localView.txtTCC.setText("0" + (tcc));
                        }
                        else if(tcc == e){
                            localView.txtTCC.setText("0" + (tcc));
                        }
                       else if(tcc == f){
                            localView.txtTCC.setText("0" + (tcc));
                        }
                        else if(tcc == g){
                            localView.txtTCC.setText("0" + (tcc));
                        }
                        else if(tcc == h){
                            localView.txtTCC.setText("0" + (tcc));
                        }
                        else if(tcc == i){
                            localView.txtTCC.setText("0" + (tcc));
                        }
                        else
                            localView.txtTCC.setText("" + (tcc));
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
            /*private void DeleteData() {
                String abc=AM.getUid();
                rootref= FirebaseDatabase.getInstance().getReference("Users").child(abc);
                rootref.removeValue();
                Toast.makeText(c, "Delete Successfully", Toast.LENGTH_LONG).show();
            }*/
        });

    }

    private void recoverydata(List<Approved_Model> list) {

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
        String data = sharedPreferences.getString("Yes",    "");
                if (test1) {

            for (int k = 0; k < list.size(); k++) {
                final Approved_Model approved_model = list.get(k);
                String id3 = approved_model.getUid();

                final DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference().child("Scores").child(id3);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Yes", "");
                editor.apply();
                editor.commit();

                test1=false;

                databaseReference2.addValueEventListener(new ValueEventListener() {
                    @SuppressLint("SetTextI18n")
                    //addListenerForSingleValueEvent
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            tcc = dataSnapshot.getChildrenCount();
                            final Realm_Model realm_model = realm.where(Realm_Model.class).equalTo("uid", dataSnapshot.getKey()).findFirst();
                            if (realm_model != null) {
                                realm.executeTransaction(new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm realm) {
                                        realm_model.setTcc(tcc);
                                        realm.insertOrUpdate(realm_model);
                                    }
                                });
                            }

                        } else {

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }

        }
    }
   /* private void datachange() {
        final DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference().child("Users");
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot empList : snapshot.getChildren()) {
                        Approved_Model model = new Approved_Model();

                        getTcc(empList.child("uid").getValue(toString().getClass()), model);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void getTcc(String id2,final Approved_Model model2) {

        final DatabaseReference data = FirebaseDatabase.getInstance().getReference("Scores").child(id2);

        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Approved_Model approved_model=new Approved_Model();
                    tcc = dataSnapshot.getChildrenCount();
                    model2.setTcc((int) tcc);
                    myView1.txtTCC.setText(approved_model.getTcc());

                   *//* Approved_Model approved_model=new Approved_Model();
                    tcc=dataSnapshot.getChildrenCount();
                    SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(c);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("tcc", (int) tcc);
                    editor.commit();
                    editor.apply();
                    *//**//*approved_model.setTcc((int) tcc);
                    list.add(approved_model);*//**//*
                    data();*//*
                   //
                }else
                {
                    myView1.txtTCC.setText(Integer.toString(0));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/


    public void data() {
        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(c);
        int a=sharedPreferences.getInt("tcc",00);
        myView1.txtTCC.setText("0"+(a));
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
