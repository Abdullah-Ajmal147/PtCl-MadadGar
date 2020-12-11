package com.StepUP.madadgaar.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Dialog;
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
import com.StepUP.madadgaar.R;
import com.StepUP.madadgaar.utils.Constants;

import java.util.HashMap;
import java.util.Map;

import static com.StepUP.madadgaar.utils.Constants.PLAYLIST_CONFIGURATION_DEMOS;
import static com.StepUP.madadgaar.utils.Constants.PLAYLIST_COPPER_NETWORKS;
import static com.StepUP.madadgaar.utils.Constants.PLAYLIST_COPPER_TEST_EQUIPMENT;
import static com.StepUP.madadgaar.utils.Constants.PLAYLIST_FIBER_TEST_EQUIPMENT;
import static com.StepUP.madadgaar.utils.Constants.PLAYLIST_GPON_INSTALLATION;
import static com.StepUP.madadgaar.utils.Constants.PLAYLIST_OPTICAL_FIBER;

public class TechnicalActivity extends AppCompatActivity {

    private static final String FIRST_VIDEO_ID_CONFIGURATION_DEMOS = "PLq9TNzZPsJ4ko16V0t9cD3TCvhdh8JtYb";//"EiUSB4dA6T8";
    private static final String FIRST_VIDEO_ID_COPPER_NETWORKS = "AbL6WcrS8FE";
    private static final String FIRST_VIDEO_ID_FIBER_TEST_EQUIPMENT = "9Wluojvwbfk";
    private static final String FIRST_VIDEO_ID_COPPER_TEST_EQUIPMENT = "8zLWufxyp2U";
    private static final String FIRST_VIDEO_ID_GPON_INSTALLATION = "8GvKPI3PXWU";
    private static final String FIRST_VIDEO_ID_OPTICAL_FIBER = "6bFfa6Qhl64";

    private String uid;
    private ProgressBar configurationScoreBar,copperNetworkScoreBar,fiberTestScoreBar,copperTestScoreBar,gponScoreBar,opticalScoreBar;
    private TextView scoreConfiguration,scoreCopperNetworks,scoreFireTest,scoreCopperTest,scoreGpon,scoreOptical;
    private ConstraintLayout configuration,copperNetwork,fiberTest,copperTest,gpon,optical;

    private TextView txtConfiguration,txtCopperNetwork,txtFiberNetWork,txtCopperTest,txtGpon,txtOptical;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technical);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        uid = sharedPreferences.getString("uid","null");

        SharedPreferences sharedPreferences1 = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences1.edit();
        editor.putString("category","technical");
        editor.commit();

        txtConfiguration = findViewById(R.id.titleConfiguration);
        txtCopperNetwork = findViewById(R.id.titleCopper_network);
        txtFiberNetWork = findViewById(R.id.titleFiber_test_equipment);
        txtCopperTest = findViewById(R.id.titleCopper_test_equipment);
        txtGpon = findViewById(R.id.titleGpon_installation);
        txtOptical = findViewById(R.id.titleOptical_fiber);

        configuration = findViewById(R.id.configuration);
        copperNetwork = findViewById(R.id.copper_network);
        fiberTest = findViewById(R.id.fiber_test_equipment);
        copperTest = findViewById(R.id.copper_test_equipment);
        gpon = findViewById(R.id.gpon_installation);
        optical = findViewById(R.id.optical_fiber);

//        textView.setAnimation(AnimationUtils.loadAnimation(this,R.anim.text_animation));

        scoreConfiguration = findViewById(R.id.scorePercentConfiguration);
        scoreCopperNetworks = findViewById(R.id.scorePercentCopper_network);
        scoreFireTest = findViewById(R.id.scorePercentFiber_test_equipment);
        scoreCopperTest = findViewById(R.id.scorePercentCopper_test_equipment);
        scoreGpon = findViewById(R.id.scorePercentGpon_installation);
        scoreOptical = findViewById(R.id.scorePercentOptical_fiber);

        configurationScoreBar = findViewById(R.id.scoreBarConfiguration);
        copperNetworkScoreBar = findViewById(R.id.scoreBarCopper_network);
        fiberTestScoreBar = findViewById(R.id.scoreBarFiber_test_equipment);
        copperTestScoreBar = findViewById(R.id.scoreBarCopper_test_equipment);
        gponScoreBar = findViewById(R.id.scoreBarGpon_installation);
        opticalScoreBar = findViewById(R.id.scoreBarOptical_fiber);

        calculateResult(PLAYLIST_CONFIGURATION_DEMOS,5,configurationScoreBar,scoreConfiguration,configuration);
        calculateResult(PLAYLIST_COPPER_NETWORKS,5,copperNetworkScoreBar,scoreCopperNetworks,copperNetwork);
        calculateResult(PLAYLIST_FIBER_TEST_EQUIPMENT,5,fiberTestScoreBar,scoreFireTest,fiberTest);
        calculateResult(PLAYLIST_COPPER_TEST_EQUIPMENT,5,copperTestScoreBar,scoreCopperTest,copperTest);
        calculateResult(PLAYLIST_GPON_INSTALLATION,5,gponScoreBar,scoreGpon,gpon);
        calculateResult(PLAYLIST_OPTICAL_FIBER,5,opticalScoreBar,scoreOptical,optical);

        txtConfiguration.setSelected(true);
        txtOptical.setSelected(true);
        txtGpon.setSelected(true);
        txtCopperTest.setSelected(true);
        txtFiberNetWork.setSelected(true);
        txtCopperNetwork.setSelected(true);

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
        final Dialog dialog = new Dialog(TechnicalActivity.this);
        dialog.setContentView(R.layout.popup);

                Intent intent = new Intent(TechnicalActivity.this, VideoActivity.class);
                switch (title){
                    case Constants.CONFIGURATION_DEMO:
                        currentPlayList(Constants.CONFIGURATION_DEMO);
                        intent.putExtra("playListId",PLAYLIST_CONFIGURATION_DEMOS);
                        intent.putExtra("first_video_id",FIRST_VIDEO_ID_CONFIGURATION_DEMOS);
                        intent.putExtra("videos_count",4);
                        intent.putExtra("list_video_id","false");
                        break;
                    case Constants.COPPER_NETWORKS:
                        currentPlayList(Constants.COPPER_NETWORKS);
                        intent.putExtra("playListId",PLAYLIST_COPPER_NETWORKS);
                        intent.putExtra("first_video_id",FIRST_VIDEO_ID_COPPER_NETWORKS);
                        intent.putExtra("videos_count",6);
                        intent.putExtra("list_video_id","false");
                        break;
                    case Constants.FIBER_TEST_EQUIPMENT:
                        currentPlayList(Constants.FIBER_TEST_EQUIPMENT);
                        intent.putExtra("playListId",PLAYLIST_FIBER_TEST_EQUIPMENT);
                        intent.putExtra("first_video_id",FIRST_VIDEO_ID_FIBER_TEST_EQUIPMENT);
                        intent.putExtra("videos_count",4);
                        intent.putExtra("list_video_id","false");
                        break;
                    case Constants.COPPER_TEST_EQUIPMENTS:
                        currentPlayList(Constants.COPPER_TEST_EQUIPMENTS);
                        intent.putExtra("playListId",PLAYLIST_COPPER_TEST_EQUIPMENT);
                        intent.putExtra("first_video_id",FIRST_VIDEO_ID_COPPER_TEST_EQUIPMENT);
                        intent.putExtra("videos_count",6);
                        intent.putExtra("list_video_id","false");
                        break;
                    case Constants.GPON_INSTALLATION:
                        currentPlayList(Constants.GPON_INSTALLATION);
                        intent.putExtra("playListId",PLAYLIST_GPON_INSTALLATION);
                        intent.putExtra("first_video_id",FIRST_VIDEO_ID_GPON_INSTALLATION);
                        intent.putExtra("videos_count",5);
                        intent.putExtra("list_video_id","false");
                        break;
                    case Constants.OPTICAL_FIBER:
                        currentPlayList(Constants.OPTICAL_FIBER);
                        intent.putExtra("playListId",PLAYLIST_OPTICAL_FIBER);
                        intent.putExtra("first_video_id",FIRST_VIDEO_ID_OPTICAL_FIBER);
                        intent.putExtra("videos_count",5);
                        intent.putExtra("list_video_id","false");
                        break;
                }

                intent.putExtra("selected_category",R.color.yallow_dark);
                intent.putExtra("title",title);
                startActivity(intent);
                finish();
            }

    private void currentPlayList(String playList){
        SharedPreferences sharedPreferences1 = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences1.edit();
        editor.putString("currentPlayList",playList);
        editor.commit();
    }

    public void playListConfiguration(View view) {
        watched(PLAYLIST_CONFIGURATION_DEMOS,FIRST_VIDEO_ID_CONFIGURATION_DEMOS);
        startPlayingVideo(Constants.CONFIGURATION_DEMO);
    }

    public void playListCopperNetworks(View view) {
        watched(PLAYLIST_COPPER_NETWORKS,FIRST_VIDEO_ID_COPPER_NETWORKS);
        startPlayingVideo(Constants.COPPER_NETWORKS);
    }

    public void playListFiberTestEquipment(View view) {
        watched(PLAYLIST_FIBER_TEST_EQUIPMENT,FIRST_VIDEO_ID_FIBER_TEST_EQUIPMENT);
        startPlayingVideo(Constants.FIBER_TEST_EQUIPMENT);
    }

    public void playListCopperTestEquipment(View view) {
        watched(PLAYLIST_COPPER_TEST_EQUIPMENT,FIRST_VIDEO_ID_COPPER_TEST_EQUIPMENT);
        startPlayingVideo(Constants.COPPER_TEST_EQUIPMENTS);
    }

    public void playListGponInstallation(View view) {
        watched(PLAYLIST_GPON_INSTALLATION,FIRST_VIDEO_ID_GPON_INSTALLATION);
        startPlayingVideo(Constants.GPON_INSTALLATION);
    }

    public void playListOpticalFiber(View view) {
        watched(PLAYLIST_OPTICAL_FIBER,FIRST_VIDEO_ID_OPTICAL_FIBER);
        startPlayingVideo(Constants.OPTICAL_FIBER);
    }

    private void watched(String play_list_id,String vedio_id){
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
