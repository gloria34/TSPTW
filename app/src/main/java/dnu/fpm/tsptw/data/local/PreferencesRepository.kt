package dnu.fpm.tsptw.data.local

import com.google.gson.Gson
import dnu.fpm.tsptw.data.entity.DataSet

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
            //TODO add mock data
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
}