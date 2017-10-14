package com.naahac.tvaproject.models;

/**
 * Created by Natanael on 14. 06. 2017.
 */
public class Nutrient {
    private String name;
    private String unit;
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
