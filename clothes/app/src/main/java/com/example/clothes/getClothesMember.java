package com.example.clothes;

import java.io.Serializable;

public class getClothesMember implements Serializable {
    public String id;
    public String imgPath;
    public String name;


    public getClothesMember() {

    }

    public getClothesMember(String id, String imgPath, String name) {
        this.id = id;
        this.imgPath = imgPath;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
}
