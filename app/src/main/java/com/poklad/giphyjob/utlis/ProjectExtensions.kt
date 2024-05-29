package com.poklad.giphyjob.utlis

import android.util.Log
fun Any.tag(): String {
    return this::class.simpleName!!
}
fun Any.logError(msg: String) {
    Log.e("TAG: ${tag()}", msg)
}