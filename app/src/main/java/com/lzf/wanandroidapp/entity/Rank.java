package com.lzf.wanandroidapp.entity;

public class Rank {

    private int coinCount;
    private int rank;
    private int userId;
    private String username;

    @Override
    public String toString() {
        return "Rank{" +
                "coinCount=" + coinCount +
                ", rank=" + rank +
                ", userId=" + userId +
                ", username='" + username + '\'' +
                '}';
    }

    public int getCoinCount() {
        return coinCount;
    }

    public void setCoinCount(int coinCount) {
        this.coinCount = coinCount;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
