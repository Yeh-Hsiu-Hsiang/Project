package com.example.clothes.database;

import java.io.Serializable;

public class getClothesMember implements Serializable {
    private  Long id;
    private  String imgPath;
    private  String name;
    private  String type;
    private  String style;
    private  Long tempLower;
    private  Long tempUpper;
    private  String seasen;
    private  String updatetime;


    public getClothesMember() {
    }

    public getClothesMember(Long id, String imgPath, String name,
                            String type, String style ,
                            Long tempLower, Long tempUpper,
                            String seasen, String updatetime ) {
        this.id = id;
        this.imgPath = imgPath;
        this.name = name;
        this.type = type;
        this.style = style;
        this.tempLower = tempLower;
        this.tempUpper = tempUpper;
        this.seasen = seasen;
        this.updatetime = updatetime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public Long getTempLower() {
        return tempLower;
    }

    public void setTempLower(Long tempLower) {
        this.tempLower = tempLower;
    }

    public Long getTempUpper() {
        return tempUpper;
    }

    public void setTempUpper(Long tempUpper) {
        this.tempUpper = tempUpper;
    }

    public String getSeasen() {
        return seasen;
    }

    public void setSeasen(String seasen) {
        this.seasen = seasen;
    }

    public String getUpdateTime() {
        return updatetime;
    }

    public void setUpdateTime(String updatetime) { this.updatetime = updatetime; }

}
