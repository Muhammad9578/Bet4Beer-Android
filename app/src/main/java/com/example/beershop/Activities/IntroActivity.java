package com.example.beershop.Activities;

import androidx.appcompat.app.AppCompatActivity;

import me.biubiubiu.justifytext.library.JustifyTextView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.beershop.Activities.LoginDetails.LoginActivity;
import com.example.beershop.Activities.LoginDetails.SignUpActivity;
import com.example.beershop.R;
import com.google.android.material.button.MaterialButton;

public class IntroActivity extends AppCompatActivity {

    private TextView login;
    private Button register;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);


        register = findViewById(R.id.register);
        login = findViewById(R.id.loginn);
        img = findViewById(R.id.img);

        img.bringToFront();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(IntroActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(IntroActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }


}
