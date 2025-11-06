package com.example.kingburguer.common

import android.icu.text.DateFormat
import android.os.Build
import androidx.annotation.RequiresApi
import java.util.Date
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.N)
fun Date.formatted(): String {
    val df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault())
    return df.format(this)
}