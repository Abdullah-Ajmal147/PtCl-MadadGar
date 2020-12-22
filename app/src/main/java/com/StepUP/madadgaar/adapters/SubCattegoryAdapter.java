package com.StepUP.madadgaar.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
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
        subCategoryViewHolder.txtTopicTitle.setTypeface(Typeface.create("poppins_extrabold",Typeface.BOLD));
        subCategoryViewHolder.txtnumberIncrease.setText(Integer.toString(i + 1) + ".  ");
        //subCategoryViewHolder.checkbox.findViewById(R.id.checkbox);
        calculateResult(playListId, 5, holder);


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
                editor.apply();

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
