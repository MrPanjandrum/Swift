package com.rrr.swift.LocationActivities;

public class Location
{
    private String address;
    private String addressImage;
    private String taskName;
    private String taskDescription;
    private String taskArea;


    public Location() { }



    public Location(String address, String addressImage, String taskName, String taskDescription, String taskArea)
    {
        this.address = address;
        this.addressImage = addressImage;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskArea = taskArea;
    }

    public Location(String address)
    {
        this.address = address;
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public String getAddressImage() { return addressImage; }

    public void setAddressImage(String addressImage) { this.addressImage = addressImage; }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public String getTaskName() { return taskName; }


    public void setTaskName(String taskName) { this.taskName = taskName; }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public String getTaskDescription() { return taskDescription; }


    public void setTaskDescription(String taskDescription) { this.taskDescription = taskDescription; }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public String getTaskArea() { return taskArea; }


    public void setTaskArea(String taskArea) { this.taskArea = taskArea; }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    
}
