package dnu.fpm.tsptw.ui.fragment.createnewtrip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import dnu.fpm.tsptw.data.model.Point
import dnu.fpm.tsptw.databinding.FragmentCreateNewTripBinding
import dnu.fpm.tsptw.ui.base.BaseFragment

class CreateNewTripFragment : BaseFragment() {
    lateinit var binding: FragmentCreateNewTripBinding
    lateinit var viewModel: CreateNewTripViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(CreateNewTripViewModel::class.java)
        binding = FragmentCreateNewTripBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val points: ArrayList<Point> = arrayListOf(Point(0.0, 0.0, false))
        binding.pointsRecyclerView.adapter = CreateNewTripAdapter(
            points,
            object : OnChangePointListener {
                override fun onDeletePoint(position: Int) {
                    points.removeAt(position)
                }

                override fun onUpdateLatitude(latitude: String, position: Int) {
                    try {
                        points[position].latitude = latitude.toDouble()
                    } catch (e: Exception) {
                        e.printStackTrace()
                        points[position].latitude = 0.0
                    }
                }

                override fun onUpdateLongitude(longitude: String, position: Int) {
                    try {
                        points[position].longitude = longitude.toDouble()
                    } catch (e: Exception) {
                        e.printStackTrace()
                        points[position].longitude = 0.0
                    }
                }

                override fun onAddNewPoint() {
                    points.add(Point(0.0, 0.0, false))
                }
            })
    }
}