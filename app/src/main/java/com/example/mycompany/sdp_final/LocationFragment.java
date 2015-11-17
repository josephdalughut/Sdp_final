package com.example.mycompany.sdp_final;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class LocationFragment extends Fragment {

    public static final String ARG_PARAM1 = "locationName";

    // TODO: Rename and change types of parameters
    private String locationName;

    public static LocationFragment newInstance(String locationName) {
        LocationFragment fragment = new LocationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, locationName);
        fragment.setArguments(args);
        return fragment;
    }

    public LocationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            locationName = getArguments().getString(ARG_PARAM1);
        }
    }

    private TextView location;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_location, container, false);
        location = (TextView) v.findViewById(R.id.locationName);
        location.setText(locationName);
        return v;
    }

}
