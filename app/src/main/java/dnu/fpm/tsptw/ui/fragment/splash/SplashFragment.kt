package dnu.fpm.tsptw.ui.fragment.splash

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import dnu.fpm.tsptw.R
import dnu.fpm.tsptw.databinding.FragmentSplashBinding
import dnu.fpm.tsptw.helpers.AntTsp
import dnu.fpm.tsptw.ui.base.BaseFragment


class SplashFragment : BaseFragment() {
    lateinit var binding: FragmentSplashBinding
    lateinit var viewModel: SplashViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(SplashViewModel::class.java)
        binding = FragmentSplashBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val points = arrayListOf(
            LatLng(48.419765, 35.022180),
            LatLng(48.396415, 35.040163),
            LatLng(48.388844, 35.221928),
            LatLng(48.449415, 35.139776),
            LatLng(48.473328, 35.093393)
        )
        val anttsp = AntTsp()
        anttsp.readGraph(distances(points))
        anttsp.solve()
        Handler().postDelayed({ findNavController().navigate(R.id.action_splashFragment_to_homeFragment) }, 3000)
        // binding.test.text = anttsp.bestTourLength.toString() + " " + anttsp.bestTour.toString()
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
}