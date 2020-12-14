package com.example.roomrecyclerclickcheckbox.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import static androidx.room.OnConflictStrategy.IGNORE;

//import static android.arch.persistence.room.OnConflictStrategy.IGNORE;


@Dao
public interface DataDAO {

    //Insert one item
    @Insert(onConflict = IGNORE)
    void insertItem(DataItem item);

    // Delete one item
    @Delete
    void deleteItem(DataItem person);

    //Delete one item by id
    @Query("DELETE FROM DataItem WHERE id = :itemId")
    void deleteByItemId(long itemId);


    //Get all items
    @Query("SELECT * FROM DataItem")
    LiveData<List<DataItem>> getAllData();

    //Delete All
    @Query("DELETE FROM DataItem")
    void deleteAll();
}
