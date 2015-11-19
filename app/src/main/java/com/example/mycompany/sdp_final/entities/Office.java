package com.example.mycompany.sdp_final.entities;

import android.database.Cursor;

import java.util.List;

/**
 * Created by Joseph Dalughut on 19/11/2015 at 4:19 PM.
 * Project name : Sdp_final.
 * Copyright (c) 2015 Meengle. All rights reserved.
 */
public class Office {

    public static final class Constants {

        public static final String TABLE_NAME = "offices";

        public static final class Columns {
            public static final String ID = "_id";
            public static final String DIRECTIONS = "directions";
            public static final String FACILITY = "facility";
            public static final String DESCRIPTION = "description";
            public static final String NAME = "name";

        }
    }

    public static String TABLE_CREATE_STATEMENT(){
        return "CREATE TABLE IF NOT EXISTS "+Constants.TABLE_NAME + " ( "
                + Constants.Columns.ID + " TEXT PRIMARY KEY, "
                + Constants.Columns.DIRECTIONS + " TEXT, "
                + Constants.Columns.FACILITY + " TEXT, "
                + Constants.Columns.NAME + " TEXT, "
                + Constants.Columns.DESCRIPTION + " TEXT);";
    }

    public String id, directions, facility, desctiption, name;
    private List<Staff> staff;

    public Office setId(String id){this.id = id; return this;}
    public Office setDirections(String directions){this.directions = directions; return this;}
    public Office setFacility(String facility){this.facility = facility; return this;}
    public Office setDescription(String description){this.desctiption = description; return this;}
    public Office setStaff(List<Staff> staff){this.staff = staff; return this;}
    public Office setName(String name){this.name = name; return this;}

    public String getId(){return this.id;}
    public String getDirections(){return this.directions;}
    public String getFacility(){return this.facility;}
    public String getDescription(){return this.desctiption;}
    public List<Staff> getStaff(){return this.staff;}
    public String getName(){return this.name;}

    public static Office toOffice(Cursor cursor){
        return new Office()
                .setId(cursor.getString(cursor.getColumnIndex(Constants.Columns.ID)))
                .setDirections(cursor.getString(cursor.getColumnIndex(Constants.Columns.DIRECTIONS)))
                .setFacility(cursor.getString(cursor.getColumnIndex(Constants.Columns.FACILITY)))
                .setDescription(cursor.getString(cursor.getColumnIndex(Constants.Columns.DESCRIPTION)))
                .setName(cursor.getString(cursor.getColumnIndex(Constants.Columns.NAME)));
    }

}
