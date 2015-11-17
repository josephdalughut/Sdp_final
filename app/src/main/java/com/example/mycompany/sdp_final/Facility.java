package com.example.mycompany.sdp_final;

import android.graphics.Rect;

import java.util.ArrayList;
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

    public String getFImagesAsString(){
        if(getfImages()==null || getfImages().isEmpty()) return "";
        String string = "";
        for(int i = 0; i < getfImages().size(); i++){
            string = string + (i == getfImages().size()-1 ? getfImages().get(i) : getfImages().get(i)+",");
        }
        return string;
    }

    public static List<String> getFImagesFromString(String string){
        if(string == null) return null;
        String[] splits = string.split(",");
        List<String> strings = new ArrayList<>(splits.length);
        for(String s : splits)
            strings.add(s);
        return strings;
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

}
