package com.example.mycompany.sdp_final;

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
                + Constants.Columns.PHONES + " TEXT, "
                + Constants.Columns.COURSES + " TEXT);";
    }

    private String id, name, office, description;
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
}
