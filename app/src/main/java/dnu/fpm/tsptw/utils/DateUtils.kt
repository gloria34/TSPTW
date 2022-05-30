package dnu.fpm.tsptw.utils

import android.annotation.SuppressLint
import android.content.Context
import android.util.DisplayMetrics
import java.text.SimpleDateFormat
import java.util.*


object DateUtils {
    @SuppressLint("SimpleDateFormat")
    fun formattedDate(milliseconds: Long?): String {
        return if (milliseconds != null) {
            val dateFormat = SimpleDateFormat("yyyy–MM–dd HH:mm")
            val date = Date(milliseconds)
            dateFormat.format(date)
        } else {
            ""
        }
    }

    fun convertDpToPixel(dp: Float, context: Context): Float {
        return dp * (context.resources.displayMetrics.densityDpi.toFloat()
                / DisplayMetrics.DENSITY_DEFAULT)
    }
}