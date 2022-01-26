package dnu.fpm.tsptw.data.entity

import dnu.fpm.tsptw.data.model.Point

data class DataSet(
    var name: String = "Custom data ${System.currentTimeMillis()}",
    var points: ArrayList<Point>
)
