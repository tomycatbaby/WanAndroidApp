package com.lzf.wanandroidapp.entity;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

public class Collect implements Parcelable {
    private String title;
    private String link;

    public static final String FILED_TITLE = "title";
    public static final String FILED_LINK = "link";
    public static final String TABLE_NAME = "Collect";

    public Collect() {

    }

    public Collect(String title, String link) {
        this.title = title;
        this.link = link;
    }

    protected Collect(Parcel in) {
        title = in.readString();
        link = in.readString();
    }

    public ContentValues change() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(FILED_TITLE, title);
        contentValues.put(FILED_LINK, link);
        return contentValues;
    }

    public Collect toCollect(Cursor cursor) {
        this.title = cursor.getString(cursor.getColumnIndex(FILED_TITLE));
        this.link = cursor.getString(cursor.getColumnIndex(FILED_LINK));
        return this;
    }

    public static final Creator<Collect> CREATOR = new Creator<Collect>() {
        @Override
        public Collect createFromParcel(Parcel in) {
            return new Collect(in);
        }

        @Override
        public Collect[] newArray(int size) {
            return new Collect[size];
        }
    };

    @Override
    public String toString() {
        return "Collect{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(title);
        dest.writeString(link);
    }
}
