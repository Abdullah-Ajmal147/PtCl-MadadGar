package com.StepUP.madadgaar.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.StepUP.madadgaar.R;
import com.StepUP.madadgaar.utils.Constants;

public class LevelCompletedActivity extends AppCompatActivity {

    private String playlistName;
    private Button btn_next;
    private String uid;
    private ImageView imageView,darja,starOne,starTwo,starThree;
    private LinearLayout hasil_karda_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_completed);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        uid = sharedPreferences.getString("uid","null");
        SharedPreferences sharedPreferences1 = PreferenceManager.getDefaultSharedPreferences(this);
        playlistName = sharedPreferences1.getString("currentPlayList","null");

        starOne = findViewById(R.id.starOne);
        starTwo = findViewById(R.id.starTwo);
        starThree = findViewById(R.id.starThree);
        btn_next = findViewById(R.id.btnNext);
        imageView = findViewById(R.id.levelIcon);
        darja = findViewById(R.id.leveCompleted);
        hasil_karda_number = findViewById(R.id.hasil_karda_number);

        if (playlistName.equals(Constants.CONFIGURATION_DEMO)){
            calculateResult(Constants.PLAYLIST_CONFIGURATION_DEMOS,5,R.drawable.installation_and_configuration);
        }
        if (playlistName.equals(Constants.COPPER_NETWORKS)){
            calculateResult(Constants.PLAYLIST_COPPER_NETWORKS,5,R.drawable.copper_bsed_netwrok);
        }

        // Soft
        if (playlistName.equals(Constants.COMMUNICATION)){
            calculateResult(Constants.PLAYLIST_COMMUNICATION,5,R.drawable.basic_of_communication);
        }
        if (playlistName.equals(Constants.CUSTOMER_SERVICE)){
            calculateResult(Constants.PLAYLIST_CUSTOMER_SERVICE,5,R.drawable.customer_service_result);
        }
        if (playlistName.equals(Constants.SAFTY)){
            calculateResult(Constants.PLAYLIST_SAFTY,10,R.drawable.safety);
        }
        if (playlistName.equals(Constants.GROOMING)){
            calculateResult(Constants.PLAYLIST_GROOMING,10,R.drawable.grooming_result);
        }
        if (playlistName.equals(Constants.WORK_PLACE_ETHICS)){
            calculateResult(Constants.PLAYLIST_WORK_PLACE_ETHICS,5,R.drawable.work_place_ethics);
        }
        if (playlistName.equals(Constants.FIBER_TEST_EQUIPMENT)){
            calculateResult(Constants.PLAYLIST_FIBER_TEST_EQUIPMENT,5,R.drawable.fibre_test_equipment);
        }
        if (playlistName.equals(Constants.COPPER_TEST_EQUIPMENTS)){
            calculateResult(Constants.PLAYLIST_COPPER_TEST_EQUIPMENT,5,R.drawable.copper_test_equipment);
        }
        if (playlistName.equals(Constants.GPON_INSTALLATION)){
            calculateResult(Constants.PLAYLIST_GPON_INSTALLATION,5,R.drawable.gpon_result);
        }
        if (playlistName.equals(Constants.OPTICAL_FIBER)){
            calculateResult(Constants.PLAYLIST_OPTICAL_FIBER,5,R.drawable.optical_fiber_jointing);
        }
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences1 = PreferenceManager.getDefaultSharedPreferences(LevelCompletedActivity.this);
                String category = sharedPreferences1.getString("category","");
                if (category.equals("technical")) {
                    Intent intent = new Intent(LevelCompletedActivity.this,TechnicalActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(LevelCompletedActivity.this, SubCategoryActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        });
    }
    private void calculateResult(String playListId, final int totalQuiz, int icon) {
        imageView.setBackgroundResource(0);
        imageView.setBackgroundResource(icon);
        darja.setBackgroundResource(0);
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(Constants.SCORE).child(uid)
                .child(playListId);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){

                    int correctAnswers = (int) dataSnapshot.getChildrenCount();
//                    int result =  (correctAnswers * 100) / totalQuiz ;
                    if (correctAnswers>=5){
                        darja.setBackgroundResource(R.drawable.darja_mukammal);
                        starOne.setBackgroundResource(0);
                        starTwo.setBackgroundResource(0);
                        starThree.setBackgroundResource(0);
                        starOne.setBackgroundResource(R.drawable.ic_star_fill);
                        starTwo.setBackgroundResource(R.drawable.ic_star_fill);
                        starThree.setBackgroundResource(R.drawable.ic_star_fill);
                    }
                    if (correctAnswers==4){
                        darja.setBackgroundResource(R.drawable.darja_mukammal);
                        starOne.setBackgroundResource(0);
                        starTwo.setBackgroundResource(0);
                        starOne.setBackgroundResource(R.drawable.ic_star_fill);
                        starTwo.setBackgroundResource(R.drawable.ic_star_fill);
                    }
                    if (correctAnswers<4){
                        darja.setBackgroundResource(R.drawable.darja_na_mukammal);
                        starOne.setBackgroundResource(0);
                        starOne.setBackgroundResource(R.drawable.ic_star_fill);
                        btn_next.setText("دوبارہ کریں");
                    }
//                    if (result<80){
//                        darja.setBackgroundResource(R.drawable.darja_na_mukammal);
//                    }
//                    else {
//                        darja.setBackgroundResource(R.drawable.darja_mukammal);
//                        hasil_karda_number.setVisibility(View.VISIBLE);
//                    }

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent ne = new Intent(getApplicationContext(), Sub_Mark.class);
        startActivity(ne);

        /*SharedPreferences sharedPreferences1 = PreferenceManager.getDefaultSharedPreferences(LevelCompletedActivity.this);
        String category = sharedPreferences1.getString("category","");
        if (category.equals("technical")) {
            Intent intent = new Intent(LevelCompletedActivity.this,TechnicalActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(LevelCompletedActivity.this, SubCategoryActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }*/
    }
}
