package com.rrr.swift.LocationActivities;

public class Location
{
    private String address;
    private String addressImage;
    private String taskName;
    private String taskDescription;
    private String taskArea;
    private String taskStatus;
    private int taskNum;



    public Location() { }



    public Location(String address, String addressImage, String taskName, String taskDescription, String taskArea, String taskStatus, int taskNum)
    {
        this.address = address;
        this.addressImage = addressImage;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskArea = taskArea;
        this.taskStatus = taskStatus;
        this.taskNum = taskNum;
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

    public String getTaskStatus() { return taskStatus; }

    public void setTaskStatus(String taskStatus) { this.taskStatus = taskStatus; }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public int getTaskNum() { return  taskNum; }

    public void setTaskNum(int taskNum) { this.taskNum = taskNum; }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



}
