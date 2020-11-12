package com.lzf.wanandroidapp.base

/**
 * 如果字符串为空，返回替代字符串，否则返回本身
 */
fun String?.ifIsNullOrEmpty(replace: String): String {
    return if (this.isNullOrEmpty()) replace else this
}