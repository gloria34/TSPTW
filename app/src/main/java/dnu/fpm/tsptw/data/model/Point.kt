package dnu.fpm.tsptw.data.model

import dnu.fpm.tsptw.utils.DateUtils

class Point(
    var latitude: Double?,
    var longitude: Double?,
    var timeFrom: Long?,
    var timeTo: Long?,
    var hasError: Boolean = false
) {
    fun formattedTimeFrom(): String {
        return DateUtils.formattedDate(timeFrom)
    }

    fun formattedTimeTo(): String {
        return DateUtils.formattedDate(timeTo)
    }
}
