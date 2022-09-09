package com.ahgitdevelopment.androidfeaturelist.extensions

import android.util.Log

fun String.logD(tag: String) {
    Log.d(tag, this)
}

fun String.logI(tag: String) {
    Log.i(tag, this)
}

fun String.logW(tag: String) {
    Log.w(tag, this)
}

fun Exception.logE(tag: String) {
    Log.d(tag, this.message, this.cause)
}
