package com.rrr.swift.LocationActivities;

public class Location
{
    private String address;
    private String image;
    private String dealDisc;
    private String text;
    private String img;
    private String addressImage;
    private String taskName;
    private String taskDescription;
    private int num;



    public Location() { }



    public Location(String image, String address, String dealDisc, String text, String img, String addressImage, String taskName, String taskDescription, int num)
    {
        this.address = address;
        this.image = image;
        this.dealDisc = dealDisc;
        this.text = text;
        this.img = img;
        this.addressImage = addressImage;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.num = num;
    }

    public String getAddressImage() { return addressImage; }

    public void setAddressImage(String addressImage) { this.addressImage = addressImage; }


    public String getDealDisc() {
        return dealDisc;
    }

    public void setDealDisc(String dealDisc) {
        this.dealDisc = dealDisc;
    }


    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }


    public String getImage() { return image; }

    public void setImage(String image) { this.image = image; }

    ///////////////////////////////////////////////////////////

    public String getImg() { return img; }


    public void setImg(String img) { this.img = img; }


    public String getText()
    {
        return text;
    }


    public void setText(String text)
    {
        this.text = text;
    }



    public String getTaskName() { return taskName; }


    public void setTaskName(String taskName) { this.taskName = taskName; }


    public String getTaskDescription() { return taskDescription; }


    public void setTaskDescription(String taskDescription) { this.taskDescription = taskDescription; }

    public int getNum() { return num; }

    public void setNum(int num) { this.num = num; }

}
