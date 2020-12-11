package com.StepUP.madadgaar.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.StepUP.madadgaar.R;
import com.StepUP.madadgaar.models.Video_id_Model;

import java.util.HashMap;
import java.util.Map;

public class Add_Video_Id extends AppCompatActivity {

    EditText title1,sub_title1,sub_playlist1,videoId,VideoTitle
            ,Video_Counter;

    String overview,ODI,ODU,Anteun;
    Button btnAdd;
    private DatabaseReference rootRef,topicsRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_video_id);

        rootRef = FirebaseDatabase.getInstance().getReference();

        title1=findViewById(R.id.ed_title1);

        sub_title1=findViewById(R.id.title1);
        sub_playlist1=findViewById(R.id.playlistId1);
        videoId=findViewById(R.id.videoId);
        VideoTitle=findViewById(R.id.VideoTitle);
        Video_Counter=findViewById(R.id.Video_Counter);


        btnAdd=findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Map<String, Object> newUser = new HashMap<>();
               /* newUser.put("overview", overview);
                newUser.put("ODI", ODI);
                newUser.put("ODU", ODU);
                newUser.put("Anteun", Anteun);*/
                Video_id_Model video_id_model=new Video_id_Model();
                video_id_model.setSub_title1(sub_title1.getText().toString());
                video_id_model.setSub_playlist1(sub_playlist1.getText().toString());
                video_id_model.setVideo_id(videoId.getText().toString());
                video_id_model.setVideoTitle(VideoTitle.getText().toString());
                video_id_model.setVideo_Counter(Video_Counter.getText().toString());

               /* newUser.put("mobile", empMobile);
                newUser.put("region", empRegion);
                newUser.put("uid", uid);*/

                rootRef.child("Transport").push().setValue(video_id_model).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                       // progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
            }
        });



    }
}