package com.StepUP.madadgaar.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.StepUP.madadgaar.R;
import com.StepUP.madadgaar.activities.VideoActivity;
import com.StepUP.madadgaar.models.Video_id_Model;
import com.StepUP.madadgaar.utils.Constants;

import java.util.List;

public class SubCattegoryAdapter extends RecyclerView.Adapter<SubCategoryViewHolder> {

    Context c;
    List<Video_id_Model> lstTopics;
    RelativeLayout relativeLayout;
    SubCategoryViewHolder holder;
    String number;
    private String uid;

    public SubCattegoryAdapter(Context c, List<Video_id_Model> topics) {
        this.c = c;
        this.lstTopics = topics;
    }
    @Override
    @NonNull
    public SubCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(c).inflate(R.layout.layout_subcategory_adapter, viewGroup, false);
        return new SubCategoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SubCategoryViewHolder subCategoryViewHolder, int i) {
        holder = subCategoryViewHolder;

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
        uid = sharedPreferences.getString("uid", "null");

        final Video_id_Model video_id_model = lstTopics.get(i);
        String playListId = video_id_model.getSub_playlist1();
        String totalQuiz = "5";
        subCategoryViewHolder.txtTopicTitle.setText(video_id_model.getSub_title1());
        subCategoryViewHolder.txtnumberIncrease.setText(Integer.toString(i + 1) + ".  ");
        //subCategoryViewHolder.checkbox.findViewById(R.id.checkbox);
        calculateResult(playListId, 5, holder);

        /*subCategoryViewHolder.txthome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ne =new Intent(c, After_login.class);
                c.startActivity(ne);
            }
        });
        subCategoryViewHolder.aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ne =new Intent(c, About_Us.class);
                c.startActivity(ne);
            }
        });
        subCategoryViewHolder.ContacUs.setOnClickListener(new View.OnClickListener() {
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
              *//*  i.setAction(Intent.ACTION_SEND);
                i.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                i.setType("text/plain");*//*
                i.setData(Uri.parse(url));
                c.startActivity(i);
            }
        });
        subCategoryViewHolder.Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        subCategoryViewHolder.Button_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.mdrawerlayout.openDrawer(Gravity.LEFT);
            }
        });*/

        holder.txtTopicTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(c, VideoActivity.class);
                String PLAYLIST_COMMUNICATION = video_id_model.getSub_playlist1();
                String FIRST_VIDEO_ID_COMMUNICATION = video_id_model.getVideo_id();
                String title = video_id_model.getVideoTitle();
                String MainTitle = video_id_model.getSub_title1();
                String video_Counter = video_id_model.getVideo_Counter();
                String quiz = video_id_model.getQuiz();
                // String FIRST_VIDEO_ID_COMMUNICATION="hU8Uz3kAzTQ";

                intent.putExtra("playListId", PLAYLIST_COMMUNICATION);
                intent.putExtra("first_video_id", FIRST_VIDEO_ID_COMMUNICATION);
                intent.putExtra("videos_count", video_Counter);
                intent.putExtra("list_video_id", "false");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("selected_category", R.color.yallow_dark);
                intent.putExtra("quiz", quiz);

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("subCategoryName", MainTitle);
                editor.commit();

                intent.putExtra("MainTitle", MainTitle);
                intent.putExtra("title", title);

                c.startActivity(intent);
            }

        });
      //  ((Activity)c).finish();

    }

    private void calculateResult(String playListId, final int totalQuiz, final SubCategoryViewHolder holder1 ) {
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(Constants.SCORE).child(uid)
                .child(playListId);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                 holder.checkbox.setBackgroundResource(0);

                if (dataSnapshot.exists()){
                    // holder.checkbox.setBackgroundResource(0);
                    int correctAnswers = (int) dataSnapshot.getChildrenCount();
                    int result =  (correctAnswers * 100) / totalQuiz ;
                    if (result>=80) {
                        holder1.checkbox.setBackgroundResource(R.drawable.tick_icon);

                       // Toast.makeText(c,"FOund",Toast.LENGTH_LONG).show();

                    }
                    else if (result<80){
                   // {
                        holder1.checkbox.setBackgroundResource(R.drawable.play_icon);
                       // Toast.makeText(c,"Null",Toast.LENGTH_LONG).show();

                    }

                    //}
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return lstTopics.size();
    }

}
