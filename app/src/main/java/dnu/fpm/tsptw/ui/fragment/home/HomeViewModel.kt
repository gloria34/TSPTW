package dnu.fpm.tsptw.ui.fragment.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dnu.fpm.tsptw.data.entity.DataSet
import dnu.fpm.tsptw.utils.DateUtils

class HomeViewModel : ViewModel() {
    val currentTime: MutableLiveData<String> = MutableLiveData(DateUtils.currentTime())
    val currentDate: MutableLiveData<String> = MutableLiveData(DateUtils.currentDate())
    val tripsForToday: MutableLiveData<ArrayList<DataSet>> = MutableLiveData()
}