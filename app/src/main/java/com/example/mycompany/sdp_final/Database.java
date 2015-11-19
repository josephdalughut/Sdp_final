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

import com.example.mycompany.sdp_final.entities.Facility;
import com.example.mycompany.sdp_final.entities.Office;
import com.example.mycompany.sdp_final.entities.Staff;

import java.util.ArrayList;
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
        values.put(Facility.Constants.Columns.FIMAGES, listToString(facility.getfImages()));
        getInstance(context).getWritableDatabase().replace(Facility.Constants.TABLE_NAME, null, values);
    }

    public static void cacheFacilities(List<Facility> facilities, Context context){
        if(facilities == null || facilities.isEmpty() || context == null) return;
        for(Facility facility : facilities)
            cache(facility, context);
    }

    public static void cache(Office office, Context context){
        if(office == null) return;
        ContentValues values = new ContentValues();
        values.put(Office.Constants.Columns.ID, office.getId());
        values.put(Office.Constants.Columns.NAME, office.getName());
        values.put(Office.Constants.Columns.DESCRIPTION, office.getDescription());
        values.put(Office.Constants.Columns.DIRECTIONS, office.getDirections());
        getInstance(context).getWritableDatabase().replace(Office.Constants.TABLE_NAME, null, values);
    }

    public static void cacheOffices(List<Office> offices, Context context){
        if(offices == null || offices.isEmpty() || context == null) return;
        for(Office office : offices)
            cache(office, context);
    }

    public static void cache(Staff staff, Context context){
        if(staff == null || context == null) return;
        ContentValues values = new ContentValues();
        values.put(Staff.Constants.Columns.ID, staff.getId());
        values.put(Staff.Constants.Columns.NAME, staff.getName());
        values.put(Staff.Constants.Columns.COURSES, listToString(staff.getCourses()));
        values.put(Staff.Constants.Columns.DESCRIPTION, staff.getDescription());
        values.put(Staff.Constants.Columns.EMAILS, listToString(staff.getEmails()));
        values.put(Staff.Constants.Columns.OFFICE, staff.getOffice());
        values.put(Staff.Constants.Columns.PHONES, listToString(staff.getPhones()));
        getInstance(context).getWritableDatabase().replace(Staff.Constants.TABLE_NAME, null, values);
    }

    public static String listToString(List<String> items){
        if(items==null || items.isEmpty()) return "";
        String string = "";
        for(int i = 0; i < items.size(); i++){
            string = string + (i == items.size()-1 ? items.get(i) : items.get(i)+",");
        }
        return string;
    }

    public static List<String> listFromString(String string){
        if(string == null) return null;
        String[] splits = string.split(",");
        List<String> strings = new ArrayList<>(splits.length);
        for(String s : splits)
            strings.add(s);
        return strings;
    }

}
