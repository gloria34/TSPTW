package dnu.fpm.tsptw.ui.fragment.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dnu.fpm.tsptw.data.entity.DataSet
import dnu.fpm.tsptw.data.local.PreferencesRepository
import dnu.fpm.tsptw.utils.DateUtils

class HomeViewModel : ViewModel() {
    val currentTime: MutableLiveData<String> = MutableLiveData(DateUtils.currentTime())
    val currentDate: MutableLiveData<String> = MutableLiveData(DateUtils.currentDate())
    val isEmptyScreenVisible: MutableLiveData<Boolean> = MutableLiveData(false)

    fun loadTripsForToday(): ArrayList<DataSet> {
        val trips = PreferencesRepository.dataSets
        val result = arrayListOf<DataSet>()
        for (trip in trips) {
            if (android.text.format.DateUtils.isToday(trip.date)) {
                result.add(trip)
            }
        }
        if (result.isEmpty()) {
            isEmptyScreenVisible.postValue(true)
        }
        return result
    }
}