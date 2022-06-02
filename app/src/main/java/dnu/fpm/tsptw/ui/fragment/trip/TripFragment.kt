package dnu.fpm.tsptw.ui.fragment.trip

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import com.google.maps.android.SphericalUtil
import dnu.fpm.tsptw.R
import dnu.fpm.tsptw.data.entity.DataSet
import dnu.fpm.tsptw.databinding.FragmentTripBinding
import dnu.fpm.tsptw.databinding.ItemMapMarkerBinding
import dnu.fpm.tsptw.helpers.AntTsp
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
                        val marker: Marker? = googleMap!!.addMarker(
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
                    googleMap!!.setOnMapClickListener {

                    }
                    googleMap!!.setOnMarkerClickListener(this)
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
            p0.setOnMapClickListener {

            }
            p0.setOnMarkerClickListener(this)
        }
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
        return true
    }
}