package com.iot.tingchev.parkinglist;

/**
 * Created by Harshit on 27/08/2016.
 */
public class ParkingSiteObject {

    private String siteName;
    private String location;
    private int occupied;
    private int capacity;

    public ParkingSiteObject(){
        siteName = "site A";
        location = "outer space";
        occupied = 0;
        capacity = 0;
    }


    // Setters
    public ParkingSiteObject setSiteName(String siteName){
        this.siteName = siteName;
        return this;
    }

    public ParkingSiteObject setLocation(String location){
        this.location = location;
        return this;
    }

    public ParkingSiteObject setOccupied(int occupied){
        this.occupied = occupied;
        return this;
    }

    public ParkingSiteObject setCapacity(int capacity){
        this.capacity = capacity;
        return this;
    }

    // Getters
    public String getSiteName(){
        return siteName;
    }

    public String getLocation(){
        return location;
    }

    public int getOccupied(){
        return occupied;
    }

    public int getCapacity(){
        return capacity;
    }

}
