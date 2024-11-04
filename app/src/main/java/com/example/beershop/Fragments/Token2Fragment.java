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

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.example.beershop.Activities.CustomerLandingActivity;
import com.example.beershop.Activities.Survey_Three;
import com.example.beershop.Activities.TokenActivity;
import com.example.beershop.Models.SurveyData;
import com.example.beershop.R;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.Context.MODE_PRIVATE;

public class Token2Fragment extends Fragment {
    public Token2Fragment() {
        // Required empty public constructor
    }

    private View v;
    private MaterialButton no;
    private RelativeLayout yes;
    private ImageView qr_code, share_ic;
    private FirebaseAuth mAuth;
    private String url, code;
    private ProgressDialog pd;
    private DatabaseReference mDatabase;
    private String s1,uid, s3, uid3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_token2, container, false);

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
        mAuth=FirebaseAuth.getInstance();

        SharedPreferences prefs = getActivity().getSharedPreferences("Voucher two", MODE_PRIVATE);
        url = prefs.getString("url","");
        code = prefs.getString("code","");

        SharedPreferences preferences = getActivity().getSharedPreferences("Survey 2", Context.MODE_PRIVATE);
        s1 = preferences.getString("survey2", "");
        uid = preferences.getString("udid","");

        UploadData();

        SharedPreferences preferences3 = getActivity().getSharedPreferences("Survey three", Context.MODE_PRIVATE);
        s3 = preferences3.getString("survey3", "");
        uid3 = preferences3.getString("udid","");

        yes.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        SharedPreferences preferences3 = getActivity().getSharedPreferences("Survey three", Context.MODE_PRIVATE);
        s3 = preferences3.getString("survey3", "");
        uid3 = preferences3.getString("udid","");

        if (s3.equalsIgnoreCase("completed") && uid3.equalsIgnoreCase(mAuth.getCurrentUser().getUid())) {

            Intent intent = new Intent(getContext(), TokenActivity.class);
            getContext().startActivity(intent);

        } else{
            Intent intent = new Intent(getContext(), Survey_Three.class);
            getContext().startActivity(intent);
        }
    }
});
  no.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        Intent intent = new Intent(getContext(), CustomerLandingActivity.class);
        getContext().startActivity(intent);
        getActivity().finish();
//        TakeSurveyFragment tokenFragment = new TakeSurveyFragment();
//        getActivity().getSupportFragmentManager().beginTransaction()
//                .replace(R.id.framelayout, tokenFragment, tokenFragment.getClass().getSimpleName()).addToBackStack(MainCustomerFragment.class.getName()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();

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
        Bitmap bitmap = bitmapDrawable .getBitmap();
        String bitmapPath = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), bitmap,"", null);
        Uri bitmapUri = Uri.parse(bitmapPath);
        Intent shareIntent=new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/jpeg");
        shareIntent.putExtra(Intent.EXTRA_STREAM, bitmapUri);
        startActivity(Intent.createChooser(shareIntent,"Share Image"));
    }

    public void UploadData() {

        pd.show();
        Glide.with(getContext()).load(url).into(qr_code);

        Long tsLong = System.currentTimeMillis() / 1000;
        long ts = tsLong;

        if (s1.equals("completed1") && uid.equalsIgnoreCase(mAuth.getCurrentUser().getUid())) {

            pd.dismiss();
        } else {
            mDatabase = FirebaseDatabase.getInstance().getReference();

            SurveyData surveyData = new SurveyData(code, url, "Tier 2", "3");

            mDatabase.child("Surveys").child(mAuth.getCurrentUser().getUid()).child(tsLong.toString()).setValue(surveyData);

            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    SharedPreferences sharedpreferences = getActivity().getSharedPreferences("Survey 2", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("survey2", "completed1");
                    editor.putString("udid", mAuth.getCurrentUser().getUid());
                    editor.apply();
                    editor.commit();

                    pd.dismiss();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                    pd.dismiss();
                }
            });
        }
    }
}
