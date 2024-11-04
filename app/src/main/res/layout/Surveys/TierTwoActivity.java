package com.example.beerapp.Activities.Surveys;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.beerapp.R;
import com.google.android.material.button.MaterialButton;
import com.white.progressview.HorizontalProgressView;

public class TierTwoActivity extends AppCompatActivity {

    private ProgressBar pb;
    private HorizontalProgressView progress100;
    private MaterialButton btn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tier_two);

        init();
    }

    private void init() {

        pb = findViewById(R.id.pb);
        pb.setProgressTintList(ColorStateList.valueOf(Color.BLUE));
        progress100 = findViewById(R.id.progress100);
        btn_next = findViewById(R.id.btn_next);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TierTwoActivity.this,TierThreeActivity.class);
                startActivity(intent);
                Toast.makeText(TierTwoActivity.this, "Next", Toast.LENGTH_SHORT).show();
            }
        });

// for example:
        progress100.setTextVisible(false);
        progress100.setReachBarSize(4);
        progress100.setProgressPosition(HorizontalProgressView.TOP);



        ObjectAnimator animation = ObjectAnimator.ofInt(pb, "progress", 0, 100);
        animation.setDuration(5*1000);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) { }

            @Override
            public void onAnimationEnd(Animator animator) {
                //do something when the countdown is complete
                pb.setVisibility(View.GONE);
                Toast.makeText(TierTwoActivity.this, "Time Completed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationCancel(Animator animator) { }

            @Override
            public void onAnimationRepeat(Animator animator) { }
        });
        animation.start();

    }
}
