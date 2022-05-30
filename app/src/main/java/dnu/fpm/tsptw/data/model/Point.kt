package dnu.fpm.tsptw.data.model

import java.io.Serializable

data class Point(
    var latitude: Double,
    var longitude: Double,
    var hasError: Boolean = false
) : Serializable
