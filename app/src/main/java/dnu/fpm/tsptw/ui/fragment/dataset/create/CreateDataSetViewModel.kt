package dnu.fpm.tsptw.ui.fragment.dataset.create

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dnu.fpm.tsptw.data.entity.DataSet
import dnu.fpm.tsptw.data.model.Point
import java.util.*

class CreateDataSetViewModel : ViewModel() {
    val dataSet = MutableLiveData<DataSet>()

    fun createEmptyDataSet() {
        val points = ArrayList<Point>()
        points.add(Point(null, null, null, null))
        val emptyDataSet: DataSet = DataSet(points = points, name = "")
        dataSet.postValue(emptyDataSet)
    }
}