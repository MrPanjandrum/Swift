package com.rrr.swift;

public class RecyclerData
{
    String text, img;

    RecyclerData(){}    //default constructor

    public RecyclerData(String text, String img)
    {
        this.text = text;
        this.img = img;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
