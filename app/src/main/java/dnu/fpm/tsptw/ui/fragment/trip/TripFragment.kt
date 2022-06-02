package dnu.fpm.tsptw.ui.fragment.trip

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import com.google.maps.android.SphericalUtil
import dnu.fpm.tsptw.R
import dnu.fpm.tsptw.data.entity.DataSet
import dnu.fpm.tsptw.data.model.Point
import dnu.fpm.tsptw.databinding.FragmentTripBinding
import dnu.fpm.tsptw.databinding.ItemMapMarkerBinding
import dnu.fpm.tsptw.helpers.AntTsp
import dnu.fpm.tsptw.ui.adapter.PointsAdapter
import dnu.fpm.tsptw.ui.base.BaseFragment
import dnu.fpm.tsptw.utils.DateUtils
import dnu.fpm.tsptw.utils.DeviceUtils


class TripFragment : BaseFragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    lateinit var binding: FragmentTripBinding
    val anttsp = AntTsp()
    var googleMap: GoogleMap? = null
    var shouldShowPoints = false
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
            val mainHandler = Handler(requireContext().mainLooper);

            val myRunnable = Runnable {
                anttsp.readGraph(distances(points))
                anttsp.solve()
                if (googleMap != null) {
                    binding.shortestDistanceTextView.text =
                        "Shortest distance: " + (anttsp.bestTourLength / 1000).toInt() + " km"
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
                        point.index = anttsp.bestTour.indexOf(trip.points.indexOf(point))
                        val marker: Marker? = googleMap!!.addMarker(
                            MarkerOptions().position(
                                currentLatLng
                            ).draggable(true)
                                .icon(
                                    getMarkerIcon(
                                        anttsp.bestTour.indexOf(
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
                            trip.points[anttsp.bestTour[0]].latitude,
                            trip.points[anttsp.bestTour[0]].longitude
                        ) else LatLng(
                            trip.points[anttsp.bestTour[point.index + 1]].latitude,
                            trip.points[anttsp.bestTour[point.index + 1]].longitude
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
                    binding.pointsRecyclerView.adapter = PointsAdapter(trip.points)
                } else {
                    shouldShowPoints = true
                }
            }
            mainHandler.post(myRunnable)
        }
    }

    fun distances(points: ArrayList<LatLng>): String {
        var result = ""
        for (point1 in points) {
            for (point2 in points) {
                result += "${SphericalUtil.computeDistanceBetween(point1, point2)} "
            }
            result += "/"
        }
        return result
    }

    override fun onMapReady(p0: GoogleMap) {
        googleMap = p0
        val trip: DataSet? = arguments?.getSerializable("trip") as DataSet?
        if (trip != null && shouldShowPoints) {
            binding.shortestDistanceTextView.text =
                "Shortest distance: " + (anttsp.bestTourLength / 1000).toInt() + " km"
            p0.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(trip.points[0].latitude, trip.points[0].longitude),
                    14.0F
                )
            )
            for (point in trip.points) {
                val marker: Marker? = p0.addMarker(
                    MarkerOptions().position(
                        LatLng(
                            point.latitude,
                            point.longitude
                        )
                    ).draggable(true)
                        .icon(getMarkerIcon(anttsp.bestTour[trip.points.indexOf(point)]))
                )
                if (marker != null) {
                    marker.tag = point
                }
            }
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