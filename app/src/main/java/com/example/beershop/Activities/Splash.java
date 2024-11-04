package com.example.beershop.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.beershop.Activities.LoginDetails.LoginActivity;
import com.example.beershop.R;

public class Splash extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(Splash.this, IntroActivity.class);
                Splash.this.startActivity(mainIntent);
                Splash.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences prefs = getSharedPreferences("User Data", MODE_PRIVATE);
        String email = prefs.getString("email","");
        String name = prefs.getString("name","");
        final String ud = prefs.getString("udid","");
        Log.i("userData", "name / email  =  "+name+" / "+email);


        if(!email.isEmpty() && !name.isEmpty()){

            SharedPreferences prefs1 = getSharedPreferences("Check Data", MODE_PRIVATE);
            final String check = prefs1.getString("check", "");
            final String udid = prefs1.getString("udid","");


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (check.matches("Customer")&& udid.matches(ud)) {

                        Intent intent = new Intent(Splash.this, CustomerLandingActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();

                    } else if (check.matches("No1") && udid.matches(ud)) {

                        Intent intent = new Intent(Splash.this, No_Activity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();

                    } else{

                        Intent intent = new Intent(Splash.this, ScreeningActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                }
            }, SPLASH_DISPLAY_LENGTH);
        }
    }
}
