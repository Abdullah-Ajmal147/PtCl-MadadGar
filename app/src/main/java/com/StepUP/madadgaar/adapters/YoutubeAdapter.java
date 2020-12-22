package com.StepUP.madadgaar.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.StepUP.madadgaar.R;
import com.StepUP.madadgaar.activities.Done_Activity;
import com.StepUP.madadgaar.activities.QuizActivity;
import com.StepUP.madadgaar.models.No_QuizModel;
import com.StepUP.madadgaar.models.YoutubeData;
import com.StepUP.madadgaar.utils.Constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class  YoutubeAdapter extends RecyclerView.Adapter<YoutubeAdapter.YoutubeViewHolder> {

    private Context context;
    private List<YoutubeData> playlistItems;
    private int selected_category_color;
    private String play_list_id;
    private String play_list_title;
    private String playing_video_id;
    private String uid;
    private boolean quizAble;
    private int videos_count;
    TextView title;
   // String quiz;
    DatabaseReference rootRef;
    public YoutubeAdapter(Context context, List<YoutubeData> playlistItems, String play_list_id, String play_list_title,
                          String playing_video_id, boolean quizAble, int videos_count,TextView title,DatabaseReference rootRef) {
        this.context = context;
        this.playlistItems = playlistItems;
        this.selected_category_color = selected_category_color;
        this.play_list_id = play_list_id;
        this.play_list_title = play_list_title;
        this.playing_video_id = playing_video_id;
        this.quizAble = quizAble;
        this.videos_count = videos_count;
        this.title=title;
      //  this.quiz=quiz;
        this.rootRef=rootRef;


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        uid = sharedPreferences.getString("uid","null");
    }


    @Override
    public int getItemViewType(int position) {
        return (position == playlistItems.size()) ? R.layout.start_quiz_button_layout : R.layout.video_layout;
    }

    @NonNull
    @Override
    public YoutubeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = null;
        if (viewType == R.layout.video_layout) {
            view = (LayoutInflater.from(context).inflate(R.layout.video_layout, parent, false));
        } else {
            view = (LayoutInflater.from(context).inflate(R.layout.start_quiz_button_layout, parent, false));
        }
        return new YoutubeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final YoutubeViewHolder holder, final int position) {


        if (position == playlistItems.size()) {

            holder.startQuiz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String data="not_Complete";
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                    String abc = sharedPreferences.getString("complete","null");
                    //Toast.makeText(context,abc,Toast.LENGTH_LONG).show();
                    if(abc.equals("complete")) {
                        //   String abc="no";//&& quiz!=null && quiz!="null"
                        if (playlistItems.get(position-1).getQuiz() != null && playlistItems.get(position-1).getQuiz().equals("no") && playlistItems.get(position-1).getQuiz() != "null") {
                            Intent ne = new Intent(context, Done_Activity.class);
                            boolean ques1 = true;
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("complete", data);
                            editor.commit();
                            editor.apply();
                            uid = sharedPreferences.getString("uid", "null");
                            No_QuizModel no_quizModel = new No_QuizModel();
                            no_quizModel.setQuestion1(ques1);
                            no_quizModel.setQuestion2(ques1);
                            no_quizModel.setQuestion3(ques1);
                            no_quizModel.setQuestion4(ques1);
                            no_quizModel.setQuestion5(ques1);
                            rootRef.child("Scores").child(uid).child(play_list_id).setValue(no_quizModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    //Toast.makeText(context,"Successfuly added Video",Toast.LENGTH_LONG).show();

                                }
                            });
                            context.startActivity(ne);

                        } else {
                            Intent intent = new Intent(context, QuizActivity.class);
                            intent.putExtra("vedio_id", playing_video_id);
                            intent.putExtra("playListId", play_list_id);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("complete", data);
                            editor.commit();
                            editor.apply();
                            context.startActivity(intent);
                            ((Activity) context).finish();
                        }
                    }else {
                        Toast.makeText(context,"Please First Watch Complete Videos" ,Toast.LENGTH_LONG).show();
                    }


                   /* if (!quizAble){
                        Toast.makeText(context,"Frist of All Please watch the Complete Videos",Toast.LENGTH_LONG).show();
                    }
                    final Dialog dialog = new Dialog(context);*/
                   // dialog.setContentView(R.layout.popup);

                    /*dialog.findViewById(R.id.btnClose).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });*/


                   /* dialog.findViewById(R.id.btnOk).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            Toast.makeText(context,"Video Not Upload",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(context, QuizActivity.class);
                            intent.putExtra("vedio_id", playing_video_id);
                            intent.putExtra("playListId", play_list_id);
                            context.startActivity(intent);

                            dialog.dismiss();
                        }
                    });*/
                    /*if (quizAble) {
                        dialog.show();
                    }*/
                }
            });
        } else {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            String reTitle=sharedPreferences.getString("re_title","");

            /*if(reTitle.equals(""))
            {
                title.setText(reTitle);
            }else {*/
/*       if(reTitle!=null && reTitle.equals("")) {
                play_list_title=reTitle;
                title.setText(play_list_title);
            }

       else {
                title.setText(play_list_title);
            }
            //Toast.makeText(c)
            title.setText(play_list_title);*/
            holder.title.setText(playlistItems.get(position).getTitle());

            if (playlistItems.get(position).getVedio_id().equals(playing_video_id)) {

                //holder.thumbnail.setBackgroundResource(0);
              //  holder.thumbnail.setBackgroundResource(R.drawable.playing);
//                Glide.with(context).load(R.drawable.playL).getThumbnail()).into(holder.thumbnail);
            }
/*
            holder.videoContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    watched(playlistItems.get(position).getVedio_id());

                    Intent intent = new Intent(context, VideoActivity.class);
                    intent.putExtra("list_video_id", playlistItems.get(position).getVedio_id());
                    intent.putExtra("playListId", play_list_id);
                    intent.putExtra("title", play_list_title);
                    intent.putExtra("videos_count",videos_count);
                    context.startActivity(intent);
                    ((Activity) context).finish();

                }
            });*/
        }
    }

    /*private void watched(String vedio_id) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(Constants.WATCHED)
                .child(uid).child(play_list_id);

        Map<String,Object> addToWatched = new HashMap<>();
        addToWatched.put(vedio_id,"true");

        databaseReference.updateChildren(addToWatched).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }*/

    @Override
    public int getItemCount() {
        return playlistItems.size() + 1;
    }

    public class YoutubeViewHolder extends RecyclerView.ViewHolder {

        //CircleImageView thumbnail;
        TextView title;
        ConstraintLayout videoContainer;
        Button startQuiz;

        public YoutubeViewHolder(@NonNull View itemView) {
            super(itemView);

           // thumbnail = itemView.findViewById(R.id.playVideothumb);
            title = itemView.findViewById(R.id.thumbText);
            videoContainer = itemView.findViewById(R.id.videoContainer);
            startQuiz = itemView.findViewById(R.id.startQuiz);

        }
    }
}
