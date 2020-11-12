package com.lzf.wanandroidapp.core;

import java.io.Serializable;

import okhttp3.Cookie;

public class WanCookie implements Serializable {
    private String name;
    private String value;
    private long expiresAt;
    private String domain;
    private String path;
    private boolean secure;
    private boolean httpOnly;

    private boolean persistent; // True if 'expires' or 'max-age' is present.
    private boolean hostOnly; // True unless 'domain' is present.

    public WanCookie(Cookie cookie) {
        this.name = cookie.name();
        this.value = cookie.value();
        this.expiresAt = cookie.expiresAt();
        this.domain = cookie.domain();
        this.path = cookie.path();
        this.secure = cookie.secure();
        this.httpOnly = cookie.httpOnly();
        this.persistent = cookie.persistent();
        this.hostOnly = cookie.hostOnly();
    }

    public Cookie getCookie() {
        Cookie.Builder builder = new Cookie.Builder();
        builder.name(name)
                .value(value)
                .expiresAt(expiresAt)
                .domain(domain)
                .path(path);
        if (secure){
            builder.secure();
        }
        if (httpOnly){
            builder.httpOnly();
        }
        if (hostOnly){
            builder.httpOnly();
        }
        return builder.build();
    }

    @Override
    public String toString() {
        return "WanCookie{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", expiresAt=" + expiresAt +
                ", domain='" + domain + '\'' +
                ", path='" + path + '\'' +
                ", secure=" + secure +
                ", httpOnly=" + httpOnly +
                ", persistent=" + persistent +
                ", hostOnly=" + hostOnly +
                '}';
    }
}
