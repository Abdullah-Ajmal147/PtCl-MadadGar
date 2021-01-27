package com.stepup.madadgaar.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.stepup.madadgaar.R;
import com.stepup.madadgaar.utils.Constants;

import java.util.HashMap;
import java.util.Map;

import static com.stepup.madadgaar.utils.Constants.PLAYLIST_COMMUNICATION;
import static com.stepup.madadgaar.utils.Constants.PLAYLIST_CUSTOMER_SERVICE;
import static com.stepup.madadgaar.utils.Constants.PLAYLIST_GROOMING;
import static com.stepup.madadgaar.utils.Constants.PLAYLIST_SAFTY;
import static com.stepup.madadgaar.utils.Constants.PLAYLIST_WORK_PLACE_ETHICS;

public class SubCategoryActivity extends AppCompatActivity {

    private static final String FIRST_VIDEO_ID_COMMUNICATION = "C5e3JGEXbCE";
    private static final String FIRST_VIDEO_ID_WORK_PLACE_ETHICS = "PhrJVneQ-d8";
    private static final String FIRST_VIDEO_ID_CUSTOMER_SERVICE = "hU8Uz3kAzTQ";
    private static final String FIRST_VIDEO_ID_GROOMING = "LrrSr_pBvPI";
    private static final String FIRST_VIDEO_ID_SAFTY = "G3hsSjIIQp8";

    private String uid;
    private ProgressBar communicationScoreBar,customerServiceScoreBar,saftyScoreBar,groomingScoreBar,ethicsScoreBar;
    private TextView scoreCommunication,scoreCustomerServices,scoreSafty,scoreGrooming,scoreEthics;
    private ConstraintLayout communication,services,grooming,safty,ethics;

    private TextView txtCommunication,txtService,txtGrooming,txtSafty,txtEthics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        uid = sharedPreferences.getString("uid","null");

        txtCommunication = findViewById(R.id.title);
        txtService = findViewById(R.id.title2);
        txtGrooming = findViewById(R.id.titleGrooming);
        txtSafty = findViewById(R.id.titleSafty);
        txtEthics = findViewById(R.id.titleWork_place_ethics);

        communication = findViewById(R.id.communication);
        services = findViewById(R.id.customer_service);
        grooming = findViewById(R.id.grooming);
        safty = findViewById(R.id.safty);
        ethics = findViewById(R.id.work_place_ethics);

        communicationScoreBar = findViewById(R.id.scoreBar);
        customerServiceScoreBar = findViewById(R.id.scoreBarCustomerServices);
        saftyScoreBar = findViewById(R.id.scoreBarSafty);
        groomingScoreBar = findViewById(R.id.scoreBarGrooming);
        ethicsScoreBar = findViewById(R.id.scoreBarWork_place_ethics);

        scoreCommunication = findViewById(R.id.scorePercent);
        scoreCustomerServices = findViewById(R.id.scorePercentCustomerServices);
        scoreSafty = findViewById(R.id.scorePercentSafty);
        scoreGrooming = findViewById(R.id.scorePercentGrooming);
        scoreEthics = findViewById(R.id.scorePercentWork_place_ethics);

        SharedPreferences sharedPreferences1 = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences1.edit();
        editor.putString("category","soft");
        editor.commit();

        calculateResult(PLAYLIST_COMMUNICATION,5,communicationScoreBar,scoreCommunication,communication);
        calculateResult(PLAYLIST_CUSTOMER_SERVICE,5,customerServiceScoreBar,scoreCustomerServices,services);
        calculateResult(PLAYLIST_SAFTY,10,saftyScoreBar,scoreSafty,safty);
        calculateResult(PLAYLIST_GROOMING,10,groomingScoreBar,scoreGrooming,grooming);
        calculateResult(PLAYLIST_WORK_PLACE_ETHICS,5,ethicsScoreBar,scoreEthics,ethics);

        txtEthics.setSelected(true);
        txtSafty.setSelected(true);
        txtGrooming.setSelected(true);
        txtCommunication.setSelected(true);
        txtService.setSelected(true);

    }

    private void calculateResult(String playListId, final int totalQuiz, final ProgressBar scoreBar, final TextView score,
                                 final ConstraintLayout container) {
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(Constants.SCORE).child(uid)
                .child(playListId);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){

                    container.setBackgroundResource(0);
                    score.setBackgroundResource(0);
                    int correctAnswers = (int) dataSnapshot.getChildrenCount();
                    int result =  (correctAnswers * 100) / totalQuiz ;
                    if (result>=80){
                        container.setBackgroundResource(R.color.pass_color);
                        score.setBackgroundResource(R.drawable.pass);
                        scoreBar.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.green_parent)));
                        scoreBar.setProgress(result);

                    }
                    else {
                        container.setBackgroundResource(R.color.fail_color);
                        score.setBackgroundResource(R.drawable.fail);
                        scoreBar.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.red_parent)));
                        scoreBar.setProgress(result);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void startPlayingVideo(final String title) {

                Intent intent = new Intent(SubCategoryActivity.this, VideoActivity.class);

                switch (title){
                    case Constants.COMMUNICATION:
                        currentPlayList(Constants.COMMUNICATION);
                        intent.putExtra("playListId",PLAYLIST_COMMUNICATION);
                        intent.putExtra("first_video_id",FIRST_VIDEO_ID_COMMUNICATION);
                        intent.putExtra("videos_count",3);
                        intent.putExtra("list_video_id","false");
                        break;
                    case Constants.CUSTOMER_SERVICE:
                        currentPlayList(Constants.CUSTOMER_SERVICE);
                        intent.putExtra("playListId",PLAYLIST_CUSTOMER_SERVICE);
                        intent.putExtra("first_video_id",FIRST_VIDEO_ID_CUSTOMER_SERVICE);
                        intent.putExtra("videos_count",2);
                        intent.putExtra("list_video_id","false");
                        break;
                    case Constants.GROOMING:
                        currentPlayList(Constants.GROOMING);
                        intent.putExtra("playListId",PLAYLIST_GROOMING);
                        intent.putExtra("first_video_id",FIRST_VIDEO_ID_GROOMING);
                        intent.putExtra("videos_count",5);
                        intent.putExtra("list_video_id","false");
                        break;
                    case Constants.SAFTY:
                        currentPlayList(Constants.SAFTY);
                        intent.putExtra("playListId",PLAYLIST_SAFTY);
                        intent.putExtra("first_video_id",FIRST_VIDEO_ID_SAFTY);
                        intent.putExtra("videos_count",5);
                        intent.putExtra("list_video_id","false");
                        break;
                    case Constants.WORK_PLACE_ETHICS:
                        currentPlayList(Constants.WORK_PLACE_ETHICS);
                        intent.putExtra("playListId",PLAYLIST_WORK_PLACE_ETHICS);
                        intent.putExtra("first_video_id",FIRST_VIDEO_ID_WORK_PLACE_ETHICS);
                        intent.putExtra("videos_count",2);
                        intent.putExtra("list_video_id","false");
                        break;
                }

                intent.putExtra("selected_category",R.color.yallow_dark);
                intent.putExtra("title",title);
                startActivity(intent);
    }

    private void currentPlayList(String playList){
        SharedPreferences sharedPreferences1 = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences1.edit();
        editor.putString("currentPlayList",playList);
        editor.commit();
    }

    public void playListCommunication(View view) {
        watched(PLAYLIST_COMMUNICATION,FIRST_VIDEO_ID_COMMUNICATION);
        startPlayingVideo(Constants.COMMUNICATION);
    }

    public void playListSafty(View view) {
        watched(PLAYLIST_SAFTY,FIRST_VIDEO_ID_SAFTY);
        startPlayingVideo(Constants.SAFTY);
    }

    public void playListGrooming(View view) {
        watched(PLAYLIST_GROOMING,FIRST_VIDEO_ID_GROOMING);
        startPlayingVideo(Constants.GROOMING);
    }

    public void playListCustomerServices(View view) {
        watched(PLAYLIST_CUSTOMER_SERVICE,FIRST_VIDEO_ID_CUSTOMER_SERVICE);
        startPlayingVideo(Constants.CUSTOMER_SERVICE);
    }

    public void playListWorkPlaceEthics(View view) {
        watched(PLAYLIST_WORK_PLACE_ETHICS,FIRST_VIDEO_ID_WORK_PLACE_ETHICS);
        startPlayingVideo(Constants.WORK_PLACE_ETHICS);
    }

    private void watched(String play_list_id, String vedio_id){
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
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this,CategoryActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
