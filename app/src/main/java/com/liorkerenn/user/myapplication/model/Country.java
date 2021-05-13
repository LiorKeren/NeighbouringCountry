package com.liorkerenn.user.myapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Country {
    @SerializedName("area")
    @Expose
    public String area;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("nativeName")
    @Expose
    public String nativeName;
    @SerializedName("borders")
    @Expose
    public List<String> borders;

    public Double getArea() {
        if (area == null){
            return 0.0;
        }
        return Double.valueOf(area);
    }

    public String getName() {
        return name;
    }
}
