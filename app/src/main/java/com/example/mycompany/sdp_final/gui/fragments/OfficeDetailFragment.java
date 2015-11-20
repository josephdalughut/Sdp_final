package com.example.mycompany.sdp_final.gui.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mycompany.sdp_final.Database;
import com.example.mycompany.sdp_final.R;
import com.example.mycompany.sdp_final.entities.Office;
import com.example.mycompany.sdp_final.entities.Staff;
import com.example.mycompany.sdp_final.gui.activities.MainActivity;
import com.squareup.picasso.Picasso;

/**
 * Created by Joseph Dalughut on 19/11/2015 at 5:39 PM.
 * Project name : Sdp_final.
 * Copyright (c) 2015 Meengle. All rights reserved.
 */
public class OfficeDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public static OfficeDetailFragment getInstance(Office office, String latlng){
        return new OfficeDetailFragment().setOffice(office).setLatLng(latlng);
    }

    Office office;
    String latlng;

    private final int LOADER_ID = MainActivity.getLoaderId();

    OfficeDetailFragment setOffice(Office office){
        this.office = office; return this;
    }

    OfficeDetailFragment setLatLng(String latLng){
        this.latlng = latLng; return this;
    }

    View rootView;

    TextView title, description, directions;
    ImageButton back;
    RecyclerView staffRecyclerView;
    FloatingActionButton action;
    StaffAdapter adapter;

    View findViewById(int resId){
        return rootView.findViewById(resId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView  = inflater.inflate(R.layout.fragment_office_detail, container, false);

        title = (TextView) findViewById(R.id.title);
        description = (TextView) findViewById(R.id.description);
        action = (FloatingActionButton) findViewById(R.id.action);
        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMap();
            }
        });
        directions = (TextView) findViewById(R.id.directions);
        back = (ImageButton) findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        title.setText(office.getName());
        description.setText(office.getDescription());
        directions.setText(office.getDirections());

        staffRecyclerView = (RecyclerView) findViewById(R.id.staffRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        staffRecyclerView.setLayoutManager(layoutManager);
        adapter = new StaffAdapter();
        staffRecyclerView.setAdapter(adapter);

        loadStaff();
        return rootView;
    }

    private void goBack(){
        super.getActivity().onBackPressed();
    }

    private void loadStaff(){
        try {
            ((MainActivity) super.getActivity()).getSupportLoaderManager().initLoader(LOADER_ID, null, this);
        }catch (Exception e){

        }
    }

    private class StaffViewHolder extends RecyclerView.ViewHolder {

        TextView text;
        ImageView image;
        View selector;

        public StaffViewHolder(View itemView) {
            super(itemView);
            text = (TextView) findViewById(R.id.text);
            image = (ImageView) findViewById(R.id.image);
            selector = findViewById(R.id.selector);
        }

        View findViewById(int resId){
            return itemView.findViewById(resId);
        }
    }

    private void showStaff(Staff staff){
        StaffDetailFragment fragment = StaffDetailFragment.getInstance(staff);
        ((MainActivity)super.getActivity()).getSupportFragmentManager().beginTransaction().add(R.id.container, fragment).addToBackStack(null).commit();
    }

    private void showMap(){
        Uri gmmIntentUri = Uri.parse("geo:"+latlng);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }

    private class StaffAdapter extends RecyclerView.Adapter<StaffViewHolder>{

        Cursor cursor;

        void setCursor(Cursor cursor){
            try{
                this.cursor.close();
                this.cursor = null;
            }catch (Exception e){

            }
            this.cursor = cursor;
            notifyDataSetChanged();
        }

        @Override
        public StaffViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new StaffViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_image_detail, parent, false));
        }

        @Override
        public void onBindViewHolder(StaffViewHolder holder, int position) {
            if(!cursor.moveToPosition(position)) return;
            final Staff staff = Staff.toStaff(cursor);
            holder.text.setText(staff.getName());
            Picasso.with(holder.image.getContext()).load(staff.getImage()).centerCrop().fit().into(holder.image);
            holder.selector.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showStaff(staff);
                }
            });
        }

        @Override
        public int getItemCount() {
            return cursor == null ?  0 : cursor.getCount();
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new StaffCursorLoader(getContext(), office.getId());
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        try {
            adapter.setCursor(data);
        }catch (Exception e){

        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        loadStaff();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        try{
            adapter.cursor.close();
        }catch (Exception e){

        }
        try {
            getActivity().getSupportLoaderManager().destroyLoader(LOADER_ID);
        }catch (Exception e){

        }
    }

    static class StaffCursorLoader extends CursorLoader {

        Context context;

        public StaffCursorLoader(Context context, String officeId){
            this(context);
            this.officeId = officeId;
        }

        String officeId;

        public StaffCursorLoader(Context context) {
            super(context);
            this.context = context;
        }

        @Override
        public Cursor loadInBackground() {
            return Database.getInstance(context).getWritableDatabase().rawQuery(
                    "SELECT * FROM "+ Staff.Constants.TABLE_NAME + " WHERE "+ Staff.Constants.Columns.OFFICE
                            + " ='"+officeId+"' ORDER BY "+Staff.Constants.Columns.NAME + " ASC"
                    , null);
        }
    }

}
