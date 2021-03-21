package com.example.applicationpetrol;

import android.database.sqlite.SQLiteDatabase;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ride")
public class Ride {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String startPlace, endPlace, otherPlace = "",cost;

    public void setStartPlace(String startPlace) {
        this.startPlace = startPlace;
    }

    public void setEndPlace(String endPlace) {
        this.endPlace = endPlace;
    }

    public void setOtherPlace(String otherPlace) {
        this.otherPlace = otherPlace;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getStartPlace() {
        return startPlace;
    }

    public String getEndPlace() {
        return endPlace;
    }

    public String getOtherPlace() {
        return otherPlace;
    }

    public String getCost() {
        return cost;
    }
}