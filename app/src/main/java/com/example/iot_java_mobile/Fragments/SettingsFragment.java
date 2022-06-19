package com.example.iot_java_mobile.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.iot_java_mobile.Domain.SessionManager;
import com.example.iot_java_mobile.R;


public class SettingsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    SessionManager sessionManager;

    public SettingsFragment() {
        // Required empty public constructor
    }
    public SettingsFragment(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        sessionManager = new SessionManager(getActivity().getApplicationContext());

        View v = inflater.inflate(R.layout.fragment_setting, container, false);
        Button logoutBtn = v.findViewById(R.id.logoutButton);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionManager.logoutUser();

            }
        });
        return v;
    }
}