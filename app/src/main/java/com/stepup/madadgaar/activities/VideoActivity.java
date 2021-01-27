package com.stepup.madadgaar.activities;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.stepup.madadgaar.R;
import com.stepup.madadgaar.adapters.YoutubeAdapter;
import com.stepup.madadgaar.models.YoutubeData;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.stepup.madadgaar.utils.Constants;
import com.stepup.madadgaar.utils.UserPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class VideoActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    //
//    private static final String API_KEY = "AIzaSyADgPQMkNnHoPy5nUatkeQrktVRkSLM-hk";"AIzaSyAEAPRvIi8TCLLtdhBOMfjlZa8MnuDj2kM"
    private static final String API_KEY = "AIzaSyAEAPRvIi8TCLLtdhBOMfjlZa8MnuDj2kM";
    private static final String API_KEY_OLD = "AIzaSyBtIxuLhpZJX_7sEk7GBBJcvjmofkaoLow";
    private String PLAY_LIST_URL;
    private String play_list_id;

    private String playListId;

    private String video_id;
    private YouTubePlayerView youTubePlayerView;
    private int selected_category, drawabel;
    private ImageView playThumbVideo;
//    private Button btnStartQuiz;
    private List<YoutubeData> playListItems = new ArrayList<>();
    private YoutubeAdapter adapter;
    private RecyclerView recyclerView;
    private String play_list_title;
    private TextView playingTitle;
    private String playing_video_id;
    private FrameLayout bottom;
    private int selected_category_color;
    private String list_video_id;
    private RelativeLayout start_btn_layout;
    private int videos_count;
    private boolean quizAble;
    private YouTubePlayer youTubePlayer ;
    private FrameLayout hide_player;
    ImageView Button_nav;
    DrawerLayout mdrawerlayout;
    TextView txthome,aboutUs,ContacUs,Logout,titleName;
    private DatabaseReference rootRef;
    private UserPreferences cache;
    SharedPreferences sharedPreferences;
    YouTubePlayer mYoutubePlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        //playThumbVideo = findViewById(R.id.playVideothumb);
        hide_player = findViewById(R.id.hide_player);
        recyclerView = findViewById(R.id.recentVideos);
        playingTitle = findViewById(R.id.playingTitle);
        bottom = findViewById(R.id.frameLayout);
        start_btn_layout = findViewById(R.id.start_btn_layout);
        cache = UserPreferences.getInstance(this);
        titleName=findViewById(R.id.titleName);
        start_btn_layout.setVisibility(View.GONE);
        Button_nav=findViewById(R.id.Button_nav);
        txthome=findViewById(R.id.home);
        aboutUs=findViewById(R.id.about);
        Logout=findViewById(R.id.logout);
        ContacUs=findViewById(R.id.Contact);
        mdrawerlayout=findViewById(R.id.Drawer);
        rootRef=FirebaseDatabase.getInstance().getReference();


        selected_category = getIntent().getIntExtra("selected_category", 0);
       drawabel = getIntent().getIntExtra("drawabel", drawabel);

       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        youTubePlayerView = findViewById(R.id.videoView);
        youTubePlayerView.initialize(API_KEY, this);

        play_list_title = getIntent().getStringExtra("title");
      //  setVideoTitle(play_list_title);

         sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String abc=sharedPreferences.getString("subCategoryName","null");
        playingTitle.setText(abc);

//        playingTitle.setText(play_list_title);
        // Get Selected category color and set them

        selected_category_color = getIntent().getIntExtra("selected_category", 0);
        //bottom.setBackgroundResource(selected_category_color);

        list_video_id = getIntent().getStringExtra("list_video_id");


            if (list_video_id.equals("false")) {
                playing_video_id = getIntent().getStringExtra("first_video_id");
            } else {
                playing_video_id = list_video_id;

            }


        txthome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ne =new Intent(getApplicationContext(),After_login.class);
                startActivity(ne);
            }
        });
        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ne =new Intent(getApplicationContext(),About_Us.class);
                startActivity(ne);
            }
        });
        ContacUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contact = "+923335311123";
                String message ="Dear \n" +
                        "Raheem Zeeshan\n" +
                        "Manager (Training & Development)";
                String url = null;
                try {
                    url = "https://api.whatsapp.com/send?phone="+contact+"&text="+ URLEncoder.encode(message, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Intent i = new Intent(Intent.ACTION_VIEW);
              /* i.setAction(Intent.ACTION_SEND);
                i.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                i.setType("text/plain");*/
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cache.saveBoolean(UserPreferences.PREF_IS_SPLASH,false);
                Intent ne =new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(ne);
                finish();
            }
        });

        Button_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mdrawerlayout.openDrawer(Gravity.LEFT);
            }
        });


//        playList = (List<YoutubeData>) getIntent().getSerializableExtra("playList");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        btnStartQuiz.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startQuiz();
//            }
//        });

        play_list_id = getIntent().getStringExtra("playListId");

        videos_count = getIntent().getIntExtra("videos_count",0);
        //getIntExtra
        PLAY_LIST_URL = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet,contentDetails&maxResults=25&" +
                 "playlistId=" + play_list_id + "&key=" + API_KEY + "&rel=0";



        quizAble(videos_count);
        showPlayList(PLAY_LIST_URL);


    }


    private void quizAble(final int videos_count){

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String uid = sharedPreferences.getString("uid","null");
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(Constants.WATCHED)
                .child(uid)
                .child(play_list_id);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    if (dataSnapshot.getChildrenCount() == videos_count){
                        quizAble = true;
                    }
                    else {
                        quizAble = false;
                        //that's points line
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    private void showPlayList(final String playListId) {

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                playListId,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("items");
                            boolean isSavedVideoFound=false;
                            String savedVideo=sharedPreferences.getString("pauseVideoId","");
                            for (int a = 0; a < jsonArray.length(); a++) {


                                JSONObject item = jsonArray.getJSONObject(a);

                                JSONObject snippet = item.getJSONObject("snippet");
                                String thumbnail = snippet.getJSONObject("thumbnails").getJSONObject("high").getString("url");

                                String video_id = item.getJSONObject("contentDetails").getString("videoId");
                                String title = snippet.getString("title");
                                String quiz = getIntent().getStringExtra("quiz");
                                String description = snippet.getString("description");
                                if(savedVideo!=null && !savedVideo.equals(""))
                                {
                                    if(savedVideo.equals(video_id))
                                        isSavedVideoFound=true;
                                }
                                YoutubeData youtubeData = new YoutubeData(title, thumbnail, video_id);
                                youtubeData.setQuiz(quiz);
                                playListItems.add(youtubeData);

                            }
                            if(!isSavedVideoFound)
                            {
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("pauseVideoId","");

                                editor.apply();
                                editor.commit();
                            }
                            String pauseVideoId=sharedPreferences.getString("pauseVideoId","");
                            String title=sharedPreferences.getString("re_title","");

                            if(pauseVideoId!=null && !pauseVideoId.equals(""))
                            {
                                playing_video_id=pauseVideoId;
                            }
                            String videoTitle="";
                            for(YoutubeData yd:playListItems)
                            {
                                if(yd.getVedio_id().equals(playing_video_id))
                                {
                                    title=yd.getTitle();
                                    break;
                                }

                            }
                            titleName.setText(title);
                            /*if(title!=null && title.equals("")){
                                play_list_title=title;
                                //playingTitle.setText(play_list_title);
                               // intent.putExtra("title", data);
                            }*/
                                  adapter = new YoutubeAdapter(VideoActivity.this, playListItems, play_list_id, play_list_title,
                                    playing_video_id, quizAble, videos_count,titleName,rootRef);
                               recyclerView.setAdapter(adapter);
                                if(mYoutubePlayer!=null)
                                    mYoutubePlayer.loadVideo(playing_video_id,0);



                        } catch (JSONException e) {
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(VideoActivity.this, "error"+error.networkResponse.statusCode, Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(stringRequest);
    }
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, boolean wasRestored) {
         mYoutubePlayer=youTubePlayer;
        youTubePlayer.setPlayerStateChangeListener(new YouTubePlayer.PlayerStateChangeListener() {
            @Override
            public void onLoading() {

            }

            @Override
            public void onLoaded(String s) {
            }

            @Override
            public void onAdStarted() {

            }

            @Override
            public void onVideoStarted() {
                String current= playing_video_id;

                int myIndex=1;
                //Toast.makeText(getApplicationContext(),"test mode"+current,Toast.LENGTH_LONG).show();
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("currentVideo",current);
                editor.putString("index", String.valueOf(index));
                editor.putInt("index",index);
                String currentVideoId=sharedPreferences.getString("currentVideo","");
                int currentIndex=0;
                for(YoutubeData yd: playListItems)
                {
                    if(yd.getVedio_id().equals(currentVideoId))
                    {
                        currentIndex=playListItems.indexOf(yd);
                        break;
                    }
                }

                //editor.putInt("pauseVideoTime",0);
                //editor.putString("pauseVideoId","");

                editor.apply();
                editor.commit();
                //Toast.makeText(getApplicationContext(),"test mode"+current,Toast.LENGTH_LONG).show();

                String pauseVideoId=sharedPreferences.getString("pauseVideoId","");
                int pauseVideoTime=sharedPreferences.getInt("pauseVideoTime",0);
                String title=sharedPreferences.getString("re_title","");
               // Toast.makeText(getApplicationContext(),title,Toast.LENGTH_LONG).show();
                if(!pauseVideoId.equals("") && pauseVideoId.equals(playing_video_id)) {
                    youTubePlayer.seekToMillis(pauseVideoTime);
                    editor.putInt("pauseVideoTime", 0);
                    editor.putString("pauseVideoId", "");
                    editor.putString("re_title","");
                    editor.apply();
                    editor.commit();

                }
            }
            int index = 0;
            @Override
            public void onVideoEnded() {
                //working now:

                //Toast.makeText(getApplicationContext(),"onVideoEnded",Toast.LENGTH_LONG).show();
                String play= String.valueOf(playListItems.size());

                Intent intent = new Intent(getApplicationContext(), VideoActivity.class);
                // current++;
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String currentVideoId=sharedPreferences.getString("currentVideo","");
                int currentIndex=0;
                for(YoutubeData yd: playListItems)
                {
                    if(yd.getVedio_id().equals(currentVideoId))
                    {
                        currentIndex=playListItems.indexOf(yd);
                        break;
                    }
                }

                if(currentIndex+1==playListItems.size())
                {
                    hide_player.setVisibility(View.VISIBLE);
                    String course="complete";
                    SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = sharedPreference.edit();
                    editor.putString("complete", course);
                    editor.commit();
                    editor.apply();
                    Toast.makeText(getApplicationContext(),"Course completed",Toast.LENGTH_LONG).show();
                }
                else {
                    playing_video_id=playListItems.get(currentIndex + 1).getVedio_id();

                    SharedPreferences sharedPreferences1 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = sharedPreferences1.edit();
                    editor.putString("currentVideo",playing_video_id);
                    editor.apply();
                    editor.commit();
                    intent.putExtra("list_video_id", playListItems.get(currentIndex + 1).getVedio_id());
                    intent.putExtra("playListId", play_list_id);
                    String end="videoEnd";

                    editor.putString("onVideoEnd", end);
                    editor.commit();
                    editor.apply();
                    String data= playListItems.get(currentIndex+1).getTitle();
                    editor.putString("currentVideo",playing_video_id);
                    intent.putExtra("title", data);
                   // Toast.makeText(getApplicationContext(),data,Toast.LENGTH_LONG).show();

                 //   intent.putExtra("title", play_list_title);
                    startActivity(intent);
                    hide_player.setVisibility(View.VISIBLE);
                 /* if(currentIndex==2){
                    }else {
                        finish();
                    }*/
                }

            }

            @Override
            public void onError(YouTubePlayer.ErrorReason errorReason) {

            }
        });
        youTubePlayer.setPlaybackEventListener(new YouTubePlayer.PlaybackEventListener() {
            @Override
            public void onPlaying() {

                a=a+1;
                Log.d("Playing: "," "+a);
            }

            @Override
            public void onPaused() {
                //working now:::
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String End=sharedPreferences.getString("onVideoEnd","");
                int currentIndex=0;
               // String end=getIntent().getStringExtra("onVideoEnd");
                if(End.equals("videoEnd"))
                {
                    //SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("pauseVideoTime", 0);
                    editor.putString("pauseVideoId", "");
                    editor.putString("re_title","");
                    editor.putString("onVideoEnd","");
                    editor.apply();
                    editor.commit();
                }else {
               int time=youTubePlayer.getCurrentTimeMillis();

                //SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("pauseVideoTime",time);
                editor.putString("pauseVideoId",playing_video_id);
                    String currentVideoId=sharedPreferences.getString("currentVideo","");
                    for(YoutubeData yd: playListItems)
                    {
                        if(yd.getVedio_id().equals(currentVideoId))
                        {
                            currentIndex=playListItems.indexOf(yd);
                            break;
                        }
                    }
                    String ab_title=playListItems.get(currentIndex).getTitle();
                    editor.putString("re_title",ab_title);
                    editor.apply();
                    editor.commit();

                  // Toast.makeText(getApplicationContext(), title, Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onStopped() {

            }

            @Override
            public void onBuffering(boolean b) {
                a=a+1;
                Log.d("Buffering: "," "+a);
            }

            @Override
            public void onSeekTo(int i) {
                a=a+1;
                Log.d("Steak: "," "+i);
            }
        });
       youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
        /*if (!wasRestored) {
//            youTubePlayer.cueVideo(playing_video_id);
            String PLAYLIST_COMMUNICATION = getIntent().getStringExtra("first_video_id");

            youTubePlayer.loadVideo(playing_video_id,0);

            //playing_video_id
        }*/
    }

    int a=0;
    @Override
    public void onPause() {
       /* if(youTubePlayer!=null) {
            int time=youTubePlayer.getCurrentTimeMillis();
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("pauseVideoTime", time);
            editor.putString("pauseVideoId", playing_video_id);

            editor.apply();
            editor.commit();
            super.onPause();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"equal function call ",Toast.LENGTH_LONG).show();
        }*/
        super.onPause();
    }

    @Override
    public void onStop() {
        /*int time=youTubePlayer.getCurrentTimeMillis();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("pauseVideoTime",time);
        editor.putString("pauseVideoId",playing_video_id);

        editor.apply();
        editor.commit();
        super.onStop();*/

        super.onStop();
    }
    private YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener() {
        @Override
        public void onPlaying() {

          a=a+1;
            Log.d("Playing: "," "+a);
        }

        @Override
        public void onPaused() {
            //not working...
           // Toast.makeText(getApplicationContext(),"onPaused methode",Toast.LENGTH_LONG).show();
        }


        @Override
        public void onStopped() {

        }

        @Override
        public void onBuffering(boolean b) {
            a=a+1;
            Log.d("Buffering: "," "+a);
        }

        @Override
        public void onSeekTo(int i) {
            a=a+1;
            Log.d("Steak: "," "+i);
        }
    };

    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onLoading() {

        }

        @Override
        public void onLoaded(String s) {
        }

        @Override
        public void onAdStarted() {

        }

        @Override
        public void onVideoStarted() {
            String current= playing_video_id;
            //not working......
            int myIndex=1;
            //Toast.makeText(getApplicationContext(),"test mode"+current,Toast.LENGTH_LONG).show();
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("currentVideo",current);
            editor.putString("index", String.valueOf(index));
            editor.putInt("index",index);
            editor.apply();
            editor.commit();
            //Toast.makeText(getApplicationContext(),"test mode"+current,Toast.LENGTH_LONG).show();

           /* if(abc=="") {
                int value = Integer.parseInt(abc);
                for (int i = 0; i == value; i++) {
                    int next= Integer.parseInt(abc);
                    next++;
                    Toast.makeText(getApplicationContext(),next,Toast.LENGTH_LONG).show();
                }
            }
            else{

            }*/
        }
        int index = 0;
        @Override
        public void onVideoEnded() {

            //not working......
            //Toast.makeText(getApplicationContext(),"onVideoEnded",Toast.LENGTH_LONG).show();

            String play= String.valueOf(playListItems.size());

            Intent intent = new Intent(getApplicationContext(), VideoActivity.class);
               // current++;
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String currentVideoId=sharedPreferences.getString("currentVideo","");
         int currentIndex=0;
            for(YoutubeData yd: playListItems)
            {
                if(yd.getVedio_id().equals(currentVideoId))
                {
                    currentIndex=playListItems.indexOf(yd);
                    break;
                }
            }

           if(currentIndex+1==playListItems.size())
           {
               hide_player.setVisibility(View.VISIBLE);
               String course="complete";
               SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
               SharedPreferences.Editor editor = sharedPreference.edit();
               editor.putString("complete", course);
               editor.commit();
               editor.apply();
             //  Toast.makeText(getApplicationContext(),"completed",Toast.LENGTH_LONG).show();
           }
           else {
               playing_video_id=playListItems.get(currentIndex + 1).getVedio_id();

               SharedPreferences sharedPreferences1 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
               SharedPreferences.Editor editor = sharedPreferences1.edit();
               editor.putString("currentVideo",playing_video_id);
               editor.apply();
               editor.commit();
               intent.putExtra("list_video_id", playListItems.get(currentIndex + 1).getVedio_id());
               intent.putExtra("playListId", play_list_id);
               String data=playListItems.get(currentIndex+1).getTitle();
               intent.putExtra("title", data);
              // Toast.makeText(getApplicationContext(),data,Toast.LENGTH_LONG).show();
               startActivity(intent);
               finish();

           }



        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {

        }
    };

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }
    @Override
    public void onBackPressed() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        final String CateGory=sharedPreferences.getString("Title_Name","as");
        final String fb_CateGoryName=sharedPreferences.getString("fb_title_Name","as");

       // Toast.makeText(getApplicationContext(),"testing msag",Toast.LENGTH_LONG).show();

        Intent ne = new Intent(getApplicationContext(),Sub_Mark.class);
        Bundle bundle=new Bundle();
        String Radio=fb_CateGoryName;
        String txtRadio=CateGory;
        bundle.putString("title",txtRadio);
        bundle.putString("Radeio",Radio);
        ne.putExtras(bundle);
        startActivity(ne);
        finish();
    }
   /* @Override
    public void onBackPressed() {
        super.onBackPressed();

        SharedPreferences sharedPreferences1 = PreferenceManager.getDefaultSharedPreferences(this);
        String category = sharedPreferences1.getString("category","");
        if (category.equals("technical")) {
            Intent intent = new Intent(VideoActivity.this,After_login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(VideoActivity.this, After_login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }*/
}
