package com.example.beershop.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.beershop.R;
import com.google.android.material.button.MaterialButton;

public class MainCustomerFragment extends Fragment {
    public MainCustomerFragment() {
        // Required empty public constructor
    }

    private View v;
    private MaterialButton btn_take_survey;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_main_customer, container, false);
        init();
        return v;
    }

    private void init() {
        btn_take_survey = v.findViewById(R.id.btn_take_survey);

        btn_take_survey.setOnClickListener(Navigation.
                createNavigateOnClickListener(R.id.action_mainCustomerFragment_to_takeSurveyFragment));

    }
}
