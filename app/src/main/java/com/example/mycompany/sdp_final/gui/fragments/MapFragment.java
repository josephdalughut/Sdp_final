package com.example.mycompany.sdp_final.gui.fragments;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mycompany.sdp_final.Database;
import com.example.mycompany.sdp_final.R;
import com.example.mycompany.sdp_final.entities.Facility;
import com.example.mycompany.sdp_final.gui.activities.MainActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Joseph Dalughut on 20/11/2015 at 1:43 AM.
 * Project name : Sdp_final.
 * Copyright (c) 2015 Meengle. All rights reserved.
 */
public class MapFragment extends Fragment {

    public static MapFragment getInstance(){
        return new MapFragment();
    }

    View rootView;
    TextView textSource;
    Bitmap bit;
    ImageView imageView;

    private HashMap<Rect, Facility> FACILITIES = new HashMap<>();

    View findViewById(int resId){
        return rootView.findViewById(resId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main, container, false);

        textSource = (TextView) findViewById(R.id.sourceuri);
        imageView = (ImageView) findViewById(R.id.view);
        //Load the background image

        try {
            bit = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        imageView.setOnTouchListener(new View.OnTouchListener(){

            //@Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                int x = (int) event.getX();
                int y = (int) event.getY();
                tryFit(x, y);
                switch(action){
                    case MotionEvent.ACTION_DOWN:
                        textSource.setText("ACTION_DOWN-" + x + " : " + y);
                        textSource.setBackgroundColor(
                                getProjectedColor((ImageView) v, bit, x, y));
                        break;
                    case MotionEvent.ACTION_MOVE:
                        textSource.setText("ACTION_MOVE-" + x + " : " + y);
                        textSource.setBackgroundColor(
                                getProjectedColor((ImageView) v, bit, x, y));
                        break;
                    case MotionEvent.ACTION_UP:

                        textSource.setText("ACTION_UP-" + x + " : " + y);
                        textSource.setBackgroundColor(
                                getProjectedColor((ImageView) v, bit, x, y));

                        break;
                }
    /*
     * Return 'true' to indicate that the event have been consumed.
     * If auto-generated 'false', your code can detect ACTION_DOWN only,
     * cannot detect ACTION_MOVE and ACTION_UP.
     */
                return true;
            }});
        loadFacilities();
        return rootView;
    }

    private void loadFacilities(){
        new AsyncTask<Void, Void, HashMap<Rect, Facility>>(){

            @Override
            protected HashMap<Rect, Facility> doInBackground(Void... params) {
                HashMap<Rect, Facility> h = new HashMap<Rect, Facility>();
                Cursor cursor = Database.getInstance(getContext().getApplicationContext()).getWritableDatabase().rawQuery(
                        "SELECT * FROM "+Facility.Constants.TABLE_NAME ,
                        null
                );
                if(cursor.moveToFirst())
                    do{
                        Facility facility = Facility.toFacility(cursor);
                        h.put(facility.getCoordinates(), facility);
                    }while (cursor.moveToNext());
                try {
                    cursor.close();
                }catch (Exception e){

                }
                return h;
            }

            @Override
            protected void onPostExecute(HashMap<Rect, Facility> rectFacilityHashMap) {
                FACILITIES = rectFacilityHashMap;
                imageView.setVisibility(View.VISIBLE);
                findViewById(R.id.progress).setVisibility(View.GONE);
            }
        }.execute();
    }

    /*
 * Project position on ImageView to position on Bitmap
 * return the color on the position
 */
    private int getProjectedColor(ImageView iv, Bitmap bm, int x, int y) {
        if(x<0 || y<0 || x > iv.getWidth() || y > iv.getHeight()){
            //outside ImageView
            return android.R.color.background_light;
        }else{


            int projectedX = (int)((double)x * ((double)imageView.getMeasuredWidth()/(double)iv.getMeasuredWidth()));
            int projectedY = (int)((double)y * ((double)imageView.getMeasuredHeight()/(double)iv.getMeasuredHeight()));

            textSource.setText(x + ":" + y + "/" + iv.getWidth() + " : " + iv.getHeight() + "\n" +
                            projectedX + " : " + projectedY + "/" + bm.getWidth() + " : " + bm.getHeight()
            );
            return bm.getPixel(projectedX, projectedY);
        }
    }

    private void showFacility(Facility facility){
        FacilityDetailFragment fragment = FacilityDetailFragment.getInstance(facility);
        if(fragment!=null)
        ((MainActivity) super.getActivity()).getSupportFragmentManager().beginTransaction().add(R.id.container, fragment).addToBackStack(null).commit();
    }

    private void tryFit(int x, int y){
        int pictureX = ((Float)(((float)x / (float) imageView.getMeasuredWidth()) * 885f)).intValue();
        int pictureY = ((Float)(((float)y / (float) imageView.getMeasuredHeight()) * 1227f)).intValue();

        for(Map.Entry<Rect, Facility> entry : FACILITIES.entrySet()){
            if(entry.getKey().contains(pictureX, pictureY)){
                showFacility(entry.getValue());
                return;
            }
        }
    }
}
