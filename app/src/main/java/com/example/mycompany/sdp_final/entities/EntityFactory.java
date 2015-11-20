package com.example.mycompany.sdp_final.entities;

import android.graphics.Rect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Joseph Dalughut on 20/11/2015 at 9:35 AM.
 * Project name : Sdp_final.
 * Copyright (c) 2015 Meengle. All rights reserved.
 */
public class EntityFactory {

    public static List<Facility> generateFacilities(){
        List<Facility> facilities = new ArrayList<>();
        facilities.add(new Facility()
                .setId("gate")
                .setCoordinates(new Rect(403, 28, 457, 89))
                .setName("Gate")
                .setDescription("The school gate.")
                .setDirection("Approach the school gate :D")
                .setLatLng("9.198274, 12.499145"));
        facilities.add(new Facility()
                .setId("clinic")
                .setCoordinates(new Rect(79, 166, 138, 211))
                .setName("Clinic")
                .setDescription("The school Clinic is a place where students go to /n receive medical healthcare..")
                .setDirection("Northside of the campus :D")
                .setLatLng("9.195795, 12.497557"));
        facilities.add(new Facility()
                .setId("poh")
                .setCoordinates(new Rect(461, 527, 520, 563))
                .setName("POH")
                .setDescription("Peter Okocha Hall")
                .setDirection("Middle of campus")
                .setLatLng("9.191146, 12.501484"));
        return facilities;
    }

    public static List<Office> generateOffices(){
        List<Office> offices = new ArrayList<>();
        offices.add(new Office()
                .setId("nurses_office")
                .setName("Nurses Office")
        .setDescription("The office of the nurse")
        .setFacility("clinic")
        .setDirections("Once in clinic, go to the first office on your left."));
        offices.add(new Office()
                .setId("doctors_office")
                .setName("Doctors Office")
                .setDescription("The office of the Doctor")
                .setFacility("clinic")
                .setDirections("Once in clinic, ask for the doctors office"));
        return offices;
    }

    public static List<Staff> generateStaff(){
        List<Staff> staff = new ArrayList<>();
        staff.add(new Staff()
                .setId("01")
                .setName("Nurse Heather")
                .setDescription("I'm confident, clean, and will make sure you're health is a priority.")
                .setEmails(Arrays.asList("heather.kivolsky@aun.edu.ng", "hkivol@gmail.com"))
                .setPhones(Arrays.asList("+23481234567", "+2347056789012"))
                .setOffice("nurses_office")
                .setCourses(Arrays.asList("HEALTH 101", "GEN 101")));

        staff.add(new Staff()
                .setId("02")
                .setName("Doctor Kingsley")
                .setDescription("Health is not just a state of the body, it's a state of mind.")
                .setEmails(Arrays.asList("kinglsey.kivolsky@aun.edu.ng", "kingkivol@gmail.com"))
                .setPhones(Arrays.asList("+23481234567", "+2347056789012"))
                .setOffice("doctors_office")
                .setCourses(Arrays.asList("HEALTH 101", "GEN 101", "PHI 101")));
        return staff;
    }

}
