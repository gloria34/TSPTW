package dnu.fpm.tsptw.ui.fragment.createnewtrip

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dnu.fpm.tsptw.data.entity.DataSet
import dnu.fpm.tsptw.data.model.Point

class CreateNewTripViewModel : ViewModel() {
    val dataSet: MutableLiveData<DataSet> =
        MutableLiveData(DataSet(points = arrayListOf(emptyPoint())))

    fun saveDataSet() {
        //TODO call repository method
    }

    private fun emptyPoint(): Point = Point(0.0, 0.0, 0)
}