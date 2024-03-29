package dnu.fpm.tsptw.utils

import android.content.Context
import android.util.DisplayMetrics

object DeviceUtils {

    fun convertDpToPixel(dp: Float, context: Context): Float {
        return dp * (context.resources.displayMetrics.densityDpi.toFloat()
                / DisplayMetrics.DENSITY_DEFAULT)
    }
}