package com.example.mycompany.sdp_final.gui.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
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
import com.example.mycompany.sdp_final.entities.Facility;
import com.example.mycompany.sdp_final.entities.Office;
import com.example.mycompany.sdp_final.gui.activities.MainActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

/**
 * Created by Joseph Dalughut on 17/11/2015 at 10:13 PM.
 * Project name : Sdp_final.
 * Copyright (c) 2015 Meengle. All rights reserved.
 */
public class FacilityDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, OnMapReadyCallback, AppBarLayout.OnOffsetChangedListener {

    View rootView;

    Facility facility;

    GoogleMap map;

    static FacilityDetailFragment INSTANCE = null;

    private final int LOADER_ID = MainActivity.getLoaderId();

    public static FacilityDetailFragment getInstance(Facility facility){
        if(INSTANCE!=null)
            return null;
        FacilityDetailFragment detailFragment = new FacilityDetailFragment().setFacility(facility);
        INSTANCE = detailFragment;
        return INSTANCE;
    }

    View findViewById(int resId){
        return rootView.findViewById(resId);
    }

    private FacilityDetailFragment setFacility(Facility facility){this.facility = facility; return this;}

    private TextView description, directions;
    private RecyclerView imagesRecyclerView, officesRecyclerView;
    private FloatingActionButton action;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private OfficeAdapter adapter;
    private AppBarLayout appBarLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_detail_facility, container, false);

        description = (TextView) findViewById(R.id.description);
        directions = (TextView) findViewById(R.id.directions);
        imagesRecyclerView = (RecyclerView) findViewById(R.id.imagesRecyclerView);
        officesRecyclerView = (RecyclerView) findViewById(R.id.officesRecyclerView);
        action = (FloatingActionButton) findViewById(R.id.action);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
        appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
        appBarLayout.addOnOffsetChangedListener(this);
        collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.blue));

        description.setText(facility.getDescription());
        directions.setText(facility.getDirection());
        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMap();
            }
        });

        collapsingToolbarLayout.setTitleEnabled(true);
        collapsingToolbarLayout.setTitle(facility.getName());
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);

        ImageButton back = (ImageButton) findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext());
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);

        officesRecyclerView.setLayoutManager(layoutManager);
        imagesRecyclerView.setLayoutManager(layoutManager1);

        imagesRecyclerView.setAdapter(new ImageAdapter().setImages(facility.getfImages()));
        adapter = new OfficeAdapter();
        officesRecyclerView.setAdapter(adapter);
        loadOffices();
        SupportMapFragment fragment = new SupportMapFragment();
        getChildFragmentManager().beginTransaction().replace(R.id.frame, fragment).commit();
        fragment.getMapAsync(this);
        return rootView;
    }

    private void showMap(){
        Uri gmmIntentUri = Uri.parse("geo:"+facility.getLatlng());
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }

    private void showImage(String url){
        ImageFragment fragment = ImageFragment.getInstance(url);
        ((MainActivity) super.getActivity()).getSupportFragmentManager().beginTransaction().add(R.id.container, fragment).addToBackStack(null).commit();
    }

    private void showOffice(Office office){
        OfficeDetailFragment fragment = OfficeDetailFragment.getInstance(office, facility.getLatlng());
        ((MainActivity) super.getActivity()).getSupportFragmentManager().beginTransaction().add(R.id.container, fragment).addToBackStack(null).commit();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;
        map.getUiSettings().setAllGesturesEnabled(false);
        String[] lats = facility.getLatlng().split(",");
        LatLng latLng = new LatLng(Double.valueOf(lats[0]), Double.valueOf(lats[1]));
        map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        map.addMarker(new MarkerOptions().position(latLng).title(facility.getName()));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

    }

    private static class OfficeHolder extends RecyclerView.ViewHolder {

        TextView text;
        ImageView image;
        View selector;

        View findViewById(int resId){
            return itemView.findViewById(resId);
        }

        public OfficeHolder(View itemView) {
            super(itemView);
            text = (TextView) findViewById(R.id.text);
            image = (ImageView) findViewById(R.id.image);
            selector = findViewById(R.id.selector);
        }
    }

    private class ImageHolder extends RecyclerView.ViewHolder {
        ImageView image;
        View selector;

        public ImageHolder(View itemView) {
            super(itemView);
            image = (ImageView) findViewById(R.id.image);
            selector = findViewById(R.id.selector);
        }

        View findViewById(int resId){
            return itemView.findViewById(resId);
        }
    }

    private class ImageAdapter extends RecyclerView.Adapter<ImageHolder>{

        List<String> images;

        ImageAdapter setImages(List<String> images){this.images = images; notifyDataSetChanged();return this;}

        @Override
        public ImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ImageHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image,  parent, false));
        }

        @Override
        public void onBindViewHolder(ImageHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return images == null ? 0 : images.size();
        }
    }

    private class OfficeAdapter extends RecyclerView.Adapter<OfficeHolder> {

        Cursor cursor;

        void setCursor(Cursor cursor){
            if(this.cursor != null){
                try{
                    cursor.close();
                }catch (Exception e){

                }
            }
            this.cursor = cursor;
            notifyDataSetChanged();
        }

        @Override
        public OfficeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new OfficeHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_detail_centered, parent, false));
        }

        @Override
        public void onBindViewHolder(OfficeHolder holder, int position) {
            if(!cursor.moveToPosition(position)) return;
            final Office office = Office.toOffice(cursor);
            holder.text.setText(office.getName());
            holder.selector.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showOffice(office);
                }
            });
            holder.selector.setTag(position);
        }

        @Override
        public int getItemCount() {
            return cursor == null ? 0 : cursor.getCount();
        }
    }

    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new OfficeLoader(getContext(), facility.getId());
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {
        try {
            adapter.setCursor(data);
        }catch (Exception e){

        }
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {
        loadOffices();
    }

    public static class OfficeLoader extends CursorLoader {

        String facilityId;
        Context context;

        public OfficeLoader(Context context, String facilityId){
            this(context);
            this.facilityId = facilityId;
        }

        public OfficeLoader(Context context) {
            super(context);
            this.context = context;
        }

        @Override
        public Cursor loadInBackground() {
            return Database.getInstance(context).getWritableDatabase().rawQuery(
                    "SELECT * FROM "+ Office.Constants.TABLE_NAME + " WHERE "+Office.Constants.Columns.FACILITY + " ='"
                    + facilityId + "' ORDER BY "+Office.Constants.Columns.NAME + " DESC ",
                    null
            );
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        try {
            getActivity().getSupportLoaderManager().destroyLoader(LOADER_ID);
        }catch (Exception e){

        }
        INSTANCE = null;
    }

    private void goBack(){
        super.getActivity().onBackPressed();
    }

    private void loadOffices(){
        try {
            ((MainActivity) super.getActivity()).getSupportLoaderManager().initLoader(LOADER_ID, null, this);
        }catch (Exception e){

        }
    }


}
