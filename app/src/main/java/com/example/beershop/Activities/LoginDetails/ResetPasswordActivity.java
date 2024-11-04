package com.example.beershop.Activities.LoginDetails;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.beershop.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {
    private EditText user_email;
    private MaterialButton btn_reset;
    private String email = "";
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private ProgressDialog pd;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        init();
    }

    private void init() {
        user_email = findViewById(R.id.user_email);
        btn_reset = findViewById(R.id.btn_reset);

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ResetPassword();
            }
        });

        pd = new ProgressDialog(this);
        pd.setCancelable(false);
        pd.setMessage("Please wait...");
        auth = FirebaseAuth.getInstance();
    }

    public void ResetPassword(){

        email = user_email.getText().toString().trim();

        if(email.isEmpty()){

            user_email.setError("Enter email address");
        }else if(!email.matches(emailPattern)){

            user_email.setError("Invalid email address");
        }else {

            user_email.setError(null);

            pd.show();

            auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                pd.dismiss();

                                Dialogue("Reset password link sent successfully.");

                            } else {
                                pd.dismiss();

                                Dialogue("Email address not found.");
                            }
                        }
                    });
        }
    }


    public void Dialogue(String message) {

        new AlertDialog.Builder(ResetPasswordActivity.this)
                .setTitle("Bet4Beers")
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
    }
}
