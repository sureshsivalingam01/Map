package com.mrright.map.utils

import android.app.Activity
import android.widget.TextView
import android.widget.Toast
import com.mrright.map.R


fun TextView.setIfNullOrEmpty(text: String?) {
    this.text =
        if (text.isNullOrEmpty()) {
            context.getString(R.string.dash)
        } else {
            context.getString(
                R.string.text,
                text
            )
        }
}


fun Activity.toast(text: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, duration).show()
}