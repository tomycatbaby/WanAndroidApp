package com.lzf.wanandroidapp.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class WanDatabaseHelper extends SQLiteOpenHelper {
    private Context mContext;
    public static final String CREATE_COLLECT = "create table Collect ("
            + "id integer primary key autoincrement,"
            + "title text,"
            + "link text)";
    public static final String COLLECT_DATABASE_NAME = "CollectStore.db";

    public WanDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_COLLECT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
