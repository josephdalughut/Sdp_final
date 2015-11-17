package com.example.mycompany.sdp_final;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

/**
 * Created by Joseph Dalughut on 17/11/2015 at 10:13 PM.
 * Project name : Sdp_final.
 * Copyright (c) 2015 Meengle. All rights reserved.
 */
public class FacilityDetailFragment extends Fragment {

    private Facility facility;

    public static FacilityDetailFragment getInstance(Facility facility){
        return new FacilityDetailFragment().setFacility(facility);
    }

    private FacilityDetailFragment setFacility(Facility facility){this.facility = facility; return this;}

    private TextView name, description, directions, offices, staff;
    private GridView images;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }



}
