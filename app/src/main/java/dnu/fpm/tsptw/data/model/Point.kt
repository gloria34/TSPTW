package dnu.fpm.tsptw.data.model

import dnu.fpm.tsptw.utils.DateUtils

data class Point(
    var latitude: Double?,
    var longitude: Double?,
    var hasError: Boolean = false
)
