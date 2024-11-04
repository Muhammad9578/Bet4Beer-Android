package com.example.beershop.Activities.LoginDetails;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.beershop.Activities.CustomerLandingActivity;
import com.example.beershop.Models.Compaign.C_Discount;
import com.example.beershop.Models.Compaign.C_Redemption;
import com.example.beershop.Models.Compaign.Com_metadata;
import com.example.beershop.Models.Compaign.CompaignData;
import com.example.beershop.Models.Compaign.Voucher;
import com.example.beershop.Models.Customer.Address;
import com.example.beershop.Models.Customer.C_Metadata;
import com.example.beershop.Models.Customer.CustomerData;
import com.example.beershop.Models.Publication.Campaign;
import com.example.beershop.Models.Publication.Customer;
import com.example.beershop.Models.Publication.P_Metadata;
import com.example.beershop.Models.Publication.PublicationData;
import com.example.beershop.Models.SurveyData;
import com.example.beershop.Models.UserData;
import com.example.beershop.Models.Voucher.Code_Config;
import com.example.beershop.Models.Voucher.Discount;
import com.example.beershop.Models.Voucher.Metadata;
import com.example.beershop.Models.Voucher.Redemption;
import com.example.beershop.Models.Voucher.VoucherData;
import com.example.beershop.R;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static java.security.AccessController.getContext;

public class SignUpActivity extends AppCompatActivity {
    private EditText user_email, user_pass, user_c_pass, user_name,ref_code;
    private MaterialButton btn_signup;
    private CircleImageView profile_img;
    private TextView login,upload;
    private CheckBox cbRememberme,c_emails;
    private String start_date = null, end_date = null;
    private StorageReference storageReference;
    private FirebaseAuth mAuth;
    private String downloadURL = "";
    private ProgressDialog pd;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Uri imageUri;
    private DatePicker picker;
    private String d,m,y;
    private int years;
    private String Voucher_Code = "";
    private String url = "", url2 = "";
    private String id = "";
    private int key = 0;
    private String Customer_id = "";
    private String full_name = "", email = "", password = "", c_password = "",referral="";
    private String dob = "";
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private TextView tvDOB;
    private Date date1,date2 ;
    private String android_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();
    }

    private void init() {
        user_email = findViewById(R.id.user_email);
        user_pass = findViewById(R.id.user_pass);
        user_c_pass = findViewById(R.id.user_c_pass);
        user_name = findViewById(R.id.user_name);
        picker=(DatePicker)findViewById(R.id.datePicker1);
        btn_signup = findViewById(R.id.btn_signup);
        profile_img = findViewById(R.id.profile_img);
        login = findViewById(R.id.login);
        upload = findViewById(R.id.upload);
        tvDOB = findViewById(R.id.tvDOB);
        ref_code = findViewById(R.id.ref_code);
        c_emails = findViewById(R.id.c_emails);
        cbRememberme = findViewById(R.id.cbRememberme);

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        android_id = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        Log.i("Android ID","android_id= "+android_id);

        pd = new ProgressDialog(this);
        pd.setCancelable(false);
        pd.setMessage("Signing up...");

        String text = "I would like to sign up to receive email updates from Bet4Beers and selected partners";
        SpannableString ss = new SpannableString(text);

        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(SignUpActivity.this, "Privacy Policy", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void updateDrawState(TextPaint textPaint) {
                super.updateDrawState(textPaint);
                textPaint.setColor(Color.BLUE);
                textPaint.setUnderlineText(true);
            }
        };

        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(SignUpActivity.this, "T&Cs", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void updateDrawState(TextPaint textPaint) {
                super.updateDrawState(textPaint);
                textPaint.setColor(Color.BLUE);
                textPaint.setUnderlineText(true);
            }
        };

//        ss.setSpan(clickableSpan1, 69, 83, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        ss.setSpan(clickableSpan2, 88, 92, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        cbRememberme.setText(ss);
        cbRememberme.setMovementMethod(LinkMovementMethod.getInstance());


        String text1 = "I can confirm i have read and accepted the Bet4Beers terms & conditions";
        SpannableString ss1 = new SpannableString(text1);

        ClickableSpan clickableSpan11 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(SignUpActivity.this, "Privacy Policy", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void updateDrawState(TextPaint textPaint) {
                super.updateDrawState(textPaint);
                textPaint.setColor(Color.BLUE);
                textPaint.setUnderlineText(true);
            }
        };

        ClickableSpan clickableSpan12 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(SignUpActivity.this, "T&Cs", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void updateDrawState(TextPaint textPaint) {
                super.updateDrawState(textPaint);
                textPaint.setColor(Color.BLUE);
                textPaint.setUnderlineText(true);
            }
        };

//        ss1.setSpan(clickableSpan11, 69, 83, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        ss1.setSpan(clickableSpan12, 88, 92, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        cbRememberme.setText(ss1);
        cbRememberme.setMovementMethod(LinkMovementMethod.getInstance());





        tvDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        SignUpActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
//                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                dob = month + "-" + day + "-" + year;
                tvDOB.setText(dob);
            }
        };
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(SignUpActivity.this)
                        .crop()
                        .compress(1024)
                        .maxResultSize(1080, 1080)
                        .start();
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Registration();

            }
        });

        profile_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(SignUpActivity.this)
                        .crop()                    //Crop image(Optional), Check Customization for more option
                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            imageUri = data.getData();
            profile_img.setImageURI(imageUri);
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.RESULT_ERROR, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
        }
    }

    public void Registration(){

        full_name = user_name.getText().toString().trim();
        email = user_email.getText().toString().trim();
        password = user_pass.getText().toString().trim();
        c_password = user_c_pass.getText().toString().trim();

        if(full_name.isEmpty()){

            user_name.setError("Enter name");
        }else if(email.isEmpty()){

            user_email.setError("Enter email");
        }else if(!email.matches(emailPattern)){

            user_email.setError("Invalid email");
        }else if(password.isEmpty()){

            user_pass.setError("Enter password");
        }else if(c_password.isEmpty()){

            user_c_pass.setError("Enter confirm password");
        }else if(!password.equalsIgnoreCase(c_password)){

            user_c_pass.setError("Passwords doesn`t match");
        } else if(imageUri == null){

          //  imageUri = Uri.parse("android.resource://com.example.beershop/drawable/i_profile");
            imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                    "://" + getResources().getResourcePackageName(R.drawable.i_profile)
                    + '/' + getResources().getResourceTypeName(R.drawable.i_profile) + '/' +
                    getResources().getResourceEntryName(R.drawable.i_profile) );
          //  Toast.makeText(getApplicationContext(),"Upload profile image",Toast.LENGTH_LONG).show();
        } else if(dob.isEmpty()){

            Toast.makeText(getApplicationContext(),"Select date of birth",Toast.LENGTH_LONG).show();
        }else if(!cbRememberme.isChecked()){

            Toast.makeText(getApplicationContext(),"Please agree to our T&Cs",Toast.LENGTH_LONG).show();
        }else if(password.length() < 6){

            user_pass.setError("Password should be at least 6 characters");
        }
        else {

            CalculateYears();
            user_name.setError(null);
            user_email.setError(null);
            user_pass.setError(null);
            user_c_pass.setError(null);
            user_pass.setError(null);

            if (years < 21) {

                Dialogue("Bet4Beers","We`re sorry, you need to be 21 years old to use this app.\n\nGoodbye.");

            } else {
                pd.show();

                referral = ref_code.getText().toString().trim();

                final DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference("Device IDs");
                mDatabase1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (dataSnapshot.hasChild(android_id)) {

                            Dialogue("Bet4Beers","You already used this app.\n Login to your account.");
                            pd.dismiss();
                        } else {

                            if (!referral.isEmpty()) {

                                final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Referrals");
                                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                        if (dataSnapshot.hasChild(referral)) {


                                                            storageReference = FirebaseStorage.getInstance().getReference().child("Profile Images").child(imageUri.getLastPathSegment());
                storageReference.putFile(imageUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @SuppressWarnings("VisibleForTests")
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Log.i("Success", "Uploaded successfully!");
                                downloadURL = taskSnapshot.getDownloadUrl().toString();

                                mAuth.createUserWithEmailAndPassword(email, password)
                                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {

                                                if (task.isSuccessful()) {
                                                    UserData user = new UserData(full_name, email, downloadURL, dob);
                                                    final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users");
                                                    mDatabase.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {

                                                            final DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference("Device IDs");
                                                            mDatabase1.child(android_id).setValue(android_id).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {

                                                                    Log.i("ID", "Added");
                                                                }
                                                            }).addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    Log.i("ID", "Failed");
                                                                }
                                                            });

                                                            Voucher();
                                                            Voucher1(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                                            mDatabase.push().getKey();
                                                            sendVerificationEmail();

                                                            pd.dismiss();
                                                            Toast.makeText(getApplicationContext(), "Sign up successfully!", Toast.LENGTH_SHORT).show();

                                                            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                                            startActivity(intent);
                                                            finish();
                                                        }
                                                    });
                                                } else {
                                                    pd.dismiss();
                                                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {

                                                        Dialogue("Bet4Beers","Email already registered.");

                                                    } else {
                                                        Toast.makeText(getApplicationContext(), "Registration failed", Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            }
                                        });

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                Toast.makeText(getApplicationContext(), "Registration failed", Toast.LENGTH_LONG).show();
                            }
                        });
                                        } else {

                                            Dialogue("Bet4Beers","Invalid referral code.");
                                            pd.dismiss();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                        Toast.makeText(getApplicationContext(), "Please try again", Toast.LENGTH_LONG).show();
                                    }
                                });

                            } else {
                                                storageReference = FirebaseStorage.getInstance().getReference().child("Profile Images").child(imageUri.getLastPathSegment());
                storageReference.putFile(imageUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @SuppressWarnings("VisibleForTests")
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Log.i("Success", "Uploaded successfully!");
                                downloadURL = taskSnapshot.getDownloadUrl().toString();

                                mAuth.createUserWithEmailAndPassword(email, password)
                                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {

                                                if (task.isSuccessful()) {

                                                    UserData user = new UserData(full_name, email, downloadURL, dob);
                                                    final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users");
                                                    mDatabase.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {

                                                            final DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference("Device IDs");
                                                            mDatabase1.child(android_id).setValue(android_id).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {

                                                                    Log.i("ID", "Added");
                                                                }
                                                            }).addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    Log.i("ID", "Failed");
                                                                }
                                                            });

                                                                mDatabase.push().getKey();
                                                                sendVerificationEmail();

                                                            pd.dismiss();
                                                           // Toast.makeText(getApplicationContext(), "Sign up successfully!", Toast.LENGTH_SHORT).show();

                                                            Dialogue("Verify Your Email Address.","We now need to verify your email address. Please click the link in the email we have sent you.");
//                                                            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
//                                                            startActivity(intent);
//                                                            finish();
                                                        }
                                                    });
                                                } else {
                                                    pd.dismiss();
                                                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {

                                                        Dialogue("Bet4Beers","Email already registered.");

                                                    } else {
                                                        Toast.makeText(getApplicationContext(), "Registration failed", Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            }
                                        });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                Toast.makeText(getApplicationContext(), "Registration failed", Toast.LENGTH_LONG).show();
                            }
                        });
                             }

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        }

    }

    private void sendVerificationEmail()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            FirebaseAuth.getInstance().signOut();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Failed to send Verification email",Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }
    public void Dialogue(String title,String message) {

        new AlertDialog.Builder(SignUpActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
    }

    public void CalculateYears(){


        d = String.valueOf(picker.getDayOfMonth());
        m = String.valueOf(picker.getMonth()+1);
        y = String.valueOf(picker.getYear());

        end_date = ""+m+"-"+d+"-"+y;

        String format = "MM-dd-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format);

//        dob = "7/2/2019";
//        end_date = "7/2/2020";
        try {
            date1 = sdf.parse(dob);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            date2 = sdf.parse(end_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long diff = date2.getTime() - date1.getTime();

        int diffDays = (int) (diff / (24 * 60 * 60 * 1000));

        years = diffDays/365;

        Log.i("YYY",""+years);
    }

    public void Voucher(){

        Gson gson = new Gson();

        Code_Config code_config = new Code_Config("PROMO-#####");

        Discount discount = new Discount(100, "PERCENT");

        Metadata metadata = new Metadata(true, "de-en");

        final Redemption redemption = new Redemption(1);

        VoucherData voucherData = new VoucherData("Customers Vouchers","DISCOUNT_VOUCHER","2019-01-01T00:00:00Z","2020-12-31T23:59:59Z",code_config,discount, redemption,metadata);

        final String json = gson.toJson(voucherData);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String URL = "https://us1.api.voucherify.io/v1/vouchers";

        final String requestBody = json.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("VOLLEY", response);

                try {

                    JSONObject jsonObject = new JSONObject(response);

                    Object ob = jsonObject.getJSONObject("assets");
                    Object os = ((JSONObject) ob).getJSONObject("qr");
                    url = ((JSONObject) os).getString("url");
                    Voucher_Code = jsonObject.getString("code");

                    id = jsonObject.getString("id");

                    Long tsLong = System.currentTimeMillis()/1000;
                    long ts = tsLong;

                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                    SurveyData surveyData = new SurveyData(Voucher_Code, url, "Referral Token", "1");
                    mDatabase.child("Surveys").child(referral).child(tsLong.toString()).setValue(surveyData);
                    mDatabase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            Log.i("Ref_token", "Successful");
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                            Log.i("Ref_token", "Failed");

                        }
                    });

                    Log.i("iii",""+Voucher_Code);
                    Log.i("uuu",""+url);

                    Customer(Voucher_Code);
                    Compaing(full_name);


                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),""+e,Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY", error.toString());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                final Map<String, String> params = new HashMap<>();
                params.put("X-App-Id" , "2986fb82-6dae-4e5b-a070-ec57e8849b84");
                params.put("X-App-Token" , "90bf01af-4ac2-4d0e-970d-04ff3a639bfb");
                params.put("Content-Type" , "application/json");
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
    public void Voucher1(final String uid){

        Gson gson = new Gson();

        Code_Config code_config = new Code_Config("PROMO-#####");

        Discount discount = new Discount(100, "PERCENT");

        Metadata metadata = new Metadata(true, "de-en");

        final Redemption redemption = new Redemption(1);

        VoucherData voucherData = new VoucherData("Customers Vouchers","DISCOUNT_VOUCHER","2019-01-01T00:00:00Z","2020-12-31T23:59:59Z",code_config,discount, redemption,metadata);

        final String json = gson.toJson(voucherData);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String URL = "https://us1.api.voucherify.io/v1/vouchers";

        final String requestBody = json.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("VOLLEY", response);

                try {

                    JSONObject jsonObject = new JSONObject(response);

                    Object ob = jsonObject.getJSONObject("assets");
                    Object os = ((JSONObject) ob).getJSONObject("qr");
                    url2 = ((JSONObject) os).getString("url");
                    Voucher_Code = jsonObject.getString("code");

                    id = jsonObject.getString("id");

                    Long tsLong = System.currentTimeMillis()/1000;
                    long ts = tsLong;
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                    SurveyData surveyData = new SurveyData(Voucher_Code, url2, "Referral Token", "1");
                    mDatabase.child("Surveys").child(uid).child(tsLong.toString()).setValue(surveyData);
                    mDatabase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            Log.i("Ref_token", "Successful");
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                            Log.i("Ref_token", "Failed");

                        }
                    });
//
                    Log.i("iii",""+Voucher_Code);
                    Log.i("uuu",""+url2);

                    Customer(Voucher_Code);

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),""+e,Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY", error.toString());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                final Map<String, String> params = new HashMap<>();
                params.put("X-App-Id" , "2986fb82-6dae-4e5b-a070-ec57e8849b84");
                params.put("X-App-Token" , "90bf01af-4ac2-4d0e-970d-04ff3a639bfb");
                params.put("Content-Type" , "application/json");
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
    public void Customer(final String v_id){

        Gson gson = new Gson();

        Address address = new Address("Melbourne", "FL", "226 E Fee Ave", "null", "Australia", "32901");

        C_Metadata c_metadata =  new C_Metadata("en");

        CustomerData customerData =  new CustomerData(id, full_name, email, "Premium user, ACME Inc.", address, c_metadata);

        final String json1 = gson.toJson(customerData);

        Log.i("ccc",""+json1);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "https://us1.api.voucherify.io/v1/customers";

        final String requestBody = json1.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("Customer", response);
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    Customer_id = jsonObject.getString("id");
                    Log.i("cic",""+Customer_id);

                    Publication(v_id,Customer_id);

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),""+e,Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Customer", error.toString());

            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                final Map<String, String> params = new HashMap<>();
                params.put("X-App-Id" , "2986fb82-6dae-4e5b-a070-ec57e8849b84");
                params.put("X-App-Token" , "90bf01af-4ac2-4d0e-970d-04ff3a639bfb");
                params.put("Content-Type" , "application/json");
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
    public void Publication(String v_code, String c_id){

        Gson gson = new Gson();

        Campaign campaign = new Campaign(full_name, 1);

        Customer customer = new Customer(full_name,email,"Test User");

        P_Metadata p_metadata = new P_Metadata(true, "Shop Admin");

        PublicationData publicationData = new PublicationData(campaign, customer, p_metadata, v_code);

        final String json1 = gson.toJson(publicationData);

        Log.i("ppp",""+json1);

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String url = "https://us1.api.voucherify.io/v1/publications";

        final String requestBody = json1.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("publication", response);
                try {

                    JSONObject jsonObject = new JSONObject(response);

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),""+e,Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("publication", error.toString());

            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                final Map<String, String> params = new HashMap<>();
                params.put("X-App-Id" , "2986fb82-6dae-4e5b-a070-ec57e8849b84");
                params.put("X-App-Token" , "90bf01af-4ac2-4d0e-970d-04ff3a639bfb");
                params.put("Content-Type" , "application/json");
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
    public void Compaing(final String name){

        Gson gson = new Gson();

        C_Discount c_discount = new C_Discount("100.0","PERCENT");

        C_Redemption c_redemption = new C_Redemption(1);

        Code_Config code_config = new Code_Config("TC6-PROMO-#######");

        Voucher voucher = new Voucher("DISCOUNT_VOUCHER",c_discount,c_redemption,code_config);

        Com_metadata com_metadata = new Com_metadata(true);

        CompaignData compaignData = new CompaignData(name, "2016-10-26T00:00:00Z", "2025-12-26T00:00:00Z", 10, voucher, com_metadata);

        final String json1 = gson.toJson(compaignData);

        Log.i("com",""+json1);

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String url = "https://us1.api.voucherify.io/v1/campaigns";

        final String requestBody = json1.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("Compaign", response);
                try {

                    JSONObject jsonObject = new JSONObject(response);

                    SharedPreferences preferences = getSharedPreferences("Campaign name", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("campaign", name);
                    editor.apply();
                    editor.commit();

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),""+e,Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Compaign", error.toString());

            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                final Map<String, String> params = new HashMap<>();
                params.put("X-App-Id" , "2986fb82-6dae-4e5b-a070-ec57e8849b84");
                params.put("X-App-Token" , "90bf01af-4ac2-4d0e-970d-04ff3a639bfb");
                params.put("Content-Type" , "application/json");
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

}
