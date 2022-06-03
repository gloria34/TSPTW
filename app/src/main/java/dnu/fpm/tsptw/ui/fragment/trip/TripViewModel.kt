package dnu.fpm.tsptw.ui.fragment.trip

import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import dnu.fpm.tsptw.data.model.Point
import dnu.fpm.tsptw.helpers.AntTsp

class TripViewModel : ViewModel() {
    val antTsp = AntTsp()

    fun solve(points: ArrayList<LatLng>) {
        antTsp.readGraph(distances(points))
        antTsp.solve()
    }

    private fun distances(points: ArrayList<LatLng>): String {
        var result = ""
        for (point1 in points) {
            for (point2 in points) {
                result += "${SphericalUtil.computeDistanceBetween(point1, point2)} "
            }
            result += "/"
        }
        return result
    }
}

class PointComparator : Comparator<Point> {
    override fun compare(o1: Point, o2: Point): Int {
        return o1.index.compareTo(o2.index)
    }
}