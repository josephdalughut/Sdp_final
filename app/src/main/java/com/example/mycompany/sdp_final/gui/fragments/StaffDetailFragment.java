package com.example.mycompany.sdp_final.gui.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mycompany.sdp_final.R;
import com.example.mycompany.sdp_final.entities.Staff;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

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
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        action = (FloatingActionButton) findViewById(R.id.action);
        back = (ImageButton)findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
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

        LinearLayoutManager verticalLayoutManager = new RecyclerViewLayoutManager(getContext());
        verticalLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getContext());
        horizontalLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        infoRecyclerView.setLayoutManager(verticalLayoutManager);
        coursesRecyclerView.setLayoutManager(horizontalLayoutManager);
        setupAdapters();
        return rootView;
    }

    private void goBack(){
        super.getActivity().onBackPressed();
    }

    private void setupAdapters(){
        List<DetailItem> detailItems = new ArrayList<>();
        if(staff.getEmails()!=null && !staff.getEmails().isEmpty()){
            for(int i = 0; i < staff.getEmails().size(); i++){
                detailItems.add(new DetailItem(i == 0 ? R.mipmap.ic_action_maps_local_post_office_icon : null, staff.getEmails().get(i), i == staff.getEmails().size() -1 ));
            }
        }
        if(staff.getPhones()!=null && !staff.getPhones().isEmpty()){
            for(int i = 0; i < staff.getPhones().size(); i++){
                detailItems.add(new DetailItem(i == 0 ? R.mipmap.ic_action_maps_local_phone_icon : null, staff.getPhones().get(i), i == staff.getPhones().size() -1 ));
            }
        }
        DetailAdapter detailAdapter = new DetailAdapter(detailItems);
        infoRecyclerView.setAdapter(detailAdapter);
        coursesRecyclerView.setAdapter(new CourseAdapter(staff.getCourses()));
    }

    private class DetailItem {
        Integer iconRes;
        String text;
        boolean showDivider;
        public DetailItem(Integer iconRes, String text, boolean showDivider){
            this.iconRes = iconRes; this.text = text; this.showDivider = showDivider;
        }
    }

    private class DetailViewHolder extends RecyclerView.ViewHolder {

        TextView text;
        View divider;
        ImageButton icon;

        public DetailViewHolder(View itemView) {
            super(itemView);
            text = (TextView) findViewById(R.id.text);
            divider = findViewById(R.id.divider);
            icon = (ImageButton) findViewById(R.id.icon);
        }

        View findViewById(int resId){
            return itemView.findViewById(resId);
        }
    }

    private class CourseViewHolder extends RecyclerView.ViewHolder {

        TextView text;

        public CourseViewHolder(View itemView) {
            super(itemView);
            text = (TextView) findViewById(R.id.text);
        }

        View findViewById(int resId){
            return itemView.findViewById(resId);
        }
    }

    private class CourseAdapter extends RecyclerView.Adapter<CourseViewHolder>{

        public CourseAdapter(List<String> courses){
            this.courses = courses;
        }

        private List<String> courses;

        @Override
        public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CourseViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_course, parent, false));
        }

        @Override
        public void onBindViewHolder(CourseViewHolder holder, int position) {
            holder.text.setText(getItem(position));
        }

        private String getItem(int position){
            return courses.get(position);
        }

        @Override
        public int getItemCount() {
            return courses==null ? 0 : courses.size();
        }
    }

    private class DetailAdapter  extends RecyclerView.Adapter<DetailViewHolder> {

        public DetailAdapter(List<DetailItem> items){
            this.itemList = items;
            notifyDataSetChanged();
            Log.d("Denty", "Number of items in detail list: "+items.size());
        }

        private List<DetailItem> itemList;

        @Override
        public DetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new DetailViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_detail_with_icon, parent, false));
        }

        @Override
        public void onBindViewHolder(DetailViewHolder holder, int position) {
            DetailItem item = getItem(position);
            holder.divider.setVisibility(item.showDivider ? View.VISIBLE : View.INVISIBLE);
            holder.text.setText(item.text);
            if(item.iconRes !=null){
                holder.icon.setImageResource(item.iconRes);
            }
        }

        private DetailItem getItem(int position){
            return itemList.get(position);
        }

        @Override
        public int getItemCount() {
            return itemList == null ? 0 : itemList.size();
        }
    }

    void sendEmail(){
        if(staff.getEmails()==null || staff.getEmails().isEmpty()) return;
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", staff.getEmails().get(0), null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "AUN");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{staff.getEmails().get(0)});
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }

    public static class RecyclerViewLayoutManager extends LinearLayoutManager {

        public RecyclerViewLayoutManager(Context context)    {
            super(context);
        }

        private int[] mMeasuredDimension = new int[2];

        @Override
        public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state,
                              int widthSpec, int heightSpec) {
            final int widthMode = View.MeasureSpec.getMode(widthSpec);
            final int heightMode = View.MeasureSpec.getMode(heightSpec);
            final int widthSize = View.MeasureSpec.getSize(widthSpec);
            final int heightSize = View.MeasureSpec.getSize(heightSpec);
            int width = 0;
            int height = 0;
            for (int i = 0; i < getItemCount(); i++) {

                if (getOrientation() == HORIZONTAL) {

                    measureScrapChild(recycler, i,
                            View.MeasureSpec.makeMeasureSpec(i, View.MeasureSpec.UNSPECIFIED),
                            heightSpec,
                            mMeasuredDimension);

                    width = width + mMeasuredDimension[0];
                    if (i == 0) {
                        height = mMeasuredDimension[1];
                    }
                } else {
                    measureScrapChild(recycler, i,
                            widthSpec,
                            View.MeasureSpec.makeMeasureSpec(i, View.MeasureSpec.UNSPECIFIED),
                            mMeasuredDimension);
                    height = height + mMeasuredDimension[1];
                    if (i == 0) {
                        width = mMeasuredDimension[0];
                    }
                }
            }
            switch (widthMode) {
                case View.MeasureSpec.EXACTLY:
                    width = widthSize;
                case View.MeasureSpec.AT_MOST:
                case View.MeasureSpec.UNSPECIFIED:
            }

            switch (heightMode) {
                case View.MeasureSpec.EXACTLY:
                    height = heightSize;
                case View.MeasureSpec.AT_MOST:
                case View.MeasureSpec.UNSPECIFIED:
            }

            setMeasuredDimension(width, height);
        }

        private void measureScrapChild(RecyclerView.Recycler recycler, int position, int widthSpec,
                                       int heightSpec, int[] measuredDimension) {
            View view = recycler.getViewForPosition(position);
            recycler.bindViewToPosition(view, position);
            if (view != null) {
                RecyclerView.LayoutParams p = (RecyclerView.LayoutParams) view.getLayoutParams();
                int childWidthSpec = ViewGroup.getChildMeasureSpec(widthSpec,
                        getPaddingLeft() + getPaddingRight(), p.width);
                int childHeightSpec = ViewGroup.getChildMeasureSpec(heightSpec,
                        getPaddingTop() + getPaddingBottom(), p.height);
                view.measure(childWidthSpec, childHeightSpec);
                try {
                    int w = measuredDimension[0];
                    int n = view.getMeasuredWidth() + p.leftMargin + p.rightMargin;
                    measuredDimension[0] = w > n ? w : n;
                }catch (Exception e){
                    measuredDimension[0] = view.getMeasuredWidth() + p.leftMargin + p.rightMargin;
                }
                measuredDimension[1] = view.getMeasuredHeight() + p.bottomMargin + p.topMargin;
                recycler.recycleView(view);
            }
        }
    }

}
