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

    @SuppressLint("SimpleDateFormat")
    fun currentDate(): String {
        val dateFormat = SimpleDateFormat("dd MMM yyyy")
        val date = Date()
        return dateFormat.format(date)
    }

    @SuppressLint("SimpleDateFormat")
    fun currentTime(): String {
        val dateFormat = SimpleDateFormat("HH:mm")
        val date = Date()
        return dateFormat.format(date)
    }

    @SuppressLint("SimpleDateFormat")
    fun formattedYearMonth(milliseconds: Long): String {
        val dateFormat = SimpleDateFormat("MMM yyyy", Locale.ENGLISH)
        val date = Date(milliseconds)
        return dateFormat.format(date)
    }
}