package com.example.mycompany.sdp_final.entities;

import android.database.Cursor;
import android.graphics.Rect;

import com.example.mycompany.sdp_final.Database;

import java.util.List;

/**
 * Created by Awa on 11/13/2015.
 */


public class Facility {
    private Rect coordinates;
    private String name;
    private String description;
    private String direction;
    private List<String> fImages;

    public static final class Constants {

        public static final String TABLE_NAME = "facilities";

        public static final class Columns {
            public static final String ID = "_id";
            public static final String NAME = "name";
            public static final String DESCRIPTION = "description";
            public static final String DIRECTION = "direction";
            public static final String FIMAGES = "fimages";
        }

    }

    public static String TABLE_CREATE_STATEMENT(){
        String CREATE_STATEMENT = "CREATE TABLE IF NOT EXISTS " + Constants.TABLE_NAME + " ( "
                + Constants.Columns.ID + " TEXT PRIMARY KEY, "
                + Constants.Columns.NAME + " TEXT, "
                + Constants.Columns.DESCRIPTION + " TEXT, "
                + Constants.Columns.DIRECTION + " TEXT, "
                + Constants.Columns.FIMAGES + " TEXT);";
        return CREATE_STATEMENT;
    }

    public Facility(Rect coordinates, String name) {
        this.coordinates = coordinates;
        this.name = name;
    }

    public Facility(){}

    public Rect getCoordinates() {
        return coordinates;
    }

    public Facility setCoordinates(Rect coordinates) {
        this.coordinates = coordinates;
        return this;
    }

    public String getName() {
        return name;
    }

    public Facility setName(String name) {
        this.name = name; return this;
    }

    public String getDescription() {
        return description;
    }

    public Facility setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getDirection() {
        return direction;
    }

    public Facility setDirection(String direction) {
        this.direction = direction; return this;
    }

    public List<String> getfImages() {
        return fImages;
    }

    public Facility setfImages(List<String> fImages) {
        this.fImages = fImages;
        return this;
    }

    public String getIdByCoordinates(){
        if(coordinates==null) return null;
        return ""+coordinates.left + "-"+coordinates.top+"-"+coordinates.right+"-"+coordinates.bottom;
    }



    public static Rect getCoordinatesById(String id){
        if(id == null) return null;
        String[] splits = id.split("-");
        if(splits.length < 4) throw new IllegalStateException("id must be of form x-t-r-b where x,t,r,b are coordinates such that split should be of length 4");
        return new Rect(Integer.valueOf(splits[0]),
                Integer.valueOf(splits[1]),
                Integer.valueOf(splits[2]),
                Integer.valueOf(splits[3]));
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
                .setfImages(Database.listFromString(FIMAGES));
    }

}
