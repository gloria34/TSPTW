package dnu.fpm.tsptw.ui.fragment.trip

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import dnu.fpm.tsptw.R
import dnu.fpm.tsptw.data.entity.DataSet
import dnu.fpm.tsptw.data.model.Point
import dnu.fpm.tsptw.databinding.FragmentTripBinding
import dnu.fpm.tsptw.databinding.ItemMapMarkerBinding
import dnu.fpm.tsptw.ui.adapter.PointsAdapter
import dnu.fpm.tsptw.ui.base.BaseFragment
import dnu.fpm.tsptw.utils.DeviceUtils
import java.util.*
import kotlin.collections.ArrayList


class TripFragment : BaseFragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    lateinit var binding: FragmentTripBinding
    lateinit var viewModel: TripViewModel
    var googleMap: GoogleMap? = null
    var shouldShowPoints = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(TripViewModel::class.java)
        binding = FragmentTripBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.isListVisible = false
        binding.mapImageView.setOnClickListener {
            binding.isListVisible = false
            binding.mapImageView.setImageResource(R.drawable.ic_map_checked)
            binding.listImageView.setImageResource(R.drawable.ic_list_unchecked)
        }
        binding.listImageView.setOnClickListener {
            binding.isListVisible = true
            binding.mapImageView.setImageResource(R.drawable.ic_map_unchecked)
            binding.listImageView.setImageResource(R.drawable.ic_list_checked)
        }
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
        initMap()
        val trip: DataSet? = arguments?.getSerializable("trip") as DataSet?
        if (trip != null) {
            binding.trip = trip
            val points: ArrayList<LatLng> = arrayListOf()
            for (point in trip.points) {
                points.add(LatLng(point.latitude, point.longitude))
            }
            val mainHandler = Handler(requireContext().mainLooper)

            val myRunnable = Runnable {
                viewModel.solve(points)
                if (googleMap != null) {
                    showPoints(trip)
                } else {
                    shouldShowPoints = true
                }
            }
            mainHandler.post(myRunnable)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showPoints(trip: DataSet) {
        binding.shortestDistanceTextView.text =
            getString(R.string.shortest_distance) + (viewModel.antTsp.bestTourLength / 1000).toInt() + getString(
                R.string.km
            )
        googleMap!!.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(trip.points[0].latitude, trip.points[0].longitude),
                14.0F
            )
        )
        for (point in trip.points) {
            val currentLatLng = LatLng(
                point.latitude,
                point.longitude
            )
            point.index = viewModel.antTsp.bestTour.indexOf(trip.points.indexOf(point))
            val marker: Marker? = googleMap!!.addMarker(
                MarkerOptions().position(
                    currentLatLng
                ).draggable(true)
                    .icon(
                        getMarkerIcon(
                            viewModel.antTsp.bestTour.indexOf(
                                trip.points.indexOf(
                                    point
                                )
                            )
                        )
                    )
            )
            if (marker != null) {
                marker.tag = point
            }
            val nextLatLng = if (point.index + 1 == trip.points.size) LatLng(
                trip.points[viewModel.antTsp.bestTour[0]].latitude,
                trip.points[viewModel.antTsp.bestTour[0]].longitude
            ) else LatLng(
                trip.points[viewModel.antTsp.bestTour[point.index + 1]].latitude,
                trip.points[viewModel.antTsp.bestTour[point.index + 1]].longitude
            )
            googleMap?.addPolyline(
                PolylineOptions()
                    .clickable(true)
                    .add(
                        currentLatLng,
                        nextLatLng
                    )
                    .color(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.dark_violet
                        )
                    )
            )
        }
        Collections.sort(trip.points, PointComparator())
        binding.pointsRecyclerView.adapter = PointsAdapter(trip.points)
    }

    override fun onMapReady(p0: GoogleMap) {
        googleMap = p0
        val trip: DataSet? = arguments?.getSerializable("trip") as DataSet?
        if (trip != null && shouldShowPoints) {
            showPoints(trip)
        }
        p0.setOnMapClickListener {
            binding.pointLayout.root.visibility = View.GONE
        }
        p0.setOnMarkerClickListener(this)
    }

    private fun initMap() {
        binding.mapView.onCreate(arguments)
        binding.mapView.onResume()
        binding.mapView.getMapAsync(this)
    }

    private fun getMarkerIcon(number: Int): BitmapDescriptor {
        val itemMapMarkerBinding: ItemMapMarkerBinding = ItemMapMarkerBinding.inflate(
            layoutInflater
        )
        when (number + 1) {
            1 -> {
                itemMapMarkerBinding.marker.setImageResource(R.drawable.ic_map_marker_1)
            }
            2 -> {
                itemMapMarkerBinding.marker.setImageResource(R.drawable.ic_map_marker_2)
            }
            3 -> {
                itemMapMarkerBinding.marker.setImageResource(R.drawable.ic_map_marker_3)
            }
            4 -> {
                itemMapMarkerBinding.marker.setImageResource(R.drawable.ic_map_marker_4)
            }
            else -> {
                itemMapMarkerBinding.marker.setImageResource(R.drawable.ic_map_marker_5)
            }
        }
        return BitmapDescriptorFactory.fromBitmap(
            getBitmapFromView(
                itemMapMarkerBinding.root,
                requireContext()
            )
        )
    }

    private fun getBitmapFromView(view: View, context: Context): Bitmap {
        val width = view.width
        val height = view.height
        val measuredWidth = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY)
        val measuredHeight = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY)
        view.measure(measuredWidth, measuredHeight)
        view.layout(
            0, 0, DeviceUtils.convertDpToPixel(25F, context).toInt(),
            DeviceUtils.convertDpToPixel(36F, context).toInt()
        )
        val b = Bitmap.createBitmap(
            DeviceUtils.convertDpToPixel(25F, context).toInt(),
            DeviceUtils.convertDpToPixel(36F, context).toInt(), Bitmap.Config.ARGB_8888
        )
        val c = Canvas(b)
        view.draw(c)
        return b
    }

    override fun onMarkerClick(p0: Marker): Boolean {
        val point: Point? = p0.tag as Point?
        if (point != null) {
            binding.pointLayout.point = point
            binding.pointLayout.root.visibility = View.VISIBLE
        }
        return true
    }
}