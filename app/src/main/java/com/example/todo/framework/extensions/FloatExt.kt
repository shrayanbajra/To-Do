package com.example.todo.framework.extensions

import android.content.Context
import android.util.DisplayMetrics
import timber.log.Timber


fun Float.roundOff(numberOfDecimalDigits: Int = 2): String {

    return try {

        Timber.d("Before rounding off to $numberOfDecimalDigits digits: $this")
        val roundedOffNumber = String.format("%.${numberOfDecimalDigits}f", this)

        Timber.d("After rounding off to $numberOfDecimalDigits digits: $roundedOffNumber")
        roundedOffNumber

    } catch (ex: NumberFormatException) {

        "$this"

    }

}

/**
 * This method converts dp unit to equivalent pixels, depending on device density.
 *
 * @param this A value in dp (density independent pixels) unit. Which we need to convert into pixels
 * @param context Context to get resources and device specific display metrics
 * @return A float value to represent px equivalent to dp depending on device density
 */
fun Float.convertDpToPixel(context: Context): Float {
    return this * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

/**
 * This method converts device specific pixels to density independent pixels.
 *
 * @param this A value in px (pixels) unit. Which we need to convert into dp
 * @param context Context to get resources and device specific display metrics
 * @return A float value to represent dp equivalent to px value
 */
fun Float.convertPixelsToDp(context: Context): Float {
    return this / (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}