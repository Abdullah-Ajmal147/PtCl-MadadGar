package com.StepUP.madadgaar.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.StepUP.madadgaar.R;
import com.StepUP.madadgaar.utils.UserPreferences;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_DISPLAY_LENGTH = 5000;
    private ImageView logo;
    private ProgressBar progressBar;
    private UserPreferences cache;
    boolean showsplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        logo = findViewById(R.id.logo);
        cache = UserPreferences.getInstance(this);
        //progressBar = findViewById(R.id.progressBar);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        showsplash = cache.getBoolean(UserPreferences.PREF_IS_SPLASH, false);
        // progressBar();
        if (showsplash != true) {
            logoAnimation();
            splashScreenExit();

        } else {
            startActivity(new Intent(SplashActivity.this, After_login.class));
            finish();
        }

    }
   /* private void progressBar() {
        ValueAnimator animator = ValueAnimator.ofInt(0, progressBar.getMax());
        animator.setDuration(4000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation){
                progressBar.setProgress((Integer)animation.getAnimatedValue());
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                // start your activity here
            }
        });
        animator.start();
    }
*/
    private void splashScreenExit() {
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
//                if (isLogin()){
//                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
//                    finish();
//                }
//                else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
//                }

            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    private boolean isLogin() {
        boolean isLogin = false;



        return isLogin;
    }

    private void logoAnimation() {
        Animation animation = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.anim_logo);
        logo.startAnimation(animation);
    }
}
