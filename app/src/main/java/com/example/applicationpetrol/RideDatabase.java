package com.example.applicationpetrol;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Ride.class},version = 1)
public  abstract class RideDatabase extends RoomDatabase {
    public abstract RideDAO rideDAO();
    private static RideDatabase OurInstance;

    public static RideDatabase getInstance(Context context) {
        if (OurInstance == null) {
            OurInstance = Room.databaseBuilder(context,
                    RideDatabase.class, "gasApp.db")
                    .createFromAsset("database/gasApp.db")
                    .allowMainThreadQueries()
                    .build();
        }
        return OurInstance;
    }

}
