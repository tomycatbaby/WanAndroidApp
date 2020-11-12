package com.lzf.wanandroidapp.utils

import com.tencent.mmkv.MMKV

fun insert(key: String, value: String) {
    val mmkv = MMKV.defaultMMKV()
    mmkv.encode(key, value)
}

fun insert(key: String, value: Int) {
    val mmkv = MMKV.defaultMMKV()
    mmkv.encode(key, value)
}

fun insert(key: String, value: Long) {
    val mmkv = MMKV.defaultMMKV()
    mmkv.encode(key, value)
}

fun insert(key: String, value: Float) {
    val mmkv = MMKV.defaultMMKV()
    mmkv.encode(key, value)
}

fun insert(key: String, value: Double) {
    val mmkv = MMKV.defaultMMKV()
    mmkv.encode(key, value)
}

fun insert(key: String, value: Boolean) {
    val mmkv = MMKV.defaultMMKV()
    mmkv.encode(key, value)
}

fun query(key: String, defaultValue: String = ""): String {
    val mmkv = MMKV.defaultMMKV()
    return mmkv.decodeString(key, defaultValue)
}

fun query(key: String, defaultValue: Int = 0): Int {
    val mmkv = MMKV.defaultMMKV()
    return mmkv.decodeInt(key, defaultValue)
}

fun query(key: String, defaultValue: Long = 0L): Long {
    val mmkv = MMKV.defaultMMKV()
    return mmkv.decodeLong(key, defaultValue)
}

fun query(key: String, defaultValue: Float = 0f): Float {
    val mmkv = MMKV.defaultMMKV()
    return mmkv.decodeFloat(key, defaultValue)
}

fun query(key: String, defaultValue: Double = 0.0): Double {
    val mmkv = MMKV.defaultMMKV()
    return mmkv.decodeDouble(key, defaultValue)
}

fun query(key: String, defaultValue: Boolean = false): Boolean {
    val mmkv = MMKV.defaultMMKV()
    return mmkv.decodeBool(key, defaultValue)
}