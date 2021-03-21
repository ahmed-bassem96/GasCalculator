package com.example.applicationpetrol;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RideDAO {
    @Query("select * from ride ORDER BY id DESC ")
 List<com.example.applicationpetrol.Ride>selectStartPlace();
@Insert
    long insertRide(Ride ride);

   // @Query ("DELETE FROM ride WHERE id IN (SELECT id FROM ride  LIMIT 1)")
  @Query("DELETE FROM ride WHERE id IN (SELECT id FROM ride ORDER BY id DESC LIMIT 1)")
  void del();


    @Query("DELETE FROM ride WHERE id IN (SELECT id FROM ride  )")
   void clearAll();


    @Query("DELETE FROM ride WHERE id = :id")
    void deleteRide(int id);

}
