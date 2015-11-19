package com.example.mycompany.sdp_final.gui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.mycompany.sdp_final.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Created by Joseph Dalughut on 19/11/2015 at 7:42 PM.
 * Project name : Sdp_final.
 * Copyright (c) 2015 Meengle. All rights reserved.
 */
public class ImageFragment extends Fragment {

    public static ImageFragment getInstance(String url){
        return new ImageFragment().setUrl(url);
    }

    View rootView;
    ImageView image;
    FloatingActionButton action;
    ImageButton back;
    String url;

    ImageFragment setUrl(String url){this.url = url; return this;}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_image_detail, container, false);

        image = (ImageView) findViewById(R.id.image);
        action = (FloatingActionButton) findViewById(R.id.action);
        back = (ImageButton) findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        Picasso.with(getContext()).load(url).centerCrop().fit().into(image, new Callback() {
            @Override
            public void onSuccess() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        action.show();
                    }
                });
            }

            @Override
            public void onError() {

            }
        });
        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImage();
            }
        });
        return rootView;
    }

    private void saveImage(){

    }

    View findViewById(int resId){
        return rootView.findViewById(resId);
    }


}
