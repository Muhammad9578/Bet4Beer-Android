package com.example.beershop.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.beershop.Activities.CustomerLandingActivity;
import com.example.beershop.App;
import com.example.beershop.Events;
import com.example.beershop.Models.SurveyData;
import com.example.beershop.R;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.surveymonkey.surveymonkeyandroidsdk.SurveyMonkey;

import static android.content.Context.MODE_PRIVATE;

public class TokenFragment extends Fragment {

    private SurveyMonkey s = new SurveyMonkey();

    public TokenFragment() {
    }

    private View v;
    private FirebaseAuth mAuth;
    private String url, code;
    private ProgressDialog pd;
    private DatabaseReference mDatabase;
    private MaterialButton no;
    private RelativeLayout yes;
    private ImageView qr_code, share_ic;
    private String s1, uid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("mytag", "inside token frag");
        v = inflater.inflate(R.layout.fragment_token, container, false);
        init();
        return v;
    }

    private void init() {
        no = v.findViewById(R.id.no);
        yes = v.findViewById(R.id.yes);
        qr_code = v.findViewById(R.id.qr_code);
        share_ic = v.findViewById(R.id.share_ic);

        pd = new ProgressDialog(getContext());
        pd.setCancelable(false);
        pd.setMessage("Please wait...");
        mAuth = FirebaseAuth.getInstance();

        SharedPreferences prefs = getActivity().getSharedPreferences("Voucher one", MODE_PRIVATE);
        url = prefs.getString("url", "");
        code = prefs.getString("code", "");

        SharedPreferences preferences = getActivity().getSharedPreferences("Survey 1", Context.MODE_PRIVATE);
        s1 = preferences.getString("survey1", "");
        uid = preferences.getString("udid", "");

        UploadData();

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), CustomerLandingActivity.class);
                getContext().startActivity(intent);
                getActivity().finish();
//                TakeSurveyFragment tokenFragment = new TakeSurveyFragment();
//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.framelayout, tokenFragment, tokenFragment.getClass().getSimpleName()).addToBackStack(MainCustomerFragment.class.getName()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();

            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences preferences1 = getActivity().getSharedPreferences("Survey 1", Context.MODE_PRIVATE);
                s1 = preferences1.getString("survey1", "");
                uid = preferences1.getString("udid", "");

                SharedPreferences preferences = getActivity().getSharedPreferences("Survey 2", Context.MODE_PRIVATE);
                String s2 = preferences.getString("survey2", "");
                String uid2 = preferences.getString("udid", "");

                if (s2.equals("completed1") && uid2.equalsIgnoreCase(mAuth.getCurrentUser().getUid())) {
                    Token2Fragment creditsReceivedFragment = new Token2Fragment();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.framelayout, creditsReceivedFragment, creditsReceivedFragment.getClass().getSimpleName()).addToBackStack(Token2Fragment.class.getName()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();


                } else {
                    if (s1.equalsIgnoreCase("completed")) {
                        String SURVEY_HASH = "ZFWXRTM";
                        takeSurvey(SURVEY_HASH, 1);

                    } else {
                        Toast.makeText(getActivity(), "Complete first Survey", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        share_ic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareMe();
            }
        });
    }

    private void ShareMe() {

        BitmapDrawable bitmapDrawable = ((BitmapDrawable) qr_code.getDrawable());
        Bitmap bitmap = bitmapDrawable.getBitmap();
        String bitmapPath = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), bitmap, "", null);
        Uri bitmapUri = Uri.parse(bitmapPath);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/jpeg");
        shareIntent.putExtra(Intent.EXTRA_STREAM, bitmapUri);
        startActivity(Intent.createChooser(shareIntent, "Share Image"));
    }

    public void UploadData() {

        pd.show();
        Glide.with(getContext()).load(url).into(qr_code);

        long tsLong = System.currentTimeMillis() / 1000;
        long ts = tsLong;

        if (s1.equals("completed") && uid.equalsIgnoreCase(mAuth.getCurrentUser().getUid())) {

            pd.dismiss();
        } else {
            mDatabase = FirebaseDatabase.getInstance().getReference();

            SurveyData surveyData = new SurveyData(code, url, "Tier 1", "1");

            mDatabase.child("Surveys").child(mAuth.getCurrentUser().getUid()).child(Long.toString(tsLong)).setValue(surveyData);

            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    SharedPreferences sharedpreferences = getActivity().getSharedPreferences("Survey 1", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("survey1", "completed");
                    editor.putString("udid", mAuth.getCurrentUser().getUid());
                    editor.apply();
                    editor.commit();
                    App.bus.post(new Events.SurveyCompleted());

                    // SurveyData surveyData = dataSnapshot.getValue(SurveyData.class);
                    pd.dismiss();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                    pd.dismiss();
                }
            });
        }
    }

    public void takeSurvey(String SURVEY_HASH, int code) {


        s.startSMFeedbackActivityForResult(getActivity(), code, SURVEY_HASH);

    }

    public void takeSurveyFragment(View view) {

    }

}
