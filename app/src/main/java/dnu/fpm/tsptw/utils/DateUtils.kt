package dnu.fpm.tsptw.utils

import android.annotation.SuppressLint
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
}