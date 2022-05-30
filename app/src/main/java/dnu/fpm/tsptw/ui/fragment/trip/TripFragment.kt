package dnu.fpm.tsptw.ui.fragment.trip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import dnu.fpm.tsptw.data.entity.DataSet
import dnu.fpm.tsptw.databinding.FragmentTripBinding
import dnu.fpm.tsptw.ui.base.BaseFragment

class TripFragment : BaseFragment(), OnMapReadyCallback {
    lateinit var binding: FragmentTripBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTripBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMap()
        val trip: DataSet? = arguments?.getSerializable("trip") as DataSet?
        if (trip != null) {
            binding.trip = trip
        }
    }

    override fun onMapReady(p0: GoogleMap) {

    }

    private fun initMap() {
        binding.mapView.onCreate(arguments)
        binding.mapView.onResume()
        binding.mapView.getMapAsync(this)
    }
}