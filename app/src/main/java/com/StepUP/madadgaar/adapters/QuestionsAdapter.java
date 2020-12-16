package com.StepUP.madadgaar.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.StepUP.madadgaar.R;
import com.StepUP.madadgaar.activities.About_Us;
import com.StepUP.madadgaar.activities.After_login;
import com.StepUP.madadgaar.activities.QuizActivity;
import com.StepUP.madadgaar.models.QuestionModel;
import com.StepUP.madadgaar.utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestionsAdapter extends PagerAdapter {

    private Context context;
    private List<QuestionModel> questionList;
    private String video_id;
    private ProgressBar progressBar;
    private String playListId;
    public QuestionsAdapter(Context context, List<QuestionModel> questionList, String video_id, String playListId) {
        this.context = context;
        this.questionList = questionList;
        this.video_id = video_id;
        this.playListId = playListId;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, final int position) {

        final ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.question_layout, container, false);
        final TextView question, option1, option2, option3,option4;
        final ImageView Button_nav;
        final DrawerLayout mdrawerlayout;
        final TextView txthome,aboutUs,ContacUs,Logout;

        Button_nav=(ImageView)viewGroup.findViewById(R.id.Button_nav);
        txthome=(TextView)viewGroup.findViewById(R.id.home);
        aboutUs=(TextView)viewGroup.findViewById(R.id.about);
        Logout=(TextView)viewGroup.findViewById(R.id.logout);
        ContacUs=(TextView)viewGroup.findViewById(R.id.Contact);
        mdrawerlayout=(DrawerLayout)viewGroup.findViewById(R.id.Drawer);
        question = viewGroup.findViewById(R.id.question);
        option1 = viewGroup.findViewById(R.id.optOne);
        option2 = viewGroup.findViewById(R.id.optTwo);
        option3 = viewGroup.findViewById(R.id.optThree);
       // option4 = viewGroup.findViewById(R.id.optFour);

        progressBar = viewGroup.findViewById(R.id.progressBar2);

        question.setText(questionList.get(position).getQuestion());


        if (question.getText().toString() != null){
            progressBar.setVisibility(View.GONE);
        }
        option1.setText("1.    "+questionList.get(position).getOption1());
        option2.setText("2.    "+questionList.get(position).getOption2());
        option3.setText("3.    "+questionList.get(position).getOption3());
        //option4.setText("4. "+questionList.get(position).getOption4());
        txthome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ne =new Intent(context, After_login.class);
                context.startActivity(ne);
            }
        });
        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ne =new Intent(context, About_Us.class);
                //c.startActivity(ne);
            }
        });
        ContacUs.setOnClickListener(new View.OnClickListener() {
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
              /*  i.setAction(Intent.ACTION_SEND);
                i.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                i.setType("text/plain");*/
                i.setData(Uri.parse(url));
                //contact.startActivity(i);
            }
        });
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        Button_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public
            void onClick(View view) {
               mdrawerlayout.openDrawer(Gravity.LEFT);
            }
        });

        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option1.setEnabled(false);
                option2.setEnabled(false);
                option3.setEnabled(false);
                ((QuizActivity)context).ShowMethode();
                if ("a".equals(questionList.get(position).getCorrect())) {
                    addToCorrectAnswer(position);
                    //option1.setTextColor(R.drawable.ques_color_true);
                    option1.setTextColor(Color.parseColor("#0289fc"));
                   // ((QuizActivity)context).hideMethode();
                } /*else {
                    option1.setTextColor(R.drawable.ques_color_true);
                }*/
                if ("b".equals(questionList.get(position).getCorrect())) {
                    //option2.setTextColor(R.drawable.ques_color_true);
                    option1.setTextColor(Color.parseColor("#0289fc"));
                    //((QuizActivity)context).hideMethode();

                }
                if ("c".equals(questionList.get(position).getCorrect())) {
                    //option3.setTextColor(R.drawable.ques_color_true);
                    option1.setTextColor(Color.parseColor("#0289fc"));
                   // ((QuizActivity)context).hideMethode();

                }
            }
        });
        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option1.setEnabled(false);
                option2.setEnabled(false);
                option3.setEnabled(false);
                ((QuizActivity)context).ShowMethode();
                if ("b".equals(questionList.get(position).getCorrect())) {
                    addToCorrectAnswer(position);
                   // option2.setTextColor(R.drawable.ques_color_true);
                   // option2.setTextColor(Integer.parseInt("#F60B0B"));
                    option2.setTextColor(Color.parseColor("#0289fc"));

                }/* else {
                    option2.setTextColor(R.drawable.ques_color_true);
                }*/
                if ("a".equals(questionList.get(position).getCorrect())) {
                    //option1.setTextColor(R.drawable.ques_color_true);
                    option2.setTextColor(Color.parseColor("#0289fc"));

                }
                if ("c".equals(questionList.get(position).getCorrect())) {
                    //option3.setTextColor(R.drawable.ques_color_true);
                    option2.setTextColor(Color.parseColor("#0289fc"));

                }
            }
        });
        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option1.setEnabled(false);
                option2.setEnabled(false);
                option3.setEnabled(false);
                ((QuizActivity)context).ShowMethode();
                if ("c".equals(questionList.get(position).getCorrect())) {
                    addToCorrectAnswer(position);
                   // option3.setTextColor(R.drawable.ques_color_true);
                   // option3.setTextColor(Integer.parseInt("#F60B0B"));
                    option3.setTextColor(Color.parseColor("#0289fc"));
                    //option3.setTextColor(Color.RED);


                }/* else {
                    option3.setTextColor(R.drawable.ques_color_true);
                }*/
                if ("b".equals(questionList.get(position).getCorrect())) {
                    //option2.setTextColor(R.drawable.ques_color_true);
                    option3.setTextColor(Color.parseColor("#0289fc"));

                }
                if ("a".equals(questionList.get(position).getCorrect())) {
                  //  option1.setTextColor(R.drawable.ques_color_true);
                    option3.setTextColor(Color.parseColor("#0289fc"));

                }
            }
        });
       /* option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option1.setEnabled(false);
                option2.setEnabled(false);
                option3.setEnabled(false);
                if ("c".equals(questionList.get(position).getCorrect())) {
                    addToCorrectAnswer(position);
                    option3.setBackgroundResource(R.drawable.true_question_layout);
                } else {
                    option3.setBackgroundResource(R.drawable.false_question_layout);
                }
                if ("b".equals(questionList.get(position).getCorrect())) {
                    option2.setBackgroundResource(R.drawable.true_question_layout);
                }
                if ("a".equals(questionList.get(position).getCorrect())) {
                    option1.setBackgroundResource(R.drawable.true_question_layout);
                }
            }
        });*/

        container.addView(viewGroup, 0);
        return viewGroup;

    }


    private void addToCorrectAnswer(int position) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String uid = sharedPreferences.getString("uid","Please Login");

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(Constants.SCORE).
                child(uid).child(playListId);

        Map<String,Object> score = new HashMap<>();
        score.put(questionList.get(position).getQuestionId(),"true");

        databaseReference.updateChildren(score)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getCount() {
        return questionList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {

        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        //container.removeView((ConstraintLayout) object);
    }

}
