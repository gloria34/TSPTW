package dnu.fpm.tsptw.data.local

import com.google.gson.Gson
import dnu.fpm.tsptw.data.entity.DataSet
import dnu.fpm.tsptw.data.model.Point

object PreferencesRepository {
    var dataSets: ArrayList<DataSet>
        get() {
            val strings = PreferencesHelper.loadStringSet(PreferencesHelper.DATA_SETS)
            val dataSets: ArrayList<DataSet> = arrayListOf()
            if (strings != null) {
                for (string in strings) {
                    dataSets.add(Gson().fromJson(string, DataSet::class.java))
                }
            }
            if (dataSets.isEmpty()) {
                dataSets.addAll(getMockTrips())
            }
            return dataSets
        }
        set(dataSets) {
            val strings: MutableSet<String> = mutableSetOf()
            for (dataSet in dataSets) {
                strings.add(Gson().toJson(dataSet))
            }
            PreferencesHelper.saveStringSet(
                PreferencesHelper.DATA_SETS,
                strings
            )
        }

    private fun getMockTrips(): ArrayList<DataSet> {
        val trips = ArrayList<DataSet>()
        trips.add(
            DataSet(
                tripName = "Lafayette",
                date = System.currentTimeMillis() - 2592000000L,
                points = arrayListOf(
                    Point(30.233417, -92.101965, 0),
                    Point(30.233155, -92.014507, 0),
                    Point(30.201409, -91.999563, 0),
                    Point(30.167296, -92.036249, 0),
                    Point(30.109821, -92.240990, 0)
                )
            )
        )
        return trips
    }
}