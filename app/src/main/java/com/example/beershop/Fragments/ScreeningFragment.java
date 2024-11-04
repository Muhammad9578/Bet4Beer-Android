package com.example.beershop.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.beershop.R;


public class ScreeningFragment extends Fragment {


    public ScreeningFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_screening, container, false);

        TextView yes = view.findViewById(R.id.yes);
        TextView no = view.findViewById(R.id.no);
        TextView potentially = view.findViewById(R.id.potentially);

        yes.setOnClickListener(Navigation.

                createNavigateOnClickListener(R.id.action_takeSurveyFragment_to_takesurveyFragment));

        potentially.setOnClickListener(Navigation.

                createNavigateOnClickListener(R.id.action_takeSurveyFragment_to_takesurveyFragment));

        no.setOnClickListener(Navigation.
                createNavigateOnClickListener(R.id.action_takeSurveyFragment_to_noFragment));

        return view;
    }



}
