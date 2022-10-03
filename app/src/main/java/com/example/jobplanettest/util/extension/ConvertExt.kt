package com.example.jobplanettest.util.extension

import android.content.Context
import android.util.DisplayMetrics
import java.text.DecimalFormat

fun Long.convertToAmountNotation(): String {
    val decimal = DecimalFormat("#,###")

    return decimal.format(this)
}

fun Number.convertToAmountNotation(): String {
    val decimal = DecimalFormat("#,###")

    return decimal.format(this)
}

fun String.substringYYMMDD(): String = this.substring(0, 7).replace("-", ".")




fun Context.dpToPixel(dp: Float): Float {
    return dp * (resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}
