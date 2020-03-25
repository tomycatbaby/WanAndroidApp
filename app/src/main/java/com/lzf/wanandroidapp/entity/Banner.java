package com.lzf.wanandroidapp.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 轮播图
 */
public class Banner implements Parcelable {
    String desc;
    int id;
    String imagePath;
    String isVisible;
    int order;
    String title;
    int type;
    String url;

    //反序列化
    protected Banner(Parcel in) {
        desc = in.readString();
        id = in.readInt();
        imagePath = in.readString();
        isVisible = in.readString();
        order = in.readInt();
        title = in.readString();
        type = in.readInt();
        url = in.readString();
    }

    //
    public static final Creator<Banner> CREATOR = new Creator<Banner>() {
        @Override
        public Banner createFromParcel(Parcel in) {
            return new Banner(in);
        }

        @Override
        public Banner[] newArray(int size) {
            return new Banner[size];
        }
    };

    @Override
    public String toString() {
        return "Banner{" +
                "desc='" + desc + '\'' +
                ", id=" + id +
                ", imagePath='" + imagePath + '\'' +
                ", isVisible='" + isVisible + '\'' +
                ", order=" + order +
                ", title='" + title + '\'' +
                ", type=" + type +
                ", url='" + url + '\'' +
                '}';
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(String isVisible) {
        this.isVisible = isVisible;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    //序列化的过程叫writeToParcel
    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(desc);
        dest.writeInt(id);
        dest.writeString(imagePath);
        dest.writeString(isVisible);
        dest.writeInt(order);
        dest.writeString(title);
        dest.writeInt(type);
        dest.writeString(url);
    }

}
