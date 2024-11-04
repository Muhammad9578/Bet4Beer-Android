package com.example.beershop.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.surveymonkey.surveymonkeyandroidsdk.SMFeedbackFragmentListener;
import com.surveymonkey.surveymonkeyandroidsdk.utils.SMError;

import org.json.JSONObject;

public class SimpleFragmentActivity extends FragmentActivity implements SMFeedbackFragmentListener {
    public static final String COLLECTOR_HASH = "collectorHash";
    private String mCollectorHash;
    private Toast mThanksToast;
    private Toast mErrorToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public void onFetchRespondentSuccess(JSONObject respondent) {
    }

    public void onFetchRespondentFailure(SMError e) {
    }
}
