package com.lzf.wanandroidapp.core;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.lzf.wanandroidapp.entity.Collect;
import com.lzf.wanandroidapp.entity.dao.CollectDao;

@Database(entities = {Collect.class},version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CollectDao collectDao();
}
