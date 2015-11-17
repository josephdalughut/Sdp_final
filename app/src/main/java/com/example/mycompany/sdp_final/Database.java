package com.example.mycompany.sdp_final;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
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

        public static final String TABLE_NAME = "facilities";

        public static final class Columns {
            public static final String ID = "_id";
            public static final String NAME = "name";
            public static final String DESCRIPTION = "description";
            public static final String DIRECTION = "direction";
            public static final String FIMAGES = "fimages";
        }
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
            String CREATE_STATEMENT = "CREATE TABLE IF NOT EXISTS " + Constants.TABLE_NAME + " ( "
                    + Constants.Columns.ID + " TEXT PRIMARY KEY, "
                    + Constants.Columns.NAME + " TEXT, "
                    + Constants.Columns.DESCRIPTION + " TEXT, "
                    + Constants.Columns.DIRECTION + " TEXT, "
                    + Constants.Columns.FIMAGES + " TEXT);";
            db.execSQL(CREATE_STATEMENT);
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
        values.put(Constants.Columns.ID, facility.getIdByCoordinates());
        values.put(Constants.Columns.NAME, facility.getName());
        values.put(Constants.Columns.DESCRIPTION, facility.getDescription());
        values.put(Constants.Columns.DIRECTION, facility.getDirection());
        values.put(Constants.Columns.FIMAGES, facility.getFImagesAsString());
        getInstance(context).getWritableDatabase().replace(Constants.TABLE_NAME, null, values);
    }

    public static void cache(List<Facility> facilities, Context context){
        if(facilities == null || facilities.isEmpty() || context == null) return;
        for(Facility facility : facilities)
            cache(facility, context);
    }

    public static Facility toFacility(Cursor cursor){
        String ID = cursor.getString(cursor.getColumnIndex(Constants.Columns.ID));
        String NAME = cursor.getString(cursor.getColumnIndex(Constants.Columns.NAME));
        String DESCRIPTION = cursor.getString(cursor.getColumnIndex(Constants.Columns.DESCRIPTION));
        String DIRECTION = cursor.getString(cursor.getColumnIndex(Constants.Columns.DIRECTION));
        String FIMAGES = cursor.getString(cursor.getColumnIndex(Constants.Columns.FIMAGES));
        return new Facility()
                .setCoordinates(Facility.getCoordinatesById(ID))
                .setName(NAME)
                .setDescription(DESCRIPTION)
                .setDirection(DIRECTION)
                .setfImages(Facility.getFImagesFromString(FIMAGES));
    }

}
