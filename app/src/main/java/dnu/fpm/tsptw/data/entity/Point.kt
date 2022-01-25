package dnu.fpm.tsptw.data.entity

data class Point(
    var latitude: Double?,
    var longitude: Double?,
    var time: Long?,
    var hasError: Boolean = false
)
