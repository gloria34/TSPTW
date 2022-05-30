package dnu.fpm.tsptw.data.entity

import dnu.fpm.tsptw.data.model.Point
import dnu.fpm.tsptw.utils.DateUtils
import java.io.Serializable

class DataSet(
    var tripName: String = "Custom data ${System.currentTimeMillis()}",
    var date: Long = System.currentTimeMillis(),
    var points: ArrayList<Point>
) : Serializable {
    fun getStringDate(): String {
        return DateUtils.formattedDate(date)
    }

    fun getStartAddress(): String {
        return "Test Address"
    }

    fun getEndAddress(): String {
        return "Test Address"
    }
}
