package com.lzf.wanandroidapp.core;

import android.content.SharedPreferences;
import android.preference.Preference;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.CookieManager;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.lzf.wanandroidapp.base.App;
import com.lzf.wanandroidapp.base.Constant;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

import static android.content.Context.MODE_PRIVATE;

public class HttpCookieJar implements CookieJar {
    private List<Cookie> cookies;

    private static final String COOKIE_SIZE = "cookie_size";
    private static final String COOKIE_FILE = "cookie_file";
    private static final String COOKIE_DATA = "cookie_data";

    public HttpCookieJar() {
        cookies = new ArrayList<>();
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> arg) {
        if (url.toString().equals("https://www.wanandroid.com/user/login")) {
            saveStringPreference(COOKIE_FILE, COOKIE_SIZE, String.valueOf(arg.size()));
            for (int i = 1; i < arg.size(); i++) {
                WanCookie wanCookie = new WanCookie(arg.get(i));
                saveObjectPreference(COOKIE_FILE, COOKIE_DATA + i, wanCookie);
            }
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        if (cookies.isEmpty()) {
            String size = getStringPreference(COOKIE_FILE, COOKIE_SIZE, "");
            if (!TextUtils.isEmpty(size)){
                for (int i = 1; i < Integer.parseInt(size); i++) {
                    WanCookie wanCookie = getObjectPreference(COOKIE_FILE, COOKIE_DATA + i);
                    if (wanCookie != null)
                        cookies.add(wanCookie.getCookie());
                }
            }
        }
        return cookies;
    }

    /**
     * 保存String类型至SharedPreferences
     *
     * @param file  the file
     * @param key   the key
     * @param value the value
     */
    public static void saveStringPreference(String file, String key, String value) {
        SharedPreferences sp = App.getContext().getSharedPreferences(file, MODE_PRIVATE);
        SharedPreferences.Editor mEditor = sp.edit();
        mEditor.putString(key, value);
        mEditor.apply();
    }

    /**
     * 从SharedPreferences获取String
     *
     * @param file          the file
     * @param key           the key
     * @param defaultString the default string
     * @return the string
     */
    public static String getStringPreference(String file, String key, String defaultString) {
        SharedPreferences sp = App.getContext().getSharedPreferences(file, MODE_PRIVATE);
        String value = sp.getString(key, defaultString);
        return value;
    }

    public static void saveObjectPreference(String file, String key, Object obj) {
        try {
            if (obj instanceof Serializable) {
                // 保存对象
                SharedPreferences.Editor sharedata = App.getContext().getSharedPreferences(file, MODE_PRIVATE).edit();
                //先将序列化结果写到byte缓存中，其实就分配一个内存空间
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream os = new ObjectOutputStream(bos);
                //将对象序列化写入byte缓存
                os.writeObject(obj);
                //将序列化的数据转为16进制保存
                String bytesToHexString = bytesToHexString(bos.toByteArray());
                //保存该16进制数组
                sharedata.putString(key, bytesToHexString);
                sharedata.apply();
            } else {
                throw new IOException("对象不可序列化");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * byte数组转16进制字符串
     *
     * @param bArray the b array
     * @return the string
     */
    public static String bytesToHexString(byte[] bArray) {
        if (bArray == null) {
            return null;
        }
        if (bArray.length == 0) {
            return "";
        }
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 16进制字符串转byte数组
     *
     * @param data the data
     * @return the byte [ ]
     */
    public static byte[] StringToBytes(String data) {
        String hexString = data.toUpperCase().trim();
        if (hexString.length() % 2 != 0) {
            return null;
        }
        byte[] retData = new byte[hexString.length() / 2];
        for (int i = 0; i < hexString.length(); i++) {
            int int_ch;  // 两位16进制数转化后的10进制数
            char hex_char1 = hexString.charAt(i); ////两位16进制数中的第一位(高位*16)
            int int_ch3;
            if (hex_char1 >= '0' && hex_char1 <= '9')
                int_ch3 = (hex_char1 - 48) * 16;   //// 0 的Ascll - 48
            else if (hex_char1 >= 'A' && hex_char1 <= 'F')
                int_ch3 = (hex_char1 - 55) * 16; //// A 的Ascll - 65
            else
                return null;
            i++;
            char hex_char2 = hexString.charAt(i); ///两位16进制数中的第二位(低位)
            int int_ch4;
            if (hex_char2 >= '0' && hex_char2 <= '9')
                int_ch4 = (hex_char2 - 48); //// 0 的Ascll - 48
            else if (hex_char2 >= 'A' && hex_char2 <= 'F')
                int_ch4 = hex_char2 - 55; //// A 的Ascll - 65
            else
                return null;
            int_ch = int_ch3 + int_ch4;
            retData[i / 2] = (byte) int_ch;//将转化后的数放入Byte里
        }
        return retData;
    }

    /**
     * 从SharedPreferences获取Object
     *
     * @param file the file
     * @param key  the key
     * @return the object
     */
    @Nullable
    public static <T> T getObjectPreference(String file, String key) {
        try {
            SharedPreferences sharedata = App.getContext().getSharedPreferences(file, MODE_PRIVATE);
            if (sharedata.contains(key)) {
                String string = sharedata.getString(key, "");
                if (TextUtils.isEmpty(string)) {
                    return null;
                } else {
                    //将16进制的数据转为数组，准备反序列化
                    byte[] stringToBytes = StringToBytes(string);
                    ByteArrayInputStream bis = new ByteArrayInputStream(stringToBytes);
                    ObjectInputStream is = new ObjectInputStream(bis);
                    //返回反序列化得到的对象
                    T readObject = (T) is.readObject();
                    return readObject;
                }
            } else {
                return null;
            }
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //所有异常返回null
        return null;
    }
}
