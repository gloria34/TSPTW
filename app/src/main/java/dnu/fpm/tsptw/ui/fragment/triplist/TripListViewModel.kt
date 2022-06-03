package dnu.fpm.tsptw.ui.fragment.triplist

import androidx.lifecycle.ViewModel
import dnu.fpm.tsptw.data.entity.DataSet
import dnu.fpm.tsptw.data.local.PreferencesRepository
import dnu.fpm.tsptw.data.model.Month
import dnu.fpm.tsptw.utils.DateUtils
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList

class TripListViewModel : ViewModel() {
    private val months = ArrayList<Month>()
    private val trips = PreferencesRepository.dataSets

    fun getMonthList(): ArrayList<Month> {
        for (trip in trips) {
            val month = DateUtils.formattedYearMonth(trip.date)
            if (!isMonthListContainsMonth(months, month)) {
                months.add(Month(month, trip.date))
            }
        }
        sortMonths()
        return months
    }

    private fun sortMonths() {
        Collections.sort(months, MonthComparator())
    }

    fun getTripsForSelectedPeriod(position: Int): ArrayList<DataSet> {
        val month = months[position]
        val tripsForMonth = ArrayList<DataSet>()
        for (trip in trips) {
            if (DateUtils.formattedYearMonth(trip.date) == month.monthName) {
                tripsForMonth.add(trip)
            }
        }
        return tripsForMonth
    }

    private fun isMonthListContainsMonth(months: ArrayList<Month>, monthString: String): Boolean {
        for (month in months) {
            if (month.monthName == monthString) {
                return true
            }
        }
        return false
    }
}

class MonthComparator : Comparator<Month> {
    override fun compare(o1: Month, o2: Month): Int {
        return o1.millis.compareTo(o2.millis)
    }
}