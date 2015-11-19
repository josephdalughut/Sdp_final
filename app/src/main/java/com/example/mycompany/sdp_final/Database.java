package com.example.mycompany.sdp_final;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import java.util.List;

/**
 * Created by Joseph Dalughut on 17/11/2015 at 9:29 PM.
 * Project name : Sdp_final.
 * Copyright (c) 2015 Meengle. All rights reserved.
 */
public class Database extends SQLiteOpenHelper {

    public static Database INSTANCE;
    private Context context;

    public static final class Constants {

        public static final String DATABASE_NAME = "database";
        public static final int DATABASE_VERSION = 1;

    }

    public static void initialize(Context context){
        INSTANCE = new Database(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    public static Database getInstance(Context context){
        if(INSTANCE == null)
            initialize(context);
        return INSTANCE;
    }

    public Database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public Database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Facility.TABLE_CREATE_STATEMENT());
        db.execSQL(Office.TABLE_CREATE_STATEMENT());
        db.execSQL(Staff.TABLE_CREATE_STATEMENT());
    }

    private boolean isDatabaseCreated(Context context){
        SharedPreferences preferences = context.getSharedPreferences(Constants.DATABASE_NAME, Context.MODE_PRIVATE);
        return preferences.getBoolean(Constants.DATABASE_NAME, false);
    }

    @SuppressLint("CommitPrefEdits")
    private void setDatabaseCreated(Context context, boolean created){
        SharedPreferences preferences = context.getSharedPreferences(Constants.DATABASE_NAME, Context.MODE_PRIVATE);
        preferences.edit().putBoolean(Constants.DATABASE_NAME, created).commit();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static void cache(Facility facility, Context context){
        if(facility == null) return;
        ContentValues values = new ContentValues();
        values.put(Facility.Constants.Columns.ID, facility.getIdByCoordinates());
        values.put(Facility.Constants.Columns.NAME, facility.getName());
        values.put(Facility.Constants.Columns.DESCRIPTION, facility.getDescription());
        values.put(Facility.Constants.Columns.DIRECTION, facility.getDirection());
        values.put(Facility.Constants.Columns.FIMAGES, facility.getFImagesAsString());
        getInstance(context).getWritableDatabase().replace(Facility.Constants.TABLE_NAME, null, values);
    }

    public static void cache(List<Facility> facilities, Context context){
        if(facilities == null || facilities.isEmpty() || context == null) return;
        for(Facility facility : facilities)
            cache(facility, context);
    }

}
