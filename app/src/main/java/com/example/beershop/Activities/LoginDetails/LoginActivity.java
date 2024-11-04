package com.example.beershop.Activities.LoginDetails;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.beershop.Activities.CustomerLandingActivity;
import com.example.beershop.Activities.No_Activity;
import com.example.beershop.Activities.ScreeningActivity;
import com.example.beershop.Models.UserData;
import com.example.beershop.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private EditText user_email, user_pass;
    private MaterialButton btn_login;
    private TextView reset_pass, signup;
    private String email = "", password = "";
    private FirebaseAuth mAuth;
    private  AlertDialog.Builder builder;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        builder= new AlertDialog.Builder(this);
    }

    private void init() {
        user_email = findViewById(R.id.user_email);
        user_pass = findViewById(R.id.user_pass);
        btn_login = findViewById(R.id.btn_login);
        reset_pass = findViewById(R.id.reset_pass);
        signup = findViewById(R.id.signup);

        pd = new ProgressDialog(this);
        pd.setCancelable(false);
        pd.setMessage("Signing in...");
        mAuth=FirebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Login();
            }
        });

        reset_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    public void Login(){

        email = user_email.getText().toString().trim();
        password = user_pass.getText().toString().trim();

        if(email.isEmpty()){

            user_email.setError("Enter email");
        }else if(!email.matches(emailPattern)){

            user_email.setError("Invalid email");
        }else if(password.isEmpty()){

            user_pass.setError("Enter password");
        }else if(password.length() < 6){

            user_pass.setError("Password should be at least 6 characters long");
        }else {

            user_email.setError(null);
            user_pass.setError(null);

            pd.show();

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                if (user.isEmailVerified()) {
                                    Toast.makeText(getApplicationContext(), "Signed in successfully!", Toast.LENGTH_LONG).show();
                                    getUserData();
                                    pd.dismiss();

                                    SharedPreferences prefs = getSharedPreferences("Check Data", MODE_PRIVATE);
                                    String check = prefs.getString("check", "");
                                    String udid = prefs.getString("udid", "");

                                    if (check.matches("Customer") && udid.matches(mAuth.getCurrentUser().getUid())) {

                                        Intent intent = new Intent(LoginActivity.this, CustomerLandingActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();

                                    } else if (check.matches("No1") && udid.matches(mAuth.getCurrentUser().getUid())) {

                                        Intent intent = new Intent(LoginActivity.this, No_Activity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();

                                    } else {

                                        Intent intent = new Intent(LoginActivity.this, ScreeningActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }else {


                                    pd.dismiss();
                                    builder.setMessage("Please verify your E-mail")
                                            .setCancelable(false)
                                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    FirebaseAuth.getInstance().signOut();
                                                    dialogInterface.dismiss();
                                                }
                                            });
                                    AlertDialog alertDialog= builder.create();
                                    alertDialog.setTitle("Email Verification");
                                    alertDialog.show();
                                }
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    pd.dismiss();
                    Dialogue("Invalid credentials.");
                }
            });
        }
    }

    private void getUserData() {

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users");
        mDatabase.child(mAuth.getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        UserData user = dataSnapshot.getValue(UserData.class);

                        String name = user.getName();
                        String email =  user.getEmail();
                        String image = user.getImage();
                        String dob = user.getDob();
                        String udid = mAuth.getCurrentUser().getUid();

                        SharedPreferences sharedpreferences = getSharedPreferences("User Data", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("name", name);
                        editor.putString("email", email);
                        editor.putString("image", image);
                        editor.putString("udid", udid);
                        editor.putString("dob",dob);
                        editor.putString("password",password);
                        editor.apply();
                        editor.commit();

                        Log.i("DATA", ""+name+ "/" +email+ "/" +image+ "/" +udid);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                    }
                });
    }

    public void Dialogue(String message) {

        new AlertDialog.Builder(LoginActivity.this)
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
