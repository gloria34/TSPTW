package dnu.fpm.tsptw.data.model

import java.io.Serializable

class Point(
    var latitude: Double,
    var longitude: Double,
    var index: Int
) : Serializable {
    fun getAddress(): String {
        return "$latitude; $longitude"
    }

    fun getStringIndex(): String {
        return (index + 1).toString()
    }
}
