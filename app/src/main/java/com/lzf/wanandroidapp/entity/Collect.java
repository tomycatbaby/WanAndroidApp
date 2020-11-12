package com.lzf.wanandroidapp.entity;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Collect{

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "title")
    public String title;
    @ColumnInfo(name = "link")
    public String link;

    public Collect(String title, String link) {
        this.title = title;
        this.link = link;
    }
}
