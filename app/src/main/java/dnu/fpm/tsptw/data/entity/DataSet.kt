package dnu.fpm.tsptw.data.entity

import android.location.Address
import android.location.Geocoder
import androidx.core.os.ConfigurationCompat
import dnu.fpm.tsptw.App
import dnu.fpm.tsptw.data.model.Point
import dnu.fpm.tsptw.utils.DateUtils
import java.io.Serializable

class DataSet(
    var tripName: String = "",
    var date: Long = System.currentTimeMillis(),
    var points: ArrayList<Point>
) : Serializable {
    fun getStringDate(): String {
        return DateUtils.formattedDate(date)
    }

    fun getStartAddress(): String {
        val geocoder = Geocoder(
            App.instance,
            ConfigurationCompat.getLocales(App.instance.resources.configuration).get(0)
        )
        val address: List<Address> =
            geocoder.getFromLocation(points[0].latitude, points[0].longitude, 1)
        if (address.isNotEmpty() && address[0].getAddressLine(0) != null) {
            return address[0].getAddressLine(0)
        }
        return "${points[0].latitude}; ${points[0].longitude}"
    }

    fun getEndAddress(): String {
        val lastIndex = points.size - 1
        val geocoder = Geocoder(
            App.instance,
            ConfigurationCompat.getLocales(App.instance.resources.configuration).get(0)
        )
        val address: List<Address> =
            geocoder.getFromLocation(points[lastIndex].latitude, points[lastIndex].longitude, 1)
        if (address.isNotEmpty() && address[0].getAddressLine(0) != null) {
            return address[0].getAddressLine(0)
        }
        return "${points[lastIndex].latitude}; ${points[lastIndex].longitude}"
    }
}
