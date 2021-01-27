package com.stepup.madadgaar.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.stepup.madadgaar.R;

import java.util.HashMap;
import java.util.Map;

public class Admin extends AppCompatActivity {

    private EditText questionId,question,videoId,one,two,three,correct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        questionId = findViewById(R.id.qid);
        videoId = findViewById(R.id.id);
        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        three = findViewById(R.id.three);
        correct = findViewById(R.id.three);
        question = findViewById(R.id.question);

    }

    public void save(View view) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Questions")
                .child(videoId.getText().toString()).child(questionId.getText().toString());

        Map<String, Object> newQ = new HashMap<>();
        newQ.put("option1",one.getText().toString());
        newQ.put("option2",two.getText().toString());
        newQ.put("option3",three.getText().toString());
        newQ.put("correct",correct.getText().toString());
        newQ.put("questionId",questionId.getText().toString());
        newQ.put("question",question.getText().toString());

        databaseReference.setValue(newQ).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(Admin.this,"SUCCESS",Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Admin.this,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }
}
