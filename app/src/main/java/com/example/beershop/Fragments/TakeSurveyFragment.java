package com.example.beershop.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.beershop.Activities.Survey_Three;
import com.example.beershop.Activities.TokenActivity;
import com.example.beershop.App;
import com.example.beershop.Events;
import com.example.beershop.Models.GlobalClass;
import com.example.beershop.R;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.otto.Subscribe;
import com.surveymonkey.surveymonkeyandroidsdk.SurveyMonkey;

public class TakeSurveyFragment extends Fragment {

    public static final int SM_REQUEST_CODE = 0;
    public static final String SM_RESPONDENT = "smRespondent";
    public static final String SM_ERROR = "smError";
    public static final String RESPONSES = "responses";
    public static final String QUESTION_ID = "question_id";
    public static final String FEEDBACK_QUESTION_ID = "813797519";
    public static final String ANSWERS = "answers";
    public static final String ROW_ID = "row_id";
    public static final String FEEDBACK_FIVE_STARS_ROW_ID = "9082377273";
    public static final String FEEDBACK_POSITIVE_ROW_ID_2 = "9082377274";
    public static final String SAMPLE_APP = "Sample App";
    private FirebaseAuth mAuth;
    private String s1 = "", s2 = "", uid = "", uid2 = "", s3 = "", uid3 = "";

    private SurveyMonkey s = new SurveyMonkey();

    public TakeSurveyFragment() {

    }

    private View v;
    private CardView survey1, survey2, survey3;
    private RelativeLayout lay1, lay2, lay3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        App.bus.register(this);
        v = inflater.inflate(R.layout.fragment_take_survey, container, false);
        init();
        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.i("mytag", "takesurvey onAttach");
    }

    /*@Override
    public void onResume() {
        super.onResume();
        Log.i("mytag", "takesurvey onresume");
        if (v != null) {
            Log.i("mytag", "v not null");
            init();
        } else {
            Log.i("mytag", "v is null");
        }
    }*/

    private void init() {
        survey1 = v.findViewById(R.id.survey1);
        survey2 = v.findViewById(R.id.survey2);
        survey3 = v.findViewById(R.id.survey3);
        lay1 = v.findViewById(R.id.layout_1);
        lay2 = v.findViewById(R.id.layout_2);
        lay3 = v.findViewById(R.id.layout_3);

        mAuth = FirebaseAuth.getInstance();

        SharedPreferences preferences = getActivity().getSharedPreferences("Survey 1", Context.MODE_PRIVATE);
        s1 = preferences.getString("survey1", "");
        uid = preferences.getString("udid", "");

        if (s1.equalsIgnoreCase("completed") && uid.equalsIgnoreCase(mAuth.getCurrentUser().getUid())) {

            lay1.getBackground().setTint(getResources().getColor(R.color.dark_blue_app));
            lay2.getBackground().setTint(getResources().getColor(R.color.light_blue));
        }


        SharedPreferences preferences1 = getActivity().getSharedPreferences("Survey 2", Context.MODE_PRIVATE);
        s2 = preferences1.getString("survey2", "");
        uid2 = preferences1.getString("udid", "");

        if (s2.equalsIgnoreCase("completed1") && uid.equalsIgnoreCase(mAuth.getCurrentUser().getUid())) {

            lay2.getBackground().setTint(getResources().getColor(R.color.dark_blue_app));
            lay3.getBackground().setTint(getResources().getColor(R.color.light_blue));
        }

        SharedPreferences preferences3 = getActivity().getSharedPreferences("Survey three", Context.MODE_PRIVATE);
        s3 = preferences3.getString("survey3", "");
        uid3 = preferences3.getString("udid", "");

        if (s3.equalsIgnoreCase("completed") && uid3.equalsIgnoreCase(mAuth.getCurrentUser().getUid())) {

            lay3.getBackground().setTint(getResources().getColor(R.color.dark_blue_app));
        }

        survey1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GlobalClass.i = 1;
                SharedPreferences preferences = getActivity().getSharedPreferences("Survey 1", Context.MODE_PRIVATE);
                s1 = preferences.getString("survey1", "");
                uid = preferences.getString("udid", "");
                if (s1.equals("completed") && uid.equalsIgnoreCase(mAuth.getCurrentUser().getUid())) {
                    TokenFragment tokenFragment = new TokenFragment();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.framelayout, tokenFragment, tokenFragment.getClass().getSimpleName()).addToBackStack(TokenFragment.class.getName()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
                } else {
                    String SURVEY_HASH = "6WJ2KQR";
                    takeSurvey(SURVEY_HASH, 0);
                }

            }
        });
        survey2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalClass.i = 1;
                SharedPreferences preferences = getActivity().getSharedPreferences("Survey 2", Context.MODE_PRIVATE);
                s2 = preferences.getString("survey2", "");
                uid2 = preferences.getString("udid", "");

                if (s2.equals("completed1") && uid2.equalsIgnoreCase(mAuth.getCurrentUser().getUid())) {
                    Token2Fragment creditsReceivedFragment = new Token2Fragment();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.framelayout, creditsReceivedFragment, creditsReceivedFragment.getClass().getSimpleName()).addToBackStack(Token2Fragment.class.getName()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();


                } else {
                    if (s1.equalsIgnoreCase("completed") && uid.equalsIgnoreCase(mAuth.getCurrentUser().getUid())) {
                        String SURVEY_HASH = "ZFWXRTM";
                        takeSurvey(SURVEY_HASH, 1);

                    } else {
                        Toast.makeText(getActivity(), "Complete first Tier", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        survey3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences preferences = getActivity().getSharedPreferences("Survey 2", Context.MODE_PRIVATE);
                s2 = preferences.getString("survey2", "");
                uid2 = preferences.getString("udid", "");

                SharedPreferences preferences3 = getActivity().getSharedPreferences("Survey three", Context.MODE_PRIVATE);
                s3 = preferences3.getString("survey3", "");
                uid3 = preferences3.getString("udid", "");

                if (s2.equalsIgnoreCase("completed1") && uid2.equalsIgnoreCase(mAuth.getCurrentUser().getUid())) {

                    if (s3.equalsIgnoreCase("completed") && uid3.equalsIgnoreCase(mAuth.getCurrentUser().getUid())) {

                        Intent intent = new Intent(getContext(), TokenActivity.class);
                        getContext().startActivity(intent);

                    } else {
                        Intent intent = new Intent(getContext(), Survey_Three.class);
                        getContext().startActivity(intent);
                    }
                } else {

                    Toast.makeText(getActivity(), "Complete second Tier", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void takeSurvey(String SURVEY_HASH, int code) {
        Log.i("mytag", "inside take survey");
        s.startSMFeedbackActivityForResult(getActivity(), code, SURVEY_HASH);

    }

    public void takeSurveyFragment(View view) {

    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Subscribe
    public void onSurveyComplete(Events.SurveyCompleted e) {
        init();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        App.bus.unregister(this);
    }
}