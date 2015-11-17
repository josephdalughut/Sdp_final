package com.example.mycompany.sdp_final;

import android.graphics.Rect;
import android.media.Image;
import android.provider.MediaStore;

import java.util.List;

/**
 * Created by Awa on 11/13/2015.
 */


public class Facility {
    private Rect coordinates;
    private String name;
    private String description;
    private String direction;
    private List<Image> fImages;

    public Facility(Rect coordinates, String name) {
        this.coordinates = coordinates;
        this.name = name;
    }

    public Rect getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Rect coordinates) {
        this.coordinates = coordinates;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public List<Image> getfImages() {
        return fImages;
    }

    public void setfImages(List<Image> fImages) {
        this.fImages = fImages;
    }
}
