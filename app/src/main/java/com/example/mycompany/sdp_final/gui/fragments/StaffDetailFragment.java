package com.example.mycompany.sdp_final.gui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mycompany.sdp_final.R;
import com.example.mycompany.sdp_final.entities.Staff;
import com.squareup.picasso.Picasso;

/**
 * Created by Joseph Dalughut on 19/11/2015 at 5:38 PM.
 * Project name : Sdp_final.
 * Copyright (c) 2015 Meengle. All rights reserved.
 */
public class StaffDetailFragment extends Fragment {

    public static StaffDetailFragment getInstance(Staff staff){
        return new StaffDetailFragment().setStaff(staff);
    }

    StaffDetailFragment setStaff(Staff staff){this.staff = staff; return this;}

    Staff staff;

    View rootView;
    CollapsingToolbarLayout collapsingToolbarLayout;
    ImageView image;
    TextView description;
    RecyclerView infoRecyclerView, coursesRecyclerView;
    FloatingActionButton action;
    ImageButton back;

    View findViewById(int resId){
        return rootView.findViewById(resId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_staff_detail, container, false);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
        image = (ImageView) findViewById(R.id.image);
        description = (TextView) findViewById(R.id.description);
        infoRecyclerView = (RecyclerView) findViewById(R.id.infoRecyclerView);
        coursesRecyclerView = (RecyclerView) findViewById(R.id.cousesRecyclerView);
        action = (FloatingActionButton) findViewById(R.id.action);
        back = (ImageButton)findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });

        collapsingToolbarLayout.setTitleEnabled(true);
        collapsingToolbarLayout.setTitle(staff.getName());
        description.setText(staff.getDescription());
        Picasso.with(getContext()).load(staff.getImage()).centerCrop().fit().into(image);

        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(getContext());
        verticalLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getContext());
        horizontalLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        infoRecyclerView.setLayoutManager(verticalLayoutManager);
        coursesRecyclerView.setLayoutManager(horizontalLayoutManager);



        return rootView;
    }

    private class DetailItem {
        private Integer iconRes;
        private String text;
        private boolean showDivider;
    }

    private class DetailViewHolder extends RecyclerView.ViewHolder {

        TextView text;
        View divider;
        ImageButton icon;

        public DetailViewHolder(View itemView) {
            super(itemView);
            text = (TextView) findViewById(R.id.text);
            divider = findViewById(R.id.divider);

        }

        View findViewById(int resId){
            return itemView.findViewById(resId);
        }
    }

    private class CourseViewHolder extends RecyclerView.ViewHolder {

        public CourseViewHolder(View itemView) {
            super(itemView);
        }

        View findViewById(int resId){
            return itemView.findViewById(resId);
        }
    }

    void sendEmail(){

    }
}
