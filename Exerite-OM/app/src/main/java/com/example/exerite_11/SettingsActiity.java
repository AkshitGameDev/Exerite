package com.example.exerite_11;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Trace;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsActiity#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsActiity extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingsActiity() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsActiity.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsActiity newInstance(String param1, String param2) {
        SettingsActiity fragment = new SettingsActiity();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    Button EditProfile;
    Button SignOut;
    public static TextView name;


    public String TrimIfNeeded(){
        String temp = Login_activity.name;
        if(temp.length() >= 5){
            temp = "Hey, " + temp.substring(0, 4) + "..." + "!";
            return temp;
        }
        else return  "Hey, " + temp + "!";
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_settings_actiity, container, false);

        name = rootView.findViewById(R.id.greetingTextView);
        name.setText(TrimIfNeeded());

        // Set up sign out button
        SignOut = rootView.findViewById(R.id.logOutButton);
        SignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Assuming MainActivity is your "sign out" destination and clears the user session
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clears the activity stack
                startActivity(intent);
            }
        });

        // Set up edit profile button
        EditProfile = rootView.findViewById(R.id.editProfileButton);
        EditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Assuming EditProfile is your "edit profile" destination
                startActivity(new Intent(getContext(), EditProfile.class)); // Start the EditProfile Activity
            }
        });

        return rootView;
    }


}