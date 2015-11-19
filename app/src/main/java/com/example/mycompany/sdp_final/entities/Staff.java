package com.example.mycompany.sdp_final.entities;

import android.database.Cursor;

import com.example.mycompany.sdp_final.Database;

import java.util.List;

/**
 * Created by Joseph Dalughut on 19/11/2015 at 4:19 PM.
 * Project name : Sdp_final.
 * Copyright (c) 2015 Meengle. All rights reserved.
 */
public class Staff {

    public static final class Constants {

        public static final String TABLE_NAME = "staff";

        public static final class Columns {
            public static final String ID = "_id";
            public static final String NAME = "name";
            public static final String OFFICE = "office";
            public static final String DESCRIPTION = "description";
            public static final String EMAILS = "emails";
            public static final String PHONES = "phones";
            public static final String IMAGE = "image";
            public static final String COURSES = "courses";
        }
    }

    public static String TABLE_CREATE_STATEMENT(){
        return "CREATE TABLE IF NOT EXISTS "+Constants.TABLE_NAME + " ( "
                +Constants.Columns.ID + " TEXT PRIMARY KEY, "
                +Constants.Columns.NAME + " TEXT, "
                +Constants.Columns.OFFICE + " TEXT, "
                + Constants.Columns.DESCRIPTION + " TEXT, "
                + Constants.Columns.EMAILS + " TEXT, "
                + Constants.Columns.IMAGE + " TEXT, "
                + Constants.Columns.PHONES + " TEXT, "
                + Constants.Columns.COURSES + " TEXT);";
    }

    private String id, name, office, description, image;
    private List<String> emails, phones, courses;

    public Staff setId(String id){
        this.id = id; return this;
    }
    public Staff setName(String name){this.name = name;  return this;}
    public Staff setOffice(String office){this.office = office; return this;}
    public Staff setDescription(String description){this.description = description; return this;}
    public Staff setEmails(List<String> emails){this.emails = emails; return this;}
    public Staff setPhones(List<String> phones){this.phones = phones; return this;}
    public Staff setCourses(List<String> courses){this.courses = courses; return this;}
    public Staff setImage(String image){this.image = image; return this;}

    public String getId(){return this.id;}
    public String getOffice(){return this.office;}
    public String getDescription(){return this.description;}
    public List<String> getEmails(){return this.emails;}
    public List<String> getPhones(){return this.phones;}
    public List<String> getCourses(){return this.courses;}
    public String getName(){return this.name;}
    public String getImage(){return this.image;}

    public static Staff toStaff(Cursor cursor){
        return new Staff()
                .setId(cursor.getString(cursor.getColumnIndex(Constants.Columns.ID)))
                .setName(cursor.getString(cursor.getColumnIndex(Constants.Columns.NAME)))
                .setCourses(Database.listFromString(cursor.getString(cursor.getColumnIndex(Constants.Columns.COURSES))))
                .setDescription(cursor.getString(cursor.getColumnIndex(Constants.Columns.DESCRIPTION)))
                .setOffice(cursor.getString(cursor.getColumnIndex(Constants.Columns.OFFICE)))
                .setImage(cursor.getString(cursor.getColumnIndex(Constants.Columns.IMAGE)))
                .setEmails(Database.listFromString(cursor.getString(cursor.getColumnIndex(Constants.Columns.EMAILS))))
                .setPhones(Database.listFromString(cursor.getString(cursor.getColumnIndex(Constants.Columns.PHONES))));
    }
}
