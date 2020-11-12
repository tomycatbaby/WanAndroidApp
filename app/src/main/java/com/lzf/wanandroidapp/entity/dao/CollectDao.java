package com.lzf.wanandroidapp.entity.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.lzf.wanandroidapp.entity.Collect;

import java.util.List;

@Dao
public interface CollectDao {
    @Insert
    void insertAll(Collect... collects);

    @Query("SELECT * FROM collect")
    List<Collect> getAll();

    @Delete
    void delete(Collect... collects);
}
