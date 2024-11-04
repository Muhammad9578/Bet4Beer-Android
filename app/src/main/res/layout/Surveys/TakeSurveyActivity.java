package com.example.beerapp.Activities.Surveys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.beerapp.R;
import com.google.android.material.button.MaterialButton;

public class TakeSurveyActivity extends AppCompatActivity implements View.OnClickListener {
    private MaterialButton btn_survey1, btn_survey2, btn_survey3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_survey);

        init();
    }

    private void init() {
        btn_survey1 = findViewById(R.id.btn_survey1);
        btn_survey2 = findViewById(R.id.btn_survey2);
        btn_survey3 = findViewById(R.id.btn_survey3);

        btn_survey1.setOnClickListener(this);
        btn_survey2.setOnClickListener(this);
        btn_survey3.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_survey1:
                Intent intent = new Intent(TakeSurveyActivity.this,TierOneActivity.class);
                startActivity(intent);
                Toast.makeText(TakeSurveyActivity.this, "Survey 1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_survey2:
                Toast.makeText(TakeSurveyActivity.this, "Survey 2", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_survey3:
                Toast.makeText(TakeSurveyActivity.this, "Survey 3", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
