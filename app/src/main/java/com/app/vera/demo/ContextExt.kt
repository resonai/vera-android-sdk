package com.app.vera.demo

import android.content.Context
import android.widget.Toast

fun Context.showToast(msg: String?, duration: Int? = null) {
    Toast.makeText(this, msg, duration ?: Toast.LENGTH_SHORT).show()
}