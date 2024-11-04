package com.example.beerapp.Activities.Surveys;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.beerapp.R;
import com.google.android.material.button.MaterialButton;

public class TokenActivity extends AppCompatActivity {
    private MaterialButton yes,no;
    private ImageView qr_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_token);

        init();
    }

    private void init() {
        yes = findViewById(R.id.yes);
        no = findViewById(R.id.no);
        qr_code = findViewById(R.id.qr_code);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TokenActivity.this, "Yes", Toast.LENGTH_SHORT).show();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TokenActivity.this, "No", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
