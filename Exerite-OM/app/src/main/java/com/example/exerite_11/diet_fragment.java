package com.example.exerite_11;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link diet_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class diet_fragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    ImageView vegbtn, nonvegbtn, drinkbtn;

    public diet_fragment() {
        // Required empty public constructor
    }

    public static diet_fragment newInstance(String param1, String param2) {
        diet_fragment fragment = new diet_fragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_diet_fragment, container, false);

        // Find ImageViews by ID
        vegbtn = view.findViewById(R.id.Veg);
        nonvegbtn = view.findViewById(R.id.NonVeg);
        drinkbtn = view.findViewById(R.id.Drinks);

        // Set OnClickListener for vegbtn
        vegbtn.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), VegActivity.class);
            startActivity(intent);
        });

        // Set OnClickListener for nonvegbtn
        nonvegbtn.setOnClickListener(v -> {
            // Handle click event for nonvegbtn
            // Example: startNewActivity(NonVegActivity.class);
            Intent intent = new Intent(getContext(), NonVegActivity.class);
            startActivity(intent);
        });

        // Set OnClickListener for drinkbtn
        drinkbtn.setOnClickListener(v -> {
            // Handle click event for drinkbtn
            // Example: startNewActivity(DrinksActivity.class);
            Intent intent = new Intent(getContext(), DrinksActivity.class);
            startActivity(intent);
        });

        return view;
    }

}
