package com.stepup.madadgaar.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ValueEventListener;
import com.stepup.madadgaar.R;
import com.stepup.madadgaar.adapters.QuestionsAdapter;
import com.stepup.madadgaar.models.QuestionModel;
import com.stepup.madadgaar.utils.Constants;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private QuestionsAdapter adapter;
    private List<QuestionModel> questionsList = new ArrayList<>();
    private String video_id,playListId;
    private int size = 1;
    private TextView btn_next,btn_show_results;
    private String playlistName;
    private int pages;
    private String uid;
//    private TextView counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        btn_next = findViewById(R.id.btnNextQuestion);
        btn_show_results = findViewById(R.id.showResults);
//        counter = findViewById(R.id.counter);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        uid = sharedPreferences.getString("uid","null");

        video_id = getIntent().getStringExtra("vedio_id");
        playListId = getIntent().getStringExtra("playListId");
        playlistName = getIntent().getStringExtra("playlistName");

        viewPager = findViewById(R.id.viewPager);
                      viewPager.beginFakeDrag();
        getQuestions(playListId);
        adapter = new QuestionsAdapter(QuizActivity.this, questionsList, video_id, playListId);
        viewPager.setAdapter(adapter);

//        counter.setText(viewPager.getCurrentItem()+1+"/8");



        btn_show_results.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(Constants.SCORE).child(uid)
                        .child(playListId);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                 holder.checkbox.setBackgroundResource(0);
                        int totalQuiz=5;

                        if (dataSnapshot.exists()){
                            // holder.checkbox.setBackgroundResource(0);
                            int correctAnswers = (int) dataSnapshot.getChildrenCount();
                            int result =  (correctAnswers * 100) / totalQuiz ;
                            if (result>=80) {
                                //Toast.makeText(getApplicationContext(),"FOund",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(QuizActivity.this,Done_Activity.class);
                                /*intent.putExtra("playlistName",video_id);
                                intent.putExtra("playlistName",playListId);
                                intent.putExtra("playlistName",playlistName);*/
                                startActivity(intent);
                                finish();

                            }
                            else if (result<80){
                                // {
                                Intent intent = new Intent(QuizActivity.this,Not_done.class);
                               // Toast.makeText(getApplicationContext(),"Null",Toast.LENGTH_LONG).show();
                                startActivity(intent);
                                finish();
                            }
                            //}
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideMethode();
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, false);
            }
        });
//        btn_previous.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1, true);
//            }
//        });

        questionChange();

    }

    private void questionChange() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pages = viewPager.getAdapter().getCount();

//                counter.setText(viewPager.getCurrentItem()+1+"/8");
                if (size == viewPager.getCurrentItem()+1) {


                    btn_next.setVisibility(View.INVISIBLE);
                    btn_show_results.setVisibility(View.VISIBLE);
//                    btn_previous.setVisibility(View.VISIBLE);
//                    nextIcon.setVisibility(View.INVISIBLE);
//                    backIcon.setVisibility(View.VISIBLE);
                }
                else if (viewPager.getCurrentItem() == 5) {

//                    btn_next.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(),"ViewPager ",Toast.LENGTH_LONG).show();
//                    btn_previous.setVisibility(View.INVISIBLE);
//                    nextIcon.setVisibility(View.VISIBLE);
//                    backIcon.setVisibility(View.INVISIBLE);
                }
                else {
//
//                    btn_next.setVisibility(View.VISIBLE);
//                    btn_previous.setVisibility(View.VISIBLE);
//                    nextIcon.setVisibility(View.VISIBLE);
//                    backIcon.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setUpQuestion(int position) {

//        question.setText(questionsList.get(position).getQuestion());
//        option1.setText(questionsList.get(position).getOption1());
//        option2.setText(questionsList.get(position).getOption2());
//        option3.setText(questionsList.get(position).getOption3());

    }


    public void nextQuestion(View view) {

        viewPager.setCurrentItem(viewPager.getCurrentItem() - 1, true);

    }


    public void previousQuestion(View view) {
        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
    }

    private void getQuestions(String play_List_Id) {
        //play_List_Id="PLq9TNzZPsJ4kDqDTu-KV8e85hi85YVprD";
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                .child(Constants.QUESTION_LIST).child(play_List_Id);
      /*  databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    QuestionModel questionModel = dataSnapshot.getValue(QuestionModel.class);
                    questionsList.add(questionModel);
                    size = questionsList.size();

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled( DatabaseError databaseError) {

            }
        });*/
     /*   databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (final DataSnapshot item : dataSnapshot.getChildren()) {

                    QuestionModel questionModel = new QuestionModel();

                    questionModel.setQuestionId(String.valueOf(item.child("sdfg").getValue()));
                    questionModel.setQuestion(String.valueOf(item.child("sdfg").getValue()));
                    questionModel.setCorrect(String.valueOf(item.child("sdfg").getValue()));
                    questionModel.setOption1(String.valueOf(item.child("sdfg").getValue()));
                    questionModel.setOption2(String.valueOf(item.child("sdfg").getValue()));
                    questionModel.setOption3(String.valueOf(item.child("sdfg").getValue()));

                    dataSnapshot.getValue(QuestionModel.class);

                    questionsList.add(questionModel);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
              // for (final DataSnapshot item : dataSnapshot.getChildren()) {
                if (dataSnapshot.exists()) {
                    try {
                        QuestionModel questionModel = new QuestionModel();

                        questionModel.setQuestionId(String.valueOf(dataSnapshot.child("questionId").getValue()));
                        questionModel.setQuestion(String.valueOf(dataSnapshot.child("question").getValue()));
                        questionModel.setCorrect(String.valueOf(dataSnapshot.child("correct").getValue()));
                        questionModel.setOption1(String.valueOf(dataSnapshot.child("option1").getValue()));
                        questionModel.setOption2(String.valueOf(dataSnapshot.child("option2").getValue()));
                        questionModel.setOption3(String.valueOf(dataSnapshot.child("option3").getValue()));
                        //dataSnapshot.getValue(QuestionModel.class);

                        questionsList.add(questionModel);

                        size = questionsList.size();
                    }

                    catch (Exception ex) {
                        int ab = 0;
                        int a = 5;
                        if (ab == a) {
                            a = 20;
                        }
                    }
                 //   }
                    //bhai jab firebase sa data ma integer ata ha tab yeh carsh ho jata ha , kis field ka issue he?
                    //koi be interger ho  ma na ise ko double be kiy ha oor long be lakin same error show hota ha

                    adapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void hideMethode() {
        btn_next.setVisibility(View.GONE);
        //btn_show_results.setVisibility(View.GONE);
        //Toast.makeText(getApplicationContext(),"Hide methode call",Toast.LENGTH_LONG).show();
    }
    public void ShowMethode() {
        btn_next.setVisibility(View.VISIBLE);
        ///
        // btn_show_results.setVisibility(View.VISIBLE);
        //Toast.makeText(getApplicationContext(),"Show methode call",Toast.LENGTH_LONG).show();
    }

}
