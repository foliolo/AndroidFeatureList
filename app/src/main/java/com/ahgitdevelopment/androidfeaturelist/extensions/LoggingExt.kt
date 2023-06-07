package com.ahgitdevelopment.androidfeaturelist.extensions

import android.util.Log

fun String.logD(tag: String = "TAG") {
    Log.d(tag, this)
}

fun String.logI(tag: String = "TAG") {
    Log.i(tag, this)
}

fun String.logW(tag: String = "TAG") {
    Log.w(tag, this)
}

fun Exception.logE(tag: String = "TAG") {
    Log.d(tag, this.message, this.cause)
}
